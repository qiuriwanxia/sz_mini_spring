package com.sz.context.support;

import com.sz.core.io.ClassPathXmlResource;
import com.sz.core.io.Resource;
import com.sz.spring.factory.config.BeanDefinition;
import com.sz.spring.factory.support.AbstractBeanDefinition;
import com.sz.spring.factory.support.BeanFactory;
import com.sz.spring.factory.support.DefaultListableBeanFactory;
import com.sz.spring.factory.support.GenericBeanDefinition;
import com.sz.spring.factory.xml.XmlBeanDefinitionReader;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 解析XML 获取Bean的配置信息
 * 注意此时不实例化bean
 */
public class ClassPathXmlApplicationContext {


    private static final String CLASSPATH = "classpath:";


    private BeanFactory beanFactory = new DefaultListableBeanFactory();

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }

    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    /**
     * 通过路径解析xml配置文件
     *
     * @param fileName
     */
    public ClassPathXmlApplicationContext(String fileName) {
        try {
            // 1.读取配置文件 获取资源
            Resource resource = new ClassPathXmlResource(findXml(fileName));

            //2.解析资源 注册beanDefinition
            XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);

            //3.注册beanDefinition
            xmlBeanDefinitionReader.loadBeanDefinitions(resource);

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

}
