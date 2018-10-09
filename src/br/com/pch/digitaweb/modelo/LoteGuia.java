package br.com.pch.digitaweb.modelo;

import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="tbl_loteguia")
public class LoteGuia {
	
	@Id @Column(name="cod_loteGuia")
	private long id = (new GregorianCalendar().getTimeInMillis()+this.hashCode()/100000);
	
	@Column(name="dtReferencia_ltg")
	@Temporal(TemporalType.DATE)
	private Calendar referencia;
	
	@OneToOne @JoinColumn(name="cod_prestador")
	private Prestador prestador;
	
	@Column(name="dtFechamento_ltg")
	@Temporal(TemporalType.DATE)
	private Calendar dataFechamento = Calendar.getInstance();
	
	@OneToOne @JoinColumn(name="cod_statusLoteGuia")
	private StatusLote status;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}


	public Calendar getReferencia() {
		return referencia;
	}

	public void setReferencia(Calendar referencia) {
		this.referencia = referencia;
	}

	public Prestador getPrestador() {
		return prestador;
	}

	public void setPrestador(Prestador prestador) {
		this.prestador = prestador;
	}

	public Calendar getDataFechamento() {
		return dataFechamento;
	}

	public void setDataFechamento(Calendar dataFechamento) {
		this.dataFechamento = dataFechamento;
	}

	public StatusLote getStatus() {
		return status;
	}

	public void setStatus(StatusLote status) {
		this.status = status;
	}
	

}
