package br.com.pch.digitaweb.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tbl_aviso")
public class Aviso {
	
	@Id
	@Column(name="cod_aviso")
	private int id;
	
	@Column(name="chamado_avs")
	private String chamado;
	
	@Column(name="descricao_avs")
	private String descricao;
	
	@Column(name="link_avs")
	private String link;
	
	@Column(name="status_avs")
	private int status;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getChamado() {
		return chamado;
	}

	public void setChamado(String chamado) {
		this.chamado = chamado;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	

}
