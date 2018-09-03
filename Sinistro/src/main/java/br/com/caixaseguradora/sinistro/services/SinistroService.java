package br.com.caixaseguradora.sinistro.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.caixaseguradora.sinistro.domains.Sinistro;
import br.com.caixaseguradora.sinistro.domains.SinistroEmail;
import br.com.caixaseguradora.sinistro.domains.StoreMail;
import br.com.caixaseguradora.sinistro.repositories.SinistroRepository;

@Transactional(readOnly=true)
@Service
public class SinistroService {

	@Autowired
	private SinistroRepository sinistroRepository;

	@Autowired
	SinistroEmailService sinistroEmailService;

	@Autowired
	MSExchangeEmailService EmailService;

	public List<Sinistro> findAll() {
		List<Sinistro> obj = sinistroRepository.findAll();
		return obj;
	}

	public Page<Sinistro> findPage(Integer page, Integer size, String direction, String orderBy) {

		PageRequest pageable =  PageRequest.of(page, size, Direction.valueOf(direction), orderBy);
		return sinistroRepository.findAll(pageable);

	}

	public List<Sinistro> batimento() {
		// MSExchangeEmailService EmailService = new MSExchangeEmailService();
		int cont = 0;
		List<StoreMail> listMail= null;
		List<Sinistro> listSinistro = findAll();
		if(!listSinistro.isEmpty()) {
			listMail = EmailService.readMails();			
		}				
		List<Sinistro> bateu = new ArrayList<Sinistro>();
		SinistroEmail sinistroEmail;
//		Stream st = listSinistro.stream().
		if (!listSinistro.isEmpty()) {
			for (Sinistro sinistro : listSinistro) {
				sinistroEmail = new SinistroEmail();
				if (!listMail.isEmpty()) {
					
					for (StoreMail storeMail : listMail) {
						
						
						if (sinistro.getPk().getAccountId().equals(storeMail.getCertificado())) {
							sinistroEmail.setEmailRecebido('S');							
							cont++;
							bateu.add(sinistro);

							System.out.println("" + "Nome Cliente: " + sinistro.getNomeCliente() + ""
									+ "\nCertificado: " + sinistro.getPk().getAccountId() + ""
									+ "\nTipo de Contribuição: " + sinistro.getDescBeneficio());
							System.out.println("\n");
						} 
						else {
						}
						
					}
				}
				else {					
					System.out.println("Caixa de entrada sem emails para o dia pesquisado");
				}
				
				sinistroEmail.setCertificado(sinistro.getPk().getAccountId());
				sinistroEmail.setNumeroSolicit(sinistro.getPk().getNumeroSolicit());
				sinistroEmail.setDataSolicitacao(sinistro.getDataSolicitacao());
				sinistroEmail.setNomeCliente(sinistro.getNomeCliente());
				sinistroEmail.setTipoContribuicao(sinistro.getDescBeneficio());
				sinistroEmail.setValorContribuicao(sinistro.getValorContrib());
				
				sinistroEmailService.add(sinistroEmail);
			}

		}
		else {
			
			System.out.println("consulta no Banco retornado vazio");
		}
		System.out.println("Quantidade de E-mails nao batido: " + (listSinistro.size() - cont));
		System.out.println("Quantidade de E-mails batidos: " + cont);
		return bateu;

	}
}
