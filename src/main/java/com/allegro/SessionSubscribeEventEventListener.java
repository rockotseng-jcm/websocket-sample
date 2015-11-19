package com.allegro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

@Component
public class SessionSubscribeEventEventListener implements ApplicationListener<SessionSubscribeEvent> {

    @Autowired
    CollaboratorManager collaboratorManager;

    @Override
    public void onApplicationEvent(SessionSubscribeEvent event) {

        MessageHeaders headers = event.getMessage().getHeaders();
        
        String subscriptionId = (String) headers.get("simpSubscriptionId");
        String sessionId = (String) headers.get("simpSessionId");
        String destination = (String) headers.get("simpDestination");
        
        Collaborator collaborator = new Collaborator();
        collaborator.setSubscriptionId(subscriptionId);
        collaborator.setSessionId(sessionId);
        collaborator.setDestination(destination);
        
        collaboratorManager.addCollaborator(collaborator);
    }

}
