package com.sz.spring.beans.support;

import com.sz.spring.beans.config.BeanDefinition;
import com.sz.spring.beans.exception.BeansException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultListableBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory{

    /**
     * 存放beanDefinition的map集合
     */
    private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap();


    /**
     * 顺序存放bean的名称  用于保证实例化时bean注册的顺序性
     */
    private List<String> beanNames = new ArrayList();


    @Override
    public Object getBean(String beanName) throws BeansException {
        Object bean = this.getSingleton(beanName);
        if (bean==null){
            synchronized (DefaultListableBeanFactory.class){
                if (bean==null){
                    //获取beandefinition
                    BeanDefinition beanDefinition = this.beanDefinitionMap.get(beanName);
                    if (beanDefinition==null){
                        throw new BeansException("NoSuchBeanDefinitionException");
                    }

                    //创建bean实例
                    Class<? extends BeanDefinition> beanDefinitionClass = beanDefinition.getClass();

                    try {
                        bean = beanDefinitionClass.newInstance();
                        super.registerSingleton(beanName,bean);
                        return bean;
                    } catch (InstantiationException e) {
                        throw new RuntimeException(e);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }

                }
            }
        }

        return null;
    }


    @Override
    public void registerBeanDefinition(BeanDefinition beanDefinition) {
        this.beanDefinitionMap.put(beanDefinition.getId(),beanDefinition);
        this.beanNames.add(beanDefinition.getId());
    }

    @Override
    public boolean containsBean(String name) {
        return super.containsSingleton(name);
    }

    @Override
    public void registerBean(String beanName, Object obj) {
        super.registerSingleton(beanName,obj);
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
