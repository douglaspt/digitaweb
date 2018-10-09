package br.com.pch.digitaweb.teste;

import javax.persistence.EntityManager;

import br.com.pch.digitaweb.dao.JPAUtil;
import br.com.pch.digitaweb.dao.UsuarioDao;
import br.com.pch.digitaweb.modelo.Usuario;

public class TesteTempoConexao {

	public static void main(String[] args) throws InterruptedException {

		//EntityManagerFactory emf = Persistence.createEntityManagerFactory("digitaweb");

		//EntityManager em = emf.createEntityManager();
		
		EntityManager em = new JPAUtil().getEntityManager();
		
		UsuarioDao dao = new UsuarioDao(em);
		
		for (int i = 0; i < 3; i++) {
			
			Usuario u = dao.buscaPorLogin("douglas");
			
			System.out.println(u.getNome());
			
			Thread.sleep(20000);
			
		}

		
		System.out.println("FIMMMMMMMM");

		//em.close();

	}

}
