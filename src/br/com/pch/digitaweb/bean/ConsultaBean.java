package br.com.pch.digitaweb.bean;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.pch.digitaweb.dao.BeneficiarioDao;
import br.com.pch.digitaweb.dao.CIDDao;
import br.com.pch.digitaweb.dao.ConsultaDao;
import br.com.pch.digitaweb.dao.EspecialidadeDao;
import br.com.pch.digitaweb.dao.EspecialidadePrestadorDao;
import br.com.pch.digitaweb.dao.LoteGuiaDao;
import br.com.pch.digitaweb.dao.ProfissionalDao;
import br.com.pch.digitaweb.dao.TipoConsultaDao;
import br.com.pch.digitaweb.dao.TipoSaidaDao;
import br.com.pch.digitaweb.modelo.Beneficiario;
import br.com.pch.digitaweb.modelo.CID;
import br.com.pch.digitaweb.modelo.Consulta;
import br.com.pch.digitaweb.modelo.Especialidade;
import br.com.pch.digitaweb.modelo.EspecialidadePrestador;
import br.com.pch.digitaweb.modelo.LoteGuia;
import br.com.pch.digitaweb.modelo.Meses;
import br.com.pch.digitaweb.modelo.Prestador;
import br.com.pch.digitaweb.modelo.Profissional;
import br.com.pch.digitaweb.modelo.StatusLote;
import br.com.pch.digitaweb.modelo.TipoConsulta;
import br.com.pch.digitaweb.modelo.TipoSaida;
import br.com.pch.digitaweb.modelo.Usuario;
import br.com.pch.digitaweb.tx.Transacional;

