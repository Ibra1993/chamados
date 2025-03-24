package com.softline.chamados.service;

import com.softline.chamados.model.Papel;
import com.softline.chamados.repository.PapelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PapelService {

    @Autowired
    private PapelRepository papelRepository;


    public List<Papel> getAllPapeis() {

        return papelRepository.findAll();

    }
}
