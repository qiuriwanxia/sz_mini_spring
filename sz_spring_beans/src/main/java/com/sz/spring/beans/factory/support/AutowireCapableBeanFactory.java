package com.sz.spring.beans.factory.support;

import com.sz.spring.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import com.sz.spring.beans.factory.config.BeanPostProcessor;
import com.sz.spring.beans.factory.exception.BeansException;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class AutowireCapableBeanFactory extends AbstractBeanFactory{

    /**
     * 存储所有的处理器
     */
    private List<BeanPostProcessor> beanPostProcessorList = new CopyOnWriteArrayList<>();


    /**
     * 新增处理器
     * @param beanPostProcessor
     */
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor){
        //避免重复
        this.beanPostProcessorList.remove(beanPostProcessor);
        this.beanPostProcessorList.add(beanPostProcessor);
    }


    public int getBeanPostProcessorSize(){
        return this.beanPostProcessorList.size();
    }

    public List<BeanPostProcessor> getBeanPostProcessorList(){
        return this.beanPostProcessorList;
    }


    public Object postProcessorBeforeinitialization(Object bean, String beanName) throws BeansException {
        for (BeanPostProcessor beanPostProcessor : this.beanPostProcessorList) {
            if (beanPostProcessor instanceof AutowiredAnnotationBeanPostProcessor){
                AutowiredAnnotationBeanPostProcessor postProcessor = (AutowiredAnnotationBeanPostProcessor) beanPostProcessor;
                postProcessor.setBeanFactory(this);
                return   postProcessor.postProcessorBeforeinitialization(bean,beanName);
            }
        }
        return bean;
    }

    public Object postProcessorAfterinitialization(Object bean, String beanName) throws BeansException {
        for (BeanPostProcessor beanPostProcessor : this.beanPostProcessorList) {
            if (beanPostProcessor instanceof AutowiredAnnotationBeanPostProcessor){
                AutowiredAnnotationBeanPostProcessor postProcessor = (AutowiredAnnotationBeanPostProcessor) beanPostProcessor;
                postProcessor.setBeanFactory(this);
                return   postProcessor.postProcessorAfterinitialization(bean,beanName);
            }
        }
        return bean;
    }

    @Override
    public void applyBeanPostProcessorAfterInitialization(Object bean, String beanName) {
        this.postProcessorAfterinitialization(bean,beanName);
    }

    @Override
    public void invokeInitMethod(Object bean, String initMethodName) {
        super.invokeInitMethod(bean, initMethodName);
    }

    @Override
    public void applyBeanPostProcessorBeforeInitialization(Object bean, String beanName) {
        this.postProcessorBeforeinitialization(bean,beanName);
    }
}
