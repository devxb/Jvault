package org.jvault.bean.composition;

import org.jvault.bean.Bean;
import org.jvault.exceptions.DisallowedAccessException;
import org.jvault.metadata.InternalAPI;

@InternalAPI
public final class SingletonBean extends AbstractBean {

    private SingletonBean(Bean.Builder<SingletonBean> builder) {
        super(builder);
    }

    static Builder<SingletonBean> getBuilder() {
        return new Builder<SingletonBean>() {
            @Override
            protected SingletonBean create() {
                return new SingletonBean(this);
            }
        };
    }

    @Override
    @SuppressWarnings("unchecked")
    public <R> R load() {
        return (R) INSTANCE;
    }


}
