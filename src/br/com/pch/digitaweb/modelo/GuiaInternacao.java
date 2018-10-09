package br.com.pch.digitaweb.modelo;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="vw_guiaInternacao")
public class GuiaInternacao {
	
	@Id @Column(name="cod_guiaInternacao")
	private long id;
	
	@Column(name = "dtEmissao_int")
	@Temporal(TemporalType.DATE)
	private Calendar dataEmissao;
	
	@Column(name="cod_conveniado")
	private int idPrestador;
	
	@Column(name = "dtAtendimento_int")
	@Temporal(TemporalType.DATE)
	private Date dataAtendimento;
	
	@Column(name = "numCarteira_int")
	private String carteira;
	
	@Column(name = "nomeBeneficiario_int")
	private String nome;
	
	@Column(name = "subTotal")
	private Double valor;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Calendar getDataEmissao() {
		return dataEmissao;
	}

	public void setDataEmissao(Calendar dataEmissao) {
		this.dataEmissao = dataEmissao;
	}

	public int getIdPrestador() {
		return idPrestador;
	}

	public void setIdPrestador(int idPrestador) {
		this.idPrestador = idPrestador;
	}

	public Date getDataAtendimento() {
		return dataAtendimento;
	}

	public void setDataAtendimento(Date dataAtendimento) {
		this.dataAtendimento = dataAtendimento;
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
