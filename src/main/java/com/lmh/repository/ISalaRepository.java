package com.lmh.repository;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.lmh.entity.Sala;

public interface ISalaRepository  extends IBaseRepository<Sala, Long>, QuerydslPredicateExecutor<Sala> {
	
}