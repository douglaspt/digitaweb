package br.com.pch.digitaweb.dao;

import java.io.Serializable;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.pch.digitaweb.modelo.Acesso;

public class AcessoDao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager em;
	
	public AcessoDao(){
		
	}

	public AcessoDao(EntityManager manager) {
		this.em = manager;
	}

	public Acesso buscaPorLogin(String login) {
		try {
			return em.find(Acesso.class, login);
		} catch (Exception e) {
			return null;
		}
	}

}
