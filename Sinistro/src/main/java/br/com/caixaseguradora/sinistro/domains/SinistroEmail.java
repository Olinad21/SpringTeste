package br.com.caixaseguradora.sinistro.domains;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;


@Entity
@Table(name = "tb_batimento")
@NamedQuery(name=SinistroEmail.FINDBYDATE, query="SELECT s FROM Sinistro s WHERE s.dataSolicitacao between :initialDate and :finalDate") 

public class SinistroEmail implements Serializable {

	public static final String FINDBYDATE = "SinistroEmail.findByDataSolicitacao";
	private static final long serialVersionUID = 1L;

	@Transient
	private Date initialDate;
	@Transient
	private Date finalDate;
	
    @NotEmpty(message="nome não pode ser vazio")
	@Column(name = "nome")
	private String nomeCliente;	

    @NotEmpty(message="tipo_contribuicao não pode ser vazio")
	@Column(name = "tipo_contribuicao")
	private String tipoContribuicao;

    @NotEmpty(message="valor_contribuicao não pode ser vazio")
	@Column(name = "valor_contribuicao")
	private Double valorContribuicao;

    @NotEmpty(message="data_solicitacao não pode ser vazio")
	@Temporal(TemporalType.DATE)
	@Column(name = "data_solicitacao")
	private Date dataSolicitacao;
	
    @NotEmpty(message="certificado não pode ser vazio")
	@Column(name = "certificado")
	private String certificado;

    @NotEmpty(message="numero_solicitacao não pode ser vazio")
	@Column(name = "numero_solicitacao")
	@Id
	private Integer numeroSolicit;
	
    @NotEmpty(message="email_recebido não pode ser vazio")
	@Column(name = "email_recebido", length = 1)
	private char emailRecebido;

	public SinistroEmail() {		
		super();
		this.emailRecebido = 'N';
	}
	

//	public SinistroEmail(Sinistro obj, char bateu) {
//		super();
//		this.certificado =obj.getPk().getAccountId();
//		this.numeroSolicit=getNumeroSolicit();
//		this.nomeCliente = obj.getNomeCliente();
//		this.tipoContribuicao = obj.getPk().getAccountId();
//		this.valorContribuicao = obj.getValorContrib();
//		this.dataSolicitacao = obj.getDataSolicitacao();
//		this.emailRecebido = bateu;
//	}

	
	public Date getInitialDate() {
		return initialDate;
	}


	public void setInitialDate(Date initialDate) {
		this.initialDate = initialDate;
	}
	
	public char getEmailRecebido() {
		return emailRecebido;
	}


	public void setEmailRecebido(char emailRecebido) {
		this.emailRecebido = emailRecebido;
	}


	public Date getFinalDate() {
		return finalDate;
	}


	public void setFinalDate(Date finalDate) {
		this.finalDate = finalDate;
	}


	


	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	public String getTipoContribuicao() {
		return tipoContribuicao;
	}

	public void setTipoContribuicao(String tipoContribuicao) {
		this.tipoContribuicao = tipoContribuicao;
	}

	public Double getValorContribuicao() {
		return valorContribuicao;
	}

	public void setValorContribuicao(Double valorContribuicao) {
		this.valorContribuicao = valorContribuicao;
	}

	public Date getDataSolicitacao() {
		return dataSolicitacao;
	}

	public void setDataSolicitacao(Date dataSolicitacao) {
		this.dataSolicitacao = dataSolicitacao;
	}

	public String getCertificado() {
		return certificado;
	}

	public void setCertificado(String certificado) {
		this.certificado = certificado;
	}

	public Integer getNumeroSolicit() {
		return numeroSolicit;
	}

	public void setNumeroSolicit(Integer numeroSolicit) {
		this.numeroSolicit = numeroSolicit;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((numeroSolicit == null) ? 0 : numeroSolicit.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SinistroEmail other = (SinistroEmail) obj;
		if (numeroSolicit == null) {
			if (other.numeroSolicit != null)
				return false;
		} else if (!numeroSolicit.equals(other.numeroSolicit))
			return false;
		return true;
	}

	
	
	

	

}
