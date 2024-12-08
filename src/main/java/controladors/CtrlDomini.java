package controladors;

import java.io.IOException;
import java.util.*;
import Exepcions.*;
import classes.*;

import static java.lang.Integer.max;
import static java.lang.Integer.min;



/**
 * Controlador principal del dominio para gestionar productos, prestatgeries, comandas y caja.
 */
public class CtrlDomini {

    public CtrlProducte ctrlProducte; // Controlador de productos
    public CtrlPrestatgeria ctrlPrestatgeria; // Controlador de prestatgeries
    public Caixa caixa; // Controlador de la caja
    public CtrlComandes ctrlComandes; // Controlador de comandas
    public Algorismes algorismes; // Algoritmos de optimización
    public Database database;
    /**
     * Constructor que inicializa todos los controladores auxiliares.
     */
    public CtrlDomini() {
        ctrlComandes = new CtrlComandes();
        ctrlProducte = new CtrlProducte();
        ctrlPrestatgeria = new CtrlPrestatgeria();
        caixa = new Caixa();
        algorismes = new Algorismes();
        database = new Database();
    }

    public void guardar() throws IOException {

        database.saveAll(ctrlComandes.getComandes().values(), ctrlProducte.getMagatzem().values(), ctrlPrestatgeria.getPrestatgeries().values(), caixa);

        ctrlComandes = new CtrlComandes();
        ctrlProducte = new CtrlProducte();
        ctrlPrestatgeria = new CtrlPrestatgeria();
        caixa = new Caixa();
    }


    /**
     * Crea una nueva comanda.
     *
     * @param nomComanda Nombre de la comanda.
     * @throws IllegalArgumentException Si el nombre de la comanda es nulo o vacío.
     */
    public void crearComanda(String nomComanda) throws IllegalArgumentException, IOException {
        if (nomComanda == null || nomComanda.isEmpty()) {
            throw new IllegalArgumentException("El nom de la comanda no pot estar buit.");
        }
        ctrlComandes.crearComanda(nomComanda);
        database.addComanda(ctrlComandes.getComandaUnica(nomComanda));
        ctrlComandes.eliminarComanda(nomComanda);
    }


    /**
     * Elimina una comanda existente.
     *
     * @param nomComanda Nombre de la comanda.
     * @throws IllegalArgumentException Si el nombre de la comanda es nulo o vacío.
     * @throws ComandaNotFoundException Si la comanda no existe.
     */
    public void eliminarComanda(String nomComanda) throws IllegalArgumentException, ComandaNotFoundException, IOException {
        if (nomComanda == null || nomComanda.isEmpty()) {
            throw new IllegalArgumentException("El nom de la comanda no pot estar buit.");
        }

        database.deleteComanda(nomComanda);
    }

    /**
     * Añade un producto a una comanda.
     *
     * @param nomComanda Nombre de la comanda.
     * @param nomProducte Nombre del producto.
     * @param quantitat Cantidad a añadir.
     * @throws IllegalArgumentException Si algún argumento es inválido.
     * @throws QuanitatInvalidException Si la cantidad es inválida.
     * @throws ComandaNotFoundException Si la comanda no existe.
     * @throws ProductNotFoundMagatzemException Si el producto no existe en el almacén.
     */
    public void afegirProducteComanda(String nomComanda, String nomProducte, int quantitat)
            throws IllegalArgumentException, QuanitatInvalidException, ComandaNotFoundException, ProductNotFoundMagatzemException, IOException {
        if (nomComanda == null || nomComanda.isEmpty()) {
            throw new IllegalArgumentException("El nom de la comanda no pot estar buit.");
        }
        if (nomProducte == null || nomProducte.isEmpty()) {
            throw new IllegalArgumentException("El nom del producte no pot estar buit.");
        }
        if (quantitat <= 0) {
            throw new IllegalArgumentException("La quantitat ha de ser > 0.");
        }
        if (!database.existeixComanda(nomComanda)) {
            throw new ComandaNotFoundException(nomComanda);
        }
        if (!database.existeixProducte(nomProducte)) {
            throw new ProductNotFoundMagatzemException(nomProducte);
        }
        //Cargamos la comanda y el producte
        Comanda comanda = database.getComanda(nomComanda);
        Producte producte = database.getProducte(nomProducte);
        ctrlComandes.cargarComanda(comanda);
        ctrlProducte.carregarProducte(producte);

        //Afegim el producte a al comanda
        ctrlComandes.afegirProducteComanda(nomComanda, nomProducte, quantitat);
    }

    /**
     * Elimina un producto de una comanda.
     *
     * @param nomComanda Nombre de la comanda.
     * @param nomProducte Nombre del producto.
     * @param quantitat Cantidad a eliminar.
     * @throws IllegalArgumentException Si algún argumento es inválido.
     * @throws ProductNotFoundComandaException Si el producto no existe en la comanda.
     */
    public void eliminarProducteComanda(String nomComanda, String nomProducte, int quantitat)
            throws IllegalArgumentException, ProductNotFoundComandaException, IOException {
        if (nomComanda == null || nomComanda.isEmpty()) {
            throw new IllegalArgumentException("El nom de la comanda no pot estar buit.");
        }
        if (nomProducte == null || nomProducte.isEmpty()) {
            throw new IllegalArgumentException("El nom del producte no pot estar buit.");
        }
        if (quantitat <= 0) {
            throw new IllegalArgumentException("La quantitat ha de ser > 0.");
        }

        //Cargamos la comanda y el produco
        Comanda comanda = database.getComanda(nomComanda);
        Producte producte = database.getProducte(nomProducte);
        ctrlComandes.cargarComanda(comanda);
        ctrlProducte.carregarProducte(producte);

        //Eliminamos el producto de la comanda
        ctrlComandes.eliminarProducteComanda(nomComanda, nomProducte, quantitat);
    }

