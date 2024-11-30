package classes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import Exepcions.*;

public class Database {
    private static final String RESOURCES_PATH = "src/main/resources/";
    private static final String COMANDES_PATH = RESOURCES_PATH + "comandes/";
    private static final String PRODUCTES_PATH = RESOURCES_PATH + "productes/";
    private static final String PRESTATGERIES_PATH = RESOURCES_PATH + "prestatgeries/";
    private static final String CAIXA_PATH = RESOURCES_PATH + "caixa/caixa.json";


    private final ObjectMapper objectMapper;

    //Guarda los nombres de los archivos cargados para poder borrar los que no se se hayan eliminado
    private final Set<String> loadedComandes;
    private final Set<String> loadedProductes;
    private final Set<String> loadedPrestatgeries;

    //Constructor de la clase Database
    public Database() {
        this.objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        this.loadedComandes = new HashSet<>();
        this.loadedProductes = new HashSet<>();
        this.loadedPrestatgeries = new HashSet<>();
    }

    //Método que carga una comanda a partir de su id
    public Comanda getComanda(String id) throws ComandaNotFoundException, IOException {
        File file = new File(COMANDES_PATH + id + ".json");
        if (!file.exists()) {
            throw new ComandaNotFoundException(id);
        }
        loadedComandes.add(id);
        return objectMapper.readValue(file, Comanda.class);
    }


    //Método que carga un producto a partir de su id
    public Producte getProducte(String id) throws ProductNotFoundMagatzemException, IOException {
        File file = new File(PRODUCTES_PATH + id + ".json");
        if (!file.exists()) {
            throw new ProductNotFoundMagatzemException(id);
        }
        loadedProductes.add(id);
        return objectMapper.readValue(file, Producte.class);
    }

    //Método que carga una prestatgeria a partir de su id
    public Prestatgeria getPrestatgeria(String id) throws PrestatgeriaNotFoundException, IOException {
        File file = new File(PRESTATGERIES_PATH + id + ".json");
        if (!file.exists()) {
            throw new PrestatgeriaNotFoundException(id);
        }
        loadedPrestatgeries.add(id);
        return objectMapper.readValue(file, Prestatgeria.class);
    }


    //Método que carga la caja
    public Caixa getCaixa() throws IOException {
        File file = new File(CAIXA_PATH);
        return objectMapper.readValue(file, Caixa.class);
    }

    //Guarda las comandas en un archivo json, si se eliminan comandas, se eliminan los archivos correspondientes y
    //si se modifican, se sobreescriben los archivos correspondientes
    public void saveComandes(Collection<Comanda> comandes) throws IOException {

        // Get current comanda IDs
        Set<String> currentIds = new HashSet<>();
        for (Comanda comanda : comandes) {
            currentIds.add(comanda.getNom());
        }

        // Find deleted comandes
        Set<String> deletedIds = new HashSet<>(loadedComandes);
        deletedIds.removeAll(currentIds);

        // Delete files for removed comandes
        for (String deletedId : deletedIds) {
            Files.deleteIfExists(Paths.get(COMANDES_PATH + deletedId + ".json"));
        }

        // Save current comandes
        for (Comanda comanda : comandes) {
            File file = new File(COMANDES_PATH + comanda.getNom() + ".json");
            objectMapper.writeValue(file, comanda);
        }

        loadedComandes.clear();
    }
    //Guarda los productos en un archivo json, si se eliminan productos, se eliminan los archivos correspondientes y
    //si se modifican, se sobreescriben los archivos correspondientes
    public void saveProductes(Collection<Producte> productes) throws IOException {
        // Get current producte IDs
        Set<String> currentIds = new HashSet<>();
        for (Producte producte : productes) {
            currentIds.add(producte.getNom());
        }

        // Find deleted productes
        Set<String> deletedIds = new HashSet<>(loadedProductes);
        deletedIds.removeAll(currentIds);

        // Delete files for removed productes
        for (String deletedId : deletedIds) {
            Files.deleteIfExists(Paths.get(PRODUCTES_PATH + deletedId + ".json"));
        }

        // Save current productes
        for (Producte producte : productes) {
            File file = new File(PRODUCTES_PATH + producte.getNom() + ".json");
            objectMapper.writeValue(file, producte);
        }

        loadedProductes.clear();
    }

    //Guarda las prestatgeries en un archivo json, si se eliminan prestatgeries, se eliminan los archivos correspondientes y
    //si se modifican, se sobreescriben los archivos correspondientes
    public void savePrestatgeries(Collection<Prestatgeria> prestatgeries) throws IOException {
        // Get current prestatgeria IDs
        Set<String> currentIds = new HashSet<>();
        for (Prestatgeria prestatgeria : prestatgeries) {
            currentIds.add(prestatgeria.getId());
        }

        // Find deleted prestatgeries
        Set<String> deletedIds = new HashSet<>(loadedPrestatgeries);
        deletedIds.removeAll(currentIds);

        // Delete files for removed prestatgeries
        for (String deletedId : deletedIds) {
            Files.deleteIfExists(Paths.get(PRESTATGERIES_PATH + deletedId + ".json"));
        }

        // Save current prestatgeries
        for (Prestatgeria prestatgeria : prestatgeries) {
            File file = new File(PRESTATGERIES_PATH + prestatgeria.getId() + ".json");
            objectMapper.writeValue(file, prestatgeria);
        }

        loadedPrestatgeries.clear();
    }

    public void saveCaixa(Caixa caixa) throws IOException {
        objectMapper.writeValue(new File(CAIXA_PATH), caixa);
    }

    public void saveAll(Collection<Comanda> comandes, Collection<Producte> productes, Collection<Prestatgeria> prestatgeries, Caixa caixa) throws IOException {
        saveComandes(comandes);
        saveProductes(productes);
        savePrestatgeries(prestatgeries);
        saveCaixa(caixa);
    }
}
