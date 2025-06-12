package org.khr.proxydemo;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.PriorityOrdered;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

/**
 * @author KK
 * @create 2025-06-12-15:02
 */
@Component
public class ConsumerBootstrap implements BeanPostProcessor,PriorityOrdered {

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = bean.getClass();
        // 遍历对象的所有属性
        Field[] declaredFields = beanClass.getDeclaredFields();
        for (Field field : declaredFields) {
            Inject annotation = field.getAnnotation(Inject.class);
            if (annotation != null) {
                // 为属性生成代理对象
                Class<?> interfaceClass = field.getType();
                field.setAccessible(true);
                Object proxyObject = null;
                try {
                    proxyObject = ServiceProxyFactory.getProxyByJavassist(interfaceClass);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                try {
                    field.set(bean, proxyObject);
                    field.setAccessible(false);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("为字段注入代理对象失败", e);
                }
            }
        }
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
