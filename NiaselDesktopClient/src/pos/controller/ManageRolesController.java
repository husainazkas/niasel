/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pos.controller;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import static pos.controller.BaseController.emf;
import pos.model.Role;
import pos.model.User;

/**
 *
 * @author husainazkas
 */
public class ManageRolesController extends BaseController {

    private List<Role> roles = new ArrayList();

    private boolean markNeedsRebuild = false;
    private final DateFormat dateFormat = new SimpleDateFormat();

    public void loadRoles(TableModel tableModel) {
        try (final EntityManager em = emf.createEntityManager()) {
            TypedQuery<Role> queryRole = em.createQuery("SELECT r FROM Role r WHERE r.isDeleted = false", Role.class);
            roles = queryRole.getResultList();
        } catch (Exception ex) {
            Logger.getLogger(ManageRolesController.class.getName()).log(Level.SEVERE, ex.getMessage());
        }

        DefaultTableModel table = (DefaultTableModel) tableModel;
        table.setRowCount(0);
        for (int i = 0; i < roles.size(); i++) {
            Role role = roles.get(i);

            Object[] row = {
                Long.valueOf(String.valueOf(i + 1)),
                role.getName(),
                role.getIsCanUpdateProduct(),
                role.getIsCanReadUsers(),
                role.getIsCanCreateUpdateUser(),
                role.getIsCanDeleteUser(),
                role.getIsCanCreatePurchase(),
                dateFormat.format(role.getUpdatedAt())
            };
            table.addRow(row);
        }
    }

    public void addRole(String name, User user) {
        try (final EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.createNativeQuery("INSERT INTO master_role (name, updated_by, created_by) VALUES (:name, :userId, :userId)")
                    .setParameter("name", name)
                    .setParameter("userId", user.getId())
                    .executeUpdate();
            em.getTransaction().commit();
        }
        markNeedsRebuild = true;
    }

    public void removeRole(int index, User user) {
        Role role = roles.get(index);
        try (final EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.createQuery("UPDATE Role r SET r.isDeleted = true, r.updatedBy = :userId WHERE r.id = :id")
                    .setParameter("id", role.getId())
                    .setParameter("userId", user.getId())
                    .executeUpdate();
            em.getTransaction().commit();
        }
        markNeedsRebuild = true;
    }
    
    public boolean isShouldRebuild() {
        return markNeedsRebuild;
    }
}
