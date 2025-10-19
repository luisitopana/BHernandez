package com.lmh.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lmh.service.IPatroncartonService;
import com.lmh.utils.BeanFactory;

import io.vavr.control.Option;

@RestController
@RequestMapping("/api/bingo")
public class CartonController {

	private IPatroncartonService patroncartonService;

	@RequestMapping(method = RequestMethod.GET, value = "/comprar", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity comprarCartones(@RequestParam int userId, @RequestParam int cantidad, @RequestParam int idpartida) {
        String cartones = getPatroncartonService().generarCartones(userId, cantidad, idpartida);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(cartones);
    }

	private IPatroncartonService getPatroncartonService() {
		patroncartonService = Option.of(patroncartonService).getOrElse(BeanFactory.getBean(IPatroncartonService.class));
		return patroncartonService;
	}
}