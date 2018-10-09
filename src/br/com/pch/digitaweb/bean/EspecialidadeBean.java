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
import br.com.pch.digitaweb.modelo.Especialidade;
import br.com.pch.digitaweb.tx.Transacional;

@Named
@ViewScoped
public class EspecialidadeBean implements Serializable {

	private static final long serialVersionUID = 1L;

	// private Integer autorId;

	private Especialidade especialidade = new Especialidade();
	private List<Especialidade> especialidades = new ArrayList<>();

	@Inject
	private EspecialidadeDao dao;

	@Inject
	FacesContext context;

	public List<Especialidade> listar() {
		return especialidades;
	}

	@PostConstruct
	public void init() {
		especialidades = this.dao.listaTodos();
	}

	@Transacional
	public void gravar() {

		if (this.especialidade == null) {
			this.dao.adiciona(this.especialidade);
		} else {
			this.dao.atualiza(this.especialidade);
		}

		this.especialidade = new Especialidade();
		especialidades = this.dao.listaTodos();
	}

	@Transacional
	public void remover(Especialidade e) {
		this.dao.remove(e);
		especialidades = this.dao.listaTodos();
		
	}

	public Especialidade getEspecialidade() {
		return especialidade;
	}

	public void setEspecialidade(Especialidade especialidade) {
		this.especialidade = especialidade;
	}

	public List<Especialidade> getEspecialidades() {
		return especialidades;
	}

	public void setEspecialidades(List<Especialidade> especialidades) {
		this.especialidades = especialidades;
	}

}
