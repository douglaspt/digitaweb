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
@Table(name = "tbl_itemsadt")
public class ItemSADT {

	@Id
	@Column(name = "cod_guiaItemSADT")
	private long id = (new GregorianCalendar().getTimeInMillis()+this.hashCode()/100000);
	
	@OneToOne
	@JoinColumn(name = "cod_guiaSADT")
	private SADT sadt;

	@Column(name = "dtRealizacao_its")
	@Temporal(TemporalType.DATE)
	private Calendar dataRealizacao;
	
	@Column(name = "cod_tabela")
	private int tabela = 1;
	
	@OneToOne
	@JoinColumn(name = "cod_item")
	private Item item;
	
	@Column(name = "qtde_its")
	private int quantidade = 1;
	
	@Column(name = "viaAcesso_its", columnDefinition = "char(1)")
	private String viaAcesso = "U";
	
	@Column(name = "tecnicaUtilizada_its", columnDefinition = "char(1)")
	private String tecnicaUtilizada = "C";
	
	@Column(name = "percentual_its")
	private int percentual = 100;
	
	@Column(name = "vlTotal_its")
	private String valor;
	
	@Column(name = "descricao_its")
	private String descricao;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public SADT getSadt() {
		return sadt;
	}

	public void setSadt(SADT sadt) {
		this.sadt = sadt;
	}

	public Calendar getDataRealizacao() {
		return dataRealizacao;
	}

	public void setDataRealizacao(Calendar dataRealizacao) {
		this.dataRealizacao = dataRealizacao;
	}

	public int getTabela() {
		return tabela;
	}

	public void setTabela(int tabela) {
		this.tabela = tabela;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public String getViaAcesso() {
		return viaAcesso;
	}

	public void setViaAcesso(String viaAcesso) {
		this.viaAcesso = viaAcesso;
	}

	public String getTecnicaUtilizada() {
		return tecnicaUtilizada;
	}

	public void setTecnicaUtilizada(String tecnicaUtilizada) {
		this.tecnicaUtilizada = tecnicaUtilizada;
	}

	public int getPercentual() {
		return percentual;
	}

	public void setPercentual(int percentual) {
		this.percentual = percentual;
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

	

}
