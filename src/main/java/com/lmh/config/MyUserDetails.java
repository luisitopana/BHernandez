package com.lmh.config;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.lmh.entity.Usuario;

public class MyUserDetails implements UserDetails {
	 
    private static final long serialVersionUID = 2761405145279982955L;
    
	private Usuario user;
     
    public MyUserDetails(Usuario user) {
        this.user = user;
    }
 
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(user.getNombreusuario() == "admin" ? "ADMIN" : "JUGADOR"));
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
