package br.com.pch.digitaweb.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_item")
public class Item {
	
	@Id
	@Column(name = "cod_item")
	private int id;
	
	@Column(name = "nomeExtrato_item")
	private String nomeExtrato;
	
	@Column(name = "nome_item")
	private String nome;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNomeExtrato() {
		return nomeExtrato;
	}

	public void setNomeExtrato(String nomeExtrato) {
		this.nomeExtrato = nomeExtrato;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
