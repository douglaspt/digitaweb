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

import br.com.pch.digitaweb.modelo.Consulta;
import br.com.pch.digitaweb.modelo.Prestador;
import br.com.pch.digitaweb.modelo.TotalProcedimentos;

public class ConsultaDao implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager em;
	
	public ConsultaDao() {
		
	}

	public ConsultaDao(EntityManager em) {
		this.em = em;
	}
	
	public void adiciona(Consulta c) {
		System.out.println("Adicionar Consulta");
		em.persist(c);		
	}

	public void remove(Consulta c) {
		System.out.println("Removendo");
		em.remove(em.merge(c));
	}

	public void atualiza(Consulta c) {
		System.out.println("Atualiza Consulta");
		em.merge(c);
	}

	public List<Consulta> listaTodos() {
		CriteriaQuery<Consulta> query = em.getCriteriaBuilder().createQuery(Consulta.class);
		query.select(query.from(Consulta.class));

		List<Consulta> lista = em.createQuery(query).setMaxResults(100).getResultList();
		return lista;
	}

	public Consulta buscaPorId(long id) {
		Consulta consulta = em.find(Consulta.class, id);
		return consulta;
	}
	
	public List<Consulta> buscaPorPrestadorReferencia(int idPrestador, Calendar referencia) {
		
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Consulta> query = criteriaBuilder.createQuery(Consulta.class);
		Root<Consulta> root = query.from(Consulta.class);

		Path<Integer> prestadorPath = root.<Prestador> get("prestador").<Integer> get("id");
		Path<Calendar> referenciaPath = root.<Calendar> get("dataEmissao");

		List<Predicate> predicates = new ArrayList<Predicate>();

		Predicate prestadorIgual = criteriaBuilder.equal(prestadorPath, idPrestador);
		predicates.add(prestadorIgual);

		Predicate referenciaIgual = criteriaBuilder.equal(referenciaPath, referencia);
		predicates.add(referenciaIgual);

		query.where((Predicate[]) predicates.toArray(new Predicate[0]));

		TypedQuery<Consulta> typedQuery = em.createQuery(query);
		try {
			return typedQuery.getResultList();			
		} catch (NoResultException ex) {
			return null;
			
		}

	}
	
public List<TotalProcedimentos> buscaGuiaConsulta(int idPrestador, Calendar referencia) {
		
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<TotalProcedimentos> query = criteriaBuilder.createQuery(TotalProcedimentos.class);
		Root<Consulta> item = query.from(Consulta.class);
		query.multiselect(item.<Long> get("id"),
				item.<Date> get("dataAtendimento"),
				item.<String> get("carteira"), item.<String> get("nome"),
				item.<Double> get("valor"));			

		Path<Integer> prestadorPath = item.<Prestador> get("prestador").<Integer> get("id");
		Path<Calendar> referenciaPath = item.<Calendar> get("dataEmissao");

		List<Predicate> predicates = new ArrayList<Predicate>();

		Predicate prestadorIgual = criteriaBuilder.equal(prestadorPath, idPrestador);
		predicates.add(prestadorIgual);

		Predicate referenciaIgual = criteriaBuilder.equal(referenciaPath, referencia);
		predicates.add(referenciaIgual);

		query.where((Predicate[]) predicates.toArray(new Predicate[0]));
		TypedQuery<TotalProcedimentos> typedQuery = em.createQuery(query);
		try {
			return typedQuery.getResultList();
		} catch (NoResultException ex) {
			return null;

		}
	}
	


}
