package org.jvault.struct.choiceconstructorinject;

import org.jvault.annotation.Inject;
import org.jvault.annotation.InternalBean;
import org.jvault.bean.Type;

@InternalBean(type = Type.NEW)
public final class ChoiceConstructor {

    @Inject
    private ChoiceConstructor(@Inject("choiceConstructorBean") ChoiceConstructorBean choiceConstructorBean,
                              ChoiceConstructorBean notChoiceConstructorBean){}

}
