package br.com.pch.digitaweb.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.pch.digitaweb.dao.PrestadorDao;
import br.com.pch.digitaweb.dao.ProfissionalDao;
import br.com.pch.digitaweb.dao.UsuarioDao;
import br.com.pch.digitaweb.modelo.Prestador;
import br.com.pch.digitaweb.modelo.Profissional;
import br.com.pch.digitaweb.modelo.Usuario;
import br.com.pch.digitaweb.tx.Log;
import br.com.pch.digitaweb.tx.Transacional;

@Named
@ViewScoped
public class UsuarioBean implements Serializable {

	private static final long serialVersionUID = 1L;

	// private Integer autorId;

	private Usuario usuario = new Usuario();
	private Usuario usuarioAlteracao = new Usuario();
	private List<Usuario> usuarios = new ArrayList<>();
	private String idProfissional = "";
	private String idPrestador = "";

	@Inject
	private UsuarioDao dao;
	
	@Inject
	private ProfissionalDao profissioanalDao;
	
	@Inject
	private PrestadorDao prestadorDao;

	@Inject
	FacesContext context;

	public List<Usuario> listar() {
		return usuarios;
	}

	@PostConstruct
	public void init() {
		usuarios = this.dao.listaTodos();
		usuarioAlteracao = (Usuario) context.getExternalContext().getSessionMap().get("usuarioLogado");
		usuarioAlteracao.setSenha("");
	}

	@Log
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	
	public boolean validarCampos() {
		
		boolean validado = true;
		
		Usuario usuarioNovo = dao.buscaPorLogin(this.usuario.getLogin());
		if (usuarioNovo != null){
			context.getExternalContext().getFlash().setKeepMessages(true);
			context.addMessage(null, new FacesMessage("Já Existe um Usuário com este Login"));
			validado = false;
			
		}
		
		Profissional profissional = profissioanalDao.buscaPorId(Integer.parseInt(idProfissional));
		this.usuario.setProfissional(profissional);
		if (profissional == null) {
			context.getExternalContext().getFlash().setKeepMessages(true);
			context.addMessage(null, new FacesMessage("Num. no Conselho Inválido"));
			validado = false;
		}
		
		Prestador prestador = prestadorDao.buscaPorId(Integer.parseInt(idPrestador));
		this.usuario.setPrestador(prestador);
		if (prestador == null) {
			context.getExternalContext().getFlash().setKeepMessages(true);
			context.addMessage(null, new FacesMessage("Conveniado Inválido"));
			validado = false;
		}
		
		return validado;
	}
	
	@Transacional
	public String alterar(){
		System.out.println("Alterando");
		dao.atualiza(usuarioAlteracao);
		return "principal?faces-redirect=true";
	}
	
	@Transacional
	public void gravar() {

		if (validarCampos()) {
			this.dao.adiciona(this.usuario);
			context.getExternalContext().getFlash().setKeepMessages(true);
			context.addMessage(null, new FacesMessage("Usuário Adicionado com Sucesso!"));
			this.usuario = new Usuario();
			idPrestador = "";
			idProfissional = "";
		}

		// return "livro?faces-redirect=true";
	}

	@Transacional
	public void remover(Usuario usuario) {
		this.dao.remove(usuario);
		this.usuarios = this.dao.listaTodos();
		// this.livros.remove(livro);
	}

	public void carregar(Usuario usuario) {
		this.usuario = usuario;
	}

	public void carregaPelaId() {

		String login = this.usuario.getLogin();
		this.usuario = this.dao.buscaPorLogin(login);
		if (this.usuario == null) {
			this.usuario = new Usuario();
		}

	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public String getIdProfissional() {
		return idProfissional;
	}

	public void setIdProfissional(String idProfissional) {
		this.idProfissional = idProfissional;
	}

	public String getIdPrestador() {
		return idPrestador;
	}

	public void setIdPrestador(String idPrestador) {
		this.idPrestador = idPrestador;
	}

	public Usuario getUsuarioAlteracao() {
		return usuarioAlteracao;
	}

	public void setUsuarioAlteracao(Usuario usuarioAlteracao) {
		this.usuarioAlteracao = usuarioAlteracao;
	}

}
