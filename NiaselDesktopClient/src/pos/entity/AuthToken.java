/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pos.entity;

/**
 *
 * @author husainazkas
 */
public class AuthToken {

    private String accessToken;
    private String refreshToken;

    /**
     * Get the value of accessToken
     *
     * @return the value of accessToken
     */
    public String getAccessToken() {
        return accessToken;
    }

    /**
     * Set the value of accessToken
     *
     * @param accessToken new value of accessToken
     */
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    /**
     * Get the value of refreshToken
     *
     * @return the value of refreshToken
     */
    public String getRefreshToken() {
        return refreshToken;
    }

    /**
     * Set the value of refreshToken
     *
     * @param refreshToken new value of refreshToken
     */
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

}
