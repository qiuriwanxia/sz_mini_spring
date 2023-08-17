package com.sz.spring.beans.factory.config;

import com.sz.spring.beans.factory.BeanFactory;
import com.sz.spring.beans.factory.exception.BeansException;

import java.util.Map;

/**
 * 功能扩展
 *      1.判断是否包含某个bean
 *      2.获取bd的数量
 *      3，获取全部bd的name集合
 *      4.根据类型获取beanName
 *      5.根据类型获取bean Map
 */
public interface ListableBeanFactory extends BeanFactory {
    boolean containsBeanDefinition(String beanName);
    int getBeanDefinitionCount();
    String[] getBeanDefinitionNames();
    String[] getBeanNamesForType(Class<?> type);
    <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException;
}