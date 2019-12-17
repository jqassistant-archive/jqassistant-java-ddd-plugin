package org.jqassistant.contrib.plugin.ddd.test.constraints;

import com.buschmais.jqassistant.core.report.api.model.Result;
import com.buschmais.jqassistant.core.rule.api.model.Constraint;
import com.buschmais.jqassistant.core.rule.api.model.RuleException;
import com.buschmais.jqassistant.plugin.java.test.AbstractJavaPluginIT;
import org.jqassistant.contrib.plugin.ddd.test.set.violation.bc1.BC1;
import org.jqassistant.contrib.plugin.ddd.test.set.violation.bc2.IllegalDependency;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class IllegalDependenciesBetweenBoundedContextsIT extends AbstractJavaPluginIT {

    @Test
    public void testIllegalDependenciesBetweenBoundedContexts() throws RuleException {
        scanClasses(IllegalDependency.class, BC1.class);
        assertThat(applyConcept("java-ddd:BoundedContextDependency").getStatus()).isEqualTo(Result.Status.SUCCESS);
        Result<Constraint> result = validateConstraint("java-ddd:IllegalDependenciesBetweenBoundedContexts");
        assertEquals(1, result.getRows().size());
        Map<String, Object> row = result.getRows().get(0);
        assertEquals("bc2", row.get("DependentBoundedContext"));
        assertEquals("bc1", row.get("Dependency"));
    }
}
