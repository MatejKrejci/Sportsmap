package com.example.sport;

import javafx.scene.control.Alert;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Třída DatabaseManager slouží především pro import databáze ale i jako správce databáze a poskytuje rozhraní pro komunikaci
 * a manipulaci s databázovým systémem.
 *
 * Tato třída zjednodušuje přístup k databázi,
 * poskytuje metody pro vytváření spojení, vykonávání dotazů, správu transakcí a další.
 *
 * Tato třída může být dále rozšířena o specifické metody a funkce v závislosti na potřebách
 * konkrétní aplikace a použitého databázového systému.
 *
 * @author Milan Lisý
 * @version 1.2., 2024-01-28
 */

public class DatabaseManager {

    public Connection connection;

    /**
     * Konstruktor třídy DatabaseManager, který vytváří připojení k databázi
     *
     */
    public DatabaseManager() {
        String url = "jdbc:mysql://localhost:3306/sportsmap1";
        String username = "root";
        String password = "Mastrmaty";
        try {
            connection = DriverManager.getConnection(url, username, password);
            //System.out.println("uspesne pripojeno");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Metoda Registrace slouží k registraci nového uživatele do systému.
     *
     * Uživatelské jméno a heslo jsou vloženy do databáze pomocí připraveného SQL dotazu. Po úspěšné registraci
     * je možné používat nově vytvořený účet pro přihlášení do systému.
     *
     * @param username Uživatelské jméno nového uživatele.
     * @param heslo    Heslo nového uživatele.
     *
     * @throws RuntimeException Pokud dojde k chybě při provádění SQL dotazu nebo pokud
     *                          zadané uživatelské jméno již existuje v databázi.
     */
    public void Registrace(String username, String heslo) {
        String sql = "INSERT INTO uzivatel (username, heslo) VALUES (?, ?)";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, heslo);
            int rows = statement.executeUpdate();
            if (rows > 0) {
                //System.out.println("A row has been inserted");
            }
            statement.close();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Dane jmeno uz je zabrane");
            alert.showAndWait();
            throw new RuntimeException(e);
        }
    }

    /**
     * Metoda KontrolaHesla slouží k ověření správnosti zadaného hesla pro konkrétního uživatele.
     * Porovnává zadané heslo s heslem uloženým v databázi pro dané uživatelské jméno.
     *
     * @param username Uživatelské jméno, pro které se kontroluje heslo.
     * @param heslo    Zadané heslo, které se kontroluje.
     *
     * @return true, pokud je zadané heslo shodné s heslem v databázi pro dané uživatelské jméno,
     *         jinak false.
     *
     * @throws RuntimeException Pokud dojde k chybě při provádění SQL dotazu.
     */
    public boolean KontrolaHesla(String username, String heslo) {
        String storedPassword = null;
        String sql = "SELECT HESLO FROM UZIVATEL WHERE USERNAME = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                storedPassword = resultSet.getString("HESLO");
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return storedPassword != null && storedPassword.equals(heslo);
    }

