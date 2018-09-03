package br.com.caixaseguradora.sinistro.domains;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;

import org.springframework.stereotype.Component;

import br.com.caixaseguradora.sinistro.domains.pks.SinistroPk;
@Component
@Entity
@Table(name = "ncl_consulta_sinistro")
@NamedQuery(name=Sinistro.FINDBYDATE, query="SELECT s FROM Sinistro s where s.dataSolicitacao = sysdateto_char(sysdate, 'dd/mm/YYYY')") 
public class Sinistro implements Serializable {
	
	public static final String FINDBYDATE = "Sinistro.findByDataSolicitacao";
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	@NotEmpty
	private SinistroPk pk;

	@NotEmpty
	@Column(name = "nome_cliente")
	private String nomeCliente;
	
	@NotEmpty
	@Temporal(TemporalType.DATE)
	@Column(name = "data_solicitacao")
	private Date dataSolicitacao;
	
	@NotEmpty
	@Temporal(TemporalType.DATE)
	@Column(name = "DATA_ADESAO")
	private Date dataAdesao;
	
	@NotEmpty
	@Column(name = "descricao_beneficio")
	private String descBeneficio;
	
	@NotEmpty
	@Column(name = "valor_cobertura")
	private Double valorCobertura;
	
	@NotEmpty
	@Column(name = "contribuicao")
	private String contribuicao;
	
	@NotEmpty
	@Column(name = "valor_contribuicao")
	private Double valorContrib;
	
	@NotEmpty
	@Column(name = "CANAL")
	private String canal;
	
	@NotEmpty
	@Column(name = "Incluido_Por")
	private String incluidoPor;
	
	@NotEmpty
	@Column(name = "canal_inclusao")
	private String canalInclusao;

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	public Date getDataSolicitacao() {
		return dataSolicitacao;
	}

	public void setDataSolicitacao(Date dataSolicitacao) {
		this.dataSolicitacao = dataSolicitacao;
	}

	public Date getDataAdesao() {
		return dataAdesao;
	}

	public void setDataAdesao(Date dataAdesao) {
		this.dataAdesao = dataAdesao;
	}

	public String getDescBeneficio() {
		return descBeneficio;
	}

	public void setDescBeneficio(String descBeneficio) {
		this.descBeneficio = descBeneficio;
	}

	public Double getValorCobertura() {
		return valorCobertura;
	}

	public void setValorCobertura(Double valorCobertura) {
		this.valorCobertura = valorCobertura;
	}

	public String getContribuicao() {
		return contribuicao;
	}

	public void setContribuicao(String contribuicao) {
		this.contribuicao = contribuicao;
	}

	public Double getValorContrib() {
		return valorContrib;
	}

	public void setValorContrib(Double valorContrib) {
		this.valorContrib = valorContrib;
	}

	public String getCanal() {
		return canal;
	}

	public void setCanal(String canal) {
		this.canal = canal;
	}

	public String getIncluidoPor() {
		return incluidoPor;
	}

	public void setIncluidoPor(String incluidoPor) {
		this.incluidoPor = incluidoPor;
	}

	public String getCanalInclusao() {
		return canalInclusao;
	}

	public void setCanalInclusao(String canalInclusao) {
		this.canalInclusao = canalInclusao;
	}

	public SinistroPk getPk() {
		return pk;
	}

	public void setPk(SinistroPk pk) {
		this.pk = pk;
	}
	
	

}
