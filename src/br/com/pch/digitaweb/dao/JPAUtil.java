package br.com.pch.digitaweb.dao;

import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@ApplicationScoped
public class JPAUtil {
	
	private static EntityManagerFactory emf = Persistence
			.createEntityManagerFactory("digitaweb");

	@Produces
    @RequestScoped
	public EntityManager getEntityManager() {
		System.out.println("ABRIR CONNEX�O");
		return emf.createEntityManager();
		
	}

	public void close(@Disposes EntityManager em) {
		
		if (em.isOpen()) {
			System.out.println("FECHAR CONNEX�O");
            em.close();
        }
	}
	
	@PreDestroy
    public void preDestroy(){
        if (emf.isOpen()) {
        	System.out.println("FECHAR EMF");
            emf.close();
        }
    }

}
