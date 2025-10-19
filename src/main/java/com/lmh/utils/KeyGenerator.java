package com.lmh.utils;

import java.util.Base64;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Jwts;

public class KeyGenerator {

	public static void main(String[] args) {
		// Genera una clave segura para el algoritmo HS256
		SecretKey key = Jwts.SIG.HS256.key().build();
		String base64Key = Base64.getEncoder().encodeToString(key.getEncoded());
		System.out.println("Tu clave secreta es: " + base64Key);
	}
}