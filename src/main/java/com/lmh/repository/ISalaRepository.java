package com.lmh.repository;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.lmh.entity.Sala;

@Repository
public interface ISalaRepository  extends IBaseRepository<Sala, Integer>, QuerydslPredicateExecutor<Sala> {
	
}