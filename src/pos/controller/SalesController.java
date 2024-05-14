/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pos.controller;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import pos.model.Product;
import pos.model.SalesOrder;
import pos.model.SalesOrderItem;
import pos.model.User;

/**
 *
 * @author husainazkas
 */
public class SalesController extends BaseController {

    private final HashMap<Long, Integer> selectedProduct = new HashMap();

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
            Logger.getLogger(SalesController.class.getName()).log(Level.SEVERE, ex.getMessage());
        }

        DefaultTableModel table = (DefaultTableModel) tableModel;
        table.setRowCount(0);
        for (int i = 0; i < products.size(); i++) {
            Product p = products.get(i);
            Object[] row = {
                Long.valueOf(String.valueOf(i + 1)),
                p.getBarcodeId(),
                p.getName(),
                p.getPrice(),
                p.getStock(),
                p.getBrand()
            };
            table.addRow(row);
        }
    }

    public void addProductToCart(Long id, int count, boolean replace) {
        if (id != null) {
            Integer currentCount = selectedProduct.get(id);
            if (!replace && currentCount != null) {
                count += currentCount;
            }

            selectedProduct.put(id, count);
        }
    }

    public void removeProductFromCart(Long id) {
        if (id != null) {
            selectedProduct.remove(id);
        }
    }

    public void loadCart(TableModel tableModel) {
        DefaultTableModel table = (DefaultTableModel) tableModel;
        table.setRowCount(0);

        Entry<Long, Integer> selected[] = selectedProduct.entrySet().toArray(Entry[]::new);
        for (int i = 0; i < selected.length; i++) {
            Entry<Long, Integer> entry = selected[i];
            Product product = products.stream()
                    .filter(e -> Objects.equals(e.getId(), entry.getKey()))
                    .findFirst()
                    .orElseThrow();

            String name = product.getName();
            Integer count = entry.getValue();
            Long totalPrice = count * product.getPrice();
            Object[] row = {
                Long.valueOf(String.valueOf(i + 1)),
                name,
                count,
                totalPrice
            };
            table.addRow(row);
        }
    }

    public void clearCart(TableModel tableModel) {
        DefaultTableModel table = (DefaultTableModel) tableModel;
        table.setRowCount(0);

        selectedProduct.clear();
    }

    /**
     *
     * @param model must be provided from {@code javax.swing.JTable.getModel()}
     * @param index must be provided from
     * {@code javax.swing.JTable.getSelectedRow()}
     * @return the {@code product}, if present, otherwise {@code null}
     */
    public Product getProduct(TableModel model, int index) {
        if (index == -1) {
            return null;
        }

        String barcodeId = (String) model.getValueAt(index, 1);
        return products.stream()
                .filter(e -> e.getBarcodeId().equalsIgnoreCase(barcodeId))
                .findFirst()
                .orElse(null);
    }

    /**
     *
     * @param index must be provided from
     * {@code javax.swing.JTable.getSelectedRow()}
     * @return the {@code product}, if present, otherwise {@code null}
     */
    public Product getSelectedProduct(int index) {
        if (index == -1) {
            return null;
        }

        Entry<Long, Integer> selected[] = selectedProduct.entrySet().toArray(Entry[]::new);
        for (int i = 0; i < selected.length; i++) {
            if (i == index) {
                Entry<Long, Integer> entry = selected[i];
                Product result = products.stream()
                        .filter(e -> Objects.equals(e.getId(), entry.getKey()))
                        .findFirst()
                        .orElse(null);
                if (result != null) {
                    return result;
                }
            }
        }
        return null;
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
                Object[] row = {
                    Long.valueOf(String.valueOf(index + 1)),
                    p.getBarcodeId(),
                    p.getName(),
                    p.getPrice(),
                    p.getStock(),
                    p.getBrand()
                };
                table.addRow(row);

                index++;
            }
        }
    }

    public void createTransaction(User user, long totalPrice, long cash, long cashChange) throws Exception {
        SalesOrder order = new SalesOrder();
        order.setUuid(UUID.randomUUID().toString());
        order.setTotalPrice(totalPrice);
        order.setCash(cash);
        order.setCashChange(cashChange);
        order.setCreatedBy(user);

        try (final EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            
            em.persist(order);

            for (Entry<Long, Integer> entry : selectedProduct.entrySet()) {
                Product product = products.stream()
                        .filter(e -> Objects.equals(e.getId(), entry.getKey()))
                        .findFirst()
                        .orElse(null);
                if (product != null) {
                    SalesOrderItem orderItem = new SalesOrderItem();
                    orderItem.setOrder(order);
                    orderItem.setProduct(product);
                    orderItem.setCount(entry.getValue());
                    orderItem.setPrice(product.getPrice() * entry.getValue());

                    em.persist(orderItem);
                }
            }

            em.getTransaction().commit();
        }
    }
}
