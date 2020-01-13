package org.jqassistant.contrib.plugin.ddd.test.set.violation.bc2;

import org.jqassistant.contrib.plugin.ddd.annotation.DDD;

@DDD.BoundedContext(name = "bc2", dependsOn = {"bc1"})
public class UnneededDependency {
}
