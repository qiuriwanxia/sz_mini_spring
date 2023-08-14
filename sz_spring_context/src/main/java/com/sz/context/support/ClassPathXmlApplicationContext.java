package com.sz.context.support;

import com.sz.context.ApplicationContext;
import com.sz.context.ApplicationEvent;
import com.sz.core.io.ClassPathXmlResource;
import com.sz.core.io.Resource;
import com.sz.spring.beans.config.BeanDefinition;
import com.sz.spring.beans.support.BeanFactory;
import com.sz.spring.beans.support.DefaultListableBeanFactory;
import com.sz.spring.beans.xml.XmlBeanDefinitionReader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * 解析XML 获取Bean的配置信息
 * 注意此时不实例化bean
 */
public class ClassPathXmlApplicationContext implements ApplicationContext {


    private static final String CLASSPATH = "classpath:";


    private BeanFactory beanFactory = new DefaultListableBeanFactory();

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }

    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public ClassPathXmlApplicationContext(String fileName) {
        this(fileName, true);
    }

    /**
     * 通过路径解析xml配置文件
     *
     * @param fileName
     */
    public ClassPathXmlApplicationContext(String fileName, boolean isRefesh) {
        try {
            // 1.读取配置文件 获取资源
            Resource resource = new ClassPathXmlResource(findXml(fileName));

            //2.解析资源 注册beanDefinition
            XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);

            //3.注册beanDefinition
            xmlBeanDefinitionReader.loadBeanDefinitions(resource);

            if (isRefesh){
                this.refresh();
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }


    private InputStream findXml(String fileName) throws FileNotFoundException {

        if (fileName.startsWith("classpath:")) {
            fileName = fileName.substring(CLASSPATH.length(), fileName.length());
            return Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
        } else {
            return new FileInputStream(fileName);
        }

    }


    public Object getBean(String beanName) {
        return this.beanFactory.getBean(beanName);
    }

    public void registerBeanDefinition(BeanDefinition beanDefinition) {
        this.beanFactory.registerBeanDefinition(beanDefinition);
    }


    /**
     * 新增能力  判断是否存在某个单例bean 来自父类
     *
     * @param name
     * @return
     */
    public boolean containsBean(String name) {
        return this.beanFactory.containsBean(name);
    }

    /**
     * 新增能力 注册单例bean 来自父类
     *
     * @param beanName
     * @param bean
     */
    public void registerBean(String beanName, Object bean) {
        this.beanFactory.registerBean(beanName, bean);
    }

    public void publishEvent(ApplicationEvent event) {
    }

    public boolean isSingleton(String name) {
        return false;
    }

    public boolean isPrototype(String name) {
        return false;
    }

    public Class<?> getType(String name) {
        return null;
    }

    @Override
    public void refresh() {

        this.beanFactory.refresh();

    }

}
