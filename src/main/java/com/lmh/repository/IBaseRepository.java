package com.lmh.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface IBaseRepository<E, ID extends Serializable> extends CrudRepository<E, ID>, QuerydslPredicateExecutor<E>, JpaRepository<E, ID> {

}
