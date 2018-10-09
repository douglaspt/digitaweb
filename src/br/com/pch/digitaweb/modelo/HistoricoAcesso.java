package br.com.pch.digitaweb.modelo;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="tbl_historicoAcesso")
public class HistoricoAcesso {
	
	@Id @Column(name="cod_historicoAcesso")
	private long id;
	
	@Column(name="dataHora_hac")
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar data;
	
	@OneToOne @JoinColumn(name="cod_conveniado")
	private Prestador prestador;
	
	@Column(name="login_usu")
	private String login;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Calendar getData() {
		return data;
	}

	public void setData(Calendar data) {
		this.data = data;
	}

	public Prestador getPrestador() {
		return prestador;
	}

	public void setPrestador(Prestador prestador) {
		this.prestador = prestador;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

}
