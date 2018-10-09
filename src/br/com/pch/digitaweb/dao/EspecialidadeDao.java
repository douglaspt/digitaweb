package br.com.pch.digitaweb.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;

import br.com.pch.digitaweb.modelo.Especialidade;

public class EspecialidadeDao implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager em;
	
	public EspecialidadeDao(){
		
	}

	public EspecialidadeDao(EntityManager manager) {
		this.em = manager;
	}
	
	public Especialidade buscaPorId(Integer id) {
		Especialidade e = em.find(Especialidade.class, id);
		return e;
	}
	
	public List<Especialidade> listaTodos() {
		CriteriaQuery<Especialidade> query = em.getCriteriaBuilder().createQuery(Especialidade.class);
		query.select(query.from(Especialidade.class));
		
		return em.createQuery(query).getResultList();
	}
	
	public void adiciona(Especialidade e) {
		em.persist(e);		
	}
	
	public void atualiza(Especialidade e) {
		em.merge(e);
	}
	
	public void remove(Especialidade e) {
		System.out.println("Removendo "+e.getDescricao());
		em.remove(em.merge(e));
		
	}

}
