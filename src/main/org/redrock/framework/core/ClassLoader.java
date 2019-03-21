package org.redrock.framework.core;

import org.redrock.framework.util.StringUtil;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

/*
* 类加载器
* */
public class ClassLoader {
    /*
    * 单例
    * */
    private static ClassLoader singleton;
    /*
    * 包下面所有类的集合
    * */
    private static final String PROTOCOL_TYPE = "file";
    private static final String PACKAGE_NAME = "packageName";
    private static final String POINT = ".";
    private static final String END_WITH_CLASS = ".class";
    private static final String EMPTY_STRING = "";
    private Set<Class<?>> classSet;
    /*
    * 获得单例
    * */
    public static ClassLoader getInstance(){
        if(singleton == null){
            synchronized (ClassLoader.class){
                if(singleton == null){
                    singleton = new ClassLoader(PropsLoader.getInstance());
                }
            }
        }
        return singleton;
    }
    /*
    * 限制任何方式调用无参构造函数
    * */
    private ClassLoader() throws IllegalAccessException{
         throw new IllegalAccessException();
    }
    private ClassLoader(PropsLoader propsLoader){
        load(propsLoader);
    }
    /*
    * 包扫描器
    * */
    private void load(PropsLoader propsLoader){
        classSet = new HashSet<>();

        String packageName = propsLoader.getString(PACKAGE_NAME);

        try {
            //下面获取线程中的类加载器，并加载包名下的所有类
            Enumeration<URL> resource = Thread.currentThread().getContextClassLoader().getResources(packageName.replaceAll("\\.","/"));
            while(resource.hasMoreElements()){
                URL target = resource.nextElement();
                String protocol = target.getProtocol();
                if(protocol.equalsIgnoreCase(PROTOCOL_TYPE)){
                    String packagePath = target.getPath();
                    loadClass(packageName,packagePath);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Set<Class<?>> getClassSet(){
        return this.classSet;
    }
    private void loadClass(String packageName, String packagePath){
        //找到该包名下面的目录，以及字码文件.class
        File[] files = new File(packagePath).listFiles(file ->file.isDirectory()||file.getName().endsWith(END_WITH_CLASS));
        if(files != null){
            for(File file : files){
                String fileName = file.getName();
                //判断文件是不是目录文件
                if(file.isDirectory()){
                    //将这个目录文件与原包名拼接packageName.fileName
                    String subPackageName = packageName + POINT + fileName;
                    String subPackagePath = packageName + File.pathSeparator + fileName;
                    loadClass(subPackageName,subPackagePath);
                }else{
                    Class<?> clazz = null;
                    String className = StringUtil.append(packageName,POINT,fileName).replaceAll(END_WITH_CLASS,EMPTY_STRING);
                    try {
                        clazz = Class.forName(className);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    if(clazz!=null){
                        classSet.add(clazz);
                    }
                }
            }
        }
    }
    public Set<Class<?>> getClassSet(Class<?> clazz){
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        for (Class clz : this.classSet){
            if(clazz.isAssignableFrom(clz) && !clazz.equals(clz)){
                classSet.add(clz);
            }
        }
        return classSet;
    }
    public Set<Class<?>> getClassSetByAnnotation(Class<? extends Annotation> classAnnotation){
        Set<Class<?>> classSetAnnotation = new HashSet<>();
        for (Class<?> clazz : this.classSet){
            if(clazz.isAnnotationPresent(classAnnotation)){
                classSetAnnotation.add(clazz);
            }
        }
        return classSetAnnotation;
    }
}
