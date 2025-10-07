package com.lmh.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lmh.entity.Sala;
import com.lmh.repository.ISalaRepository;
import com.lmh.service.ISalaService;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class SalaImpl extends BaseServiceImpl<Sala, ISalaRepository> implements ISalaService {
}
