package org.ritesh;


import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;



public class App
{
    public static void main( String[] args )
    {
        ApplicationContext factory = new AnnotationConfigApplicationContext(AppConfig.class);

        Samsung obj = (Samsung) factory.getBean(Samsung.class);
        obj.config();

    }
}
