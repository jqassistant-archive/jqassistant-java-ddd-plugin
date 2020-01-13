package org.jqassistant.contrib.plugin.ddd.descriptor;

import com.buschmais.xo.neo4j.api.annotation.Label;
import com.buschmais.xo.neo4j.api.annotation.Relation;
import org.jqassistant.contrib.plugin.ddd.report.DDDLanguage;

import java.util.List;

import static org.jqassistant.contrib.plugin.ddd.report.DDDLanguage.DDDLanguageElement.BoundedContext;

@DDDLanguage(BoundedContext)
@Label(value = "BoundedContext")
public interface BoundedContextDescriptor extends DDDDescriptor {

    String getName();

    @Relation("DEFINES_DEPENDENCY")
    List<BoundedContextDescriptor> getDefinedDependencies();

}
