package org.redrock.framework.util;

import org.redrock.framework.been.RouteTree;
import org.redrock.framework.core.Handle;

import java.util.Set;

public class RouteUtil {
    public static RouteTree insertNode(Set<RouteTree> nodes, char data){
        RouteTree node = new RouteTree(data);
        if(nodes.contains(node)){
            for(RouteTree treeNode : nodes){
                if(treeNode.getData() == data){
                    return treeNode;
                }
            }
        }else{
            nodes.add(node);
            return node;
        }
        return null;
    }
    public static Handle isExist(RouteTree treeNode, String uri) {
        char[] data = uri.toLowerCase().toCharArray();
        RouteTree flag = treeNode;
        for (int i = 0; i < data.length; i++) {
            for (RouteTree node : flag.getChildNode()){
                if(data[i] == node.getData()){
                    flag = node;
                    break;
                }
            }
            if(i == data.length-1){
                return flag.getHandle();
            }
        }
        return null;
    }
    public static void traverse(RouteTree treeNode){
        if(treeNode.isLeaf()){
            return;
        }
        for (RouteTree node : treeNode.getChildNode()){
            System.out.print(node.getData() + "\t");
            traverse(node);
        }
    }
    public static RouteTree insert(RouteTree treeNode, String uri, Handle handle) {
        RouteTree flag = treeNode;
        char[] chars = uri.toCharArray();
        try {
            for (int i = 0; i < chars.length; i++) {
                flag = insertNode(flag.getChildNode(), chars[i]);
                if (i == chars.length - 1) {
                    flag.setHandle(handle);
                }
            }
        }catch (NullPointerException e){
            throw new RuntimeException("有空值");
        }
        return treeNode;
    }
}
