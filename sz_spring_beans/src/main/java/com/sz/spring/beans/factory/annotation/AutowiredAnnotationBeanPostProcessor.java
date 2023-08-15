package com.sz.spring.beans.factory.annotation;

import com.sz.spring.beans.factory.BeanFactory;
import com.sz.spring.beans.factory.config.BeanPostProcessor;
import com.sz.spring.beans.factory.exception.BeansException;

import java.lang.reflect.Field;

public class AutowiredAnnotationBeanPostProcessor  implements BeanPostProcessor {

    private BeanFactory beanFactory;

    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public Object postProcessorBeforeinitialization(Object bean, String beanName) throws BeansException {
        //在前置方法中实现@Autowired自动注入
        //1.判断当前bean的字段上是否有@Autowired注解
        Class<?> beanClass = bean.getClass();
        for (Field declaredField : beanClass.getDeclaredFields()) {

            boolean annotationPresent = declaredField.isAnnotationPresent(Autowired.class);

            if (annotationPresent){
                Autowired annotation = declaredField.getAnnotation(Autowired.class);
                //获取required的值
                if (annotation.required()) {

                    //获取字段名
                    String fieldName = declaredField.getName();

                    //查找bean
                    Object obj = this.beanFactory.getBean(fieldName);

                    //设置值
                    declaredField.setAccessible(true);
                    try {
                        declaredField.set(bean,obj);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }

                }

            }

        }

        return BeanPostProcessor.super.postProcessorBeforeinitialization(bean, beanName);
    }

    @Override
    public Object postProcessorAfterinitialization(Object bean, String beanName) throws BeansException {

        return BeanPostProcessor.super.postProcessorAfterinitialization(bean, beanName);
    }
}
