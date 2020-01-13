package org.jqassistant.contrib.plugin.ddd.test.set.layer.infrastructure;

import org.jqassistant.contrib.plugin.ddd.annotation.DDD;
import org.jqassistant.contrib.plugin.ddd.test.set.layer.application.Application2;
import org.jqassistant.contrib.plugin.ddd.test.set.layer.domain.Domain1;
import org.jqassistant.contrib.plugin.ddd.test.set.layer.interfaces.Interface1;

@DDD.Layer.InfrastructureLayer
public class Infrastructure1 {

    private Domain1 infToDomDep;
    private Application2 infToAppDep;
    private Interface1 infToIntDep;

}
