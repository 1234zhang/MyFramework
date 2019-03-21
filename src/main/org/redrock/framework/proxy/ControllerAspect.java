package org.redrock.framework.proxy;

import org.redrock.framework.annotation.Aspect;
import org.redrock.framework.annotation.Controller;
import org.redrock.framework.annotation.RequestMapper;

import java.lang.reflect.Method;

@Aspect(Controller.class)
public class ControllerAspect extends AspectProxy {
    @Override
    public void before(Class<?> clazz, Method method, Object[] params) {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
