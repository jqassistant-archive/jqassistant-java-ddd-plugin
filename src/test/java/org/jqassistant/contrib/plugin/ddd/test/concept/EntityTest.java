package org.jqassistant.contrib.plugin.ddd.test.concept;

import com.buschmais.jqassistant.core.analysis.api.Result;
import com.buschmais.jqassistant.core.analysis.api.rule.RuleException;
import com.buschmais.jqassistant.plugin.java.api.model.TypeDescriptor;
import com.buschmais.jqassistant.plugin.java.test.AbstractJavaPluginIT;
import org.jqassistant.contrib.plugin.ddd.test.set.entity.Entity1;
import org.jqassistant.contrib.plugin.ddd.test.set.entity.Entity2;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EntityTest extends AbstractJavaPluginIT {

    @Test
    public void entityType() throws RuleException {
        scanClasses(Entity1.class);
        assertEquals(Result.Status.SUCCESS, applyConcept("java-ddd:EntityType").getStatus());
        store.beginTransaction();
        List<TypeDescriptor> types = query("MATCH (t:DDD:Entity) RETURN t").getColumn("t");
        assertThat(types.size(), equalTo(1));
        assertThat(types.get(0).getName(), equalTo("Entity1"));
        store.commitTransaction();
    }

    @Test
    public void entityPackage() throws RuleException {
        scanClassPathDirectory(getClassesDirectory(Entity2.class));
        assertEquals(Result.Status.SUCCESS, applyConcept("java-ddd:EntityPackage").getStatus());
        store.beginTransaction();
        List<TypeDescriptor> types = query("MATCH (t:DDD:Entity) RETURN t ORDER BY t.fqn").getColumn("t");
        assertThat(types.size(), equalTo(3));
        assertThat(types.get(0).getName(), equalTo("Entity1"));
        assertThat(types.get(1).getName(), equalTo("Entity2"));
        assertThat(types.get(2).getName(), equalTo("package-info"));
        store.commitTransaction();
    }

}