    /**
     * Obtiene una comanda específica.
     *
     * @param nomComanda Nombre de la comanda.
     * @return La comanda encontrada.
     * @throws IllegalArgumentException Si el nombre es nulo o vacío.
     * @throws ComandaNotFoundException Si la comanda no existe.
     */
    public Comanda getComandaUnica(String nomComanda) throws IllegalArgumentException, ComandaNotFoundException {
        if (nomComanda == null || nomComanda.isEmpty()) {
            throw new IllegalArgumentException("El nom de la comanda no pot estar buit.");
        }
        return ctrlComandes.getComandaUnica(nomComanda);
    }

    /**
     * Obtiene todas las comandas registradas.
     *
     * @return Un mapa con todas las comandas.
     */
    public Map<String, Comanda> getComandes() {
        return ctrlComandes.getComandes();
    }

    /**
     * Genera una comanda automática basada en los productos faltantes en el almacén.
     *
     * @param nomComanda Nombre de la comanda a generar.
     * @throws IllegalArgumentException Si el nombre es nulo o vacío.
     */
    public void generarComandaAutomatica(String nomComanda) throws IllegalArgumentException, IOException {
        if (nomComanda == null || nomComanda.isEmpty()) {
            throw new IllegalArgumentException("El nom de la comanda automatica no pot estar buit.");
        }
        //Cargamos el almazen
        Map<String, Producte> productesMagatzem = database.getProductes();
        for (Producte producte : productesMagatzem.values()) {
            ctrlProducte.carregarProducte(producte);
        }
        //Generamos comanda automatica y la guardamos
        Map<String, Integer> productosFaltantes = ctrlProducte.generarComandaAutomatica();
        ctrlComandes.crearComandaAutomatica(nomComanda, productosFaltantes);
        database.addComanda(ctrlComandes.getComandaUnica(nomComanda));
    }

    /**
     * Ejecuta una lista de comandas, actualizando el estado del almacén y las prestatgeries.
     *
     * @param noms Array con los nombres de las comandas a ejecutar.
     * @throws IllegalArgumentException Si el array es nulo.
     * @throws ComandaNotFoundException Si alguna de las comandas no existe.
     */
    public void executarComandes(String[] noms) throws IllegalArgumentException, ComandaNotFoundException, IOException {
        if (noms == null) {
            throw new IllegalArgumentException("Els noms no poden estar buits.");
        }
        //Cargamos las comandas y el magatzem
        Map<String, Producte> productesMagatzem = database.getProductes();
        for (Producte producte : productesMagatzem.values()) {
            ctrlProducte.carregarProducte(producte);
        }

        for(String nom:noms){
            ctrlComandes.cargarComanda(database.getComanda(nom));
        }


        Map<String, Comanda> comandesAExecutar = ctrlComandes.obtenirComandes(noms);
        ctrlProducte.executarComandes(comandesAExecutar);
    }



//Prestatgeria

    /**
     * Añade un producto a una prestatgeria específica.
     *
     * @param nomProducte    Nombre del producto.
     * @param quantitat      Cantidad a añadir.
     * @param idPrestatgeria Identificador de la prestatgeria.
     * @throws PrestatgeriaNotFoundException       Si la prestatgeria no existe.
     * @throws QuanitatInvalidException           Si la cantidad es inválida.
     * @throws MaxHuecoWarning                    Si la cantidad supera el máximo permitido por hueco.
     * @throws ProductNotFoundMagatzemException   Si el producto no está en el almacén.
     * @throws JaExisteixProucteaPrestatgeriaException Si el producto ya existe en la prestatgeria.
     * @throws NotEnoughQuantityMagatzem          Si no hay suficiente cantidad en el almacén.
     * @throws IllegalArgumentException           Si los argumentos son nulos o inválidos.
     */
    public void afegirProductePrestatgeria(String nomProducte, int quantitat, String idPrestatgeria)
            throws PrestatgeriaNotFoundException, QuanitatInvalidException, MaxHuecoWarning, ProductNotFoundMagatzemException,
            JaExisteixProucteaPrestatgeriaException, NotEnoughQuantityMagatzem, IllegalArgumentException, IOException {

        if (idPrestatgeria == null || idPrestatgeria.isEmpty()) {
            throw new IllegalArgumentException("El nom de la Prestatgeria no pot estar buit.");
        }
        if (nomProducte == null || nomProducte.isEmpty()) {
            throw new IllegalArgumentException("El nom del Producte no pot estar buit.");
        }
        if(!ctrlPrestatgeria.existeixPrestatgeria(idPrestatgeria)){
            ctrlPrestatgeria.carregarPrestatgeria(database.getPrestatgeria(idPrestatgeria));
        }
        if(!ctrlProducte.existeixProducte(nomProducte)){
            ctrlProducte.carregarProducte(database.getProducte(nomProducte));
        }
        int maxhueco = ctrlProducte.getMaxHueco(nomProducte);
        int stock = ctrlProducte.getStockMagatzem(nomProducte);
        if(quantitat <= 0) {
            throw new QuanitatInvalidException(1);
        }

        if(stock >= quantitat) {
            if (ctrlPrestatgeria.containsProducte(idPrestatgeria, nomProducte)) {
                int actual = ctrlPrestatgeria.getQuantitatProducte(idPrestatgeria, nomProducte);

                if (quantitat + actual > maxhueco) {
                    ctrlPrestatgeria.incrementarQuantitatProducte(idPrestatgeria, nomProducte, maxhueco - actual);
                    ctrlProducte.decrementarStock(nomProducte, maxhueco - actual);
                    throw new MaxHuecoWarning(nomProducte);
                } else {
                    ctrlPrestatgeria.incrementarQuantitatProducte(idPrestatgeria, nomProducte, quantitat);
                    ctrlProducte.decrementarStock(nomProducte, quantitat);
                }
            } else {
                if (quantitat > maxhueco) {
                    ctrlPrestatgeria.afegirProducte(idPrestatgeria, nomProducte, maxhueco);
                    ctrlProducte.decrementarStock(nomProducte, maxhueco);
                    throw new MaxHuecoWarning(nomProducte);
                } else {
                    ctrlPrestatgeria.afegirProducte(idPrestatgeria, nomProducte, quantitat);
                    ctrlProducte.decrementarStock(nomProducte, quantitat);
                }
            }
        }
        else{
            throw new NotEnoughQuantityMagatzem(nomProducte);
        }


    }

