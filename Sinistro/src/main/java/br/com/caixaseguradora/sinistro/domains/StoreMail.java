package br.com.caixaseguradora.sinistro.domains;

import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class StoreMail {
	private String nome;
	private String subject;
	private String certificado; // Certificado:
	private String cpf; // CPF:
	private String tipo; // Tipo:
	private String valorContribuicao; // Contribuía com:
	private Date data; // Data:

	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getCertificado() {
		return certificado;
	}

	public void setCertificado(String certificado) {
		this.certificado = certificado;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getValorContribuicao() {
		return valorContribuicao;
	}

	public void setValorContribuicao(String valorContribuicao) {
		this.valorContribuicao = valorContribuicao;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "StoreMail [subject=" + subject + ", certificado=" + certificado + ", cpf=" + cpf + ", tipo=" + tipo
				+ ", valorContribuicao=" + valorContribuicao + ", data=" + data + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
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
		StoreMail other = (StoreMail) obj;
		if (cpf == null) {
			if (other.cpf != null)
				return false;
		} else if (!cpf.equals(other.cpf))
			return false;
		return true;
	}

}
