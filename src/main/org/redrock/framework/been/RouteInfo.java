package org.redrock.framework.been;

import org.redrock.framework.annotation.HttpMethod;

import java.util.Objects;

//设置路由信息
public class RouteInfo {
    private HttpMethod method;
    private String url;
    private String urlParam;

    public HttpMethod getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    public String getUrlParam() {
        return urlParam;
    }

    public void setUrlParam(String urlParam) {
        this.urlParam = urlParam;
    }
    public RouteInfo(HttpMethod method, String url){
        this.method = method;
        this.url = url;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if(obj == null || getClass() != obj.getClass()){
            return false;
        }
        RouteInfo routeInfo = (RouteInfo) obj;
        return Objects.equals(this.url, routeInfo.getUrl()) && this.method == routeInfo.getMethod();
    }
    @Override
    public int hashCode(){
        return Objects.hash(this.url, this.method);
    }
}
