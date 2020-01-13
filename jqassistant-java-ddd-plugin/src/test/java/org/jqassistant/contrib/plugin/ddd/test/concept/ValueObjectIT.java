package org.jqassistant.contrib.plugin.ddd.test.concept;

import com.buschmais.jqassistant.core.report.api.model.Result;
import com.buschmais.jqassistant.core.rule.api.model.RuleException;
import com.buschmais.jqassistant.plugin.java.api.model.TypeDescriptor;
import com.buschmais.jqassistant.plugin.java.test.AbstractJavaPluginIT;
import org.jqassistant.contrib.plugin.ddd.test.set.valueobject.ValueObject1;
import org.jqassistant.contrib.plugin.ddd.test.set.valueobject.ValueObject2;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ValueObjectIT extends AbstractJavaPluginIT {

    @Test
    public void valueObjectType() throws RuleException {
        scanClasses(ValueObject1.class);
        assertThat(applyConcept("java-ddd:ValueObjectType").getStatus()).isEqualTo(Result.Status.SUCCESS);
        store.beginTransaction();
        List<TypeDescriptor> types = query("MATCH (t:DDD:ValueObject) RETURN t").getColumn("t");
        assertThat(types.size()).isEqualTo(1);
        assertThat(types.get(0).getName()).isEqualTo("ValueObject1");
        store.commitTransaction();
    }

    @Test
    public void valueOjectPackage() throws RuleException {
        scanClassPathDirectory(getClassesDirectory(ValueObject2.class));
        assertThat(applyConcept("java-ddd:ValueObjectPackage").getStatus()).isEqualTo(Result.Status.SUCCESS);
        store.beginTransaction();
        List<TypeDescriptor> types = query("MATCH (t:DDD:ValueObject) RETURN t ORDER BY t.fqn").getColumn("t");
        assertThat(types.size()).isEqualTo(3);
        assertThat(types.get(0).getName()).isEqualTo("ValueObject1");
        assertThat(types.get(1).getName()).isEqualTo("ValueObject2");
        assertThat(types.get(2).getName()).isEqualTo("package-info");
        store.commitTransaction();
    }

}
