package org.jqassistant.contrib.plugin.ddd.test.concept;

import com.buschmais.jqassistant.core.report.api.model.Result;
import com.buschmais.jqassistant.core.rule.api.model.RuleException;
import com.buschmais.jqassistant.plugin.java.api.model.TypeDescriptor;
import com.buschmais.jqassistant.plugin.java.test.AbstractJavaPluginIT;
import org.jqassistant.contrib.plugin.ddd.test.set.aggregate.Aggregate1;
import org.jqassistant.contrib.plugin.ddd.test.set.aggregate.Aggregate2;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AggregateRootIT extends AbstractJavaPluginIT {

    @Test
    public void aggregateType() throws RuleException {
        scanClasses(Aggregate1.class);
        assertEquals(Result.Status.SUCCESS, applyConcept("java-ddd:AggregateRootType").getStatus());
        store.beginTransaction();
        List<TypeDescriptor> types = query("MATCH (t:DDD:AggregateRoot) RETURN t").getColumn("t");
        assertThat(types.size()).isEqualTo(1);
        assertThat(types.get(0).getName()).isEqualTo("Aggregate1");
        store.commitTransaction();
    }

    @Test
    public void aggregatePackage() throws RuleException {
        scanClassPathDirectory(getClassesDirectory(Aggregate2.class));
        assertEquals(Result.Status.SUCCESS, applyConcept("java-ddd:AggregateRootPackage").getStatus());
        store.beginTransaction();
        List<TypeDescriptor> types = query("MATCH (t:DDD:AggregateRoot) RETURN t ORDER BY t.fqn").getColumn("t");
        assertThat(types.size()).isEqualTo(3);
        assertThat(types.get(0).getName()).isEqualTo("Aggregate1");
        assertThat(types.get(1).getName()).isEqualTo("Aggregate2");
        assertThat(types.get(2).getName()).isEqualTo("package-info");
        store.commitTransaction();
    }
}
