package com.softline.chamados.service;

import com.softline.chamados.jwt.JwtService;
import com.softline.chamados.model.Chamado;
import com.softline.chamados.model.Usuarios;
import com.softline.chamados.repository.UsuariosRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Collections;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class UsuarioService {

    @Autowired
    private UsuariosRepository usuariosRepository;

   @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private final JwtService jwtService; // Injeção do JwtService



    public Usuarios salvarUsuarios(Usuarios usuarios){


       // Criptografa a senha antes de salvar
        usuarios.setPassword(passwordEncoder.encode(usuarios.getPassword()));
       return usuariosRepository.save(usuarios);

   }

    public List<Usuarios> getAllUsers(){

        return usuariosRepository.findAll();
    }

   public Usuarios atualizarUsuarios(Usuarios usuarios){

       // Criptografa a senha antes de atualizar


      usuarios.setPassword(passwordEncoder.encode(usuarios.getPassword()));
      return usuariosRepository.save(usuarios);

   }

   public Usuarios buscarPorEmail(String email) {

       return usuariosRepository.getByEmail(email);

   }


    public String getCnpjFromToken(String token) {
        try {
            // Decodifica o token usando o método generateTokenClaims
            Map<String, Object> claims = jwtService.generateTokenClaimsFromToken(token);

            // Retorna o CNPJ armazenado nos claims
            return (String) claims.get("cnpj");
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Caso ocorra algum erro
        }
    }

    // Método para retornar o CNPJ
    public String pegarCnpj(String token) {
        return getCnpjFromToken(token);
    }

    public String getEmailFromToken(String token) {
        try {
            // Decodifica o token usando o método generateTokenClaims
            Map<String, Object> claims = jwtService.generateTokenClaimsFromToken(token);

            // Retorna o CNPJ armazenado nos claims
            return (String) claims.get("sub");
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Caso ocorra algum erro
        }
    }


    public String pegarEmail(String token) {
        return getEmailFromToken(token);
    }


}
