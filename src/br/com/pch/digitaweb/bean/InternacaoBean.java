package br.com.pch.digitaweb.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.pch.digitaweb.dao.BeneficiarioDao;
import br.com.pch.digitaweb.dao.CIDDao;
import br.com.pch.digitaweb.dao.GrauParticipacaoDao;
import br.com.pch.digitaweb.dao.InternacaoDao;
import br.com.pch.digitaweb.dao.ItemDao;
import br.com.pch.digitaweb.dao.ItemInternacaoDao;
import br.com.pch.digitaweb.dao.LoteGuiaDao;
import br.com.pch.digitaweb.dao.OutrasDespesasDao;
import br.com.pch.digitaweb.dao.ProfissionalDao;
import br.com.pch.digitaweb.modelo.Beneficiario;
import br.com.pch.digitaweb.modelo.CID;
import br.com.pch.digitaweb.modelo.Despesa;
import br.com.pch.digitaweb.modelo.Estados;
import br.com.pch.digitaweb.modelo.GrauParticipacao;
import br.com.pch.digitaweb.modelo.Internacao;
import br.com.pch.digitaweb.modelo.Item;
import br.com.pch.digitaweb.modelo.ItemInternacao;
import br.com.pch.digitaweb.modelo.LoteGuia;
import br.com.pch.digitaweb.modelo.OutrasDespesas;
import br.com.pch.digitaweb.modelo.Prestador;
import br.com.pch.digitaweb.modelo.Profissional;
import br.com.pch.digitaweb.modelo.StatusLote;
import br.com.pch.digitaweb.modelo.Tabela;
import br.com.pch.digitaweb.modelo.Tecnica;
import br.com.pch.digitaweb.modelo.Usuario;
import br.com.pch.digitaweb.modelo.ViaAcesso;
import br.com.pch.digitaweb.tx.Transacional;

