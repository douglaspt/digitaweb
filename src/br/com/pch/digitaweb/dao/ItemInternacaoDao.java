package br.com.pch.digitaweb.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

import br.com.pch.digitaweb.modelo.GuiaInternacao;
import br.com.pch.digitaweb.modelo.Internacao;
import br.com.pch.digitaweb.modelo.ItemInternacao;
import br.com.pch.digitaweb.modelo.TotalProcedimentos;
import br.com.pch.digitaweb.tx.Transacional;

public class ItemInternacaoDao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager em;

	public ItemInternacaoDao() {

	}

	public ItemInternacaoDao(EntityManager em) {
		this.em = em;
	}

	public void adiciona(ItemInternacao i) {
		em.persist(i);
	}

	@Transacional
	public void remove(ItemInternacao i) {
		em.remove(em.merge(i));
	}

	public void removerItens(List<ItemInternacao> itens) {
		for (ItemInternacao i : itens) {
			em.remove(em.merge(i));
		}
	}

	public void atualiza(ItemInternacao i) {
		em.merge(i);
	}
	
	public List<ItemInternacao> buscaPorInternacao(long idInternacao){
		
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<ItemInternacao> query = criteriaBuilder.createQuery(ItemInternacao.class);
		Root<ItemInternacao> root = query.from(ItemInternacao.class);
		Path<Long> intPath = root.<Internacao> get("internacao").<Long> get("id");
		query.where(criteriaBuilder.equal(intPath, idInternacao));
		TypedQuery<ItemInternacao> typedQuery = em.createQuery(query);
		try {
			return typedQuery.getResultList();
		} catch (NoResultException ex) {
			return null;

		}
		
	}

	public List<TotalProcedimentos> buscaGuiaInternacao(int idPrestador, Calendar referencia) {

		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<TotalProcedimentos> query = criteriaBuilder.createQuery(TotalProcedimentos.class);
		Root<GuiaInternacao> item = query.from(GuiaInternacao.class);

		query.multiselect(item.<Long> get("id"), item.<Date> get("dataAtendimento"), item.<String> get("carteira"),
				item.<String> get("nome"), criteriaBuilder.sum(item.<Double> get("valor")));

		Path<Integer> prestadorPath = item.<Integer> get("idPrestador");
		Path<Calendar> referenciaPath = item.<Calendar> get("dataEmissao");

		List<Predicate> predicates = new ArrayList<Predicate>();

		Predicate prestadorIgual = criteriaBuilder.equal(prestadorPath, idPrestador);
		predicates.add(prestadorIgual);

		Predicate referenciaIgual = criteriaBuilder.equal(referenciaPath, referencia);
		predicates.add(referenciaIgual);

		query.where((Predicate[]) predicates.toArray(new Predicate[0]));

		query.groupBy(item.<Long> get("id"), item.<Date> get("dataAtendimento"), item.<String> get("carteira"),
				item.<String> get("nome"));
		TypedQuery<TotalProcedimentos> typedQuery = em.createQuery(query);
		try {
			return typedQuery.getResultList();
		} catch (NoResultException ex) {
			return null;

		}

	}

}
