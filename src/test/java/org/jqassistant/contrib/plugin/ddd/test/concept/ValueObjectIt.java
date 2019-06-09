package org.jqassistant.contrib.plugin.ddd.test.concept;

import com.buschmais.jqassistant.core.analysis.api.Result;
import com.buschmais.jqassistant.core.analysis.api.rule.RuleException;
import com.buschmais.jqassistant.plugin.java.api.model.TypeDescriptor;
import com.buschmais.jqassistant.plugin.java.test.AbstractJavaPluginIT;
import org.jqassistant.contrib.plugin.ddd.test.set.valueobject.ValueObject1;
import org.jqassistant.contrib.plugin.ddd.test.set.valueobject.ValueObject2;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class ValueObjectIt extends AbstractJavaPluginIT {

    @Test
    public void valueObjectType() throws RuleException {
        scanClasses(ValueObject1.class);
        assertEquals(applyConcept("java-ddd:ValueObjectType").getStatus(), Result.Status.SUCCESS);
        store.beginTransaction();
        List<TypeDescriptor> types = query("MATCH (t:DDD:ValueObject) RETURN t").getColumn("t");
        assertThat(types.size(), equalTo(1));
        assertThat(types.get(0).getName(), equalTo("ValueObject1"));
        store.commitTransaction();
    }

    @Test
    public void valueOjectPackage() throws RuleException {
        scanClassPathDirectory(getClassesDirectory(ValueObject2.class));
        assertEquals(applyConcept("java-ddd:ValueObjectPackage").getStatus(), Result.Status.SUCCESS);
        store.beginTransaction();
        List<TypeDescriptor> types = query("MATCH (t:DDD:ValueObject) RETURN t ORDER BY t.fqn").getColumn("t");
        assertThat(types.size(), equalTo(3));
        assertThat(types.get(0).getName(), equalTo("ValueObject1"));
        assertThat(types.get(1).getName(), equalTo("ValueObject2"));
        assertThat(types.get(2).getName(), equalTo("package-info"));
        store.commitTransaction();
    }

}
