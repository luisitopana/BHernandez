package com.lmh.service.impl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lmh.repository.IBaseRepository;
import com.lmh.service.IBaseService;
import com.lmh.utils.BeanFactory;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class BaseServiceImpl<E, R> implements IBaseService<E>{

	@Override
	public List<E> search() {
		IBaseRepository baseRepo = getRepository();
		Iterable<E> iterable = baseRepo.findAll();
		List<E> list = new ArrayList<>();
		iterable.forEach(list::add);

		return list;
	} 
	
	@Override
	public List<E> search(Predicate p, OrderSpecifier o) {
		if(p == null) {
			return search(o);
		}
		IBaseRepository baseRepo = getRepository();
		Iterable<E> iterable = baseRepo.findAll(p, o);
		List<E> list = new ArrayList<>();
		iterable.forEach(list::add);

		return list;
	}
	
	@Override
	public List<E> search(Predicate p, OrderSpecifier... o) {
		if(p == null) {
			return search(o);
		}
		IBaseRepository baseRepo = getRepository();
		Iterable<E> iterable = baseRepo.findAll(p, o);
		List<E> list = new ArrayList<>();
		iterable.forEach(list::add);

		return list;
	}
	
	@Override
	public List<E> search(OrderSpecifier o) {
		IBaseRepository baseRepo = getRepository();
		Iterable<E> iterable = baseRepo.findAll(o);
		List<E> list = new ArrayList<>();
		iterable.forEach(list::add);

		return list;
	}
	
	@Override
	public List<E> search(OrderSpecifier... o) {
		IBaseRepository baseRepo = getRepository();
		Iterable<E> iterable = baseRepo.findAll(o);
		List<E> list = new ArrayList<>();
		iterable.forEach(list::add);

		return list;
	}

	@Override
	public void delete(E entity){
		IBaseRepository baseRepo = getRepository();
		baseRepo.delete(entity);
	}
	
	@Override
	public void delete(Predicate p) {
		IBaseRepository baseRepo = getRepository();
		baseRepo.delete(p);
	}

	@Override
	public E save(E entity) {
		IBaseRepository baseRepo = getRepository();
		return (E) baseRepo.save(entity);
	}
	
	@Override
	public List<E> search(Predicate p) {
		if(p == null) {
			return search();
		}
		
		IBaseRepository baseRepo = getRepository();
		Iterable<E> iterable = baseRepo.findAll(p);
		List<E> list = new ArrayList<>();
		iterable.forEach(list::add);

		return list;
	}
	
	@Override
	public List<E> search(List<Predicate> predicate) {
		if(predicate == null || predicate.size() == 0) {
			return search();
		}
		
		BooleanBuilder builder = new BooleanBuilder();
		Predicate p = null;
		
		for (int i = 0; i < predicate.size(); i++) {
			builder.and(predicate.get(i));
		}
		
		IBaseRepository baseRepo = getRepository();
		Iterable<E> iterable = baseRepo.findAll(builder);
		List<E> list = new ArrayList<>();
		iterable.forEach(list::add);

		return list;
	}
	
	@Override
	public List<E> search(List<Predicate> predicate, OrderSpecifier... order) {
		if(predicate == null || predicate.size() == 0) {
			return search();
		}
		
		BooleanBuilder builder = new BooleanBuilder();
		Predicate p = null;
		
		for (int i = 0; i < predicate.size(); i++) {
			builder.and(predicate.get(i));
		}
		
		IBaseRepository baseRepo = getRepository();
		Iterable<E> iterable = baseRepo.findAll(builder, order);
		List<E> list = new ArrayList<>();
		iterable.forEach(list::add);

		return list;
	}

	public IBaseRepository getRepository() {
		Type genericSuperClass = getClass().getGenericSuperclass();

		ParameterizedType parametrizedType = null;
		while (parametrizedType == null) {
			if ((genericSuperClass instanceof ParameterizedType)) {
				parametrizedType = (ParameterizedType) genericSuperClass;
			} else {
				genericSuperClass = ((Class<?>) genericSuperClass).getGenericSuperclass();
			}
		}

		Class<R> itemClass = (Class<R>) parametrizedType.getActualTypeArguments()[1];
		return (IBaseRepository) BeanFactory.getBean(itemClass);
	}
}
