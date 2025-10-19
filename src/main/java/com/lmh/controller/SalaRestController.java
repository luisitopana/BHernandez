package com.lmh.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lmh.service.ISalaService;
import com.lmh.utils.BeanFactory;

import io.vavr.control.Option;

@RestController
@RequestMapping("/api/bingo")
public class SalaRestController {
	
	private ISalaService salaService;
	
	@RequestMapping(method = RequestMethod.GET, value = "/salas", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getSalas() {
		try {
			ObjectMapper mapper = new ObjectMapper();
	        String sala = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(getSalaService().search());
			 return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(sala);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
		return null;
    }
	
	private ISalaService getSalaService() {
		salaService = Option.of(salaService).getOrElse(BeanFactory.getBean(ISalaService.class));
		return salaService;
	}
}
