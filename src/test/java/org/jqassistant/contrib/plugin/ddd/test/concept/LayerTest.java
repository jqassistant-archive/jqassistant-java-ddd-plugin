package org.jqassistant.contrib.plugin.ddd.test.concept;

import com.buschmais.jqassistant.core.analysis.api.Result;
import com.buschmais.jqassistant.core.analysis.api.rule.RuleException;
import com.buschmais.jqassistant.plugin.java.api.model.TypeDescriptor;
import com.buschmais.jqassistant.plugin.java.test.AbstractJavaPluginIT;
import org.jqassistant.contrib.plugin.ddd.test.set.layer.application.Application1;
import org.jqassistant.contrib.plugin.ddd.test.set.layer.application.Application2;
import org.jqassistant.contrib.plugin.ddd.test.set.layer.domain.Domain1;
import org.jqassistant.contrib.plugin.ddd.test.set.layer.domain.Domain2;
import org.jqassistant.contrib.plugin.ddd.test.set.layer.infrastructure.Infrastructure1;
import org.jqassistant.contrib.plugin.ddd.test.set.layer.infrastructure.Infrastructure2;
import org.jqassistant.contrib.plugin.ddd.test.set.layer.interfaces.Interface1;
import org.jqassistant.contrib.plugin.ddd.test.set.layer.interfaces.Interface2;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class LayerTest extends AbstractJavaPluginIT {

    @Test
    public void applicationType() throws RuleException {
        scanClasses(Application1.class);
        assertEquals(Result.Status.SUCCESS, applyConcept("java-ddd:LayerType").getStatus());
        store.beginTransaction();
        List<TypeDescriptor> types = query("MATCH (:DDD:Layer{name: 'Application'})-[:CONTAINS]->(t:Type:Java) RETURN t").getColumn("t");
        assertThat(types.size(), equalTo(1));
        assertThat(types.get(0).getName(), equalTo("Application1"));
        store.commitTransaction();
    }

    @Test
    public void applicationPackage() throws RuleException {
        scanClassPathDirectory(getClassesDirectory(Application2.class));
        assertEquals(applyConcept("java-ddd:LayerPackage").getStatus(), Result.Status.SUCCESS);
        store.beginTransaction();
        List<TypeDescriptor> types = query("MATCH (:DDD:Layer{name: 'Application'})-[:CONTAINS]->(t:Type:Java) RETURN t ORDER BY t.fqn").getColumn("t");
        assertThat(types.size(), equalTo(3));
        assertThat(types.get(0).getName(), equalTo("Application1"));
        assertThat(types.get(1).getName(), equalTo("Application2"));
        assertThat(types.get(2).getName(), equalTo("package-info"));
        store.commitTransaction();
    }

    @Test
    public void domainType() throws RuleException {
        scanClasses(Domain1.class);
        assertEquals(applyConcept("java-ddd:LayerType").getStatus(), Result.Status.SUCCESS);
        store.beginTransaction();
        List<TypeDescriptor> types = query("MATCH (:DDD:Layer{name: 'Domain'})-[:CONTAINS]->(t:Type:Java) RETURN t").getColumn("t");
        assertThat(types.size(), equalTo(1));
        assertThat(types.get(0).getName(), equalTo("Domain1"));
        store.commitTransaction();
    }

    @Test
    public void domainPackage() throws RuleException {
        scanClassPathDirectory(getClassesDirectory(Domain2.class));
        assertEquals(applyConcept("java-ddd:LayerPackage").getStatus(), Result.Status.SUCCESS);
        store.beginTransaction();
        List<TypeDescriptor> types = query("MATCH (:DDD:Layer{name: 'Domain'})-[:CONTAINS]->(t:Type:Java) RETURN t ORDER BY t.fqn").getColumn("t");
        assertThat(types.size(), equalTo(3));
        assertThat(types.get(0).getName(), equalTo("Domain1"));
        assertThat(types.get(1).getName(), equalTo("Domain2"));
        assertThat(types.get(2).getName(), equalTo("package-info"));
        store.commitTransaction();
    }

    @Test
    public void infrastructureType() throws RuleException {
        scanClasses(Infrastructure1.class);
        assertEquals(applyConcept("java-ddd:LayerType").getStatus(), Result.Status.SUCCESS);
        store.beginTransaction();
        List<TypeDescriptor> types = query("MATCH (:DDD:Layer{name: 'Infrastructure'})-[:CONTAINS]->(t:Type:Java) RETURN t").getColumn("t");
        assertThat(types.size(), equalTo(1));
        assertThat(types.get(0).getName(), equalTo("Infrastructure1"));
        store.commitTransaction();
    }

    @Test
    public void infrastructurePackage() throws RuleException {
        scanClassPathDirectory(getClassesDirectory(Infrastructure2.class));
        assertEquals(applyConcept("java-ddd:LayerPackage").getStatus(), Result.Status.SUCCESS);
        store.beginTransaction();
        List<TypeDescriptor> types = query("MATCH (:DDD:Layer{name: 'Infrastructure'})-[:CONTAINS]->(t:Type:Java) RETURN t ORDER BY t.fqn").getColumn("t");
        assertThat(types.size(), equalTo(3));
        assertThat(types.get(0).getName(), equalTo("Infrastructure1"));
        assertThat(types.get(1).getName(), equalTo("Infrastructure2"));
        assertThat(types.get(2).getName(), equalTo("package-info"));
        store.commitTransaction();
    }

    @Test
    public void interfaceType() throws RuleException {
        scanClasses(Interface1.class);
        assertEquals(applyConcept("java-ddd:LayerType").getStatus(), Result.Status.SUCCESS);
        store.beginTransaction();
        List<TypeDescriptor> types = query("MATCH (:DDD:Layer{name: 'Interface'})-[:CONTAINS]->(t:Type:Java) RETURN t").getColumn("t");
        assertThat(types.size(), equalTo(1));
        assertThat(types.get(0).getName(), equalTo("Interface1"));
        store.commitTransaction();
    }

    @Test
    public void interfacePackage() throws RuleException {
        scanClassPathDirectory(getClassesDirectory(Interface2.class));
        assertEquals(applyConcept("java-ddd:LayerPackage").getStatus(), Result.Status.SUCCESS);
        store.beginTransaction();
        List<TypeDescriptor> types = query("MATCH (:DDD:Layer{name: 'Interface'})-[:CONTAINS]->(t:Type:Java) RETURN t ORDER BY t.fqn").getColumn("t");
        assertThat(types.size(), equalTo(3));
        assertThat(types.get(0).getName(), equalTo("Interface1"));
        assertThat(types.get(1).getName(), equalTo("Interface2"));
        assertThat(types.get(2).getName(), equalTo("package-info"));
        store.commitTransaction();
    }

}
