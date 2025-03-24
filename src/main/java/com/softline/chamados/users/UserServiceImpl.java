package com.softline.chamados.users;

import com.softline.chamados.domain.AccessToken;
import com.softline.chamados.domain.UserService;
import com.softline.chamados.jwt.JwtService;
import com.softline.chamados.model.Usuarios;
import com.softline.chamados.repository.UsuariosRepository;
import com.softline.chamados.service.UsuarioService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UsuariosRepository usuariosRepository;

    @Autowired
   private UsuarioService usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    @Override
    public Usuarios buscar_por_email(String email) {

        return usuarioService.buscarPorEmail(email);

    }

    @Override
    public AccessToken authenticate(String email, String password) {

        var Usuarios = buscar_por_email(email);

        if(Usuarios == null){

            return null;

        }

        boolean matches = passwordEncoder.matches(password, Usuarios.getPassword());
        if(matches){

            return jwtService.generateToken(Usuarios);
        }

        return null;
    }

}
