package com.allegro;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;

@Component
public class SessionConnectedEventListener implements ApplicationListener<SessionConnectedEvent> {

    private static final Logger logger = LoggerFactory.getLogger(SessionConnectedEventListener.class);
    
    @Override
    public void onApplicationEvent(SessionConnectedEvent event) {
        
        MessageHeaders headers = event.getMessage().getHeaders();
        String sessionId = (String) headers.get("simpSessionId");
        
        logger.info("Connection established with [{}]", sessionId);
    }

}
