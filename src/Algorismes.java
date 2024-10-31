import java.util.*;

public class Algorismes {

        /////////////////BACKTRACK//////////////////////////////

    double maxSimilitud = 0.0;
    Vector<String> mejorDistribucion;

    public Vector<String> encontrarMejorDistribucionBacktrack(Vector<String> distribucion, Set<String> productosFijos) {
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
