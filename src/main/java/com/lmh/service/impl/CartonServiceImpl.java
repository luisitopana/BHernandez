package com.lmh.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lmh.entity.Carton;
import com.lmh.repository.ICartonRepository;
import com.lmh.service.ICartonService;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class CartonServiceImpl extends BaseServiceImpl<Carton, ICartonRepository> implements ICartonService {
}
