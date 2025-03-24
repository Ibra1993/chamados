package com.softline.chamados.service;

import com.softline.chamados.model.Assunto;
import com.softline.chamados.repository.AssuntoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssuntoService {

    @Autowired
    private AssuntoRepository assuntoRepository;

    public List<Assunto> getAllAssunto() {
        return assuntoRepository.findAll(Sort.by("descricao"));
    }

}
