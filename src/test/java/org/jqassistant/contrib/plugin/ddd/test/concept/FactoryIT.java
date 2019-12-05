package org.jqassistant.contrib.plugin.ddd.test.concept;

import com.buschmais.jqassistant.core.report.api.model.Result;
import com.buschmais.jqassistant.core.rule.api.model.RuleException;
import com.buschmais.jqassistant.plugin.java.api.model.TypeDescriptor;
import com.buschmais.jqassistant.plugin.java.test.AbstractJavaPluginIT;
import org.jqassistant.contrib.plugin.ddd.test.set.factory.Factory1;
import org.jqassistant.contrib.plugin.ddd.test.set.factory.Factory2;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class FactoryIT extends AbstractJavaPluginIT {

    @Test
    public void factoryType() throws RuleException {
        scanClasses(Factory1.class);
        assertThat(applyConcept("java-ddd:FactoryType").getStatus()).isEqualTo(Result.Status.SUCCESS);
        store.beginTransaction();
        List<TypeDescriptor> types = query("MATCH (t:DDD:Factory) RETURN t").getColumn("t");
        assertThat(types.size()).isEqualTo(1);
        assertThat(types.get(0).getName()).isEqualTo("Factory1");
        store.commitTransaction();
    }

    @Test
    public void factoryPackage() throws RuleException {
        scanClassPathDirectory(getClassesDirectory(Factory2.class));
        assertThat(applyConcept("java-ddd:FactoryPackage").getStatus()).isEqualTo(Result.Status.SUCCESS);
        store.beginTransaction();
        List<TypeDescriptor> types = query("MATCH (t:DDD:Factory) RETURN t ORDER BY t.fqn").getColumn("t");
        assertThat(types.size()).isEqualTo(3);
        assertThat(types.get(0).getName()).isEqualTo("Factory1");
        assertThat(types.get(1).getName()).isEqualTo("Factory2");
        assertThat(types.get(2).getName()).isEqualTo("package-info");
        store.commitTransaction();
    }

}
