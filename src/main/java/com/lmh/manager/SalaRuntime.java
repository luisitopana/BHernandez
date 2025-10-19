package com.lmh.manager;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import com.lmh.entity.Partida;
import com.lmh.entity.Sala;
import com.lmh.service.IPartidaService;
import com.lmh.service.ISalaService;
import com.lmh.utils.BeanFactory;

import io.vavr.control.Option;

public class SalaRuntime {

	private ISalaService salaService;

	private Sala sala;

	private ScheduledExecutorService tiempoExecutor = Executors.newSingleThreadScheduledExecutor();
	private ScheduledExecutorService partidaExecutor = Executors.newSingleThreadScheduledExecutor();
	private ScheduledExecutorService datosExecutor = Executors.newSingleThreadScheduledExecutor();

	private SalaState state = SalaState.WAITING;
	private final List<Integer> numerosSorteados = new ArrayList<>();
	private final Map<String, List<Integer>> cartonesJugador = new ConcurrentHashMap<>();

	private final SimpMessagingTemplate messagingTemplate;

	private int countdown = 10;
	private int countdown1 = 10;

	private volatile boolean activa = true;
	
	private Partida p;
	
	private IPartidaService partidaService;

	public SalaRuntime(Sala sala, SimpMessagingTemplate messagingTemplate) {
		this.sala = sala;
		this.messagingTemplate = messagingTemplate;

		Thread.currentThread().setName(sala.getCodigo());
		enviarDatosSala();
		controladorPartida();
	}
	
	private void enviarDatosSala() {
		/*datosExecutor = Executors.newSingleThreadScheduledExecutor();
		
		datosExecutor.scheduleAtFixedRate(() -> {
            if (!activa) {
            	datosExecutor.shutdownNow();
            	return;
            }
            
            Gson g = new Gson();
            String datosSala = g.toJson(sala);
            messagingTemplate.convertAndSend("/topic/sala/" + sala.getIdsala() + "/datos", datosSala);
        }, 0, 2, TimeUnit.SECONDS);*/
	}

	public void iniciarCuentaAtras() {
		tiempoExecutor = Executors.newSingleThreadScheduledExecutor();
		
		state = SalaState.WAITING;
		
		crearPartida();
		
		tiempoExecutor.scheduleAtFixedRate(() -> {
            if (!activa && state == SalaState.WAITING) {
            	state = SalaState.STOPPED;
            	messagingTemplate.convertAndSend("/topic/sala/" + sala.getIdsala() + "/estado", state.name());
            	tiempoExecutor.shutdownNow();
            	return;
            }
            
            int minutos = countdown / 60;
            int segundos = countdown % 60;

            String tiempoFormateado = String.format("%02d:%02d", minutos, segundos);

            messagingTemplate.convertAndSend("/topic/sala/" + sala.getIdsala() + "/countdown", tiempoFormateado);
            messagingTemplate.convertAndSend("/topic/sala/" + sala.getIdsala() + "/estado", state.name());
            messagingTemplate.convertAndSend("/topic/sala/" + sala.getIdsala() + "/idpartida", String.valueOf(p.getIdpartida()));
            countdown--;

            if (countdown < 0) {
                tiempoExecutor.shutdownNow();
                messagingTemplate.convertAndSend("/topic/sala/" + sala.getIdsala() + "/empezar", "OK");
            }
        }, 0, 1, TimeUnit.SECONDS);
	}
	
	private void controladorPartida() {
		WebSocketStompClient stompClient = new WebSocketStompClient(new StandardWebSocketClient());
		stompClient.setMessageConverter(new StringMessageConverter());
		stompClient.setDefaultHeartbeat(new long[] { 0, 0 });

		StompSessionHandler sessionHandler = new StompSessionHandlerAdapter() {
			@Override
			public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
				session.subscribe("/topic/sala/" + sala.getIdsala() + "/empezar", new StompFrameHandler() {
					@Override
					public Type getPayloadType(StompHeaders headers) {
						return String.class;
					}

					@Override
					public void handleFrame(StompHeaders headers, Object payload) {
						iniciarPartida();
					}
				});
				
				session.subscribe("/topic/sala/" + sala.getIdsala() + "/finalizar", new StompFrameHandler() {
					@Override
					public Type getPayloadType(StompHeaders headers) {
						return String.class;
					}

					@Override
					public void handleFrame(StompHeaders headers, Object payload) {
						terminarPartida();
					}
				});
			}

			@Override
			public void handleTransportError(StompSession session, Throwable exception) {
				System.err.println("Error de transporte: " + exception.getMessage());
			}
		};

		String url = "ws://localhost:8080/ws-bingo";
		stompClient.connect(url, new WebSocketHttpHeaders(), sessionHandler);
	}
	
	private void iniciarPartida() {
		partidaExecutor = Executors.newSingleThreadScheduledExecutor();
		state = SalaState.PLAYING;
		messagingTemplate.convertAndSend("/topic/sala/" + sala.getIdsala() + "/estado", state.name());
		
		p.setFechainicio(new Date());
		cambiarEstadoPartida(PartidaState.JUGANDO);

		partidaExecutor.scheduleAtFixedRate(() -> {
            countdown1--;
            
            if(countdown1 == 0) {
            	countdown1 = 10;
            	countdown = 30;
            	partidaExecutor.shutdownNow();
            	messagingTemplate.convertAndSend("/topic/sala/" + sala.getIdsala() + "/finalizar", "OK");
            }
        }, 0, 1, TimeUnit.SECONDS);
	}
		
	private void crearPartida() {
		p = new Partida();
		p.setEstado(PartidaState.INICIANDO);
		p.setIdsala(sala);
		p.setPremiolinea(0);
		p.setPremiobingo(0);
		p.setSaliobote(false);
		p = getPartidaService().save(p);
	}
	
	private void cambiarEstadoPartida(PartidaState estado) {
		p.setEstado(estado);
		p = getPartidaService().save(p);
	}
	
	private void terminarPartida() {
		p.setFechafin(new Date());
		cambiarEstadoPartida(PartidaState.FINALIZADA);
		
        iniciarCuentaAtras();
    }

    public void stop() {
        activa = false;
    }

	private ISalaService getSalaService() {
		salaService = Option.of(salaService).getOrElse(BeanFactory.getBean(ISalaService.class));
		return salaService;
	}
	
	private IPartidaService getPartidaService() {
		partidaService = Option.of(partidaService).getOrElse(BeanFactory.getBean(IPartidaService.class));
		return partidaService;
	}

	public SalaState getState() {
		return state;
	}

	public void setState(SalaState state) {
		this.state = state;
	}
}
