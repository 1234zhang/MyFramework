package org.redrock.framework.core;

import com.google.gson.Gson;
import org.redrock.framework.been.FrameworkContext;
import org.redrock.framework.been.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Enumeration;

/*
* 封装处理器
* */
public class Handle {
    /*
    * 全局统一的json解析器
    * */
    private static Gson gson = new Gson();
    /*
    * 用来处理请求的方法
    * */
    private Method method;
    /*
    * 该方法所属的controller
    * */
    private Object controller;
    public Handle(Object controller, Method method){
        this.controller = controller;
        this.method = method;
    }
    /*
    * 根据上下文执行方法
    * */
    String invoke(FrameworkContext context){
        HttpServletRequest request = context.getRequest();
        HttpServletResponse response = context.getResponse();
        Enumeration<String> params = request.getParameterNames();
        while(params.hasMoreElements()){
            String key = params.nextElement();
            String value = request.getParameter(key);
            context.put(key,value);
        }
        ResponseEntity res = null;
        try{
            res = (ResponseEntity)method.invoke(controller,context);
        }catch (IllegalAccessException | InvocationTargetException e){
            e.printStackTrace();
        }
        if(res != null){
            return gson.toJson(res);
        }
        return null;
    }
}
