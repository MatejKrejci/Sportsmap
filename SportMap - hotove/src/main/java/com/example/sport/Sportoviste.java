package com.example.sport;

import java.net.URL;

/**
 * Třída reprezentující sportoviště. Obsahuje informace o sportovišti jako ID, druh sportu, název sportoviště, adresa,
 * číslo popisné, PSČ, popisek a kapacita.
 *
 * @author Matěj Krejčí, Milan Lisý, František Žoltak
 * @version 1.2., 2024-01-28
 */

public class Sportoviste {
    private String idSportoviste;
    private String sport;
    private String nazevSportoviste;
    private String ulice;
    private int cisloPopisne;
    private int PSC;
    private String popisek;
    private int kapacita;

    /**
     * Konstruktor pro vytvoření instance sportoviště s předdefinovanými informacemi.
     *
     * @param idSportoviste    ID sportoviště.
     * @param sport            Druh sportu.
     * @param nazevSportoviste Název sportoviště.
     * @param ulice            Ulice, kde se sportoviště nachází.
     * @param cisloPopisne     Číslo popisné sportoviště.
     * @param PSC              PSČ sportoviště.
     * @param popisek          Popisek nebo krátký popis sportoviště.
     * @param kapacita         Kapacita sportoviště.
     */
    public Sportoviste(String idSportoviste, String sport, String nazevSportoviste, String ulice, int cisloPopisne, int PSC, String popisek, int kapacita) {
        this.idSportoviste = idSportoviste;
        this.sport = sport;
        this.nazevSportoviste = nazevSportoviste;
        this.ulice = ulice;
        this.cisloPopisne = cisloPopisne;
        this.PSC = PSC;
        this.popisek = popisek;
        this.kapacita = kapacita;
    }

    /**
     * Získává identifikátor sportoviště.
     *
     * @return Identifikátor sportoviště.
     */
    public String getIdSportoviste() {
        return idSportoviste;
    }

    /**
     * Nastavuje identifikátor sportoviště.
     *
     * @param idSportoviste Nový identifikátor sportoviště.
     */
    public void setIdSportoviste(String idSportoviste) {
        this.idSportoviste = idSportoviste;
    }

    /**
     * Získává druh sportu, který je na sportovišti provozován.
     *
     * @return Druh sportu.
     */
    public String getSport() {
        return sport;
    }

    /**
     * Nastavuje druh sportu, který je na sportovišti provozován.
     *
     * @param sport Nový druh sportu.
     */
    public void setSport(String sport) {
        this.sport = sport;
    }

    /**
     * Získává název sportoviště.
     *
     * @return Název sportoviště.
     */
    public String getNazevSportoviste() {
        return nazevSportoviste;
    }

    /**
     * Nastavuje název sportoviště.
     *
     * @param nazevSportoviste Nový název sportoviště.
     */
    public void setNazevSportoviste(String nazevSportoviste) {
        this.nazevSportoviste = nazevSportoviste;
    }

    /**
     * Získává název ulice, kde se sportoviště nachází.
     *
     * @return Název ulice.
     */
    public String getUlice() {
        return ulice;
    }

    /**
     * Nastavuje název ulice, kde se sportoviště nachází.
     *
     * @param ulice Nový název ulice.
     */
    public void setUlice(String ulice) {
        this.ulice = ulice;
    }

    /**
     * Získává číslo popisné sportoviště.
     *
     * @return Číslo popisné sportoviště.
     */
    public int getCisloPopisne() {
        return cisloPopisne;
    }

    /**
     * Nastavuje číslo popisné sportoviště.
     *
     * @param cisloPopisne Nové číslo popisné sportoviště.
     */
    public void setCisloPopisne(int cisloPopisne) {
        this.cisloPopisne = cisloPopisne;
    }

    /**
     * Získává poštovní směrovací číslo (PSC) sportoviště.
     *
     * @return Poštovní směrovací číslo (PSC).
     */
    public int getPSC() {
        return PSC;
    }

    /**
     * Nastavuje poštovní směrovací číslo (PSC) sportoviště.
     *
     * @param PSC Nové poštovní směrovací číslo (PSC).
     */
    public void setPSC(int PSC) {
        this.PSC = PSC;
    }

    /**
     * Získává popisek sportoviště.
     *
     * @return Popisek sportoviště.
     */
    public String getPopisek() {
        return popisek;
    }

    /**
     * Nastavuje popisek sportoviště.
     *
     * @param popisek Nový popisek sportoviště.
     */
    public void setPopisek(String popisek) {
        this.popisek = popisek;
    }

    /**
     * Získává kapacitu sportoviště.
     *
     * @return Kapacita sportoviště.
     */
    public int getKapacita() {
        return kapacita;
    }

    /**
     * Nastavuje kapacitu sportoviště.
     *
     * @param kapacita Nová kapacita sportoviště.
     */
    public void setKapacita(int kapacita) {
        this.kapacita = kapacita;
    }
}