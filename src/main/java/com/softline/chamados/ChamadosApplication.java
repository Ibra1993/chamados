package com.softline.chamados;


import com.softline.chamados.model.Usuarios;
import com.softline.chamados.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChamadosApplication{ // implements CommandLineRunner {

	@Autowired
	private UsuarioService usuarioService;
	public static void main(String[] args) {

		SpringApplication.run(ChamadosApplication.class, args);

	}


/*
	@Override
	public void run(String... args) throws Exception {
		// Criação do usuário
		Usuarios usuario = new Usuarios();
		usuario.setNome("Jaqueline");
		usuario.setEmail("jaqueline@gmail.com");
		usuario.setLogin("jaqueline");
		usuario.setPassword("jaqueline"); // Senha em texto plano
		usuario.setAtivo(true);
		usuario.setEmpresa("Soft Line");
		usuario.setCnpj("17.167.396/0001-69");

		// Salva o usuário com a senha criptografada
		usuarioService.salvarUsuarios(usuario);
	}
*/

}


