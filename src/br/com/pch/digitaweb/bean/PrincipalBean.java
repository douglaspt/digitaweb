package br.com.pch.digitaweb.bean;

import java.io.Serializable;
import java.util.Calendar;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.pch.digitaweb.dao.LoteGuiaDao;
import br.com.pch.digitaweb.modelo.LoteGuia;
import br.com.pch.digitaweb.modelo.Prestador;

@Named
@ViewScoped
public class PrincipalBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private LoteGuia loteGuia = new LoteGuia();
	private Calendar referencia = Calendar.getInstance();
	
	@Inject
	FacesContext context;

	@Inject
	private LoteGuiaDao loteGuiDao;

	@PostConstruct
	void init() {
		referencia = (Calendar) context.getExternalContext().getSessionMap().get("referencia");
		Prestador prestador = (Prestador) context.getExternalContext().getSessionMap().get("prestadorSessao");
		this.loteGuia = loteGuiDao.buscaPorReferenciaPrestador(prestador.getId(), referencia);
		
	}


	public LoteGuia getLoteGuia() {
		return loteGuia;
	}

	public void setLoteGuia(LoteGuia loteGuia) {
		this.loteGuia = loteGuia;
	}

	public Calendar getReferencia() {
		return referencia;
	}

	public void setReferencia(Calendar referencia) {
		this.referencia = referencia;
	}
	

}
