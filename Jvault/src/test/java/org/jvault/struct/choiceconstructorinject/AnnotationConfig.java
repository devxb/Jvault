package org.jvault.struct.choiceconstructorinject;

import org.jvault.annotation.BeanWire;
import org.jvault.annotation.VaultConfiguration;

@VaultConfiguration(name = "CHOICE_CONSTRUCTOR_VAULT")
public final class AnnotationConfig {

    @BeanWire private ChoiceConstructorBean choiceConstructorBean;
    @BeanWire private ChoiceConstructor choiceConstructor;

}
