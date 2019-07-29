package edu.kit.mensameet.client.model;

/**
 * Credentials for the login, a singleton.
 */
public class Credentials {
    private static Credentials instance = new Credentials();
    private String email;
    private String password;

    private Credentials() {
    }

    public static Credentials getInstance() {
        return instance;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
