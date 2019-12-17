package org.jqassistant.contrib.plugin.ddd.test.concept;

import com.buschmais.jqassistant.core.report.api.model.Result;
import com.buschmais.jqassistant.core.rule.api.model.RuleException;
import com.buschmais.jqassistant.plugin.java.api.model.TypeDescriptor;
import com.buschmais.jqassistant.plugin.java.test.AbstractJavaPluginIT;
import org.jqassistant.contrib.plugin.ddd.test.set.entity.Entity1;
import org.jqassistant.contrib.plugin.ddd.test.set.entity.Entity2;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EntityIT extends AbstractJavaPluginIT {

    @Test
    public void entityType() throws RuleException {
        scanClasses(Entity1.class);
        assertEquals(Result.Status.SUCCESS, applyConcept("java-ddd:EntityType").getStatus());
        store.beginTransaction();
        List<TypeDescriptor> types = query("MATCH (t:DDD:Entity) RETURN t").getColumn("t");
        assertThat(types.size()).isEqualTo(1);
        assertThat(types.get(0).getName()).isEqualTo("Entity1");
        store.commitTransaction();
    }

    @Test
    public void entityPackage() throws RuleException {
        scanClassPathDirectory(getClassesDirectory(Entity2.class));
        assertEquals(Result.Status.SUCCESS, applyConcept("java-ddd:EntityPackage").getStatus());
        store.beginTransaction();
        List<TypeDescriptor> types = query("MATCH (t:DDD:Entity) RETURN t ORDER BY t.fqn").getColumn("t");
        assertThat(types.size()).isEqualTo(3);
        assertThat(types.get(0).getName()).isEqualTo("Entity1");
        assertThat(types.get(1).getName()).isEqualTo("Entity2");
        assertThat(types.get(2).getName()).isEqualTo("package-info");
        store.commitTransaction();
    }

}
