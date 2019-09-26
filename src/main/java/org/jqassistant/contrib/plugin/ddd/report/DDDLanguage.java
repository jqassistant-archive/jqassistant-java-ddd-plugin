package org.jqassistant.contrib.plugin.ddd.report;

import com.buschmais.jqassistant.core.report.api.Language;
import com.buschmais.jqassistant.core.report.api.LanguageElement;
import com.buschmais.jqassistant.core.report.api.SourceProvider;
import org.jqassistant.contrib.plugin.ddd.descriptor.BoundedContextDescriptor;
import org.jqassistant.contrib.plugin.ddd.descriptor.LayerDescriptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Defines the language elements for "DDD"
 */
@Language
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface DDDLanguage {

    DDDLanguageElement value();

    enum DDDLanguageElement implements LanguageElement {
        BoundedContext {
            @Override
            public SourceProvider<BoundedContextDescriptor> getSourceProvider() {
                return new SourceProvider<BoundedContextDescriptor>() {
                    @Override
                    public String getName(BoundedContextDescriptor descriptor) {
                        return descriptor.getName();
                    }

                    @Override
                    public String getSourceFile(BoundedContextDescriptor descriptor) {
                        return null;
                    }

                    @Override
                    public Integer getLineNumber(BoundedContextDescriptor descriptor) {
                        return null;
                    }
                };
            }
        },

        Layer {
            @Override
            public SourceProvider<LayerDescriptor> getSourceProvider() {
                return new SourceProvider<LayerDescriptor>() {
                    @Override
                    public String getName(LayerDescriptor descriptor) {
                        return descriptor.getName();
                    }

                    @Override
                    public String getSourceFile(LayerDescriptor descriptor) {
                        return null;
                    }

                    @Override
                    public Integer getLineNumber(LayerDescriptor descriptor) {
                        return null;
                    }
                };
            }
        };

        @Override
        public String getLanguage() {
            return "DDD";
        }

    }
}
