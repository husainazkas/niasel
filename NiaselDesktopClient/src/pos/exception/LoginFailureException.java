/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pos.exception;

/**
 *
 * @author husainazkas
 */
public class LoginFailureException extends Exception {

    public LoginFailureException(String message) {
        super(message);
    }

    public static LoginFailureException userNotFound() {
        return new LoginFailureException("User not found");
    }

    public static LoginFailureException invalidUsernameOrPassword() {
        return new LoginFailureException("Invalid username or password");
    }

    public static LoginFailureException userIsInactive() {
        return new LoginFailureException("User is inactive, you have to contact an administrator to get login");
    }
}
