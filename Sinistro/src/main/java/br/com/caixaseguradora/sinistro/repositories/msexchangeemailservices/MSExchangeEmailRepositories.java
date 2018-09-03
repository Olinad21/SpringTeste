package br.com.caixaseguradora.sinistro.repositories.msexchangeemailservices;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.SynchronousQueue;

import org.springframework.stereotype.Component;
import br.com.caixaseguradora.sinistro.domains.StoreMail;
import br.com.caixaseguradora.sinistro.domains.UserMail;
import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.PropertySet;
import microsoft.exchange.webservices.data.core.enumeration.misc.ExchangeVersion;
import microsoft.exchange.webservices.data.core.enumeration.property.WellKnownFolderName;
import microsoft.exchange.webservices.data.core.enumeration.search.LogicalOperator;
import microsoft.exchange.webservices.data.core.service.folder.Folder;
import microsoft.exchange.webservices.data.core.service.item.EmailMessage;
import microsoft.exchange.webservices.data.core.service.item.Item;
import microsoft.exchange.webservices.data.core.service.schema.ItemSchema;
import microsoft.exchange.webservices.data.credential.WebCredentials;
import microsoft.exchange.webservices.data.property.complex.EmailAddress;
import microsoft.exchange.webservices.data.property.complex.ItemId;
import microsoft.exchange.webservices.data.search.FindItemsResults;
import microsoft.exchange.webservices.data.search.ItemView;
import microsoft.exchange.webservices.data.search.filter.SearchFilter;

@Component
public class MSExchangeEmailRepositories {

	private UserMail user = new UserMail();

	private StoreMail storeMail;
	private String body;
	private List<StoreMail> listStoreMail;
	private static ExchangeService service;
	private static Integer NUMBER_EMAILS_FETCH = 5; // Define a quantidade MAX de emails a ser buscado

	public MSExchangeEmailRepositories() {
		
		try {
			
		
			//service.setCredentials (new NetworkCredential(user.getUser(),  user.getPass());
			service.setCredentials(new WebCredentials(user.getUser(), user.getPass()));
			service.autodiscoverUrl(user.getDomain(), new RedirectionUrlCallback());	
			
		} catch (Exception ex) {
			System.out.println("Erro ao conectar ao E-mail"+ex.getMessage());
		}
		service.setTraceEnabled(true);
	}

	/**
	 * Primeiro, verifique se "https://webmail.xxxx.com/ews/Services.wsdl" e *
	 * "https://webmail.xxxx.com/ews/Exchange.asmx" está acessível, se sim,
	 * significa que o serviço Web do Exchange está ativado * no seu MS Exchange.
	 * 
	 */
	static {

		try {
			service = new ExchangeService(ExchangeVersion.Exchange2010_SP1);

		} catch (Exception e) {
			System.err.println("Erro ao ativar serviço Web do Exchange");
		}

	}

	/**
	 * Lendo um email de cada vez. Usando o ID do item do email. Criando um mapa de
	 * dados da mensagem como um valor de retorno.*
	 * 
	 */
	@SuppressWarnings("rawtypes")
	public Map<String, String> readEmailItem(ItemId itemId) {
		@SuppressWarnings("unchecked")
		Map<String, String> messageData = new HashMap();
		try {

			Item itm = Item.bind(service, itemId, PropertySet.FirstClassProperties);
			EmailMessage emailMessage = EmailMessage.bind(service, itm.getId());
			messageData.put("emailItemId", emailMessage.getId().toString());
			messageData.put("subject", emailMessage.getSubject().toString());
			messageData.put("fromAddress", emailMessage.getFrom().getAddress().toString());
			messageData.put("senderName", emailMessage.getSender().getName().toString());
			Date dateTimeCreated = emailMessage.getDateTimeCreated();
			messageData.put("SendDate", dateTimeCreated.toString());
			Date dateTimeRecieved = emailMessage.getDateTimeReceived();
			messageData.put("RecievedDate", dateTimeRecieved.toString());
			messageData.put("Size", emailMessage.getSize() + "");

			storeMail.setData(dateTimeRecieved);
			storeMail.setSubject(emailMessage.getSubject());
			this.body = emailMessage.getBody().toString();

		} catch (Exception e) {

			System.err.println("Erro ao ler EmailItem"+e.getMessage());

		}

		return messageData;

	}

