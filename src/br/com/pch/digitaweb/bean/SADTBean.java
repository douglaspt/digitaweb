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
import br.com.pch.digitaweb.dao.ItemDao;
import br.com.pch.digitaweb.dao.ItemSADTDao;
import br.com.pch.digitaweb.dao.LoteGuiaDao;
import br.com.pch.digitaweb.dao.OutrasDespesasDao;
import br.com.pch.digitaweb.dao.ProfissionalDao;
import br.com.pch.digitaweb.dao.SADTDao;
import br.com.pch.digitaweb.dao.TipoAtendimentoDao;
import br.com.pch.digitaweb.dao.TipoSaidaDao;
import br.com.pch.digitaweb.modelo.Beneficiario;
import br.com.pch.digitaweb.modelo.CID;
import br.com.pch.digitaweb.modelo.Despesa;
import br.com.pch.digitaweb.modelo.Estados;
import br.com.pch.digitaweb.modelo.Item;
import br.com.pch.digitaweb.modelo.ItemSADT;
import br.com.pch.digitaweb.modelo.LoteGuia;
import br.com.pch.digitaweb.modelo.OutrasDespesas;
import br.com.pch.digitaweb.modelo.Prestador;
import br.com.pch.digitaweb.modelo.Profissional;
import br.com.pch.digitaweb.modelo.SADT;
import br.com.pch.digitaweb.modelo.StatusLote;
import br.com.pch.digitaweb.modelo.Tabela;
import br.com.pch.digitaweb.modelo.Tecnica;
import br.com.pch.digitaweb.modelo.TipoAtendimento;
import br.com.pch.digitaweb.modelo.TipoSaida;
import br.com.pch.digitaweb.modelo.Usuario;
import br.com.pch.digitaweb.modelo.ViaAcesso;
import br.com.pch.digitaweb.tx.Transacional;

