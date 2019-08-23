package org.jqassistant.contrib.plugin.ddd.test.concept;

import com.buschmais.jqassistant.core.analysis.api.Result;
import com.buschmais.jqassistant.core.analysis.api.rule.RuleException;
import com.buschmais.jqassistant.plugin.java.api.model.TypeDescriptor;
import com.buschmais.jqassistant.plugin.java.test.AbstractJavaPluginIT;
import org.jqassistant.contrib.plugin.ddd.test.set.repository.Repository1;
import org.jqassistant.contrib.plugin.ddd.test.set.repository.Repository2;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class RepositoryTest extends AbstractJavaPluginIT {

    @Test
    public void repositoryType() throws RuleException {
        scanClasses(Repository1.class);
        assertEquals(Result.Status.SUCCESS, applyConcept("java-ddd:RepositoryType").getStatus());
        store.beginTransaction();
        List<TypeDescriptor> types = query("MATCH (t:DDD:Repository) RETURN t").getColumn("t");
        assertThat(types.size(), equalTo(1));
        assertThat(types.get(0).getName(), equalTo("Repository1"));
        store.commitTransaction();
    }

    @Test
    public void repositoryPackage() throws RuleException {
        scanClassPathDirectory(getClassesDirectory(Repository2.class));
        assertEquals(Result.Status.SUCCESS, applyConcept("java-ddd:RepositoryPackage").getStatus());
        store.beginTransaction();
        List<TypeDescriptor> types = query("MATCH (t:DDD:Repository) RETURN t ORDER BY t.fqn").getColumn("t");
        assertThat(types.size(), equalTo(3));
        assertThat(types.get(0).getName(), equalTo("Repository1"));
        assertThat(types.get(1).getName(), equalTo("Repository2"));
        assertThat(types.get(2).getName(), equalTo("package-info"));
        store.commitTransaction();
    }

}
