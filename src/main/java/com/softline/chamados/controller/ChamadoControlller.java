package com.softline.chamados.controller;


import com.softline.chamados.configuration.NovoChamadoEvent;
import com.softline.chamados.exception.ChamadoNotFoundException;
import com.softline.chamados.model.*;
import com.softline.chamados.repository.*;
import com.softline.chamados.service.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@RestController
//@CrossOrigin(origins = "*") //, allowCredentials = "true"
@RequestMapping("/chamados")
public class ChamadoControlller {

    @Autowired
    private ChamadoRepository chamadoRepository;

    @Autowired
    private AssuntoRepository assuntoRepository;
    @Autowired
    private ColaboradorRepository colaboradorRepository;
    @Autowired
    private StatusChamadoRepository statusChamadoRepository;

    @Autowired
    private NovosClientesService novosClientesService;

    @Autowired
    private ChamadoService chamadoService;

    @Autowired
    private ColaboradorService colaboradorService;
    @Autowired
    private StatusChamadoService statuschamadoService;
    @Autowired
    private AssuntoService assuntoservice;

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private NovosClientesRepository novosClientesRepository;

    private final ApplicationEventPublisher eventPublisher;

    public ChamadoControlller(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @GetMapping("/user/userListChamados")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN', 'USERSOFTLINE')")
    public ResponseEntity<List<Chamado>>listarAll(@RequestParam int paginas, @RequestParam int itens) {

        return ResponseEntity.ok(chamadoService.getAllCall(paginas, itens));

    }



   @GetMapping("/implantacao/usuarioImplantacao")
   @PreAuthorize("hasAnyAuthority('IMPLANTACAO')")
    public ResponseEntity<List<Chamado>> listarChamadosImplantacao(@RequestParam int paginas, @RequestParam int itens) {

       return ResponseEntity.ok(chamadoService.getAllImplantacao(paginas, itens));

   }


    @GetMapping("/customizacao/usuarioCustomizacao")
    @PreAuthorize("hasAnyAuthority('CUSTOMIZACAO')")
    public ResponseEntity<List<Chamado>> listarChamadosCustomizacao(@RequestParam int paginas, @RequestParam int itens) {

        return ResponseEntity.ok(chamadoService.getAllCustomizacao(paginas, itens));

    }


  /* @GetMapping("/clientes/novosClientes")
    public ResponseEntity<List<NovosClientes>> listarNovosClientess(@RequestParam int paginas, @RequestParam int itens) {

       return ResponseEntity.ok(novosClientesService.listaDosNovosClientes(paginas, itens));


   }*/




    @GetMapping("/user/userListAssuntos")
    public List<Assunto> listarAssuntos() {

        return assuntoservice.getAllAssunto();
    }

    @GetMapping("/user/userListColaboradores")
    public List<Colaborador> listarColaboradores() {

        return colaboradorService.getAllColaborador();
    }

    @GetMapping("/user/userStatusChamados")
    public List<StatusChamado> listarStatusChamados() {

        return statuschamadoService.getAllStatusChamados();
    }


 @PostMapping("/admin/novosClientes")
   public ResponseEntity<NovosClientes> cadastrarNovosClientes(@RequestBody NovosClientes novosClientes){

     try {
         NovosClientes novoClienteSalvo = chamadoService.saveNovosClientes(novosClientes);
         return ResponseEntity.status(HttpStatus.CREATED).body(novoClienteSalvo);
     } catch (Exception e) {
         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
     }
   }


    @PostMapping(value = "/user/cadastrar",consumes = MediaType.MULTIPART_FORM_DATA_VALUE )  //{"multipart/form-data"})
    public ResponseEntity<Chamado> novoChamado(
            @RequestPart("chamado") Chamado chamado,
            @RequestPart(value = "file", required = false) MultipartFile file
            ) {


        try {


            // Atribuir os valores automaticamente
            chamado.setData(Date.valueOf(LocalDate.now())); // Atribui a data atual
            String TIME = String.valueOf(LocalTime.now().getHour()) + ":" + String.valueOf(LocalTime.now().getMinute());
            chamado.setHorario(TIME);
            chamado.setTicket(Long.toString(chamado.getnChamado())); // Gera e atribui o ticket baseado no número do chamado



            // Recuperar as entidades relacionadas (Assunto, StatusChamado e Colaborador)
            if (chamado.getAssuntos() != null && chamado.getAssuntos().getId() != null) {
                Assunto assunto = assuntoRepository.findById(chamado.getAssuntos().getId())
                        .orElseThrow(() -> new IllegalArgumentException("Assunto inválido: " + chamado.getAssuntos().getId()));
                chamado.setAssuntos(assunto);
            }

            if (chamado.getStatuschamados() != null && chamado.getStatuschamados().getId() != null) {
                StatusChamado statusChamado = statusChamadoRepository.findById(chamado.getStatuschamados().getId())
                        .orElseThrow(() -> new IllegalArgumentException("Status inválido: " + chamado.getStatuschamados().getId()));
                chamado.setStatuschamados(statusChamado);
            }

            if (chamado.getColaboradores() != null && chamado.getColaboradores().getId() != null) {
                Colaborador colaborador = colaboradorRepository.findById(chamado.getColaboradores().getId())
                        .orElseThrow(() -> new IllegalArgumentException("Colaborador inválido: " + chamado.getColaboradores().getId()));
                chamado.setColaboradores(colaborador);
            }

            // Salvar o chamado com arquivo (se houver)
            Chamado chamadoSalvo = chamadoService.salvarChamado(chamado, file);

            // Emitir evento do novo chamado
            eventPublisher.publishEvent(new NovoChamadoEvent(this, chamadoSalvo.getTicket()));


            return ResponseEntity.ok(chamadoSalvo);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @GetMapping("/user/me")
    public ResponseEntity<?> obterDadosDoUsuario(@RequestHeader("Authorization") String token) {
        try {
            // Remove o prefixo "Bearer " do token
            String tokenSemPrefixo = token.replace("Bearer ", "");

            // Obtém o email do usuário a partir do token JWT
            String email = chamadoService.obterEmailDoToken(tokenSemPrefixo);

            if (email == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido ou expirado.");
            }

            // Busca o usuário pelo email
            Usuarios usuario = usuariosRepository.findByEmail(email);
            if (usuario == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
            }

            // Retorna os dados do usuário (você pode personalizar os dados conforme necessário)
            return ResponseEntity.ok(usuario);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao obter dados do usuário.");
        }
    }



    @GetMapping("/implantacao/buscarImplantacao")
    public ResponseEntity<List<Chamado>> buscarImplantacao(@RequestParam("ticket") String ticket) {

        String recebeTickcet = ticket.trim();
        List<Chamado> chamados = chamadoService.listarImplantacaoChamados(recebeTickcet);
        if (chamados.isEmpty()) {
            return ResponseEntity.noContent().build(); // Retorna 204 se não houver resultados
        }

        return ResponseEntity.ok(chamados); // Retorna 200 com a lista de chamados


    }


    @GetMapping("/customizacao/buscarCustomizacao")
    public ResponseEntity<List<Chamado>> buscarcustomizacao(@RequestParam("ticket") String ticket) {

        String recebeTickcet = ticket.trim();
        List<Chamado> chamados = chamadoService.listarCustomizacaoChamados(recebeTickcet);
        if (chamados.isEmpty()) {
            return ResponseEntity.noContent().build(); // Retorna 204 se não houver resultados
        }

        return ResponseEntity.ok(chamados); // Retorna 200 com a lista de chamados


    }



    @GetMapping("/user/buscarChamado")   // Buscar chamado por ticket
     public ResponseEntity<List<Chamado>> listarPorTicket(@RequestParam("ticket") String ticket,
                                                @RequestHeader("Authorization") String token){


        System.out.println("Recebendo requisição para listar chamados...");

        // Extrai o CNPJ do token
        String cnpj = chamadoService.obterCnpjDoToken(token);
        System.out.println("CNPJ extraído do token: " + cnpj);

        if (cnpj == null) {
            System.out.println("Erro: CNPJ não encontrado no token!");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }


        // Chama o repositório para buscar os chamados pelo ticket

        String recebeTicket = ticket.trim();
        List<Chamado> chamados = chamadoRepository.findByTicketAndCnpj(recebeTicket, cnpj);
        //List<Chamado> chamados = chamadoRepository.findByTicket(recebeTickcet);

        // Verifica se há resultados
        if (chamados.isEmpty()) {
            return ResponseEntity.noContent().build(); // Retorna 204 se não houver resultados
        }

        return ResponseEntity.ok(chamados); // Retorna 200 com a lista de chamados

    }


    @PutMapping(value = "/user/atualizar/{id}") //, consumes = {"multipart/form-data"})
    public ResponseEntity<Chamado> atualizarChamado(
            @PathVariable Long id, // ID do chamado a ser atualizado
            @RequestBody @Valid Chamado chamado){ //,
            //@RequestPart(value = "file", required = false) MultipartFile file) {

        try {
            // Recuperar o chamado existente
            Chamado chamadoExistente = chamadoRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Chamado não encontrado: " + id));



            // Atualizar os dados do chamado
            if (chamado.getAssuntos() != null && chamado.getAssuntos().getId() != null) {
                Assunto assunto = assuntoRepository.findById(chamado.getAssuntos().getId())
                        .orElseThrow(() -> new IllegalArgumentException("Assunto inválido: " + chamado.getAssuntos().getId()));
                chamadoExistente.setAssuntos(assunto);
            }

            if (chamado.getStatuschamados() != null && chamado.getStatuschamados().getId() != null) {
                StatusChamado statusChamado = statusChamadoRepository.findById(chamado.getStatuschamados().getId())
                        .orElseThrow(() -> new IllegalArgumentException("Status inválido: " + chamado.getStatuschamados().getId()));
                chamadoExistente.setStatuschamados(statusChamado);
            }

            if (chamado.getColaboradores() != null && chamado.getColaboradores().getId() != null) {
                Colaborador colaborador = colaboradorRepository.findById(chamado.getColaboradores().getId())
                        .orElseThrow(() -> new IllegalArgumentException("Colaborador inválido: " + chamado.getColaboradores().getId()));
                chamadoExistente.setColaboradores(colaborador);
            }

            // Atualizar outros campos do chamado
            if (chamado.getJustificativa() != null || chamado.getReclamacao() == null) {
                chamadoExistente.setJustificativa(chamado.getJustificativa());
            }
            if (chamado.getReclamacao() != null || chamado.getReclamacao() == null) {
                chamadoExistente.setReclamacao(chamado.getReclamacao());
            }

            if(chamado.getEmpresa() != null || chamado.getEmpresa() == null){
                chamadoExistente.setEmpresa(chamado.getEmpresa());
            }

            // Atualizar o campo pesquisa
            if (chamado.getPesquisa() != null) {
                chamadoExistente.setPesquisa(chamado.getPesquisa());
            }


           // Chamado chamadoAtualizado = chamadoService.alterarChamado(chamadoExistente, file);
            Chamado chamadoAtualizado = chamadoService.alterarChamado(chamadoExistente);


            return ResponseEntity.ok(chamadoAtualizado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }





    // Endpoint para excluir um chamado pelo ID
    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<String> excluirChamado(@PathVariable Long id) {

        Optional<Chamado> chamado = chamadoRepository.findById(id);
        if (chamado.isPresent()) {
            chamadoRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Chamado excluído com sucesso!");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Chamado não encontrado.");
    }


    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("id") Long fileId) throws ChamadoNotFoundException {
        // Load file from database
        Chamado dbFile = chamadoService.getFile(fileId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(dbFile.getType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbFile.getName() + "\"")
                .body(new ByteArrayResource(dbFile.getArquivo()));
    }



    @Transactional
    @GetMapping("/user/userListChamadoss")
    public ResponseEntity<List<Chamado>> listarAll(
            @RequestHeader("Authorization") String token,
            @RequestParam int paginas,
            @RequestParam int itens) {

        System.out.println("Recebendo requisição para listar chamados...");

        // Extrai o CNPJ do token
        String cnpj = chamadoService.obterCnpjDoToken(token);
        System.out.println("CNPJ extraído do token: " + cnpj);

        if (cnpj == null) {
            System.out.println("Erro: CNPJ não encontrado no token!");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Tenta buscar os chamados
        try {
            List<Chamado> chamados = chamadoService.getAllCallByCnpj(paginas, itens, cnpj);
            System.out.println("Chamados encontrados: " + chamados.size());
            return ResponseEntity.ok(chamados);
        } catch (Exception e) {
            e.printStackTrace(); // Mostra o erro no console
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping("/user/softline/buscarChamados")   // Buscar chamado por ticket
    public ResponseEntity<List<Chamado>> listarPorTicket(@RequestParam("ticket") String ticket){

        // Chama o repositório para buscar os chamados pelo ticket

        String recebeTicket = ticket.trim();
        List<Chamado> chamados = chamadoRepository.findByTicket(recebeTicket);

        // Verifica se há resultados
        if (chamados.isEmpty()) {
            return ResponseEntity.noContent().build(); // Retorna 204 se não houver resultados
        }

        return ResponseEntity.ok(chamados); // Retorna 200 com a lista de chamados

    }

    @GetMapping("/clientes/novosClientess")
    public ResponseEntity<Map<String, Object>> listarNovosClientes(
            @RequestParam int paginas,
            @RequestParam int itens) {

        // Obtém a página de resultados
        Page<NovosClientes> pagina = novosClientesService.listaDosNovosClientess(paginas, itens);

        // Cria a resposta com os dados e informações de paginação
        Map<String, Object> resposta = new HashMap<>();
        resposta.put("clientes", pagina.getContent()); // Lista de clientes da página atual
        resposta.put("paginaAtual", pagina.getNumber()); // Página atual
        resposta.put("totalItens", pagina.getTotalElements()); // Total de itens no banco de dados
        resposta.put("totalPaginas", pagina.getTotalPages()); // Total de páginas

        return ResponseEntity.ok(resposta);

    }

   @GetMapping("/user/listarPorColaborador")
   public ResponseEntity<List<Chamado>> listarChamadosPorColaborador(@RequestParam("nome") String nome){


        String colaborador = nome.trim().toUpperCase();
       List<Chamado> chamados =  chamadoService.getAllChamados();

       // Cria uma nova lista para armazenar os chamados filtrados
       List<Chamado> chamadosFiltrados = new ArrayList<>();

       // List<Chamado> chamados =  chamadoService.getChamadosPorColaborador(colaborador);

       for(Chamado chmds : chamados){

           if (chmds.getColaboradores().getNome().equals(colaborador)) {
               chamadosFiltrados.add(chmds);


           }
       }



       if(chamados.isEmpty()){
           return ResponseEntity.noContent().build();
       }

       return ResponseEntity.ok(chamadosFiltrados);
      //  return ResponseEntity.ok(chamados); // Retorna 200 com a lista de chamados

   }

}





