package com.lmh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.lmh.manager.SalaManager;
import com.lmh.service.ISalaService;
import com.lmh.utils.BeanFactory;

import io.vavr.control.Option;

@Controller
public class SalaController {

	private ISalaService salaService;

	@Autowired
	private SalaManager salaManager;

	/*@MessageMapping("/join/{roomId}")
    @SendTo("/topic/room/{roomId}")
    public Player joinRoom(@DestinationVariable String roomId, Player player) {
        // Añadir jugador a la sala
        return player;
    }*/

    @MessageMapping("/draw/{roomId}")
    @SendTo("/topic/room/{roomId}")	
    public Integer drawNumber(@DestinationVariable String roomId) {
        // Lógica para sacar número aleatorio
        return 1;
    }
    
    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public String greeting() throws Exception {
      Thread.sleep(1000); // simulated delay
      return "HOLA";
    }

	private ISalaService getSalaService() {
		salaService = Option.of(salaService).getOrElse(BeanFactory.getBean(ISalaService.class));
		return salaService;
	}
}