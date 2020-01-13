package org.jqassistant.contrib.plugin.ddd.test.constraints;

import com.buschmais.jqassistant.core.report.api.model.Result;
import com.buschmais.jqassistant.core.rule.api.model.Constraint;
import com.buschmais.jqassistant.core.rule.api.model.RuleException;
import com.buschmais.jqassistant.plugin.java.test.AbstractJavaPluginIT;
import org.jqassistant.contrib.plugin.ddd.test.set.violation.bc1.MultipleBoundedContext;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TypeInMultipleBoundedContextsIT extends AbstractJavaPluginIT {

    @Test
    public void testTypeInMultipleBoundedContexts() throws RuleException {
        scanClassPathDirectory(getClassesDirectory(MultipleBoundedContext.class));
        assertThat(applyConcept("java-ddd:BoundedContextType").getStatus()).isEqualTo(Result.Status.SUCCESS);
        assertThat(applyConcept("java-ddd:BoundedContextPackage").getStatus()).isEqualTo(Result.Status.SUCCESS);
        Result<Constraint> result = validateConstraint("java-ddd:TypeInMultipleBoundedContexts");
        assertEquals(1, result.getRows().size());
        Map<String, Object> row = result.getRows().get(0);
        assertEquals(row.get("Type"), "org.jqassistant.contrib.plugin.ddd.test.set.violation.bc1.MultipleBoundedContext");
    }
}
