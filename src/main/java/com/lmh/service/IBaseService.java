package com.lmh.service;

import java.util.List;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;

public interface IBaseService<E> {

	List<E> search();
	
	List<E> search(Predicate p, OrderSpecifier o);

	void delete(E item);
	
	void delete(Predicate p);

	E save(E entity);
	
	List<E> search(Predicate predicate);
	
	List<E> search(List<Predicate> predicate);

	List<E> search(OrderSpecifier o);
	
	List<E> search(List<Predicate> predicate, OrderSpecifier... order);
	
	List<E> search(Predicate p, OrderSpecifier... o);
	
	List<E> search(OrderSpecifier... o);
}
