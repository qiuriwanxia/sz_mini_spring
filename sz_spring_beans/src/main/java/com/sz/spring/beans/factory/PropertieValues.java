package com.sz.spring.beans.factory;

import javax.swing.*;
import java.util.*;

public class PropertieValues {

    private List<PropertyValue> propertyValues = new ArrayList<>();

    private Set<String> processedProperties = new LinkedHashSet<>();

    public List<PropertyValue> getPropertyValues() {
        return propertyValues;
    }

    public void setPropertyValues(List<PropertyValue> propertyValues) {
        this.propertyValues = propertyValues;
    }

    public Set<String> getProcessedProperties() {
        return processedProperties;
    }

    public void setProcessedProperties(Set<String> processedProperties) {
        this.processedProperties = processedProperties;
    }

    /**
     *  添加一个配置元信息类
     */
    public void addPropertieValue(PropertyValue pvl){
        this.propertyValues.add(pvl);
    }

    /**
     *  创建并添加一个配置元信息类
     */
    public  void addPropertieValue(String name,String value){
        this.addPropertieValue(new PropertyValue(name,value));
    }

    /**
     * 删除一个元信息类
     */
    public void removePropertieValue(PropertyValue pvl){
            this.propertyValues.remove(pvl);
    }

    /**
     * 删除指定的元信息类
     */
    public void removePropertieValue(String name){
        this.propertyValues.remove(getPropertieValue(name));
    }

    /**
     * 获取元信息类
     */
    public PropertyValue getPropertieValue(String name){
        Iterator<PropertyValue> iterator = this.propertyValues.iterator();

        while (iterator.hasNext()){
            PropertyValue next = iterator.next();
            if (next.getName().equals(name)){
                return next;
            }
        }

        return null;
    }



}
