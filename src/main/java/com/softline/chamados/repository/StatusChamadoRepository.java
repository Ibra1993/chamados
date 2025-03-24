package com.softline.chamados.repository;

import com.softline.chamados.model.StatusChamado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusChamadoRepository extends JpaRepository<StatusChamado, Long> {

}
