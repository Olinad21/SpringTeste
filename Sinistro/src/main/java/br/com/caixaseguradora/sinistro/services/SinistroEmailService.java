package br.com.caixaseguradora.sinistro.services;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import br.com.caixaseguradora.sinistro.domains.SinistroEmail;
import br.com.caixaseguradora.sinistro.repositories.SinistroEmailRepository;

@Service
public class SinistroEmailService {
	@Autowired
	private SinistroEmailRepository repo;

	public void add(SinistroEmail obj) {
		repo.save(obj);
	}

	public List<SinistroEmail> findAll() {
		return repo.findAll();
	}
	public Page<SinistroEmail> findAll(int page,int size) {
		return repo.findAll(PageRequest.of(page, size));
	}

	public Page<SinistroEmail> findPage(Integer page, Integer size, String direction, String orderBy) {

		PageRequest pageable = PageRequest.of(page, size, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageable);

	}

	public List<SinistroEmail> findByDate(Date initialDate, Date finalDate) {
		String dataInicial = getFormatDate(initialDate);
		String dataFinal= getFormatDate(finalDate);
		System.out.println(dataInicial);
		
		
		return repo.findByDataSolicitacao(initialDate, finalDate);

	}

	public Page<SinistroEmail> findPage(Integer page, Integer size, String direction, String orderBy, Date initialDate,
			Date finalDate) {

		PageRequest pageable = PageRequest.of(page, size, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageable);

	}
	
	public String getFormatDate(Date dt){
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String date = sdf.format(dt);
		String ano = date.substring(0, 4);
		String mes= date.substring(5, 7);
		String dia = date.substring(8, 10);
		String dataFormatada =  dia+ "/" + mes+ "/" + ano;
		return dataFormatada;
	}

}