	/**
	 * O número de e-mail que queremos ler é definido como NUMBER_EMAILS_FETCH, *
	 * 
	 * @throws ParseException
	 */

	public List<StoreMail> readEmails() {
		listStoreMail = new ArrayList<>();
		// List<String> msgDataList = new ArrayList<>();
		try {
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			Calendar now = Calendar.getInstance();
			
					
			Folder folder = Folder.bind(service, WellKnownFolderName.Inbox);
		  
		  
			SearchFilter searchFilterTipo = new SearchFilter.ContainsSubstring(ItemSchema.Subject,

					"Ativação / Suspensão de Contribuições");
			
			SearchFilter searchFilterDateMax = new SearchFilter.IsGreaterThanOrEqualTo(ItemSchema.DateTimeReceived,
					sdf.parse(df.format(now.getTime())));
//					sdf.parse("29/07/2018"));
			SearchFilter searchFilterDateMin = new SearchFilter.IsLessThan(ItemSchema.DateTimeReceived,
					sdf.parse(df.format(now.getTime())));
//					sdf.parse("07/07/2018"));
			
//			SearchFilter searchFilterDate = new SearchFilter.IsEqualTo(ItemSchema.DateTimeReceived,
////					sdf.parse(df.format(now.getTime())));
//					sdf.parse("06/07/2018"));
			

			SearchFilter searchFilterAll = new SearchFilter.SearchFilterCollection(LogicalOperator.And,
					searchFilterTipo, searchFilterDateMax);

			FindItemsResults<Item> results = service.findItems(folder.getId(), searchFilterAll,
					new ItemView(NUMBER_EMAILS_FETCH));

			int i = 0;

			for (Item item : results) {
				storeMail = new StoreMail();
				Map<String, String> messageData = new HashMap<String, String>();

				messageData = readEmailItem(item.getId());

				if (body != null) {
					storeMail.setNome(findDate("Cliente:"));
					storeMail.setCertificado(findDate("Certificado:"));
					storeMail.setCpf(findDate("CPF:"));
					storeMail.setTipo(findDate("Tipo:"));
					storeMail.setValorContribuicao((findDate("Contribuía com:")));
					System.out.println("\n");
					listStoreMail.add(storeMail);
				}

				if (storeMail.getSubject() != null) {
					if (storeMail.getSubject().contains("Ativação / Suspensão de Contribuições")) {
						i++;
						System.out.println(i+"--------------------------------------");
						System.out.println("Nome do Cliente: " + storeMail.getNome());
						System.out.println("Cpf: " + storeMail.getCpf());
						System.out.println("Certificado: " + storeMail.getCertificado());
						System.out.println("Contribuição: " + storeMail.getValorContribuicao());
						System.out.println("Tipo: " + storeMail.getTipo());
						System.out.println("Data: " + storeMail.getData());
					}
				}
				System.out.println("subject: " + storeMail.getSubject());
				System.out.println("\n");

			}

		} catch (Exception e) {

			System.out.println("opa deu erro: "+e);
			

		}
		if (!listStoreMail.isEmpty())
			System.out.println("Tamanho da lista: " + listStoreMail.size());
		

		return listStoreMail;

	}
	
