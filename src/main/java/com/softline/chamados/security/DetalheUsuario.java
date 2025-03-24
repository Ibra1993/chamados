package com.softline.chamados.security;

import com.softline.chamados.model.Papel;
import com.softline.chamados.model.Usuarios;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DetalheUsuario implements UserDetails {

    private Usuarios usuario;

    public DetalheUsuario(Usuarios usuario) {
        super();
        this.usuario = usuario;
    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<Papel> papeis = usuario.getPapeis();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        for(Papel papel: papeis) {
            SimpleGrantedAuthority sga = new SimpleGrantedAuthority(papel.getPapel());
            authorities.add(sga);
        }

        return authorities;

    }

    @Override
    public String getPassword() {
        return usuario.getPassword();
    }

    @Override
    public String getUsername() {

        return usuario.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {

        return true;
    }

    @Override
    public boolean isEnabled() {
        return usuario.isAtivo();
    }
}
