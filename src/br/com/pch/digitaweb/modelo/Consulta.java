package br.com.pch.digitaweb.modelo;

import java.util.Calendar;
import java.util.Date;
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
@Table(name = "tbl_consulta")
public class Consulta {

	@Id
	@Column(name = "cod_guiaConsulta")
	private long id = (new GregorianCalendar().getTimeInMillis()+this.hashCode()/100000);
	
	@OneToOne
	@JoinColumn(name = "cod_conveniado")
	private Prestador prestador;

	@Column(name = "dtAtendimento_con")
	@Temporal(TemporalType.DATE)
	private Date dataAtendimento = new Date();
	
	@OneToOne
	@JoinColumn(name = "cod_tipoConsulta")
	private TipoConsulta tipoConsulta;
		
	@Column(name = "cod_ConselhoProfissional", columnDefinition = "char(7)")
	private String conselhoProfissional = "CRM";
	
	@OneToOne
	@JoinColumn(name = "cod_Profissional")
	private Profissional profissional;
	
	@Column(name = "cod_UFConselhoProfissional")
	private String uf = "SP";
	
	@OneToOne
	@JoinColumn(name = "cod_CIDPrincipal")
	private CID cid;
	
	@OneToOne
	@JoinColumn(name = "cod_tipoSaida")
	private TipoSaida tipoSaida;
	
	@Column(name = "numCarteira_con")
	private String carteira;
	
	@OneToOne
	@JoinColumn(name = "cod_especialidade")
	private Especialidade especialidade;
	
	@Column(name = "nomeBeneficiario_con")
	private String nome;
	
	@Column(name = "login_usu")
	private String login;
	
	@Column(name = "dtSistema_con")
	@Temporal(TemporalType.DATE)
	private Calendar dataSistema = Calendar.getInstance();
	
	@Column(name = "dtEmissao_con")
	@Temporal(TemporalType.DATE)
	private Calendar dataEmissao;
	
	@Column(name = "cod_autorizacao")
	private int autorizacao = 0;
	
	@Column(name = "vlTotal_con")
	private Double valor;

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

	public Date getDataAtendimento() {
		return dataAtendimento;
	}

	public void setDataAtendimento(Date dataAtendimento) {
		this.dataAtendimento = dataAtendimento;
	}

	public TipoConsulta getTipoConsulta() {
		return tipoConsulta;
	}

	public void setTipoConsulta(TipoConsulta tipoConsulta) {
		this.tipoConsulta = tipoConsulta;
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

	public CID getCid() {
		return cid;
	}

	public void setCid(CID cid) {
		this.cid = cid;
	}

	public TipoSaida getTipoSaida() {
		return tipoSaida;
	}

	public void setTipoSaida(TipoSaida tipoSaida) {
		this.tipoSaida = tipoSaida;
	}

	public String getCarteira() {
		return carteira;
	}

	public void setCarteira(String carteira) {
		this.carteira = carteira;
	}

	public Especialidade getEspecialidade() {
		return especialidade;
	}

	public void setEspecialidade(Especialidade especialidade) {
		this.especialidade = especialidade;
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

	public int getAutorizacao() {
		return autorizacao;
	}

	public void setAutorizacao(int autorizacao) {
		this.autorizacao = autorizacao;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}
	
	//public Double getValorDouble(){
	//	return Double.parseDouble(getValor());
	//}
	
}
