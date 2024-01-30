package com.example.sport;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Třída reprezentující vstupní bod aplikace. Rozšiřuje třídu javafx.application.Application.
 * V této třídě se inicializuje aplikace, včetně nastavení scény a spuštění prvního okna.
 *
 * @author Milan Lisý
 * @version 1.3., 2024-01-28
 */

public class HelloApplication extends Application {

    /**
     * Vstupní metoda pro spuštění aplikace. Inicializuje okno registrace a nastavuje controller a správce databáze.
     *
     * @param stage Hlavní okno aplikace.
     * @throws IOException Pokud dojde k chybě při načítání FXML souboru.
     */
    //sem jen tad prihlasovani a setTitle
    @Override
    public void start(Stage stage) throws IOException {
        Uzivatel uzivatel = new Uzivatel("", "");
        Event event = new Event();
        DatabaseManager databaseManager = new DatabaseManager();
        HelloController helloController = new HelloController(databaseManager, uzivatel, event);

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("registrace.fxml"));
        fxmlLoader.setController(helloController);
        helloController.setStage(stage);

        Scene scene = new Scene(fxmlLoader.load(), 310, 480);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Vstupní metoda pro spuštění celé aplikace.
     *
     * @param args Argumenty příkazové řádky.
     */
    public static void main(String[] args) {launch();}
}