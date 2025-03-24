package com.softline.chamados.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CNPJ;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Entity(name = "usuarios")
public class Usuarios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3, message = "O nome deve ter no mínimo 3 caracteres")
    private String nome;

    @Email(message = "Email inválido")
    private String email;
    @NotEmpty(message = "O login deve ser informado")
    @Size(min = 4, message = "O loging deve ter no mínimo 4 caracteres")
    private String login;

    private boolean ativo;

    @NotEmpty(message = "A senha deve ser informado")
    @Size(min = 4, message = "A senha deve ter no mínimo 4 caracteres")
    private String password;

    private String empresa;

    @Column(name="cnpj")
    @CNPJ
    private String cnpj;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="usuario_papel",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "papel_id"))
    private List<Papel> papeis;

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

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
        this.nome = nome.trim().toUpperCase();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email.trim().toLowerCase();
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmpresa() {
        return empresa.toUpperCase();
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa.toUpperCase();
    }

    public List<Papel> getPapeis() {
        return papeis;
    }

    public void setPapeis(List<Papel> papeis) {
        this.papeis = papeis;
    }


    public @CNPJ String getCnpj() {
        return cnpj;
    }

    public void setCnpj(@CNPJ String cnpj) {
        this.cnpj = cnpj.trim();
    }

    public Usuarios(Long id, String nome, String email, String login, boolean ativo, String password, String empresa, String cnpj, List<Papel> papeis) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.login = login;
        this.ativo = ativo;
        this.password = password;
        this.empresa = empresa;
        this.cnpj = cnpj;
        this.papeis = papeis;
    }

    public Usuarios() {


    }


}