    /**
     * Mueve un producto de un hueco a otro dentro de una prestatgeria.
     *
     * @param id_prestatgeria Identificador de la prestatgeria.
     * @param huecoOrigen     Índice del hueco de origen.
     * @param huecoDestino    Índice del hueco de destino.
     * @throws PrestatgeriaNotFoundException Si la prestatgeria no existe.
     * @throws ProducteNotInHuecoException   Si el producto no está en el hueco de origen.
     * @throws InvalidHuecosException        Si los huecos son inválidos.
     * @throws IllegalArgumentException      Si los argumentos son nulos o inválidos.
     */
    public void moureProducteDeHueco(String id_prestatgeria, int huecoOrigen, int huecoDestino)
            throws PrestatgeriaNotFoundException, InvalidHuecosException, IllegalArgumentException, IOException {
        if (id_prestatgeria == null || id_prestatgeria.isEmpty()) {
            throw new IllegalArgumentException("El nom de la comanda no pot estar buit.");
        }
        if(!ctrlPrestatgeria.existeixPrestatgeria(id_prestatgeria)){
            ctrlPrestatgeria.carregarPrestatgeria(database.getPrestatgeria(id_prestatgeria));
        }
        ctrlPrestatgeria.moureProducte(id_prestatgeria, huecoOrigen, huecoDestino);
    }

    /**
     * Decrementa el stock de un producto en una prestatgeria y lo incrementa en el almacén.
     *
     * @param id_prestatgeria Identificador de la prestatgeria.
     * @param nomProducte     Nombre del producto.
     * @param quantitat       Cantidad a decrementar.
     * @throws QuanitatInvalidException            Si la cantidad es inválida.
     * @throws ProductNotFoundPrestatgeriaException Si el producto no está en la prestatgeria.
     * @throws PrestatgeriaNotFoundException       Si la prestatgeria no existe.
     * @throws MaxMagatzemWarning                  Si el almacén no tiene capacidad suficiente.
     * @throws NotEnoughQuantityPrestatgeriaWarning Si no hay suficiente cantidad en la prestatgeria.
     * @throws IllegalArgumentException            Si los argumentos son nulos o inválidos.
     */
    public void decrementarStockAProducte(String id_prestatgeria, String nomProducte, int quantitat)
            throws QuanitatInvalidException, ProductNotFoundPrestatgeriaException, PrestatgeriaNotFoundException, MaxMagatzemWarning, ProductNotFoundMagatzemException,
            NotEnoughQuantityPrestatgeriaWarning, IllegalArgumentException, IOException {
        if (id_prestatgeria == null || id_prestatgeria.isEmpty()) {
            throw new IllegalArgumentException("El nom de la comanda no pot estar buit.");
        }
        if (nomProducte == null || nomProducte.isEmpty()) {
            throw new IllegalArgumentException("El nom del Producte no pot estar buit.");
        }
        if(!ctrlPrestatgeria.existeixPrestatgeria(id_prestatgeria)){
            ctrlPrestatgeria.carregarPrestatgeria(database.getPrestatgeria(id_prestatgeria));
        }
        if(!ctrlProducte.existeixProducte(nomProducte)){
            ctrlProducte.carregarProducte(database.getProducte(nomProducte));
        }
        int quantitatelim = ctrlPrestatgeria.decrementarQuantitatProducte(id_prestatgeria, nomProducte, quantitat);
        ctrlProducte.incrementarStock(nomProducte, quantitatelim);
        if(quantitatelim == 0){
            throw new NotEnoughQuantityPrestatgeriaWarning(id_prestatgeria, nomProducte);
        }

    }

