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
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.codec.digest.DigestUtils;
import static pos.controller.BaseController.emf;
import pos.model.Role;
import pos.model.User;

/**
 *
 * @author husainazkas
 */
public class ManageUsersController extends BaseController {

    private List<User> users = new ArrayList();
    private List<Role> roles = new ArrayList();
    private Optional<User> selectedUser = Optional.empty();

    private final DateFormat dateFormat = new SimpleDateFormat();

    /**
     *
     * @param tableModel must be provided from
     * {@code javax.swing.JTable.getModel()}
     */
    public void loadUsers(TableModel tableModel) {
        try (final EntityManager em = emf.createEntityManager()) {
            TypedQuery<User> queryUser = em.createQuery("SELECT u FROM User u WHERE u.isDeleted = false", User.class);
            TypedQuery<Role> queryRole = em.createQuery("SELECT r FROM Role r WHERE r.isDeleted = false", Role.class);

            users = queryUser.getResultList();
            roles = queryRole.getResultList();
        } catch (Exception ex) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, ex.getMessage());
        }

        DefaultTableModel table = (DefaultTableModel) tableModel;
        table.setRowCount(0);
        for (int i = 0; i < users.size(); i++) {
            User u = users.get(i);
            Role role = roles.stream().filter(e -> {
                return Objects.equals(e.getId(), u.getRoleId());
            }).findFirst().orElse(null);

            Object[] row = {
                Long.valueOf(String.valueOf(i + 1)),
                u.getFirstName() + " " + u.getLastName(),
                role != null ? role.getName() : "-",
                u.getIsActive() ? "Active" : "Inactive",
                dateFormat.format(u.getUpdatedAt()),
                dateFormat.format(u.getCreatedAt()),
                u.getId() // This is hidden by view
            };
            table.addRow(row);
        }
    }

    /**
     *
     * @param id a numeric user id to get full data of user
     */
    public void selectUser(Long id) {
        if (id != null) {
            selectedUser = users.stream().filter(e -> {
                return Objects.equals(e.getId(), id);
            }).findFirst();
        } else {
            selectedUser = Optional.empty();
        }
    }

    /**
     *
     * @return a user that has been selected before using {@link selectUser}
     */
    public User getUser() {
        return selectedUser.orElse(null);
    }
}
