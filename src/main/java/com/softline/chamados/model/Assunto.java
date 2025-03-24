package com.softline.chamados.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity(name = "assuntos")
@Table(name = "assuntos")
public class Assunto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descricao;


    @OneToMany(mappedBy = "assuntos", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Chamado> chamados;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<Chamado> getChamados() {
        return chamados;
    }

    public void setChamados(List<Chamado> chamados) {
        this.chamados = chamados;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Assunto assunto = (Assunto) o;
        return Objects.equals(id, assunto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public Assunto() {

    }
}
