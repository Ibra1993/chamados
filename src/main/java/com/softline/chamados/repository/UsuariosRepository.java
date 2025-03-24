package com.softline.chamados.repository;


import com.softline.chamados.model.Chamado;
import com.softline.chamados.model.Usuarios;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuariosRepository extends JpaRepository<Usuarios, Long> {


    @Query(value = "select * from usuarios u where u.login = :login", nativeQuery = true)
    Usuarios findByLogin(String login);


    @Transactional
    @Query(value = "select * from usuarios u where u.cnpj = :cnpj", nativeQuery = true )
    List<Usuarios> findByCnpj(@Param("cnpj") String cnpj);

    @Query(value = "select * from usuarios u where u.email = :email", nativeQuery = true)
    Usuarios findByEmail(String email);

    Usuarios getByEmail(String email);



}
