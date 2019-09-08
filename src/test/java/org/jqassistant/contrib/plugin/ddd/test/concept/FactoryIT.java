package org.jqassistant.contrib.plugin.ddd.test.concept;

import com.buschmais.jqassistant.core.analysis.api.Result;
import com.buschmais.jqassistant.core.analysis.api.rule.RuleException;
import com.buschmais.jqassistant.plugin.java.api.model.TypeDescriptor;
import com.buschmais.jqassistant.plugin.java.test.AbstractJavaPluginIT;
import org.jqassistant.contrib.plugin.ddd.test.set.factory.Factory1;
import org.jqassistant.contrib.plugin.ddd.test.set.factory.Factory2;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class FactoryIT extends AbstractJavaPluginIT {

    @Test
    public void factoryType() throws RuleException {
        scanClasses(Factory1.class);
        assertEquals(Result.Status.SUCCESS, applyConcept("java-ddd:FactoryType").getStatus());
        store.beginTransaction();
        List<TypeDescriptor> types = query("MATCH (t:DDD:Factory) RETURN t").getColumn("t");
        assertThat(types.size(), equalTo(1));
        assertThat(types.get(0).getName(), equalTo("Factory1"));
        store.commitTransaction();
    }

    @Test
    public void factoryPackage() throws RuleException {
        scanClassPathDirectory(getClassesDirectory(Factory2.class));
        assertEquals(Result.Status.SUCCESS, applyConcept("java-ddd:FactoryPackage").getStatus());
        store.beginTransaction();
        List<TypeDescriptor> types = query("MATCH (t:DDD:Factory) RETURN t ORDER BY t.fqn").getColumn("t");
        assertThat(types.size(), equalTo(3));
        assertThat(types.get(0).getName(), equalTo("Factory1"));
        assertThat(types.get(1).getName(), equalTo("Factory2"));
        assertThat(types.get(2).getName(), equalTo("package-info"));
        store.commitTransaction();
    }

}
