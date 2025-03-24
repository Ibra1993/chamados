package com.softline.chamados.domain;

import com.softline.chamados.model.Usuarios;

public interface UserService {


   // Usuarios getByEmail(String email);
    Usuarios buscar_por_email(String email);


    AccessToken authenticate(String email, String password);


}
