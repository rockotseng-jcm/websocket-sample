package com.allegro;

import java.util.Collection;

public interface CollaboratorManager {

    void addCollaborator(Collaborator collaborator);
    
    Collaborator removeCollaborator(String subscriptionId, String sessionId);
    
    Collection<String> listCollaborators(String destination);
}
