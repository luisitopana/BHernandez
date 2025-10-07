package com.lmh.repository;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.lmh.entity.Usuario;

public interface IUsuarioRepository  extends IBaseRepository<Usuario, Long>, QuerydslPredicateExecutor<Usuario> {
	public Usuario findByNombreusuario(String username);
}