package org.jqassistant.contrib.plugin.ddd.descriptor;

import com.buschmais.xo.neo4j.api.annotation.Label;
import org.jqassistant.contrib.plugin.ddd.report.DDDLanguage;

import static org.jqassistant.contrib.plugin.ddd.report.DDDLanguage.DDDLanguageElement.Layer;

@DDDLanguage(Layer)
@Label("Layer")
public interface LayerDescriptor extends DDDDescriptor {

    String getName();

}
