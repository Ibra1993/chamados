package com.softline.chamados.repository;


import com.softline.chamados.model.Chamado;
import com.softline.chamados.model.Usuarios;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChamadoRepository extends JpaRepository<Chamado, Long> {

    @Transactional
    @Query(value = "select * from chamados c where c.ticket = :ticket and c.cnpj = :cnpj", nativeQuery = true)
    List<Chamado> findByTicketAndCnpj(@Param("ticket") String ticket, @Param("cnpj") String cnpj);

    @Transactional
    @Query(value = "select * from chamados c where c.ticket = :ticket", nativeQuery = true )
    List<Chamado> findByTicket(@Param("ticket") String ticket);

   /* @Transactional
    @Query(value = "SELECT c.* FROM chamados c INNER JOIN colaboradores col ON c.colaboradorid = col.id WHERE col.nome = :nome", nativeQuery = true)
    @Query(value = "SELECT c.* FROM chamados c INNER JOIN colaboradores col ON c.colaboradorid = col.id WHERE UPPER(col.nome) = UPPER(:nome)", nativeQuery = true)
    List<Chamado> findByColaborador(@Param("nome") String nome); */


    Page<Chamado> findAll(Pageable paginacao);
    Page<Chamado> findByCnpj(String cnpj, Pageable pageable);


    @Query(value = "select count(*) from chamados", nativeQuery = true)
    long totalChamados();


    @Query(value = "select count(*) from chamados where statuschamadoid = 2", nativeQuery = true)
   long countChamadosEmAndamento();

    @Query(value = "select count(*) from chamados where statuschamadoid = 1", nativeQuery = true)
    long countChamadosEmAnalise();

    @Query(value = "select count(*) from chamados where statuschamadoid = 3", nativeQuery = true)
    long countChamadosEmTeste();
    @Query(value = "select count(*) from chamados where statuschamadoid = 4", nativeQuery = true)
    long countChamadosSolucionados();

    @Query(value = "select count(*) from chamados  where assuntosid = 1 and statuschamadoid = 1", nativeQuery = true)
    long countChmadosSuperteEmAberto();

    @Query(value = "select count(*) from chamados  where assuntosid = 2 and statuschamadoid = 1", nativeQuery = true)
    long countChmadosFiscalEmAberto();

    @Query(value = "select count(*) from chamados  where assuntosid = 3 and statuschamadoid = 1", nativeQuery = true)
    long countChmadosCobrancaEmAberto();

    @Query(value = "select count(*) from chamados  where assuntosid = 4 and statuschamadoid = 1", nativeQuery = true)
    long countChmadosCustomizacaoEmAberto();

    @Query(value = "select count(*) from chamados  where assuntosid = 5 and statuschamadoid = 1", nativeQuery = true)
    long countChmadosImplantacaoEmAberto();

    @Query(value = "select count(*) from chamados c where c.assuntosid = 6 and c.statuschamadoid = 1", nativeQuery = true)
    long countChmadosComercialEmAberto();



}
