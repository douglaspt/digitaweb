package br.com.pch.digitaweb.bean;

import java.io.Serializable;
import java.util.Calendar;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.pch.digitaweb.dao.AcessoDao;
import br.com.pch.digitaweb.dao.LoteGuiaDao;
import br.com.pch.digitaweb.dao.UsuarioDao;
import br.com.pch.digitaweb.modelo.Acesso;
import br.com.pch.digitaweb.modelo.Prestador;
import br.com.pch.digitaweb.modelo.Usuario;

@Named
@ViewScoped
public class LoginBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Usuario usuario = new Usuario();
	private Acesso acesso = new Acesso();
	private Prestador prestador = new Prestador();
	private Calendar referencia = Calendar.getInstance();
	
	@Inject
    UsuarioDao dao;
	
	@Inject 
	AcessoDao acessoDao;
	
	@Inject
	LoteGuiaDao loteGuiaDao;
	
	@Inject
    FacesContext context;

	public Usuario getUsuario() {
		return usuario;
	}

	public String efetuaLogin() {
		System.out.println("Fazendo login do usuário " + this.usuario.getLogin()+" - "+this.usuario.getSenha());
			
		this.usuario = dao.existe(this.usuario);
		
		if (this.usuario != null){
			context.getExternalContext().getSessionMap()
            .put("usuarioLogado", this.usuario);
			
			this.prestador = this.usuario.getPrestador();
			context.getExternalContext().getSessionMap()
            .put("prestadorSessao", this.prestador);
			//this.acesso
			this.acesso = acessoDao.buscaPorLogin(this.usuario.getLogin());
			
			context.getExternalContext().getSessionMap()
            .put("acessoLogin", this.acesso);
			
			this.referencia = loteGuiaDao.buscaUltimaReferenciaFechada(this.prestador.getId());
			if (this.referencia == null){
				this.referencia = Calendar.getInstance();
			}
			this.referencia.add(Calendar.MONTH, 1);
			context.getExternalContext().getSessionMap()
			.put("referencia", this.referencia);

			return "principal?faces-redirect=true";
		} 
		context.getExternalContext().getFlash().setKeepMessages(true);
		context.addMessage(null, new FacesMessage("Usuário não encontrado"));
		
		return "login?faces-redirect=true";
		
	}
	
	public String deslogar() {
	    context.getExternalContext().getSessionMap().remove("usuarioLogado");
	    context.getExternalContext().getSessionMap().remove("acessoLogin");
	    context.getExternalContext().getSessionMap().remove("prestadorSessao");
	    context.getExternalContext().getSessionMap().remove("referencia");

	    return "login?faces-redirect=true";
	}


}
