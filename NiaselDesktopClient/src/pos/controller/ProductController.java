/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pos.controller;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import pos.model.Product;
import pos.model.User;

/**
 *
 * @author husainazkas
 */
public class ProductController extends BaseController {

    private List<Product> products = new ArrayList();

    /**
     *
     * @param tableModel must be provided from
     * {@code javax.swing.JTable.getModel()}
     */
    public void loadProducts(TableModel tableModel) {
        try (final EntityManager em = emf.createEntityManager()) {
            TypedQuery<Product> query = em.createQuery("SELECT p FROM Product p WHERE p.isDeleted = false", Product.class);
            products = query.getResultList();
        } catch (Exception ex) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, ex.getMessage());
        }

        DefaultTableModel table = (DefaultTableModel) tableModel;
        table.setRowCount(0);
        for (int i = 0; i < products.size(); i++) {
            Product p = products.get(i);
            Object[] row = {
                Long.valueOf(String.valueOf(i + 1)),
                p.getId(),
                p.getBarcodeId(),
                p.getName(),
                p.getPrice(),
                p.getStock(),
                p.getBrand()
            };
            table.addRow(row);
        }
    }

    /**
     *
     * @param model must be provided from {@code javax.swing.JTable.getModel()}
     * @param index must be provided from
     * {@code javax.swing.JTable.getSelectedRow()}
     * @return the {@code product}, if present, otherwise {@code null}
     */
    public Product getSelectedProduct(TableModel model, int index) {
        if (index == -1) {
            return null;
        }

        Long id = (Long) model.getValueAt(index, 1);
        return products.stream()
                .filter(e -> Objects.equals(e.getId(), id))
                .findFirst()
                .orElse(null);
    }

    public void filterBySearch(String text, TableModel tableModel) {
        DefaultTableModel table = (DefaultTableModel) tableModel;
        table.setRowCount(0);
        int index = 0;
        for (int i = index; i < products.size(); i++) {
            Product p = products.get(i);
            String productId = String.valueOf(p.getId());
            String productName = p.getName().toLowerCase();
            String productBrand = p.getBrand().toLowerCase();
            text = text.toLowerCase();

            boolean isMatch = productId.contains(text) || p.getBarcodeId().contains(text)
                    || productName.contains(text) || productBrand.contains(text);
            if (isMatch || text.isEmpty()) {
                Object[] row = {Long.valueOf(String.valueOf(index + 1)), p.getId(), p.getBarcodeId(), p.getName(), p.getPrice(), p.getStock(), p.getBrand()};
                table.addRow(row);

                index++;
            }
        }
    }

    public void save(User user, TableModel model, int index, String barcodeId, String name, String price, String stock, String brand) {
        try (final EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            Product product = getSelectedProduct(model, index);
            if (product != null) {
                product.setBarcodeId(barcodeId);
                product.setName(name);
                product.setPrice(Long.valueOf(price));
                product.setStock(Integer.valueOf(stock));
                product.setBrand(brand);
                product.setUpdatedBy(user);
                em.merge(product);
            } else {
                product = new Product();
                product.setBarcodeId(barcodeId);
                product.setName(name);
                product.setPrice(Long.valueOf(price));
                product.setStock(Integer.valueOf(stock));
                product.setBrand(brand);
                product.setUpdatedBy(user);
                product.setCreatedBy(user);
                em.persist(product);
            }

            em.getTransaction().commit();
        }
    }

    public void remove(User user, TableModel model, int index) {
        Product product = getSelectedProduct(model, index);
        if (product == null) {
            return;
        }
        try (final EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            product.setIsDeleted(true);
            product.setUpdatedBy(user);
            em.merge(product);

            em.getTransaction().commit();
        }
    }
}
