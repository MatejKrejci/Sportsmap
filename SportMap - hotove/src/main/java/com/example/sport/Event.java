package com.example.sport;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * Třída reprezentující událost na sportovišti.
 * Každá událost má svůj identifikátor, název, kapacitu účastníků, aktuální obsazenost, datum konání, tvůrce a volitelný komentář.
 *
 * Třída obsahuje metody pro získání informací o události pomocí getterů a také přetíženou metodu {@link #toString()},
 * která vrací textovou reprezentaci události pro účely debugování a logování.
 *
 * @author Krejčí Matěj
 * @version 1.1., 2024-01-28
 */

public class Event {

    private String ID_event;
    private String ID_sportoviste;
    private String username;
    private String nazev;
    private int kapacita;
    private int obsazenost;
    private LocalDateTime datum;
    private String komentar;

    /**
     * Výchozí konstruktor pro událost s defaultními hodnotami.
     */
    public Event() {
        this.ID_event = "0";
        this.ID_sportoviste = "0";
        this.username = "username";
        this.nazev = "nazev";
        this.kapacita = 0;
        this.obsazenost = 0;
        this.datum = LocalDateTime.now();
        this.komentar = "komentar";
    }

    /**
     * Konstruktor pro vytvoření události s konkrétními hodnotami.
     *
     * @param ID_event        Identifikátor události.
     * @param ID_sportoviste  Identifikátor sportoviště.
     * @param username        Uživatelské jméno tvůrce události.
     * @param nazev           Název události.
     * @param kapacita        Kapacita účastníků události.
     * @param obsazenost      Obsazenost události.
     * @param datum           Datum konání události.
     * @param komentar        Komentář k události.
     */
    public Event(String ID, String ID_sportoviste, String username, String nazev, int kapacita, int obsazenost, LocalDateTime datum, String komentar) {
        this.ID_event = ID;
        this.ID_sportoviste = ID_sportoviste;
        this.username = username;
        this.nazev = nazev;
        this.kapacita = kapacita;
        this.obsazenost = obsazenost;
        this.datum = datum;
        this.komentar = komentar;
    }

    /**
     * Získává identifikátor události.
     *
     * @return Identifikátor události.
     */
    public String getID() {return ID_event;}

    /**
     * Nastavuje identifikátor události na zadanou hodnotu.
     *
     * @param ID Nový identifikátor události.
     */
    public void setID(String ID) {this.ID_event = ID;}

    /**
     * Nastavuje identifikátor sportoviště, kde se událost koná, na zadanou hodnotu.
     *
     * @param idSportoviste Nový identifikátor sportoviště.
     */
    public void setID_sportoviste(String idSportoviste) {this.ID_sportoviste = idSportoviste;}

    /**
     * Získává identifikátor sportoviště, kde se událost koná.
     *
     * @return Identifikátor sportoviště.
     */
    public String getID_sportoviste() {return ID_sportoviste;}

    /**
     * Získává uživatelské jméno tvůrce události.
     *
     * @return Uživatelské jméno tvůrce události.
     */
    public String getUsername() {return username;}

    /**
     * Nastavuje uživatelské jméno tvůrce události na zadanou hodnotu.
     *
     * @param username Nové uživatelské jméno tvůrce události.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Získává název události.
     *
     * @return Název události.
     */
    public String getNazev() {return nazev;}

    /**
     * Nastavuje název události na zadanou hodnotu.
     *
     * @param nazev Nový název události.
     */
    public void setNazev(String nazev) {this.nazev = nazev;}

    /**
     * Získává kapacitu účastníků události.
     *
     * @return Kapacita účastníků události.
     */
    public int getKapacita(){return kapacita;}

    /**
     * Nastavuje kapacitu účastníků události na zadanou hodnotu.
     *
     * @param kapacita Nová kapacita účastníků události.
     */
    public void setKapacita(int kapacita) {this.kapacita = kapacita;}

    /**
     * Získává aktuální obsazenost události.
     *
     * @return Aktuální obsazenost události.
     */
    public int getObsazenost(){return obsazenost;}

    /**
     * Nastavuje aktuální obsazenost události na zadanou hodnotu.
     *
     * @param obsazenost Nová hodnota aktuální obsazenosti události.
     * @return Nová hodnota aktuální obsazenosti události.
     */
    public int setObsazenost(int obsazenost) {
        this.obsazenost = obsazenost;
        return obsazenost;
    }

    /**
     * Získává datum a čas konání události.
     *
     * @return Datum a čas konání události.
     */
    public LocalDateTime getDatum(){return datum;}

    /**
     * Nastavuje datum a čas konání události na zadanou hodnotu.
     *
     * @param datumACas Nové datum a čas konání události.
     */
    public void setDatum(LocalDateTime datumACas) {this.datum = datumACas;}

    /**
     * Získává komentář k události.
     *
     * @return Komentář k události.
     */
    public String getKomentar(){return komentar;}

    /**
     * Nastavuje komentář k události na zadanou hodnotu.
     *
     * @param komentar Nový komentář k události.
     */
    public void setKomentar(String komentar) {this.komentar = komentar;}

    /**
     * Vytváří textovou reprezentaci objektu události.
     *
     * @return Textová reprezentace objektu události.
     */
    @Override
    public String toString() {
        return "Event{" +
                "ID=" + ID_event +
                ", ID_sportoviste=" + ID_sportoviste +
                ", username='" + username + '\'' +
                ", nazev='" + nazev + '\'' +
                ", kapacita=" + kapacita +
                ", obsazenost=" + obsazenost +
                ", datum=" + datum +
                ", komentar='" + komentar + '\'' +
                '}';
    }
}
