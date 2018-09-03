package br.com.caixaseguradora.sinistro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.caixaseguradora.sinistro.domains.Usuario;
import br.com.caixaseguradora.sinistro.repositories.UsuarioRepository;



@SpringBootApplication
public class SinistroApplication implements CommandLineRunner{
	@Autowired
	private UsuarioRepository ur;
	
	public static void main(String[] args) {
		SpringApplication.run(SinistroApplication.class, args);
		System.out.print(new BCryptPasswordEncoder().encode("123"));
		
	}

	@Override
	public void run(String... args) throws Exception {
		Usuario user = new Usuario();
		user.setLogin("Danilo");
		user.setNomeCompleto("Danilo dos Santos Oliveira");
		user.setSenha("$2a$10$CNhj3N8Rhyf9fVW./dNAlOoOHGyYRaGdnYN1XuQZjZMOvJYM66Yx2");
		ur.save(user);
		
	}
	
}
