package org.jqassistant.contrib.plugin.ddd.test.concept;

import com.buschmais.jqassistant.core.analysis.api.Result;
import com.buschmais.jqassistant.core.analysis.api.rule.RuleException;
import com.buschmais.jqassistant.plugin.java.api.model.TypeDescriptor;
import com.buschmais.jqassistant.plugin.java.test.AbstractJavaPluginIT;
import org.jqassistant.contrib.plugin.ddd.test.set.bc.App;
import org.jqassistant.contrib.plugin.ddd.test.set.bc.bc1.Product;
import org.jqassistant.contrib.plugin.ddd.test.set.bc.bc2.OrderService;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BoundedContextIT extends AbstractJavaPluginIT {

    @Test
    public void boundedContextClass() throws RuleException {
        scanClasses(Product.class);
        assertEquals(applyConcept("java-ddd:BoundedContextType").getStatus(), Result.Status.SUCCESS);
        store.beginTransaction();
        assertThat(query("MATCH (bC:DDD:BoundedContext) RETURN bC").getColumn("bC").size(), equalTo(1));
        assertThat(query("MATCH (bC:DDD:BoundedContext{name: 'catalog'}) RETURN bC").getColumn("bC").size(), equalTo(1));
        List<TypeDescriptor> types = query("MATCH (:DDD:BoundedContext{name: 'catalog'})-[:CONTAINS]->(t:Type) RETURN t").getColumn("t");
        assertThat(types.size(), equalTo(1));
        assertThat(types.get(0).getName(), equalTo("Product"));
        store.commitTransaction();
    }

    @Test
    public void boundedContextPackage() throws RuleException {
        scanClassPathDirectory(getClassesDirectory(OrderService.class));
        assertEquals(applyConcept("java-ddd:BoundedContextPackage").getStatus(), Result.Status.SUCCESS);
        store.beginTransaction();
        assertThat(query("MATCH (bC:DDD:BoundedContext) RETURN bC").getColumn("bC").size(), equalTo(1));
        assertThat(query("MATCH (bC:DDD:BoundedContext{name: 'order'}) RETURN bC").getColumn("bC").size(), equalTo(1));
        List<TypeDescriptor> types = query("MATCH (:DDD:BoundedContext{name: 'order'})-[:CONTAINS]->(t:Type) RETURN t ORDER BY t.fqn").getColumn("t");
        assertThat(types.size(), equalTo(3));
        assertThat(types.get(0).getName(), equalTo("OrderRepository"));
        assertThat(types.get(1).getName(), equalTo("OrderService"));
        assertThat(types.get(2).getName(), equalTo("package-info"));
        store.commitTransaction();
    }

    @Test
    public void definedBoundedContextDependencies() throws RuleException {
        scanClassPathDirectory(getClassesDirectory(App.class));
        assertEquals(applyConcept("java-ddd:DefinedBoundedContextDependencies").getStatus(), Result.Status.SUCCESS);
        store.beginTransaction();
        assertThat(query("MATCH (bC:DDD:BoundedContext) RETURN bC").getColumn("bC").size(), equalTo(2));
        assertThat(query("MATCH (bC:DDD:BoundedContext{name: 'catalog'}) RETURN bC").getColumn("bC").size(), equalTo(1));
        assertThat(query("MATCH (bC:DDD:BoundedContext{name: 'order'}) RETURN bC").getColumn("bC").size(), equalTo(1));
        assertThat(query("MATCH (:DDD:BoundedContext)-[d:DEFINES_DEPENDENCY]->(:DDD:BoundedContext) RETURN d").getColumn("d").size(), equalTo(1));
        assertThat(query("MATCH (:DDD:BoundedContext{name: 'order'})-[d:DEFINES_DEPENDENCY]->(:DDD:BoundedContext{name: 'catalog'}) RETURN d").getColumn("d").size(), equalTo(1));
        store.commitTransaction();
    }

    @Test
    public void boundedContextDependency() throws RuleException {
        scanClassPathDirectory(getClassesDirectory(App.class));
        assertEquals(applyConcept("java-ddd:BoundedContextDependency").getStatus(), Result.Status.SUCCESS);
        store.beginTransaction();
        assertThat(query("MATCH (:DDD:BoundedContext)-[d:DEPENDS_ON]->(:DDD:BoundedContext) RETURN d").getColumn("d").size(), equalTo(1));
        assertThat(query("MATCH (:DDD:BoundedContext{name: 'order'})-[d:DEPENDS_ON]->(:DDD:BoundedContext{name: 'catalog'}) RETURN d").getColumn("d").size(), equalTo(1));
        store.commitTransaction();
    }

}
