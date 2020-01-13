package org.jqassistant.contrib.plugin.ddd.test.concept;

import com.buschmais.jqassistant.core.report.api.model.Result;
import com.buschmais.jqassistant.core.rule.api.model.RuleException;
import com.buschmais.jqassistant.plugin.java.api.model.TypeDescriptor;
import org.jqassistant.contrib.plugin.ddd.test.AbstractJavaDDDPluginIT;
import org.jqassistant.contrib.plugin.ddd.test.set.bc.App;
import org.jqassistant.contrib.plugin.ddd.test.set.bc.bc1.Product;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BoundedContextIT extends AbstractJavaDDDPluginIT {

    @Test
    public void boundedContextClass() throws RuleException {
        scanClasses(Product.class);
        assertEquals(Result.Status.SUCCESS, applyConcept("java-ddd:BoundedContextType").getStatus());
        store.beginTransaction();
        assertThat(query("MATCH (bC:DDD:BoundedContext) RETURN bC").getColumn("bC").size()).isEqualTo(1);
        assertThat(query("MATCH (bC:DDD:BoundedContext{name: 'catalog'}) RETURN bC").getColumn("bC").size()).isEqualTo(1);
        List<TypeDescriptor> types = query("MATCH (:DDD:BoundedContext{name: 'catalog'})-[:CONTAINS]->(t:Type) RETURN t").getColumn("t");
        assertThat(types.size()).isEqualTo(1);
        assertThat(types.get(0).getName()).isEqualTo("Product");
        store.commitTransaction();
    }

    @Test
    public void boundedContextPackage() throws RuleException {
        scanClassesAndPackages(App.class);
        assertEquals(Result.Status.SUCCESS, applyConcept("java-ddd:BoundedContextPackage").getStatus());
        store.beginTransaction();
        assertThat(query("MATCH (bC:DDD:BoundedContext) RETURN bC").getColumn("bC").size()).isEqualTo(2);
        assertThat(query("MATCH (bC:DDD:BoundedContext{name: 'order'}) RETURN bC").getColumn("bC").size()).isEqualTo(1);
        List<TypeDescriptor> types = query("MATCH (:DDD:BoundedContext{name: 'order'})-[:CONTAINS]->(t:Type) RETURN t ORDER BY t.fqn").getColumn("t");
        assertThat(types.size()).isEqualTo(3);
        assertThat(types.get(0).getName()).isEqualTo("OrderRepository");
        assertThat(types.get(1).getName()).isEqualTo("OrderService");
        assertThat(types.get(2).getName()).isEqualTo("package-info");
        store.commitTransaction();
    }

    @Test
    public void definedBoundedContextDependencies() throws RuleException {
        scanClassesAndPackages(App.class);
        assertEquals(Result.Status.SUCCESS, applyConcept("java-ddd:DefinedBoundedContextDependencies").getStatus());
        store.beginTransaction();
        assertThat(query("MATCH (bC:DDD:BoundedContext) RETURN bC").getColumn("bC").size()).isEqualTo(2);
        assertThat(query("MATCH (bC:DDD:BoundedContext{name: 'catalog'}) RETURN bC").getColumn("bC").size()).isEqualTo(1);
        assertThat(query("MATCH (bC:DDD:BoundedContext{name: 'order'}) RETURN bC").getColumn("bC").size()).isEqualTo(1);
        assertThat(query("MATCH (:DDD:BoundedContext)-[d:DEFINES_DEPENDENCY]->(:DDD:BoundedContext) RETURN d").getColumn("d").size()).isEqualTo(1);
        assertThat(query("MATCH (:DDD:BoundedContext{name: 'order'})-[d:DEFINES_DEPENDENCY]->(:DDD:BoundedContext{name: 'catalog'}) RETURN d").getColumn("d").size()).isEqualTo(1);
        store.commitTransaction();
    }

    @Test
    public void boundedContextDependency() throws RuleException {
        scanClassesAndPackages(App.class);
        assertEquals(Result.Status.SUCCESS, applyConcept("java-ddd:BoundedContextDependency").getStatus());
        store.beginTransaction();
        assertThat(query("MATCH (:DDD:BoundedContext)-[d:DEPENDS_ON]->(:DDD:BoundedContext) RETURN d").getColumn("d").size()).isEqualTo(1);
        assertThat(query("MATCH (:DDD:BoundedContext{name: 'order'})-[d:DEPENDS_ON]->(:DDD:BoundedContext{name: 'catalog'}) RETURN d").getColumn("d").size()).isEqualTo(1);
        store.commitTransaction();
    }

}
