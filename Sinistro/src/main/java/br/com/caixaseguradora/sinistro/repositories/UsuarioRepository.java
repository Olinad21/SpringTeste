package br.com.caixaseguradora.sinistro.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import br.com.caixaseguradora.sinistro.domains.Usuario;

//
//public interface UsuarioRepository extends JpaRepository<Usuario, String>{
//
//	Usuario findByLogin(String login);
//}
public interface UsuarioRepository extends CrudRepository<Usuario, String>{

	Usuario findByLogin(String login);
}
