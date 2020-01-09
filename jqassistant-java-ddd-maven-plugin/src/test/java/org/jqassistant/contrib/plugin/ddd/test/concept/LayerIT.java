package org.jqassistant.contrib.plugin.ddd.test.concept;

import com.buschmais.jqassistant.core.report.api.model.Result;
import com.buschmais.jqassistant.core.rule.api.model.RuleException;
import com.buschmais.jqassistant.plugin.java.api.model.TypeDescriptor;
import com.buschmais.jqassistant.plugin.java.api.scanner.JavaScope;
import com.buschmais.jqassistant.plugin.java.test.AbstractJavaPluginIT;
import org.jqassistant.contrib.plugin.ddd.test.set.layer.LayerApp;
import org.jqassistant.contrib.plugin.ddd.test.set.layer.application.Application1;
import org.jqassistant.contrib.plugin.ddd.test.set.layer.domain.Domain1;
import org.jqassistant.contrib.plugin.ddd.test.set.layer.infrastructure.Infrastructure1;
import org.jqassistant.contrib.plugin.ddd.test.set.layer.interfaces.Interface1;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class LayerIT extends AbstractJavaPluginIT {

    @Test
    public void interfaceType() throws RuleException {
        scanClasses(Interface1.class);
        assertThat(applyConcept("java-ddd:LayerType").getStatus()).isEqualTo(Result.Status.SUCCESS);
        store.beginTransaction();
        List<TypeDescriptor> types = query("MATCH (:DDD:Layer{name: 'Interface'})-[:CONTAINS]->(t:Type:Java) RETURN t").getColumn("t");
        assertThat(types.size()).isEqualTo(1);
        assertThat(types.get(0).getName()).isEqualTo("Interface1");
        store.commitTransaction();
    }

    @Test
    public void applicationType() throws RuleException {
        scanClasses(Application1.class);
        assertThat(applyConcept("java-ddd:LayerType").getStatus()).isEqualTo(Result.Status.SUCCESS);
        store.beginTransaction();
        List<TypeDescriptor> types = query("MATCH (:DDD:Layer{name: 'Application'})-[:CONTAINS]->(t:Type:Java) RETURN t").getColumn("t");
        assertThat(types.size()).isEqualTo(1);
        assertThat(types.get(0).getName()).isEqualTo("Application1");
        store.commitTransaction();
    }

    @Test
    public void domainType() throws RuleException {
        scanClasses(Domain1.class);
        assertThat(applyConcept("java-ddd:LayerType").getStatus()).isEqualTo(Result.Status.SUCCESS);
        store.beginTransaction();
        List<TypeDescriptor> types = query("MATCH (:DDD:Layer{name: 'Domain'})-[:CONTAINS]->(t:Type:Java) RETURN t").getColumn("t");
        assertThat(types.size()).isEqualTo(1);
        assertThat(types.get(0).getName()).isEqualTo("Domain1");
        store.commitTransaction();
    }

    @Test
    public void infrastructureType() throws RuleException {
        scanClasses(Infrastructure1.class);
        assertThat(applyConcept("java-ddd:LayerType").getStatus()).isEqualTo(Result.Status.SUCCESS);
        store.beginTransaction();
        List<TypeDescriptor> types = query("MATCH (:DDD:Layer{name: 'Infrastructure'})-[:CONTAINS]->(t:Type:Java) RETURN t").getColumn("t");
        assertThat(types.size()).isEqualTo(1);
        assertThat(types.get(0).getName()).isEqualTo("Infrastructure1");
        store.commitTransaction();
    }

    @Test
    public void layerPackage() throws RuleException {
        scanClassesAndPackages(LayerApp.class);
        assertThat(applyConcept("java-ddd:LayerPackage").getStatus()).isEqualTo(Result.Status.SUCCESS);
        store.beginTransaction();
        verifyInterfaceLayerPackage();
        verifyApplicationLayerPackage();
        verifyDomainLayerPackage();
        verifyInfrastructureLayerPackage();
        store.commitTransaction();
    }

    @Test
    public void definedLayerDependencies() throws RuleException {
        scanClassesAndPackages(LayerApp.class);
        assertThat(applyConcept("java-ddd:DefinedLayerDependencies").getStatus()).isEqualTo(Result.Status.SUCCESS);
        store.beginTransaction();
        assertThat(query("MATCH (layer:DDD:Layer) RETURN layer").getColumn("layer").size()).isEqualTo(4);
        assertThat(query("MATCH (:DDD:Layer)-[d:DEFINES_DEPENDENCY]->(:DDD:Layer) RETURN d").getColumn("d").size()).isEqualTo(6);
        assertThat(query("MATCH (:DDD:Layer{name: 'Infrastructure'})-[d:DEFINES_DEPENDENCY]->(:DDD:Layer{name: 'Interface'}) RETURN d").getColumn("d").size()).isEqualTo(1);
        assertThat(query("MATCH (:DDD:Layer{name: 'Infrastructure'})-[d:DEFINES_DEPENDENCY]->(:DDD:Layer{name: 'Application'}) RETURN d").getColumn("d").size()).isEqualTo(1);
        assertThat(query("MATCH (:DDD:Layer{name: 'Infrastructure'})-[d:DEFINES_DEPENDENCY]->(:DDD:Layer{name: 'Domain'}) RETURN d").getColumn("d").size()).isEqualTo(1);
        assertThat(query("MATCH (:DDD:Layer{name: 'Interface'})-[d:DEFINES_DEPENDENCY]->(:DDD:Layer{name: 'Application'}) RETURN d").getColumn("d").size()).isEqualTo(1);
        assertThat(query("MATCH (:DDD:Layer{name: 'Interface'})-[d:DEFINES_DEPENDENCY]->(:DDD:Layer{name: 'Domain'}) RETURN d").getColumn("d").size()).isEqualTo(1);
        assertThat(query("MATCH (:DDD:Layer{name: 'Application'})-[d:DEFINES_DEPENDENCY]->(:DDD:Layer{name: 'Domain'}) RETURN d").getColumn("d").size()).isEqualTo(1);
        store.commitTransaction();
    }

    @Test
    public void layerDependency() throws RuleException {
        scanClassesAndPackages(LayerApp.class);
        assertThat(applyConcept("java-ddd:LayerDependency").getStatus()).isEqualTo(Result.Status.SUCCESS);
        store.beginTransaction();
        assertThat(query("MATCH (t1:Type)-[:DEPENDS_ON]->(t2:Type) " +
            "WHERE t1.fqn STARTS WITH 'org.jqassistant.contrib.plugin.ddd.test.set.layer' AND " +
            "t2.fqn STARTS WITH 'org.jqassistant.contrib.plugin.ddd.test.set.layer' " +
            "RETURN t1.name, t2.name").getRows().size()).isEqualTo(12);
        assertThat(query("MATCH (l:DDD:Layer)-[:CONTAINS]->(t:Type) WHERE t.name <> 'package-info' RETURN l.name, t.name").getRows().size()).isEqualTo(8);
        TestResult queryResult = query("MATCH (l1:DDD:Layer)-[:CONTAINS]->(t1:Type), " +
            "(l2:DDD:Layer)-[:CONTAINS]->(t2:Type), " +
            "(t1)-[d:DEPENDS_ON]->(t2) " +
            "RETURN l1.name AS Source, l2.name AS Target");
        // this should return 12 rows but returns 6
        assertThat(queryResult.getRows().size()).isEqualTo(12);
        assertThat(query("MATCH (:DDD:Layer)-[d:DEPENDS_ON]->(:DDD:Layer) RETURN d").getColumn("d").size()).isEqualTo(12);
        store.commitTransaction();
    }

    private void verifyInterfaceLayerPackage() {
        List<TypeDescriptor> types = query("MATCH (:DDD:Layer{name: 'Interface'})-[:CONTAINS]->(t:Type:Java) RETURN t ORDER BY t.fqn").getColumn("t");
        assertThat(types.size()).isEqualTo(3);
        assertThat(types.get(0).getName()).isEqualTo("Interface1");
        assertThat(types.get(1).getName()).isEqualTo("Interface2");
        assertThat(types.get(2).getName()).isEqualTo("package-info");
    }

    private void verifyApplicationLayerPackage() {
        List<TypeDescriptor> types = query("MATCH (:DDD:Layer{name: 'Application'})-[:CONTAINS]->(t:Type:Java) RETURN t ORDER BY t.fqn").getColumn("t");
        assertThat(types.size()).isEqualTo(3);
        assertThat(types.get(0).getName()).isEqualTo("Application1");
        assertThat(types.get(1).getName()).isEqualTo("Application2");
        assertThat(types.get(2).getName()).isEqualTo("package-info");
    }

    private void verifyDomainLayerPackage() {
        List<TypeDescriptor> types = query("MATCH (:DDD:Layer{name: 'Domain'})-[:CONTAINS]->(t:Type:Java) RETURN t ORDER BY t.fqn").getColumn("t");
        assertThat(types.size()).isEqualTo(3);
        assertThat(types.get(0).getName()).isEqualTo("Domain1");
        assertThat(types.get(1).getName()).isEqualTo("Domain2");
        assertThat(types.get(2).getName()).isEqualTo("package-info");
    }

    private void verifyInfrastructureLayerPackage() {
        List<TypeDescriptor> types = query("MATCH (:DDD:Layer{name: 'Infrastructure'})-[:CONTAINS]->(t:Type:Java) RETURN t ORDER BY t.fqn").getColumn("t");
        assertThat(types.size()).isEqualTo(3);
        assertThat(types.get(0).getName()).isEqualTo("Infrastructure1");
        assertThat(types.get(1).getName()).isEqualTo("Infrastructure2");
        assertThat(types.get(2).getName()).isEqualTo("package-info");
    }

    void scanClassesAndPackages(Class<?> clazz) {
        Map<String, Object> pluginProps = new HashMap<>();
        pluginProps.put("file.include", "/" + clazz.getPackage().getName().replace(".", "/") + "/**");
        getScanner(pluginProps).scan(getClassesDirectory(clazz), "/", JavaScope.CLASSPATH);
    }

}
