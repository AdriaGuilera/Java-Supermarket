package classes;

import Exepcions.*;
import Exepcions.ProductNotFoundPrestatgeriaException;

import java.util.HashMap;
import java.util.Map;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

/**
 * Clase que representa una prestatgeria (estantería) que contiene productos y sus distribuciones.
 * Permite gestionar productos, posiciones, y modificar su capacidad.
 */
public class Prestatgeria {

	private String id; // Identificador de la prestatgeria
	private int midaPrestatgeria; // Tamaño total de la prestatgeria
	private int midaPrestatge; // Incremento de tamaño por prestatge
	private Map<String, Integer> productes; // Mapa de productos y cantidades
	private Map<Integer, String> posicions; // Mapa de posiciones y productos
	private Set<String> productesFixats; // Productos fijados

	/**
	 * Constructor de la clase Prestatgeria.
	 *
	 * @param id               Identificador de la prestatgeria.
	 * @param midaPrestatgeria Tamaño total de la prestatgeria.
	 * @param midaPrestatge    Tamaño de incremento por prestatge.
	 */
	public Prestatgeria(String id, int midaPrestatgeria, int midaPrestatge) {
		this.id = id;
		this.midaPrestatgeria = midaPrestatgeria;
		this.midaPrestatge = midaPrestatge;
		this.productes = new HashMap<>();
		this.posicions = new HashMap<>();
		this.productesFixats = new HashSet<>();
	}

	/**
	 * Devuelve el conjunto de productos fijados.
	 *
	 * @return Un conjunto de nombres de productos fijados.
	 */
	public Set<String> getProductesFixats() {
		return productesFixats;
	}

	/**
	 * Añade un producto a la prestatgeria.
	 *
	 * @param nomP     Nombre del producto.
	 * @param quantitat Cantidad del producto.
	 * @throws JaExisteixProucteaPrestatgeriaException Si el producto ya existe.
	 * @throws PrestatgeriaFullException               Si no hay espacio en la prestatgeria.
	 * @throws QuanitatInvalidException                Si la cantidad es inválida.
	 */
	public void afegirProducte(String nomP, Integer quantitat)
			throws JaExisteixProucteaPrestatgeriaException, PrestatgeriaFullException, QuanitatInvalidException {
		if (quantitat <= 0) {
			throw new QuanitatInvalidException(1);
		}
		if (!productes.containsKey(nomP)) {
			int pos = 0;
			while (posicions.containsKey(pos)) {
				pos++;
			}
			if (pos < midaPrestatgeria) {
				posicions.put(pos, nomP);
				productes.put(nomP, quantitat);
			} else {
				throw new PrestatgeriaFullException(id);
			}
		} else {
			throw new JaExisteixProucteaPrestatgeriaException(nomP, id);
		}
	}

	/**
	 * Elimina un producto de la prestatgeria.
	 *
	 * @param nomP Nombre del producto a eliminar.
	 * @throws ProductNotFoundPrestatgeriaException Si el producto no se encuentra en la prestatgeria.
	 */
	public void eliminarProducte(String nomP) throws ProductNotFoundPrestatgeriaException {
		if (productes.containsKey(nomP)) {
			int pos = getPos(nomP);
			productes.remove(nomP);
			productesFixats.remove(nomP);
			posicions.remove(pos);
		} else {
			throw new ProductNotFoundPrestatgeriaException(id, nomP);
		}
	}

	/**
	 * Elimina un producto de la prestatgeria.
	 *
	 * @param nomP Nombre del producto a eliminar.
	 */
	public void eliminarProducteSinRevisarSiExiste(String nomP)   {
		if (productes.containsKey(nomP)) {
			int pos = getPos(nomP);
			productes.remove(nomP);
			productesFixats.remove(nomP);
			posicions.remove(pos);
		}
	}

	/**
	 * Devuelve el identificador de la prestatgeria.
	 *
	 * @return El identificador de la prestatgeria.
	 */
	public String getId() {
		return id;
	}

	/**
	 * Devuelve el tamaño total de la prestatgeria.
	 *
	 * @return El tamaño total.
	 */
	public int getMidaPrestatgeria() {
		return midaPrestatgeria;
	}

	/**
	 * Devuelve los nombres de los productos en la prestatgeria.
	 *
	 * @return Un vector con los nombres de los productos.
	 */
	public Vector<String> getNomsProductes() {
		return new Vector<>(productes.keySet());
	}

