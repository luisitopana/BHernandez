package com.lmh.service;

import com.lmh.entity.Partida;

public interface IPartidaService extends IBaseService<Partida> {

	public Partida save(Partida partida);
}
