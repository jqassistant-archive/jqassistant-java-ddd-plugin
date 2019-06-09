package org.jqassistant.contrib.plugin.ddd.test.concept;

import com.buschmais.jqassistant.core.analysis.api.Result;
import com.buschmais.jqassistant.core.analysis.api.rule.RuleException;
import com.buschmais.jqassistant.plugin.java.api.model.TypeDescriptor;
import com.buschmais.jqassistant.plugin.java.test.AbstractJavaPluginIT;
import org.jqassistant.contrib.plugin.ddd.test.set.domainevent.DomainEvent1;
import org.jqassistant.contrib.plugin.ddd.test.set.domainevent.DomainEvent2;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DomainEventIt extends AbstractJavaPluginIT {

    @Test
    public void domainEventType() throws RuleException {
        scanClasses(DomainEvent1.class);
        assertEquals(applyConcept("java-ddd:DomainEventType").getStatus(), Result.Status.SUCCESS);
        store.beginTransaction();
        List<TypeDescriptor> types = query("MATCH (t:DDD:DomainEvent) RETURN t").getColumn("t");
        assertThat(types.size(), equalTo(1));
        assertThat(types.get(0).getName(), equalTo("DomainEvent1"));
        store.commitTransaction();
    }

    @Test
    public void domainEventPackage() throws RuleException {
        scanClassPathDirectory(getClassesDirectory(DomainEvent2.class));
        assertEquals(applyConcept("java-ddd:DomainEventPackage").getStatus(), Result.Status.SUCCESS);
        store.beginTransaction();
        List<TypeDescriptor> types = query("MATCH (t:DDD:DomainEvent) RETURN t ORDER BY t.fqn").getColumn("t");
        assertThat(types.size(), equalTo(3));
        assertThat(types.get(0).getName(), equalTo("DomainEvent1"));
        assertThat(types.get(1).getName(), equalTo("DomainEvent2"));
        assertThat(types.get(2).getName(), equalTo("package-info"));
        store.commitTransaction();
    }

}
