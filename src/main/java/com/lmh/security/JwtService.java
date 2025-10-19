package com.lmh.security;

import java.util.Date;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secretKeyString;

    @Value("${jwt.expiration.ms}")
    private long jwtExpirationMs;

    /**
     * Genera un token JWT a partir de la información de autenticación del usuario.
     */
    public String generateToken(Authentication authentication) {
        // El "principal" es el objeto que representa al usuario autenticado.
        // A menudo es una instancia de UserDetails.
        String username = authentication.getName();

        // Recopila los roles/autoridades del usuario.
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(",")); // Ej: "ROLE_USER,ROLE_ADMIN"

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);

        return Jwts.builder()
                // 1. Establecer el "sujeto" del token (quién es el usuario)
                .subject(username)
                
                // 2. Añadir "claims" personalizados (información extra)
                // Aquí guardamos los roles para usarlos después en la autorización.
                .claim("roles", authorities)
                
                // 3. Establecer fechas de emisión y expiración
                .issuedAt(now)
                .expiration(expiryDate)
                
                // 4. Firmar el token con la clave secreta y el algoritmo
                .signWith(getSigningKey())
                
                // 5. Construir el string del token final
                .compact();
    }

    /**
     * Convierte la clave secreta (String en Base64) a un objeto SecretKey.
     */
    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(this.secretKeyString);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
