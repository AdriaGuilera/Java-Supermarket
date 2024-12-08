package classes;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
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


    //Constructor de la clase Database
    public Database() {
        this.objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }


    //OPERACIONES PARA CARGAR ELEMENTOS DE LA BASE DE DATOS (GET)

    //Método que carga una comanda a partir de su id
    public Comanda getComanda(String id) throws ComandaNotFoundException, IOException {
        File file = new File(COMANDES_PATH + id + ".json");
        if (!file.exists()) {
            throw new ComandaNotFoundException(id);
        }

        return objectMapper.readValue(file, Comanda.class);
    }

    // Método para cargar todas las comandas
    public Map<String, Comanda> getComandes() throws IOException {
        Map<String, Comanda> comandes = new HashMap<>();
        File directory = new File(COMANDES_PATH);

        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles((dir, name) -> name.endsWith(".json"));

            if (files != null) {
                for (File file : files) {
                    Comanda comanda = objectMapper.readValue(file, Comanda.class);
                    comandes.put(comanda.getNom(), comanda);
                }
            }
        } else {
            throw new IOException("El directorio de comandas no existe o no es válido: " + COMANDES_PATH);
        }

        return comandes;
    }

    //Método que carga un producto a partir de su id
    public Producte getProducte(String id) throws ProductNotFoundMagatzemException, IOException {
        File file = new File(PRODUCTES_PATH + id + ".json");
        if (!file.exists()) {
            throw new ProductNotFoundMagatzemException(id);
        }
        return objectMapper.readValue(file, Producte.class);
    }

    public Map<String, Producte> getProductes() throws IOException {
        Map<String, Producte> productes = new HashMap<>();
        File directory = new File(PRODUCTES_PATH);

        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles((dir, name) -> name.endsWith(".json"));

            if (files != null) {
                for (File file : files) {
                    Producte producte = objectMapper.readValue(file, Producte.class);
                    productes.put(producte.getNom(), producte);
                }
            }
        } else {
            throw new IOException("El directorio de productos no existe o no es válido: " + PRODUCTES_PATH);
        }

        return productes;
    }

    //Método que carga una prestatgeria a partir de su id
    public Prestatgeria getPrestatgeria(String id) throws PrestatgeriaNotFoundException, IOException {
        File file = new File(PRESTATGERIES_PATH + id + ".json");
        if (!file.exists()) {
            throw new PrestatgeriaNotFoundException(id);
        }
        return objectMapper.readValue(file, Prestatgeria.class);
    }


    //Método que carga todas las prestatgerias
    public Map<String, Prestatgeria> getPrestatgeries() throws IOException {
        Map<String, Prestatgeria> prestatgeries = new HashMap<>();
        File directory = new File(PRESTATGERIES_PATH);

        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles((dir, name) -> name.endsWith(".json"));

            if (files != null) {
                for (File file : files) {
                    Prestatgeria prestatgeria = objectMapper.readValue(file, Prestatgeria.class);
                    prestatgeries.put(prestatgeria.getId(), prestatgeria);
                }
            }
        }

        return prestatgeries;
    }

    //Método que carga la caja
    public Caixa getCaixa() throws IOException {
        File file = new File(CAIXA_PATH);
        return objectMapper.readValue(file, Caixa.class);
    }


    //OPERACIONES PARA ACTUALIZAR ELEMENTOS DE LA BASE DE DATOS (SAVE)

    //Actualiza las comandas de la base de datos
    public void saveComandes(Collection<Comanda> comandes) throws IOException {
        for (Comanda comanda : comandes) {
            File file = new File(COMANDES_PATH + comanda.getNom() + ".json");
            objectMapper.writeValue(file, comanda);
        }

    }

    //Actualiza los productos de la base de datos
    public void saveProductes(Collection<Producte> productes) throws IOException {
        for (Producte producte : productes) {
            File file = new File(PRODUCTES_PATH + producte.getNom() + ".json");
            objectMapper.writeValue(file, producte);
        }
    }

    //Actualiza las prestatgerias de la base de datos
    public void savePrestatgeries(Collection<Prestatgeria> prestatgeries) throws IOException {
        for (Prestatgeria prestatgeria : prestatgeries) {
            File file = new File(PRESTATGERIES_PATH + prestatgeria.getId() + ".json");
            objectMapper.writeValue(file, prestatgeria);
        }
    }

    //Actualiza la caja de la base de datos
    public void saveCaixa(Caixa caixa) throws IOException {
        objectMapper.writeValue(new File(CAIXA_PATH), caixa);
    }

    //Actualiza todos los elementos de la base de datos
    public void saveAll(Collection<Comanda> comandes, Collection<Producte> productes, Collection<Prestatgeria> prestatgeries, Caixa caixa) throws IOException {
        saveComandes(comandes);
        saveProductes(productes);
        savePrestatgeries(prestatgeries);
        saveCaixa(caixa);
    }



    //OPERACIONES PARA ELIMINAR ELEMENTOS DE LA BASE DE DATOS

    //Elimina una comanda de la base de datos
    public void deleteComanda(String id) throws ComandaNotFoundException {
        if(existeixComanda(id)) {
            File file = new File(COMANDES_PATH + id + ".json");
            file.delete();
        }
        else {
            throw new ComandaNotFoundException(id);
        }
    }

    //Elimina un producto de la base de datos
    public void deleteProducte(String id) throws ProductNotFoundMagatzemException {
        if(existeixProducte(id)) {
            File file = new File(PRODUCTES_PATH + id + ".json");
            file.delete();
        }
        else {
            throw new ProductNotFoundMagatzemException(id);
        }
    }

    //Elimina una prestatgeria de la base de datos
    public void deletePrestatgeria(String id) throws PrestatgeriaNotFoundException {
        if(existeixPrestatgeria(id)) {
            File file = new File(PRESTATGERIES_PATH + id + ".json");
            file.delete();
        }
        else {
            throw new PrestatgeriaNotFoundException(id);
        }
    }



    //OPERACIONES PARA AÑADIR ELEMENTOS A LA BASE DE DATOS

    //Añade una comanda a la base de datos
    public void addComanda(Comanda comanda) throws IOException, ComandaAlreadyExistsException {
        if(existeixComanda(comanda.getNom())) {
            throw new ComandaAlreadyExistsException(comanda.getNom());
        }
        File file = new File(COMANDES_PATH + comanda.getNom() + ".json");
        objectMapper.writeValue(file, comanda);
    }

    //Añade un producto a la base de datos
    public void addProducte(Producte producte) throws IOException, ProducteAlreadyExistsException {
        if(existeixProducte(producte.getNom())) {
            throw new ProducteAlreadyExistsException(producte.getNom());
        }
        File file = new File(PRODUCTES_PATH + producte.getNom() + ".json");
        objectMapper.writeValue(file, producte);
    }

    //Añade una prestatgeria a la base de datos
    public void addPrestatgeria(Prestatgeria prestatgeria) throws IOException, PrestatgeriaAlreadyExistsException {
        if(existeixPrestatgeria(prestatgeria.getId())) {
            throw new PrestatgeriaAlreadyExistsException(prestatgeria.getId());
        }
        File file = new File(PRESTATGERIES_PATH + prestatgeria.getId() + ".json");
        objectMapper.writeValue(file, prestatgeria);
    }


    //OPERACIONES PARA COMPROBAR LA EXISTENCIA DE ELEMENTOS EN LA BASE DE DATOS

    //Comprueba si una comanda existe
    public boolean existeixComanda(String id) {
        File file = new File(COMANDES_PATH + id + ".json");
        return file.exists();
    }

    //Comprueba si un producto existe
    public boolean existeixProducte(String id){
        File file = new File(PRODUCTES_PATH + id + ".json");
        return file.exists();
    }

    //Comprueba si una prestatgeria existe
    public boolean existeixPrestatgeria(String id) {
        File file = new File(PRESTATGERIES_PATH + id + ".json");
        return file.exists();
    }


}
