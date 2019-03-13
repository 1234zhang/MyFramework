package org.redrock.framework.been;

import org.redrock.framework.core.Handle;

import java.util.HashSet;
import java.util.Set;

public class RouteTree {
    private Set<RouteTree> childNode;
    private char data;
    private Handle handle;
    public RouteTree(){
        init();
    }
    public RouteTree(char data){
        this.data = data;
        init();
    }
    public boolean isLeaf(){
        return childNode.isEmpty() || childNode == null;
    }
    public char getData() {
        return data;
    }

    public void setData(char data) {
        this.data = data;
    }

    public Set<RouteTree> getChildNode() {
        return childNode;
    }

    private void init(){
        childNode = new HashSet<>();
    }

    public void setHandle(Handle handle) {
        this.handle = handle;
    }

    public Handle getHandle() {
        return handle;
    }

    @Override
    public boolean equals(Object obj){
        RouteTree node = (RouteTree) obj;
        return this.getData() == ((RouteTree) obj).getData();
    }
    @Override
    public int hashCode(){
        return this.getData();
    }
}
