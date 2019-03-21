package org.redrock.framework.core;

import org.redrock.framework.annotation.AutoWired;
import org.redrock.framework.annotation.Component;
import org.redrock.framework.util.ReflectUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/*
* 对象工厂
* */
public class BeenFactory {
    /*
    * 单例模式的单例对象
    * */
    private static BeenFactory beenFactory;
    /*
    * 类加载器
    * */
    private ClassLoader classLoader;
    /*
    * 组件集合 所有含有@controller何@component注解的类
    * */
    private Set<Class<?>> componentSet;
    /*
    * 用来提供给controller类和对象提供映射的集合
    * */
    private Map<Class<?>,Object> controllerMap;
    /*
    * 给component提供映射的集合类
    * */
    private Map<Class<?>, Object> componentMap;
    /*
    * 获得单例
    * */
    public static BeenFactory getInstance(){
        if(beenFactory == null){
            synchronized (BeenFactory.class){
                if(beenFactory == null){
                    beenFactory = new BeenFactory();
                }
            }
        }
        return beenFactory;
    }
    public Map<Class<?>, Object> getControllerMap() {
        return controllerMap;
    }
    public Map<Class<?> , Object> getComponentMap(){return componentMap;}
    private BeenFactory() {
        controllerMap = new HashMap<>();
        componentMap = new HashMap<>();
        componentSet = new HashSet<>();
        classLoader = ClassLoader.getInstance();
        init();
    }
    private void init(){
        Set<Class<?>> classSet = classLoader.getClassSet();
        for(Class<?> clazz : classSet){
            Annotation[] annotations = clazz.getAnnotations();
            for(Annotation annotation : annotations){
                Class<? extends Annotation> an = annotation.annotationType();
                if(an.equals(Component.class) || an.isAnnotationPresent(Component.class)){
                    Object obj = ReflectUtil.newInstance(clazz);
                    componentMap.put(clazz,obj);
                    componentSet.add(clazz);
                    if(! an.equals(Component.class)){
                        controllerMap.put(clazz,obj);
                    }
                }
            }
        }
        //为每一个component成员参数赋值
        for (Class<?> clazz : componentSet){
            Field[] fields = clazz.getDeclaredFields();
            for(Field field : fields){
                AutoWired autoWired = field.getAnnotation(AutoWired.class);
                if(autoWired != null){
                    Object target = componentMap.get(field.getType());
                    if(target != null){
                        Object obj = componentMap.get(clazz);
                        ReflectUtil.setFieldValue(field,obj,target);
                    }else{
                        throw new RuntimeException("this component" + field.getName() + "not found");
                    }
                }
            }
        }
    }
    public void set(Class<?> clazz,Object obj){
        componentMap.put(clazz, obj);
        componentSet.add(clazz);
    }
    public <T> T getBeen(Class<T> clazz){
        T t = (T) componentMap.get(clazz);
        if(t != null){
            return t;
        }
        throw new RuntimeException("been not found");
    }
}
