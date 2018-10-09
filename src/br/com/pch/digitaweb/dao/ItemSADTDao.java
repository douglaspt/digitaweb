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

import br.com.pch.digitaweb.modelo.GuiaSADT;
import br.com.pch.digitaweb.modelo.ItemSADT;
import br.com.pch.digitaweb.modelo.Prestador;
import br.com.pch.digitaweb.modelo.SADT;
import br.com.pch.digitaweb.modelo.TotalProcedimentos;
import br.com.pch.digitaweb.tx.Transacional;

public class ItemSADTDao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager em;

	public ItemSADTDao() {

	}

	public ItemSADTDao(EntityManager em) {
		this.em = em;
	}

	public void adiciona(ItemSADT s) {
		em.persist(s);
	}

	@Transacional
	public void remove(ItemSADT s) {
		em.remove(em.merge(s));
	}
	
	public void removerItens(List<ItemSADT> itens){
		for (ItemSADT i : itens) {
			em.remove(em.merge(i));
		}
	}

	public void atualiza(ItemSADT s) {
		em.merge(s);
	}

	public List<ItemSADT> listaTodos() {
		CriteriaQuery<ItemSADT> query = em.getCriteriaBuilder().createQuery(ItemSADT.class);
		query.select(query.from(ItemSADT.class));

		List<ItemSADT> lista = em.createQuery(query).setMaxResults(100).getResultList();
		return lista;
	}

	public ItemSADT buscaPorId(long id) {
		ItemSADT itemsadt = em.find(ItemSADT.class, id);
		return itemsadt;
	}

	public List<ItemSADT> buscaPorSADT(long idSadt){
		
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<ItemSADT> query = criteriaBuilder.createQuery(ItemSADT.class);
		Root<ItemSADT> root = query.from(ItemSADT.class);
		Path<Long> sadtPath = root.<SADT> get("sadt").<Long> get("id");
		query.where(criteriaBuilder.equal(sadtPath, idSadt));
		TypedQuery<ItemSADT> typedQuery = em.createQuery(query);
		try {
			return typedQuery.getResultList();
		} catch (NoResultException ex) {
			return null;

		}
		
	}
	
	public List<ItemSADT> buscaPorPrestadorReferencia(int idPrestador, Calendar referencia) {

		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<ItemSADT> query = criteriaBuilder.createQuery(ItemSADT.class);
		Root<ItemSADT> root = query.from(ItemSADT.class);

		Path<Integer> prestadorPath = root.<SADT> get("sadt").<Prestador> get("prestador").<Integer> get("id");
		Path<Calendar> referenciaPath = root.<SADT> get("sadt").<Calendar> get("dataEmissao");

		List<Predicate> predicates = new ArrayList<Predicate>();

		Predicate prestadorIgual = criteriaBuilder.equal(prestadorPath, idPrestador);
		predicates.add(prestadorIgual);

		Predicate referenciaIgual = criteriaBuilder.equal(referenciaPath, referencia);
		predicates.add(referenciaIgual);

		query.where((Predicate[]) predicates.toArray(new Predicate[0]));

		TypedQuery<ItemSADT> typedQuery = em.createQuery(query);
		try {
			return typedQuery.getResultList();
		} catch (NoResultException ex) {
			return null;

		}

	}

	public List<TotalProcedimentos> buscaTotalPorPrestadorReferencia(int idPrestador, Calendar referencia) {

		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<TotalProcedimentos> query = criteriaBuilder.createQuery(TotalProcedimentos.class);
		Root<ItemSADT> item = query.from(ItemSADT.class);
		// query.select(item, criteriaBuilder.sum(item.<Double> get("valor")));

		query.multiselect(item.<SADT> get("sadt").<Long> get("id"),
				item.<SADT> get("sadt").<Calendar> get("dataAtendimento"),
				item.<SADT> get("sadt").<String> get("carteira"), item.<SADT> get("sadt").<String> get("nome"),
				criteriaBuilder.sum(item.<Double> get("valor")));

		Path<Integer> prestadorPath = item.<SADT> get("sadt").<Prestador> get("prestador").<Integer> get("id");
		Path<Calendar> referenciaPath = item.<SADT> get("sadt").<Calendar> get("dataEmissao");

		List<Predicate> predicates = new ArrayList<Predicate>();

		Predicate prestadorIgual = criteriaBuilder.equal(prestadorPath, idPrestador);
		predicates.add(prestadorIgual);

		Predicate referenciaIgual = criteriaBuilder.equal(referenciaPath, referencia);
		predicates.add(referenciaIgual);

		query.where((Predicate[]) predicates.toArray(new Predicate[0]));

		query.groupBy(item.<SADT> get("sadt").<Long> get("id"),
				item.<SADT> get("sadt").<Calendar> get("dataAtendimento"),
				item.<SADT> get("sadt").<String> get("carteira"), item.<SADT> get("sadt").<String> get("nome"));
		TypedQuery<TotalProcedimentos> typedQuery = em.createQuery(query);
		try {
			return typedQuery.getResultList();
		} catch (NoResultException ex) {
			return null;

		}

	}
	
	public List<TotalProcedimentos> buscaGuiaSADT(int idPrestador, Calendar referencia) {
		
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<TotalProcedimentos> query = criteriaBuilder.createQuery(TotalProcedimentos.class);
		Root<GuiaSADT> item = query.from(GuiaSADT.class);
		// query.select(item, criteriaBuilder.sum(item.<Double> get("valor")));

		query.multiselect(item.<Long> get("id"),
				item.<Date> get("dataAtendimento"),
				item.<String> get("carteira"), item.<String> get("nome"),
				criteriaBuilder.sum(item.<Double> get("valor")));

		Path<Integer> prestadorPath = item.<Integer> get("idPrestador");
		Path<Calendar> referenciaPath = item.<Calendar> get("dataEmissao");

		List<Predicate> predicates = new ArrayList<Predicate>();

		Predicate prestadorIgual = criteriaBuilder.equal(prestadorPath, idPrestador);
		predicates.add(prestadorIgual);

		Predicate referenciaIgual = criteriaBuilder.equal(referenciaPath, referencia);
		predicates.add(referenciaIgual);

		query.where((Predicate[]) predicates.toArray(new Predicate[0]));

		query.groupBy(item.<Long> get("id"),
				item.<Date> get("dataAtendimento"),
				item.<String> get("carteira"), item.<String> get("nome"));
		TypedQuery<TotalProcedimentos> typedQuery = em.createQuery(query);
		try {
			return typedQuery.getResultList();
		} catch (NoResultException ex) {
			return null;

		}
		
	}
	
}
