package org.jqassistant.contrib.plugin.ddd.test.concept;

import com.buschmais.jqassistant.core.report.api.model.Result;
import com.buschmais.jqassistant.core.rule.api.model.RuleException;
import com.buschmais.jqassistant.plugin.java.api.model.TypeDescriptor;
import com.buschmais.jqassistant.plugin.java.test.AbstractJavaPluginIT;
import org.jqassistant.contrib.plugin.ddd.test.set.service.Service1;
import org.jqassistant.contrib.plugin.ddd.test.set.service.Service2;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ServiceIT extends AbstractJavaPluginIT {

    @Test
    public void serviceType() throws RuleException {
        scanClasses(Service1.class);
        assertThat(applyConcept("java-ddd:ServiceType").getStatus()).isEqualTo(Result.Status.SUCCESS);
        store.beginTransaction();
        List<TypeDescriptor> types = query("MATCH (t:DDD:Service) RETURN t").getColumn("t");
        assertThat(types.size()).isEqualTo(1);
        assertThat(types.get(0).getName()).isEqualTo("Service1");
        store.commitTransaction();
    }

    @Test
    public void servicePackage() throws RuleException {
        scanClassPathDirectory(getClassesDirectory(Service2.class));
        assertThat(applyConcept("java-ddd:ServicePackage").getStatus()).isEqualTo(Result.Status.SUCCESS);
        store.beginTransaction();
        List<TypeDescriptor> types = query("MATCH (t:DDD:Service) RETURN t ORDER BY t.fqn").getColumn("t");
        assertThat(types.size()).isEqualTo(3);
        assertThat(types.get(0).getName()).isEqualTo("Service1");
        assertThat(types.get(1).getName()).isEqualTo("Service2");
        assertThat(types.get(2).getName()).isEqualTo("package-info");
        store.commitTransaction();
    }

}
