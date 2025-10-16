package com.lmh.service;

import com.lmh.entity.Patroncarton;

public interface IPatroncartonService extends IBaseService<Patroncarton> {

	String generarCartones(int idusuario, int cantidad, int idpartida);
}
