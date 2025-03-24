package com.softline.chamados.model;
import jakarta.persistence.*;
import org.hibernate.validator.constraints.br.CNPJ;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity(name = "novosclientes")
@Table(name = "novosclientes")
public class NovosClientes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomeEmpresa;
    private String ramo;
    private int qtdMaquinas;
    private int horasContratadas;
    private String endereco;
    private String pontoDeReferrencia;
    private String responsavel;
    private String telefone;
    private String observacaoImplantacao;
    private String observacaoTreinamento;

    @CNPJ
    private String cjpj;
    private Date dataInicialDaImplantacao;
    private Date dataInaguracao;
    private Date dataFinalDaImplantacao;
    private String localDoServidor;
    private String configuracaoServidor;
    private String migracaoDeDados;
    private String homologacaBoleto;

    private String assinaturaContratoDePrestacaoDeServico;
    private String pagamento;;
    private String servidorEmNuvemEmCotacao;
    private String configuracoesDoServidor;
    private List<String> quaisDadosAmigrar = new ArrayList<>();;
    private List<String> responsabilidadeDoCliente = new ArrayList<>();
    private List<String> modulos = new ArrayList<>();
    private List<String> treinamento = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeEmpresa() {
        return nomeEmpresa;
    }

    public void setNomeEmpresa(String nomeEmpresa) {
        this.nomeEmpresa = nomeEmpresa;
    }

    public String getRamo() {
        return ramo;
    }

    public void setRamo(String ramo) {
        this.ramo = ramo;
    }

    public int getQtdMaquinas() {
        return qtdMaquinas;
    }

    public void setQtdMaquinas(int qtdMaquinas) {
        this.qtdMaquinas = qtdMaquinas;
    }

    public int getHorasContratadas() {
        return horasContratadas;
    }

    public void setHorasContratadas(int horasContratadas) {
        this.horasContratadas = horasContratadas;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getPontoDeReferrencia() {
        return pontoDeReferrencia;
    }

    public void setPontoDeReferrencia(String pontoDeReferrencia) {
        this.pontoDeReferrencia = pontoDeReferrencia;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getObservacaoImplantacao() {
        return observacaoImplantacao;
    }

    public void setObservacaoImplantacao(String observacaoImplantacao) {
        this.observacaoImplantacao = observacaoImplantacao;
    }

    public String getObservacaoTreinamento() {
        return observacaoTreinamento;
    }

    public void setObservacaoTreinamento(String observacaoTreinamento) {
        this.observacaoTreinamento = observacaoTreinamento;
    }

    public String getCjpj() {
        return cjpj;
    }

    public void setCjpj(String cjpj) {
        this.cjpj = cjpj;
    }

    public Date getDataInicialDaImplantacao() {
        return dataInicialDaImplantacao;
    }

    public void setDataInicialDaImplantacao(Date dataInicialDaImplantacao) {
        this.dataInicialDaImplantacao = dataInicialDaImplantacao;
    }

    public Date getDataInaguracao() {
        return dataInaguracao;
    }

    public void setDataInaguracao(Date dataInaguracao) {
        this.dataInaguracao = dataInaguracao;
    }

    public Date getDataFinalDaImplantacao() {
        return dataFinalDaImplantacao;
    }

    public void setDataFinalDaImplantacao(Date dataFinalDaImplantacao) {
        this.dataFinalDaImplantacao = dataFinalDaImplantacao;
    }

    public String getLocalDoServidor() {
        return localDoServidor;
    }

    public void setLocalDoServidor(String localDoServidor) {
        this.localDoServidor = localDoServidor;
    }

    public String getConfiguracaoServidor() {
        return configuracaoServidor;
    }

    public void setConfiguracaoServidor(String configuracaoServidor) {
        this.configuracaoServidor = configuracaoServidor.toUpperCase();
    }

    public String getMigracaoDeDados() {
        return migracaoDeDados;
    }

    public void setMigracaoDeDados(String migracaoDeDados) {
        this.migracaoDeDados = migracaoDeDados.toUpperCase();
    }

    public String getHomologacaBoleto() {
        return homologacaBoleto;
    }

    public void setHomologacaBoleto(String homologacaBoleto) {
        this.homologacaBoleto = homologacaBoleto.toUpperCase();
    }

    public String getAssinaturaContratoDePrestacaoDeServico() {
        return assinaturaContratoDePrestacaoDeServico;
    }

    public void setAssinaturaContratoDePrestacaoDeServico(String assinaturaContratoDePrestacaoDeServico) {
        this.assinaturaContratoDePrestacaoDeServico = assinaturaContratoDePrestacaoDeServico;
    }

    public String getPagamento() {
        return pagamento;
    }

    public void setPagamento(String pagamento) {
        this.pagamento = pagamento;
    }

    public String getServidorEmNuvemEmCotacao() {
        return servidorEmNuvemEmCotacao;
    }

    public void setServidorEmNuvemEmCotacao(String servidorEmNuvemEmCotacao) {
        this.servidorEmNuvemEmCotacao = servidorEmNuvemEmCotacao;
    }

    public String getConfiguracoesDoServidor() {
        return configuracoesDoServidor;
    }

    public void setConfiguracoesDoServidor(String configuracoesDoServidor) {
        this.configuracoesDoServidor = configuracoesDoServidor;
    }

    public List<String> getQuaisDadosAmigrar() {
        return quaisDadosAmigrar;
    }

    public void setQuaisDadosAmigrar(List<String> quaisDadosAmigrar) {
        this.quaisDadosAmigrar = quaisDadosAmigrar;
    }

    public List<String> getResponsabilidadeDoCliente() {
        return responsabilidadeDoCliente;
    }

    public void setResponsabilidadeDoCliente(List<String> responsabilidadeDoCliente) {
        this.responsabilidadeDoCliente = responsabilidadeDoCliente;
    }

    public List<String> getModulos() {
        return modulos;
    }

    public void setModulos(List<String> modulos) {
        this.modulos = modulos;
    }

    public List<String> getTreinamento() {
        return treinamento;
    }

    public void setTreinamento(List<String> treinamento) {
        this.treinamento = treinamento;
    }

    public NovosClientes() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NovosClientes that = (NovosClientes) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }


}
