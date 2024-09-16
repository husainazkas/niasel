/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pos.exception;

/**
 *
 * @author husainazkas
 */
public class InstanceNotFoundException extends Exception {

    public InstanceNotFoundException() {

    }

    public InstanceNotFoundException(String message) {
        super(message);
    }

    public InstanceNotFoundException(Class className) {
        super(className.getSimpleName() + " is not registered yet. Did you forget to register?");
    }
}
