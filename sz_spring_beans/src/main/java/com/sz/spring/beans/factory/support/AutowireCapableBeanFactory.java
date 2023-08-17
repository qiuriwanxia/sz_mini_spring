package com.sz.spring.beans.factory.support;

import com.sz.spring.beans.factory.BeanFactory;
import com.sz.spring.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import com.sz.spring.beans.factory.config.BeanPostProcessor;
import com.sz.spring.beans.factory.exception.BeansException;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public interface AutowireCapableBeanFactory extends BeanFactory {


    int AUTOWIRE_NO = 0;
    int AUTOWIRE_BY_NAME = 1;
    int AUTOWIRE_BY_TYPE = 2;

    Object applyBeanPostProcessorBeforeInitialization(Object existingBean, String beanName) throws BeansException;

    Object applyBeanPostProcessorAfterInitialization(Object existingBean, String beanName) throws BeansException;

}
