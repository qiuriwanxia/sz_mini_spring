package com.sz.spring.beans.factory.config;

import com.sz.spring.beans.factory.BeanFactory;

/**
 * beanFactory 功能扩展
 *          1.添加bean的处理器
 *          2.获取处理器数量
 *          3.注册依赖eban
 *          4.获取依赖bean
 */
public interface ConfigurableBeanFactory extends
        BeanFactory,SingletonBeanRegister {
    String SCOPE_SINGLETON = "singleton";
    String SCOPE_PROTOTYPE = "prototype";
    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);
    int getBeanPostProcessorCount();
    void registerDependentBean(String beanName, String dependentBeanName);
    String[] getDependentBeans(String beanName);
    String[] getDependenciesForBean(String beanName);
}