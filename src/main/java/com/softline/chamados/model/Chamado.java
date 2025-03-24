package com.softline.chamados.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.Email;
import org.hibernate.validator.constraints.br.CNPJ;
import org.springframework.lang.Nullable;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;


@Entity(name = "chamados")
@Table(name = "chamados")
public class Chamado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Transient
    private LocalDate data1 = LocalDate.now();

    //@Transient
   // LocalTime agora = LocalTime.now();

   // @Transient
    //int hora = agora.getHour();

    //@Transient
   // int minuto = agora.getMinute();

    // Variável que armazenará a data de conclusão
    String dataConclusao = null;


    private long dias;
    private Date data; // = Date.valueOf(data1);
    private  String horario; // = String.valueOf(hora) + ":" + String.valueOf(minuto);

    @Column(columnDefinition = "TEXT")
    private String justificativa;

    @Column(columnDefinition = "TEXT")
    private String reclamacao;

    @Column(name="empresa")
    private String empresa;

    private long nChamado;
    private String ticket;

    @Column(name="cnpj")
    @CNPJ
    private String cnpj;
    private String nome;

    @Email
    private String email;

    @Lob
    private byte[] arquivo;

    private String name;

    private String type;


    private String pesquisa;

    @ManyToOne
    @JoinColumn(name="assuntosid")
    private Assunto assuntos;

    @ManyToOne
    @JoinColumn(name="statuschamadoid")
    private StatusChamado statuschamados;


    @ManyToOne
    @JoinColumn(name="colaboradorid")
    private Colaborador colaboradores;

    public String getPesquisa() {
        return pesquisa;
    }

    public void setPesquisa(String pesquisa) {
        this.pesquisa = pesquisa;
    }

    public String getHorario() {

        return horario;

    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public Date getData() {

        return this.data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public LocalDate getData1() {
        return data1;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmpresa() {
        return empresa.toUpperCase();
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa.toUpperCase();
    }


    public long getDias() {

        // =====================   CÁLCULO DE DIAS     ========================
        LocalDate dataChamado = this.getData().toLocalDate();
        return this.dias = ChronoUnit.DAYS.between(dataChamado, this.getData1());
    }




    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getJustificativa() {
        return justificativa;
    }

    public void setJustificativa(String justificativa) {
        this.justificativa = justificativa;
    }

    public String getReclamacao() {
        return reclamacao;
    }

    public void setReclamacao(String reclamacao) {
        this.reclamacao = reclamacao;
    }



    public long getnChamado() {
        return this.nChamado;
    }




    public void setnChamado(long nChamado) {
       this.nChamado = nChamado;
        long ticket = nChamado;

        setTicket(Long.toString(ticket));
    }

    public String getTicket() {
        return ticket; // = Long.toString(getnChamado());
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public @CNPJ String getCnpj() {
        return cnpj;
    }

    public void setCnpj(@CNPJ String cnpj) {
        this.cnpj = cnpj;
    }

    public @Email String getEmail() {
        return email;
    }

    public void setEmail(@Email String email) {
        this.email = email;
    }

    public byte[] getArquivo() {
        return arquivo;
    }

    public void setArquivo(byte[] arquivo) {
        this.arquivo = arquivo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Assunto getAssuntos() {
        return assuntos;
    }

    public void setAssuntos(Assunto assuntos) {
        this.assuntos = assuntos;
    }

    public StatusChamado getStatuschamados() {
        return statuschamados;
    }

    public void setStatuschamados(StatusChamado statuschamados) {
        this.statuschamados = statuschamados;
    }

    public Colaborador getColaboradores() {
        return colaboradores;
    }

    public void setColaboradores(Colaborador colaboradores) {
        this.colaboradores = colaboradores;
    }


    public String getDataConclusao() {
        return dataConclusao;
    }

    public void setDataConclusao(String dataConclusao) {
        this.dataConclusao = dataConclusao;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Chamado chamado = (Chamado) o;
        return Objects.equals(id, chamado.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public Chamado() {

    }

}
