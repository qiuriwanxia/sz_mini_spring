package com.sz.context.support;

import com.sz.spring.factory.config.BeanDefinition;
import com.sz.spring.factory.support.AbstractBeanDefinition;
import com.sz.spring.factory.support.GenericBeanDefinition;
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

    private static final String CLASSPATH="classpath:";


    /**
     * 通过路径解析xml配置文件
     *
     * @param fileName
     */
    public ClassPathXmlApplicationContext(String fileName) {
        // 1.读取配置文件 存放bean的定义信息
        this.parseXml(fileName);
        // 2.实例化bean
        this.refresh();
    }


    public Object getBean(String beanName){
        return singletonObjects.get(beanName);
    }

    private void refresh() {
        for (String beanName : this.beanNames) {
            BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
            Class beanClass = beanDefinition.getBeanClass();
            try {
                Object bean = beanClass.newInstance();
                singletonObjects.put(beanName,bean);
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }


    private void parseXml(String fileName) {

        InputStream inputStream = null;
        try {
            inputStream = this.findXml(fileName);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(fileName+"文件不存在");
        }

        //通过dom4j解析xml配置文件
        SAXReader saxReader = new SAXReader();
        try {
            Document document = saxReader.read(inputStream);
            Element rootElement = document.getRootElement();
            Iterator<Element> beanIterator = rootElement.elementIterator("bean");
            while (beanIterator.hasNext()){
                Element element = beanIterator.next();
                Attribute idAttribute = element.attribute("id");
                Attribute classAttribute = element.attribute("class");
                if (idAttribute==null||classAttribute==null){
                    throw new RuntimeException((idAttribute==null?"class":"class")+"不能为空");
                }
                //封装BeanDefinition
                BeanDefinition beanDefinition = buildBeanDefinition(idAttribute, classAttribute);
                try {
                    beanDefinition.setBeanClass(Class.forName(beanDefinition.getBeanClassName()));
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                //保存至Map集合中
                String id = beanDefinition.getId();

                beanDefinitionMap.put(id,beanDefinition);

                beanNames.add(id);
            }
        } catch (DocumentException e) {
            throw new RuntimeException(e);
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

    private InputStream findXml(String fileName) throws FileNotFoundException {

        if (fileName.startsWith("classpath:")) {
            fileName=fileName.substring(CLASSPATH.length(),fileName.length());
            return Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
        } else {
            return new FileInputStream(fileName);
        }

    }


}
