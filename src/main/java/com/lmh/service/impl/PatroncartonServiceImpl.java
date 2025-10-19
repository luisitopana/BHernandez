package com.lmh.service.impl;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lmh.entity.Carton;
import com.lmh.entity.Partida;
import com.lmh.entity.Patroncarton;
import com.lmh.entity.QCarton;
import com.lmh.entity.QPatroncarton;
import com.lmh.entity.Usuario;
import com.lmh.repository.IPatroncartonRepository;
import com.lmh.service.ICartonService;
import com.lmh.service.IPartidaService;
import com.lmh.service.IPatroncartonService;
import com.lmh.service.IUsuarioService;
import com.lmh.utils.BeanFactory;
import com.lmh.utils.CrearCarton;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;

import io.vavr.control.Option;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class PatroncartonServiceImpl extends BaseServiceImpl<Patroncarton, IPatroncartonRepository> implements IPatroncartonService {
	
	private ICartonService cartonService;
	
	private IPartidaService partidaService;
	
	private IUsuarioService usuarioService;

	public String generarCartones(int idusuario, int numeroCartones, int idpartida) {
		Random r = new Random();
		List<Patroncarton> patroncartons = new ArrayList<>();
		
		int totalPatrones = super.search().size();
		int ale;
		
		for (int i = 0; i < numeroCartones; i++) {
			ale = r.nextInt(totalPatrones) + 1;
			
			patroncartons.addAll(super.search(QPatroncarton.patroncarton.idpatroncarton.eq(ale)));
		}
		
		try {
			LinkedList<Carton> cartones = CrearCarton.getInstance().rellenarCarton(transformarArray(patroncartons));
			
			LinkedList<String> cartonesEnJuego = new LinkedList<>();
			
			Partida p = getPartidaService().load(idpartida);
			Usuario u = getUsuarioService().load(idusuario);
			
			for (Carton carton : cartones) {
				carton.setIdpartida(p);
				carton.setIdusuario(u);
				carton.setPremiadobingo(false);
				carton.setPremiadolinea(false);
				carton.setEstaenjuego(numeroCartones > 0);
				
				getCartonService().save(carton);
				
				cartonesEnJuego.add(carton.getNumeros());
			}
			
			ObjectMapper mapper = new ObjectMapper();
	        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(cartonesEnJuego);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private List<Carton> cargarCartones(Integer idUsuario, Integer idPartida) {
		Predicate p = QCarton.carton.idusuario.idusuario.eq(idUsuario)
				.and(QCarton.carton.idpartida.idpartida.eq(idPartida));
		
		OrderSpecifier o = QCarton.carton.idcarton.asc();
		
		return getCartonService().search(p, o);
				
	}
	
	private LinkedList<int[][]> transformarArray(List<Patroncarton> patroncartons) {
		LinkedList<int[][]> resultado = new LinkedList<>();

        if (patroncartons == null || patroncartons.isEmpty()) {
            return resultado;
        }

        try {
            for (Patroncarton p : patroncartons) {
                int[][] matriz = new int[3][9];

                // Cargar los 27 campos (n1...n27)
                for (int j = 1; j <= 27; j++) {
                    Method getter = Patroncarton.class.getMethod("getN" + j);
                    String valor = (String) getter.invoke(p);

                    int numero = 0;
                    if (valor != null && !valor.isEmpty()) {
                        try {
                            numero = Integer.parseInt(valor);
                        } catch (NumberFormatException e) {
                            numero = 0; // o podrías lanzar un error si prefieres
                        }
                    }

                    // Calcular fila y columna
                    int fila = (j - 1) / 9;
                    int columna = (j - 1) % 9;

                    matriz[fila][columna] = numero;
                }

                resultado.add(matriz);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultado;
	}
	
	private ICartonService getCartonService() {
		cartonService = Option.of(cartonService).getOrElse(BeanFactory.getBean(ICartonService.class));
		return cartonService;
	}
	
	private IPartidaService getPartidaService() {
		partidaService = Option.of(partidaService).getOrElse(BeanFactory.getBean(IPartidaService.class));
		return partidaService;
	}
	
	private IUsuarioService getUsuarioService() {
		usuarioService = Option.of(usuarioService).getOrElse(BeanFactory.getBean(IUsuarioService.class));
		return usuarioService;
	}
}
