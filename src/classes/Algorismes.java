package classes;

import java.util.*;

/**
 * Clase que implementa algoritmos de optimización para encontrar distribuciones óptimas de productos.
 */
public class Algorismes {

    /**
     * Máxima similitud encontrada durante la ejecución de un algoritmo.
     */
    private double maxSimilitud = 0.0;

    /**
     * Mejor distribución encontrada durante la ejecución de un algoritmo.
     */
    private Vector<String> mejorDistribucion;

    /**
     * Constructor vacío para la clase Algorismes.
     */
    public Algorismes() {}

    ////////////////////// BACKTRACKING /////////////////////////////

    /**
     * Encuentra la mejor distribución de productos utilizando el algoritmo de Backtracking.
     *
     * @param distribucion      Vector con la distribución inicial.
     * @param productosFijos    Conjunto de productos fijos que no se pueden mover.
     * @param productesMagatzem Mapa de productos con sus datos asociados.
     * @return La mejor distribución encontrada.
     */
    public Vector<String> encontrarMejorDistribucionBacktracking(Vector<String> distribucion, Set<String> productosFijos, Map<String, Producte> productesMagatzem) {
        maxSimilitud = 0.0;
        mejorDistribucion = distribucion;

        // Crear lista de índices de productos movibles
        List<Integer> indicesMovibles = new ArrayList<>();
        for (int i = 0; i < distribucion.size(); i++) {
            if (!productosFijos.contains(distribucion.get(i))) {
                indicesMovibles.add(i);
            }
        }

        // Ejecutar el algoritmo de Backtracking
        backtrack(distribucion, productosFijos, indicesMovibles, 0, productesMagatzem);

        return mejorDistribucion;
    }

    /**
     * Función auxiliar para ejecutar el algoritmo de Backtracking.
     *
     * @param distribucion      Vector con la distribución actual.
     * @param productosFijos    Conjunto de productos fijos.
     * @param indicesMovibles   Lista de índices movibles.
     * @param nivel             Nivel actual en el árbol de backtracking.
     * @param productesMagatzem Mapa de productos con sus datos asociados.
     */
    private void backtrack(Vector<String> distribucion, Set<String> productosFijos, List<Integer> indicesMovibles, int nivel, Map<String, Producte> productesMagatzem) {
        if (nivel == indicesMovibles.size()) {
            double similitudActual = calcularSimilitudTotal(distribucion, productesMagatzem);
            if (similitudActual > maxSimilitud) {
                maxSimilitud = similitudActual;
                mejorDistribucion = new Vector<>(distribucion);
            }
            return;
        }

        for (int i = nivel; i < indicesMovibles.size(); i++) {
            Collections.swap(distribucion, indicesMovibles.get(nivel), indicesMovibles.get(i));
            backtrack(distribucion, productosFijos, indicesMovibles, nivel + 1, productesMagatzem);
            Collections.swap(distribucion, indicesMovibles.get(nivel), indicesMovibles.get(i));
        }
    }

    ////////////////////// HILL CLIMBING /////////////////////////////

    /**
     * Encuentra la mejor distribución de productos utilizando el algoritmo de Hill Climbing.
     *
     * @param distribucion      Vector con la distribución inicial.
     * @param productosFijos    Conjunto de productos fijos que no se pueden mover.
     * @param productesMagatzem Mapa de productos con sus datos asociados.
     * @return La mejor distribución encontrada.
     */
    public Vector<String> encontrarMejorDistribucionHillClimbing(Vector<String> distribucion, Set<String> productosFijos, Map<String, Producte> productesMagatzem) {
        Vector<String> mejorDistribucion = generarSolucionInicial(distribucion, productosFijos);
        double maxSimilitud = calcularSimilitudTotal(mejorDistribucion, productesMagatzem);

        boolean hayMejora = true;
        while (hayMejora) {
            hayMejora = false;
            Vector<String> mejorVecino = null;
            double mejorSimilitudVecino = maxSimilitud;

            for (int i = 0; i < mejorDistribucion.size() - 1; i++) {
                for (int j = i + 1; j < mejorDistribucion.size(); j++) {
                    if (!productosFijos.contains(mejorDistribucion.get(i)) && !productosFijos.contains(mejorDistribucion.get(j))) {
                        Vector<String> vecino = new Vector<>(mejorDistribucion);
                        Collections.swap(vecino, i, j);
                        double similitudVecino = calcularSimilitudTotal(vecino, productesMagatzem);

                        if (similitudVecino > mejorSimilitudVecino) {
                            mejorSimilitudVecino = similitudVecino;
                            mejorVecino = vecino;
                            hayMejora = true;
                        }
                    }
                }
            }

            if (hayMejora) {
                mejorDistribucion = mejorVecino;
                maxSimilitud = mejorSimilitudVecino;
            }
        }

        return mejorDistribucion;
    }

    /**
     * Genera una solución inicial respetando los productos fijos.
     *
     * @param distribucion   Vector con la distribución inicial.
     * @param productosFijos Conjunto de productos fijos.
     * @return Una solución inicial aleatoria que respeta los productos fijos.
     */
    private Vector<String> generarSolucionInicial(Vector<String> distribucion, Set<String> productosFijos) {
        Vector<String> solucionInicial = new Vector<>(distribucion);
        List<String> productosMovibles = new ArrayList<>();

        for (String producto : distribucion) {
            if (!productosFijos.contains(producto)) {
                productosMovibles.add(producto);
            }
        }

        Collections.shuffle(productosMovibles);

        int indexMovible = 0;
        for (int i = 0; i < solucionInicial.size(); i++) {
            if (!productosFijos.contains(solucionInicial.get(i))) {
                solucionInicial.set(i, productosMovibles.get(indexMovible++));
            }
        }

        return solucionInicial;
    }

    ////////////////////// FUNCIONES COMPARTIDAS /////////////////////////////

    /**
     * Calcula la similitud entre dos productos.
     *
     * @param nom1             Nombre del primer producto.
     * @param nom2             Nombre del segundo producto.
     * @param productesMagatzem Mapa de productos con sus datos asociados.
     * @return El valor de similitud entre los dos productos.
     */
    public double getSimilitud(String nom1, String nom2, Map<String, Producte> productesMagatzem) {
        if (nom1 == null || nom2 == null) return 0;
        return productesMagatzem.get(nom1).getSimilitud(nom2);
    }

    /**
     * Calcula la similitud total de una distribución.
     *
     * @param distribucion     Vector con la distribución de productos.
     * @param productesMagatzem Mapa de productos con sus datos asociados.
     * @return El valor total de similitud de la distribución.
     */
    public double calcularSimilitudTotal(Vector<String> distribucion, Map<String, Producte> productesMagatzem) {
        int tamanoDistribucion = distribucion.size();
        double similitudTotal = getSimilitud(distribucion.get(0), distribucion.get(tamanoDistribucion - 1), productesMagatzem);

        for (int i = 0; i < tamanoDistribucion - 1; ++i) {
            similitudTotal += getSimilitud(distribucion.get(i), distribucion.get(i + 1), productesMagatzem);
        }
        return similitudTotal;
    }
}
