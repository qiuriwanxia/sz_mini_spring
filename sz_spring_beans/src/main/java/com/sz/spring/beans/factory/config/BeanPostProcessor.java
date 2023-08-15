package com.sz.spring.beans.factory.config;

import com.sz.spring.beans.factory.exception.BeansException;

public interface BeanPostProcessor {

    /**
     * 初始化前执行
     * @param bean 当前bean
     * @param beanName bean名称
     * @return
     * @throws BeansException
     */
    default Object postProcessorBeforeinitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    /**
     * 初始化后执行
     * @param bean 当前bean
     * @param beanName bean名称
     * @return
     * @throws BeansException
     */
    default Object postProcessorAfterinitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

}
