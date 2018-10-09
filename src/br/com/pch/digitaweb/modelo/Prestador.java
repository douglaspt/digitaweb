package br.com.pch.digitaweb.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tbl_prestador")
public class Prestador {
	
	@Id @Column(name="cod_prestador")
	private int id;
	
	@Column(name="nome_pre")
	private String nome;
	
	@Column(name="razao_pre")
	private String razaoSocial;
	
	@Column(name="cod_cnes")
	private String cnes;
	
	@Column(name="cod_logradouro")
	private String idLogradouro;
	
	@Column(name="logradouro_pre")
	private String logradouro;
	
	@Column(name="numero_pre")
	private String numero;
	
	@Column(name="complemento_pre")
	private String complemento;
	
	@Column(name="municipio_pre")
	private String municipio;
	
	@Column(name="uf_pre")
	private String uf;
	
	@Column(name="cod_municipioIBGE")
	private String idMunicipio;
	
	@Column(name="cod_cep")
	private String cep;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getCnes() {
		return cnes;
	}

	public void setCnes(String cnes) {
		this.cnes = cnes;
	}

	public String getIdLogradouro() {
		return idLogradouro;
	}

	public void setIdLogradouro(String idLogradouro) {
		this.idLogradouro = idLogradouro;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getIdMunicipio() {
		return idMunicipio;
	}

	public void setIdMunicipio(String idMunicipio) {
		this.idMunicipio = idMunicipio;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}
	

}
