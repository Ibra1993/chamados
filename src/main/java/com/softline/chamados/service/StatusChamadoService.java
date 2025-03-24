package com.softline.chamados.service;

import com.softline.chamados.model.StatusChamado;
import com.softline.chamados.repository.StatusChamadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatusChamadoService {

    @Autowired
    private StatusChamadoRepository statusChamadoRepository;

    public List<StatusChamado> getAllStatusChamados() {
        return statusChamadoRepository.findAll(Sort.by("status"));
    }

}
