package com.lmh.repository;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.lmh.entity.Usuario;

@Repository
public interface IUsuarioRepository  extends IBaseRepository<Usuario, Long>, QuerydslPredicateExecutor<Usuario> {
	public Usuario findByNombreusuario(String username);
}