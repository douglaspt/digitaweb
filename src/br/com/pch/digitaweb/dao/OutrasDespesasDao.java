package br.com.pch.digitaweb.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import br.com.pch.digitaweb.modelo.OutrasDespesas;
import br.com.pch.digitaweb.tx.Transacional;

public class OutrasDespesasDao implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager em;
	
	public OutrasDespesasDao(){
		
	}

	public OutrasDespesasDao(EntityManager em) {
		this.em = em;
	}
	
	public void adiciona(OutrasDespesas o) {
		em.persist(o);
	}

	@Transacional
	public void remove(OutrasDespesas o) {
		em.remove(em.merge(o));
	}
	
	public void removerItens(List<OutrasDespesas> itens){
		for (OutrasDespesas i : itens) {
			em.remove(em.merge(i));
		}
	}
	
	public void atualiza(OutrasDespesas o) {
		em.merge(o);
	}
	
	public List<OutrasDespesas> listaTodos() {
		CriteriaQuery<OutrasDespesas> query = em.getCriteriaBuilder().createQuery(OutrasDespesas.class);
		query.select(query.from(OutrasDespesas.class));

		List<OutrasDespesas> lista = em.createQuery(query).setMaxResults(100).getResultList();
		return lista;
	}

	public OutrasDespesas buscaPorId(long id) {
		OutrasDespesas outrasDespesas = em.find(OutrasDespesas.class, id);
		return outrasDespesas;
	}
	
	public List<OutrasDespesas> buscaPorIdGuia(long idGuia){
		
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<OutrasDespesas> query = criteriaBuilder.createQuery(OutrasDespesas.class);
		Root<OutrasDespesas> root = query.from(OutrasDespesas.class);
		Path<Long> guiaPath = root.<Long> get("idGuia");
		query.where(criteriaBuilder.equal(guiaPath, idGuia));
		TypedQuery<OutrasDespesas> typedQuery = em.createQuery(query);
		try {
			return typedQuery.getResultList();
		} catch (NoResultException ex) {
			return null;

		}
		
	}

}
