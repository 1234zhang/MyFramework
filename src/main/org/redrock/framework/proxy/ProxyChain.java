package org.redrock.framework.proxy;

import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.List;

public class ProxyChain {
    private Class<?> targetClass;//目标类
    private Object targetObject;//目标对象
    private Method targetMethod;//目标方法
    private MethodProxy methodProxy;//代理方法
    private Object[] targetParams;//方法参数

    private List<Proxy> proxyList;//代理列表
    private int proxyIndex;//代理索引

    public ProxyChain(Class<?> targetClass,Object targetObject, Method targetMethod, Object[] targetParams, List<Proxy> proxyList, MethodProxy methodProxy){
        this.targetClass = targetClass;
        this.targetObject = targetObject;
        this.methodProxy = methodProxy;
        this.targetParams = targetParams;
        this.targetMethod= targetMethod;
        this.proxyList = proxyList;
    }

    public Class<?> getTargetClass() {
        return targetClass;
    }

    public Method getTargetMethod() {
        return targetMethod;
    }

    public Object[] getTargetParams() {
        return targetParams;
    }

    public Object getTargetObject() {
        return targetObject;
    }
    public Object doProxyChain() throws Throwable{
        Object methodResult;
        if(proxyIndex < proxyList.size()){
            methodResult = proxyList.get(proxyIndex++).doProxy(this);
        }else{
            methodResult = methodProxy.invokeSuper(targetObject, targetParams);
        }
        return methodResult;
    }
}
