package org.jqassistant.contrib.plugin.ddd.test.set.violation.bc2;

import org.jqassistant.contrib.plugin.ddd.annotation.DDD;
import org.jqassistant.contrib.plugin.ddd.test.set.violation.bc1.BC1;

@DDD.BoundedContext(name = "bc2")
public class IllegalDependency {

    public void doSomething(BC1 bc1) {

    }

}
