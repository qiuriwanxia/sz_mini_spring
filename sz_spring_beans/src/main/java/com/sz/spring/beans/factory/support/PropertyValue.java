package com.sz.spring.beans.factory.support;

import com.sun.istack.internal.Nullable;

import java.util.Objects;

public class PropertyValue {

    private String name;

    @Nullable
    private Object value;

    private String type;

    private boolean isRef;


    public boolean isRef() {
        return isRef;
    }

    public void setRef(boolean ref) {
        isRef = ref;
    }

    public  PropertyValue(Object value, String type, String name) {
        this.name = name;
        this.value = value;
        this.type = type;
    }

    public PropertyValue(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PropertyValue)) return false;

        PropertyValue that = (PropertyValue) o;

        if (!Objects.equals(name, that.name)) return false;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }
}
