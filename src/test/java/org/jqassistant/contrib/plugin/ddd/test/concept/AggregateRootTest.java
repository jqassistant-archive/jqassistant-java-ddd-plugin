package org.jqassistant.contrib.plugin.ddd.test.concept;

import com.buschmais.jqassistant.core.analysis.api.Result;
import com.buschmais.jqassistant.core.analysis.api.rule.RuleException;
import com.buschmais.jqassistant.plugin.java.api.model.TypeDescriptor;
import com.buschmais.jqassistant.plugin.java.test.AbstractJavaPluginIT;
import org.jqassistant.contrib.plugin.ddd.test.set.aggregate.Aggregate1;
import org.jqassistant.contrib.plugin.ddd.test.set.aggregate.Aggregate2;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AggregateRootTest extends AbstractJavaPluginIT {

    @Test
    public void aggregateType() throws RuleException {
        scanClasses(Aggregate1.class);
        assertEquals(applyConcept("java-ddd:AggregateRootType").getStatus(), Result.Status.SUCCESS);
        store.beginTransaction();
        List<TypeDescriptor> types = query("MATCH (t:DDD:AggregateRoot) RETURN t").getColumn("t");
        assertThat(types.size(), equalTo(1));
        assertThat(types.get(0).getName(), equalTo("Aggregate1"));
        store.commitTransaction();
    }

    @Test
    public void aggregatePackage() throws RuleException {
        scanClassPathDirectory(getClassesDirectory(Aggregate2.class));
        assertEquals(applyConcept("java-ddd:AggregateRootPackage").getStatus(), Result.Status.SUCCESS);
        store.beginTransaction();
        List<TypeDescriptor> types = query("MATCH (t:DDD:AggregateRoot) RETURN t ORDER BY t.fqn").getColumn("t");
        assertThat(types.size(), equalTo(3));
        assertThat(types.get(0).getName(), equalTo("Aggregate1"));
        assertThat(types.get(1).getName(), equalTo("Aggregate2"));
        assertThat(types.get(2).getName(), equalTo("package-info"));
        store.commitTransaction();
    }
}
