package com.sz.spring.beans.factory.support;
import com.sz.spring.beans.factory.config.ConfigurableBeanFactory;
import com.sz.spring.beans.factory.config.ListableBeanFactory;

public interface ConfigurableListableBeanFactory
        extends ListableBeanFactory, AutowireCapableBeanFactory,
        ConfigurableBeanFactory {
}