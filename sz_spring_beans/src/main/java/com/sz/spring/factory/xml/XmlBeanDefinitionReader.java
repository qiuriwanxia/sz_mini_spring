package com.sz.spring.factory.xml;

import com.sz.core.io.ClassPathXmlResource;
import com.sz.core.io.Resource;
import com.sz.spring.factory.config.BeanDefinition;
import com.sz.spring.factory.support.AbstractBeanDefinition;
import com.sz.spring.factory.support.BeanFactory;
import com.sz.spring.factory.support.GenericBeanDefinition;
import org.dom4j.Attribute;
import org.dom4j.Element;

import java.lang.reflect.Field;

public class XmlBeanDefinitionReader {


    private BeanFactory beanFactory;

    public XmlBeanDefinitionReader(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public void loadBeanDefinitions(Resource resource){
        while (resource.hasNext()){
            Element element = (Element) resource.next();
            Attribute idAttribute = element.attribute("id");
            Attribute classAttribute = element.attribute("class");
            if (idAttribute==null||classAttribute==null){
                throw new RuntimeException((idAttribute==null?"class":"class")+"不能为空");
            }
            BeanDefinition beanDefinition = buildBeanDefinition(idAttribute, classAttribute);
            beanFactory.registerBeanDefinition(beanDefinition);
        }
    }

    private BeanDefinition buildBeanDefinition(Attribute... attribute) {

        if (attribute==null){
            return null;
        }

        BeanDefinition beanDefinition = new GenericBeanDefinition();

        Class<AbstractBeanDefinition> beanDefinitionClass = AbstractBeanDefinition.class;

        for (Attribute attr : attribute) {
            String fieldName = attr.getName();
            try {
                if ("class".equals(fieldName)){
                    fieldName="beanClassName";
                }
                Field field = beanDefinitionClass.getDeclaredField(fieldName);
                field.setAccessible(true);
                try {
                    field.set(beanDefinition,attr.getValue());
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            }
        }
        return beanDefinition;
    }
}
