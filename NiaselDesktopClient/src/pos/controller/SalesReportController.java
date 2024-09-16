/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pos.controller;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import pos.model.SalesOrder;
import pos.model.SalesOrderItem;

/**
 *
 * @author husainazkas
 */
public class SalesReportController extends BaseController {

    private final SimpleDateFormat sdf = new SimpleDateFormat();
    private final List<SalesOrder> orders = new ArrayList();
    private List<SalesOrderItem> orderItems = new ArrayList();

    public void loadSales(TableModel tableModel) {
        try (final EntityManager em = emf.createEntityManager()) {
            TypedQuery<SalesOrderItem> query = em.createQuery("FROM SalesOrderItem", SalesOrderItem.class);
            orderItems = query.getResultList();
            orders.clear();
            for (SalesOrderItem orderItem : orderItems) {
                SalesOrder order = orderItem.getOrder();
                if (!orders.contains(order)) {
                    orders.add(order);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(SalesReportController.class.getName()).log(Level.SEVERE, ex.getMessage());
        }

        DefaultTableModel table = (DefaultTableModel) tableModel;
        table.setRowCount(0);
        for (int i = 0; i < orders.size(); i++) {
            SalesOrder order = orders.get(i);

            int count = 0;
            for (SalesOrderItem item : orderItems) {
                if (Objects.equals(item.getOrder().getId(), order.getId())) {
                    count += item.getCount();
                }
            }

            Object[] row = {
                Long.valueOf(String.valueOf(i + 1)),
                order.getUuid(),
                count,
                order.getTotalPrice(),
                order.getCash(),
                order.getCashChange(),
                order.getCreatedBy().getFullName(),
                sdf.format(order.getCreatedAt())
            };
            table.addRow(row);
        }

    }
}