    /**
     * Metoda zmenaHesla slouží k změně hesla pro konkrétního uživatele v databázi.
     * Aktualizuje heslo pro zadané uživatelské jméno na nově zadané heslo.
     *
     * @param username  Uživatelské jméno uživatele, pro kterého se mění heslo.
     * @param noveHeslo Nové heslo, které se má nastavit pro uživatele.
     *
     * @throws RuntimeException Pokud dojde k chybě při provádění SQL dotazu.
     */
    public void zmenaHesla(String username, String noveHeslo) {
        String sql = "UPDATE uzivatel SET HESLO = ? WHERE USERNAME = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, noveHeslo);
            statement.setString(2, username);
            int rows = statement.executeUpdate();
            if (rows > 0) {
                //System.out.println("Heslo has been updated");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metoda smazaniUctu slouží k odstranění účtu uživatele z databáze v případě, že
     * zadané uživatelské jméno a heslo odpovídají uloženým údajům.
     *
     * @param username Uživatelské jméno uživatele, jehož účet se má odstranit.
     * @param heslo    Heslo uživatele pro ověření jeho identity.
     *
     * @return true, pokud byl účet úspěšně odstraněn, jinak false.
     *
     * @throws RuntimeException Pokud dojde k chybě při provádění SQL dotazu.
     */
    public boolean smazaniUctu(String username, String heslo) {
        if (KontrolaHesla(username, heslo)) {
            String sql = "DELETE FROM uzivatel WHERE USERNAME = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, username);

                int rows = statement.executeUpdate();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("Opravdu chcete odstranit svuj ucet?");
                alert.showAndWait();
                if (rows > 0) {
                    //System.out.println("User account has been deleted");
                    return true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            //System.out.println("Invalid username or password for account deletion");
        }
        return false;
    }

    /**
     * Metoda pridatJmeno slouží k aktualizaci jména uživatele v databázi na základě
     * zadaného uživatelského jména.
     *
     * @param username Uživatelské jméno uživatele, pro kterého se aktualizuje jméno.
     * @param jmeno    Nové jméno, které se má přiřadit uživateli.
     *
     * @throws RuntimeException Pokud dojde k chybě při provádění SQL dotazu.
     */
    public void pridatJmeno(String username, String jmeno) {
        String sql = "UPDATE uzivatel SET JMENO = ? WHERE USERNAME = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, jmeno);
            statement.setString(2, username);
            int rows = statement.executeUpdate();
            if (rows > 0) {
                //System.out.println("Jmeno has been updated");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metoda pridatPrijmeni slouží k aktualizaci příjmení uživatele v databázi na základě
     * zadaného uživatelského jména.
     *
     * @param username Uživatelské jméno uživatele, pro kterého se aktualizuje příjmení.
     * @param prijmeni Nové příjmení, které se má přiřadit uživateli.
     *
     * @throws RuntimeException Pokud dojde k chybě při provádění SQL dotazu.
     */
    public void pridatPrijmeni(String username, String prijmeni) {
        String sql = "UPDATE uzivatel SET PRIJMENI = ? WHERE USERNAME = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, prijmeni);
            statement.setString(2, username);
            int rows = statement.executeUpdate();
            if (rows > 0) {
                //System.out.println("Prijmeni has been updated");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metoda pridatVek slouží k aktualizaci věku uživatele v databázi na základě
     * zadaného uživatelského jména.
     *
     * @param username Uživatelské jméno uživatele, pro kterého se aktualizuje věk.
     * @param vek      Nový věk, který se má přiřadit uživateli.
     *
     * @throws RuntimeException Pokud dojde k chybě při provádění SQL dotazu.
     */
    public void pridatVek(String username, int vek) {
        String sql = "UPDATE uzivatel SET VEK = ? WHERE USERNAME = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, vek);
            statement.setString(2, username);
            int rows = statement.executeUpdate();
            if (rows > 0) {
                //System.out.println("Vek has been updated");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metoda vytvoritEvent slouží k vytvoření nového události (eventu) a uložení
     * informací o něm do databáze.
     *
     * @param ID_SPORTOVISTE Identifikátor sportoviště, na kterém se bude konat událost.
     * @param Username       Uživatelské jméno organizátora události.
     * @param nazevEventu    Název události.
     * @param kapacitaEventu Kapacita účastníků události.
     * @param obsazenost     Aktuální obsazenost události.
     * @param datumCas       Datum a čas konání události.
     * @param komentar       Komentář k události.
     *
     * @throws RuntimeException Pokud dojde k chybě při provádění SQL dotazu.
     */
    public void vytvoritEvent(String ID_SPORTOVISTE, String Username, String nazevEventu, int kapacitaEventu, int obsazenost, LocalDate datumCas, String komentar) {
        String sql = "INSERT INTO EVENT (ID_SPORTOVISTE, USERNAME, NAZEV_EVENT, KAPACITA_EVENT, OBSAZENOST, `DATUM A CAS`, KOMENTAR) VALUES ( ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, ID_SPORTOVISTE);
            preparedStatement.setString(2, Username);
            preparedStatement.setString(3, nazevEventu);
            preparedStatement.setInt(4, kapacitaEventu);
            preparedStatement.setInt(5, obsazenost);
            Timestamp timestamp = Timestamp.valueOf(datumCas.atStartOfDay());
            preparedStatement.setTimestamp(6, timestamp);
            preparedStatement.setString(7, komentar);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                //System.out.println("Event inserted successfully.");
            } else {
                //System.out.println("Failed to insert event.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metoda getEventsBySportovisteId získává seznam událostí (eventů) na základě identifikátoru sportoviště.
     *
     * @param sportovisteId Identifikátor sportoviště, pro které se získávají události.
     *
     * @return Seznam událostí spojených se sportovištěm daného identifikátoru.
     *
     * @throws RuntimeException Pokud dojde k chybě při provádění SQL dotazu.
     */
    public List<Event> getEventsBySportovisteId(String sportovisteId) {
        List<Event> events = new ArrayList<>();
        String sql = "SELECT * FROM event WHERE id_sportoviste = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, sportovisteId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Event event = new Event();
                    event.setID(resultSet.getString("id_event"));
                    event.setID_sportoviste(resultSet.getString("id_sportoviste"));
                    event.setUsername(resultSet.getString("username"));
                    event.setNazev(resultSet.getString("nazev_event"));
                    event.setKapacita(resultSet.getInt("kapacita_event"));
                    event.setObsazenost(resultSet.getInt("obsazenost"));
                    event.setDatum(resultSet.getTimestamp("datum a cas").toLocalDateTime());
                    event.setKomentar(resultSet.getString("komentar"));
                    events.add(event);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return events;
    }

    /**
     * Metoda getEventsByUserId získává seznam událostí (eventů) na základě uživatelského jména.
     *
     * @param username Uživatelské jméno, pro které se získávají události.
     *
     * @return Seznam událostí spojených s uživatelem daného jména.
     *
     * @throws RuntimeException Pokud dojde k chybě při provádění SQL dotazu.
     */
    public List<Event> getEventsByUserId(String username) {
        List<Event> events = new ArrayList<>();
        String sql = "SELECT * FROM event WHERE username = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Event event = new Event();
                    event.setID(resultSet.getString("id_event"));
                    event.setID_sportoviste(resultSet.getString("id_sportoviste"));
                    event.setUsername(resultSet.getString("username"));
                    event.setNazev(resultSet.getString("nazev_event"));
                    event.setKapacita(resultSet.getInt("kapacita_event"));
                    event.setObsazenost(resultSet.getInt("obsazenost"));
                    event.setDatum(resultSet.getTimestamp("datum a cas").toLocalDateTime());
                    event.setKomentar(resultSet.getString("komentar"));
                    events.add(event);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return events;
    }

    /**
     * Metoda getPrihlaseneEventy získává seznam identifikátorů událostí, na které je uživatel přihlášen.
     *
     * @param username Uživatelské jméno uživatele, pro kterého se získávají přihlášené události.
     *
     * @return Seznam identifikátorů událostí, na které je uživatel přihlášen.
     *
     * @throws RuntimeException Pokud dojde k chybě při provádění SQL dotazu.
     */
    public List<String> getPrihlaseneEventy(String username) {
        List<String> Ids = new ArrayList<>();
        String sql = "SELECT * FROM seznam_prihlasenych WHERE username = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String id = "";
                    id = resultSet.getString("ID_EVENT");
                    Ids.add(id);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Ids;
    }

    /**
     * Metoda getAllSportovisteFromDatabase získává seznam všech sportovišť z databáze.
     *
     * @return Seznam objektů typu Sportoviste obsahující informace o všech sportovištích.
     *
     * @throws RuntimeException Pokud dojde k chybě při provádění SQL dotazu.
     */
    public List<Sportoviste> getAllSportovisteFromDatabase() {
        List<Sportoviste> sportovisteList = new ArrayList<>();
        // Připojení k databázi a získání dat
        // ...
        String sql = "SELECT * FROM sportoviste";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Sportoviste sportoviste = new Sportoviste(
                            resultSet.getString("ID_SPORTOVISTE"),
                            resultSet.getString("NAZEV_SPORT"),
                            resultSet.getString("NAZEV_SPORTOVISTE"),
                            resultSet.getString("ULICE"),
                            resultSet.getInt("CISLO_POPISNE"),
                            resultSet.getInt("PSC"),
                            resultSet.getString("POPISEK"),
                            resultSet.getInt("KAPACITA_SPORTOVISTE")
                    );
                    sportovisteList.add(sportoviste);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sportovisteList;
    }

    /**
     * Metoda getAllEventFromDatabase získává seznam všech událostí (eventů) z databáze.
     *
     * @return Seznam objektů typu Event obsahující informace o všech událostech.
     *
     * @throws RuntimeException Pokud dojde k chybě při provádění SQL dotazu.
     */
    public List<Event> getAllEventFromDatabase() {
        List<Event> eventList = new ArrayList<>();
        String sql = "SELECT * FROM event";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Event event = new Event(
                            resultSet.getString("ID_EVENT"),
                            resultSet.getString("ID_SPORTOVISTE"),
                            resultSet.getString("USERNAME"),
                            resultSet.getString("NAZEV_EVENT"),
                            resultSet.getInt("KAPACITA_EVENT"),
                            resultSet.getInt("OBSAZENOST"),
                            resultSet.getTimestamp("DATUM A CAS").toLocalDateTime(),
                            resultSet.getString("KOMENTAR")
                    );
                    eventList.add(event);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return eventList;
    }

    /**
     * Metoda prihlasitEvent přihlašuje uživatele k události.
     *
     * @param creator  Uživatelské jméno přihlašujícího uživatele (tvůrce události).
     * @param ID_EVENT Identifikátor události, ke které je uživatel přihlašován.
     *
     * @throws RuntimeException Pokud dojde k chybě při provádění SQL dotazu.
     */
    public void prihlasitEvent(String creator, String ID_EVENT) {
        String sql = "INSERT INTO seznam_prihlasenych (USERNAME, ID_EVENT) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, creator);
            statement.setString(2, ID_EVENT);
            int rows = statement.executeUpdate();
            if (rows > 0) {
                //System.out.println("Event has been updated");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metoda isUzivatelPrihlasen zjišťuje, zda je uživatel přihlášen k dané události.
     *
     * @param username Uživatelské jméno uživatele.
     * @param idEvent  Identifikátor události.
     *
     * @return True, pokud je uživatel přihlášen k události; jinak false.
     *
     * @throws RuntimeException Pokud dojde k chybě při provádění SQL dotazu.
     */
    public boolean isUzivatelPrihlasen(String username, String idEvent) {
        String sql = "SELECT * FROM seznam_prihlasenych WHERE USERNAME = ? AND ID_EVENT = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, idEvent);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Metoda isUzivatelPrihlasen2 zjišťuje, zda je uživatel přihlášen k dané události.
     * Tato verze metody provádí dotaz přímo na tabulku "event" a porovnává uživatelské jméno a identifikátor události.
     *
     * @param username Uživatelské jméno uživatele.
     * @param idEvent  Identifikátor události.
     *
     * @return True, pokud je uživatel přihlášen k události; jinak false.
     *
     * @throws RuntimeException Pokud dojde k chybě při provádění SQL dotazu.
     */
    public boolean isUzivatelPrihlasen2(String username, String idEvent) {
        String sql = "SELECT * FROM event WHERE USERNAME = ? AND ID_EVENT = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, idEvent);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Metoda odhlasitUzivateleZEventu odhlašuje uživatele z dané události.
     *
     * @param username Uživatelské jméno uživatele.
     * @param idEvent  Identifikátor události.
     *
     * @return True, pokud byl uživatel úspěšně odhlášen z události; jinak false.
     *
     * @throws RuntimeException Pokud dojde k chybě při provádění SQL dotazu.
     */
    public boolean odhlasitUzivateleZEventu(String username, String idEvent) {
        String sql = "DELETE FROM seznam_prihlasenych WHERE USERNAME = ? AND ID_EVENT = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            statement.setString(2, idEvent);
            int rows = statement.executeUpdate();
            if (rows > 0) {
                //System.out.println("User has been unregistered from the event");
                return true;
            } else {
                //System.out.println("User was not registered for the event");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Metoda zvyseniObsazenostiOJedna zvyšuje obsazenost dané události o jedna.
     *
     * @param idEvent Identifikátor události, pro kterou se má zvýšit obsazenost.
     *
     * @throws RuntimeException Pokud dojde k chybě při provádění SQL dotazu.
     */
    public void zvyseniObsazenostiOJedna(String idEvent) {
        String sql = "UPDATE event SET OBSAZENOST = OBSAZENOST + 1 WHERE ID_EVENT = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, idEvent);
            int rows = statement.executeUpdate();
            if (rows > 0) {
                //System.out.println("Obsazenost has been increased for the event");
            } else {
                //System.out.println("Failed to increase obsazenost for the event");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metoda snizeniObsazenostiOJedna snižuje obsazenost dané události o jedna.
     *
     * @param idEvent Identifikátor události, pro kterou se má snížit obsazenost.
     *
     * @throws RuntimeException Pokud dojde k chybě při provádění SQL dotazu.
     */
    //Tohle používat při odhlášení z eventu
    public void snizeniObsazenostiOJedna(String idEvent) {
        String sql = "UPDATE event SET OBSAZENOST = OBSAZENOST - 1 WHERE ID_EVENT = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, idEvent);
            int rows = statement.executeUpdate();
            if (rows > 0) {
                //System.out.println("Obsazenost has been decreased for the event");
            } else {
                //System.out.println("Failed to decrease obsazenost for the event");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metoda getNazevSportovisteById získává název sportoviště na základě jeho identifikátoru.
     *
     * @param idSportoviste Identifikátor sportoviště.
     *
     * @return Název sportoviště nebo null, pokud sportoviště s daným identifikátorem není nalezeno.
     *
     * @throws RuntimeException Pokud dojde k chybě při provádění SQL dotazu.
     */
    public String getNazevSportovisteById(String idSportoviste) {
        String nazevSportoviste = null;
        String sql = "SELECT NAZEV_SPORTOVISTE FROM sportoviste WHERE ID_SPORTOVISTE = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, idSportoviste);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    nazevSportoviste = resultSet.getString("NAZEV_SPORTOVISTE");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nazevSportoviste;
    }

    /**
     * Metoda getKapacitaSportovisteById získává kapacitu sportoviště na základě jeho identifikátoru.
     *
     * @param idSportoviste Identifikátor sportoviště.
     *
     * @return Kapacita sportoviště nebo 0, pokud sportoviště s daným identifikátorem není nalezeno.
     *
     * @throws RuntimeException Pokud dojde k chybě při provádění SQL dotazu.
     */
    public int getKapacitaSportovisteById(String idSportoviste) {
        int kapacita = 0;
        String sql = "SELECT KAPACITA_SPORTOVISTE FROM sportoviste WHERE ID_SPORTOVISTE = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, idSportoviste);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    kapacita = resultSet.getInt("KAPACITA_SPORTOVISTE");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return kapacita;
    }
}

