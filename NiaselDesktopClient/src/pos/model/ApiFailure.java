/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pos.model;

/**
 *
 * @author husainazkas
 */
public class ApiFailure extends Exception {

    private String status;
    private String message;

    public ApiFailure() {
        super();
    }

    public ApiFailure(String status, String message) {
        super(message);
        this.status = status;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
