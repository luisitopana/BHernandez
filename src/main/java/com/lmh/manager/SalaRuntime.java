package com.lmh.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.messaging.simp.SimpMessagingTemplate;

import com.lmh.entity.Sala;
import com.lmh.service.ISalaService;
import com.lmh.utils.BeanFactory;

import io.vavr.control.Option;

public class SalaRuntime {

	private ISalaService salaService;

	private Sala sala;

	private ScheduledExecutorService tiempoExecutor = Executors.newSingleThreadScheduledExecutor();
	private ScheduledExecutorService partidaExecutor = Executors.newSingleThreadScheduledExecutor();

	private SalaState state = SalaState.WAITING;
	private final List<Integer> numerosSorteados = new ArrayList<>();
	private final Map<String, List<Integer>> cartonesJugador = new ConcurrentHashMap<>();

	private final SimpMessagingTemplate messagingTemplate;

	private int countdown = 10;
	private int countdown1 = 10;

	private volatile boolean activa = true;

	public SalaRuntime(Sala sala, SimpMessagingTemplate messagingTemplate) {
		this.sala = sala;
		this.messagingTemplate = messagingTemplate;

		Thread.currentThread().setName(sala.getCodigo());
	}
	
	public void iniciarCuentaAtras() {
		tiempoExecutor = Executors.newSingleThreadScheduledExecutor();
		
		state = SalaState.WAITING;
		
        System.out.println("Sala " + sala.getIdsala() + " inicia cuenta atrás...");

        tiempoExecutor.scheduleAtFixedRate(() -> {
            if (!activa && state == SalaState.WAITING) {
            	System.out.println("Parando la sala " + sala.getIdsala());
            	state = SalaState.STOPPED;
            	messagingTemplate.convertAndSend("/topic/sala/" + sala.getIdsala() + "/estado", state.name());
            	tiempoExecutor.shutdownNow();
            	return;
            }

            System.out.println("Sala " + sala.getIdsala() + " - countdown para empezar: " + countdown);
            messagingTemplate.convertAndSend("/topic/sala/" + sala.getIdsala() + "/countdown", countdown < 10 ? "00:0" + String.valueOf(countdown) : "00:" + String.valueOf(countdown));
            messagingTemplate.convertAndSend("/topic/sala/" + sala.getIdsala() + "/estado", state.name());
            countdown--;

            if (countdown < 0) {
                System.out.println("Cuenta atrás terminada, iniciando partida...");
                countdown = 10;
                tiempoExecutor.shutdownNow();
                iniciarPartida();
            }
        }, 0, 1, TimeUnit.SECONDS);
	}

	private void iniciarPartida() {
		partidaExecutor = Executors.newSingleThreadScheduledExecutor();
		state = SalaState.PLAYING;
		//

		partidaExecutor.scheduleAtFixedRate(() -> {
            System.out.println("Sala " + sala.getIdsala() + " - partida iniciada");
            
            countdown1--;
            
            messagingTemplate.convertAndSend("/topic/sala/" + sala.getIdsala() + "/estado", state.name());
            
            if(countdown1 == 0) {
            	System.out.println("Partida terminada, iniciando cuenta atras...");
            	countdown1 = 10;
            	partidaExecutor.shutdownNow();
            	terminarPartida();
            }
        }, 0, 1, TimeUnit.SECONDS);
	}
	
	private void terminarPartida() {
        System.out.println("Sala " + sala.getIdsala() + " - partida terminada, reiniciando ciclo");
        iniciarCuentaAtras(); // vuelve a empezar
    }

    public void stop() {
        activa = false;
        //tiempoExecutor.shutdownNow();
        //System.out.println("Sala " + sala.getIdsala() + " detenida");
    }

	public void addCarton(String jugador) {
		if (state != SalaState.WAITING) return;
		List<Integer> carton = new Random().ints(1, 76).distinct().limit(15).boxed().toList();
		cartonesJugador.put(jugador, carton);

		messagingTemplate.convertAndSend("/topic/sala/" + sala.getIdsala() + "/cartones/" + jugador, carton);
	}

	private ISalaService getSalaService() {
		salaService = Option.of(salaService).getOrElse(BeanFactory.getBean(ISalaService.class));
		return salaService;
	}

	public SalaState getState() {
		return state;
	}

	public void setState(SalaState state) {
		this.state = state;
	}


}
