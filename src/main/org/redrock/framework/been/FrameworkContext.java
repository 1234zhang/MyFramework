package org.redrock.framework.been;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class FrameworkContext {
    private HttpServletRequest request;
    private HttpServletResponse response;
    //private RouteInfo routeInfo;
    private Map<String, String> paramMap;

    public HttpServletRequest getRequest() {
        return request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }
    //public RouteInfo getRouteInfo() {
      //  return routeInfo;
    //}
    public FrameworkContext(HttpServletResponse response, HttpServletRequest request/*, RouteInfo routeInfo*/){
        this.request = request;
        this.response = response;
        //this.routeInfo = routeInfo;
        paramMap = new HashMap<String, String >();
    }
    public void put(String key, String value){
        paramMap.put(key,value);
    }

    public Map<String, String> getParamMap() {
        return paramMap;
    }
}
