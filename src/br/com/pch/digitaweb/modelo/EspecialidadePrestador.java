package br.com.pch.digitaweb.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="tbl_especialidadePrestador")
public class EspecialidadePrestador {
	
	@Id @Column(name="cod_especialidadePrestador")
	private int id;
	
	@OneToOne 
	@JoinColumn(name="cod_especialidade")
	//@ManyToOne @JoinColumn(name="cod_especialidade")
	private Especialidade especialidade;
	
	@OneToOne 
	@JoinColumn(name="cod_conveniado")
	private Prestador prestador;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Especialidade getEspecialidade() {
		return especialidade;
	}

	public void setEspecialidade(Especialidade especialidade) {
		this.especialidade = especialidade;
	}

	public Prestador getPrestador() {
		return prestador;
	}

	public void setPrestador(Prestador prestador) {
		this.prestador = prestador;
	}	

}
