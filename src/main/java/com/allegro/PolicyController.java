package com.allegro;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
@MessageMapping("/policies")
public class PolicyController {

    @MessageMapping("/{policyReferenceNumber}")
    public void save(@DestinationVariable String policyReferenceNumber, PolicyDto message) throws Exception {
        
        Thread.sleep(1000); // do save
    }

}
