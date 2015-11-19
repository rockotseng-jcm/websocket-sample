package com.allegro;

import java.util.Collection;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CollaboratorMonitor {

    private static final Logger logger = LoggerFactory.getLogger(CollaboratorMonitor.class);
    
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    
    @Autowired
    private CollaboratorManager collaboratorManager;
    
    @AfterReturning("execution(* *..CollaboratorManager.addCollaborator(..)) && args(collaborator)")
    public void afterAddCollaborator(Collaborator collaborator) {
        
        logger.info("After add collaborator");
        
        afterCollaboratorIsUpdated(collaborator);
    }

    @AfterReturning(pointcut = "execution(* *..CollaboratorManager.removeCollaborator(..))", returning="collaborator")
    public void afterRemoveCollaborator(Collaborator collaborator) {
        
        logger.info("After remove collaborator");
        
        afterCollaboratorIsUpdated(collaborator);
    }

    private void afterCollaboratorIsUpdated(Collaborator collaborator) {
        
        Collection<String> collaborators = collaboratorManager.listCollaborators(collaborator.getDestination());

        Notification notification = new Notification();
        notification.setContent(String.join(", ", collaborators));

        messagingTemplate.convertAndSend(collaborator.getDestination(), notification);
    }

}
