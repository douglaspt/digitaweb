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

import br.com.pch.digitaweb.modelo.Beneficiario;

public class BeneficiarioDao implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager em;
	
	public BeneficiarioDao(){
		
	}

	public BeneficiarioDao(EntityManager manager) {
		this.em = manager;
	}
	
	public Beneficiario buscaPorId(Integer id) {
		Beneficiario b = em.find(Beneficiario.class, id);
		return b;
	}
	
	
	public Beneficiario buscaPorInscricaoTit(int inscricao, int titularidade) {
		
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Beneficiario> query = criteriaBuilder.createQuery(Beneficiario.class);
		Root<Beneficiario> root = query.from(Beneficiario.class);
		Path<Integer> inscricaoPath = root.<Integer> get("inscricao");
		Path<Integer> titularidadePath = root.<Integer> get("titularidade");

		List<Predicate> predicates = new ArrayList<Predicate>();

		Predicate inscricaoIgual = criteriaBuilder.equal(inscricaoPath, inscricao);
		predicates.add(inscricaoIgual);

		Predicate titularidadeIgual = criteriaBuilder.equal(titularidadePath, titularidade);
		predicates.add(titularidadeIgual);

		query.where((Predicate[]) predicates.toArray(new Predicate[0]));

		TypedQuery<Beneficiario> typedQuery = em.createQuery(query);
		try {
			return typedQuery.getSingleResult();			
		} catch (NoResultException ex) {
			return null;
			
		}

	}

}
