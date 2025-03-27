package com.softline.chamados.controller;

import com.softline.chamados.repository.ChamadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
//@CrossOrigin(origins = "*")
@RequestMapping("/chamados/relatorio")
public class RelatorioController {

    @Autowired
    private ChamadoRepository chamadoRepository;

    @GetMapping("/statusDosChamados")
    @PreAuthorize("hasAnyAuthority('ADMIN')") // Exige uma das autoridades
    public Map<String, Object> exibirRelatorioStatusDosChamados() {

        Map<String, Object> response = new HashMap<>();
        // if(chamadoRepository.countChamadosEmAndamento() == 0){


        if (chamadoRepository.totalChamados() == 0) {

            response.put("EmAndamento", 0);
            response.put("EmFaseDeTeste", 0);
            response.put("Resolvido", 0);
            response.put("EmAnalise", 0);

        } else {

            double EmAndamento = ((double) chamadoRepository.countChamadosEmAndamento() / chamadoRepository.totalChamados()) * 100;
            double EmFaseDeTeste = ((double) chamadoRepository.countChamadosEmTeste() / chamadoRepository.totalChamados()) * 100;
            double Resolvido = ((double) chamadoRepository.countChamadosSolucionados() / chamadoRepository.totalChamados()) * 100;
            double EmAnalise = ((double) chamadoRepository.countChamadosEmAnalise() / chamadoRepository.totalChamados()) * 100;

            response.put("EmAndamento", EmAndamento);
            response.put("EmFaseDeTeste", EmFaseDeTeste);
            response.put("Resolvido", Resolvido);
            response.put("EmAnalise", EmAnalise);

        }
        return response;
    }



    @GetMapping("/statusAssuntosChamados")
    @PreAuthorize("hasAnyAuthority('ADMIN')") // Exige uma das autoridades
    public Map<String, Object> exibirRelatorioTodosAssuntosEmAberto() {

        Map<String, Object> response = new HashMap<>();

        if (chamadoRepository.totalChamados() == 0) {

            response.put("suporte", 0);
            response.put("fiscal", 0);
            response.put("cobranca", 0);
            response.put("customizacao", 0);
            response.put("implantacao", 0);
            response.put("comercial", 0);
        }else{

            double suporte = ((double) chamadoRepository.countChmadosSuperteEmAberto() / chamadoRepository.totalChamados()) * 100;
            double fiscal = ((double) chamadoRepository.countChmadosFiscalEmAberto() / chamadoRepository.totalChamados()) * 100;
            double cobranca = ((double) chamadoRepository.countChmadosCobrancaEmAberto() / chamadoRepository.totalChamados()) * 100;
            double customizacao = ((double) chamadoRepository.countChmadosCustomizacaoEmAberto() / chamadoRepository.totalChamados()) * 100;
            double implantacao = ((double) chamadoRepository.countChmadosImplantacaoEmAberto() / chamadoRepository.totalChamados()) * 100;
            double comercial = ((double) chamadoRepository.countChmadosComercialEmAberto() / chamadoRepository.totalChamados()) * 100;



            response.put("suporte", suporte);
            response.put("fiscal", fiscal);
            response.put("cobranca", cobranca);
            response.put("customizacao", customizacao);
            response.put("implantacao", implantacao);
            response.put("comercial", comercial);

        }

        return response;
    }

    @GetMapping("/avaliacaoSatisfacaoDoCliente")
    @PreAuthorize("hasAnyAuthority('ADMIN')") // Exige uma das autoridades
    public  Map<String, Object> avaliacaoSatisfacaoDoCliente(){

        Map<String, Object> response = new HashMap<>();

        if (chamadoRepository.totalChamados() == 0){

            response.put("excelente", 0);
            response.put("bom", 0);
            response.put("ruim", 0);
            response.put("regular", 0);

        }else{

            double excelente = ((double) chamadoRepository.countChamadosExcelente() / chamadoRepository.totalChamados()) * 100;
            double bom = ((double) chamadoRepository.countChamadosBom() / chamadoRepository.totalChamados()) * 100;
            double ruim = ((double) chamadoRepository.countChamadosRuim() / chamadoRepository.totalChamados()) * 100;
            double regular = ((double) chamadoRepository.countChamadosRegular() / chamadoRepository.totalChamados()) * 100;


            response.put("excelente", excelente);
            response.put("bom", bom);
            response.put("ruim", ruim);
            response.put("regular", regular);

        }

        return response;

    }

}