package br.com.caixaseguradora.sinistro.resources;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import br.com.caixaseguradora.sinistro.domains.Sinistro;
import br.com.caixaseguradora.sinistro.services.SinistroService;

@RestController
@RequestMapping(value="/sinistros")
public class SinistroResource {
	
	@Autowired
	private SinistroService sinistroService;
	
	@RequestMapping(value="/teste", method = RequestMethod.GET)
	public String teste() {
		return "Rest Funcioanndo";
	}
	
	@RequestMapping(value="/cancelamentos", method = RequestMethod.GET)
	public ResponseEntity<?>findAll(){
		List<Sinistro> list  = sinistroService.findAll();

		for (Sinistro sinistro : list) {
			
			System.out.println("Canal: "+sinistro.getCanal());
		}
		
		return ResponseEntity.noContent().build();
		
	}
	
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public ResponseEntity<Page<Sinistro>> findPage(
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "size", defaultValue = "1") Integer size,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction,
			@RequestParam(value = "orderBy", defaultValue = "nomeCliente") String orderBy) {
		
		
		Page<Sinistro> list = sinistroService.findPage(page, size, direction, orderBy);
		//Page<Sinistro> listDTO = list.map(obj -> new ClienteDTO(obj));
		return ResponseEntity.ok().body(list);

	}
	
	@RequestMapping(value = "/batimento", method = RequestMethod.GET)
	public ResponseEntity<List<Sinistro>> batimento() {
		List<Sinistro> list = sinistroService.batimento();
		return ResponseEntity.ok().body(list);
	}
	
	public static void main(String[] args) {

		SinistroResource msees = new SinistroResource();		
		
		try {
			// msees.getAllMeetings();
			msees.batimento();

		} catch (Exception ex) {
			System.out.println("Erro "+ex.getMessage());
			// Logger.getLogger(MSExchangeEmailService.class.getName()).log(Level.SEVERE,
			// null, ex);

		}

	}
	
	@Value("${welcome.message:test}")
	private String message = "Hello World";

	@RequestMapping("/")
	public String welcome(Map<String, Object> model) {
		model.put("message", this.message);
		return "welcome";
	}
	
	@RequestMapping(value = "/page1", method = RequestMethod.GET)
	public String page1() {
		return "page1";
	}
	
	@GetMapping(value = "/page2")
	public String page2() {
		return "page2";
	}
}
