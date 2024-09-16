/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pos.controller;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.util.List;
import java.util.Optional;
import org.apache.commons.codec.digest.DigestUtils;
import pos.exception.LoginFailureException;
import pos.model.Role;
import pos.model.User;

/**
 *
 * @author husainazkas
 */
public class AuthController extends BaseController {

    private boolean isSubmitting = false;
    private Optional<User> currentUser = Optional.empty();

    public boolean getIsSubmitting() {
        return isSubmitting;
    }

    public Optional<User> getCurrentUser() {
        return currentUser;
    }

    public void login(String username, String password) throws Exception {
        isSubmitting = true;

        final String encodedUsername = DigestUtils.sha1Hex(username);
        final String encodedPass = DigestUtils.sha1Hex(password);

        try (final EntityManager em = emf.createEntityManager()) {
            Query query = em.createNativeQuery("SELECT * FROM master_user e WHERE e.username = :username AND e.password = :password", User.class);
            query.setParameter("username", encodedUsername);
            query.setParameter("password", encodedPass);

            List<User> users = (List<User>) query.getResultList();
            if (users.isEmpty()) {
                throw LoginFailureException.invalidUsernameOrPassword();
            }

            User user = users.get(0);
            if (user.getIsDeleted()) {
                throw LoginFailureException.userNotFound();
            } else if (!user.getIsActive()) {
                throw LoginFailureException.userIsInactive();
            } 
            
            Role role = user.getRole();
            if (role == null || !role.getIsActive()) {
                throw new Exception("Role is not active");
            }

            currentUser = Optional.of(user);
        } finally {
            isSubmitting = false;
        }
    }

    public void logout() {
        currentUser = Optional.empty();
        isSubmitting = false;
    }

}
