package org.redrock.framework.util;

/*
* 字符串工具类
* */
public class StringUtil {
    /*
    * 判断字符串是否为空
    * */
    public static boolean isEmpty(String text){
        return text == null || " ".equals(text);
    }
    public static String append(String...array){
        StringBuilder sb = new StringBuilder();
        for(String str : array){
            sb.append(str);
        }
        return sb.toString();
    }
}
