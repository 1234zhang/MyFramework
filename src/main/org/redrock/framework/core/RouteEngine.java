package org.redrock.framework.core;

import org.redrock.framework.annotation.HttpMethod;
import org.redrock.framework.annotation.RequestMapper;
import org.redrock.framework.been.RouteInfo;
import org.redrock.framework.been.RouteTree;
import org.redrock.framework.util.RouteUtil;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/*
* 路由引擎
* */
public class RouteEngine {
    private static RouteEngine instance;
    private ClassLoader classLoader  = ClassLoader.getInstance();
    Map<HttpMethod, RouteTree> routeMap = new HashMap<>();
    private BeenFactory beenFactory;
    public static RouteEngine getInstance(){
        if(instance == null){
            synchronized (RouteEngine.class){
                if(instance == null){
                    instance = new RouteEngine();
                }
            }
        }
        return instance;
    }
    private RouteEngine(){
        init();
    }
    private void init(){
        initMap();
        beenFactory = BeenFactory.getInstance();
        loadMap();
    }
    private void initMap(){
        routeMap.put(HttpMethod.POST,new RouteTree());
        routeMap.put(HttpMethod.GET,new RouteTree());
        routeMap.put(HttpMethod.DELETE,new RouteTree());
        routeMap.put(HttpMethod.PUT,new RouteTree());
    }
    private void loadMap(){
        Map<Class<?>,Object> controllerMap = beenFactory.getControllerMap();
        String prefix = "";
        for(Map.Entry<Class<?>,Object> entry : controllerMap.entrySet()){
            Class<?> clazz = entry.getKey();
            Object obj = entry.getValue();
            RequestMapper requestMapper = clazz.getAnnotation(RequestMapper.class);
            if(requestMapper != null){
                prefix = requestMapper.value();
            }
            Method[] methods = clazz.getMethods();
            for(Method method : methods){
                RequestMapper mapper = method.getAnnotation(RequestMapper.class);
                if(mapper != null){
                    String url = mapper.value();
                    HttpMethod httpMethod = mapper.method();
                    RouteTree tree = routeMap.get(httpMethod);
                    Handle handle = new Handle(obj,method);
                    RouteUtil.insert(tree,prefix + url,handle);
                    routeMap.put(httpMethod,tree);
                }
            }
        }
    }
    public Handle getHandle(HttpMethod httpMethod,String uri) {
        return RouteUtil.isExist(routeMap.get(httpMethod),uri);
    }
    public void traverse(HttpMethod httpMethod){
       RouteTree tree = routeMap.get(httpMethod);
        RouteUtil.traverse(tree);
        System.out.println(tree.getChildNode().isEmpty());
    }
}