	/**
	 * Devuelve el número de productos en la prestatgeria.
	 *
	 * @return El número de productos.
	 */
	public int getProductesSize() {
		return productes.size();
	}

	/**
	 * Devuelve el mapa de productos y cantidades.
	 *
	 * @return Un mapa de productos y sus cantidades.
	 */
	public Map<String, Integer> getProductes() {
		return productes;
	}

	/**
	 * Devuelve el mapa de posiciones y productos.
	 *
	 * @return Un mapa de posiciones y nombres de productos.
	 */
	public Map<Integer, String> getPosicions() {
		return posicions;
	}

	/**
	 * Devuelve la posición de un producto en la prestatgeria.
	 *
	 * @param nomP Nombre del producto.
	 * @return La posición del producto.
	 * @throws ProductNotFoundPrestatgeriaException Si el producto no se encuentra en la prestatgeria.
	 */
	public int getPos(String nomP) throws ProductNotFoundPrestatgeriaException {
		if (!productes.containsKey(nomP)) {
			throw new ProductNotFoundPrestatgeriaException(id, nomP);
		}
		for (Map.Entry<Integer, String> entry : posicions.entrySet()) {
			if (entry.getValue().equals(nomP)) {
				return entry.getKey();
			}
		}
		return -1;
	}

	/**
	 * Incrementa la cantidad de un producto en la prestatgeria.
	 *
	 * @param nomP     Nombre del producto.
	 * @param quantitat Cantidad a incrementar.
	 * @throws QuanitatInvalidException Si la cantidad es inválida.
	 */
	public void incrementarQuantitat(String nomP, Integer quantitat) throws QuanitatInvalidException {
		if (quantitat < 0) {
			throw new QuanitatInvalidException(0);
		}
		if (productes.containsKey(nomP)) {
			productes.compute(nomP, (k, currentQuantitat) -> currentQuantitat + quantitat);
		}
	}

	/**
	 * Reduce la cantidad de un producto en la prestatgeria.
	 *
	 * @param nomP     Nombre del producto.
	 * @param quantitat Cantidad a reducir.
	 * @return La cantidad realmente reducida.
	 * @throws QuanitatInvalidException                Si la cantidad es inválida.
	 * @throws ProductNotFoundPrestatgeriaException    Si el producto no se encuentra.
	 */
	public int decrementarQuantitat(String nomP, Integer quantitat)
			throws QuanitatInvalidException, ProductNotFoundPrestatgeriaException {
		if (quantitat < 0) {
			throw new QuanitatInvalidException(0);
		}
		if (productes.containsKey(nomP)) {
			int quantitatActual = productes.get(nomP);
			if (quantitatActual - quantitat >= 0) {
				productes.put(nomP, quantitatActual - quantitat);
				return quantitat;
			} else {
				productes.put(nomP, 0);
				return quantitatActual;
			}
		} else {
			throw new ProductNotFoundPrestatgeriaException(id, nomP);
		}
	}
	/**
	 * Incrementa la capacidad total de la prestatgeria añadiendo un prestatge.
	 */
	public void afegirPrestatge() {
		midaPrestatgeria += midaPrestatge;
	}

	/**
	 * Elimina un prestatge de la prestatgeria, removiendo los productos que ocupen dicho espacio.
	 *
	 * @return Un mapa con los productos eliminados y sus cantidades.
	 */
	public Map<String, Integer> eliminarPrestatge() throws MidaPrestatgeriaMinException {
		Map<String, Integer> productesEliminats = new HashMap<>();
		if (midaPrestatge < midaPrestatgeria) {
			for (int i = midaPrestatgeria - midaPrestatge; i < midaPrestatgeria; i++) {
				if (posicions.containsKey(i)) {
					String nomP = posicions.get(i);
					if (nomP != null) productesEliminats.put(nomP, productes.get(nomP));
					productes.remove(nomP);
					productesFixats.remove(nomP);
					posicions.remove(i);
				}
			}
			midaPrestatgeria -= midaPrestatge;
			return productesEliminats;
		}
		else{
			throw new MidaPrestatgeriaMinException(id);
		}

	}

