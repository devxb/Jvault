package org.jvault.struct.choiceconstructorinject;

import org.jvault.annotation.BeanArea;
import org.jvault.annotation.BeanWire;

@BeanArea(name = "CHOICE_CONSTRUCTOR_VAULT")
public final class AnnotationConfig {

    @BeanWire private ChoiceConstructorBean choiceConstructorBean;
    @BeanWire private ChoiceConstructor choiceConstructor;

}
