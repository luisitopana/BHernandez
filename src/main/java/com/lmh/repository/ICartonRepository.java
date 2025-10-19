package com.lmh.repository;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.lmh.entity.Carton;

@Repository
public interface ICartonRepository  extends IBaseRepository<Carton, Integer>, QuerydslPredicateExecutor<Carton> {
	
}