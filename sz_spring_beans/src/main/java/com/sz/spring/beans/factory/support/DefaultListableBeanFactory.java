package com.sz.spring.beans.factory.support;

import com.sz.spring.beans.factory.config.BeanDefinition;
import com.sz.spring.beans.factory.exception.BeansException;

public class DefaultListableBeanFactory extends AbstractBeanFactory  {



    public void refresh() {
        super.refresh();
    }

    @Override
    public Object getBean(String beanName) throws BeansException {
        return super.getBean(beanName);
    }



    @Override
    public void registerBeanDefinition(BeanDefinition beanDefinition) {
        super.registerBeanDefinition(beanDefinition);
    }

    @Override
    public boolean containsBean(String name) {
        return super.containsSingleton(name);
    }

    @Override
    public void registerBean(String beanName, Object obj) {
        super.registerSingleton(beanName, obj);
    }

    @Override
    public boolean isSingleton(String name) {
        return false;
    }

    @Override
    public boolean isPrototype(String name) {
        return false;
    }

    @Override
    public Class<?> getType(String name) {
        return null;
    }
}
