package org.ritesh;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
//@Primary  //primary Class to make object
public class MediaTek implements MobileProcessor{
    public void process(){
        System.out.println("MediaTek CPU");
    }
}
