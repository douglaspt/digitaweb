package br.com.pch.digitaweb.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.pch.digitaweb.modelo.Usuario;

public class UsuarioDao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager em;
	
	public UsuarioDao(){
		
	}

	public UsuarioDao(EntityManager manager) {
		this.em = manager;
	}

	
	public void adiciona(Usuario u) {
		em.persist(u);		
	}

	public void remove(Usuario u) {
		System.out.println("Removendo");
		em.remove(em.merge(u));
	}

	public void atualiza(Usuario u) {
		em.merge(u);
	}
	
	public Usuario buscaPorLogin(String login) {
		try {
			return em.find(Usuario.class, login);
		} catch (Exception e) {
			return null;
		}
	}

	public List<Usuario> listaTodos() {
		CriteriaQuery<Usuario> query = em.getCriteriaBuilder().createQuery(Usuario.class);
		query.select(query.from(Usuario.class));
		
		return em.createQuery(query).getResultList();
	}
	
	public Usuario existe(Usuario usuario) {
		
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

		try {
			return typedQuery.getSingleResult();			
		} catch (NoResultException ex) {
			return null;
			
		}

	}
	
}
