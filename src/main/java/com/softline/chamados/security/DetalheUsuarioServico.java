package com.softline.chamados.security;


import com.softline.chamados.model.Usuarios;
import com.softline.chamados.repository.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DetalheUsuarioServico implements UserDetailsService {

    @Autowired
    private UsuariosRepository usuarioRepository;

    public DetalheUsuarioServico(UsuariosRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Usuarios usuario = usuarioRepository.findByEmail(username);

        if(usuario != null && usuario.isAtivo()) {
            DetalheUsuario detalheUsuario = new DetalheUsuario(usuario);
            return detalheUsuario;
        } else {
            throw new UsernameNotFoundException("Usuário não encontrado");
        }


    }

}

