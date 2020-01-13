package org.jqassistant.contrib.plugin.ddd.test;

import com.buschmais.jqassistant.plugin.java.api.scanner.JavaScope;
import com.buschmais.jqassistant.plugin.java.test.AbstractJavaPluginIT;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractJavaDDDPluginIT extends AbstractJavaPluginIT {

    protected void scanClassesAndPackages(Class<?> clazz) {
        Map<String, Object> pluginProps = new HashMap<>();
        pluginProps.put("file.include", "/" + clazz.getPackage().getName().replace(".", "/") + "/**");
        getScanner(pluginProps).scan(getClassesDirectory(clazz), "/", JavaScope.CLASSPATH);
    }

}
