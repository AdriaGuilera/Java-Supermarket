package classes;

import java.util.*;

public class Algorismes {

    public Algorismes() {}
        /////////////////BACKTRACKING//////////////////////////////

    double maxSimilitud = 0.0;
    Vector<String> mejorDistribucion;

    public Vector<String> encontrarMejorDistribucionBacktracking(Vector<String> distribucion, Set<String> productosFijos,Map<String, Producte>productes_magatzem) {
        maxSimilitud = 0.0;
        mejorDistribucion = distribucion;


        // Creamos una lista de índices que no están fijos
        List<Integer> indicesMovibles = new ArrayList<>();
        for (int i = 0; i < distribucion.size(); i++) {
            if (!productosFijos.contains(distribucion.get(i))) {
                indicesMovibles.add(i);
            }
        }

        // Ejecutar el algoritmo de backtracking
        backtrack(distribucion, productosFijos, indicesMovibles, 0, productes_magatzem);

        return mejorDistribucion; // Retornar la mejor distribución encontrada
    }

    private void backtrack(Vector<String> distribucion, Set<String> productosFijos, List<Integer> indicesMovibles, int nivel,Map<String, Producte>productes_magatzem) {
        // Caso base: si hemos llegado al final de los índices movibles
        if (nivel == indicesMovibles.size()) {
            double similitudActual = calcularSimilitudTotal(distribucion, productes_magatzem);
            if (similitudActual > maxSimilitud) {
                maxSimilitud = similitudActual;
                mejorDistribucion = new Vector<>(distribucion); // Guardar la mejor distribución
            }
            return;
        }

        // Permutar los productos movibles
        for (int i = nivel; i < indicesMovibles.size(); i++) {
            // Intercambiar productos en los índices movibles
            Collections.swap(distribucion, indicesMovibles.get(nivel), indicesMovibles.get(i));

            // Llamada recursiva al siguiente nivel
            backtrack(distribucion, productosFijos, indicesMovibles, nivel + 1,productes_magatzem);

            // Deshacer el intercambio (backtracking)
            Collections.swap(distribucion, indicesMovibles.get(nivel), indicesMovibles.get(i));
        }
    }

///////////////////////////////HILL CLIMBING//////////////////////////////////////

    // Genera una solución inicial respetando los productos fijos
    private Vector<String> generarSolucionInicial(Vector<String> distribucion, Set<String> productosFijos) {
        Vector<String> solucionInicial = new Vector<>(distribucion);
        List<String> productosMovibles = new ArrayList<>();

        // Extraer los productos movibles (no fijos)
        for (String producto : distribucion) {
            if (!productosFijos.contains(producto)) {
                productosMovibles.add(producto);
            }
        }

        // Mezclamos los productos movibles de forma aleatoria
        Collections.shuffle(productosMovibles);

        // Colocamos los productos movibles en las posiciones que no están fijas
        int indexMovible = 0;
        for (int i = 0; i < solucionInicial.size(); i++) {
            if (!productosFijos.contains(solucionInicial.get(i))) {
                solucionInicial.set(i, productosMovibles.get(indexMovible++));
            }
        }

        return solucionInicial;
    }


    public Vector<String> encontrarMejorDistribucionHillClimbing(Vector<String> distribucion, Set<String> productosFijos, Map<String, Producte>productes_magatzem) {
        // Generar una solución inicial
        Vector<String> mejorDistribucion = generarSolucionInicial(distribucion, productosFijos);
        double maxSimilitud = calcularSimilitudTotal(mejorDistribucion, productes_magatzem);

        boolean hayMejora = true;
        while (hayMejora) {

            hayMejora = false;
            Vector<String> mejorVecino = null;
            double mejorSimilitudVecino = maxSimilitud;

            // Generar todos los vecinos mediante swap y evaluar su similitud
            for (int i = 0; i < mejorDistribucion.size() - 1; i++) {
                for (int j = i + 1; j < mejorDistribucion.size(); j++) {
                    // Solo intentamos el swap si ambos productos no son fijos
                    if (!productosFijos.contains(mejorDistribucion.get(i)) && !productosFijos.contains(mejorDistribucion.get(j))) {
                        // Crear una copia de la distribución actual para probar el swap
                        Vector<String> vecino = new Vector<>(mejorDistribucion);
                        Collections.swap(vecino, i, j);
                        double similitudVecino = calcularSimilitudTotal(vecino,productes_magatzem);

                        // Si el vecino es mejor, lo guardamos como el mejor encontrado en esta iteración
                        if (similitudVecino > mejorSimilitudVecino) {
                            mejorSimilitudVecino = similitudVecino;
                            mejorVecino = vecino;
                            hayMejora = true;
                        }
                    }
                }
            }

            // Si encontramos un vecino mejor, lo usamos como la nueva configuración
            if (hayMejora) {
                mejorDistribucion = mejorVecino;
                maxSimilitud = mejorSimilitudVecino;
            }
        }

        return mejorDistribucion;
    }



    ///////////////////////////////FUNCIONES COMPARTIDAS//////////////////////////////////////
    public double get_similitud(String nom1, String nom2, Map<String, Producte>productes_magatzem) {
        if(nom1==null ||nom2==null) return 0;
        return productes_magatzem.get(nom1).getSimilitud(nom2);
    }

        public double calcularSimilitudTotal(Vector<String> distribucio, Map<String, Producte>productes_magatzem){

            int tamano_distribucio=distribucio.size();
            double similitud_total= get_similitud(distribucio.get(0),distribucio.get(tamano_distribucio-1), productes_magatzem);

            for(int i=0; i<tamano_distribucio-1; ++i){
                similitud_total+= get_similitud(distribucio.get(i),distribucio.get(i+1),productes_magatzem);
            }
            return similitud_total;
        }


}
