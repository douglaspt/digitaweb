package br.com.pch.digitaweb.bean;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.pch.digitaweb.dao.ItemSADTDao;
import br.com.pch.digitaweb.dao.LoteGuiaDao;
import br.com.pch.digitaweb.dao.OutrasDespesasDao;
import br.com.pch.digitaweb.dao.SADTDao;
import br.com.pch.digitaweb.modelo.ItemSADT;
import br.com.pch.digitaweb.modelo.LoteGuia;
import br.com.pch.digitaweb.modelo.Meses;
import br.com.pch.digitaweb.modelo.OutrasDespesas;
import br.com.pch.digitaweb.modelo.Prestador;
import br.com.pch.digitaweb.modelo.SADT;
import br.com.pch.digitaweb.modelo.StatusLote;
import br.com.pch.digitaweb.modelo.TotalProcedimentos;

@Named
@ViewScoped
public class GuiaSADTBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private int mes;
	private int ano;
	private Meses[] meses = Meses.values();
	private Prestador prestador;
	private Calendar referencia;
	private LoteGuia loteGuia = new LoteGuia();
	private List<TotalProcedimentos> listaSADT = new ArrayList<>();
	private int quantidade = 0;
	private Double valorTotal = 0.0;
	private String valorTotalFormatado = "";

	@Inject
	FacesContext context;

	@Inject
	private LoteGuiaDao loteGuiDao;

	@Inject
	private ItemSADTDao itemSADTDao;

	@Inject
	private SADTDao sadtDao;

	@Inject
	private OutrasDespesasDao outrasDespesasDao;


	@PostConstruct
	void init() {
		prestador = (Prestador) context.getExternalContext().getSessionMap().get("prestadorSessao");
		referencia = (Calendar) context.getExternalContext().getSessionMap().get("referencia");
		this.ano = referencia.get(Calendar.YEAR);
		this.mes = (referencia.get(Calendar.MONTH));
		if (loteGuia == null) {
			StatusLote status = new StatusLote();
			status.setId(0);
			status.setDescricao("Guia em aberto para digitação");
			loteGuia = new LoteGuia();
			loteGuia.setPrestador(prestador);
			loteGuia.setReferencia(referencia);
			loteGuia.setStatus(status);
			loteGuiDao.adiciona(loteGuia);
		}
		pesquisaTotalProcedimentos();
	}

	public void pesquisaTotalProcedimentos() {
		// Prestador prestador = (Prestador)
		// context.getExternalContext().getSessionMap().get("prestadorSessao");
		Calendar data = Calendar.getInstance();
		data.set(ano, mes, 1);
		this.loteGuia = loteGuiDao.buscaPorReferenciaPrestador(prestador.getId(), data);
		if (loteGuia == null) {
			StatusLote status = new StatusLote();
			status.setId(0);
			status.setDescricao("Guia em aberto para digitação");

			loteGuia = new LoteGuia();
			loteGuia.setPrestador(prestador);
			loteGuia.setReferencia(data);
			loteGuia.setStatus(status);
			loteGuiDao.adiciona(loteGuia);
		}
		this.listaSADT = itemSADTDao.buscaGuiaSADT(prestador.getId(), referencia);
		calculaTotal();
	}

	public void calculaTotal() {
		Double valor;
		this.valorTotal = 0.0;

		for (TotalProcedimentos t : listaSADT) {
			valor = t.getValor();
			this.valorTotal += valor;
		}
		this.quantidade = listaSADT.size();
		valorTotalFormatado = "R$ "+new DecimalFormat("#,###,##0.00").format(this.valorTotal);
	}
	
	// @Transacional
	public void remover(TotalProcedimentos totalProcedimentos) {
		SADT sadt = this.sadtDao.buscaPorId(totalProcedimentos.getId());
		List<OutrasDespesas> outrasDespesas = outrasDespesasDao.buscaPorIdGuia(sadt.getId());
		outrasDespesasDao.removerItens(outrasDespesas);

		List<ItemSADT> itens = itemSADTDao.buscaPorSADT(sadt.getId());
		itemSADTDao.removerItens(itens);

		this.sadtDao.remove(sadt);
		pesquisaTotalProcedimentos();

	}

	public int getMes() {
		return mes;
	}

	public void setMes(int mes) {
		this.mes = mes;
	}

	public int getAno() {
		return ano;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}

	public Meses[] getMeses() {
		return meses;
	}

	public void setMeses(Meses[] meses) {
		this.meses = meses;
	}

	public LoteGuia getLoteGuia() {
		return loteGuia;
	}

	public void setLoteGuia(LoteGuia loteGuia) {
		this.loteGuia = loteGuia;
	}

	public List<TotalProcedimentos> getListaSADT() {
		return listaSADT;
	}

	public void setListaSADT(List<TotalProcedimentos> listaSADT) {
		this.listaSADT = listaSADT;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public Double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
	}

	public String getValorTotalFormatado() {
		return valorTotalFormatado;
	}

	public void setValorTotalFormatado(String valorTotalFormatado) {
		this.valorTotalFormatado = valorTotalFormatado;
	}
	

}
