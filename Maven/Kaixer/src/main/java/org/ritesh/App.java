package org.ritesh;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App 
{
    public static void main( String[] args )
    {

        //Dependency Injection By Two ways in Spring
        //BeanFactory
        //Application Context  (superset)

        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");


        Vechicle obj = (Vechicle) context.getBean("car");
        obj.drive();


//        Tyre obj = (Tyre) context.getBean("tyre");
//        System.out.println(obj);
    }
}
