package br.com.pch.digitaweb.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_tipoconsulta")
public class TipoConsulta {
	
	@Id
	@Column(name = "cod_tipoConsulta")
	private int id;
	
	@Column(name = "descricao_tpc")
	private String descricao;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}	

}
