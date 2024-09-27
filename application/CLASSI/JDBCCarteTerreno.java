package application.CLASSI;

import java.awt.AWTException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javafx.scene.image.Image;

public class JDBCCarteTerreno {

    public static void creaTabellaCarteTerreno() {
        Connection conn = null;
        Statement statement = null;

        try {
            conn = DriverManager.getConnection("jdbc:sqlite:database/carteTerreno.db");
            statement = conn.createStatement();
            String createTableQuery = "CREATE TABLE IF NOT EXISTS carte_terreno (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "nome TEXT," +
                    "tipologia TEXT," +
                    "rarita TEXT," +
                    "effetto TEXT," +
                    "immagine TEXT)";
            statement.execute(createTableQuery);

            System.out.println("Tabella 'carte_terreno' creata correttamente.");
        } catch (Exception e) {
            gestisciEccezione(e);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.err.println("Errore durante la chiusura delle risorse: " + e.getMessage());
            }
        }
    }

    public static void aggiungiCartaTerreno(String nome, String tipologia, String rarita, String effetto, String immagine) {
        // Controlla se una carta con lo stesso nome esiste già nel database
        if (cartaTerrenoEsiste(nome)) {
            System.out.println("Una carta terreno con lo stesso nome esiste già nel database.");
            return; // Esci dal metodo senza aggiungere la carta
        }

        String insertSQL = "INSERT INTO carte_terreno (nome, tipologia, rarita, effetto, immagine) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:database/carteTerreno.db");
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {

            pstmt.setString(1, nome);
            pstmt.setString(2, tipologia);
            pstmt.setString(3, rarita);
            pstmt.setString(4, effetto);
            pstmt.setString(5, immagine);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Carta terreno aggiunta con successo: " + nome);
            } else {
                System.out.println("Nessuna carta terreno aggiunta.");
            }
        } catch (Exception e) {
            gestisciEccezione(e);
        }
    }

    public static boolean cartaTerrenoEsiste(String nome) {
        String query = "SELECT COUNT(*) FROM carte_terreno WHERE nome = ?";

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:database/carteTerreno.db");
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, nome);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;
            }
        } catch (Exception e) {
            gestisciEccezione(e);
        }

        return false;
    }
    
    public static boolean isRunningFromJar() {
        String className = JDBCCaricamento.class.getName().replace('.', '/');
        String classJar = JDBCCaricamento.class.getResource("/" + className + ".class").toString();
        return classJar.startsWith("jar:");
    }
    
    public static void aggiornaPercorsiImmaginiTerreno() {
        String pathPc = JDBCCaricamento.getPathComputer();

        String query = "SELECT nome, immagine FROM carte_terreno";
        String updateQuery = "UPDATE carte_terreno SET immagine = ? WHERE nome = ?";

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:database/carteTerreno.db");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query);
             PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {

            while (rs.next()) {
                String nome = rs.getString("nome");
                String pathFinale="";
                if(isRunningFromJar()) {
                	String vecchioPath = rs.getString("immagine");
                    pathFinale = "file:" + pathPc + "\\carte\\Altro\\" + nome +".png"; 
                } 
                else {
                	pathFinale ="file:"+pathPc + "\\src\\application\\Carte\\Altro\\" + nome +".png";
                }
                
                // Aggiorna il percorso nel database
                pstmt.setString(1, pathFinale);
                pstmt.setString(2, nome);
                pstmt.executeUpdate();
            }
            System.out.println("Percorsi delle immagini aggiornati con successo.");
        } catch (Exception e) {
            gestisciEccezione(e);
        }
    }
    
    public static ArrayList<CartaTerreno> getCarteTerrenoByTipologia(String mit) {
        ArrayList<CartaTerreno> carteByTipologia = new ArrayList<>();
        mit=mit.toUpperCase();
        String query = "SELECT * FROM carte_terreno WHERE tipologia = ?";
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:database/carteTerreno.db");
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, mit);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String nome = rs.getString("nome");
                String rarita = rs.getString("rarita");
                String effetto = rs.getString("effetto");
                String immaginePath = rs.getString("immagine");
                Image image = new Image(immaginePath);
                TipiMitologie mitologia = TipiMitologie.valueOf(mit);
                RaritaCartaGiocabile rar = RaritaCartaGiocabile.valueOf(rarita.toUpperCase());
                CartaTerreno carta = new CartaTerreno(nome, mitologia, rar, effetto, image);
                carteByTipologia.add(carta);
            }
        } catch (Exception e) {
            gestisciEccezione(e);
        }
        return carteByTipologia;
    }
    
    public static void aggiornaRaritaCartaTerreno(String nomeCarta, String nuovaRarita) {
        String updateQuery = "UPDATE carte_terreno SET rarita = ? WHERE nome = ?";

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:database/carteTerreno.db");
             PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {

            pstmt.setString(1, nuovaRarita);
            pstmt.setString(2, nomeCarta);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Rarità aggiornata con successo per la carta: " + nomeCarta);
            } else {
                System.out.println("Nessun aggiornamento effettuato. Verifica il nome della carta.");
            }
        } catch (Exception e) {
            gestisciEccezione(e);
        }
    }
    
    public static void aggiornaMitologiaCartaTerreno(String nomeCarta, String nuovaMitologia) {
        String updateQuery = "UPDATE carte_terreno SET mitologia = ? WHERE nome = ?";

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:database/carteTerreno.db");
             PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {

            pstmt.setString(1, nuovaMitologia);
            pstmt.setString(2, nomeCarta);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Mitologia aggiornata con successo per la carta: " + nomeCarta);
            } else {
                System.out.println("Nessun aggiornamento effettuato. Verifica il nome della carta.");
            }
        } catch (Exception e) {
            gestisciEccezione(e);
        }
    }
    
    public static void aggiornaCartaTerreno(String oldNome, String newNome, String tipologia, String rarita, String effetto, String immagine) {
        String updateSQL = "UPDATE carte_terreno SET nome = ?, tipologia = ?, rarita = ?, effetto = ?, immagine = ? WHERE nome = ?";

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:database/carteTerreno.db");
             PreparedStatement pstmt = conn.prepareStatement(updateSQL)) {

            pstmt.setString(1, newNome);
            pstmt.setString(2, tipologia);
            pstmt.setString(3, rarita);
            pstmt.setString(4, effetto);
            pstmt.setString(5, immagine);
            pstmt.setString(6, oldNome);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Carta terreno aggiornata con successo: " + newNome);
            } else {
                System.out.println("Nessuna carta terreno aggiornata.");
            }
        } catch (Exception e) {
            gestisciEccezione(e);
        }
    }

    public static CartaTerreno getCartaTerrenoByNome(String nomeCarta) {
        String query = "SELECT * FROM carte_terreno WHERE nome = ?";

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:database/carteTerreno.db");
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, nomeCarta);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String tip = rs.getString("tipologia");
                String rarita = rs.getString("rarita");
                String effetto = rs.getString("effetto");
                String immagine = rs.getString("immagine");
                Image image = new Image(immagine);
                TipiMitologie tipologia = TipiMitologie.valueOf(tip.toUpperCase());
                RaritaCartaGiocabile rar = RaritaCartaGiocabile.valueOf(rarita.toUpperCase());

                CartaTerreno carta = new CartaTerreno(nomeCarta, tipologia, rar, effetto, image);
                return carta;
            }
        } catch (Exception e) {
            gestisciEccezione(e);
        }

        return null; 
    }
    
    public static void rimuoviCartaTerreno(String nome) {
        String deleteSQL = "DELETE FROM carte_terreno WHERE nome = ?";

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:database/carteTerreno.db");
             PreparedStatement pstmt = conn.prepareStatement(deleteSQL)) {

            pstmt.setString(1, nome);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Carta terreno rimossa con successo: " + nome);
            } else {
                System.out.println("Nessuna carta terreno rimossa. La carta potrebbe non esistere nel database.");
            }
        } catch (Exception e) {
            gestisciEccezione(e);
        }
    }
    
    private static void gestisciEccezione(Exception e) {
        if (e instanceof AWTException) {
            System.out.println("Si è verificata un'eccezione AWT: " + e.getMessage());
        } else if (e instanceof IOException) {
            System.out.println("Si è verificata un'eccezione di I/O: " + e.getMessage());
        } else if (e instanceof SQLException) {
            SQLException sqlException = (SQLException) e;
            while (sqlException != null) {
                System.err.println("SQLState: " + sqlException.getSQLState());
                System.err.println("ErrorCode: " + sqlException.getErrorCode());
                System.err.println("Messaggio: " + sqlException.getMessage());
                sqlException = sqlException.getNextException();
            }
        } else {
            System.out.println("Si è verificata un'eccezione generica: " + e.getMessage());
            e.printStackTrace();
        }
    }
}