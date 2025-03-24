package com.softline.chamados.repository;

import com.softline.chamados.model.NovosClientes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NovosClientesRepository extends JpaRepository<NovosClientes, Long> {

}
