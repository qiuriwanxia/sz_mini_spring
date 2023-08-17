package com.sz.spring.beans.factory.support;

import com.sz.spring.beans.factory.BeanFactory;
import com.sz.spring.beans.factory.config.BeanDefinition;
import com.sz.spring.beans.factory.exception.BeansException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory {

    /**
     * 存放beanDefinition的map集合
     */
    private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap();


    /**
     * 顺序存放bean的名称  用于保证实例化时bean注册的顺序性
     */
    private List<String> beanNames = new ArrayList();


    @Override
    public Object getBean(String beanName) throws BeansException {

        Object bean = this.getSingleton(beanName);
        if (bean == null) {
            synchronized (DefaultListableBeanFactory.class) {
                if (bean == null) {
                    //获取beandefinition
                    AbstractBeanDefinition beanDefinition = (AbstractBeanDefinition) this.beanDefinitionMap.get(beanName);
                    if (beanDefinition == null) {
                        throw new BeansException("NoSuchBeanDefinitionException");
                    }

                    try {
                        //创建bean的实例
                        bean = doCreateBean(beanDefinition);

                        //添加到二级缓存中
                        addEarlySingleton(beanName,bean);

                        //处理属性
                        handleProper(beanName, bean, beanDefinition);

                        //执行初始化前方法回调
                        applyBeanPostProcessorBeforeInitialization(bean,beanName);

                        //执行初始化方法
                        String initMethodName = beanDefinition.getInitMethodName();

                        if (initMethodName !=null&&!initMethodName.equals("")){
                            //反射回调初始化方法
                            invokeInitMethod(bean,initMethodName);
                        }

                        //执行初始化后方法回调
                        applyBeanPostProcessorAfterInitialization(bean,beanName);

                        return bean;
                    } catch (InstantiationException e) {
                        throw new RuntimeException(e);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }


                }
            }
        }

        return bean;
    }

    public Object applyBeanPostProcessorAfterInitialization(Object bean, String beanName) {
        return bean;
    }

    public void invokeInitMethod(Object bean, String initMethodName) {

    }

    public Object applyBeanPostProcessorBeforeInitialization(Object bean, String beanName) {
        return bean;
    }


    @Override
    public void registerBeanDefinition(BeanDefinition beanDefinition) {
        this.beanDefinitionMap.put(beanDefinition.getId(), beanDefinition);
        this.beanNames.add(beanDefinition.getId());
    }

    @Override
    public boolean containsBean(String name) {
        return false;
    }

    @Override
    public void registerBean(String beanName, Object obj) {

    }

    @Override
    public boolean isSingleton(String name) {
        return false;
    }

    @Override
    public boolean isPrototype(String name) {
        return false;
    }

    @Override
    public Class<?> getType(String name) {
        return null;
    }

    @Override
    public void refresh() {
        for (String beanName : beanNames) {
            try {
                getBean(beanName);
            }catch (Exception e){
                throw e;
            }
        }
    }

    private Object handleProper(String beanName, Object bean, AbstractBeanDefinition beanDefinition) throws IllegalAccessException {
        //处理属性值
        List<PropertyValue> propertyValueList = beanDefinition.getPropertieValues().getPropertyValues();
        Class  beanDefinitionClass = null;
        try {
            beanDefinitionClass = Class.forName(beanDefinition.getBeanClassName());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        for (PropertyValue propertyValue : propertyValueList) {
            String name = propertyValue.getName();
            String type = propertyValue.getType();
            Object value = propertyValue.getValue();
            boolean ref = propertyValue.isRef();
            if (!ref) {
                switch (type) {
                    case "String":
                        value = String.valueOf(value);
                        break;
                    case "Integer":
                        value = Integer.valueOf((String) value);
                        break;
                    case "int":
                        value = Integer.valueOf((String) value);
                        break;
                    default:
                        value = String.valueOf(value);
                        break;
                }
            } else {
                //如果是依赖类型  先获取依赖类
                value=getBean(String.valueOf(value));
            }

            //调用setter方法注入属性
            String methodName = "set".concat(name.substring(0, 1).toUpperCase()).concat(name.substring(1));
            try {
                Method classMethod = beanDefinitionClass.getMethod(methodName, value.getClass());
                classMethod.setAccessible(true);
                try {
                    classMethod.invoke(bean, value);
                } catch (InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }


        }


        super.registerSingleton(beanName, bean);
        return bean;
    }

    private static Object doCreateBean(AbstractBeanDefinition beanDefinition) throws InstantiationException, IllegalAccessException {
        Object bean = null;
        ConstructorArgumentValues constructorArgumentValues = beanDefinition.getConstructorArgumentValues();

        //创建bean实例
        Class<? extends Object> beanDefinitionClass = null;
        try {
            beanDefinitionClass = Class.forName(beanDefinition.getBeanClassName());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        //获取构造参数
        int argumentCount = constructorArgumentValues.getArgumentCount();


        if (argumentCount == 0) {
            //直接创建实例
            bean =  beanDefinitionClass.newInstance();

        }

        Class[] constructorTypes = new Class[argumentCount];
        Object[] constructorValues = new Object[argumentCount];

        for (int i = 0; i < constructorArgumentValues.getArgumentCount(); i++) {

            //根据类型作出不同的处理
            ValueHolder indexedArgumentValue = constructorArgumentValues.getIndexedArgumentValue(i);
            switch (indexedArgumentValue.getType()) {
                case "String":
                    constructorTypes[i] = String.class;
                    constructorValues[i] = String.valueOf(indexedArgumentValue.getValue());
                    break;
                case "Integer":
                    constructorTypes[i] = Integer.class;
                    constructorValues[i] = Integer.valueOf((String) indexedArgumentValue.getValue());
                    break;
                case "int":
                    constructorTypes[i] = int.class;
                    constructorValues[i] = Integer.valueOf((String) indexedArgumentValue.getValue());
                    break;
                default:
                    constructorTypes[i] = String.class;
                    constructorValues[i] = String.valueOf(indexedArgumentValue.getValue());
                    break;
            }

            //调用构造方法
            try {
                Constructor<?> constructor = beanDefinitionClass.getConstructor(constructorTypes);
                constructor.setAccessible(true);
                try {
                    bean = constructor.newInstance(constructorValues);
                } catch (InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }

        }
        return bean;
    }

}
