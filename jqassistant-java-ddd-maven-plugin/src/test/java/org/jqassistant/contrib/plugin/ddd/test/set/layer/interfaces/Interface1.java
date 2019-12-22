package org.jqassistant.contrib.plugin.ddd.test.set.layer.interfaces;

import org.jqassistant.contrib.plugin.ddd.annotation.DDD;
import org.jqassistant.contrib.plugin.ddd.test.set.layer.application.Application1;
import org.jqassistant.contrib.plugin.ddd.test.set.layer.domain.Domain2;
import org.jqassistant.contrib.plugin.ddd.test.set.layer.infrastructure.Infrastructure1;

@DDD.Layer.InterfaceLayer
public class Interface1 {

    private Application1 intToAppDep;
    private Domain2 intToDomDep;
    private Infrastructure1 infToInfIllDep;

}
