package br.com.pch.digitaweb.bean;

import java.io.IOException;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import br.com.pch.digitaweb.dao.ConsultaDao;
import br.com.pch.digitaweb.dao.ItemInternacaoDao;
import br.com.pch.digitaweb.dao.ItemSADTDao;
import br.com.pch.digitaweb.dao.LoteGuiaDao;
import br.com.pch.digitaweb.modelo.LoteGuia;
import br.com.pch.digitaweb.modelo.Prestador;
import br.com.pch.digitaweb.modelo.StatusLote;
import br.com.pch.digitaweb.modelo.TotalProcedimentos;
import br.com.pch.digitaweb.relatorio.GeradorRelatorio;
import br.com.pch.digitaweb.tx.Transacional;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Named
@ViewScoped
public class FechamentoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private int qtdeConsulta = 0;
	private Double totalConsulta = 0.0;
	private String totalConsultaFormatado = "";
	private int qtdeSadt = 0;
	private Double totalSadt = 0.0;
	private String totalSadtFormatado = "";
	private int qtdeInternacao = 0;
	private Double totalInternacao = 0.0;
	private String totalInternacaoFormatado = "";
	private Calendar referencia;
	private Prestador prestador;
	private List<TotalProcedimentos> consultas = new ArrayList<>();
	private List<TotalProcedimentos> listaSADT = new ArrayList<>();
	private List<TotalProcedimentos> listaInternacao = new ArrayList<>();

	private LoteGuia loteGuia = new LoteGuia();

	@Inject
	FacesContext context;

	@Inject
	private LoteGuiaDao loteGuiDao;

	@Inject
	private ConsultaDao consultaDao;

	@Inject
	private ItemSADTDao itemSADTDao;

	@Inject
	private ItemInternacaoDao itemInternacaoDao;

	@PostConstruct
	void init() {
		prestador = (Prestador) context.getExternalContext().getSessionMap().get("prestadorSessao");
		referencia = (Calendar) context.getExternalContext().getSessionMap().get("referencia");
		this.loteGuia = loteGuiDao.buscaPorReferenciaPrestador(prestador.getId(), referencia);
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
		this.consultas = consultaDao.buscaGuiaConsulta(prestador.getId(), referencia);
		this.listaSADT = itemSADTDao.buscaGuiaSADT(prestador.getId(), referencia);
		this.listaInternacao = itemInternacaoDao.buscaGuiaInternacao(prestador.getId(), referencia);
		calculaTotal();

	}

	public void calculaTotal() {
		Double valor;

		for (TotalProcedimentos consulta : consultas) {
			valor = consulta.getValor();
			this.totalConsulta += valor;
		}

		for (TotalProcedimentos sadt : listaSADT) {
			valor = sadt.getValor();
			this.totalSadt += valor;
		}

		for (TotalProcedimentos internacao : listaInternacao) {
			valor = internacao.getValor();
			this.totalInternacao += valor;
		}

		this.qtdeConsulta = consultas.size();
		this.qtdeSadt = listaSADT.size();
		this.qtdeInternacao = listaInternacao.size();
		totalConsultaFormatado = "R$ "+new DecimalFormat("#,###,##0.00").format(this.totalConsulta);
		totalSadtFormatado = "R$ "+new DecimalFormat("#,###,##0.00").format(this.totalSadt);
		totalInternacaoFormatado = "R$ "+new DecimalFormat("#,###,##0.00").format(this.totalInternacao);
	}

	@Transacional
	public void confirmar() throws JRException, IOException {
		if ((qtdeConsulta == 0) && (qtdeSadt == 0) && (qtdeInternacao == 0)) {
			context.getExternalContext().getFlash().setKeepMessages(true);
			context.addMessage(null, new FacesMessage("Necessário ter lançamentos para fechar o Lote!"));

		} else {
			StatusLote statusLote = new StatusLote();
			statusLote.setId(1);
			statusLote.setDescricao("Guia Fechada ( Enviada para o IMASF )");
			this.loteGuia.setStatus(statusLote);
			this.loteGuia.setDataFechamento(Calendar.getInstance());
			loteGuiDao.atualiza(this.loteGuia);
			this.loteGuia = loteGuiDao.buscaPorReferenciaPrestador(prestador.getId(), referencia);
		}
		// imprimir();
	}

	public void imprimir() throws JRException, IOException {

		if (consultas.isEmpty()) {
			TotalProcedimentos t1 = new TotalProcedimentos(0l, null, null, "SEM REGISTRO", 0.0);
			consultas.add(t1);
		}
		if (listaSADT.isEmpty()) {
			TotalProcedimentos t1 = new TotalProcedimentos(0l, null, null, "SEM REGISTRO", 0.0);
			listaSADT.add(t1);
		}
		if (listaInternacao.isEmpty()) {
			TotalProcedimentos t1 = new TotalProcedimentos(0l, null, null, "SEM REGISTRO", 0.0);
			listaInternacao.add(t1);
		}

		ServletContext scontext = (ServletContext) context.getExternalContext().getContext();
		HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
		response.setContentType("application/pdf");
		response.setHeader("Content-disposition", "filename=\"fechamento.pdf\"");
		String pathJasper = scontext.getRealPath("/jasper/");
		String pathPDF = scontext.getRealPath("/pdf/");

		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(consultas);
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("nomePrestador", prestador.getId() + " - " + prestador.getNome());
		parametros.put("idPrestador", prestador.getId());
		parametros.put("dtEmissao", referencia.getTime());
		parametros.put("qtdeConsulta", qtdeConsulta);
		parametros.put("totalConsulta", totalConsulta);
		parametros.put("qtdeSadt", qtdeSadt);
		parametros.put("totalSadt", totalSadt);
		parametros.put("qtdeInternacao", qtdeInternacao);
		parametros.put("totalInternacao", totalInternacao);
		parametros.put("qtdeGeral", qtdeConsulta + qtdeSadt + qtdeInternacao);
		parametros.put("totalGeral", totalConsulta + totalSadt + totalInternacao);
		parametros.put("SUBREPORT_DIR", pathJasper);
		parametros.put("lista2", listaSADT);
		parametros.put("lista3", listaInternacao);

		GeradorRelatorio gerador = new GeradorRelatorio(pathJasper + "fechamento.jasper", parametros, dataSource);
		gerador.geraPDFParaOutputStream(response.getOutputStream(), pathPDF);
		
		context.responseComplete();

	}

	public int getQtdeConsulta() {
		return qtdeConsulta;
	}

	public void setQtdeConsulta(int qtdeConsulta) {
		this.qtdeConsulta = qtdeConsulta;
	}

	public int getQtdeSadt() {
		return qtdeSadt;
	}

	public void setQtdeSadt(int qtdeSadt) {
		this.qtdeSadt = qtdeSadt;
	}

	public int getQtdeInternacao() {
		return qtdeInternacao;
	}

	public void setQtdeInternacao(int qtdeInternacao) {
		this.qtdeInternacao = qtdeInternacao;
	}

	public Double getTotalConsulta() {
		return totalConsulta;
	}

	public void setTotalConsulta(Double totalConsulta) {
		this.totalConsulta = totalConsulta;
	}

	public Double getTotalSadt() {
		return totalSadt;
	}

	public void setTotalSadt(Double totalSadt) {
		this.totalSadt = totalSadt;
	}

	public Double getTotalInternacao() {
		return totalInternacao;
	}

	public void setTotalInternacao(Double totalInternacao) {
		this.totalInternacao = totalInternacao;
	}

	public Calendar getReferencia() {
		return referencia;
	}

	public void setReferencia(Calendar referencia) {
		this.referencia = referencia;
	}

	public List<TotalProcedimentos> getConsultas() {
		return consultas;
	}

	public void setConsultas(List<TotalProcedimentos> consultas) {
		this.consultas = consultas;
	}

	public List<TotalProcedimentos> getListaSADT() {
		return listaSADT;
	}

	public void setListaSADT(List<TotalProcedimentos> listaSADT) {
		this.listaSADT = listaSADT;
	}

	public List<TotalProcedimentos> getListaInternacao() {
		return listaInternacao;
	}

	public void setListaInternacao(List<TotalProcedimentos> listaInternacao) {
		this.listaInternacao = listaInternacao;
	}

	public LoteGuia getLoteGuia() {
		return loteGuia;
	}

	public void setLoteGuia(LoteGuia loteGuia) {
		this.loteGuia = loteGuia;
	}

	public String getTotalConsultaFormatado() {
		return totalConsultaFormatado;
	}

	public void setTotalConsultaFormatado(String totalConsultaFormatado) {
		this.totalConsultaFormatado = totalConsultaFormatado;
	}

	public String getTotalSadtFormatado() {
		return totalSadtFormatado;
	}

	public void setTotalSadtFormatado(String totalSadtFormatado) {
		this.totalSadtFormatado = totalSadtFormatado;
	}

	public String getTotalInternacaoFormatado() {
		return totalInternacaoFormatado;
	}

	public void setTotalInternacaoFormatado(String totalInternacaoFormatado) {
		this.totalInternacaoFormatado = totalInternacaoFormatado;
	}
	

}
