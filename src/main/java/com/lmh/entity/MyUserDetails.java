package com.lmh.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class MyUserDetails implements UserDetails {
	 
    private Usuario user;
     
    public MyUserDetails(Usuario user) {
        this.user = user;
    }
 
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
         
        authorities.add(new SimpleGrantedAuthority(user.getNombreusuario() == "admin" ? "ADMIN" : "JUGADOR"));
         
        return authorities;
    }
 
    @Override
    public String getPassword() {
        return user.getPassword();
    }
 
    public String getNombreusuario() {
        return user.getNombreusuario();
    }
 
    public boolean estaActivo() {
        return user.isActivo();
    }

	@Override
	public String getUsername() {
		return user.getNombreusuario();
	}
}
