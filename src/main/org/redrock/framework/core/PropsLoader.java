package org.redrock.framework.core;

import org.redrock.framework.util.CastUtil;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/*
* 配置加载器
* */
public class PropsLoader {
    private static PropsLoader singleton;
    private Properties properties;
    public static final String PROPS_PATH = "/WEB-INF/application.properties";
    public static PropsLoader getInstance(){
        if(singleton != null){
            return singleton;
        }
        throw new RuntimeException("PropsLoader is null");
    }
    /*
    * 初始化配置
    * @param context Servlet的上下文文件
    * */
    public static void init(ServletContext context){
        if(singleton == null){
            synchronized (PropsLoader.class){
                if(singleton == null){
                    singleton = new PropsLoader(context);
                }
            }
        }
    }
    /*
    * 禁止无参构造函数
    * */
    public PropsLoader() throws IllegalAccessException{
        throw new IllegalAccessException();
    }
    public PropsLoader(ServletContext context){
        InputStream in = context.getResourceAsStream(PROPS_PATH);
        if( in != null){
            properties = new Properties();
            try {
                properties.load(in);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            throw new RuntimeException("the Properties file not found");
        }
    }
    public String getString(String key){
        String res;
        if(key != null){
            res = properties.getProperty(key);
            if(res != null){
                return res;
            }
            return "";
        }
        throw new RuntimeException("properties " + key + " not found");
    }
    public int getInt(String key){
        return CastUtil.castInt(getString(key),0);
    }
    public long getLong(String key){
        return CastUtil.castLong(getString(key),0);
    }
}
