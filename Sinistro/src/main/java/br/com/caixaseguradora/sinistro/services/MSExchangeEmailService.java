package br.com.caixaseguradora.sinistro.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.caixaseguradora.sinistro.domains.StoreMail;
import br.com.caixaseguradora.sinistro.repositories.msexchangeemailservices.MSExchangeEmailRepositories;

@Service
public class MSExchangeEmailService {	

	@Autowired
	private MSExchangeEmailRepositories mSExchangeEmailRepositories;
	
	public List<StoreMail> readMails() {
		return mSExchangeEmailRepositories.readEmails();		
	}
	
	public List<StoreMail>readMailByDate(String initialDate, String finalDate) {
		String dataInicial = getFormatDate(initialDate);
		String dataFinal= getFormatDate(finalDate);
		return mSExchangeEmailRepositories.readEmails(dataInicial, dataFinal);

	}
	
	public String getFormatDate(String date){
		String ano = date.substring(0, 4);
		String mes= date.substring(5, 7);
		String dia = date.substring(8, 10);
		String dataFormatada =  dia+ "/" + mes+ "/" + ano;
		return dataFormatada;
	}
}
