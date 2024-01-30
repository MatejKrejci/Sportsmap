package com.example.sport;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.event.ActionEvent;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Kontrolér pro správu událostí ve sportovní aplikaci.
 * Obsahuje metody pro inicializaci, přechody mezi scénami a manipulaci s událostmi.
 *
 * @author Krejčí Matěj, Lisý Milan
 * @version 1.1., 2024-01-28
 */

public class HelloController {

    public ListView meUdalosti;
    public ListView meVytvoreneUdalosti;
    public Label nazevEventu;
    public Label infoEventu;
    public Label seznamHracu;
    public Button prihlasitEvent;
    public Label mistoKonani;
    public Label Datum;
    public Label kapacita;
    @FXML
    public ImageView ObrazekHristeDU;
    public Label nazevEventu1;
    public Label infoEventu1;
    public Label seznamHracu1;
    public Button prihlasitEvent1;
    public Label mistoKonani1;
    public Label Datum1;
    public Label kapacita1;
    public TextField uzivatelskeJmenoReg;
    public ImageView ObrazekHristeDO;
    public PasswordField hesloReg;
    public PasswordField hesloZnavuReg;
    public PasswordField hesloPrih;
    public TextField uzivatelskeJmeno;
    public TextField changeJmenoUziv;
    public TextField changePrijmeniUziv;
    public TextField changeDatumNar;
    public PasswordField changeHeslo;
    public PasswordField inputHeslp;
    public ChoiceBox<String> inputObtiznost;
    public TextField inputKapacita;
    public DatePicker inputDatum;
    public TextArea inputInfoEvent;
    public TextField inputNazevEvent;
    public Label infoHristě;
    public ImageView ObrazekHriste;
    public ChoiceBox inputSportoviste;
    public TableView hristeTable;
    public ListView SeznamEventu;
    public Event Udalost;
    private DatabaseManager database;
    private Uzivatel uzivatel;
    public Label LabelMilan;
    @FXML
    private Stage stage;
    @FXML
    private Scene scene;
    @FXML
    private Parent root;
    private ObservableList<String> eventNames = FXCollections.observableArrayList();
    private ObservableList<String> myEventNames = FXCollections.observableArrayList();
    private ObservableList<String> myCreatedEventNames = FXCollections.observableArrayList();

    /**
     * Konstruktor pro vytvoření instance třídy {@code HelloController}.
     *
     * @param databaseManager Správce databáze pro komunikaci s úložištěm dat.
     * @param uzivatel       Přihlášený uživatel.
     * @param event          Aktuální událost.
     */
    public HelloController(DatabaseManager databaseManager, Uzivatel uzivatel, Event event) {
        this.database = databaseManager;
        this.uzivatel = uzivatel;
        this.Udalost = event;
    }

    /**
     * Inicializační metoda, která nastavuje výchozí hodnoty a možnosti pro JavaFX komponenty.
     * Načítá také seznam sportovišť z databáze pro výběr ve formuláři.
     */
    @FXML
    public void initialize() {
        // Obtiznost choicebox moznosti
        ObservableList<String> choices = FXCollections.observableArrayList("Začátečníci", "Mírně pokročilý", "Pokročilý");

        if (inputObtiznost != null) {
            inputObtiznost.setItems(choices);

            inputObtiznost.setValue("Obtížnost");
        }

        List<String> nazvySportovist = new ArrayList<>();

        for (Sportoviste sportoviste : database.getAllSportovisteFromDatabase()) {
            nazvySportovist.add(sportoviste.getNazevSportoviste());
        }

        ObservableList<String> sportoviste = FXCollections.observableArrayList(nazvySportovist);

        if (inputSportoviste != null) {
            inputSportoviste.setItems(sportoviste);

            inputSportoviste.setValue("Vyberte Sportoviste");
        }
    }

