package Pares;

import java.util.*;

public class EmpresaEstudiante {

    // Versión generalizada del algoritmo de Gale–Shapley
    public static int[] galeShapley(int n, List<List<Integer>> prefProponentes, int[][] rankReceptores) {

        int[] parejaProponente = new int[n];
        int[] parejaReceptor = new int[n];
        Arrays.fill(parejaProponente, -1);
        Arrays.fill(parejaReceptor, -1);

        int[] proxPropuesta = new int[n];
        Queue<Integer> libres = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            libres.add(i);
        }

        while (!libres.isEmpty()) {
            int p = libres.poll(); // proponente actual
            List<Integer> prefs = prefProponentes.get(p);

            // Si el proponente ya propuso a todos no continua
            if (proxPropuesta[p] < prefs.size()) {

                int r = prefs.get(proxPropuesta[p]);
                proxPropuesta[p]++;

                // Caso 1: receptor libre
                if (parejaReceptor[r] == -1) {
                    parejaReceptor[r] = p;
                    parejaProponente[p] = r;
                }
                // Caso 2: receptor ya emparejado
                else {
                    int actual = parejaReceptor[r];
                    // Si el receptor prefiere al nuevo proponente
                    if (rankReceptores[r][p] < rankReceptores[r][actual]) {
                        parejaReceptor[r] = p;
                        parejaProponente[p] = r;
                        parejaProponente[actual] = -1;
                        libres.add(actual); // el anterior queda libre
                    } else {
                        // el receptor rechaza, proponente sigue libre
                        libres.add(p);
                    }
                }
            }
        }

        return parejaProponente;
    }

    public static void main(String[] args) {

        int n = 4;

        // Preferencias de EMPRESAS
        List<List<Integer>> prefEmpresas = new ArrayList<>();
        prefEmpresas.add(Arrays.asList(0, 1, 2, 3)); // E0
        prefEmpresas.add(Arrays.asList(1, 0, 2, 3)); // E1
        prefEmpresas.add(Arrays.asList(2, 3, 0, 1)); // E2
        prefEmpresas.add(Arrays.asList(0, 1, 2, 3)); // E3

        // Preferencias de ESTUDIANTES
        List<List<Integer>> prefEstudiantes = new ArrayList<>();
        prefEstudiantes.add(Arrays.asList(1, 0, 3, 2)); // S0
        prefEstudiantes.add(Arrays.asList(0, 2, 1, 3)); // S1
        prefEstudiantes.add(Arrays.asList(2, 0, 1, 3)); // S2
        prefEstudiantes.add(Arrays.asList(0, 1, 2, 3)); // S3

        // Construcción de rank de los estudiantes
        int[][] rankEstudiantes = new int[n][n];
        for (int s = 0; s < n; s++) {
            Arrays.fill(rankEstudiantes[s], Integer.MAX_VALUE);
            List<Integer> prefs = prefEstudiantes.get(s);
            for (int r = 0; r < prefs.size(); r++) {
                rankEstudiantes[s][prefs.get(r)] = r;
            }
        }

        // Construcción de rank de las empresas
        int[][] rankEmpresas = new int[n][n];
        for (int e = 0; e < n; e++) {
            Arrays.fill(rankEmpresas[e], Integer.MAX_VALUE);
            List<Integer> prefs = prefEmpresas.get(e);
            for (int r = 0; r < prefs.size(); r++) {
                rankEmpresas[e][prefs.get(r)] = r;
            }
        }

        // Empresas proponen
        int[] empEmpresas = galeShapley(n, prefEmpresas, rankEstudiantes);
        System.out.println("EMPRESAS PROPONEN");
        for (int e = 0; e < n; e++) {
            System.out.printf("Empresa %d -> Estudiante %d\n", e, empEmpresas[e]);
        }

        // Estudiantes proponen
        int[] empEstudiantes = galeShapley(n, prefEstudiantes, rankEmpresas);
        System.out.println("ESTUDIANTES PROPONEN");
        for (int s = 0; s < n; s++) {
            System.out.printf("Estudiante %d -> Empresa %d\n", s, empEstudiantes[s]);
        }
    }
}