    /**
     * Genera una distribución de productos en una prestatgeria utilizando el algoritmo de Backtracking.
     *
     * @param id_prestatgeria Identificador de la prestatgeria.
     * @throws PrestatgeriaNotFoundException Si la prestatgeria no existe.
     * @throws IllegalArgumentException      Si los argumentos son nulos o inválidos.
     */
    public void generarDistribucioBacktracking(String id_prestatgeria)
            throws PrestatgeriaNotFoundException, IllegalArgumentException, IOException {
        if(id_prestatgeria == null || id_prestatgeria.isEmpty()) {
            throw new IllegalArgumentException("El nom de la Prestatgeria no pot estar buit.");
        }
        if(!ctrlPrestatgeria.existeixPrestatgeria(id_prestatgeria)){
            ctrlPrestatgeria.carregarPrestatgeria(database.getPrestatgeria(id_prestatgeria));
        }
        Vector<String> productes = ctrlPrestatgeria.getNomsProductes(id_prestatgeria);
        for(String producte:productes){
            if(!ctrlProducte.existeixProducte(producte)){
                ctrlProducte.carregarProducte(database.getProducte(producte));
            }
        }
        Set<String> fixats = ctrlPrestatgeria.getProductesFixats(id_prestatgeria);
        Vector<String> novadist = algorismes.encontrarMejorDistribucionBacktracking(productes , fixats, ctrlProducte.getMagatzem());
        ctrlPrestatgeria.setDistribucio(id_prestatgeria, novadist);
    }

    /**
     * Genera una distribución de productos en una prestatgeria utilizando el algoritmo de Hill Climbing.
     *
     * @param id_prestatgeria Identificador de la prestatgeria.
     * @throws PrestatgeriaNotFoundException Si la prestatgeria no existe.
     * @throws IllegalArgumentException      Si los argumentos son nulos o inválidos.
     */
    public void generarDistribucioHillClimbing(String id_prestatgeria)
            throws PrestatgeriaNotFoundException, IllegalArgumentException, IOException {
        if(id_prestatgeria == null || id_prestatgeria.isEmpty()) {
            throw new IllegalArgumentException("El nom de la Prestatgeria no pot estar buit.");
        }
        if(!ctrlPrestatgeria.existeixPrestatgeria(id_prestatgeria)){
            ctrlPrestatgeria.carregarPrestatgeria(database.getPrestatgeria(id_prestatgeria));
        }
        Vector<String> productes = ctrlPrestatgeria.getNomsProductes(id_prestatgeria);
        for(String producte:productes){
            if(!ctrlProducte.existeixProducte(producte)){
                ctrlProducte.carregarProducte(database.getProducte(producte));
            }
        }
        Set<String> fixats = ctrlPrestatgeria.getProductesFixats(id_prestatgeria);
        Vector<String> novadist = algorismes.encontrarMejorDistribucionHillClimbing(productes , fixats, ctrlProducte.getMagatzem());
        ctrlPrestatgeria.setDistribucio(id_prestatgeria, novadist);

    }

    /**
     * Añade una prestatgeria al sistema.
     *
     * @param idPrestatgeria Identificador de la prestatgeria.
     * @param midaPrestatge  Tamaño del prestatge.
     * @param midaPrestatgeria Tamaño total de la prestatgeria.
     * @throws MidaPrestatgeriaInvalidException Si los tamaños son inválidos.
     * @throws PrestatgeriaAlreadyExistsException  Si la prestatgeria ya existe.
     * @throws IllegalArgumentException         Si los argumentos son nulos o inválidos.
     */
    public void afegirPrestatgeria(String idPrestatgeria, int midaPrestatge, int midaPrestatgeria)
            throws MidaPrestatgeriaInvalidException, PrestatgeriaAlreadyExistsException, IllegalArgumentException, IOException {
        if(idPrestatgeria == null || idPrestatgeria.isEmpty()) {
            throw new IllegalArgumentException("El nom de la Prestatgeria no pot estar buit.");
        }

        ctrlPrestatgeria.afegirPrestatgeria(idPrestatgeria, midaPrestatgeria, midaPrestatge);
        database.addPrestatgeria(ctrlPrestatgeria.getPrestatgeria(idPrestatgeria));
    }

    /**
     * Elimina una prestatgeria y devuelve sus productos al almacén.
     *
     * @param idPrestatgeria Identificador de la prestatgeria.
     * @throws PrestatgeriaNotFoundException Si la prestatgeria no existe.
     * @throws IllegalArgumentException      Si los argumentos son nulos o inválidos.
     */
    public void eliminarPrestatgeria(String idPrestatgeria)
            throws PrestatgeriaNotFoundException, ProductNotFoundMagatzemException, MaxMagatzemWarning, QuanitatInvalidException, IllegalArgumentException, IOException {
        if(idPrestatgeria == null || idPrestatgeria.isEmpty()) {
            throw new IllegalArgumentException("El nom de la Prestatgeria no pot estar buit.");
        }
        if(!ctrlPrestatgeria.existeixPrestatgeria(idPrestatgeria)){
            ctrlPrestatgeria.carregarPrestatgeria(database.getPrestatgeria(idPrestatgeria));
        }
        Map<String,Integer> producteseliminats = ctrlPrestatgeria.eliminarPrestatgeria(idPrestatgeria);
        for(Map.Entry<String, Integer> entry : producteseliminats.entrySet()){
            if(!ctrlProducte.existeixProducte(entry.getKey())){
                ctrlProducte.carregarProducte(database.getProducte(entry.getKey()));
            }
            String nom = entry.getKey();
            int quantitat = entry.getValue();
            ctrlProducte.incrementarStock(nom, quantitat);
        }
        database.deletePrestatgeria(idPrestatgeria);
    }

