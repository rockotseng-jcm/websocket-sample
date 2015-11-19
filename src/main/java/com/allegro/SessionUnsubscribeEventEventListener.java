package com.allegro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;

@Component
public class SessionUnsubscribeEventEventListener implements ApplicationListener<SessionUnsubscribeEvent> {

    @Autowired
    CollaboratorManager collaboratorManager;
    
    @Override
    public void onApplicationEvent(SessionUnsubscribeEvent event) {
        
        MessageHeaders headers = event.getMessage().getHeaders();
        
        String subscriptionId = (String) headers.get("simpSubscriptionId");
        String sessionId = (String) headers.get("simpSessionId");
        
        collaboratorManager.removeCollaborator(subscriptionId, sessionId);
    }

}
