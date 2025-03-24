package com.softline.chamados.model;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity(name = "colaboradores")
@Table(name = "colaboradores")
public class Colaborador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;


    @OneToMany(mappedBy = "colaboradores", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Chamado> chamados;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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
        Colaborador that = (Colaborador) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public Colaborador() {

    }

}
