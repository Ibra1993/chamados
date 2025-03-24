package com.softline.chamados.service;

import com.softline.chamados.model.Colaborador;
import com.softline.chamados.repository.ColaboradorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ColaboradorService {

    @Autowired
    private ColaboradorRepository collaboradorRepository;

    public List<Colaborador> getAllColaborador(){

        return collaboradorRepository.findAll(Sort.by("nome"));
    }
}