    /**
     * Añade un prestatge a una prestatgeria existente.
     *
     * @param idPrestatgeria Identificador de la prestatgeria.
     * @throws PrestatgeriaNotFoundException Si la prestatgeria no existe.
     * @throws IllegalArgumentException      Si los argumentos son nulos o inválidos.
     */
    public void afegirPrestatge(String idPrestatgeria)
            throws PrestatgeriaNotFoundException, IllegalArgumentException, IOException {
        if(idPrestatgeria == null || idPrestatgeria.isEmpty()) {
            throw new IllegalArgumentException("El nom de la Prestatgeria no pot estar buit.");
        }
        if(!ctrlPrestatgeria.existeixPrestatgeria(idPrestatgeria)){
            ctrlPrestatgeria.carregarPrestatgeria(database.getPrestatgeria(idPrestatgeria));
        }
        ctrlPrestatgeria.afegirPrestatge(idPrestatgeria);
    }

    /**
     * Elimina un prestatge de una prestatgeria y devuelve sus productos al almacén.
     *
     * @param idPrestatgeria Identificador de la prestatgeria.
     * @throws PrestatgeriaNotFoundException Si la prestatgeria no existe.
     * @throws IllegalArgumentException      Si los argumentos son nulos o inválidos.
     */
    public void eliminarPrestatge(String idPrestatgeria)
            throws PrestatgeriaNotFoundException, QuanitatInvalidException, ProductNotFoundMagatzemException, IllegalArgumentException, MidaPrestatgeriaMinException, IOException {
        if(idPrestatgeria == null || idPrestatgeria.isEmpty()) {
            throw new IllegalArgumentException("El nom de la Prestatgeria no pot estar buit.");
        }
        if(!ctrlPrestatgeria.existeixPrestatgeria(idPrestatgeria)){
            ctrlPrestatgeria.carregarPrestatgeria(database.getPrestatgeria(idPrestatgeria));
        }
        Map<String,Integer> eliminats = ctrlPrestatgeria.eliminarPrestatge(idPrestatgeria);
        for(Map.Entry<String, Integer> entry : eliminats.entrySet()){
            String nom = entry.getKey();
            int quantitat = entry.getValue();
            if(!ctrlProducte.existeixProducte(nom)){
                ctrlProducte.carregarProducte(database.getProducte(nom));
            }
            ctrlProducte.incrementarStock(nom, quantitat);
        }
    }

    /**
     * Reabastece una prestatgeria con productos del almacén.
     *
     * @param id_prestatgeria Identificador de la prestatgeria.
     * @throws PrestatgeriaNotFoundException Si la prestatgeria no existe.
     * @throws IllegalArgumentException      Si los argumentos son nulos o inválidos.
     */
    public void reposarPrestatgeria(String id_prestatgeria)
            throws PrestatgeriaNotFoundException, ProductNotFoundMagatzemException, QuanitatInvalidException, IllegalArgumentException, IOException {
        if(id_prestatgeria == null || id_prestatgeria.isEmpty()) {
            throw new IllegalArgumentException("El nom de la Prestatgeria no pot estar buit.");
        }
        if(!ctrlPrestatgeria.existeixPrestatgeria(id_prestatgeria)){
            ctrlPrestatgeria.carregarPrestatgeria(database.getPrestatgeria(id_prestatgeria));
        }
        Map<String,Integer> productes = ctrlPrestatgeria.getProductesPrestatgeria(id_prestatgeria);
        for(Map.Entry<String, Integer> entry : productes.entrySet()){
            if(!ctrlProducte.existeixProducte(entry.getKey())){
                ctrlProducte.carregarProducte(database.getProducte(entry.getKey()));
            }
            int stock = ctrlProducte.getStockMagatzem(entry.getKey());
            int max = ctrlProducte.getMaxHueco(entry.getKey());
            int actual = entry.getValue();
            if(actual < max){
                int aafegir = max - actual;
                if(aafegir <= stock){
                    ctrlPrestatgeria.incrementarQuantitatProducte(id_prestatgeria, entry.getKey(), aafegir);
                    ctrlProducte.decrementarStock(entry.getKey(), aafegir);
                }
                else{
                    ctrlPrestatgeria.incrementarQuantitatProducte(id_prestatgeria, entry.getKey(), stock);
                    ctrlProducte.decrementarStock(entry.getKey(), stock);
                }
            }
        }
    }

