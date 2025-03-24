package com.softline.chamados.repository;

import com.softline.chamados.model.Papel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PapelRepository extends JpaRepository<Papel, Long> {

    Papel findByPapel(String papel);

}
