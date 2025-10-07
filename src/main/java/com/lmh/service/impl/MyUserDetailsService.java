package com.lmh.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.lmh.entity.Usuario;

import jakarta.transaction.Transactional;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
    private UserService userService;

	//private IRolusuarioService rolUsuarioService;
	
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Usuario user = userService.findByNombreusuario(userName);
        
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(userName == "admin" ? "ADMIN" : "JUGADOR"));
        
        List<GrantedAuthority> authorities = grantedAuthorities;
        return buildUserForAuthentication(user, authorities);
    }

    private UserDetails buildUserForAuthentication(Usuario user, List<GrantedAuthority> authorities) {
        return new org.springframework.security.core.userdetails.User(user.getNombreusuario(), user.getPassword(),
          true,  true, true, true, authorities);
    }
}
