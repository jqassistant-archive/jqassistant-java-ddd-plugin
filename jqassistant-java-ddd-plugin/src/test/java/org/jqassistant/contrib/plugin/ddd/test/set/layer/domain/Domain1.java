package org.jqassistant.contrib.plugin.ddd.test.set.layer.domain;

import org.jqassistant.contrib.plugin.ddd.annotation.DDD;
import org.jqassistant.contrib.plugin.ddd.test.set.layer.application.Application1;
import org.jqassistant.contrib.plugin.ddd.test.set.layer.infrastructure.Infrastructure1;
import org.jqassistant.contrib.plugin.ddd.test.set.layer.interfaces.Interface2;

@DDD.Layer.DomainLayer
public class Domain1 {

    private Application1 domToAppIllDep;
    private Interface2 domToIntIllDep;
    private Infrastructure1 domToInfIllDep;

}
