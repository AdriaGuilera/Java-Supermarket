import java.util.*;

public class Algorismes {

        /////////////////BACKTRACKING//////////////////////////////

    double maxSimilitud = 0.0;
    Vector<String> mejorDistribucion;

    public Vector<String> encontrarMejorDistribucionBacktracking(Vector<String> distribucion, Set<String> productosFijos) {
        maxSimilitud = 0.0;
        mejorDistribucion = null;


        // Creamos una lista de índices que no están fijos
        List<Integer> indicesMovibles = new ArrayList<>();
        for (int i = 0; i < distribucion.size(); i++) {
            if (!productosFijos.contains(distribucion.get(i))) {
                indicesMovibles.add(i);
            }
        }

        // Ejecutar el algoritmo de backtracking
        backtrack(distribucion, productosFijos, indicesMovibles, 0);

        return mejorDistribucion; // Retornar la mejor distribución encontrada
    }

    private void backtrack(Vector<String> distribucion, Set<String> productosFijos, List<Integer> indicesMovibles, int nivel) {
        // Caso base: si hemos llegado al final de los índices movibles
        if (nivel == indicesMovibles.size()) {
            double similitudActual = calcularSimilitudTotal(distribucion);
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
            backtrack(distribucion, productosFijos, indicesMovibles, nivel + 1);

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


    public Vector<String> encontrarMejorDistribucionHillClimbing(Vector<String> distribucion, Set<String> productosFijos) {
        // Generar una solución inicial
        Vector<String> mejorDistribucion = generarSolucionInicial(distribucion, productosFijos);
        double maxSimilitud = calcularSimilitudTotal(mejorDistribucion);

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
                        double similitudVecino = calcularSimilitudTotal(vecino);

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

        public double calcularSimilitudTotal(Vector<String> distribucio){

            int tamano_distribucio=distribucio.size();
            double similitud_total=CtrlProducte.obtenir_similitud(distribucio.get(0),distribucio.get(tamano_distribucio-1));

            for(int i=0; i<tamano_distribucio-1; ++i){
                similitud_total+=CtrlProducte.obtenir_similitud(distribucio.get(i),distribucio.get(i+1));
            }
            return similitud_total;
        }


}
