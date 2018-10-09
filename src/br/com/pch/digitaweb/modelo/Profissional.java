package br.com.pch.digitaweb.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tbl_profissional")
public class Profissional {
	
	@Id 
	@Column(name="cod_profissional")
	private int id;
	
	@Column(name="cod_conselhoProfissional")
	private String conselhoProfissional;
	
	@Column(name="nome_pro")
	private String nome;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getConselhoProfissional() {
		return conselhoProfissional;
	}

	public void setConselhoProfissional(String conselhoProfissional) {
		this.conselhoProfissional = conselhoProfissional;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
}
