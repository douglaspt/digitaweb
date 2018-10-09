package br.com.pch.digitaweb.modelo;

import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "tbl_outrasDespesas")
public class OutrasDespesas {

	@Id
	@Column(name = "cod_guiaOutrasDespesas")
	private long id = (new GregorianCalendar().getTimeInMillis()+this.hashCode()/100000);
	
	@Column(name = "cod_guia")
	private long idGuia;

	@Column(name = "dtRealizacao_out")
	@Temporal(TemporalType.DATE)
	private Calendar dataRealizacao;
	
	@Column(name = "cod_despesa")
	private int codigoDespesa = 1;
	
	@Column(name = "cod_item")
	private int idItem;
	
	@Column(name = "cod_tabela")
	private int tabela = 1;
	
	@Column(name = "qtde_out")
	private int quantidade = 1;
	
	@Column(name = "vlTotal_out")
	private String valor;
	
	@Column(name = "descricao_out")
	private String descricao;
	
	@Column(name = "percentual_out")
	private int percentual = 100;
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getIdGuia() {
		return idGuia;
	}

	public void setIdGuia(long idGuia) {
		this.idGuia = idGuia;
	}

	public Calendar getDataRealizacao() {
		return dataRealizacao;
	}

	public void setDataRealizacao(Calendar dataRealizacao) {
		this.dataRealizacao = dataRealizacao;
	}

	public int getCodigoDespesa() {
		return codigoDespesa;
	}

	public void setCodigoDespesa(int codigoDespesa) {
		this.codigoDespesa = codigoDespesa;
	}


	public int getTabela() {
		return tabela;
	}

	public void setTabela(int tabela) {
		this.tabela = tabela;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}
	
	public Double getValorDouble(){
		return Double.parseDouble(getValor());
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public int getPercentual() {
		return percentual;
	}

	public void setPercentual(int percentual) {
		this.percentual = percentual;
	}

	public int getIdItem() {
		return idItem;
	}

	public void setIdItem(int idItem) {
		this.idItem = idItem;
	}
	

}
