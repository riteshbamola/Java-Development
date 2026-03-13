package org.ritesh;

import org.springframework.stereotype.Component;

@Component
public class Bike implements Vechicle {
    @Override
    public void drive(){
        System.out.println("Driving Bike");
    }
}
