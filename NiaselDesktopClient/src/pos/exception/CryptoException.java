/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pos.exception;

/**
 *
 * @author husainazkas
 */
public class CryptoException extends Exception {

    private CryptoException(String message) {
        super(message);
    }

    static public CryptoException encryption() {
        return new CryptoException("Failed to encrypt the data");
    }

    static public CryptoException decryption() {
        return new CryptoException("Failed to decrypt the data");
    }
}
