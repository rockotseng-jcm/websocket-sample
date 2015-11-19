package com.allegro;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PolicyMonitor {

    private static final Logger logger = LoggerFactory.getLogger(PolicyMonitor.class);
    
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    
    @AfterReturning("execution(* *..PolicyController.save(..)) && args(policyReferenceNumber,..)")
    public void afterPolicyIsUpdated(String policyReferenceNumber) {

        logger.info("After policy is updated");
        
        Notification notification = new Notification();
        notification.setContent("This policy has been updated!");

        messagingTemplate.convertAndSend("/topic/policies/" + policyReferenceNumber, notification);
    }

}
