package br.com.autodb.util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EMFUtil {
	private static final String PERSISTENCE_UNIT_NAME = "br.com.autodb_autodb_war_1.0-SNAPSHOTPU";
    private static EntityManagerFactory factory = null;
    
    public static EntityManagerFactory getEMF() {
    	if (factory == null) {
    		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    	}
    	return factory;
    }    
}
