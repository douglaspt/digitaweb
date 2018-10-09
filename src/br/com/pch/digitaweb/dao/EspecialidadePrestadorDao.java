package br.com.pch.digitaweb.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.pch.digitaweb.modelo.Especialidade;
import br.com.pch.digitaweb.modelo.EspecialidadePrestador;
import br.com.pch.digitaweb.modelo.Prestador;

public class EspecialidadePrestadorDao implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager em;
	
	public EspecialidadePrestadorDao(){
		
	}

	public EspecialidadePrestadorDao(EntityManager manager) {
		this.em = manager;
	}
	
	public void adiciona(EspecialidadePrestador e) {
		em.persist(e);		
	}
	
	public void atualiza(EspecialidadePrestador e) {
		em.merge(e);
	}
	
	public void remove(EspecialidadePrestador e) {
		em.remove(em.merge(e));
		
	}
	
	public EspecialidadePrestador buscaPorId(Integer id) {
		EspecialidadePrestador e = em.find(EspecialidadePrestador.class, id);
		return e;
	}
	
	public List<EspecialidadePrestador> buscaPorPrestador(int idPrestador) {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<EspecialidadePrestador> query = criteriaBuilder.createQuery(EspecialidadePrestador.class);
		Root<EspecialidadePrestador> root = query.from(EspecialidadePrestador.class);
		Path<Integer> prestadorPath = root.<Prestador> get("prestador").<Integer> get("id");
		Predicate prestadorIgual = criteriaBuilder.equal(prestadorPath, idPrestador);
		query.where(prestadorIgual);
		query.orderBy(criteriaBuilder.asc(root.<Especialidade> get("especialidade").<String> get("descricao")));
		
		return em.createQuery(query).getResultList();
	}
	
	

}
