package com.sz.spring.beans.support;

import com.sz.spring.beans.config.SingletonBeanRegister;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultSingletonBeanRegistry implements SingletonBeanRegister {

    /**
     * 一级缓存
     * @param beanName
     * @param singletonObject
     */
    private Map<String,Object> singletonObjects = new ConcurrentHashMap<>();

    /**
     * 二级缓存  解决循环依赖问题
     */
    private Map<String,Object> earlySingletonObjects = new ConcurrentHashMap<>();

    /**
     * 存储注册过的单例bean 并且按顺序存储
     */
    private Set<String> registeredSingletons = new LinkedHashSet<>();

    @Override
    public void registerSingleton(String beanName, Object singletonObject) {
        if (!registeredSingletons.contains(beanName)){
            synchronized (this.singletonObjects){
                if (!registeredSingletons.contains(beanName)){
                    this.addSingleton(beanName,singletonObject);
                    this.earlySingletonObjects.remove(beanName);
                }
            }
        }
    }

    @Override
    public Object getSingleton(String beanName) {
        Object bean = singletonObjects.get(beanName);
        if (bean==null){
           bean = this.earlySingletonObjects.get(beanName);
        }
        return bean;
    }

    public void addEarlySingleton(String beanName,Object obj){
        this.earlySingletonObjects.put(beanName,obj);
    }


    @Override
    public boolean containsSingleton(String beanName) {
        return registeredSingletons.contains(beanName);
    }

    @Override
    public String[] getSingletonNames() {
        return (String[]) registeredSingletons.toArray();
    }

    private void addSingleton(String beanName, Object singletonObject){
        this.singletonObjects.put(beanName,singletonObject);
        this.registeredSingletons.add(beanName);
    }
}
