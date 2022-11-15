package org.jvault.bean.composition;

import org.jvault.bean.Accessors;
import org.jvault.bean.Bean;

@SuppressWarnings("unused")
final class BeanCompositionAccessorImpl extends Accessors.BeanCompositionAccessor {

    @Override
    protected Bean.Builder<? extends Bean> getBuilder(Class<? extends Bean> beanType) {
        if(beanType.equals(NewBean.class)) return NewBean.getBuilder();
        return SingletonBean.getBuilder();
    }

    static{
        Accessors.BeanCompositionAccessor.registerAccessor(new BeanCompositionAccessorImpl());
    }

}
