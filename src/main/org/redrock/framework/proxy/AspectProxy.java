package org.redrock.framework.proxy;

import java.lang.reflect.Method;
import java.util.List;

public class AspectProxy implements Proxy{

    @Override
    public Object doProxy(ProxyChain proxyChain) throws Throwable {
        Class<?> clazz = proxyChain.getTargetClass();
        Method method = proxyChain.getTargetMethod();
        Object[] methodParams = proxyChain.getTargetParams();

        Object result;
        begin();
        if(intercept(clazz,method,methodParams)){
            before();
            result = proxyChain.doProxyChain();
            after();
        }else{
            result = proxyChain.doProxyChain();
        }
        return result;
    }
    public boolean intercept(Class<?> clazz, Method method, Object[] params){
        return true;
    }
    public void begin(){

    }
    public void before(){
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void after(){

    }
    public void end(){

    }
}
