package com.softline.chamados.service;

import com.softline.chamados.exception.ChamadoNotFoundException;
import com.softline.chamados.model.Chamado;
import com.softline.chamados.model.NovosClientes;
import com.softline.chamados.model.Usuarios;
import com.softline.chamados.repository.ChamadoRepository;
import com.softline.chamados.repository.NovosClientesRepository;
import com.softline.chamados.repository.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class ChamadoService {

    @Autowired
    private ChamadoRepository chamadoRepository;

    private UsuariosRepository usuariosRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private NovosClientesRepository novosClientesRepository;


    public List<Chamado> getAllCall(int paginas, int itens) {

       //return chamadoRepository.findAll(Sort.by(Sort.Direction.ASC, "data"));
        Sort sort = Sort.by(Sort.Direction.ASC, "nChamado");
        PageRequest pageRequest = PageRequest.of(paginas, itens, sort);


        return chamadoRepository.findAll(pageRequest).getContent();

    }


    public List<Chamado> getAllCallByCnpj(int paginas, int itens, String cnpj) {

        Sort sort = Sort.by(Sort.Direction.ASC, "nChamado");
        PageRequest pageRequest = PageRequest.of(paginas, itens, sort);

        return chamadoRepository.findByCnpj(cnpj, pageRequest).getContent();
    }



    public List<Chamado> getAllImplantacao(int paginas, int itens) {

        String assunto = "Implantação";
        List<Chamado> implantacao = chamadoRepository.findAll();
        List<Chamado> copia = new ArrayList<>();

        // Filtra os chamados com o assunto "Implantação"
        for (Chamado chamado : implantacao) {
            if (chamado.getAssuntos().getDescricao().equals(assunto)) {
                copia.add(chamado);
            }
        }

        // Ordena a lista por data
        copia.sort(Comparator.comparing(Chamado::getnChamado));

        // Aplica a lógica de paginação (caso necessário)
        int start = paginas * itens;
        int end = Math.min(start + itens, copia.size());
        List<Chamado> paginatedList = copia.subList(start, end);

        return paginatedList;
    }


    public List<Chamado> listarImplantacaoChamados(String ticket){

        String assunto = "Implantação";
        List<Chamado> implantacao = chamadoRepository.findAll();
        List<Chamado> copia = new ArrayList<>();

        // Filtra os chamados com o assunto "Implantação"
        for (Chamado chamado : implantacao) {
            if (chamado.getAssuntos().getDescricao().equals(assunto) && chamado.getTicket().equals(ticket)) {
                copia.add(chamado);
            }
        }

        // Ordena a lista por data
        copia.sort(Comparator.comparing(Chamado::getnChamado));
        return copia;

    }

    public List<Chamado> listarCustomizacaoChamados(String ticket){

        String assunto = "Customização";
        List<Chamado> implantacao = chamadoRepository.findAll();
        List<Chamado> copia = new ArrayList<>();

        // Filtra os chamados com o assunto "Implantação"
        for (Chamado chamado : implantacao) {
            if (chamado.getAssuntos().getDescricao().equals(assunto) && chamado.getTicket().equals(ticket)) {
                copia.add(chamado);
            }
        }

        // Ordena a lista por data
        copia.sort(Comparator.comparing(Chamado::getnChamado));

        return copia;

    }


    public List<Chamado> getAllCustomizacao(int paginas, int itens){

        String assunto = "Customização";

        List<Chamado> customizacao = chamadoRepository.findAll();
        List<Chamado> copia = new ArrayList<>();

        // Filtra os chamados com o assunto "Implantação"
        for (Chamado chamado : customizacao) {
            if (chamado.getAssuntos().getDescricao().equals(assunto)) {
                copia.add(chamado);
            }
        }

        // Ordena a lista por data
        copia.sort(Comparator.comparing(Chamado::getnChamado));

        // Aplica a lógica de paginação (caso necessário)
        int start = paginas * itens;
        int end = Math.min(start + itens, copia.size());
        List<Chamado> paginatedList = copia.subList(start, end);

        return paginatedList;

    }


    public List<Chamado> getAllChamados() {

        return chamadoRepository.findAll(Sort.by(Sort.Direction.ASC, "nChamado"));

    }

public NovosClientes saveNovosClientes(NovosClientes novosClientes){

        return novosClientesRepository.save(novosClientes);

}

    public Chamado salvarChamado(Chamado chamado, MultipartFile file) throws ChamadoNotFoundException, IOException {

        List<Chamado> ultimoChamado = getAllChamados();
        long ticket = 1004;
        if(ultimoChamado.isEmpty()){

            chamado.setnChamado(ticket);



            // chamado.setType(file.getContentType());
            // chamado.setArquivo(file.getBytes());
            // chamado.setName(file.getOriginalFilename());


        }else{

            chamado.setnChamado(ultimoChamado.getLast().getnChamado()+1);

            // chamado.setType(file.getContentType());
            // chamado.setArquivo(file.getBytes());
            // chamado.setName(file.getOriginalFilename());

        }



        if (file != null && !file.isEmpty()) {
            // Preencher os atributos relacionados ao arquivo
            chamado.setArquivo(file.getBytes());
            chamado.setName(file.getOriginalFilename());
            chamado.setType(file.getContentType());
        } else {
            // Se o arquivo for vazio ou ausente, definir os atributos como null
            chamado.setArquivo(null);
            chamado.setName(null);
            chamado.setType(null);


        }

        return chamadoRepository.save(chamado);

    }


    public Chamado getFile(Long fileId) throws ChamadoNotFoundException {
        return chamadoRepository.findById(fileId)
                .orElseThrow(() -> new ChamadoNotFoundException("Arquivo com id " + fileId + "não encontrado."));
    }


    public Chamado alterarChamado(Chamado chamado /*, MultipartFile file*/) throws IOException {


        /*if (file != null && !file.isEmpty()) {
            // Preencher os atributos relacionados ao arquivo
            chamado.setArquivo(file.getBytes());
            chamado.setName(file.getOriginalFilename());
            chamado.setType(file.getContentType());
        } else {
            // Se o arquivo for vazio ou ausente, definir os atributos como null
            chamado.setArquivo(null);
            chamado.setName(null);
            chamado.setType(null);


        }*/

        // Variável que indica o status
        String status = "Solucionado";

        // Variável que armazenará a data de conclusão
        String dataConclusao = null;

        if (chamado.getStatuschamados().getStatus() != null && chamado.getStatuschamados().getStatus().equals(status)){

            // Obtém a data atual
            LocalDate dataAtual = LocalDate.now();

            dataConclusao = String.valueOf(dataAtual);
            chamado.setDataConclusao(dataConclusao);

        }


        return chamadoRepository.save(chamado);

    }

    public String obterCnpjDoToken(String token) {

        String tokenWithoutBearer = token.startsWith("Bearer ") ? token.substring(7) : token;
        return usuarioService.pegarCnpj(tokenWithoutBearer);
    }

    public String obterEmailDoToken(String token) {

        String tokenWithoutBearer = token.startsWith("Bearer ") ? token.substring(7) : token;


        return usuarioService.pegarEmail(tokenWithoutBearer);

    }

  /*  public List<Chamado> getChamadosPorColaborador(String nome){

        return chamadoRepository.findByColaborador(nome);

    } */








}