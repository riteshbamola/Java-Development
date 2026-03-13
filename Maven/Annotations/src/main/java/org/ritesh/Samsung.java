package org.ritesh;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class Samsung {
    @Autowired
    @Qualifier("snapdragon")   //can set the class to choose here
    private MobileProcessor processor;

    public MobileProcessor getProcessor() {
        return processor;
    }

    public void setProcessor(MobileProcessor processor) {
        this.processor = processor;
    }

    public void config(){
        System.out.println("4GB RAM 8 CORE CPU");
        processor.process();
    }
}
