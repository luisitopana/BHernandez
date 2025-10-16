package com.lmh.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lmh.entity.Partida;
import com.lmh.entity.QSala;
import com.lmh.entity.Sala;
import com.lmh.manager.PartidaState;
import com.lmh.service.IPartidaService;
import com.lmh.service.ISalaService;

@Service
public class PartidaRuntimeService {

    @Autowired
    private IPartidaService partidaService;

    @Autowired
    private ISalaService salaService;

    @Transactional
    public Partida actualizarEstado(Partida p, Integer idSala, PartidaState estado) {
        Sala salaAdjunta = salaService.search(QSala.sala.idsala.eq(idSala)).get(0);
        p.setIdsala(salaAdjunta);
        p.setEstado(estado);
        return partidaService.save(p);
    }
}
