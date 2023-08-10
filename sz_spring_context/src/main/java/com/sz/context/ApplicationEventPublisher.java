package com.sz.context;

public interface ApplicationEventPublisher {


    default void publishEvent(ApplicationEvent event){
        this.publishEvent((Object)event);
    }

    void publishEvent(Object var1);

}
