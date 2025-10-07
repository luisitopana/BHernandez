package com.lmh.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.lmh.entity.MyUserDetails;
import com.lmh.entity.Usuario;
import com.lmh.repository.IUsuarioRepository;

public class UserDetailsServiceImpl implements UserDetailsService {

   @Autowired
   private IUsuarioRepository userRepository;
    
   @Override
   public UserDetails loadUserByUsername(String username)
           throws UsernameNotFoundException {
       Usuario user = userRepository.findByNombreusuario(username);
        
       if (user == null) {
           throw new UsernameNotFoundException("Could not find user");
       }
       
       user.setFechaultimoacceso(new Date());
       user = userRepository.save(user);
        
       return new MyUserDetails(user);
   }

}
