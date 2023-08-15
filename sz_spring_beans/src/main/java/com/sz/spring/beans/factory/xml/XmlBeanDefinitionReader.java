package com.sz.spring.beans.factory.xml;

import com.sz.core.io.Resource;
import com.sz.spring.beans.factory.config.BeanDefinition;
import com.sz.spring.beans.factory.support.ConstructorArgumentValues;
import com.sz.spring.beans.factory.support.PropertieValues;
import com.sz.spring.beans.factory.support.PropertyValue;
import com.sz.spring.beans.factory.support.ValueHolder;
import com.sz.spring.beans.factory.support.AbstractBeanDefinition;
import com.sz.spring.beans.factory.BeanFactory;
import com.sz.spring.beans.factory.support.GenericBeanDefinition;
import org.dom4j.Attribute;
import org.dom4j.Element;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class XmlBeanDefinitionReader {


    private BeanFactory beanFactory;

    public XmlBeanDefinitionReader(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public void loadBeanDefinitions(Resource resource) {
        while (resource.hasNext()) {
            Element element = (Element) resource.next();
            Attribute idAttribute = element.attribute("id");
            Attribute classAttribute = element.attribute("class");
            if (idAttribute == null || classAttribute == null) {
                throw new RuntimeException((idAttribute == null ? "class" : "class") + "不能为空");
            }
            AbstractBeanDefinition beanDefinition = (AbstractBeanDefinition) buildBeanDefinition(idAttribute, classAttribute);

            //处理构造方法参数
            ConstructorArgumentValues constructorArgumentValues = new ConstructorArgumentValues();
            List<Element> constructor = element.elements("constructor");
            for (int i = 0; i < constructor.size(); i++) {
                Element ele = constructor.get(i);
                Attribute type = ele.attribute("type");
                Attribute name = ele.attribute("name");
                Attribute value = ele.attribute("value");
                ValueHolder valueHolder = new ValueHolder(value.getValue(), type.getValue(), name.getValue());
                constructorArgumentValues.addArgumentValue(i, valueHolder);
            }

            //处理字段参数
            PropertieValues propertieValues = new PropertieValues();
            List<Element> property = element.elements("property");
            ArrayList<String> depends = new ArrayList<>();
            for (Element ele : property) {
                boolean rfFlag = false;
                PropertyValue propertyValue;
                Attribute type = ele.attribute("type");
                Attribute name = ele.attribute("name");
                Attribute value = ele.attribute("value");
                Attribute ref = ele.attribute("ref");
                if (ref != null && !"".equals(ref.getValue())) {
                    String refValue = ref.getValue();
                    value = ref;
                    depends.add(refValue);
                    rfFlag = true;
                }

                String valueValue = value.getValue();
                String typeValue;
                if (type == null) {
                    typeValue = "";
                } else {
                    typeValue = type.getValue();
                } ;
                String nameValue = name.getValue();
                propertyValue = new PropertyValue(valueValue, typeValue, nameValue);
                if (rfFlag) {
                    propertyValue.setRef(true);
                }
                propertieValues.addPropertieValue(propertyValue);
            }

            beanDefinition.setDependsOn(depends.toArray(new String[0]));
            beanDefinition.setPropertieValues(propertieValues);
            beanDefinition.setConstructorArgumentValues(constructorArgumentValues);

            beanFactory.registerBeanDefinition(beanDefinition);
        }
    }

    private BeanDefinition buildBeanDefinition(Attribute... attribute) {

        if (attribute == null) {
            return null;
        }

        BeanDefinition beanDefinition = new GenericBeanDefinition();

        Class<AbstractBeanDefinition> beanDefinitionClass = AbstractBeanDefinition.class;

        for (Attribute attr : attribute) {
            String fieldName = attr.getName();
            try {
                if ("class".equals(fieldName)) {
                    fieldName = "beanClassName";
                }
                Field field = beanDefinitionClass.getDeclaredField(fieldName);
                field.setAccessible(true);
                try {
                    field.set(beanDefinition, attr.getValue());
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
