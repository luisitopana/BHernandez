package com.lmh.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

public class GeneradorDeCartones {

	private static Random rand = new Random();

	public static void main(String[] args) {
		generarCartones();
	}

	public static List<int[][]> generarCartones() {
		rand = new Random();
		
		rand.nextInt(0);
		
		List<int[][]> cartones = new ArrayList<>();
		
		for (int i = 0; i < 46; i++) {
			//cartones.addAll(generarSerieCompleta());
		}
		
		//imprimirPatron(cartones);

		//mostrarSerie(cartones);

		return cartones;
	}

	public static List<int[][]> generarSerieCompleta() {
		List<int[][]> cartones = new ArrayList<>();


		List<List<Integer>> columnas = new ArrayList<>();
		for (int i = 0; i < 9; i++) {
			int start = (i == 0) ? 1 : i * 10;
			int end = (i == 8) ? 90 : (i == 0) ? start + 8 : start + 9;
			List<Integer> col = new ArrayList<>();
			for (int n = start; n <= end; n++) col.add(n);
			Collections.shuffle(col);
			columnas.add(col);
		}

		// 2. Distribución: cuántos números por columna y cartón
		//int[][] distribucion = new int[6][9];
		//int[] numerosPorCarton = new int[6];

		boolean completo = false;

		int[][] distribucion = new int[6][9];
		int[] numerosPorCarton = new int[6];

		while(!completo) {
			distribucion = new int[6][9];
			numerosPorCarton = new int[6];

			for (int col = 0; col < 9; col++) {
				int totalNumeros = (col == 8) ? 11 : ((col == 0) ? 9 : 10);
				int sumaCol = 0;

				while(sumaCol != totalNumeros) {
					sumaCol = 0;

					for (int c = 0; c < 6; c++) {
						int num = rand.nextInt(3);

						distribucion[c][col] = num;
					}

					for (int j = 0; j < 6; j++) {
						sumaCol+= distribucion[j][col];
					}
				}
			}

			completo = true;

			int sumaFil = 0;

			for (int i = 0; i < 6; i++) {
				sumaFil = 0;

				for (int j = 0; j < 9; j++) {
					sumaFil+= distribucion[i][j];
					if(distribucion[i][j] == 0) {
						completo = false;
					}
				}

				if(sumaFil != 15) {
					completo = false;
					break;
				}
			}
		}

		// 3. Crear cartones vacíos

		for (int i = 0; i < 6; i++) {
			cartones.add(new int[3][9]);
		}

		// 4. Asignar números según distribución
		for (int col = 0; col < 9; col++) {
			Queue<Integer> disponibles = new LinkedList<>(columnas.get(col));

			for (int c = 0; c < 6; c++) {
				int cantidad = distribucion[c][col];

				for (int k = 0; k < cantidad; k++) {
					int[][] carton = cartones.get(c);

					int fila;

					do {
						fila = rand.nextInt(3);
					} while (carton[fila][col] != 0);
					//carton[fila][col] = disponibles.poll();
					carton[fila][col] = 1;
				}
			}
		}

		// 5. Ajustar estructura y ordenar columnas
		for (int[][] carton : cartones) {
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

		ordenarCartones(cartones);
		return cartones;
	}
	
	private static void imprimirPatron(List<int[][]> patron) {
		for (int i = 0; i < patron.size(); i++) {
			String insert = "INSERT INTO PATRONCARTON VALUES (SEQ_PATRONCARTON.NEXTVAL, ";
			
			for (int j = 0; j < 3; j++) {
				for (int j2 = 0; j2 < 9; j2++) {
					insert = insert + patron.get(i)[j][j2] + ",";
				}
			}
			
			insert = insert + " SYSDATE);";
			
			System.out.println(insert);
		}
	}

	// Cuenta números en una fila
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

	private static void ordenarColumna(int[][] carton, int col) {
		List<Integer> nums = new ArrayList<>();
		for (int f = 0; f < 3; f++) if (carton[f][col] != 0) nums.add(carton[f][col]);
		Collections.sort(nums);
		int i = 0;
		for (int f = 0; f < 3; f++) {
			if (carton[f][col] != 0) carton[f][col] = nums.get(i++);
		}
	}

	private static boolean tieneMasDeDosConsecutivos(int[] fila) {
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

	private static void reordenarFila(int[] fila) {
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

	private static void ordenarCartones(List<int[][]> cartones) {
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
