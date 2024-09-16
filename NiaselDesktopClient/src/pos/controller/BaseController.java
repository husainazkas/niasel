/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pos.controller;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceUnit;

/**
 *
 * @author husainazkas
 */
public abstract class BaseController {

    @PersistenceUnit
    protected static EntityManagerFactory emf;

    /**
     * A method to connect to database. This will close previous connection if
     * exists and active.
     *
     * @throws IllegalStateException if the entity manager factory failed to
     * connect
     */
    public static void connect() {
        disconnect();
        emf = Persistence.createEntityManagerFactory("PointOfSalesPU");
    }

    public static void disconnect() {
        if (emf != null && emf.isOpen()) {
            emf.close();
            emf = null;
        }
    }
}
