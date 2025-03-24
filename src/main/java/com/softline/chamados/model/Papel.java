package com.softline.chamados.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.Objects;

@Entity(name = "papeis")
public class Papel implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String papel;

    @JsonIgnore // Ignora a serialização da lista de usuários para evitar referências cíclicas
    @ManyToMany(mappedBy = "papeis", fetch = FetchType.EAGER)
    private List<Usuarios> usuarios;

    public Papel() {

    }

    public Papel(String papel) {
        super();
        this.papel = papel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPapel() {
        return papel;
    }

    public void setPapel(String papel) {
        this.papel = papel;
    }

    public List<Usuarios> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuarios> usuarios) {
        this.usuarios = usuarios;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Papel papel = (Papel) o;
        return Objects.equals(id, papel.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String getAuthority() {  // Retorna o nome no papel, acesso ou autorização.
        return this.papel;
    }
}