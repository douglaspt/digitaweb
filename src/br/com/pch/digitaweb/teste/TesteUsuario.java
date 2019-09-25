package br.com.pch.digitaweb.teste;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.pch.digitaweb.dao.UsuarioDao;
import br.com.pch.digitaweb.modelo.Usuario;

public class TesteUsuario {

	public static void main(String[] args) {

		//EntityManager em = new JPAUtil().getEntityManager();
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("digitawebLocal");

		EntityManager em = emf.createEntityManager();
		
		Usuario usuario = new Usuario();
		usuario.setLogin("douglas");
		usuario.setSenha("12345");
		
		UsuarioDao dao = new UsuarioDao(em);
		
		System.out.println(dao.existe(usuario));
		
		/*
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Usuario> query = criteriaBuilder.createQuery(Usuario.class);
		Root<Usuario> root = query.from(Usuario.class);
		Path<String> login = root.<String> get("login");
		Path<String> senha = root.<String> get("senha");

		List<Predicate> predicates = new ArrayList<Predicate>();

		Predicate loginIgual = criteriaBuilder.equal(login, usuario.getLogin());
		predicates.add(loginIgual);

		Predicate senhaIgual = criteriaBuilder.equal(senha, usuario.getSenha());
		predicates.add(senhaIgual);

		query.where((Predicate[]) predicates.toArray(new Predicate[0]));

		TypedQuery<Usuario> typedQuery = em.createQuery(query);

	    Usuario resultado = typedQuery.getSingleResult();
	    
	    System.out.println(resultado.getEmail());
	    
*/
	}

}
