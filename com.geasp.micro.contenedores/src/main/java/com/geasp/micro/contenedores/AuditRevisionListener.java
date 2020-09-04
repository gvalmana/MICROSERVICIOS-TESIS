package com.geasp.micro.contenedores;

import org.hibernate.envers.RevisionListener;
import org.keycloak.representations.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;

import com.geasp.micro.contenedores.models.AuditRevisionEntity;

public class AuditRevisionListener implements RevisionListener{

	@Autowired
	private AccessToken token;
	
	@Override
	public void newRevision(Object revisionEntity) {
		// TODO Auto-generated method stub
        String currentUser = token.getPreferredUsername();

        AuditRevisionEntity audit = (AuditRevisionEntity) revisionEntity;
        audit.setUser(currentUser);
	}

}
