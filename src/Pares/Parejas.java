package Pares;

import java.util.*;

public class Parejas {

    public static int[] galeShapley(int n, List<List<Integer>> prefH, int[][] rankM, boolean[][] forbidden) {

        //Arreglos con los las mujeres y hombres y sus parejas
        int[] parejaHombre = new int[n];
        int[] parejaMujer = new int[n];
        Arrays.fill(parejaHombre, -1);
        Arrays.fill(parejaMujer, -1);

        //Indica la proxima persona en la lista a proponer
        int[] proxPropuesta = new int[n];
        //Al principio siempre será la primera en la lista de preferencias
        Arrays.fill(proxPropuesta, 0);

        //Lista de hombres sin pareja
        Queue<Integer> hombresLibres = new LinkedList<>();
        for (int h = 0; h < n; h++) {
            //Si el hombre tiene preferencias se lo asigna libre
            if (!prefH.get(h).isEmpty()) {
                hombresLibres.add(h);
            }
        }

        // Mientras haya hombres libres con mujeres por proponer
        while (!hombresLibres.isEmpty()) {
            //Obtengo el primer hombre libre
            int h = hombresLibres.poll();

            //Obtengo sus preferencias
            List<Integer> prefs = prefH.get(h);
            //Obtengo la mujer a proponer
            int i = proxPropuesta[h];

            // Solo procesamos si el hombre aún tiene mujeres por proponer
            if (i < prefs.size()) {
                //Obtengo la mujer de la lista de preferencias
                int m = prefs.get(i);
                //Aumento en uno, así verifico la proxima mujer de la lista de preferencias
                proxPropuesta[h]++;

                // Si el par (h,m) está prohibido
                if (forbidden[h][m]) {
                    // Solo se lo vuelve a encolar si todavía tiene más mujeres por proponer
                    if (proxPropuesta[h] < prefs.size()) {
                        hombresLibres.add(h);
                    }
                } else {
                    // Caso 1: m libre
                    if (parejaMujer[m] == -1) {
                        //Si la mujer no tiene pareja se asigna las parejas en ambos
                        parejaMujer[m] = h;
                        parejaHombre[h] = m;
                    }
                    // Caso 2: m ya emparejada
                    else {
                        //En este caso hay que ver si la mujer tiene preferencia por el hombre actual que con el que ya esta emparejada
                        int hActual = parejaMujer[m];
                        //Uso del rankM para ver si prefiere al hombre libre
                        if (rankM[m][h] < rankM[m][hActual]) {
                            // m prefiere a h sobre su actual pareja
                            parejaMujer[m] = h;
                            parejaHombre[h] = m;
                            parejaHombre[hActual] = -1;

                            // El hombre rechazado vuelve a la cola si aún tiene opciones
                            if (proxPropuesta[hActual] < prefH.get(hActual).size()) {
                                hombresLibres.add(hActual);
                            }
                        } else {
                            // m prefiere quedarse con su pareja actual entonces el h sigue libre
                            if (proxPropuesta[h] < prefH.get(h).size()) {
                                hombresLibres.add(h);
                            }
                        }
                    }
                }
            }
        }

        return parejaHombre;
    }

    public static void main(String[] args) {

        int n = 4;

        // Pares prohibidos
        boolean[][] forbidden = new boolean[n][n];

        forbidden[0][0] = true;
        forbidden[1][1] = true;


        List<List<Integer>> prefH = new ArrayList<>();
        prefH.add(Arrays.asList(1, 2, 3));      // H0
        prefH.add(Arrays.asList(0, 2, 3));      // H1
        prefH.add(Arrays.asList(0, 1));         // H2
        prefH.add(Arrays.asList(0, 1, 2, 3));   // H3

        // Preferencias de mujeres (filtradas, sin prohibidos)
        List<List<Integer>> prefM = new ArrayList<>();
        prefM.add(Arrays.asList(1, 2, 3)); // M0
        prefM.add(Arrays.asList(0, 2, 3)); // M1
        prefM.add(Arrays.asList(0, 1, 3)); // M2
        prefM.add(Arrays.asList(0, 1, 2)); // M3

        // Construcción de rankM
        int[][] rankM = new int[n][n];
        for (int m = 0; m < n; m++) {
            Arrays.fill(rankM[m], Integer.MAX_VALUE);
            List<Integer> prefs = prefM.get(m);
            for (int r = 0; r < prefs.size(); r++) {
                rankM[m][prefs.get(r)] = r;
            }
        }

        int[] parejas = galeShapley(n, prefH, rankM, forbidden);

        // Pares asignados
        System.out.println("Pares asignados:");
        for (int h = 0; h < n; h++) {
            if (parejas[h] != -1) {
                System.out.printf("Hombre %d -> Mujer %d\n", h, parejas[h]);
            }
        }

        // Listado de solteros
        System.out.println("\nSolteros:");
        // Hombres
        for (int h = 0; h < n; h++) {
            if (parejas[h] == -1) {
                System.out.printf("Hombre %d\n", h);
            }
        }
        // Mujeres
        boolean[] parejasMujer = new boolean[n];
        for (int h = 0; h < n; h++) {
            if (parejas[h] != -1) {
                parejasMujer[parejas[h]] = true;
            }
        }
        for (int m = 0; m < n; m++) {
            if (!parejasMujer[m]) {
                System.out.printf("Mujer %d\n", m);
            }
        }
    }
}
