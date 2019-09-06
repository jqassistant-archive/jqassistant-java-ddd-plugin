package org.jqassistant.contrib.plugin.ddd.test.concept;

import com.buschmais.jqassistant.core.analysis.api.Result;
import com.buschmais.jqassistant.core.analysis.api.rule.RuleException;
import com.buschmais.jqassistant.plugin.java.api.model.TypeDescriptor;
import com.buschmais.jqassistant.plugin.java.test.AbstractJavaPluginIT;
import org.apache.commons.lang3.ClassUtils;
import org.jqassistant.contrib.plugin.ddd.test.set.layer.LayerApp;
import org.jqassistant.contrib.plugin.ddd.test.set.layer.application.Application1;
import org.jqassistant.contrib.plugin.ddd.test.set.layer.domain.Domain1;
import org.jqassistant.contrib.plugin.ddd.test.set.layer.infrastructure.Infrastructure1;
import org.jqassistant.contrib.plugin.ddd.test.set.layer.interfaces.Interface1;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class LayerIT extends AbstractJavaPluginIT {

    @Test
    public void interfaceType() throws RuleException, IOException {
        scanClasses(Interface1.class);
        assertEquals(Result.Status.SUCCESS, applyConcept("java-ddd:LayerType").getStatus());
        store.beginTransaction();
        List<TypeDescriptor> types = query("MATCH (:DDD:Layer{name: 'Interface'})-[:CONTAINS]->(t:Type:Java) RETURN t").getColumn("t");
        assertThat(types.size(), equalTo(1));
        assertThat(types.get(0).getName(), equalTo("Interface1"));
        store.commitTransaction();
    }

    @Test
    public void applicationType() throws RuleException, IOException {
        scanClasses(Application1.class);
        assertEquals(Result.Status.SUCCESS, applyConcept("java-ddd:LayerType").getStatus());
        store.beginTransaction();
        List<TypeDescriptor> types = query("MATCH (:DDD:Layer{name: 'Application'})-[:CONTAINS]->(t:Type:Java) RETURN t").getColumn("t");
        assertThat(types.size(), equalTo(1));
        assertThat(types.get(0).getName(), equalTo("Application1"));
        store.commitTransaction();
    }

    @Test
    public void domainType() throws RuleException, IOException {
        scanClasses(Domain1.class);
        assertEquals(Result.Status.SUCCESS, applyConcept("java-ddd:LayerType").getStatus());
        store.beginTransaction();
        List<TypeDescriptor> types = query("MATCH (:DDD:Layer{name: 'Domain'})-[:CONTAINS]->(t:Type:Java) RETURN t").getColumn("t");
        assertThat(types.size(), equalTo(1));
        assertThat(types.get(0).getName(), equalTo("Domain1"));
        store.commitTransaction();
    }

    @Test
    public void infrastructureType() throws RuleException, IOException {
        scanClasses(Infrastructure1.class);
        assertEquals(Result.Status.SUCCESS, applyConcept("java-ddd:LayerType").getStatus());
        store.beginTransaction();
        List<TypeDescriptor> types = query("MATCH (:DDD:Layer{name: 'Infrastructure'})-[:CONTAINS]->(t:Type:Java) RETURN t").getColumn("t");
        assertThat(types.size(), equalTo(1));
        assertThat(types.get(0).getName(), equalTo("Infrastructure1"));
        store.commitTransaction();
    }

    @Test
    public void layerPackage() throws RuleException, IOException {
        scanClassesAndPackages(LayerApp.class);
        assertEquals(Result.Status.SUCCESS, applyConcept("java-ddd:LayerPackage").getStatus());
        store.beginTransaction();
        verifyInterfaceLayerPackage();
        verifyApplicationLayerPackage();
        verifyDomainLayerPackage();
        verifyInfrastructureLayerPackage();
        store.commitTransaction();
    }

    private void verifyInterfaceLayerPackage() {
        List<TypeDescriptor> types = query("MATCH (:DDD:Layer{name: 'Interface'})-[:CONTAINS]->(t:Type:Java) RETURN t ORDER BY t.fqn").getColumn("t");
        assertThat(types.size(), equalTo(3));
        assertThat(types.get(0).getName(), equalTo("Interface1"));
        assertThat(types.get(1).getName(), equalTo("Interface2"));
        assertThat(types.get(2).getName(), equalTo("package-info"));
    }

    private void verifyApplicationLayerPackage() {
        List<TypeDescriptor> types = query("MATCH (:DDD:Layer{name: 'Application'})-[:CONTAINS]->(t:Type:Java) RETURN t ORDER BY t.fqn").getColumn("t");
        assertThat(types.size(), equalTo(3));
        assertThat(types.get(0).getName(), equalTo("Application1"));
        assertThat(types.get(1).getName(), equalTo("Application2"));
        assertThat(types.get(2).getName(), equalTo("package-info"));
    }

    private void verifyDomainLayerPackage() {
        List<TypeDescriptor> types = query("MATCH (:DDD:Layer{name: 'Domain'})-[:CONTAINS]->(t:Type:Java) RETURN t ORDER BY t.fqn").getColumn("t");
        assertThat(types.size(), equalTo(3));
        assertThat(types.get(0).getName(), equalTo("Domain1"));
        assertThat(types.get(1).getName(), equalTo("Domain2"));
        assertThat(types.get(2).getName(), equalTo("package-info"));
    }

    private void verifyInfrastructureLayerPackage() {
        List<TypeDescriptor> types = query("MATCH (:DDD:Layer{name: 'Infrastructure'})-[:CONTAINS]->(t:Type:Java) RETURN t ORDER BY t.fqn").getColumn("t");
        assertThat(types.size(), equalTo(3));
        assertThat(types.get(0).getName(), equalTo("Infrastructure1"));
        assertThat(types.get(1).getName(), equalTo("Infrastructure2"));
        assertThat(types.get(2).getName(), equalTo("package-info"));
    }

    void scanClassesAndPackages(Class<?> clazz) throws IOException {
        String pathOfClass = ClassUtils.getPackageCanonicalName(clazz).replaceAll("\\.", Matcher.quoteReplacement(File.separator));
        pathOfClass = getClassesDirectory(clazz).getAbsolutePath() + File.separator + pathOfClass;
        scanClassPathDirectory(new File(pathOfClass));
    }

}
