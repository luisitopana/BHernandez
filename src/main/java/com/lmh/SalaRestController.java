package com.lmh;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lmh.service.ISalaService;
import com.lmh.utils.BeanFactory;

import io.vavr.control.Option;

@RestController
@RequestMapping("/api")
public class SalaRestController {
	
	private ISalaService salaService;
	
	@GetMapping("/salas")
    public ResponseEntity getSalas() {
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(getSalaService().search());
    }
	
	private ISalaService getSalaService() {
		salaService = Option.of(salaService).getOrElse(BeanFactory.getBean(ISalaService.class));
		return salaService;
	}
}
