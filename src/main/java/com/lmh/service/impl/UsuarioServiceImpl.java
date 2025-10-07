package com.lmh.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lmh.entity.Usuario;
import com.lmh.repository.IUsuarioRepository;
import com.lmh.service.IUsuarioService;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class UsuarioServiceImpl extends BaseServiceImpl<Usuario, IUsuarioRepository> implements IUsuarioService {

}