    /**
     * Metoda pro přechod na obrazovku registrace.
     *
     * @param event Akce, která vyvolala přechod na obrazovku registrace.
     * @throws IOException Výjimka při chybě při načítání FXML souboru.
     */
    @FXML
    public void toRegistrovatSe(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("registrace.fxml"));
        fxmlLoader.setController(this);
        Scene scene = new Scene(fxmlLoader.load(), 310, 480);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Metoda pro přihlášení uživatele a přechod na hlavní mapu.
     *
     * @param event Akce při stisku tlačítka přihlášení.
     * @throws IOException Výjimka při chybě při načítání FXML souboru.
     */
    @FXML
    public void toPrihlasitSe(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("prihlasovani.fxml"));
        fxmlLoader.setController(this);
        Scene scene = new Scene(fxmlLoader.load(), 310, 480);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Metoda pro přechod na hlavní mapu aplikace.
     *
     * @param event Akce, která vyvolala přechod na hlavní mapu.
     * @throws IOException Výjimka při chybě při načítání FXML souboru.
     */
    @FXML
    public void toMainMap(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("mainMapa.fxml"));
        fxmlLoader.setController(this);
        Scene scene = new Scene(fxmlLoader.load(), 310, 480);
        stage.setScene(scene);
        stage.show();
        List<String> IdPrihlasenychEventu = database.getPrihlaseneEventy(uzivatel.getUsername());
    }

    /**
     * Metoda pro zobrazení mapového pop-up okna.
     *
     * @param event Akce, která vyvolala zobrazení mapového pop-up okna.
     * @throws IOException Výjimka při chybě při načítání FXML souboru.
     */
    @FXML
    public void toMapaPopUp(ActionEvent event) throws IOException {
        Udalost = new Event();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("mapaPopUp.fxml"));
        fxmlLoader.setController(this);
        Scene scene = new Scene(fxmlLoader.load(), 310, 480);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Metoda pro přechod na obrazovku uživatelského profilu.
     *
     * @param event Akce, která vyvolala přechod na obrazovku uživatelského profilu.
     * @throws IOException Výjimka při chybě při načítání FXML souboru.
     */
    @FXML
    public void toProfil(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("profil.fxml"));
        fxmlLoader.setController(this);
        Scene scene = new Scene(fxmlLoader.load(), 310, 480);
        stage.setScene(scene);
        stage.show();
        LabelMilan.setText(uzivatel.getUsername());
    }

    /**
     * Metoda pro přechod na obrazovku nastavení uživatelského účtu.
     *
     * @param event Akce, která vyvolala přechod na obrazovku nastavení uživatelského účtu.
     * @throws IOException Výjimka při chybě při načítání FXML souboru.
     */
    @FXML
    public void toNastaveniUctu(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("nastaveniUctu.fxml"));
        fxmlLoader.setController(this);
        Scene scene = new Scene(fxmlLoader.load(), 310, 480);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Metoda pro přechod na obrazovku s osobními událostmi.
     * Načte FXML soubor, nastaví kontrolér, vytvoří scénu a zobrazí ji na stage.
     * Následně načte vytvořené a účastnické události.
     *
     * @param event Akce, která vyvolala přechod na obrazovku s osobními událostmi.
     * @throws IOException Výjimka při chybě při načítání FXML souboru.
     */
    @FXML
    public void toMeUdalosti(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("meUdalosti.fxml"));
        fxmlLoader.setController(this);
        Scene scene = new Scene(fxmlLoader.load(), 310, 480);
        stage.setScene(scene);
        stage.show();
        loadVytvoreneUdalosti();
        loadMeUdalosti();
    }

    /**
     * Metoda pro odhlášení uživatele z události.
     * Získá uživatele a ID události, zkontroluje možnost odhlášení pomocí databáze.
     * Pokud je odhlášení úspěšné, sníží obsazenost události o jedna a provede přechod na obrazovku s osobními událostmi.
     * V případě neúspěchu zobrazí chybový dialog.
     *
     * @param event Akce, která vyvolala odhlášení uživatele z události.
     * @throws IOException Výjimka při chybě při přechodu na obrazovku s osobními událostmi.
     */
    @FXML
    public void odhlasitSeEvent(ActionEvent event) throws IOException {
        String prihlasujici = uzivatel.getUsername();
        String idEvent = Udalost.getID();
        if (database.odhlasitUzivateleZEventu(prihlasujici, idEvent)) {
            database.snizeniObsazenostiOJedna(idEvent);
            //database.odhlasitUzivateleZEventu(prihlasujici, idEvent);
            toMeUdalosti(event);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Nelze odhlasit z eventu");
            alert.showAndWait();
        }
    }

    /**
     * Metoda pro načtení událostí vytvořených přihlášeným uživatelem.
     * Vyčistí seznam jmen vytvořených událostí, získá události z databáze podle ID přihlášeného uživatele
     * a přidá názvy těchto událostí do seznamu. Nakonec nastaví seznam jako obsah prvku JavaFX ListView pro vytvořené události.
     */
    public void loadVytvoreneUdalosti() {
        myCreatedEventNames.clear();

        List<Event> events = database.getEventsByUserId(uzivatel.getUsername());


        for (Event eventik : events) {
            myCreatedEventNames.add(eventik.getNazev());
        }

        meVytvoreneUdalosti.setItems(myCreatedEventNames);
    }

    /**
     * Metoda pro načtení událostí, na které je přihlášený uživatel.
     * Vyčistí seznam jmen událostí, získá ID událostí, na které je uživatel přihlášen,
     * získá všechny události vytvořené přihlášeným uživatelem, vyfiltruje události, na které není přihlášen,
     * a přidá názvy těchto událostí do seznamu. Nakonec nastaví seznam jako obsah prvku JavaFX ListView pro události.
     */
    public void loadMeUdalosti() {
        myEventNames.clear();

        List<String> IdPrihlasenychEventu = database.getPrihlaseneEventy(uzivatel.getUsername());

        List<Event> events = database.getEventsByUserId(uzivatel.getUsername());

        List<String> eventIds = events.stream().map(Event::getID).collect(Collectors.toList());

        IdPrihlasenychEventu.removeAll(eventIds);

        List<Event> allEvents = database.getAllEventFromDatabase();

        List<Event> selectedEvents = allEvents.stream()
                .filter(event -> IdPrihlasenychEventu.contains(event.getID()))
                .collect(Collectors.toList());

        for (Event eventik : selectedEvents) {
            myEventNames.add(eventik.getNazev());
        }

        meUdalosti.setItems(myEventNames);
    }

    /**
     * Metoda pro zobrazení detailů události po kliknutí na seznam vytvořených událostí.
     *
     * @throws IOException Výjimka při chybě při načítání FXML souboru.
     */
    @FXML
    public void SeznamVytvorenychEventu() throws IOException {
        Object selectedItem = meVytvoreneUdalosti.getSelectionModel().getSelectedItem();
        List<Event> events = database.getAllEventFromDatabase();

        for (Event kliknutyEvent : database.getAllEventFromDatabase()) {

            if (kliknutyEvent.getNazev().equalsIgnoreCase(selectedItem.toString())) {
                Udalost.setID(kliknutyEvent.getID());
                Udalost.setUsername(kliknutyEvent.getUsername());
                Udalost.setID_sportoviste(kliknutyEvent.getID_sportoviste());
                Udalost.setNazev(kliknutyEvent.getNazev());
                Udalost.setKapacita(kliknutyEvent.getKapacita());
                Udalost.setObsazenost(kliknutyEvent.getObsazenost());
                Udalost.setDatum(kliknutyEvent.getDatum());
                Udalost.setKomentar(kliknutyEvent.getKomentar());
                toDetailOdhlaseni();
            }
        }
    }

    /**
     * Metoda pro získání detailů vybrané události ze seznamu "Moje události".
     * Získá vybranou událost z prvku JavaFX ListView, porovná její název s událostmi v databázi,
     * a při shodě nastaví detaily události (ID, uživatelské jméno, ID sportoviště, název, kapacita, obsazenost, datum, komentář).
     * Nakonec přechází na obrazovku s detailem a odhlášením události.
     *
     * @throws IOException Vyjímka, pokud dojde k chybě při přechodu na obrazovku s detailem a odhlášením události.
     */
    public void SeznamMychEventu() throws IOException {
        Object selectedItem = meUdalosti.getSelectionModel().getSelectedItem();
        List<Event> events = database.getAllEventFromDatabase();

        for (Event kliknutyEvent : database.getAllEventFromDatabase()) {

            if (kliknutyEvent.getNazev().equalsIgnoreCase(selectedItem.toString())) {
                Udalost.setID(kliknutyEvent.getID());
                Udalost.setUsername(kliknutyEvent.getUsername());
                Udalost.setID_sportoviste(kliknutyEvent.getID_sportoviste());
                Udalost.setNazev(kliknutyEvent.getNazev());
                Udalost.setKapacita(kliknutyEvent.getKapacita());
                Udalost.setObsazenost(kliknutyEvent.getObsazenost());
                Udalost.setDatum(kliknutyEvent.getDatum());
                Udalost.setKomentar(kliknutyEvent.getKomentar());
                toDetailOdhlaseni();
            }
        }
    }

    /**
     * Metoda pro přechod na obrazovku s detailem a odhlášením události.
     * Inicializuje nový FXMLLoader s příslušným zdrojem FXML souboru (detailOdhlaseni.fxml),
     * nastavuje aktuální kontroler jako controller pro FXMLLoader,
     * vytváří novou scénu s načteným FXML, nastavuje ji jako hlavní scénu pro stage
     * a zobrazuje novou scénu. Nakonec volá metodu fillDataFromEvent1 pro vyplnění dat z vybrané události.
     *
     * @throws IOException Vyjímka, pokud dojde k chybě při načítání FXML souboru nebo při přechodu na obrazovku.
     */
    private void toDetailOdhlaseni() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("detailOdhlaseni.fxml"));
        fxmlLoader.setController(this);
        Scene scene = new Scene(fxmlLoader.load(), 310, 480);
        stage.setScene(scene);
        stage.show();
        fillDataFromEvent1(Udalost);
    }

    /**
     * Metoda pro přechod na obrazovku vytváření události.
     *
     * @param event Objekt události, který spustil tuto metodu (např. stisk tlačítka).
     * @throws IOException Pokud dojde k chybě při načítání FXML souboru nebo nastavení scény.
     */
    @FXML
    public void toVytvoreniUdalosti(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("vytvoreniUdalosti.fxml"));
        fxmlLoader.setController(this);
        Scene scene = new Scene(fxmlLoader.load(), 310, 480);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Metoda pro přechod na obrazovku detailu události.
     *
     * @throws IOException Pokud dojde k chybě při načítání FXML souboru nebo nastavení scény.
     */
    private void toDetailUdalosti() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("detailUdalosti.fxml"));
        fxmlLoader.setController(this);
        Scene scene = new Scene(fxmlLoader.load(), 310, 480);
        stage.setScene(scene);
        stage.show();
        fillDataFromEvent(Udalost);
    }

    /**
     * Metoda pro přechod zpět na hlavní mapovou obrazovku.
     *
     * @param event Akce, která spustila tuto metodu.
     * @throws IOException Pokud dojde k chybě při načítání FXML souboru nebo nastavení scény.
     */
    @FXML
    private void zpetV(ActionEvent event) throws IOException {
        toMainMap(event);
    }

    /**
     * Metoda pro přechod zpět na obrazovku profilu.
     *
     * @param event Akce, která spustila tuto metodu.
     * @throws IOException Pokud dojde k chybě při načítání FXML souboru nebo nastavení scény.
     */
    @FXML
    private void zpetD(ActionEvent event) throws IOException {
        toProfil(event);
    }

    /**
     * Metoda pro obsluhu události po stisknutí tlačítka "Přihlásit se".
     *
     * @param event Akce, která spustila tuto metodu.
     * @throws IOException Pokud dojde k chybě při načítání FXML souboru nebo nastavení scény.
     */
    @FXML
    private void prihlasitSe(ActionEvent event) throws IOException {
        // Akce po stisknutí tlačítka "Přihlásit se"
        String heslo = hesloPrih.getText();
        String username = uzivatelskeJmeno.getText();
        uzivatel.setUsername(username);

        if (database.KontrolaHesla(username, heslo)) {
            toMainMap(event);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Nesprávné uživatelské jméno nebo heslo.");
            alert.showAndWait();
        }
    }

    /**
     * Metoda pro odhlášení uživatele a přechod na obrazovku přihlášení.
     *
     * @param event Akce, která spustila tuto metodu.
     * @throws IOException Pokud dojde k chybě při načítání FXML souboru nebo nastavení scény.
     */
    @FXML
    private void logOut(ActionEvent event) throws IOException {
        // Akce po stisknutí tlačítka "Přihlásit se"
        uzivatel.setUsername("");
        toPrihlasitSe(event);
    }

    /**
     * Metoda pro smazání uživatelského účtu a přechod na obrazovku přihlášení.
     *
     * @param event Akce, která spustila tuto metodu.
     * @throws IOException Pokud dojde k chybě při načítání FXML souboru nebo nastavení scény.
     */
    @FXML
    private void smazatUcet(ActionEvent event) throws IOException {
        String heslo = inputHeslp.getText();

        if (database.smazaniUctu(uzivatel.getUsername(), heslo)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Ucet byl smazan.");
            alert.showAndWait();
            toPrihlasitSe(event);
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Špatné heslo.");
            alert.showAndWait();
        }
    }

    /**
     * Metoda pro editaci profilu uživatele na základě zadaných informací (jméno, příjmení, věk).
     *
     * @param event Akce, která spustila tuto metodu.
     */
    @FXML
    private void EditovatProfil(ActionEvent event) {
        // Akce po stisknutí tlačítka "Přihlásit se"
        String vek = changeDatumNar.getText();
        int intVek = 0;

        try {
            intVek = Integer.parseInt(vek);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        String jmeno = changeJmenoUziv.getText();
        String prijmeni = changePrijmeniUziv.getText();

        if (vek != "") {
            database.pridatVek(uzivatel.getUsername(), intVek);
        }
        if (jmeno != "") {
            database.pridatJmeno(uzivatel.getUsername(), jmeno);
        }
        if (prijmeni != "") {
            database.pridatPrijmeni(uzivatel.getUsername(), prijmeni);
        }
    }

    /**
     * Metoda pro změnu hesla uživatele na základě zadaného nového hesla.
     *
     * @param event Akce, která spustila tuto metodu.
     */
    @FXML
    private void zmenitHeslo(ActionEvent event) {
        String hlavniUsername = uzivatel.getUsername();
        // Akce po stisknutí tlačítka "Přihlásit se"
        String heslo = changeHeslo.getText();
        database.zmenaHesla(hlavniUsername, heslo);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Heslo bylo zmeneno");
        alert.showAndWait();
    }

    /**
     * Metoda pro vytvoření nové události na základě zadaných informací.
     *
     * @param event Akce, která spustila tuto metodu.
     * @throws IOException Pokud dojde k chybě při načítání grafického rozhraní.
     */
    @FXML
    private void vytvoritUdalost(ActionEvent event) throws IOException {
        String nazevEventu = inputNazevEvent.getText();
        String infoEventu = inputInfoEvent.getText();
        String hriste = (String) inputSportoviste.getValue();
        LocalDate datum = inputDatum.getValue();
        String kapacitaStr = inputKapacita.getText();
        String obtiznost = inputObtiznost.getValue();

        int kapacita;
        try {
            kapacita = Integer.parseInt(kapacitaStr);
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Zadejte kapacitu ve tvaru čísla.");
            alert.showAndWait();
            return;
        }
        String idHriste = "";

        for (Sportoviste sportoviste : database.getAllSportovisteFromDatabase()) {
            if (sportoviste.getNazevSportoviste().equalsIgnoreCase(hriste)) {
                idHriste = sportoviste.getIdSportoviste();
                break;
            }
        }
        int kapacitaSport = database.getKapacitaSportovisteById(idHriste);

        if (nazevEventu.isEmpty() || infoEventu.isEmpty() || datum == null || kapacita == 0 || obtiznost == null || idHriste.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Vyplňte všechny pole pro vytvoření události.");
            alert.showAndWait();
            return;
        }

        if (kapacita > kapacitaSport) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Kapacita události nesmí být vyšší než kapacita sportoviště: " + kapacitaSport);
            alert.showAndWait();
            return;
        }

        database.vytvoritEvent(idHriste, uzivatel.getUsername(), nazevEventu, kapacita, 1, datum, infoEventu);
        toMainMap(event);
    }

    /**
     * Metoda reagující na kliknutí na položku v seznamu událostí.
     * Na základě vybrané události aktualizuje objekt {@code Udalost} a přechází na detail události.
     *
     * @throws IOException Pokud dojde k chybě při načítání grafického rozhraní detailu události.
     */
    @FXML
    public void SeznamEventuKlik() throws IOException {
        Object selectedItem = SeznamEventu.getSelectionModel().getSelectedItem();
        List<Event> events = database.getAllEventFromDatabase();

        for (Event kliknutyEvent : database.getAllEventFromDatabase()) {

            if (kliknutyEvent.getNazev().equalsIgnoreCase(selectedItem.toString())) {
                Udalost.setID(kliknutyEvent.getID());
                Udalost.setUsername(kliknutyEvent.getUsername());
                Udalost.setID_sportoviste(kliknutyEvent.getID_sportoviste());
                Udalost.setNazev(kliknutyEvent.getNazev());
                Udalost.setKapacita(kliknutyEvent.getKapacita());
                Udalost.setObsazenost(kliknutyEvent.getObsazenost());
                Udalost.setDatum(kliknutyEvent.getDatum());
                Udalost.setKomentar(kliknutyEvent.getKomentar());

                toDetailUdalosti();
            }
        }
    }


    public void fillDataFromEvent(Event udalost) {
        udalost = Udalost;
        nazevEventu.setText("Nazev udalosti: " + udalost.getNazev());
        infoEventu.setText("Popis udalosti: " + udalost.getKomentar());
        seznamHracu.setText("Obsazenost udalosti: " + Udalost.getObsazenost() + " / " + udalost.getKapacita());
        mistoKonani.setText("Misto konani: " + database.getNazevSportovisteById(Udalost.getID_sportoviste()));
        Datum.setText("Datum: " + udalost.getDatum().toString());
        kapacita.setText("Kapacita: " + udalost.getKapacita());
        ObrazekHristeDU.setImage(new Image(getClass().getResource("/com/example/sport/obrazky/" + Udalost.getID_sportoviste() + ".jpg").toString()));
        ObrazekHristeDO.setImage(new Image(getClass().getResource("/com/example/sport/obrazky/" + Udalost.getID_sportoviste() + ".jpg").toString()));
    }

    /**
     * Metoda sloužící k naplnění grafického rozhraní informacemi o události.
     *
     * @param udalost Objekt reprezentující událost, ze které jsou brány informace.
     */
    public void fillDataFromEvent1(Event udalost) {
        udalost = Udalost;
        nazevEventu1.setText("Nazev udalosti: " + udalost.getNazev());
        infoEventu1.setText("Popis udalosti: " + udalost.getKomentar());
        seznamHracu1.setText("Obsazenost udalosti: " + Udalost.getObsazenost() + " / " + udalost.getKapacita());
        mistoKonani1.setText("Misto konani: " + database.getNazevSportovisteById(Udalost.getID_sportoviste()));
        Datum1.setText("Datum: " + udalost.getDatum().toString());
        kapacita1.setText("Kapacita: " + udalost.getKapacita());
        ObrazekHristeDU.setImage(new Image(getClass().getResource("/com/example/sport/obrazky/" + Udalost.getID_sportoviste() + ".jpg").toString()));
        ObrazekHristeDO.setImage(new Image(getClass().getResource("/com/example/sport/obrazky/" + Udalost.getID_sportoviste() + ".jpg").toString()));

    }
    /**
     * Metoda pro přihlášení uživatele na vybranou událost.
     *
     * @param event Akce při stisku tlačítka přihlášení na událost.
     * @throws IOException Výjimka při chybě při načítání FXML souboru.
     */
    @FXML
    private void PrihlasitEvent(ActionEvent event) throws IOException {
        String prihlasujici = uzivatel.getUsername();
        String idEvent = Udalost.getID();
        if (Udalost.getObsazenost() < Udalost.getKapacita() && database.isUzivatelPrihlasen(prihlasujici, idEvent) == false && database.isUzivatelPrihlasen2(prihlasujici, idEvent) == false) {
            database.zvyseniObsazenostiOJedna(idEvent);
            database.prihlasitEvent(prihlasujici, idEvent);
            toMeUdalosti(event);
        } else if (Udalost.getObsazenost() >= Udalost.getKapacita() && database.isUzivatelPrihlasen(prihlasujici, idEvent) == false && database.isUzivatelPrihlasen2(prihlasujici, idEvent) == false) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Udalost je plna");
            alert.showAndWait();
        } else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Už jsi přihlášen na události.");
            alert.showAndWait();
        }
    }

    /**
     * Metoda pro přechod na obrazovku úpravy událostí.
     *
     * @param event Akce stisknutí tlačítka, která spouští tuto metodu.
     * @throws IOException Výjimka, která může nastat při práci s vstupně/výstupními operacemi.
     */
    @FXML
    private void editEvent(ActionEvent event) throws IOException {
        // Akce po stisknutí tlačítka "Přihlásit se"
        toMeUdalosti(event);
    }

    /**
     * Metoda pro registraci uživatele a přechod na hlavní mapu po úspěšné registraci.
     *
     * @param event Akce stisknutí tlačítka, která spouští tuto metodu.
     * @throws IOException Výjimka, která může nastat při práci s vstupně/výstupními operacemi.
     */
    @FXML
    private void registrovatSe(ActionEvent event) throws IOException {
        if (!hesloReg.getText().equals(hesloZnavuReg.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Hesla nejsou stejna");
            alert.getDialogPane().getStylesheets().add(
                    getClass().getResource("/com/example/sport/cssstyles/styles.css").toExternalForm()
            );

            alert.getDialogPane().getStyleClass().add("custom-alert");
            alert.showAndWait();

        } else {
            database.Registrace(uzivatelskeJmenoReg.getText(), hesloReg.getText());
            uzivatel.setUsername(uzivatelskeJmenoReg.getText());
            // Akce po stisknutí tlačítka "Přihlásit se"
            toMainMap(event);
        }
    }

    /**
     * Metoda pro smazání události a návrat na hlavní mapu.
     *
     * @param event Akce stisknutí tlačítka, která spouští tuto metodu.
     * @throws IOException Výjimka, která může nastat při práci s vstupně/výstupními operacemi.
     */
    @FXML
    private void smazatEvent(ActionEvent event) throws IOException {
        // Akce po stisknutí tlačítka "Přihlásit se"
        toMainMap(event);
    }

    /**
     * Metoda pro zpracování události kliknutí na tlačítko reprezentující hřiště.
     * Po stisknutí tlačítka dojde k přechodu na mapu s podrobnostmi o hřišti a zobrazení událostí spojených s tímto hřištěm.
     *
     * @param event Akce stisknutí tlačítka, která spouští tuto metodu.
     * @throws IOException Výjimka, která může nastat při práci s vstupně/výstupními operacemi.
     */
    @FXML //novy
    private void hriste1Click(ActionEvent event) throws IOException {
        // Akce po stisknutí tlačítka "Přihlásit se"
        String id_sportoviste = "1";
        toMapaPopUp(event);

        TextFlow textFlow = new TextFlow();

        Text boldText = new Text("Fotbalové hřiště Drtinova");
        boldText.setFont(Font.font("System", FontWeight.BOLD, 16));

        Text normalText = new Text("\nDrtinova 304/7, 150 00 Praha 5 - Smíchov, Česko");
        normalText.setFont(Font.font("System", 12));

        textFlow.getChildren().addAll(boldText, normalText);
        textFlow.setTextAlignment(TextAlignment.CENTER);

        infoHristě.setGraphic(textFlow);
        ObrazekHriste.setImage(new Image("file:/C:/Users/matej/OneDrive/Plocha/Sportmap - obhajoba/SportMap - hotove/src/main/resources/com/example/sport/obrazky/1.jpg"));
        eventNames.clear();

        List<Event> events = database.getEventsBySportovisteId(id_sportoviste);

        for (Event eventik : events) {
            eventNames.add(eventik.getNazev());
        }
        SeznamEventu.setItems(eventNames);
    }

    /**
     * Metoda pro zpracování události kliknutí na tlačítko reprezentující hřiště.
     * Po stisknutí tlačítka dojde k přechodu na mapu s podrobnostmi o hřišti a zobrazení událostí spojených s tímto hřištěm.
     *
     * @param event Akce stisknutí tlačítka, která spouští tuto metodu.
     * @throws IOException Výjimka, která může nastat při práci s vstupně/výstupními operacemi.
     */
    @FXML //novy
    private void hriste2Click(ActionEvent event) throws IOException {
        // Akce po stisknutí tlačítka "Přihlásit se"
        String id_sportoviste = "2";
        toMapaPopUp(event);

        TextFlow textFlow = new TextFlow();

        Text boldText = new Text("Basketbalové hřiště Pod Petřínem");
        boldText.setFont(Font.font("System", FontWeight.BOLD, 16));

        Text normalText = new Text("\nÚjezd 409/19, 11800 Praha 1 - Malá Strana, Česko");
        normalText.setFont(Font.font("System", 12));

        textFlow.getChildren().addAll(boldText, normalText);
        textFlow.setTextAlignment(TextAlignment.CENTER);

        infoHristě.setGraphic(textFlow);
        ObrazekHriste.setImage(new Image("file:/C:/Users/matej/OneDrive/Plocha/Sportmap - obhajoba/SportMap - hotove/src/main/resources/com/example/sport/obrazky/2.jpg"));

        eventNames.clear();

        List<Event> events = database.getEventsBySportovisteId(id_sportoviste);

        for (Event eventik : events) {
            eventNames.add(eventik.getNazev());
        }
        SeznamEventu.setItems(eventNames);
    }

    /**
     * Metoda pro zpracování události kliknutí na tlačítko reprezentující hřiště.
     * Po stisknutí tlačítka dojde k přechodu na mapu s podrobnostmi o hřišti a zobrazení událostí spojených s tímto hřištěm.
     *
     * @param event Akce stisknutí tlačítka, která spouští tuto metodu.
     * @throws IOException Výjimka, která může nastat při práci s vstupně/výstupními operacemi.
     */
    @FXML //novy
    private void hriste3Click(ActionEvent event) throws IOException {
        // Akce po stisknutí tlačítka "Přihlásit se"
        String id_sportoviste = "3";
        toMapaPopUp(event);

        TextFlow textFlow = new TextFlow();

        Text boldText = new Text("Fotbalové hřiště Kozí");
        boldText.setFont(Font.font("System", FontWeight.BOLD, 16));

        Text normalText = new Text("\nKozí 799/10, 11000 Praha 1 - Staré Město, Česko");
        normalText.setFont(Font.font("System", 12));

        textFlow.getChildren().addAll(boldText, normalText);
        textFlow.setTextAlignment(TextAlignment.CENTER);

        infoHristě.setGraphic(textFlow);
        ObrazekHriste.setImage(new Image("file:/C:/Users/matej/OneDrive/Plocha/Sportmap - obhajoba/SportMap - hotove/src/main/resources/com/example/sport/obrazky/3.jpg"));

        eventNames.clear();

        List<Event> events = database.getEventsBySportovisteId(id_sportoviste);

        ObservableList<String> eventNames = FXCollections.observableArrayList();

        for (Event eventik : events) {
            eventNames.add(eventik.getNazev());
        }
        SeznamEventu.setItems(eventNames);
    }

    /**
     * Metoda pro zpracování události kliknutí na tlačítko reprezentující hřiště.
     * Po stisknutí tlačítka dojde k přechodu na mapu s podrobnostmi o hřišti a zobrazení událostí spojených s tímto hřištěm.
     *
     * @param event Akce stisknutí tlačítka, která spouští tuto metodu.
     * @throws IOException Výjimka, která může nastat při práci s vstupně/výstupními operacemi.
     */
    @FXML //novy
    private void hriste4Click(ActionEvent event) throws IOException {
        initialize();
        // Akce po stisknutí tlačítka "Přihlásit se"
        String id_sportoviste = "4";
        toMapaPopUp(event);

        TextFlow textFlow = new TextFlow();

        Text boldText = new Text("Fotbalové hřiště Masná");
        boldText.setFont(Font.font("System", FontWeight.BOLD, 16));

        Text normalText = new Text("\nMasná 702/9, 11000 Praha 1 - Staré Město, Česko");
        normalText.setFont(Font.font("System", 12));

        textFlow.getChildren().addAll(boldText, normalText);
        textFlow.setTextAlignment(TextAlignment.CENTER);

        infoHristě.setGraphic(textFlow);
        ObrazekHriste.setImage(new Image("file:/C:/Users/matej/OneDrive/Plocha/Sportmap - obhajoba/SportMap - hotove/src/main/resources/com/example/sport/obrazky/4.jpg"));

        List<Event> events = database.getEventsBySportovisteId(id_sportoviste);

        ObservableList<String> eventNames = FXCollections.observableArrayList();

        for (Event eventik : events) {
            eventNames.add(eventik.getNazev());
        }
        SeznamEventu.setItems(eventNames);
    }

    /**
     * Metoda pro zpracování události kliknutí na tlačítko reprezentující hřiště.
     * Po stisknutí tlačítka dojde k přechodu na mapu s podrobnostmi o hřišti a zobrazení událostí spojených s tímto hřištěm.
     *
     * @param event Akce stisknutí tlačítka, která spouští tuto metodu.
     * @throws IOException Výjimka, která může nastat při práci s vstupně/výstupními operacemi.
     */
    @FXML //novy
    private void hriste5Click(ActionEvent event) throws IOException {
        initialize();
        // Akce po stisknutí tlačítka "Přihlásit se"
        String id_sportoviste = "5";
        toMapaPopUp(event);

        TextFlow textFlow = new TextFlow();

        Text boldText = new Text("Sportovní areál Na Františku");
        boldText.setFont(Font.font("System", FontWeight.BOLD, 16));

        Text normalText = new Text("\nNa Františku 811/10, 110 00 Praha 1 - Staré Město, Česko");
        normalText.setFont(Font.font("System", 12));

        textFlow.getChildren().addAll(boldText, normalText);
        textFlow.setTextAlignment(TextAlignment.CENTER);

        infoHristě.setGraphic(textFlow);
        ObrazekHriste.setImage(new Image("file:/C:/Users/matej/OneDrive/Plocha/Sportmap - obhajoba/SportMap - hotove/src/main/resources/com/example/sport/obrazky/5.jpg"));

        List<Event> events = database.getEventsBySportovisteId(id_sportoviste);

        ObservableList<String> eventNames = FXCollections.observableArrayList();

        for (Event eventik : events) {
            eventNames.add(eventik.getNazev());
        }
        SeznamEventu.setItems(eventNames);

    }

    /**
     * Metoda pro zpracování události kliknutí na tlačítko reprezentující hřiště.
     * Po stisknutí tlačítka dojde k přechodu na mapu s podrobnostmi o hřišti a zobrazení událostí spojených s tímto hřištěm.
     *
     * @param event Akce stisknutí tlačítka, která spouští tuto metodu.
     * @throws IOException Výjimka, která může nastat při práci s vstupně/výstupními operacemi.
     */
    @FXML //novy
    private void hriste6Click(ActionEvent event) throws IOException {
        initialize();
        // Akce po stisknutí tlačítka "Přihlásit se"
        String id_sportoviste = "6";
        toMapaPopUp(event);

        TextFlow textFlow = new TextFlow();

        Text boldText = new Text("Basketbalové hřiště Husova");
        boldText.setFont(Font.font("System", FontWeight.BOLD, 16));

        Text normalText = new Text("\nHusova 156/21, 110 00 Praha 1 - Staré Město, Česko");
        normalText.setFont(Font.font("System", 12));

        textFlow.getChildren().addAll(boldText, normalText);
        textFlow.setTextAlignment(TextAlignment.CENTER);

        infoHristě.setGraphic(textFlow);
        ObrazekHriste.setImage(new Image("file:/C:/Users/matej/OneDrive/Plocha/Sportmap - obhajoba/SportMap - hotove/src/main/resources/com/example/sport/obrazky/6.jpg"));

        List<Event> events = database.getEventsBySportovisteId(id_sportoviste);

        ObservableList<String> eventNames = FXCollections.observableArrayList();
        for (Event eventik : events) {
            eventNames.add(eventik.getNazev());
        }
        SeznamEventu.setItems(eventNames);
    }

    /**
     * Metoda pro zpracování události kliknutí na obrázek (ImageView) s cílem vrátit se na hlavní mapu.
     * Po kliknutí na obrázek dojde k načtení pohledu hlavní mapy a nastavení scény.
     *
     * @param event Událost kliknutí myší, která spouští tuto metodu.
     * @throws IOException Výjimka, která může nastat při práci s vstupně/výstupními operacemi.
     */
    @FXML //novy
    private void toMainMapMap(MouseEvent event) throws IOException {
        // Akce po kliknutí na ImageView
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("mainMapa.fxml"));
        fxmlLoader.setController(this);
        Scene scene = new Scene(fxmlLoader.load(), 310, 480);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Metoda nastavující hlavní okno (Stage) pro tuto instanci třídy.
     * Používá se k předání hlavního okna pro manipulaci s grafickým rozhraním.
     *
     * @param stage Hlavní okno (Stage), které má být nastaveno pro tuto instanci třídy.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }
}