@Named
@ViewScoped
public class InternacaoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String idProfissional;
	private int idGrauparticipacao;
	private String idCid = "000";
	private String idItemInternacao;
	private Estados[] estados = Estados.values();
	private Tabela[] tabelas = Tabela.values();
	private ViaAcesso[] viaAcesso = ViaAcesso.values();
	private Tecnica[] tecnica = Tecnica.values();
	private Despesa[] despesas = Despesa.values();

	private Internacao internacao = new Internacao();
	private ItemInternacao itemInternacao = new ItemInternacao();
	private OutrasDespesas outrasDespesas = new OutrasDespesas();
	private List<ItemInternacao> listaItemInternacao = new ArrayList<>();
	private List<OutrasDespesas> listaOutrasDespesas = new ArrayList<>();
	private LoteGuia loteGuia = new LoteGuia();
	private Prestador prestador;
	private Calendar referencia;
	private Profissional profissional = new Profissional();
	private Beneficiario beneficiario = new Beneficiario();
	private CID cid = new CID();
	private Usuario usuario;
	private int statusTela = 0; // 0-inicial 1-avancado 2-finalizado
	private boolean camposOnOFF = false;
	private boolean tabelaONOFF = false;
	private boolean tabelaDespesaONOFF = false;

	@Inject
	FacesContext context;

	@Inject
	private LoteGuiaDao loteGuiDao;

	@Inject
	private GrauParticipacaoDao grauParticipacaoDao;

	@Inject
	private ProfissionalDao profissioanalDao;

	@Inject
	private BeneficiarioDao beneficiarioDao;

	@Inject
	private CIDDao cidDao;

	@Inject
	private ItemDao itemDao;
	
	@Inject
	private InternacaoDao internacaoDao;
	
	@Inject
	private ItemInternacaoDao itemInternacaoDao;
	
	@Inject
	private OutrasDespesasDao outrasDespesasDao;

	@PostConstruct
	void init() {
		prestador = (Prestador) context.getExternalContext().getSessionMap().get("prestadorSessao");
		referencia = (Calendar) context.getExternalContext().getSessionMap().get("referencia");
		usuario = (Usuario) context.getExternalContext().getSessionMap().get("usuarioLogado");

		this.loteGuia = loteGuiDao.buscaPorReferenciaPrestador(prestador.getId(), referencia);
		itemInternacao.setDataRealizacao(internacao.getDataAtendimento());
		outrasDespesas.setDataRealizacao(internacao.getDataAtendimento());
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
	}

	public boolean validarInternacao() {

		boolean validado = true;

		this.beneficiario = beneficiarioDao.buscaPorInscricaoTit(
				Integer.parseInt(this.internacao.getCarteira().substring(2, 7)),
				Integer.parseInt(this.internacao.getCarteira().substring(7, 9)));
		if (this.beneficiario == null) {
			context.getExternalContext().getFlash().setKeepMessages(true);
			context.addMessage(null, new FacesMessage("Nº de Carteira Inválido"));
			validado = false;
			this.beneficiario = new Beneficiario();
		} else {
			this.internacao.setNome(beneficiario.getNome());
		}

		this.profissional = profissioanalDao.buscaPorId(Integer.parseInt(idProfissional));
		this.internacao.setProfissional(this.profissional);
		if (this.profissional == null) {
			context.getExternalContext().getFlash().setKeepMessages(true);
			context.addMessage(null, new FacesMessage("Num. no Conselho Inválido"));
			validado = false;
		}

		cid = cidDao.buscaPorId(idCid);
		this.internacao.setCid(cid);
		if (cid == null) {
			context.getExternalContext().getFlash().setKeepMessages(true);
			context.addMessage(null, new FacesMessage("CID Inválido"));
			validado = false;
		}

		return validado;
	}

	public void avancar() {
		this.internacao.setPrestador(prestador);
		this.internacao.setLogin(usuario.getLogin());
		this.internacao.setDataEmissao(referencia);
		GrauParticipacao grau = grauParticipacaoDao.buscaPorId(idGrauparticipacao);
		this.internacao.setGrauParticipacao(grau);

		if (validarInternacao()) {
			statusTela = 1;
			camposOnOFF = true;
			tabelaONOFF = !listaItemInternacao.isEmpty();
			tabelaDespesaONOFF = !listaOutrasDespesas.isEmpty();

		}

	}

	public void voltar() {
		if (statusTela > 0) {
			statusTela = 0;
			camposOnOFF = false;
			tabelaONOFF = false;
			tabelaDespesaONOFF = false;
		}
	}

	public boolean validarItem() {

		boolean validado = true;

		Item item = itemDao.buscaPorId(Integer.parseInt(idItemInternacao));
		if (item == null) {
			context.getExternalContext().getFlash().setKeepMessages(true);
			context.addMessage(null, new FacesMessage("Código do Item Inválido"));
			validado = false;
			item = new Item();
		} else {
			this.itemInternacao.setItem(item);
			this.itemInternacao.setDescricao(item.getNome());
		}

		return validado;
	}

	public void adicionaItem() {
		itemInternacao.setInternacao(internacao);
		if (validarItem()) {
			listaItemInternacao.add(itemInternacao);
			System.out.println("Item adicionado na lista " + itemInternacao.getQuantidade() + " - "
					+ itemInternacao.getItem().getNome());
			this.itemInternacao = new ItemInternacao();
			idItemInternacao = "";
			itemInternacao.setDataRealizacao(internacao.getDataAtendimento());
			tabelaONOFF = !listaItemInternacao.isEmpty();
		}
	}

	public void removerItem(ItemInternacao itemInternacao) {
		listaItemInternacao.remove(itemInternacao);
		tabelaONOFF = !listaItemInternacao.isEmpty();
	}

	public void adicionaOutrasDespesas() {
		outrasDespesas.setIdGuia(internacao.getId());
		listaOutrasDespesas.add(outrasDespesas);
		this.outrasDespesas = new OutrasDespesas();
		outrasDespesas.setDataRealizacao(internacao.getDataAtendimento());
		tabelaDespesaONOFF = !listaOutrasDespesas.isEmpty();

	}

	public void removerOutrasDespesas(OutrasDespesas outrasDespesas) {
		listaOutrasDespesas.remove(outrasDespesas);
		tabelaDespesaONOFF = !listaOutrasDespesas.isEmpty();
	}

	@Transacional
	public void confirmar() {
		statusTela = 2;
		if (!listaOutrasDespesas.isEmpty() || !listaItemInternacao.isEmpty()) {

			internacaoDao.adiciona(internacao);
			for (ItemInternacao itemInternacao : listaItemInternacao) {
				System.out.println(itemInternacao);
				itemInternacaoDao.adiciona(itemInternacao);

			}

			for (OutrasDespesas outrasDespesas : listaOutrasDespesas) {
				outrasDespesasDao.adiciona(outrasDespesas);
			}

			listaItemInternacao.clear();
			listaOutrasDespesas.clear();
			internacao = new Internacao();
			context.getExternalContext().getFlash().setKeepMessages(true);
			context.addMessage(null, new FacesMessage("Internação Adicionada com Sucesso!"));
			voltar();
			idProfissional = "";
		} else {
			context.getExternalContext().getFlash().setKeepMessages(true);
			context.addMessage(null, new FacesMessage("É necessário Cadastrar ao menos um Procedimento!"));
		}

	}
	
	public List<GrauParticipacao> getGrauParticipacoes() {
		return grauParticipacaoDao.listaTodos();
	}

	public String getIdProfissional() {
		return idProfissional;
	}

	public void setIdProfissional(String idProfissional) {
		this.idProfissional = idProfissional;
	}

	public int getIdGrauparticipacao() {
		return idGrauparticipacao;
	}

	public void setIdGrauparticipacao(int idGrauparticipacao) {
		this.idGrauparticipacao = idGrauparticipacao;
	}

	public String getIdCid() {
		return idCid;
	}

	public void setIdCid(String idCid) {
		this.idCid = idCid;
	}

	public Internacao getInternacao() {
		return internacao;
	}

	public void setInternacao(Internacao internacao) {
		this.internacao = internacao;
	}

	public ItemInternacao getItemInternacao() {
		return itemInternacao;
	}

	public void setItemInternacao(ItemInternacao itemInternacao) {
		this.itemInternacao = itemInternacao;
	}

	public OutrasDespesas getOutrasDespesas() {
		return outrasDespesas;
	}

	public void setOutrasDespesas(OutrasDespesas outrasDespesas) {
		this.outrasDespesas = outrasDespesas;
	}

	public List<ItemInternacao> getListaItemInternacao() {
		return listaItemInternacao;
	}

	public void setListaItemInternacao(List<ItemInternacao> listaItemInternacao) {
		this.listaItemInternacao = listaItemInternacao;
	}

	public List<OutrasDespesas> getListaOutrasDespesas() {
		return listaOutrasDespesas;
	}

	public void setListaOutrasDespesas(List<OutrasDespesas> listaOutrasDespesas) {
		this.listaOutrasDespesas = listaOutrasDespesas;
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

	public Profissional getProfissional() {
		return profissional;
	}

	public void setProfissional(Profissional profissional) {
		this.profissional = profissional;
	}

	public Beneficiario getBeneficiario() {
		return beneficiario;
	}

	public void setBeneficiario(Beneficiario beneficiario) {
		this.beneficiario = beneficiario;
	}

	public CID getCid() {
		return cid;
	}

	public void setCid(CID cid) {
		this.cid = cid;
	}

	public int getStatusTela() {
		return statusTela;
	}

	public void setStatusTela(int statusTela) {
		this.statusTela = statusTela;
	}

	public boolean isCamposOnOFF() {
		return camposOnOFF;
	}

	public void setCamposOnOFF(boolean camposOnOFF) {
		this.camposOnOFF = camposOnOFF;
	}

	public boolean isTabelaONOFF() {
		return tabelaONOFF;
	}

	public void setTabelaONOFF(boolean tabelaONOFF) {
		this.tabelaONOFF = tabelaONOFF;
	}

	public boolean isTabelaDespesaONOFF() {
		return tabelaDespesaONOFF;
	}

	public void setTabelaDespesaONOFF(boolean tabelaDespesaONOFF) {
		this.tabelaDespesaONOFF = tabelaDespesaONOFF;
	}

	public Estados[] getEstados() {
		return estados;
	}

	public void setEstados(Estados[] estados) {
		this.estados = estados;
	}

	public Tabela[] getTabelas() {
		return tabelas;
	}

	public void setTabelas(Tabela[] tabelas) {
		this.tabelas = tabelas;
	}

	public ViaAcesso[] getViaAcesso() {
		return viaAcesso;
	}

	public void setViaAcesso(ViaAcesso[] viaAcesso) {
		this.viaAcesso = viaAcesso;
	}

	public Tecnica[] getTecnica() {
		return tecnica;
	}

	public void setTecnica(Tecnica[] tecnica) {
		this.tecnica = tecnica;
	}

	public Despesa[] getDespesas() {
		return despesas;
	}

	public void setDespesas(Despesa[] despesas) {
		this.despesas = despesas;
	}

	public String getIdItemInternacao() {
		return idItemInternacao;
	}

	public void setIdItemInternacao(String idItemInternacao) {
		this.idItemInternacao = idItemInternacao;
	}

}
