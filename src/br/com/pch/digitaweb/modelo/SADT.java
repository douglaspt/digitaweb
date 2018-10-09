package br.com.pch.digitaweb.modelo;

import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "tbl_sadt")
public class SADT {

	@Id
	@Column(name = "cod_guiaSADT")
	private long id = (new GregorianCalendar().getTimeInMillis()+this.hashCode()/100000);
	
	@OneToOne
	@JoinColumn(name = "cod_conveniado")
	private Prestador prestador;

	@Column(name = "dtAtendimento_sadt")
	@Temporal(TemporalType.DATE)
	private Calendar dataAtendimento = Calendar.getInstance();
	
	@Column(name = "cod_conselhoProfissionalSolicitante", columnDefinition = "char(7)")
	private String conselhoProfissional = "CRM";
	
	@OneToOne
	@JoinColumn(name = "cod_ProfissionalSolicitante")
	private Profissional profissional;
	
	@Column(name = "cod_UFConselhoProfissionalSolicitante", columnDefinition = "char(2)")
	private String uf = "SP";
	
	
	@Column(name = "numCarteira_sadt")
	private String carteira;
	
	@OneToOne
	@JoinColumn(name = "cod_tipoAtendimento")
	private TipoAtendimento tipoAtendimento;
	
	@OneToOne
	@JoinColumn(name = "cod_tipoSaida")
	private TipoSaida tipoSaida;
	
	@Column(name = "login_usu")
	private String login;
	
	@Column(name = "dtSistema_sadt")
	@Temporal(TemporalType.DATE)
	private Calendar dataSistema = Calendar.getInstance();
	
	@Column(name = "dtEmissao_sadt")
	@Temporal(TemporalType.DATE)
	private Calendar dataEmissao;
	
	@Column(name = "nomeBeneficiario_sadt")
	private String nome;
		
	@OneToOne
	@JoinColumn(name = "cod_CIDPrincipal")
	private CID cid;
	
	
	@Column(name = "cod_autorizacao")
	private int autorizacao;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Prestador getPrestador() {
		return prestador;
	}

	public void setPrestador(Prestador prestador) {
		this.prestador = prestador;
	}

	public Calendar getDataAtendimento() {
		return dataAtendimento;
	}

	public void setDataAtendimento(Calendar dataAtendimento) {
		this.dataAtendimento = dataAtendimento;
	}

	public String getConselhoProfissional() {
		return conselhoProfissional;
	}

	public void setConselhoProfissional(String conselhoProfissional) {
		this.conselhoProfissional = conselhoProfissional;
	}

	public Profissional getProfissional() {
		return profissional;
	}

	public void setProfissional(Profissional profissional) {
		this.profissional = profissional;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getCarteira() {
		return carteira;
	}

	public void setCarteira(String carteira) {
		this.carteira = carteira;
	}

	public TipoAtendimento getTipoAtendimento() {
		return tipoAtendimento;
	}

	public void setTipoAtendimento(TipoAtendimento tipoAtendimento) {
		this.tipoAtendimento = tipoAtendimento;
	}

	public TipoSaida getTipoSaida() {
		return tipoSaida;
	}

	public void setTipoSaida(TipoSaida tipoSaida) {
		this.tipoSaida = tipoSaida;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public Calendar getDataSistema() {
		return dataSistema;
	}

	public void setDataSistema(Calendar dataSistema) {
		this.dataSistema = dataSistema;
	}

	public Calendar getDataEmissao() {
		return dataEmissao;
	}

	public void setDataEmissao(Calendar dataEmissao) {
		this.dataEmissao = dataEmissao;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getAutorizacao() {
		return autorizacao;
	}

	public void setAutorizacao(int autorizacao) {
		this.autorizacao = autorizacao;
	}

	public CID getCid() {
		return cid;
	}

	public void setCid(CID cid) {
		this.cid = cid;
	}


}
