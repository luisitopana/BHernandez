package com.lmh;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Contraseña {

	public static void main(String[] args) {
		BCryptPasswordEncoder b = new BCryptPasswordEncoder();
		System.out.println(b.encode("admin2025"));
	}

}
