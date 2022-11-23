package org.jvault.struct.choiceconstructorinject;

import org.jvault.annotation.Inject;
import org.jvault.annotation.InternalBean;
import org.jvault.bean.Type;

@InternalBean(type = Type.NEW)
public final class ChoiceConstructor {

    private final ChoiceConstructorBean FIRST_BEAN;
    private final ChoiceConstructorBean SECOND_BEAN;

    public String helloFirstBean(){
        return FIRST_BEAN.hello();
    }

    public String helloSecondBean(){
        return SECOND_BEAN.hello();
    }

    @Inject
    private ChoiceConstructor(@Inject("choiceConstructorBean") ChoiceConstructorBean choiceConstructorBean,
                              ChoiceConstructorBean notChoiceConstructorBean){
        FIRST_BEAN = choiceConstructorBean;
        SECOND_BEAN = notChoiceConstructorBean;
    }

}
