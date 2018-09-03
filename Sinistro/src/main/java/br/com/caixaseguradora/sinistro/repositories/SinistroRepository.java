package br.com.caixaseguradora.sinistro.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.caixaseguradora.sinistro.domains.Sinistro;

@Repository
public interface SinistroRepository extends JpaRepository<Sinistro, Integer> {
		
	List<Sinistro> findByDataSolicitacao();
}
