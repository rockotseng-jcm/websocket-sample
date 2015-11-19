package com.allegro;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RedisCollaboratorManager implements CollaboratorManager {

    @Autowired
    private StringRedisTemplate redisTemplate;
    
    @Transactional
    @Override
    public void addCollaborator(Collaborator collaborator) {
        
        String subscriptionId = collaborator.getSubscriptionId();
        String sessionId = collaborator.getSessionId();
        String destination = collaborator.getDestination();
        
        redisTemplate.opsForValue().set(sessionId + ":" + subscriptionId, destination);
        redisTemplate.opsForSet().add(destination, sessionId);

    }
    
    @Transactional
    @Override
    public Collaborator removeCollaborator(String subscriptionId, String sessionId) {
        
        String destination = redisTemplate.opsForValue().get(sessionId + ":" + subscriptionId);
        
        redisTemplate.opsForSet().remove(destination, sessionId);
        redisTemplate.delete(subscriptionId);
        
        Collaborator collaborator = new Collaborator();
        collaborator.setSubscriptionId(subscriptionId);
        collaborator.setSessionId(sessionId);
        collaborator.setDestination(destination);
        
        return collaborator;
    }

    @Override
    public Collection<String> listCollaborators(String destination) {
        return redisTemplate.opsForSet().members(destination);
    }

}
