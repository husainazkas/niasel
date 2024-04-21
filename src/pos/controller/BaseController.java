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

    public BaseController() {
        if (emf == null) {
            emf = Persistence.createEntityManagerFactory("PointOfSalesPU");
        }
    }
}
