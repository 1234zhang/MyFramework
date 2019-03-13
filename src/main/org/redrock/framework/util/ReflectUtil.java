package org.redrock.framework.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

//放射工具类
public class ReflectUtil {
    /*
    * 返回一个类的对象
    * 该类必须有一个无参构造器
    *
    * */
    public static Object newInstance(Class<?> clz){
        Object obj = null;
        try {
            obj = clz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return obj;
    }

    /*
    * 返回一个类的对象
    * 该类含有一个参数的构造对象
    * */
    public static Object newInstance(Class<?> clzz, String value){
        Object obj = null;
        try {
            Constructor constructor = clzz.getConstructor(String.class);

                obj = constructor.newInstance(value);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
            e.printStackTrace();
            }catch(NumberFormatException e){
            obj = -1;
        }
        return obj;
    }
    /*
    * 检验是否为包装类
    * */
    public static boolean isPrimitive(Class<?> clazz){
        if(!clazz.isPrimitive()){
            switch (clazz.getName()){
                case "java.lang.Integer":
                    return true;
                case "java.lang.Long":
                    return true;
                case "java.lang.Double":
                    return true;
                case "java.lang.Float":
                    return true;
                case "java.lang.Boolean":
                    return true;
                case "java.lang.Character":
                    return true;
                case "java.lang.String":
                    return true;
                    default:
                        return false;
            }
        }
        return true;
    }
    /*
    * 获得值类型的包装类
    * 如果传参不是值类型，则返回原类型
    * */
    public static Class<?> getNormalClass(Class<?> clazz){
        String classType = clazz.getName();
        switch(classType){
            case "int":
                return Integer.class;
            case "double":
                return Double.class;
            case "long":
                return Long.class;
            case "float":
                return Float.class;
            case "char":
                return Character.class;
            case "boolean":
                return Boolean.class;
            default:
                return clazz;
        }
    }
    /*
    *
    * 使用反射设置每个属性的值
    * */
    public static void setFieldValue(Field field, Object obj, Object value){
        if(!field.isAccessible()){
            field.setAccessible(true);
        }
        try {
            field.set(obj,value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
