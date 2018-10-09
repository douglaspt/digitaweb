package br.com.pch.digitaweb.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.pch.digitaweb.dao.LoteGuiaDao;
import br.com.pch.digitaweb.modelo.LoteGuia;
import br.com.pch.digitaweb.modelo.Meses;
import br.com.pch.digitaweb.tx.Transacional;

@Named
@ViewScoped
public class LoteGuiaBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private int mes;
	private int ano;
	private Meses[] meses = Meses.values();
	private Calendar referencia;
	private List<LoteGuia> guias = new ArrayList<>();
	
	@Inject
	private LoteGuiaDao dao;
	
	@Inject
	FacesContext context;
	
	@PostConstruct
	void init() {
		referencia = (Calendar) context.getExternalContext().getSessionMap().get("referencia");
		this.ano = referencia.get(Calendar.YEAR);
		this.mes = (referencia.get(Calendar.MONTH));
		atualizar();
	}
	
	public void atualizar() {
		Calendar data = Calendar.getInstance();
		data.set(ano, mes, 1);
		this.guias = this.dao.buscaPorReferencia(data);				
	}
	
	@Transacional
	public void remover(LoteGuia loteGuia) {
		this.dao.remove(loteGuia);
		atualizar();
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

	public List<LoteGuia> getGuias() {
		return guias;
	}

	public void setGuias(List<LoteGuia> guias) {
		this.guias = guias;
	}
	
}
