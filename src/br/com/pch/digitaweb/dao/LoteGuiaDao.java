package br.com.pch.digitaweb.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
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

import br.com.pch.digitaweb.modelo.LoteGuia;
import br.com.pch.digitaweb.modelo.Prestador;

public class LoteGuiaDao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager em;

	public LoteGuiaDao() {
	}

	public LoteGuiaDao(EntityManager em) {
		this.em = em;
	}

	public void adiciona(LoteGuia l) {
		System.out.println("Adicionando Lote : "+l.getStatus().getDescricao());
		em.getTransaction().begin();
		em.persist(l);
		em.getTransaction().commit();
	}

	public void remove(LoteGuia l) {
		em.remove(em.merge(l));
	}

	public void atualiza(LoteGuia l) {
		System.out.println("Atualizando Lote : "+l.getStatus().getDescricao());
		em.merge(l);
	}

	public LoteGuia buscaPorId(Integer id) {
		LoteGuia loteGuia = em.find(LoteGuia.class, id);
		return loteGuia;
	}

	public LoteGuia buscaPorReferenciaPrestador(int idPrestador, Calendar referencia) {

		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<LoteGuia> query = criteriaBuilder.createQuery(LoteGuia.class);
		Root<LoteGuia> root = query.from(LoteGuia.class);
		Path<Integer> prestadorPath = root.<Prestador> get("prestador").<Integer> get("id");
		Path<Calendar> referenciaPath = root.<Calendar> get("referencia");

		List<Predicate> predicates = new ArrayList<Predicate>();

		Predicate prestadorIgual = criteriaBuilder.equal(prestadorPath, idPrestador);
		predicates.add(prestadorIgual);

		Predicate referenciaIgual = criteriaBuilder.equal(referenciaPath, referencia);
		predicates.add(referenciaIgual);

		query.where((Predicate[]) predicates.toArray(new Predicate[0]));

		TypedQuery<LoteGuia> typedQuery = em.createQuery(query).setMaxResults(1);
		try {
			return typedQuery.getSingleResult();
		} catch (NoResultException ex) {
			return null;
		}
	}

	public Calendar buscaUltimaReferenciaFechada(int idPrestador) {

		String jpql = "SELECT MAX(l.referencia) FROM LoteGuia l" + " where l.prestador.id = :pIdPrestador"
				+ " and  cod_statusLoteguia in (1,2)";

		TypedQuery<Calendar> query = em.createQuery(jpql, Calendar.class);
		query.setParameter("pIdPrestador", idPrestador);
		return query.getSingleResult();

	}
	
	public List<LoteGuia> buscaPorReferencia(Calendar referencia){
		
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<LoteGuia> query = criteriaBuilder.createQuery(LoteGuia.class);
		Root<LoteGuia> root = query.from(LoteGuia.class);
		Path<Calendar> referenciaPath = root.<Calendar> get("referencia");
		Predicate referenciaIgual = criteriaBuilder.equal(referenciaPath, referencia);
		query.where(referenciaIgual);
		query.orderBy(criteriaBuilder.asc(root.<Prestador> get("prestador").<Integer> get("id")));
		
		TypedQuery<LoteGuia> typedQuery = em.createQuery(query);
		try {
			return typedQuery.getResultList();			
		} catch (NoResultException ex) {
			return null;
			
		}	
		
	}

}
