package com.softline.chamados.component;

import com.softline.chamados.model.Papel;
import com.softline.chamados.repository.PapelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CarregadoraDados implements CommandLineRunner {

    @Autowired
    private PapelRepository papelRepository;

    @Override
    public void run(String... args) throws Exception {

        String[] papeis = {"ADMIN", "USER", "USERSOFTLINE", "IMPLANTACAO", "CUSTOMIZACAO"};

        for (String papelString: papeis) {
            Papel papel = papelRepository.findByPapel(papelString);
            if (papel == null) {
                papel = new Papel(papelString);
                papelRepository.save(papel);
            }
        }
    }

}
