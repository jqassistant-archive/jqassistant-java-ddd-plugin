package org.jqassistant.contrib.plugin.ddd.test.concept;

import com.buschmais.jqassistant.core.report.api.model.Result;
import com.buschmais.jqassistant.core.rule.api.model.RuleException;
import com.buschmais.jqassistant.plugin.java.api.model.TypeDescriptor;
import com.buschmais.jqassistant.plugin.java.test.AbstractJavaPluginIT;
import org.jqassistant.contrib.plugin.ddd.test.set.domainevent.DomainEvent1;
import org.jqassistant.contrib.plugin.ddd.test.set.domainevent.DomainEvent2;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DomainEventIT extends AbstractJavaPluginIT {

    @Test
    public void domainEventType() throws RuleException {
        scanClasses(DomainEvent1.class);
        assertEquals(Result.Status.SUCCESS, applyConcept("java-ddd:DomainEventType").getStatus());
        store.beginTransaction();
        List<TypeDescriptor> types = query("MATCH (t:DDD:DomainEvent) RETURN t").getColumn("t");
        assertThat(types.size()).isEqualTo(1);
        assertThat(types.get(0).getName()).isEqualTo("DomainEvent1");
        store.commitTransaction();
    }

    @Test
    public void domainEventPackage() throws RuleException {
        scanClassPathDirectory(getClassesDirectory(DomainEvent2.class));
        assertEquals(Result.Status.SUCCESS, applyConcept("java-ddd:DomainEventPackage").getStatus());
        store.beginTransaction();
        List<TypeDescriptor> types = query("MATCH (t:DDD:DomainEvent) RETURN t ORDER BY t.fqn").getColumn("t");
        assertThat(types.size()).isEqualTo(3);
        assertThat(types.get(0).getName()).isEqualTo("DomainEvent1");
        assertThat(types.get(1).getName()).isEqualTo("DomainEvent2");
        assertThat(types.get(2).getName()).isEqualTo("package-info");
        store.commitTransaction();
    }

}