@Named
@ViewScoped
public class SADTBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String idProfissional;
	private int idTipoAtendimento;
	private int idTipoSaida;
	private String idCid = "000";
	private int idItemSADT;
	private Estados[] estados = Estados.values();
	private Tabela[] tabelas = Tabela.values();
	private ViaAcesso[] viaAcesso = ViaAcesso.values();
	private Tecnica[] tecnica = Tecnica.values();
	private Despesa[] despesas = Despesa.values();

	private SADT sadt = new SADT();
	private ItemSADT itemSADT = new ItemSADT();
	private OutrasDespesas outrasDespesas = new OutrasDespesas();
	private List<ItemSADT> listaItemSADT = new ArrayList<>();
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
	private TipoSaidaDao tipoSaidaDao;

	@Inject
	private TipoAtendimentoDao tipoAtendimentoDao;

	@Inject
	private ProfissionalDao profissioanalDao;

	@Inject
	private BeneficiarioDao beneficiarioDao;

	@Inject
	private CIDDao cidDao;

	@Inject
	private ItemDao itemDao;

	@Inject
	private SADTDao sadtDao;

	@Inject
	private ItemSADTDao itemSADTDao;

	@Inject
	private OutrasDespesasDao outrasDespesasDao;

	@PostConstruct
	void init() {
		prestador = (Prestador) context.getExternalContext().getSessionMap().get("prestadorSessao");
		referencia = (Calendar) context.getExternalContext().getSessionMap().get("referencia");
		usuario = (Usuario) context.getExternalContext().getSessionMap().get("usuarioLogado");
		this.loteGuia = loteGuiDao.buscaPorReferenciaPrestador(prestador.getId(), referencia);
		itemSADT.setDataRealizacao(sadt.getDataAtendimento());
		outrasDespesas.setDataRealizacao(sadt.getDataAtendimento());
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

	public boolean validarSADT() {

		boolean validado = true;

		this.beneficiario = beneficiarioDao.buscaPorInscricaoTit(
				Integer.parseInt(this.sadt.getCarteira().substring(2, 7)),
				Integer.parseInt(this.sadt.getCarteira().substring(7, 9)));
		if (this.beneficiario == null) {
			context.getExternalContext().getFlash().setKeepMessages(true);
			context.addMessage(null, new FacesMessage("Nº de Carteira Inválido"));
			validado = false;
			this.beneficiario = new Beneficiario();
		} else {
			this.sadt.setNome(beneficiario.getNome());
		}

		this.profissional = profissioanalDao.buscaPorId(Integer.parseInt(idProfissional));
		this.sadt.setProfissional(this.profissional);
		if (this.profissional == null) {
			context.getExternalContext().getFlash().setKeepMessages(true);
			context.addMessage(null, new FacesMessage("Num. no Conselho Inválido"));
			validado = false;
		}

		cid = cidDao.buscaPorId(idCid);
		this.sadt.setCid(cid);
		if (cid == null) {
			context.getExternalContext().getFlash().setKeepMessages(true);
			context.addMessage(null, new FacesMessage("CID Inválido"));
			validado = false;
		}

		return validado;

	}

	public void avancar() {
		this.sadt.setPrestador(prestador);
		this.sadt.setLogin(usuario.getLogin());
		this.sadt.setDataEmissao(referencia);

		TipoAtendimento tipoAtendimento = tipoAtendimentoDao.buscaPorId(idTipoAtendimento);
		this.sadt.setTipoAtendimento(tipoAtendimento);
		TipoSaida tipoSaida = tipoSaidaDao.buscaPorId(idTipoSaida);
		this.sadt.setTipoSaida(tipoSaida);

		if (validarSADT()) {
			statusTela = 1;
			camposOnOFF = true;
			tabelaONOFF = !listaItemSADT.isEmpty();
			tabelaDespesaONOFF = !listaOutrasDespesas.isEmpty();

		}

	}

	@Transacional
	public void confirmar() {
		statusTela = 2;
		if (!listaOutrasDespesas.isEmpty() || !listaItemSADT.isEmpty()) {

			sadtDao.adiciona(sadt);
			for (ItemSADT itemSADT : listaItemSADT) {
				itemSADTDao.adiciona(itemSADT);

			}

			for (OutrasDespesas outrasDespesas : listaOutrasDespesas) {
				outrasDespesasDao.adiciona(outrasDespesas);
			}

			listaItemSADT.clear();
			listaOutrasDespesas.clear();
			sadt = new SADT();
			context.getExternalContext().getFlash().setKeepMessages(true);
			context.addMessage(null, new FacesMessage("SADT Adicionada com Sucesso!"));
			voltar();
			idProfissional = "";
		} else {
			context.getExternalContext().getFlash().setKeepMessages(true);
			context.addMessage(null, new FacesMessage("É necessário Cadastrar ao menos um Procedimento!"));
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

		Item item = itemDao.buscaPorId(idItemSADT);
		if (item == null) {
			context.getExternalContext().getFlash().setKeepMessages(true);
			context.addMessage(null, new FacesMessage("Código do Item Inválido"));
			validado = false;
			item = new Item();
		} else {
			this.itemSADT.setItem(item);
			this.itemSADT.setDescricao(item.getNome());
		}

		return validado;
	}

	public void adicionaItem() {
		itemSADT.setSadt(sadt);
		if (validarItem()) {
			listaItemSADT.add(itemSADT);
			System.out.println(
					"Item adicionado na lista " + itemSADT.getQuantidade() + " - " + itemSADT.getItem().getNome());
			this.itemSADT = new ItemSADT();
			itemSADT.setDataRealizacao(sadt.getDataAtendimento());
			tabelaONOFF = !listaItemSADT.isEmpty();
		}
	}

	public void removerItem(ItemSADT itemSADT) {
		listaItemSADT.remove(itemSADT);
		tabelaONOFF = !listaItemSADT.isEmpty();
	}

	public void adicionaOutrasDespesas() {
		outrasDespesas.setIdGuia(sadt.getId());
		listaOutrasDespesas.add(outrasDespesas);
		this.outrasDespesas = new OutrasDespesas();
		outrasDespesas.setDataRealizacao(sadt.getDataAtendimento());
		tabelaDespesaONOFF = !listaOutrasDespesas.isEmpty();

	}

	public void removerOutrasDespesas(OutrasDespesas outrasDespesas) {
		listaOutrasDespesas.remove(outrasDespesas);
		tabelaDespesaONOFF = !listaOutrasDespesas.isEmpty();
	}

	public List<TipoSaida> getTipoSaidas() {
		return tipoSaidaDao.listaTodos();
	}

	public List<TipoAtendimento> getTipoAtendimentos() {
		return tipoAtendimentoDao.listaTodos();
	}

	public SADT getSadt() {
		return sadt;
	}

	public void setSadt(SADT sadt) {
		this.sadt = sadt;
	}

	public Calendar getReferencia() {
		return referencia;
	}

	public void setReferencia(Calendar referencia) {
		this.referencia = referencia;
	}

	public String getIdProfissional() {
		return idProfissional;
	}

	public void setIdProfissional(String idProfissional) {
		this.idProfissional = idProfissional;
	}

	public int getIdTipoAtendimento() {
		return idTipoAtendimento;
	}

	public void setIdTipoAtendimento(int idTipoAtendimento) {
		this.idTipoAtendimento = idTipoAtendimento;
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

	public LoteGuia getLoteGuia() {
		return loteGuia;
	}

	public void setLoteGuia(LoteGuia loteGuia) {
		this.loteGuia = loteGuia;
	}

	public Prestador getPrestador() {
		return prestador;
	}

	public void setPrestador(Prestador prestador) {
		this.prestador = prestador;
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

	public int getIdItemSADT() {
		return idItemSADT;
	}

	public void setIdItemSADT(int idItemSADT) {
		this.idItemSADT = idItemSADT;
	}

	public ItemSADT getItemSADT() {
		return itemSADT;
	}

	public void setItemSADT(ItemSADT itemSADT) {
		this.itemSADT = itemSADT;
	}

	public List<ItemSADT> getListaItemSADT() {
		return listaItemSADT;
	}

	public void setListaItemSADT(List<ItemSADT> listaItemSADT) {
		this.listaItemSADT = listaItemSADT;
	}

	public boolean isTabelaONOFF() {
		return tabelaONOFF;
	}

	public void setTabelaONOFF(boolean tabelaONOFF) {
		this.tabelaONOFF = tabelaONOFF;
	}

	public OutrasDespesas getOutrasDespesas() {
		return outrasDespesas;
	}

	public void setOutrasDespesas(OutrasDespesas outrasDespesas) {
		this.outrasDespesas = outrasDespesas;
	}

	public List<OutrasDespesas> getListaOutrasDespesas() {
		return listaOutrasDespesas;
	}

	public void setListaOutrasDespesas(List<OutrasDespesas> listaOutrasDespesas) {
		this.listaOutrasDespesas = listaOutrasDespesas;
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

}
