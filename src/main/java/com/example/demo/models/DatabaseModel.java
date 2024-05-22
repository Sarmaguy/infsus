package com.example.demo.models;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Abstract class that represents a model in the database and provides default implementations for CRUD operations.
 * All models that extend this class must implement getId method.
 *
 * @author Jura
 * @version 1.0
 */
public abstract class DatabaseModel {
    String id;

    public DatabaseModel(String id) {
        this.id = id;
    }
    public DatabaseModel() {
        this("");
    }

    /**
     * Method that returns the id of the model
     * @return Id of the model
     */

    public  String getId(){
        return id;
    }

    /**
     * Default implementation of getAll method for all models that extend DatabaseModel
     * @param db Firestore instance
     * @param t Class of the model
     * @return Collection of models from the database
     * @param <T> Type of the model that extends DatabaseModel
     */
    public static  <T extends DatabaseModel> List<T> getAll(Firestore db, Class<T> t) {
        String collectionName = t.getSimpleName();
        CollectionReference ref = db.collection(collectionName);
        List<T> list;
        try {
            list =  ref.get().get().toObjects(t);
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        return list;
    }

    /**
     * Default implementation of get method for all models that extend DatabaseModel
     * @param db Firestore instance
     * @param t Class of the model
     * @param id Id of the model
     * @return Model from the database
     * @param <T> Type of the model that extends DatabaseModel
     */
    public static  <T extends DatabaseModel> T get(Firestore db, Class<T> t, String id) {
        String collectionName = t.getSimpleName();
        String path = collectionName + "/" + id;
        T model;

        try {
            model = db. document(path).get().get().toObject(t);
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

        return model;

    }

    /**
     * Default implementation of update method for all models that extend DatabaseModel
     * @param db Firestore instance
     * @param model Model to be updated
     * @param <T> Type of the model that extends DatabaseModel
     */
    public static <T extends DatabaseModel> ApiFuture<WriteResult> update(Firestore db, T model) {
        String collectionName = model.getClass().getSimpleName();
        String path = collectionName + "/" + model.getId();

        return db.document(path).set(model);
    }

    /**
     * Default implementation of create method for all models that extend DatabaseModel
     * @param db Firestore instance
     * @param model Model to be created
     * @return ApiFuture of the operation
     * @param <T> Type of the model that extends DatabaseModel
     */
    public static <T extends DatabaseModel> ApiFuture<WriteResult> create(Firestore db, T model) {
        String collectionName = model.getClass().getSimpleName();

        String id = db.collection(collectionName).document().getId();
        model.setId(id);

        String path = collectionName + "/" + id;

        return db.document(path).set(model);
    }

    /**
     * Default implementation of delete method for all models that extend DatabaseModel
     * @param db Firestore instance
     * @param id Id of the model to be deleted
     * @param t Class of the model
     * @return ApiFuture of the operation
     * @param <T> Type of the model that extends DatabaseModel
     */
    public static <T extends DatabaseModel> ApiFuture<WriteResult> delete(Firestore db, String id, Class<T> t) {
        String collectionName = t.getSimpleName();
        String path = collectionName + "/" + id;

        return db.document(path).delete();
    }

    void setId(String id) {
        this.id = id;
    }


}