/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pos.controller;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
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

    private boolean markNeedsRebuild = true;
    private final DateFormat dateFormat = new SimpleDateFormat();

    /**
     *
     * @param tableModel must be provided from
     * {@code javax.swing.JTable.getModel()}
     */
    public void loadUsers(TableModel tableModel) {
        if (!markNeedsRebuild) {
            return;
        }

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
            Role role = roles.stream()
                    .filter(e -> Objects.equals(e.getId(), u.getRoleId()))
                    .findFirst()
                    .orElse(null);

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
        markNeedsRebuild = false;
    }

    public String[] getRolesName() {
        return roles.stream().map((a) -> a.getName()).toArray(String[]::new);
    }

    /**
     *
     * @param id a numeric user id to get full data of user
     */
    public void selectUser(Long id) {
        if (id != null) {
            selectedUser = users.stream()
                    .filter(e -> Objects.equals(e.getId(), id))
                    .findFirst();
        } else {
            selectedUser = Optional.empty();
        }
    }

    public void deleteUser() {
        try (final EntityManager em = emf.createEntityManager()) {
            em.remove(selectedUser.orElseThrow());
            em.flush();
            em.clear();

            markNeedsRebuild = true;
        } catch (NoSuchElementException ex) {
        }
    }

    public void saveUser(
            String firstName,
            String lastName,
            int roleIndex,
            String username,
            String password,
            boolean isActive,
            User currentUser,
            boolean isUpdatePassword
    ) throws NullPointerException, Exception {
        firstName = Objects.requireNonNull(firstName);
        lastName = Objects.requireNonNull(lastName);
        username = Objects.requireNonNull(username);
        currentUser = Objects.requireNonNull(currentUser);
        long roleId = roles.get(roleIndex).getId();

        if (isUpdatePassword) {
            password = Objects.requireNonNull(password);
        }

        try (final EntityManager em = emf.createEntityManager()) {
            User user = selectedUser.orElse(null);
            Long id = null;

            StringBuilder nativeQuery = new StringBuilder();
            if (user != null) {
                id = user.getId();
                nativeQuery.append("UPDATE master_user SET ");

                if (!user.getFirstName().equals(firstName)) {
                    nativeQuery.append("first_name = :firstName, ");
                }
                if (!user.getLastName().equals(lastName)) {
                    nativeQuery.append("last_name = :lastName, ");
                }
                if (isUpdatePassword) {
                    nativeQuery.append("password = :password, ");
                }
                if (!user.getRoleId().equals(roleId)) {
                    nativeQuery.append("role_id = :roleId, ");
                }
                if (user.getIsActive() != isActive) {
                    nativeQuery.append("is_active = :isActive, ");
                }

                nativeQuery.append("updated_by = :userId ");
                nativeQuery.append("WHERE id = :id");
            } else {
                nativeQuery.append("INSERT INTO master_user ");
                nativeQuery.append("(first_name, last_name, username, password, role_id, is_active, updated_by, created_by) ");
                nativeQuery.append("VALUES (:firstName, :lastName, :username, :password, :roleId, :isActive, :userId, :userId)");
            }

            em.getTransaction().begin();

            Query query = em.createNativeQuery(nativeQuery.toString());
            query.setParameter("firstName", firstName);
            query.setParameter("lastName", lastName);
            query.setParameter("username", username);
            query.setParameter("password", password);
            query.setParameter("roleId", roleId);
            query.setParameter("isActive", isActive);
            query.setParameter("userId", currentUser.getId());

            if (id != null) {
                query.setParameter("id", id);
            }

            query.executeUpdate();

            em.getTransaction().commit();
        }

        markNeedsRebuild = true;
    }

    /**
     *
     * @param orElse an alternate if returned value is null, this may be null
     * @return a user id that has been selected using {@link selectUser}
     */
    public String getUserId(String orElse) {
        return selectedUser
                .map((a) -> String.format("%08d", a.getId()))
                .orElse(orElse);
    }

    /**
     *
     * @param orElse an alternate if returned value is null, this may be null
     * @return a user first name that has been selected using {@link selectUser}
     */
    public String getUserFirstName(String orElse) {
        return selectedUser.map((a) -> a.getFirstName()).orElse(orElse);
    }

    /**
     *
     * @param orElse an alternate if returned value is null, this may be null
     * @return a user last name that has been selected using {@link selectUser}
     */
    public String getUserLastName(String orElse) {
        return selectedUser.map((a) -> a.getLastName()).orElse(orElse);
    }

    /**
     *
     * @param orElse an alternate if returned value is null, this may be null
     * @return a user role index that has been selected using {@link selectUser}
     */
    public int getUserRoleIndex(Integer orElse) {
        return selectedUser.flatMap((a) -> {
            Optional<Role> selectedRole = roles.stream().filter((e) -> {
                return e.getIsActive()
                        && Objects.equals(e.getId(), a.getRoleId());
            }).findFirst();
            return selectedRole.map((b) -> roles.indexOf(b));
        }).orElse(Objects.requireNonNullElse(orElse, -1));
    }

    /**
     *
     * @param orElse an alternate if returned value is null, this may be null
     * @return a username of a user that has been selected using
     * {@link selectUser}
     */
    public String getUserUsername(String orElse) {
        return selectedUser.map((a) -> a.getUsername()).orElse(orElse);
    }

    /**
     *
     * @param orElse an alternate if returned value is null, this may be null
     * @return a status active of a user that has been selected using
     * {@link selectUser}
     */
    public Boolean getUserIsActive(Boolean orElse) {
        return selectedUser.map((a) -> a.getIsActive()).orElse(orElse);
    }

    /**
     *
     * @param orElse an alternate if returned value is null, this may be null
     * @return an updated timestamp of a user that has been selected using
     * {@link selectUser}
     */
    public String getUserUpdatedAt(String orElse) {
        return selectedUser
                .map((a) -> dateFormat.format(a.getUpdatedAt()))
                .orElse(orElse);
    }

    /**
     *
     * @param orElse an alternate if returned value is null, this may be null
     * @return a created timestamp of a user that has been selected using
     * {@link selectUser}
     */
    public String getUserCreatedAt(String orElse) {
        return selectedUser
                .map((a) -> dateFormat.format(a.getCreatedAt()))
                .orElse(orElse);
    }
}
