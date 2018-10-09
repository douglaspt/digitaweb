package br.com.pch.digitaweb.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;

import br.com.pch.digitaweb.modelo.Internacao;
import br.com.pch.digitaweb.tx.Transacional;

public class InternacaoDao implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager em;
	
	public InternacaoDao() {
		
	}

	public InternacaoDao(EntityManager em) {
		this.em = em;
	}
	
	public void adiciona(Internacao i) {
		em.persist(i);		
	}

	@Transacional
	public void remove(Internacao i) {
		em.remove(em.merge(i));
	}
	
	public void atualiza(Internacao i) {
		em.merge(i);
	}
	
	public List<Internacao> listaTodos() {
		CriteriaQuery<Internacao> query = em.getCriteriaBuilder().createQuery(Internacao.class);
		query.select(query.from(Internacao.class));

		List<Internacao> lista = em.createQuery(query).setMaxResults(100).getResultList();
		return lista;
	}

	public Internacao buscaPorId(long id) {
		Internacao i = em.find(Internacao.class, id);
		return i;
	}
	
	

}
