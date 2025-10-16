package com.lmh.repository;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.lmh.entity.Patroncarton;

@Repository
public interface IPatroncartonRepository  extends IBaseRepository<Patroncarton, Integer>, QuerydslPredicateExecutor<Patroncarton> {
	
}