package org.jqassistant.contrib.plugin.ddd.test.concept;

import com.buschmais.jqassistant.core.analysis.api.Result;
import com.buschmais.jqassistant.core.analysis.api.rule.RuleException;
import com.buschmais.jqassistant.plugin.java.api.model.TypeDescriptor;
import com.buschmais.jqassistant.plugin.java.test.AbstractJavaPluginIT;
import org.jqassistant.contrib.plugin.ddd.test.set.service.Service1;
import org.jqassistant.contrib.plugin.ddd.test.set.service.Service2;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class ServiceIT extends AbstractJavaPluginIT {

    @Test
    public void serviceType() throws RuleException {
        scanClasses(Service1.class);
        assertEquals(Result.Status.SUCCESS, applyConcept("java-ddd:ServiceType").getStatus());
        store.beginTransaction();
        List<TypeDescriptor> types = query("MATCH (t:DDD:Service) RETURN t").getColumn("t");
        assertThat(types.size(), equalTo(1));
        assertThat(types.get(0).getName(), equalTo("Service1"));
        store.commitTransaction();
    }

    @Test
    public void servicePackage() throws RuleException {
        scanClassPathDirectory(getClassesDirectory(Service2.class));
        assertEquals(Result.Status.SUCCESS, applyConcept("java-ddd:ServicePackage").getStatus());
        store.beginTransaction();
        List<TypeDescriptor> types = query("MATCH (t:DDD:Service) RETURN t ORDER BY t.fqn").getColumn("t");
        assertThat(types.size(), equalTo(3));
        assertThat(types.get(0).getName(), equalTo("Service1"));
        assertThat(types.get(1).getName(), equalTo("Service2"));
        assertThat(types.get(2).getName(), equalTo("package-info"));
        store.commitTransaction();
    }

}
