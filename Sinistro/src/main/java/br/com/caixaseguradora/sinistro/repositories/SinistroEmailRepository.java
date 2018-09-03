package br.com.caixaseguradora.sinistro.repositories;



import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.caixaseguradora.sinistro.domains.SinistroEmail;

@Repository
public interface SinistroEmailRepository extends JpaRepository<SinistroEmail, Integer> {
		
	List<SinistroEmail> findByDataSolicitacao(Date initialDate, Date finalDate);
	
}