    /**
     * Fija un producto en una prestatgeria.
     *
     * @param id_prestatgeria Identificador de la prestatgeria.
     * @param nomProducte     Nombre del producto.
     * @throws PrestatgeriaNotFoundException Si la prestatgeria no existe.
     * @throws ProductNotFoundPrestatgeriaException Si el producto no está en la prestatgeria.
     * @throws ProducteFixatException       Si el producto ya está fijado.
     * @throws IllegalArgumentException     Si los argumentos son nulos o inválidos.
     */
    public void fixarProducte(String id_prestatgeria, String nomProducte)
            throws PrestatgeriaNotFoundException, ProductNotFoundPrestatgeriaException, ProducteFixatException, IllegalArgumentException, IOException {
        if(id_prestatgeria == null || id_prestatgeria.isEmpty()) {
            throw new IllegalArgumentException("El nom de la Prestatgeria no pot estar buit.");
        }
        if(nomProducte == null || nomProducte.isEmpty()) {
            throw new IllegalArgumentException("El nom del Producte no pot estar buit.");
        }
        if(!ctrlPrestatgeria.existeixPrestatgeria(id_prestatgeria)){
            ctrlPrestatgeria.carregarPrestatgeria(database.getPrestatgeria(id_prestatgeria));
        }
        ctrlPrestatgeria.fixarProducte(id_prestatgeria, nomProducte);
    }

    /**
     * Desfija un producto en una prestatgeria.
     *
     * @param id_prestatgeria Identificador de la prestatgeria.
     * @param nomProducte     Nombre del producto.
     * @throws PrestatgeriaNotFoundException Si la prestatgeria no existe.
     * @throws ProductNotFoundPrestatgeriaException Si el producto no está en la prestatgeria.
     * @throws IllegalArgumentException     Si los argumentos son nulos o inválidos.
     */
    public void desfixarProducte(String id_prestatgeria, String nomProducte)
            throws PrestatgeriaNotFoundException, ProductNotFoundPrestatgeriaException, ProducteNoFixatException, IllegalArgumentException, IOException {
        if(id_prestatgeria == null || id_prestatgeria.isEmpty()) {
            throw new IllegalArgumentException("El nom de la Prestatgeria no pot estar buit.");
        }
        if(nomProducte == null || nomProducte.isEmpty()) {
            throw new IllegalArgumentException("El nom del Producte no pot estar buit.");
        }
        if(!ctrlPrestatgeria.existeixPrestatgeria(id_prestatgeria)){
            ctrlPrestatgeria.carregarPrestatgeria(database.getPrestatgeria(id_prestatgeria));
        }
        ctrlPrestatgeria.desfixarProducte(id_prestatgeria, nomProducte);
    }

    /**
     * Retira un producto de una prestatgeria y lo devuelve al almacén.
     *
     * @param id_prestatgeria Identificador de la prestatgeria.
     * @param nomProducte     Nombre del producto.
     * @throws PrestatgeriaNotFoundException     Si la prestatgeria no existe.
     * @throws ProductNotFoundMagatzemException  Si el producto no está en el almacén.
     * @throws ProductNotFoundPrestatgeriaException Si el producto no está en la prestatgeria.
     * @throws QuanitatInvalidException          Si la cantidad es inválida.
     * @throws IllegalArgumentException          Si los argumentos son nulos o inválidos.
     */
    public void retirarProducteAMagatzem(String id_prestatgeria,String nomProducte)
            throws PrestatgeriaNotFoundException, ProductNotFoundMagatzemException, ProductNotFoundPrestatgeriaException, QuanitatInvalidException, IllegalArgumentException, IOException {
        if(id_prestatgeria == null || id_prestatgeria.isEmpty()) {
            throw new IllegalArgumentException("El nom de la Prestatgeria no pot estar buit.");
        }
        if(nomProducte == null || nomProducte.isEmpty()) {
            throw new IllegalArgumentException("El nom del Producte no pot estar buit.");
        }
        if(!ctrlPrestatgeria.existeixPrestatgeria(id_prestatgeria)){
            ctrlPrestatgeria.carregarPrestatgeria(database.getPrestatgeria(id_prestatgeria));
        }
        Pair<String,Integer> producteretirat = ctrlPrestatgeria.retirarProductePrestatgeria(id_prestatgeria, nomProducte);
        if(producteretirat.getKey() != null) {
            String nom = producteretirat.getKey();
            if(!ctrlProducte.existeixProducte(nom)){
                ctrlProducte.carregarProducte(database.getProducte(nom));
            }
            int quantitat = producteretirat.getValue();
            ctrlProducte.incrementarStock(nom, quantitat);
        }
    }

    /**
     * Obtiene una prestatgeria por su identificador.
     *
     * @param id Identificador de la prestatgeria.
     * @return La prestatgeria correspondiente.
     * @throws PrestatgeriaNotFoundException Si la prestatgeria no existe.
     */
    public Prestatgeria getPrestatgeria(String id){
        return ctrlPrestatgeria.getPrestatgeria(id);
    }

    /**
     * Obtiene todas las prestatgeries registradas.
     *
     * @return Un mapa con todas las prestatgeries.
     */
    public Map<String, Prestatgeria> getPrestatgeries() throws IOException {
        Map<String, Prestatgeria> prestatgeries = database.getPrestatgeries();
        for(Prestatgeria prestatgeria : prestatgeries.values()){
            if(!ctrlPrestatgeria.existeixPrestatgeria(prestatgeria.getId())){
                ctrlPrestatgeria.carregarPrestatgeria(prestatgeria);
            }
        }
        return ctrlPrestatgeria.getPrestatgeries();
    }

    //Caixa

