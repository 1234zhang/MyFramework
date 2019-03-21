package org.redrock.framework.core;

import org.redrock.framework.annotation.Aspect;
import org.redrock.framework.annotation.AutoWired;
import org.redrock.framework.proxy.Proxy;
import org.redrock.framework.proxy.ProxyManager;
import org.redrock.framework.util.ReflectUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;

public class AopLoader {
    private static ClassLoader classLoader;
    private static BeenFactory beenFactory;

    private static AopLoader instance;

    public AopLoader(){
        this.classLoader = ClassLoader.getInstance();
        this.beenFactory = BeenFactory.getInstance();
        init();
    }

    private void init(){
        Map<Class<?>,Object> componentMap = beenFactory.getComponentMap();
        Map<Class<?>,Set<Class<?>>> proxyMap = createMap(Aspect.class);
        Map<Class<?>, List<Proxy>> targetMap = null;
        try {
            targetMap = createTargetProxy(proxyMap);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        for (Map.Entry<Class<?>, List<Proxy>> targetEntry : targetMap.entrySet()){
            Class<?> targetClass = targetEntry.getKey();
            List<Proxy> proxyList = targetEntry.getValue();
            try {
                Object proxy = ProxyManager.createProxy(targetClass, proxyList);
                Field[] fields = targetClass.getDeclaredFields();
                for (Field field : fields){
                    AutoWired getAutoWired = field.getAnnotation(AutoWired.class);
                    if(getAutoWired != null){
                        Object targetObject = componentMap.get(getAutoWired.getClass());
                        ReflectUtil.setFieldValue(field,field,targetObject);
                    }
                }
                beenFactory.set(targetClass,proxy);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
    }
    public static AopLoader geteInstance(){
        if(instance == null){
            synchronized (AopLoader.class){
                if(instance == null){
                    instance = new AopLoader();
                }
            }
        }
        return instance;
    }
    public Map<Class<?>, Set<Class<?>>> createMap(Class<?> clazz){
        Map<Class<?>,Set<Class<?>>> proxyMap = new HashMap<>();
        Set<Class<?>> classSet = classLoader.getClassSet(clazz);
        for (Class<?> proxyClass : classSet){
            if(proxyClass.isAnnotationPresent(Aspect.class)){
                Aspect aspect = proxyClass.getAnnotation(Aspect.class);
                Set<Class<?>> targetClass = createTargetClassSet(aspect);
                proxyMap.put(proxyClass,targetClass);
            }
        }
        return proxyMap;
    }
    public Map<Class<?>,List<Proxy>> createTargetProxy(Map<Class<?>,Set<Class<?>>> proxyMap) throws IllegalAccessException, InstantiationException {
        Map<Class<?>,List<Proxy>> targetProxy = new HashMap<Class<?>,List<Proxy>>();
        for (Map.Entry<Class<?>,Set<Class<?>>> proxyTarget : proxyMap.entrySet()){
            Class<?> targetClass = proxyTarget.getKey();
            Set<Class<?>> targetClassSet = proxyTarget.getValue();
            for (Class<?> clazz : targetClassSet){
                Proxy proxy = (Proxy) clazz.newInstance();
                if(targetProxy.containsKey(clazz)){
                    targetProxy.get(clazz).add(proxy);
                }else{
                    List<Proxy> proxyList = new ArrayList<>();
                    proxyList.add(proxy);
                    targetProxy.put(clazz,proxyList);
                }
            }
        }
        return targetProxy;
    }
    private Set<Class<?>> createTargetClassSet(Aspect aspect){
        Set<Class<?>> targetClass = new HashSet<Class<?>>();
        Class<? extends Annotation> annotation = aspect.value();
        if(annotation != null && !annotation.equals(Aspect.class)){
            targetClass.addAll(classLoader.getClassSetByAnnotation(annotation));

        }
        return targetClass;
    }
}
