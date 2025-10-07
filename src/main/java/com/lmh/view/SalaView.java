package com.lmh.view;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import com.lmh.entity.Sala;
import com.lmh.manager.SalaManager;
import com.lmh.manager.SalaRuntime;
import com.lmh.manager.SalaState;
import com.lmh.service.ISalaService;
import com.lmh.utils.BeanFactory;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.card.Card;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import io.vavr.control.Option;
import jakarta.annotation.security.PermitAll;

@Route(value = "salas", layout = MainView.class)
@PageTitle("Salas de Bingo")
@PermitAll
public class SalaView extends VerticalLayout {

	static final long serialVersionUID = -1577857990331017714L;

	private ISalaService salaService;

	private SalaManager salaManager;

	private List<Sala> salas = new ArrayList<>();

	private Map<Integer, Card> cards = new HashMap<>();

	private static String TIEMPO_RESTANTE = "Tiempo restante para siguiente partida: ";

	public SalaView() {
		salas = getSalaService().search();

		HorizontalLayout hl = new HorizontalLayout();
		hl.setWidthFull();

		Map<Integer, SalaRuntime> salasActivas = getSalaManager().getSalasActivas();

		for (int i = 0; i < salas.size(); i++) {
			if (i != 0 && i % 3 == 0) {
				add(hl);
				hl = new HorizontalLayout();
				hl.setWidthFull();
			}

			Card card = new Card();
			card.setTitle("ID: " + salas.get(i).getIdsala());

			VerticalLayout vl = new VerticalLayout();
			Label l1 = new Label("Nombre: " + salas.get(i).getNombre());
			Label l2 = new Label("Código: " + salas.get(i).getCodigo());
			Label l3 = new Label("Precio: " + (salas.get(i).getPrecio() / 100.0) + " €");
			Label l4 = new Label("Estado: Parado");
			Label l5 = new Label("Tiempo: 00:00");
			vl.add(l1, l2, l3, l4, l5);
			card.add(vl);

			Button btnIniciar = new Button("Iniciar Sala");
			Button btnParar = new Button("Parar Sala");

			btnIniciar.getStyle().set("font-weight", "bold");
			btnParar.getStyle().set("font-weight", "bold");

			btnIniciar.getStyle().set("color", "black");
			btnParar.getStyle().set("color", "black");

			btnIniciar.setId(salas.get(i).getIdsala().toString());

			btnIniciar.addClickListener(e -> {
				salaManager.iniciarSala(Integer.parseInt(btnIniciar.getId().get()));
				cards.get(Integer.parseInt(btnIniciar.getId().get())).getStyle().set("background-color", "orange");
				btnParar.setVisible(true);
				btnIniciar.setVisible(false);
			});

			btnParar.addClickListener(e -> {
				salaManager.pararSala(Integer.parseInt(btnIniciar.getId().get()));
			});

			if (salasActivas.isEmpty()) {
				card.getStyle().set("background-color", "red");
				btnParar.setVisible(false);
			} else if (salasActivas.get(salas.get(i).getIdsala()) != null && salasActivas.get(salas.get(i).getIdsala()).getState() == SalaState.STOPPED) {
				card.getStyle().set("background-color", "red");
				btnParar.setVisible(false);
			} else if (salasActivas.get(salas.get(i).getIdsala()) != null && salasActivas.get(salas.get(i).getIdsala()).getState() == SalaState.WAITING) {
				card.getStyle().set("background-color", "orange");
				btnIniciar.setVisible(false);
			} else if (salasActivas.get(salas.get(i).getIdsala()) != null && salasActivas.get(salas.get(i).getIdsala()).getState() == SalaState.PLAYING) {
				card.getStyle().set("background-color", "lawngreen");
				btnIniciar.setVisible(false);
			} else {
				card.getStyle().set("background-color", "red");
				btnParar.setVisible(false);
			}

			card.addToFooter(btnIniciar, btnParar);

			iniciarHandler(btnIniciar, btnParar, l4, l5, card);

			card.setWidthFull();

			cards.put(salas.get(i).getIdsala(), card);

			hl.add(card);
		}

		add(hl);
	}

	private void iniciarHandler(Button btnIniciar, Button btnParar, Label lEstado, Label lTiempo, Card card) {
		Integer idSala = Integer.parseInt(btnIniciar.getId().get());

		// StandardWebSocketClient client = new StandardWebSocketClient();

		WebSocketStompClient stompClient = new WebSocketStompClient(new StandardWebSocketClient());
		stompClient.setMessageConverter(new StringMessageConverter());
		stompClient.setDefaultHeartbeat(new long[] { 0, 0 });
		// Crear el cliente STOMP
		// WebSocketStompClient stompClient = new WebSocketStompClient(client);
		// stompClient.setMessageConverter(new MappingJackson2MessageConverter());

		/*
		 * WebSocketStompClient stompClient = new WebSocketStompClient( new
		 * SockJsClient(Collections.singletonList(new WebSocketTransport(new
		 * StandardWebSocketClient()))) );
		 */
		/*
		 * WebSocketStompClient stompClient = new WebSocketStompClient(new
		 * StandardWebSocketClient());
		 */
		// stompClient.setMessageConverter(new StringMessageConverter()); // importante

		StompSessionHandler sessionHandler = new StompSessionHandlerAdapter() {
			@Override
			public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
				session.subscribe("/topic/sala/" + idSala + "/countdown", new StompFrameHandler() {
					@Override
					public Type getPayloadType(StompHeaders headers) {
						return String.class;
					}

					@Override
					public void handleFrame(StompHeaders headers, Object payload) {
						getUI().ifPresent(ui -> ui.access(() -> {
							lTiempo.setText(TIEMPO_RESTANTE + payload);
							getUI().get().push();
						}));
					}
				});

				session.subscribe("/topic/sala/" + idSala + "/estado", new StompFrameHandler() {
					@Override
					public Type getPayloadType(StompHeaders headers) {
						return String.class;
					}

					@Override
					public void handleFrame(StompHeaders headers, Object payload) {
						System.out.println("ESTADO");
						getUI().ifPresent(ui -> ui.access(() -> {
							SalaState estado = SalaState.valueOf(payload.toString());

							lEstado.setText("Estado: " + estado);

							if (estado == SalaState.STOPPED) {
								card.getStyle().set("background-color", "red");
								btnIniciar.setVisible(true);
								btnParar.setVisible(false);
							} else if (estado == SalaState.WAITING) {
								card.getStyle().set("background-color", "orange");
								btnIniciar.setVisible(false);
								btnParar.setVisible(true);
							} else if (estado == SalaState.PLAYING) {
								card.getStyle().set("background-color", "lawngreen");
								btnIniciar.setVisible(false);
								btnParar.setVisible(true);
							}
						}));
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

	private ISalaService getSalaService() {
		salaService = Option.of(salaService).getOrElse(BeanFactory.getBean(ISalaService.class));
		return salaService;
	}

	private SalaManager getSalaManager() {
		salaManager = Option.of(salaManager).getOrElse(BeanFactory.getBean(SalaManager.class));
		return salaManager;
	}

	public static Thread getThreadByName(String name) {
		// Obtenemos todos los hilos activos
		ThreadGroup rootGroup = Thread.currentThread().getThreadGroup();
		while (rootGroup.getParent() != null) {
			rootGroup = rootGroup.getParent();
		}

		Thread[] threads = new Thread[rootGroup.activeCount() * 2];
		int count = rootGroup.enumerate(threads, true);

		for (int i = 0; i < count; i++) {
			if (threads[i].getName().equals(name)) {
				return threads[i];
			}
		}
		return null;
	}
}
