package com.lmh.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lmh.entity.Carton;

public final class CrearCarton {

	private static CrearCarton instance;

	public static CrearCarton getInstance() {
		if (instance == null) {
			instance = new CrearCarton();
		}
		return instance;
	}

	public synchronized LinkedList<Carton> rellenarCarton(LinkedList<int[][]> cartonesVacios) throws JsonProcessingException {
		Random rand = new Random();

		List<int[][]> cartones = new ArrayList<>();
		List<List<Integer>> columnas = new ArrayList<>();
		
		for (int x = 0; x < cartonesVacios.size(); x++) {
			columnas.clear();

			for (int i = 0; i < 9; i++) {
				int start = (i == 0) ? 1 : i * 10;
				int end = (i == 8) ? 90 : (i == 0) ? start + 8 : start + 9;
				List<Integer> col = new ArrayList<>();
				for (int n = start; n <= end; n++) col.add(n);
				Collections.shuffle(col);
				columnas.add(col);
			}
			
			for (int i = 0; i < 9; i++) {
				Queue<Integer> disponibles = new LinkedList<>(columnas.get(i));
				
				for (int j = 0; j < 3; j++) {
					for (int k = 0; k < 6; k++) {
						int[][] carton = cartonesVacios.get(x);
						
						if(carton[j][i] == 1) {
							try {
								carton[j][i] = disponibles.poll();
								cartonesVacios.set(x, carton);
							}catch (Exception e) {
							}
							
						}
					}
				}
			}
		}	
		
		// 5. Ajustar estructura y ordenar columnas
		for (int[][] carton : cartonesVacios) {
			for (int col = 0; col < 9; col++) ordenarColumna(carton, col);

			// Si alguna fila tiene menos de 5 números, equilibrar
			equilibrarFilas(carton);

			// Asegurar máximo 2 consecutivos
			for (int f = 0; f < 3; f++) {
				while (tieneMasDeDosConsecutivos(carton[f])) {
					reordenarFila(carton[f]);
				}
			}
		}
		//}

		ordenarCartones(cartonesVacios);
		mostrarSerie(cartonesVacios);
		
		//ObjectMapper mapper = new ObjectMapper();
		//mapper.writerWithDefaultPrettyPrinter().writeValueAsString(cartonesVacios);
        return transformarArrayACarton(cartonesVacios); 
	}
	
	private static LinkedList<Carton> transformarArrayACarton(List<int[][]> listaCartones) {
		LinkedList<Carton> cartones = new LinkedList<>();
		
		for (int[][] carton : listaCartones) {
			Carton c = new Carton();
			String numero = "";
			
			String resultado = Arrays.stream(carton)
				    .flatMapToInt(Arrays::stream)
				    .mapToObj(String::valueOf)
				    .collect(Collectors.joining(","));
			
			c.setNumeros(resultado);
			cartones.add(c);
		}
		
		return cartones;
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

	private static int contarNumerosFila(int[][] carton, int fila) {
		int c = 0;
		for (int n : carton[fila]) if (n != 0) c++;
		return c;
	}

	// Equilibrar para que haya exactamente 5 números por fila
	private static void equilibrarFilas(int[][] carton) {
		Random rand = new Random();
		int[] cuentaFila = new int[3];
		for (int f = 0; f < 3; f++) cuentaFila[f] = contarNumerosFila(carton, f);

		// Si una fila tiene más de 5, mover a otra con menos
		while (cuentaFila[0] + cuentaFila[1] + cuentaFila[2] > 15 ||
				cuentaFila[0] != 5 || cuentaFila[1] != 5 || cuentaFila[2] != 5) {

			for (int f = 0; f < 3; f++) {
				if (cuentaFila[f] > 5) {
					// buscar otra fila con menos de 5
					for (int t = 0; t < 3; t++) {
						if (cuentaFila[t] < 5) {
							// mover un número
							List<Integer> columnas = new ArrayList<>();
							for (int col = 0; col < 9; col++) {
								if (carton[f][col] != 0 && carton[t][col] == 0) {
									columnas.add(col);
								}
							}
							if (!columnas.isEmpty()) {
								int col = columnas.get(rand.nextInt(columnas.size()));
								carton[t][col] = carton[f][col];
								carton[f][col] = 0;
								cuentaFila[f]--;
								cuentaFila[t]++;
							}
						}
					}
				}
			}
		}
	}

	private void ordenarColumna(int[][] carton, int col) {
		List<Integer> nums = new ArrayList<>();
		for (int f = 0; f < 3; f++) if (carton[f][col] != 0) nums.add(carton[f][col]);
		Collections.sort(nums);
		int i = 0;
		for (int f = 0; f < 3; f++) {
			if (carton[f][col] != 0) carton[f][col] = nums.get(i++);
		}
	}

	private boolean tieneMasDeDosConsecutivos(int[] fila) {
		int consecutivos = 1, prev = 0;
		for (int n : fila) {
			if (n != 0) {
				if (prev != 0 && n == prev + 1) consecutivos++;
				else consecutivos = 1;
				if (consecutivos > 2) return true;
				prev = n;
			}
		}
		return false;
	}

	private void reordenarFila(int[] fila) {
		List<Integer> pos = new ArrayList<>();
		List<Integer> valores = new ArrayList<>();
		for (int i = 0; i < fila.length; i++) {
			if (fila[i] != 0) {
				pos.add(i);
				valores.add(fila[i]);
			}
		}
		Random rand = new Random();
		do {
			Collections.shuffle(valores, rand);
			for (int i = 0; i < pos.size(); i++) fila[pos.get(i)] = valores.get(i);
		} while (tieneMasDeDosConsecutivos(fila));
	}

	private void ordenarCartones(List<int[][]> cartones) {
		for (int k = 0; k < cartones.size(); k++) {
			int[][] c = cartones.get(k);

			for (int i = 0; i < c.length; i++) {
				// Extraer los números de la fila (ignorando ceros)
				int[] numeros = Arrays.stream(c[i])
						.filter(n -> n != 0)
						.toArray();

				// Ordenar los números
				Arrays.sort(numeros);

				// Colocar los números ordenados de nuevo en la fila
				int index = 0;
				for (int j = 0; j < c[i].length; j++) {
					if (c[i][j] != 0) {
						c[i][j] = numeros[index++];
					}
				}
			}
		}
	}
}
