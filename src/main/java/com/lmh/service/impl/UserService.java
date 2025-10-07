package com.lmh.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lmh.entity.Usuario;
import com.lmh.repository.IUsuarioRepository;

@Service
public class UserService {

    private IUsuarioRepository userRepository;

    @Autowired
    public UserService(IUsuarioRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Usuario findByNombreusuario(String userName) {
        return userRepository.findByNombreusuario(userName);
    }

}
