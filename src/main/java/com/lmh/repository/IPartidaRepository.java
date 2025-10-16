package com.lmh.repository;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.lmh.entity.Partida;

@Repository
public interface IPartidaRepository  extends IBaseRepository<Partida, Integer>, QuerydslPredicateExecutor<Partida> {
	
}