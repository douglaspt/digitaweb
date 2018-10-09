package br.com.pch.digitaweb.bean;

import java.io.Serializable;
import java.util.Calendar;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.pch.digitaweb.modelo.Meses;

@Named
@ViewScoped
public class MenuBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int mes = Calendar.getInstance().get(Calendar.MONTH);
	private int ano = Calendar.getInstance().get(Calendar.YEAR);
	private Meses[] meses = Meses.values();
	private Calendar referencia = Calendar.getInstance();
	
	@Inject
    FacesContext context;
	
	public String alterarMesAno(){
		referencia.set(ano, mes, 1);
		context.getExternalContext().getSessionMap()
		.put("referencia", referencia);
		return "principal?faces-redirect=true";
		
	}

		
	public String alterarReferencia(int mes){
		referencia = (Calendar) context.getExternalContext().getSessionMap().get("referencia");
		referencia.set(referencia.get(Calendar.YEAR), mes, 1);
		context.getExternalContext().getSessionMap()
		.put("referencia", referencia);
		System.out.println("Referencia : "+referencia.getTime());
		String viewId = context.getViewRoot().getViewId();
		viewId = viewId.replace("/", "").replace(".xhtml", "");
		
		return viewId+"?faces-redirect=true";
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
	
	

}