	/**
	 * Fija un producto en la prestatgeria, evitando que sea movido o eliminado.
	 *
	 * @param nomP Nombre del producto a fijar.
	 * @throws ProductNotFoundPrestatgeriaException Si el producto no se encuentra en la prestatgeria.
	 * @throws ProducteFixatException              Si el producto ya está fijado.
	 */
	public void fixarProductePrestatgeria(String nomP)
			throws ProductNotFoundPrestatgeriaException, ProducteFixatException {
		if (!productes.containsKey(nomP)) {
			throw new ProductNotFoundPrestatgeriaException(id, nomP);
		}
		if (productesFixats.contains(nomP)) {
			throw new ProducteFixatException(nomP);
		}
		productesFixats.add(nomP);
	}

	/**
	 * Desfija un producto previamente fijado en la prestatgeria.
	 *
	 * @param nomP Nombre del producto a desfijar.
	 * @throws ProductNotFoundPrestatgeriaException Si el producto no se encuentra en la prestatgeria.
	 * @throws ProducteNoFixatException            Si el producto no está fijado.
	 */
	public void desfixarProductePrestatgeria(String nomP)
			throws ProductNotFoundPrestatgeriaException, ProducteNoFixatException {
		if (!productes.containsKey(nomP)) {
			throw new ProductNotFoundPrestatgeriaException(id, nomP);
		}
		if (!productesFixats.contains(nomP)) {
			throw new ProducteNoFixatException(nomP);
		}
		productesFixats.remove(nomP);
	}

	/**
	 * Devuelve la distribución actual de productos en la prestatgeria.
	 *
	 * @return Un mapa que asocia posiciones con nombres de productos.
	 */
	public Map<Integer, String> getDistribucio() {
		return posicions;
	}

	/**
	 * Verifica si un producto específico está presente en la prestatgeria.
	 *
	 * @param nom Nombre del producto.
	 * @return {@code true} si el producto está presente, {@code false} en caso contrario.
	 */
	public boolean estaAPrestatgeria(String nom) {
		return productes.containsKey(nom);
	}

	/**
	 * Obtiene la cantidad de un producto específico en la prestatgeria.
	 *
	 * @param nom Nombre del producto.
	 * @return Cantidad del producto.
	 * @throws ProductNotFoundPrestatgeriaException Si el producto no se encuentra en la prestatgeria.
	 */
	public int getQuantProducte(String nom) throws ProductNotFoundPrestatgeriaException {
		if (!productes.containsKey(nom)) {
			throw new ProductNotFoundPrestatgeriaException(id, nom);
		}
		return productes.get(nom);
	}

	/**
	 * Mueve un producto de una posición de origen a una de destino.
	 *
	 * @param huecoOrigen Posición de origen.
	 * @param huecoDesti  Posición de destino.
	 * @throws ProducteNotInHuecoException Si no hay producto en el hueco de origen.
	 * @throws InvalidHuecosException      Si las posiciones son inválidas.
	 */
	public void moureProducte(int huecoOrigen, int huecoDesti)
			throws ProducteNotInHuecoException, InvalidHuecosException {
		if (huecoDesti < 0 || huecoDesti >= midaPrestatgeria || huecoOrigen < 0 || huecoOrigen >= midaPrestatgeria
				|| huecoOrigen == huecoDesti) {
			throw new InvalidHuecosException();
		}
		if (!posicions.containsKey(huecoOrigen)) {
			throw new ProducteNotInHuecoException(huecoOrigen);
		}

		String nomPorigen = posicions.get(huecoOrigen);
		String nomPdesti = posicions.get(huecoDesti);

		if (productesFixats.contains(nomPorigen)) {
			throw new ProducteFixatException(nomPorigen);
		}
		if (nomPdesti != null && productesFixats.contains(nomPdesti)) {
			throw new ProducteFixatException(nomPdesti);
		}

		if (posicions.containsKey(huecoDesti)) {
			posicions.remove(huecoOrigen);
			posicions.remove(huecoDesti);
			posicions.put(huecoDesti, nomPorigen);
			posicions.put(huecoOrigen, nomPdesti);
		} else {
			String nomP = posicions.get(huecoOrigen);
			posicions.remove(huecoOrigen);
			posicions.put(huecoDesti, nomP);
		}
	}

	/**
	 * Establece una nueva distribución de productos en la prestatgeria.
	 *
	 * @param ordre Un vector con el nuevo orden de los productos.
	 */
	public void setDistribucio(Vector<String> ordre) {
		int i = 0;
		posicions.clear();
		for (String nomP : ordre) {
			posicions.put(i, nomP);
			i++;
		}
	}
}



