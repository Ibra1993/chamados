package com.softline.chamados.jwt;

import com.softline.chamados.domain.AccessToken;
import com.softline.chamados.model.Papel;
import com.softline.chamados.model.Usuarios;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final SecretKeyGenerator keyGenerator;

    public AccessToken generateToken(Usuarios user){

        SecretKey key = keyGenerator.getKey();
        Date expirationDate = generateExpirationDate();
        var claims = generateTokenClaims(user);

        String token = Jwts
                .builder()
                .signWith(key)
                .subject(user.getEmail())
                .expiration(expirationDate)
                .claims(claims)
                .compact();

        return new AccessToken(token);

    }


    private Date generateExpirationDate(){

        var expirationMinutes = 60;
        LocalDateTime now = LocalDateTime.now().plusMinutes(expirationMinutes);
        return Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
    }

    private Map<String, Object> generateTokenClaims(Usuarios user){

        Map<String, Object> claims = new HashMap();
        claims.put("cnpj", user.getCnpj());
        claims.put("roles", user.getPapeis().stream().map(Papel::getPapel).toList());
        return claims;

    }



    public Map<String, Object> generateTokenClaimsFromToken(String token) {
        try {
            // Obtém a chave secreta
            SecretKey key = keyGenerator.getKey();

            // Cria o JwtParser usando Jwts.parser()
            JwtParser parser = Jwts.parser()
                    .verifyWith(key) // Configura a chave de verificação
                    .build();

            // Parseia o token e extrai os claims
            Claims claims = parser.parseSignedClaims(token).getPayload();

            // Retorna os claims
            return claims;
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Caso ocorra algum erro
        }
    }



}






