package br.com.pch.digitaweb.bean;

import java.io.Serializable;
import java.util.Calendar;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.pch.digitaweb.dao.LoteGuiaDao;
import br.com.pch.digitaweb.dao.PrestadorDao;
import br.com.pch.digitaweb.modelo.Prestador;

@Named
@ViewScoped
public class PrestadorBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int idPrestador;
	private Calendar referencia = Calendar.getInstance();
	
	private Prestador prestador = new Prestador();
	
	@Inject
    PrestadorDao dao;
	
	@Inject
	LoteGuiaDao loteGuiaDao;
	
	@Inject
    FacesContext context;

	public Prestador getPrestador() {
		return prestador;
	}

	public String alterar() {
		
		this.prestador = dao.buscaPorId(idPrestador);
		
		if (this.prestador != null){
			context.getExternalContext().getSessionMap()
            .put("prestadorSessao", this.prestador);
			
			this.referencia = loteGuiaDao.buscaUltimaReferenciaFechada(this.prestador.getId());
			if (this.referencia == null){
				this.referencia = Calendar.getInstance();
				this.referencia.add(Calendar.MONTH, -1);
			}
			this.referencia.add(Calendar.MONTH, 1);
			context.getExternalContext().getSessionMap()
			.put("referencia", this.referencia);	
			return "principal?faces-redirect=true";
		} 
		context.getExternalContext().getFlash().setKeepMessages(true);
		context.addMessage(null, new FacesMessage("Prestador não encontrado"));
		
		return "";
		
	}

	public int getIdPrestador() {
		return idPrestador;
	}

	public void setIdPrestador(int idPrestador) {
		this.idPrestador = idPrestador;
	}


}
