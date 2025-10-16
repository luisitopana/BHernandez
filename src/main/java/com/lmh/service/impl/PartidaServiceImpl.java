package com.lmh.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lmh.entity.Partida;
import com.lmh.repository.IPartidaRepository;
import com.lmh.service.IPartidaService;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class PartidaServiceImpl extends BaseServiceImpl<Partida, IPartidaRepository> implements IPartidaService {

	@Transactional
	public Partida save(Partida partida) {
		return super.save(partida);
	}
}
