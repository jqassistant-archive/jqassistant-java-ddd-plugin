package org.jqassistant.contrib.plugin.ddd.test.constraints;

import com.buschmais.jqassistant.core.report.api.model.Result;
import com.buschmais.jqassistant.core.rule.api.model.Constraint;
import com.buschmais.jqassistant.core.rule.api.model.RuleException;
import org.jqassistant.contrib.plugin.ddd.test.AbstractJavaDDDPluginIT;
import org.jqassistant.contrib.plugin.ddd.test.set.layer.LayerApp;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class IllegalDependenciesBetweenTechnicalLayersIT extends AbstractJavaDDDPluginIT {

    @Test
    public void testIllegalDependenciesBetweenBoundedContexts() throws RuleException {
        scanClassesAndPackages(LayerApp.class);
        assertThat(applyConcept("java-ddd:LayerDependency").getStatus()).isEqualTo(Result.Status.SUCCESS);
        Result<Constraint> result = validateConstraint("java-ddd:IllegalDependenciesBetweenTechnicalLayers");
        assertEquals(6, result.getRows().size());
        List<Map<String, Object>> rows = result.getRows();
        assertEquals("Application", rows.get(0).get("DependentLayer"));
        assertEquals("Infrastructure", rows.get(0).get("Dependency"));

        assertEquals("Application", rows.get(1).get("DependentLayer"));
        assertEquals("Interface", rows.get(1).get("Dependency"));

        assertEquals("Domain", rows.get(2).get("DependentLayer"));
        assertEquals("Application", rows.get(2).get("Dependency"));

        assertEquals("Domain", rows.get(3).get("DependentLayer"));
        assertEquals("Infrastructure", rows.get(3).get("Dependency"));

        assertEquals("Domain", rows.get(4).get("DependentLayer"));
        assertEquals("Interface", rows.get(4).get("Dependency"));

        assertEquals("Interface", rows.get(5).get("DependentLayer"));
        assertEquals("Infrastructure", rows.get(5).get("Dependency"));
    }

}
