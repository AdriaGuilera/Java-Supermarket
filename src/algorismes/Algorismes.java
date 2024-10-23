package algorismes;

import java.util.ArrayList;
import java.util.List;

public class Algorismes {

    // Función pública de backtracking para encontrar todas las combinaciones de tamaño k en un conjunto de enteros
    public List<List<Integer>> backtracking(int[] nums, int k) {
        List<List<Integer>> result = new ArrayList<>();
        backtrack(result, new ArrayList<>(), nums, k, 0);
        return result;
    }

    // Función privada auxiliar para realizar el backtracking
    private void backtrack(List<List<Integer>> result, List<Integer> tempList, int[] nums, int k, int start) {
        // Si se ha alcanzado la longitud k, se añade la combinación al resultado
        if (tempList.size() == k) {
            result.add(new ArrayList<>(tempList));
        } else {
            for (int i = start; i < nums.length; i++) {
                // Añadir el número actual a la combinación
                tempList.add(nums[i]);
                // Llamada recursiva al siguiente elemento
                backtrack(result, tempList, nums, k, i + 1);
                // Retirar el último número para explorar otra combinación
                tempList.remove(tempList.size() - 1);
            }
        }
    }

    // Función pública de Hill Climbing para maximizar una función objetivo
    public double hillClimb(double start, double stepSize, int maxIterations) {
        double current = start;
        double currentValue = objectiveFunction(current); // Evaluamos la función objetivo

        for (int i = 0; i < maxIterations; i++) {
            double next = current + stepSize;  // Moverse hacia adelante
            double nextValue = objectiveFunction(next);  // Evaluar la función en el nuevo punto

            if (nextValue > currentValue) {
                current = next;
                currentValue = nextValue; // Si mejora, actualizamos
            } else {
                stepSize = -stepSize; // Cambiar la dirección
                next = current + stepSize;  // Intentar en la dirección opuesta
                nextValue = objectiveFunction(next);

                if (nextValue > currentValue) {
                    current = next;
                    currentValue = nextValue; // Si mejora en la dirección opuesta, actualizamos
                } else {
                    break; // Si no mejora en ninguna dirección, hemos encontrado un máximo local
                }
            }
        }

        return current;  // Devolvemos el mejor valor encontrado
    }

    // Función objetivo que deseamos maximizar (puedes cambiarla según el problema)
    private double objectiveFunction(double x) {
        // Ejemplo simple: función cuadrática -x^2 + 4x + 1
        return -Math.pow(x, 2) + 4 * x + 1;
    }

    public static void main(String[] args) {
        Algorismes alg = new Algorismes();

        // Llamada a la función de backtracking (ya definida previamente)
        int[] nums = {1, 2, 3, 4};
        int k = 2;
        List<List<Integer>> combinaciones = alg.backtracking(nums, k);
        for (List<Integer> combinacion : combinaciones) {
            System.out.println(combinacion);
        }

        // Llamada a la función Hill Climbing
        double startPoint = 0.0;  // Punto inicial
        double stepSize = 0.1;    // Tamaño del paso
        int maxIterations = 100;  // Número máximo de iteraciones

        double maxima = alg.hillClimb(startPoint, stepSize, maxIterations);
        System.out.println("Máximo local encontrado en x = " + maxima);
        System.out.println("Valor de la función en el máximo local: " + alg.objectiveFunction(maxima));
    }
}
