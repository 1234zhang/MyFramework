package org.redrock.framework.util;

//类型转换工具类
public class CastUtil {
    public static int castInt(String text){
        return castInt(text,0);
    }
    public static int castInt(String text, int define){
        int res;
        try{
            res = Integer.parseInt(text);
        }catch(NumberFormatException e){
            res = define;
        }
        return res;
    }
    public static long castLong(String text){
        return castLong(text, 0);
    }
    public static long castLong(String text, long define){
        long res;
        try{
            res = Long.parseLong(text);
        }catch(NumberFormatException e){
            res = define;
        }
        return res;
    }
}
