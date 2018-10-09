package br.com.pch.digitaweb.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;

import br.com.pch.digitaweb.modelo.GrauParticipacao;
import br.com.pch.digitaweb.tx.Transacional;

public class GrauParticipacaoDao implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager em;
	
	public GrauParticipacaoDao(){
	
	}

	public GrauParticipacaoDao(EntityManager em) {
		this.em = em;
	}
	
	@Transacional
	public void adiciona(GrauParticipacao g) {
		em.persist(g);		
	}
	
	public GrauParticipacao buscaPorId(Integer id) {
		GrauParticipacao g = em.find(GrauParticipacao.class, id);
		return g;
	}
	
	public List<GrauParticipacao> listaTodos() {
		CriteriaQuery<GrauParticipacao> query = em.getCriteriaBuilder().createQuery(GrauParticipacao.class);
		query.select(query.from(GrauParticipacao.class));
		return em.createQuery(query).getResultList();
	}
	
	

}
