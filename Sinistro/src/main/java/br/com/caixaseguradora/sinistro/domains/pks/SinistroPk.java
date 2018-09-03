package br.com.caixaseguradora.sinistro.domains.pks;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;


@Embeddable
public class SinistroPk implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		
	@Column(name="numero_solicitacao")
	private Integer numeroSolicit;
	
	@Column(name="certificado")
	private String accountId;
	
	@Column(name="COD_TIPO_ALTERACAO")
	private String codTipAlter;

	public Integer getNumeroSolicit() {
		return numeroSolicit;
	}

	public void setNumeroSolicit(Integer numeroSolicit) {
		this.numeroSolicit = numeroSolicit;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getCodTipAlter() {
		return codTipAlter;
	}

	public void setCodTipAlter(String codTipAlter) {
		this.codTipAlter = codTipAlter;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accountId == null) ? 0 : accountId.hashCode());
		result = prime * result + ((codTipAlter == null) ? 0 : codTipAlter.hashCode());
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
		SinistroPk other = (SinistroPk) obj;
		if (accountId == null) {
			if (other.accountId != null)
				return false;
		} else if (!accountId.equals(other.accountId))
			return false;
		if (codTipAlter == null) {
			if (other.codTipAlter != null)
				return false;
		} else if (!codTipAlter.equals(other.codTipAlter))
			return false;
		if (numeroSolicit == null) {
			if (other.numeroSolicit != null)
				return false;
		} else if (!numeroSolicit.equals(other.numeroSolicit))
			return false;
		return true;
	}

}
