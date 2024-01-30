package com.example.sport;

/**
 * Třída reprezentující uživatele. Obsahuje informace o uživateli jako jméno, příjmení, věk, uživatelské jméno a heslo.
 *
 * @author Milan Lisý, František Žoltak
 * @version 1.5., 2024-01-28
 */

public class Uzivatel {
    private String username;
    private String sport;
    private String heslo;
    private int vek;
    private String jmeno;
    private String prijmeni;

    /**
     * Konstruktor pro vytvoření instance uživatele s uživatelským jménem a heslem.
     *
     * @param username Uživatelské jméno.
     * @param heslo    Heslo.
     */
    Uzivatel(String username, String heslo) {
        this.username = username;
        this.heslo = heslo;
    }

    /**
     * Získává uživatelské jméno.
     *
     * @return Uživatelské jméno.
     */
    public String getUsername() {return username;}

    /**
     * Nastavuje uživatelské jméno.
     *
     * @param username Nové uživatelské jméno.
     */
    public void setUsername(String username) {this.username = username;}
}