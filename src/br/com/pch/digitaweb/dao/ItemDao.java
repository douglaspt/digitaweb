package br.com.pch.digitaweb.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;

import br.com.pch.digitaweb.modelo.Item;

public class ItemDao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager em;

	public ItemDao(){
		
	}

	public ItemDao(EntityManager em) {
		this.em = em;
	}

	public void adiciona(Item i) {
		em.persist(i);
	}

	public void remove(Item i) {
		em.remove(em.merge(i));
	}

	public void atualiza(Item i) {
		em.merge(i);
	}

	public List<Item> listaTodos() {
		CriteriaQuery<Item> query = em.getCriteriaBuilder().createQuery(Item.class);
		query.select(query.from(Item.class));

		List<Item> lista = em.createQuery(query).setMaxResults(100).getResultList();
		return lista;
	}

	public Item buscaPorId(int id) {
		Item item = em.find(Item.class, id);
		return item;
	}

}
