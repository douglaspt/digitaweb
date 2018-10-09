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
@Table(name = "tbl_internacao")
public class Internacao {

	@Id
	@Column(name = "cod_guiaInternacao")
	private long id = (new GregorianCalendar().getTimeInMillis()+this.hashCode()/100000);
	
	@OneToOne
	@JoinColumn(name = "cod_conveniado")
	private Prestador prestador;

	@Column(name = "dtAtendimento_int")
	@Temporal(TemporalType.DATE)
	private Calendar dataAtendimento = Calendar.getInstance();
	
	@Column(name = "cod_conveniadoInternacao")
	private int idConveniado;
	
	@Column(name = "cod_conselhoProfissional", columnDefinition = "char(7)")
	private String conselhoProfissional = "CRM";
	
	@Column(name = "cod_UFConselhoProfissional", columnDefinition = "char(2)")
	private String uf = "SP";
	
	@OneToOne
	@JoinColumn(name = "cod_profissional")
	private Profissional profissional;
	
	@Column(name = "numCarteira_int")
	private String carteira;
	
	@Column(name = "nomeBeneficiario_int")
	private String nome;
	
	@Column(name = "login_usu")
	private String login;
	
	@Column(name = "dtSistema_int")
	@Temporal(TemporalType.DATE)
	private Calendar dataSistema = Calendar.getInstance();
	
	@Column(name = "dtEmissao_int")
	@Temporal(TemporalType.DATE)
	private Calendar dataEmissao;
	
	@OneToOne
	@JoinColumn(name = "cod_CIDPrincipal")
	private CID cid;
	
	@Column(name = "cod_autorizacao")
	private int autorizacao;
		
	@OneToOne
	@JoinColumn(name = "cod_posicaoProfissional")
	private GrauParticipacao grauParticipacao;
	
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

	public int getIdConveniado() {
		return idConveniado;
	}

	public void setIdConveniado(int idConveniado) {
		this.idConveniado = idConveniado;
	}

	public String getConselhoProfissional() {
		return conselhoProfissional;
	}

	public void setConselhoProfissional(String conselhoProfissional) {
		this.conselhoProfissional = conselhoProfissional;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public Profissional getProfissional() {
		return profissional;
	}

	public void setProfissional(Profissional profissional) {
		this.profissional = profissional;
	}

	public String getCarteira() {
		return carteira;
	}

	public void setCarteira(String carteira) {
		this.carteira = carteira;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
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

	public CID getCid() {
		return cid;
	}

	public void setCid(CID cid) {
		this.cid = cid;
	}

	public int getAutorizacao() {
		return autorizacao;
	}

	public void setAutorizacao(int autorizacao) {
		this.autorizacao = autorizacao;
	}

	public GrauParticipacao getGrauParticipacao() {
		return grauParticipacao;
	}

	public void setGrauParticipacao(GrauParticipacao grauParticipacao) {
		this.grauParticipacao = grauParticipacao;
	}

}
