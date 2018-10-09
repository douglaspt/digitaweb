package br.com.pch.digitaweb.modelo;

import java.util.Date;

public class TotalProcedimentos {
	
	private Long id;
	private Date dataRealizacao;
	private String carteira;
	private String nome;
	private Double valor;
	
	public TotalProcedimentos(Long id, Date dataRealizacao, String carteira, String nome, Double valor) {
		this.id = id;
		this.dataRealizacao = dataRealizacao;
		this.carteira = carteira;
		this.nome = nome;
		this.valor = valor;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDataRealizacao() {
		return dataRealizacao;
	}

	public void setDataRealizacao(Date dataRealizacao) {
		this.dataRealizacao = dataRealizacao;
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

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}	

}
