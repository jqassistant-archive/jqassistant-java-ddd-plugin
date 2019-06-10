package org.jqassistant.contrib.plugin.ddd.test.constraints;

import com.buschmais.jqassistant.core.analysis.api.Result;
import com.buschmais.jqassistant.core.analysis.api.rule.Constraint;
import com.buschmais.jqassistant.core.analysis.api.rule.RuleException;
import com.buschmais.jqassistant.plugin.java.test.AbstractJavaPluginIT;
import org.jqassistant.contrib.plugin.ddd.test.set.violation.bc1.BC1;
import org.jqassistant.contrib.plugin.ddd.test.set.violation.bc2.IllegalDependency;
import org.jqassistant.contrib.plugin.ddd.test.set.violation.bc2.UnneededDependency;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UnneededDependenciesBetweenBoundedContextsTest extends AbstractJavaPluginIT {

    @Test
    public void testUnneededDependenciesBetweenBoundedContexts() throws RuleException {
        scanClasses(UnneededDependency.class, BC1.class);
        assertEquals(applyConcept("java-ddd:DefinedBoundedContextDependencies").getStatus(), Result.Status.SUCCESS);
        Result<Constraint> result = validateConstraint("java-ddd:UnneededDependenciesBetweenBoundedContexts");
        assertEquals(1, result.getRows().size());
        Map<String, Object> row = result.getRows().get(0);
        assertEquals("bc2", row.get("DependentBoundedContext"));
        assertEquals("bc1", row.get("Dependency"));
    }
}
