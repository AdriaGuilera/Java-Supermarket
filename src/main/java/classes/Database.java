package classes;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.util.*;
import Exepcions.*;

public class Database {
    private static final String RESOURCES_PATH = "resources/";
    private static final String COMANDES_PATH = RESOURCES_PATH + "comandes/";
    private static final String PRODUCTES_PATH = RESOURCES_PATH + "productes/";
    private static final String PRESTATGERIES_PATH = RESOURCES_PATH + "prestatgeries/";
    private static final String CAIXA_PATH = RESOURCES_PATH + "caixa/caixa.json";


    private final ObjectMapper objectMapper;


    public void throwExcepcionNotFound(Class<?> clazz, String id)
    throws ComandaNotFoundException, ProductNotFoundMagatzemException, PrestatgeriaNotFoundException {
       if(clazz.equals(Comanda.class)) {
           throw new ComandaNotFoundException(id);
       }
       if(clazz.equals(Producte.class)) {
              throw new ProductNotFoundMagatzemException(id);
       }
         if(clazz.equals(Prestatgeria.class)) {
              throw new PrestatgeriaNotFoundException(id);
         }
    }

    //Constructor de la clase Database
    public Database() {
        this.objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    public <T> T getEntity(Class<T> clazz, String id) throws IOException {
        String path = getPathForClass(clazz);
        File file = new File(path + id + ".json");
        if (!file.exists()) {
            throwExcepcionNotFound(clazz, id);
        }
        return objectMapper.readValue(file, clazz);
    }

    public <T> List<T> getEntities(Class<T> clazz) throws IOException {
        List<T> entities = new ArrayList<>();
        String path = getPathForClass(clazz);
        File directory = new File(path);

        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles((dir, name) -> name.endsWith(".json"));

            if (files != null) {
                for (File file : files) {
                    T entity = objectMapper.readValue(file, clazz);
                    entities.add(entity);
                }
            }
        } else {
            throw new IOException("The directory does not exist or is not valid: " + path);
        }

        return entities;
    }


    public <T> boolean existeix(Class<T> clazz, String id) {
        String path = getPathForClass(clazz);
        File file = new File(path + id + ".json");
        return file.exists();
    }

    // Generic method to save entities to the database
    public <T> void saveEntities(Collection<T> entities, Collection<String> ids) throws IOException {
        if (entities.isEmpty()) {
            throw new IllegalArgumentException("Entities collection is empty.");
        }
        String path = getPathForClass(entities.iterator().next().getClass());
        Iterator<String> idIterator = ids.iterator();
        for (T entity : entities) {
            if (!idIterator.hasNext()) {
                throw new IllegalArgumentException("The number of IDs does not match the number of entities.");
            }
            String id = idIterator.next();
            File file = new File(path + id + ".json");
            objectMapper.writeValue(file, entity);
        }
    }
    public <T> void saveEntity(T entity, String id) throws IOException {
        String path = getPathForClass(entity.getClass());
        File file = new File(path + id + ".json");
        objectMapper.writeValue(file, entity);
    }

    public void saveCaixa(Caixa caixa) throws IOException {
        File file = new File(CAIXA_PATH);
        objectMapper.writeValue(file, caixa);
    }
    public Caixa getCaixa() throws IOException {
        File file = new File(CAIXA_PATH);
        if (!file.exists()) {
            return new Caixa();
        }
        return objectMapper.readValue(file, Caixa.class);
    }

    //genreic method to delete an entity
    public <T> void deleteEntity(Class<T> clazz, String id) throws IOException {
        String path = getPathForClass(clazz);
        File file = new File(path + id + ".json");
        if (!file.exists()) {
            throw new IOException(clazz.getSimpleName() + " not found: " + id);
        }
        file.delete();
    }

    private String getPathForClass(Class<?> clazz) {
        if (clazz.equals(Comanda.class)) {
            return COMANDES_PATH;
        } else if (clazz.equals(Producte.class)) {
            return PRODUCTES_PATH;
        } else if (clazz.equals(Prestatgeria.class)) {
            return PRESTATGERIES_PATH;
        } else if (clazz.equals(Caixa.class)) {
            return CAIXA_PATH;
        } else {
            throw new IllegalArgumentException("Unknown class: " + clazz.getSimpleName());
        }
    }





}
