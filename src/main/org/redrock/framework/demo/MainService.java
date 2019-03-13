package org.redrock.framework.demo;

import org.redrock.framework.annotation.Component;
import org.redrock.framework.been.ResponseEntity;

@Component
public class MainService {
    public ResponseEntity test(){
        return new ResponseEntity(10000,"hello world");
    }
}
