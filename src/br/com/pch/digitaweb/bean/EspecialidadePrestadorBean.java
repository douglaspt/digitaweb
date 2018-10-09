package br.com.pch.digitaweb.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.pch.digitaweb.dao.EspecialidadeDao;
import br.com.pch.digitaweb.dao.EspecialidadePrestadorDao;
import br.com.pch.digitaweb.modelo.Especialidade;
import br.com.pch.digitaweb.modelo.EspecialidadePrestador;
import br.com.pch.digitaweb.modelo.Prestador;
import br.com.pch.digitaweb.tx.Transacional;

@Named
@ViewScoped
public class EspecialidadePrestadorBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private EspecialidadePrestador espPrestador = new EspecialidadePrestador();
	private List<EspecialidadePrestador> especialidadesPrestador = new ArrayList<>();
	private Prestador prestador;
	private int idEspecialidade;
	
	@Inject
	private EspecialidadePrestadorDao dao;
	
	@Inject
	private EspecialidadeDao espDao;
	
	@Inject
	FacesContext context;
	
	public List<EspecialidadePrestador> listar() {
		return especialidadesPrestador;
	}
	
	@PostConstruct
	public void init() {
		prestador = (Prestador) context.getExternalContext().getSessionMap().get("prestadorSessao");
		especialidadesPrestador = this.dao.buscaPorPrestador(prestador.getId());
	}
	
	@Transacional
	public void gravar() {
		Especialidade especialidade = espDao.buscaPorId(idEspecialidade);
		String id = String.valueOf(prestador.getId())+String.valueOf(especialidade.getId());
		espPrestador.setEspecialidade(especialidade);
		espPrestador.setPrestador(prestador);
		espPrestador.setId(Integer.parseInt(id));

		if (this.espPrestador == null) {
			this.dao.adiciona(this.espPrestador);
		} else {
			this.dao.atualiza(this.espPrestador);
		}

		this.espPrestador = new EspecialidadePrestador();
		especialidadesPrestador = this.dao.buscaPorPrestador(prestador.getId());
	}

	@Transacional
	public void remover(EspecialidadePrestador e) {
		this.dao.remove(e);
		especialidadesPrestador = this.dao.buscaPorPrestador(prestador.getId());
		
	}

	public EspecialidadePrestador getEspPrestador() {
		return espPrestador;
	}

	public void setEspPrestador(EspecialidadePrestador espPrestador) {
		this.espPrestador = espPrestador;
	}
	
	public List<EspecialidadePrestador> getEspecialidadesPrestador() {
		return especialidadesPrestador;
	}

	public void setEspecialidadesPrestador(List<EspecialidadePrestador> especialidadesPrestador) {
		this.especialidadesPrestador = especialidadesPrestador;
	}

	public List<Especialidade> getEspecialidades() {
		return espDao.listaTodos();
	}

	public int getIdEspecialidade() {
		return idEspecialidade;
	}

	public void setIdEspecialidade(int idEspecialidade) {
		this.idEspecialidade = idEspecialidade;
	}

}
