package com.softline.chamados.service;

import com.softline.chamados.model.Chamado;
import com.softline.chamados.model.NovosClientes;
import com.softline.chamados.repository.NovosClientesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class NovosClientesService {

    @Autowired
    private NovosClientesRepository novosClientesRepository;


 /*   public List<NovosClientes> listaDosNovosClientes(int paginas, int itens){

       List<NovosClientes> novos = novosClientesRepository.findAll();

        // Aplica a lógica de paginação (caso necessário)
        int start = paginas * itens;
        int end = Math.min(start + itens, novos.size());
        List<NovosClientes> paginatedList = novos.subList(start, end);

        return paginatedList;

    }
*/
    public Page<NovosClientes> listaDosNovosClientess(int paginas, int itens) {
        // Cria um objeto Pageable para a paginação
        Pageable pageable = PageRequest.of(paginas, itens);

        // Retorna a página de resultados
        return novosClientesRepository.findAll(pageable);
    }
}