    /**
     * Añade un producto a la caja desde una prestatgeria.
     *
     * @param nom_producte    Nombre del producto a añadir.
     * @param quantitat       Cantidad de producto a añadir.
     * @param id_prestatgeria Identificador de la prestatgeria de donde se retirará el producto.
     * @return La cantidad que realmente se pudo añadir a la caja.
     * @throws QuanitatInvalidException            Si la cantidad es inválida.
     * @throws ProductNotFoundMagatzemException    Si el producto no existe en el almacén.
     * @throws ProductNotFoundPrestatgeriaException Si el producto no existe en la prestatgeria.
     * @throws PrestatgeriaNotFoundException       Si la prestatgeria no existe.
     * @throws IllegalArgumentException            Si alguno de los argumentos es nulo o vacío.
     */
    public int afegir_producte_caixa(String nom_producte, int quantitat, String id_prestatgeria)
            throws QuanitatInvalidException, ProductNotFoundMagatzemException, ProductNotFoundPrestatgeriaException, PrestatgeriaNotFoundException, IllegalArgumentException, IOException {
        if(nom_producte == null || nom_producte.isEmpty()) {
            throw new IllegalArgumentException("El nom del Producte no pot estar buit.");
        }
        if(id_prestatgeria == null || id_prestatgeria.isEmpty()) {
            throw new IllegalArgumentException("El nom de la Prestatgeria no pot estar buit.");
        }
        if(!ctrlProducte.existeixProducte(nom_producte)){
            ctrlProducte.carregarProducte(database.getProducte(nom_producte));
        }
        int quantitat_a_afegir = ctrlPrestatgeria.decrementarQuantitatProducte(id_prestatgeria, nom_producte,quantitat);
        caixa.afegirProducte(nom_producte, quantitat_a_afegir);
        return quantitat_a_afegir;
    }

    /**
     * Retira un producto de la caja y lo devuelve al stock del almacén.
     *
     * @param nom_producte    Nombre del producto a retirar.
     * @param quantitat       Cantidad del producto a retirar.
     * @throws QuanitatInvalidException    Si la cantidad es inválida.
     * @throws IllegalArgumentException    Si alguno de los argumentos es nulo o vacío.
     * @throws ProductNotFoundCaixaException Si el producto no existe en la caja.
     * @return La cantidad que realmente se pudo retirar de la caja.
     */
    public int retirar_producte_caixa(String nom_producte, int quantitat)
            throws QuanitatInvalidException, IllegalArgumentException, ProductNotFoundCaixaException, IOException {
        if(nom_producte == null || nom_producte.isEmpty()) {
            throw new IllegalArgumentException("El nom del Producte no pot estar buit.");
        }
        int aafegir = min(caixa.getQuantitat(nom_producte), quantitat);
        caixa.retirarProducte(nom_producte, aafegir);
        if(!ctrlProducte.existeixProducte(nom_producte)){
            ctrlProducte.carregarProducte(database.getProducte(nom_producte));
        }
        ctrlProducte.incrementarStock(nom_producte, aafegir);
        return aafegir;
    }

    /**
     * Realiza el pago de la caja, eliminando los productos del sistema
     */
    public void pagar_caixa() {
        Map<String, Integer> productes = caixa.getTicket();
        for(Map.Entry<String, Integer> entry : productes.entrySet()){
            ctrlProducte.incrementarStock(entry.getKey(), entry.getValue());
        }
        caixa.pagar();
    }

    /**
     * Obtiene el ticket de la caja, que contiene los productos y cantidades actuales.
     *
     * @return Un mapa con los productos en la caja y sus cantidades.
     */
    public Map<String,Integer> getTicket() {
        return caixa.getTicket();
    }

    //Magatzem (Productes)

    /**
     * Da de alta un nuevo producto en el sistema.
     *
     * @param nomProducte Nombre del producto.
     * @param maxHueco       Máxima cantidad permitida en la prestatgeria.
     * @param maxMagatzem       Máxima cantidad permitida en el almacén.
     * @param stockMagatzem       Cantidad inicial en el almacén.
     * @throws QuanitatInvalidException      Si las cantidades son inválidas.
     * @throws StockTooBigException          Si el stock inicial excede los límites.
     * @throws IllegalArgumentException      Si los argumentos son nulos o inválidos.
     * @throws ProducteAlreadyExistsException   Si el producto ya existe en el sistema.
     */
    public void altaProducte(String nomProducte, int maxHueco, int maxMagatzem, int stockMagatzem)
            throws QuanitatInvalidException, StockTooBigException, IllegalArgumentException, ProducteAlreadyExistsException, IOException {
        if (nomProducte == null || nomProducte.isEmpty()) {
            throw new IllegalArgumentException("El nom de la comanda no pot estar buit.");
        }
        if (maxHueco<=0) {
            throw new IllegalArgumentException("El maxim d'estock en prestatgeria ha de ser > 0");
        }
        if (maxMagatzem <=0) {
            throw new IllegalArgumentException("El maxim d'estock en magatzem ha de ser > 0");
        }
        Producte producte = new Producte(nomProducte,maxHueco,maxMagatzem,stockMagatzem);

        database.addProducte(producte);

    }

