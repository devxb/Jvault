package org.jvault.struct.choiceconstructorinject;

import org.jvault.annotation.BeanArea;
import org.jvault.annotation.BeanWire;

@BeanArea()
public final class AnnotationConfig {

    @BeanWire private ChoiceConstructorBean choiceConstructorBean;
    @BeanWire private ChoiceConstructor choiceConstructor;

}
