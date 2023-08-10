package com.sz.spring.beans.config;

public interface SingletonBeanRegister {

    /**
     * 注册单例bean
     * @param beanName
     * @param singletonObject
     */
    void registerSingleton(String beanName, Object singletonObject);

    /**
     * 获取单例bean
     * @param beanName
     * @return
     */
    Object getSingleton(String beanName);


    /**
     * 判断是否包含某个单例bean
     * @param beanName
     * @return
     */
    boolean containsSingleton(String beanName);


    /**
     * 获取所有单例bean的名称
     * @return
     */
    String[] getSingletonNames();

}
