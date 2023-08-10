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
     * 存储注册过的单例bean 并且按顺序存储
     */
    private Set<String> registeredSingletons = new LinkedHashSet<>();

    @Override
    public void registerSingleton(String beanName, Object singletonObject) {
        if (!registeredSingletons.contains(beanName)){
            synchronized (this.singletonObjects){
                if (!registeredSingletons.contains(beanName)){
                    this.addSingleton(beanName,singletonObject);
                }
            }
        }
    }

    @Override
    public Object getSingleton(String beanName) {
        return singletonObjects.get(beanName);

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
