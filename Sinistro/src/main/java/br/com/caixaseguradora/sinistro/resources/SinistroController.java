package br.com.caixaseguradora.sinistro.resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.primefaces.component.calendar.Calendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.caixaseguradora.sinistro.domains.CSVData;
import br.com.caixaseguradora.sinistro.domains.SinistroEmail;
import br.com.caixaseguradora.sinistro.services.SinistroEmailService;
import br.com.caixaseguradora.sinistro.services.SinistroService;
import javassist.expr.NewArray;

@Controller
public class SinistroController {
	int page = 0;

	@Autowired
	private SinistroService sinistroService;

	@Autowired
	private SinistroEmailService sinistroEmailService;

	
	
	@RequestMapping(value = "/batimento", method = RequestMethod.GET)
	public String batimento(Model model) {
		model.addAttribute("sinistros", sinistroService.batimento());
		return "index";
	}

	@Value("${index.message:test}")
	private String message = "Hello World";

	@GetMapping("/index")
	public String index(Model model) {
		model.addAttribute("sinistros", sinistroEmailService.findAll());
		return "index";
	}

	@GetMapping(value = "/lista")
	public String findPage(Model model, @RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "size", defaultValue = "10") Integer size,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction,
			@RequestParam(value = "orderBy", defaultValue = "dataSolicitacao") String orderBy) {

		Page<SinistroEmail> list = sinistroEmailService.findPage(page, size, direction, orderBy);
		model.addAttribute("sinistros", list);
		// Page<Sinistro> listDTO = list.map(obj -> new ClienteDTO(obj));
		return "consultaCancelamentos/consultas";

	}

	@RequestMapping(value = "/consulta", method = RequestMethod.GET)
	public String findProxPage(Model model, @RequestParam(defaultValue = "0") int page) {
		Page<SinistroEmail> list = sinistroEmailService.findAll(page, 4);
		model.addAttribute("sinistros", list);
		// Page<Sinistro> listDTO = list.map(obj -> new ClienteDTO(obj));
		return "consultaCancelamentos/consultas";
	}

	@RequestMapping("/proximaPage/")
	public String proximaPage() {
		page++;
		return "redirect:/?page=" + page + "&size=10";

	}

	@RequestMapping("/anteriorPage/")
	public String anteriorPage() {
		if (page != 0) {
			page--;
		}
		return "redirect:/?page=" + page + "&size=10";

	}

	@RequestMapping(value = "/findByDate", method = RequestMethod.GET)
	public String page1(Model model, @RequestParam Date initialDate, @RequestParam Date finalDate) {
		List<SinistroEmail> list = null;

		/**
		 * @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
		 *
		 *                     SimpleDateFormat sdf = new
		 *                     SimpleDateFormat("dd/MM/yyyy");
		 */

		list = (initialDate != null && finalDate != null) ? sinistroEmailService.findByDate(initialDate, finalDate)
				: sinistroEmailService.findAll();

		model.addAttribute("sinistros", list);

		return "index";
	}

	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/consultaDate", method = RequestMethod.GET)
	public String consultaDate(Model model, @RequestParam Date initialDate, @RequestParam Date finalDate) {

		if (initialDate == null || finalDate == null) {

		}

		if (initialDate.getDate() > finalDate.getDate()) {
			System.out.println("Data inicial maior que final");
		}
		List<SinistroEmail> list = sinistroEmailService.findByDate(initialDate, finalDate);
		model.addAttribute("sins", list);
		System.out.println("passe no byDate");
//		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//		String dtInitial = sdf.format(initialDate);
//		String dtFinal = sdf.format(initialDate);
		return "consultaDate";
//		return "index";
	}

	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public ResponseEntity<Object> downloadFile() throws IOException {
		FileWriter filewriter = null;
		try {
			CSVData csv1 = new CSVData();
			csv1.setId("1");
			csv1.setName("talk2amareswaran");
			csv1.setNumber("5601");

			CSVData csv2 = new CSVData();
			csv2.setId("2");
			csv2.setName("Amareswaran");
			csv2.setNumber("8710");

			List<CSVData> csvDataList = new ArrayList<>();
			csvDataList.add(csv1);
			csvDataList.add(csv2);

			StringBuilder filecontent = new StringBuilder("ID, NAME, NUMBER\n");
			for (CSVData csv : csvDataList) {
				filecontent.append(csv.getId()).append(",").append(csv.getName()).append(",").append(csv.getNumber())
						.append("\n");
			}

			String filename = "C:\\talk2amareswaran-downloads\\filedownload\\filedownload\\csvdata.csv";

			filewriter = new FileWriter(filename);
			filewriter.write(filecontent.toString());
			filewriter.flush();

			File file = new File(filename);

			InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));
			headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
			headers.add("Pragma", "no-cache");
			headers.add("Expires", "0");

			ResponseEntity<Object> responseEntity = ResponseEntity.ok().headers(headers).contentLength(file.length())
					.contentType(MediaType.parseMediaType("application/txt")).body(resource);
			return responseEntity;
		} catch (Exception e) {
			return new ResponseEntity<>("error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {
			if (filewriter != null)
				filewriter.close();
		}
	}

}
