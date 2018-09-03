package br.com.caixaseguradora.sinistro.domains;

import java.io.Serializable;

import org.springframework.stereotype.Component;

@Component
public class UserMail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected String domain = "danilo.oliveira@caixaseguradora.com.br";//"opservicosonline@caixaprevidencia.com.br";
	protected String user = "est11577";//"opservicosonline";
	protected String pass = "Caixa123";

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

}
