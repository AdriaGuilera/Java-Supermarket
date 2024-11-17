package classes;

/**
 * Clase genérica que representa un par de valores (clave-valor).
 *
 * @param <K> Tipo de la clave.
 * @param <V> Tipo del valor.
 */
public class Pair<K, V> {

    private K key;   // Clave del par
    private V value; // Valor del par

    /**
     * Constructor por defecto de la clase Pair.
     * Inicializa la clave y el valor a {@code null}.
     */
    public Pair() {
        this.key = null;
        this.value = null;
    }

    /**
     * Constructor que inicializa la clave y el valor con los valores proporcionados.
     *
     * @param key   Clave del par.
     * @param value Valor del par.
     */
    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    /**
     * Obtiene la clave del par.
     *
     * @return La clave del par.
     */
    public K getKey() {
        return key;
    }

    /**
     * Obtiene el valor del par.
     *
     * @return El valor del par.
     */
    public V getValue() {
        return value;
    }

    /**
     * Establece un nuevo valor para el par.
     *
     * @param value El nuevo valor a asignar.
     */
    public void setValue(V value) {
        this.value = value;
    }
}

