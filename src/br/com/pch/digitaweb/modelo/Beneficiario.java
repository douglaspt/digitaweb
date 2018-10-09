package br.com.pch.digitaweb.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tbl_beneficiario")
public class Beneficiario {
	
	@Id @Column(name="cod_beneficiario")
	private int id;
	
	@Column(name="carteira_ben")
	private String carteira;
	
	@Column(name="nome_ben")
	private String nome;
	
	@Column(name="nome_sit")
	private String situacao;
	
	@Column(name="nome_pla")
	private String plano;
	
	@Column(name="descricao_pla")
	private String descricaoPlano;
	
	@Column(name="inscricao_ben")
	private int inscricao;
	
	@Column(name="titulacartei_ben")
	private int titularidade;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCarteira() {
		return carteira;
	}

	public void setCarteira(String carteira) {
		this.carteira = carteira;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public String getPlano() {
		return plano;
	}

	public void setPlano(String plano) {
		this.plano = plano;
	}

	public String getDescricaoPlano() {
		return descricaoPlano;
	}

	public void setDescricaoPlano(String descricaoPlano) {
		this.descricaoPlano = descricaoPlano;
	}

	public int getInscricao() {
		return inscricao;
	}

	public void setInscricao(int inscricao) {
		this.inscricao = inscricao;
	}

	public int getTitularidade() {
		return titularidade;
	}

	public void setTitularidade(int titularidade) {
		this.titularidade = titularidade;
	}
	

}
