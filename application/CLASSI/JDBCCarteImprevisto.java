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

public class JDBCCarteImprevisto {

    public static void creaTabellaCarteImprevisto() {
        Connection conn = null;
        Statement statement = null;

        try {
            conn = DriverManager.getConnection("jdbc:sqlite:database/carteImprevisto.db");
            statement = conn.createStatement();
            String createTableQuery = "CREATE TABLE IF NOT EXISTS carte_imprevisto (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "nome TEXT," +
                    "mitologia TEXT," +
                    "rarita TEXT," +
                    "effetto TEXT," +
                    "immagine TEXT)";
            statement.execute(createTableQuery);

            System.out.println("Tabella 'carte_imprevisto' creata correttamente.");
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
            } catch (Exception e) {
                gestisciEccezione(e);
            }
        }
    }

    public static void aggiungiCartaImprevisto(String nome, String mitologia, String rarita, String effetto, String immagine) {
        // Controlla se una carta con lo stesso nome esiste già nel database
        if (cartaImprevistoEsiste(nome)) {
            System.out.println("Una carta imprevisto con lo stesso nome esiste già nel database.");
            return; // Esci dal metodo senza aggiungere la carta
        }

        String insertSQL = "INSERT INTO carte_imprevisto (nome, mitologia, rarita, effetto, immagine) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:database/carteImprevisto.db");
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {

            pstmt.setString(1, nome);
            pstmt.setString(2, mitologia);
            pstmt.setString(3, rarita);
            pstmt.setString(4, effetto);
            pstmt.setString(5, immagine);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Carta imprevisto aggiunta con successo: " + nome);
            } else {
                System.out.println("Nessuna carta imprevisto aggiunta.");
            }
        } catch (Exception e) {
            gestisciEccezione(e);
         }
    }

    public static boolean cartaImprevistoEsiste(String nome) {
        String query = "SELECT COUNT(*) FROM carte_imprevisto WHERE nome = ?";

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:database/carteImprevisto.db");
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
    
    public static void aggiornaPercorsiImmaginiImprevisto() {
        String pathPc = JDBCCaricamento.getPathComputer();

        String query = "SELECT nome, immagine FROM carte_imprevisto";
        String updateQuery = "UPDATE carte_imprevisto SET immagine = ? WHERE nome = ?";

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:database/carteImprevisto.db");
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
    
    public static ArrayList<CartaImprevisto> getCarteImprevistoByMitologia(String mit) {
        ArrayList<CartaImprevisto> carteByMitologia = new ArrayList<>();

        String query = "SELECT * FROM carte_imprevisto WHERE mitologia = ?";

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:carteImprevisto.db");
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, mit);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String nome = rs.getString("nome");
                String rarita = rs.getString("rarita");
                String effetto = rs.getString("effetto");
                String immaginePath = rs.getString("immagine");
                Image image = new Image(immaginePath);
                TipiMitologie mitologia = TipiMitologie.valueOf(mit.toUpperCase());
                RaritaCartaGiocabile rar = RaritaCartaGiocabile.valueOf(rarita.toUpperCase());

                CartaImprevisto carta = new CartaImprevisto(nome, mitologia, rar, effetto, image);
                carteByMitologia.add(carta);
            }
        } catch (Exception e) {
            gestisciEccezione(e);
         }

        return carteByMitologia;
    }
    
    public static void aggiornaRaritaCartaImprevisto(String nomeCarta, String nuovaRarita) {
        String updateQuery = "UPDATE carte_imprevisto SET rarita = ? WHERE nome = ?";

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:database/carteImprevisto.db");
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
    
    public static void aggiornaMitologiaCartaImprevisto(String nomeCarta, String nuovaMitologia) {
        String updateQuery = "UPDATE carte_imprevisto SET mitologia = ? WHERE nome = ?";

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:database/carteImprevisto.db");
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
    
    public static void aggiornaCartaImprevisto(String oldNome, String newNome, String mitologia, String rarita, String effetto, String immagine) {
        String updateSQL = "UPDATE carte_imprevisto SET nome = ?, mitologia = ?, rarita = ?, effetto = ?, immagine = ? WHERE nome = ?";

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:database/carteImprevisto.db");
             PreparedStatement pstmt = conn.prepareStatement(updateSQL)) {

            pstmt.setString(1, newNome);
            pstmt.setString(2, mitologia);
            pstmt.setString(3, rarita);
            pstmt.setString(4, effetto);
            pstmt.setString(5, immagine);
            pstmt.setString(6, oldNome);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Carta imprevisto aggiornata con successo: " + newNome);
            } else {
                System.out.println("Nessuna carta imprevisto aggiornata.");
            }
        } catch (Exception e) {
            gestisciEccezione(e);
         }
    }
    
    public static CartaImprevisto getCartaImprevistoByNome(String nomeCarta) {
        String query = "SELECT * FROM carte_imprevisto WHERE nome = ?";

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:database/carteImprevisto.db");
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, nomeCarta);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String mit = rs.getString("mitologia");
                String rarita = rs.getString("rarita");
                String effetto = rs.getString("effetto");
                String immagine = rs.getString("immagine");
                Image image = new Image(immagine);
                TipiMitologie mitologia = TipiMitologie.valueOf(mit.toUpperCase());
                RaritaCartaGiocabile rar = RaritaCartaGiocabile.valueOf(rarita.toUpperCase());

                CartaImprevisto carta = new CartaImprevisto(nomeCarta, mitologia, rar, effetto, image);
                return carta;
                }
        } catch (Exception e) {
            gestisciEccezione(e);
         }

        return null; 
    }
    
    public static void rimuoviCartaImprevisto(String nome) {
        String deleteSQL = "DELETE FROM carte_imprevisto WHERE nome = ?";

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:database/carteImprevisto.db");
             PreparedStatement pstmt = conn.prepareStatement(deleteSQL)) {

            pstmt.setString(1, nome);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Carta imprevisto rimossa con successo: " + nome);
            } else {
                System.out.println("Nessuna carta imprevisto rimossa. La carta potrebbe non esistere nel database.");
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
