package org.ritesh;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "org.ritesh")
public class AppConfig {

// no need to define beans just use @Component in classes - automates this task
//    @Bean
//    public Samsung getPhone(){
//        return new Samsung();
//    }
//
//    @Bean
//    public  MobileProcessor getProcessor(){
//        return new Snapdragon();
//    }

}
