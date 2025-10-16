package com.lmh.service.impl;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lmh.entity.Patroncarton;
import com.lmh.entity.QPatroncarton;
import com.lmh.repository.IPatroncartonRepository;
import com.lmh.service.IPatroncartonService;
import com.lmh.utils.CrearCarton;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class PatroncartonServiceImpl extends BaseServiceImpl<Patroncarton, IPatroncartonRepository> implements IPatroncartonService {

	public String generarCartones(int idusuario, int numeroCartones, int idpartida) {
		Random r = new Random();
		
		List<Patroncarton> patroncartons = new ArrayList<>();
		
		int totalPatrones = super.search().size();
		
		int ale;
		
		for (int i = 0; i < 4; i++) {			
			do {
				ale = r.nextInt(totalPatrones) + 1;
			} while(ale%6 != 1 || ale > totalPatrones - 6);
			
			List<Integer> idsPatron = IntStream.rangeClosed(ale, ale + 5).boxed().toList();
			
			patroncartons.addAll(super.search(QPatroncarton.patroncarton.idpatroncarton.in(idsPatron)));
		}
		
		try {
			return CrearCarton.getInstance().rellenarCarton(transformarArray(patroncartons));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		}
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
	
	public static void mostrarSerie(List<int[][]> serie) {
		int n = 1;
		for (int[][] carton : serie) {
			System.out.println("Cartón " + n++);
			for (int[] fila : carton) {
				for (int num : fila) {
					System.out.print((num == 0 ? "  " : String.format("%2d", num)) + " ");
				}
				System.out.println();
			}
			System.out.println("---------------------------------");
		}
	}
}
