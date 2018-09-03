package br.com.caixaseguradora.sinistro.resources;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;
import br.com.caixaseguradora.sinistro.domains.StoreMail;
import br.com.caixaseguradora.sinistro.services.MSExchangeEmailService;

@Controller
public class MSExchangeEmailResource {

	@Autowired
	private MSExchangeEmailService sinistroService;

	public List<StoreMail> readMail() {
		return sinistroService.readMails();
	}

	@RequestMapping(value = "/emails", method = RequestMethod.GET)
	public String readMails(Model model) {
		model.addAttribute("emails", sinistroService.readMails());
		return "email/emails";
	}

	@RequestMapping(value = "/test01")
	public RedirectView handleTestRequest(Model model) {

		RedirectView rv = new RedirectView();
		rv.setContextRelative(true);
		rv.setUrl("/test2");
		return rv;
	}

	@RequestMapping("/listar")
	public String handleRequest(ModelMap model) {
		model.addAttribute("emails");
		return "email/emails";
	}

	@RequestMapping(value = "/emailByDate", method = RequestMethod.GET)
	public String page1(ModelMap modelMap, 
						@RequestParam String initialDate, 
						@RequestParam String finalDate) {
		List<StoreMail> list = null;
		
		list = ((!initialDate.isEmpty() && finalDate.isEmpty())	||
			   (!initialDate.equals("") && !finalDate.equals(""))) ? 
				sinistroService.readMailByDate(initialDate, finalDate) : sinistroService.readMails();
		
		modelMap.addAttribute("emails", list);
		return "email/emails";
	}

}
