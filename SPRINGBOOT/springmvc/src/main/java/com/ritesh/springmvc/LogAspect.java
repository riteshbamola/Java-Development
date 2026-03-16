package com.ritesh.springmvc;

import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;



@Aspect
@Component
public class LogAspect {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(LogAspect.class);

    @Before("execution(public * com.ritesh.springmvc.AlienRestController.getAliens())")
    public void logBefore(){
        LOGGER.info("getAliens method called");
    }
//    @After("execution(public * com.ritesh.springmvc.AlienRestController.getAliens())")   //default is called in final
//    public void logAfter(){
//        LOGGER.info("getAliens method Executed");
//    }

    @AfterReturning("execution(public * com.ritesh.springmvc.AlienRestController.getAliens())")   //called after returning
    public void logAfter(){
        LOGGER.info("getAliens method Executed");
    }

//    @AfterThrowing     //return after exception
}