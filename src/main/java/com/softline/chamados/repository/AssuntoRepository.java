package com.softline.chamados.repository;

import com.softline.chamados.model.Assunto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssuntoRepository extends JpaRepository<Assunto, Long>{

}
