package org.jqassistant.contrib.plugin.ddd.test.concept;

import com.buschmais.jqassistant.core.report.api.model.Result;
import com.buschmais.jqassistant.core.rule.api.model.RuleException;
import com.buschmais.jqassistant.plugin.java.api.model.TypeDescriptor;
import com.buschmais.jqassistant.plugin.java.test.AbstractJavaPluginIT;
import org.jqassistant.contrib.plugin.ddd.test.set.repository.Repository1;
import org.jqassistant.contrib.plugin.ddd.test.set.repository.Repository2;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class RepositoryIT extends AbstractJavaPluginIT {

    @Test
    public void repositoryType() throws RuleException {
        scanClasses(Repository1.class);
        assertThat(applyConcept("java-ddd:RepositoryType").getStatus()).isEqualTo(Result.Status.SUCCESS);
        store.beginTransaction();
        List<TypeDescriptor> types = query("MATCH (t:DDD:Repository) RETURN t").getColumn("t");
        assertThat(types.size()).isEqualTo(1);
        assertThat(types.get(0).getName()).isEqualTo("Repository1");
        store.commitTransaction();
    }

    @Test
    public void repositoryPackage() throws RuleException {
        scanClassPathDirectory(getClassesDirectory(Repository2.class));
        assertThat(applyConcept("java-ddd:RepositoryPackage").getStatus()).isEqualTo(Result.Status.SUCCESS);
        store.beginTransaction();
        List<TypeDescriptor> types = query("MATCH (t:DDD:Repository) RETURN t ORDER BY t.fqn").getColumn("t");
        assertThat(types.size()).isEqualTo(3);
        assertThat(types.get(0).getName()).isEqualTo("Repository1");
        assertThat(types.get(1).getName()).isEqualTo("Repository2");
        assertThat(types.get(2).getName()).isEqualTo("package-info");
        store.commitTransaction();
    }

}