	public List<StoreMail> readEmails(String initialDate, String  finalDate) {
		listStoreMail = new ArrayList<>();
		// List<String> msgDataList = new ArrayList<>();
		try {
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			Calendar now = Calendar.getInstance();
			
					
			Folder folder = Folder.bind(service, WellKnownFolderName.Inbox);
		  
		  
			SearchFilter searchFilterTipo = new SearchFilter.ContainsSubstring(ItemSchema.Subject,

					"Ativação / Suspensão de Contribuições");
			
			SearchFilter searchFilterDateInicial = new SearchFilter.IsGreaterThanOrEqualTo(ItemSchema.DateTimeReceived,
//					sdf.parse(df.format(now.getTime())));
					sdf.parse(initialDate));
			SearchFilter searchFilterDateFinal   = new SearchFilter.IsLessThanOrEqualTo   (ItemSchema.DateTimeReceived,
//					sdf.parse(df.format(now.getTime())));
					sdf.parse(finalDate));
			
//			SearchFilter searchFilterDate = new SearchFilter.IsEqualTo(ItemSchema.DateTimeReceived,
////					sdf.parse(df.format(now.getTime())));
//					sdf.parse(initialDate));
			

			SearchFilter searchFilterAll = new SearchFilter.SearchFilterCollection(LogicalOperator.And,
					searchFilterDateInicial, searchFilterDateFinal,searchFilterTipo);

			FindItemsResults<Item> results = service.findItems(folder.getId(), searchFilterAll,
					new ItemView(NUMBER_EMAILS_FETCH));

			int i = 0;

			for (Item item : results) {
				storeMail = new StoreMail();
				Map<String, String> messageData = new HashMap<String, String>();

				messageData = readEmailItem(item.getId());

				if (body != null) {
					storeMail.setNome(findDate("Cliente:"));
					storeMail.setCertificado(findDate("Certificado:"));
					storeMail.setCpf(findDate("CPF:"));
					storeMail.setTipo(findDate("Tipo:"));
					storeMail.setValorContribuicao((findDate("Contribuía com:")));
					System.out.println("\n");
					listStoreMail.add(storeMail);
				}

				if (storeMail.getSubject() != null) {
					if (storeMail.getSubject().contains("Ativação / Suspensão de Contribuições")) {
						i++;
						System.out.println(i+"--------------------------------------");
						System.out.println("Nome do Cliente: " + storeMail.getNome());
						System.out.println("Cpf: " + storeMail.getCpf());
						System.out.println("Certificado: " + storeMail.getCertificado());
						System.out.println("Contribuição: " + storeMail.getValorContribuicao());
						System.out.println("Tipo: " + storeMail.getTipo());
						System.out.println("Data: " + storeMail.getData());
					}
				}
				System.out.println("subject: " + storeMail.getSubject());
				System.out.println("\n");

			}

		} catch (Exception e) {

			System.out.println("opa deu erro: "+e);
			

		}

		System.out.println("Tamanho da lista: " + listStoreMail.size());
		

		return listStoreMail;

	}

	public String findDate(String str) {
		String strValue = null;
		String body = this.body;
		if (body.contains(str)) {
			// findDate o corpo onde começa a String
			String StringBody = body.substring(body.indexOf(str), body.length());
			// findDate o inicio do VALOR referente a String
			String str1 = StringBody.substring(StringBody.indexOf("<strong>") + 8, StringBody.length());
			int ocorrencia = str1.indexOf("</strong>");
			strValue = str1.substring(0, ocorrencia);
		}

		return strValue;

	}

//	public static void main(String[] args) {
//
//		MSExchangeEmailRepositories msees = new MSExchangeEmailRepositories();
//		//msees.readEmails();
//		List<StoreMail> list = msees.readEmails();
//
//		if (!list.isEmpty()) {
//			for (StoreMail storeMail : list) {
//				
//						System.out.println("Nome do Cliente: " + storeMail.getNome());
//						System.out.println("Cpf: " + storeMail.getCpf());
//						System.out.println("Certificado: " + storeMail.getCertificado());
//						System.out.println("Contribuição: " + storeMail.getValorContribuicao());
//						System.out.println("Tipo: " + storeMail.getTipo());
//						System.out.println("Data: " + storeMail.getData());
//						System.out.println("\n");
//					}
//					System.out.println("\n");
//				
//			}		
//		// msees.readAppointments();
//		try {
//			// msees.getAllMeetings();
//
//		} catch (Exception ex) {
//			System.out.println(ex.getMessage());
//			// Logger.getLogger(MSExchangeEmailService.class.getName()).log(Level.SEVERE,
//			// null, ex);
//
//		}
//
//	}

}
