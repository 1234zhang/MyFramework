package org.redrock.framework.demo;

import org.redrock.framework.annotation.*;
import org.redrock.framework.been.FrameworkContext;
import org.redrock.framework.been.ResponseEntity;

import javax.servlet.annotation.WebServlet;

@Controller
@RequestMapper(value = "/test")
public class MainController {
    @AutoWired
    public MainService mainService;

    @RequestMapper(value = "/hello")
    public ResponseEntity hello(FrameworkContext context){
        return new ResponseEntity(10000,"123456");
    }
    @RequestMapper(value = "/world")
    public ResponseEntity world(FrameworkContext context){
        return new ResponseEntity(10000,"sdfasdf");
    }
    @RequestMapper(value = "/hello",method = HttpMethod.POST)
    public ResponseEntity helloWorld(FrameworkContext context){
        return mainService.test();
    }
    @RequestMapper(value = "/avc")
    public ResponseEntity avc(FrameworkContext context){
        return new ResponseEntity(10000,"sdf");
    }
}