    /**
     * Elimina un producto del sistema.
     *
     * @param nomProducte Nombre del producto a eliminar.
     * @throws IllegalArgumentException         Si el argumento es nulo o vacío.
     * @throws ProductNotFoundMagatzemException Si el producto no se encuentra en el almacén.
     * @throws ProductNotFoundCaixaException Si el producto no se encuentra en la caixa.
     * @throws ProductNotFoundPrestatgeriaException Si el producto no se encuentra en la caixa.
     */
    public void eliminarProducte(String nomProducte) throws IllegalArgumentException, ProductNotFoundMagatzemException, ProductNotFoundCaixaException, ProductNotFoundPrestatgeriaException, IOException {
        if (nomProducte == null || nomProducte.isEmpty()) {
            throw new IllegalArgumentException("El nom de la comanda no pot estar buit.");
        }

        if(!database.existeixProducte(nomProducte)) throw new IllegalArgumentException("El Producte no existeix");
        Producte producte = database.getProducte(nomProducte);

        //Eliminamos el producto
        database.deleteProducte(nomProducte);

        //Cargamos las prestatgeries y Lo eliminamos de las prestatgerias y guardamos las prestatgeries
        Map<String, Prestatgeria> prestatgeries = database.getPrestatgeries();
        for (Prestatgeria prestatgeria : prestatgeries.values()) {
            ctrlPrestatgeria.carregarPrestatgeria(prestatgeria);
        }
        ctrlPrestatgeria.eliminarProducte(nomProducte);
        database.savePrestatgeries(ctrlPrestatgeria.getPrestatgeries().values());

        //Lo eliminamos de la caixa y guaradamos la caixa
        caixa = database.getCaixa();
        int qcaixa = caixa.getQuantitat(nomProducte);
        caixa.retirarProducte(nomProducte, qcaixa);
        database.saveCaixa(caixa);
    }

    /**
     * Añade una relación de similitud entre dos productos.
     *
     * @param nom1  Nombre del primer producto.
     * @param nom2  Nombre del segundo producto.
     * @param value Valor de la similitud (debe ser mayor a 0).
     * @throws IllegalArgumentException         Si los argumentos son nulos, vacíos o el valor es inválido.
     * @throws ProductNotFoundMagatzemException Si alguno de los productos no existe en el almacén.
     * @throws calculMateixosProductesSimilitud Si se intenta añadir una similitud con el mismo producto.
     */
    public void afegir_similitud(String nom1, String nom2, float value) throws IllegalArgumentException, ProductNotFoundMagatzemException, calculMateixosProductesSimilitud, IOException {
        if (nom1 == null || nom1.isEmpty()) {
            throw new IllegalArgumentException("El nom del primer producte no pot estar buit.");
        }
        if (nom2 == null || nom2.isEmpty()) {
            throw new IllegalArgumentException("El nom del segon producte no pot estar buit.");
        }
        if (value<=0) {
            throw new IllegalArgumentException("El valor de la similitud ha de ser > 0");
        }
        //Cargamos los dos productos

        Producte producte1 = database.getProducte(nom1);
        Producte producte2 = database.getProducte(nom2);
        ctrlProducte.carregarProducte(producte1);
        ctrlProducte.carregarProducte(producte2);

        //Añadimos la similitud
        ctrlProducte.afegirSimilitud(nom1, nom2, value);
    }

    /**
     * Elimina una relación de similitud entre dos productos.
     *
     * @param nom1 Nombre del primer producto.
     * @param nom2 Nombre del segundo producto.
     * @throws IllegalArgumentException         Si los argumentos son nulos o vacíos.
     * @throws ProductNotFoundMagatzemException Si alguno de los productos no existe en el almacén.
     * @throws calculMateixosProductesSimilitud Si se intenta eliminar una similitud con el mismo producto.
     */
    public void eliminarSimilitud(String nom1, String nom2) throws IllegalArgumentException, ProductNotFoundMagatzemException, calculMateixosProductesSimilitud, IOException {
        if (nom1 == null || nom1.isEmpty()) {
            throw new IllegalArgumentException("El nom del primer producte no pot estar buit.");
        }
        if (nom2 == null || nom2.isEmpty()) {
            throw new IllegalArgumentException("El nom del segon producte no pot estar buit.");
        }

        //Cargamos los dos productos

        Producte producte1 = database.getProducte(nom1);
        Producte producte2 = database.getProducte(nom2);
        ctrlProducte.carregarProducte(producte1);
        ctrlProducte.carregarProducte(producte2);

        //Eliminamos la similitud
        ctrlProducte.eliminarSimilitud(nom1, nom2);
    }

    /**
     * Obtiene un producto por su nombre.
     *
     * @param nomProducte Nombre del producto.
     * @return El producto correspondiente.
     * @throws IllegalArgumentException         Si el argumento es nulo o vacío.
     * @throws ProductNotFoundMagatzemException Si el producto no existe en el almacén.
     */
    public Producte get_producte(String nomProducte) throws IllegalArgumentException, ProductNotFoundMagatzemException {
        if (nomProducte == null || nomProducte.isEmpty()) {
            throw new IllegalArgumentException("El nom del producte no pot estar buit.");
        }
        return ctrlProducte.getProducte(nomProducte);
    }

    /**
     * Obtiene todos los productos disponibles en el almacén.
     *
     * @return Un mapa con los productos del almacén, donde las claves son los nombres de los productos
     * y los valores son los objetos {@code Producte}.
     */
    public Map<String, Producte> getMagatzem() {
        return ctrlProducte.getMagatzem();
    }




}
