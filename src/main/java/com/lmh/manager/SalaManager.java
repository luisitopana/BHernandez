package com.lmh.manager;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.lmh.entity.QSala;
import com.lmh.entity.Sala;
import com.lmh.service.ISalaService;
import com.lmh.utils.BeanFactory;
import com.querydsl.core.types.Predicate;

import io.vavr.control.Option;

@Service
public class SalaManager {

	private ISalaService salaService;
	
	//private static SalaManager instance;

	private final Map<Integer, SalaRuntime> salasActivas = new ConcurrentHashMap<>();
	
	@Autowired
    private SimpMessagingTemplate messagingTemplate;

	/*public static synchronized SalaManager getInstance() {
        if (instance == null) {
            instance = new SalaManager();
        }
        
        return instance;
    }*/
	
	@Autowired
    public SalaManager(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

	public SalaRuntime iniciarSala(Integer idsala) {
		if(salasActivas.containsKey(idsala)) {
			return salasActivas.get(idsala);
		} else {
			Predicate p = QSala.sala.idsala.eq(idsala);
			Sala sala = getSalaService().search(p).get(0);

			SalaRuntime runtime = new SalaRuntime(sala, messagingTemplate);
			salasActivas.put(sala.getIdsala(), runtime);
			runtime.iniciarCuentaAtras();

			return runtime;
		}
	}
	
	public void pararSala(Integer idsala) {
		if(salasActivas.containsKey(idsala)) {
			SalaRuntime salaRuntime = salasActivas.get(idsala);
			salaRuntime.stop();		
			salasActivas.remove(idsala);
			salaRuntime = null;
		}
	}
	
	public Map<Integer, SalaRuntime> getSalasActivas(){
		return salasActivas;
	}

	public SalaRuntime getRuntime(Long salaId) {
		return salasActivas.get(salaId);
	}

	private ISalaService getSalaService() {
		salaService = Option.of(salaService).getOrElse(BeanFactory.getBean(ISalaService.class));
		return salaService;
	}
}