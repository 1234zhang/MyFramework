package org.redrock.framework.been;

import lombok.Data;
//返回的数据类
@Data
public class ResponseEntity<T>{
    //设置响应实体
    int code;
    String info;
    T t;
    public ResponseEntity(int code,String info){
        this.code = code;
        this.info = info;
    }
    public ResponseEntity(int code,String info,T t){
        this.code = code;
        this.info = info;
        this.t = t;
    }
}
