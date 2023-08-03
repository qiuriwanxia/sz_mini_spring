package com.sz.spring.factory.support;

import com.sz.spring.factory.config.BeanDefinition;
import com.sz.spring.factory.exception.BeansException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultListableBeanFactory implements BeanFactory{

    /**
     * 存放beanDefinition的map集合
     */
    private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap();


    /**
     * 顺序存放bean的名称  用于保证实例化时bean注册的顺序性
     */
    private List<String> beanNames = new ArrayList();

    /**
     * 存放单例对象bean
     */
    private final Map<String, Object> singletonObjects = new ConcurrentHashMap<>(256);

    @Override
    public Object getBean(String beanName) throws BeansException {
        Object bean = this.singletonObjects.get(beanName);
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
                        this.singletonObjects.put(beanName,bean);
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
}