@Named
@ViewScoped
public class ConsultaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private int mes;
	private int ano;
	private Meses[] meses = Meses.values();
	private int idProfissional;
	private int idEspecialidade;
	private int idTipoConsulta;
	private int idTipoSaida;
	private String idCid = "000";
	private Usuario usuario = new Usuario();
	private Prestador prestador;
	private Calendar referencia;
	private int quantidade = 0;
	private Double valorTotal = 0.0;
	private String valorTotalFormatado = "";

	private Consulta consulta = new Consulta();
	private LoteGuia loteGuia = new LoteGuia();
	private List<Consulta> consultas = new ArrayList<>();

	@Inject
	private ConsultaDao dao;

	@Inject
	private LoteGuiaDao loteGuiDao;

	@Inject
	private ProfissionalDao profissioanalDao;

	@Inject
	private EspecialidadeDao especialidadeDao;

	@Inject
	private TipoConsultaDao tipoConsultaDao;

	@Inject
	private TipoSaidaDao tipoSaidaDao;

	@Inject
	private BeneficiarioDao beneficiarioDao;

	@Inject
	private CIDDao cidDao;

	@Inject
	private EspecialidadePrestadorDao especialidadePrestadorDao;

	@Inject
	FacesContext context;

	@PostConstruct
	void init() {
		prestador = (Prestador) context.getExternalContext().getSessionMap().get("prestadorSessao");
		referencia = (Calendar) context.getExternalContext().getSessionMap().get("referencia");
		this.ano = referencia.get(Calendar.YEAR);
		this.mes = (referencia.get(Calendar.MONTH));
		System.out.println("ConsultaBean está nascendo ...." + this.mes);
		usuario = (Usuario) context.getExternalContext().getSessionMap().get("usuarioLogado");
		idProfissional = usuario.getProfissional().getId();
		atualizar();
	}

	@PreDestroy
	void morte() {
		System.out.println("ConsultaBean está morrendo ....");
	}

	public boolean validarCampos() {

		boolean validado = true;

		Especialidade especialidade = especialidadeDao.buscaPorId(idEspecialidade);
		this.consulta.setEspecialidade(especialidade);
		if (especialidade == null) {
			context.getExternalContext().getFlash().setKeepMessages(true);
			context.addMessage(null, new FacesMessage("Especialidade Inválida"));
			validado = false;
		}

		Profissional profissional = profissioanalDao.buscaPorId(idProfissional);
		this.consulta.setProfissional(profissional);
		if (profissional == null) {
			context.getExternalContext().getFlash().setKeepMessages(true);
			context.addMessage(null, new FacesMessage("Num. no Conselho Inválido"));
			validado = false;
		}
		try {
			Beneficiario beneficiario = beneficiarioDao.buscaPorInscricaoTit(
					Integer.parseInt(this.consulta.getCarteira().substring(2, 7)),
					Integer.parseInt(this.consulta.getCarteira().substring(7, 9)));
			if (beneficiario == null) {
				context.getExternalContext().getFlash().setKeepMessages(true);
				context.addMessage(null, new FacesMessage("Nº de Carteira Inválido"));
				validado = false;
			} else {
				this.consulta.setNome(beneficiario.getNome());
			}
		} catch (Exception e) {
			context.getExternalContext().getFlash().setKeepMessages(true);
			context.addMessage(null, new FacesMessage("Nº de Carteira deve ser Numérico"));
			validado = false;
		}

		CID cid = cidDao.buscaPorId(idCid);
		this.consulta.setCid(cid);
		if (cid == null) {
			context.getExternalContext().getFlash().setKeepMessages(true);
			context.addMessage(null, new FacesMessage("CID Inválido"));
			validado = false;
		}

		return validado;

	}

	@Transacional
	public void gravar() {

		this.consulta.setPrestador(prestador);
		this.consulta.setLogin(usuario.getLogin());

		TipoConsulta tipoConsulta = tipoConsultaDao.buscaPorId(idTipoConsulta);
		this.consulta.setTipoConsulta(tipoConsulta);

		TipoSaida tipoSaida = tipoSaidaDao.buscaPorId(idTipoSaida);
		this.consulta.setTipoSaida(tipoSaida);

		this.consulta.setDataEmissao(referencia);

		if (validarCampos()) {
			this.dao.adiciona(this.consulta);
			atualizar();
			context.getExternalContext().getFlash().setKeepMessages(true);
			context.addMessage(null, new FacesMessage("Consulta Adicionada com Sucesso!"));
			this.consulta = new Consulta();
		}
		// return "addConsulta?faces-redirect=true";
	}

	public void atualizar() {
		// getConsultas();
		Prestador prestador = (Prestador) context.getExternalContext().getSessionMap().get("prestadorSessao");
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
		this.consultas = this.dao.buscaPorPrestadorReferencia(prestador.getId(), data);
		calculaTotal();
		System.out.println("Atualizar Consulta");
	}

	public void calculaTotal() {
		Double valor;
		//String str_Valor;
		this.valorTotal = 0.0;

		for (Consulta consulta : consultas) {
			//str_Valor = consulta.getValor();
			//str_Valor = str_Valor.replaceAll(",", ".");
			//valor = Double.parseDouble(str_Valor);
			valor = consulta.getValor();
			this.valorTotal += valor;
		}
		this.quantidade = consultas.size();
		valorTotalFormatado = "R$ "+new DecimalFormat("#,###,##0.00").format(this.valorTotal);
	}
	
	@Transacional
	public void remover(Consulta consulta) {
		this.dao.remove(consulta);
		atualizar();
	}

	public void carregar(Consulta consulta) {
		System.out.println("Carregando livro " + consulta.getNome());
		this.consulta = consulta;
	}

	public void carregaPelaId() {

		long id = this.consulta.getId();
		this.consulta = this.dao.buscaPorId(id);
		if (this.consulta == null) {
			this.consulta = new Consulta();
		}

	}

	public List<Consulta> getConsultas() {
		return consultas;
	}

	public void setConsultas(List<Consulta> consultas) {
		this.consultas = consultas;
	}

	public Consulta getConsulta() {
		return consulta;
	}

	public void setConsulta(Consulta consulta) {
		this.consulta = consulta;
	}

	public List<TipoConsulta> getTipoConsultas() {
		return tipoConsultaDao.listaTodos();
	}

	public List<TipoSaida> getTipoSaidas() {
		return tipoSaidaDao.listaTodos();
	}

	public List<EspecialidadePrestador> listaEspecialidadePrestador() {
		return especialidadePrestadorDao.buscaPorPrestador(prestador.getId());
	}

	public LoteGuia getLoteGuia() {
		return loteGuia;
	}

	public void setLoteGuia(LoteGuia loteGuia) {
		this.loteGuia = loteGuia;
	}

	public Meses[] getMeses() {
		return meses;
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

	public int getIdProfissional() {
		return idProfissional;
	}

	public void setIdProfissional(int idProfissional) {
		this.idProfissional = idProfissional;
	}

	public int getIdEspecialidade() {
		return idEspecialidade;
	}

	public void setIdEspecialidade(int idEspecialidade) {
		this.idEspecialidade = idEspecialidade;
	}

	public int getIdTipoConsulta() {
		return idTipoConsulta;
	}

	public void setIdTipoConsulta(int idTipoConsulta) {
		this.idTipoConsulta = idTipoConsulta;
	}

	public int getIdTipoSaida() {
		return idTipoSaida;
	}

	public void setIdTipoSaida(int idTipoSaida) {
		this.idTipoSaida = idTipoSaida;
	}

	public String getIdCid() {
		return idCid;
	}

	public void setIdCid(String idCid) {
		this.idCid = idCid;
	}

	public Calendar getReferencia() {
		return referencia;
	}

	public void setReferencia(Calendar referencia) {
		this.referencia = referencia;
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
