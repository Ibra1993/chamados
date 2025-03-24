package com.softline.chamados.controller;


import com.softline.chamados.domain.UserService;
import com.softline.chamados.exception.DuplicatedTupleException;
import com.softline.chamados.jwt.JwtService;
import com.softline.chamados.model.Chamado;
import com.softline.chamados.model.CredentialsDTO;
import com.softline.chamados.model.Papel;
import com.softline.chamados.model.Usuarios;
import com.softline.chamados.repository.PapelRepository;
import com.softline.chamados.repository.UsuariosRepository;
import com.softline.chamados.service.ChamadoService;
import com.softline.chamados.service.PapelService;
import com.softline.chamados.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.util.*;


@RequiredArgsConstructor
@RestController
//@CrossOrigin(origins = "*")
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PapelService papelService;

    @Autowired
    private PapelRepository papelRepository;

    private final UserService userService;

    @Autowired
    private final JwtService jwtService; // Injeção do JwtService

    @Autowired
    private ChamadoService chamadoService;

    @PostMapping("/salvar")
    public ResponseEntity<Usuarios> gravarUsuarios(@Valid @RequestBody Usuarios usuarios) {
        // Verifica se o usuário já existe
        // Usuarios user = usuariosRepository.findByLogin(usuarios.getLogin());


        // Verifica se o usuário já existe
        Usuarios user = usuariosRepository.findByEmail(usuarios.getEmail());

        if (user != null) {
            // Retorna erro com status HTTP 400 (Bad Request)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();


        }

        // Busca o papel "USER" e adiciona ao usuário
        Papel papel = papelRepository.findByPapel("USER");
        if (papel == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        List<Papel> papeis = new ArrayList<Papel>();
        papeis.add(papel);
        usuarios.setPapeis(papeis); // associa o papel de USER ao usuário

        //String senhaCriptografia = criptografia.encode(usuarios.getPassword());
        //usuarios.setPassword(senhaCriptografia);

        // Salva o usuário

        Usuarios usuarioSalvo = usuarioService.salvarUsuarios(usuarios);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioSalvo);
    }


    @GetMapping("/admin/listarUsuarios")
    @PreAuthorize("hasAuthority('ADMIN')") // Exige a autoridade ADMIN
    public List<Usuarios> getUsuarios() {

        return usuarioService.getAllUsers();

    }


    @GetMapping("/admin/listarPapeis")
    public List<Papel> getPapeis() {

        return papelService.getAllPapeis();

    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Usuarios> editarUsuario(@PathVariable Long id, @Valid @RequestBody Usuarios usuarioAtualizado) {
        try {
            // Busca o usuário existente pelo ID
            Usuarios usuarioExistente = usuariosRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado: " + id));

            // Atualiza os atributos do usuário
            usuarioExistente.setNome(usuarioAtualizado.getNome());
            usuarioExistente.setEmail(usuarioAtualizado.getEmail());
            usuarioExistente.setLogin(usuarioAtualizado.getLogin());
            usuarioExistente.setAtivo(usuarioAtualizado.isAtivo());
            usuarioExistente.setPassword(usuarioAtualizado.getPassword());
            usuarioExistente.setEmpresa(usuarioAtualizado.getEmpresa());
            usuarioExistente.setCnpj(usuarioAtualizado.getCnpj().trim());

            // Salva as alterações
            Usuarios usuarioSalvo = usuarioService.atualizarUsuarios(usuarioExistente);

            return ResponseEntity.ok(usuarioSalvo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Caso o usuário não seja encontrado
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); // Qualquer outro erro
        }
    }


    @PutMapping("/editarPapel/{idUsuario}")
    public ResponseEntity<?> atribuirPapel(@PathVariable Long idUsuario, @RequestParam(value = "pps", required = false) int[] pps,
                                           @Valid @RequestBody(required = false) Usuarios usuarios) {

        // Verifica se o array de papéis é nulo ou vazio
        if (pps == null || pps.length == 0) {
            return ResponseEntity.badRequest().body("Pelo menos um papel deve ser informado.");

            //ResponseEntity.badRequest().body("Pelo menos um papel deve ser informado.");
        }

        // Obtem a lista de papéis a partir dos IDs fornecidos
        List<Papel> papeis = new ArrayList<>();
        for (int idPapel : pps) {
            Optional<Papel> papelOptional = papelRepository.findById((long) idPapel);
            if (papelOptional.isPresent()) {
                papeis.add(papelOptional.get());
            } else {
                return ResponseEntity.badRequest().body("Papel com ID " + idPapel + " não encontrado.");
            }
        }

        // Busca o usuário pelo ID
        Optional<Usuarios> usuarioOptional = usuariosRepository.findById(idUsuario);
        if (usuarioOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // Relaciona os papéis ao usuário e salva no banco
        Usuarios usr = usuarioOptional.get();
        usr.setPapeis(papeis);
        usuariosRepository.save(usr);

        // Retorna o usuário atualizado com os papéis atribuídos
        return ResponseEntity.ok(usr);
    }


    @GetMapping("/admin/buscarUsuarios")
    public ResponseEntity<List<Usuarios>> buscarUsuarios(@RequestParam("cnpj") String cnpj) {

        String recebeCnpj = cnpj.trim();
        List<Usuarios> usuario = usuariosRepository.findByCnpj(recebeCnpj);

        if (usuario.isEmpty()) {
            return ResponseEntity.noContent().build(); // Retorna 204 se não houver resultados
        }

        return ResponseEntity.ok(usuario); // Retorna 200 com a lista de chamados

    }

    @PostMapping("/login")
    public ResponseEntity authenticate(@RequestBody CredentialsDTO credentials) {


        var token = userService.authenticate(credentials.getEmail().trim(), credentials.getPassword().trim());
        if (token == null) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }



        Map<String, String> response = new HashMap<>();
        response.put("token", token.getAccessToken());

        String email = credentials.getEmail();


        return ResponseEntity.ok(response);

    }


    @GetMapping("/listarCnpj")
    public ResponseEntity<String> listarCnpj(@RequestHeader("Authorization") String token) {


        String cnpj = chamadoService.obterCnpjDoToken(token);

        if (cnpj == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok(cnpj);

    }


}
