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
import java.util.Collections;
import java.util.Comparator;

import javafx.scene.image.Image;

public class JDBCCarteEquipaggiamento {

    public static void creaTabellaCarteEquipaggiamento() {
        Connection conn = null;
        Statement statement = null;

        try {
            conn = DriverManager.getConnection("jdbc:sqlite:database/carteEquipaggiamento.db");
            statement = conn.createStatement();
            String createTableQuery = "CREATE TABLE IF NOT EXISTS carte_equipaggiamento (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "nome TEXT," +
                    "mitologia TEXT," +
                    "rarita TEXT," +
                    "effetto TEXT," +
                    "immagine TEXT)";
            statement.execute(createTableQuery);

            System.out.println("Tabella 'carte_equipaggiamento' creata correttamente.");
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

    public static void aggiungiCartaEquipaggiamento(String nome, String mitologia, String rarita, String effetto, String immagine) {
        

        String insertSQL = "INSERT INTO carte_equipaggiamento (nome, mitologia, rarita, effetto, immagine) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:database/carteEquipaggiamento.db");
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {

            pstmt.setString(1, nome);
            pstmt.setString(2, mitologia);
            pstmt.setString(3, rarita);
            pstmt.setString(4, effetto);
            pstmt.setString(5, immagine);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Carta equipaggiamento aggiunta con successo: " + nome);
            } else {
                System.out.println("Nessuna carta equipaggiamento aggiunta.");
            }
        } catch (Exception e) {
            gestisciEccezione(e);
        }
    }

    public static boolean cartaEquipaggiamentoEsiste(String nome) {
        String query = "SELECT COUNT(*) FROM carte_equipaggiamento WHERE nome = ?";

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:database/carteEquipaggiamento.db");
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

    public static void stampaTutteLeCarteEquipaggiamento() {
        String query = "SELECT * FROM carte_equipaggiamento";

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:database/carteEquipaggiamento.db");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String mitologia = rs.getString("mitologia");
                String rarita = rs.getString("rarita");
                String effetto = rs.getString("effetto");
                String immagine = rs.getString("immagine");

                System.out.println("ID: " + id + ", Nome: " + nome + ", Mitologia: " + mitologia +
                        ", Rarità: " + rarita + ", Effetto: " + effetto +  " Immagine: " + immagine);
            }
        } catch (Exception e) {
            gestisciEccezione(e);
        }
    }
    
    public static boolean isRunningFromJar() {
        String className = JDBCCaricamento.class.getName().replace('.', '/');
        String classJar = JDBCCaricamento.class.getResource("/" + className + ".class").toString();
        return classJar.startsWith("jar:");
    }
    
    public static void aggiornaPercorsiImmaginiEquipaggiamento() {
        String pathPc = JDBCCaricamento.getPathComputer();

        String query = "SELECT nome, immagine FROM carte_equipaggiamento";
        String updateQuery = "UPDATE carte_equipaggiamento SET immagine = ? WHERE nome = ?";

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:database/carteEquipaggiamento.db");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query);
             PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {

            while (rs.next()) {
            	String pathFinale="";
                String nome = rs.getString("nome");
                if(isRunningFromJar()) {
                String vecchioPath = rs.getString("immagine");

                pathFinale = "file:" + pathPc + "\\carte\\Equipaggiamento\\" + nome +".jpg"; 
                }
                else {
                	pathFinale ="file:"+pathPc + "\\src\\application\\Carte\\Equipaggiamento\\" + nome +".jpg";
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

    public static ArrayList<CartaEquipaggiamento> getCarteEquipaggiamentoByMitologia(String mit) {
        ArrayList<CartaEquipaggiamento> carteByMitologia = new ArrayList<>();

        String query = "SELECT * FROM carte_equipaggiamento WHERE mitologia = ?";

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:database/carteEquipaggiamento.db");
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
                CartaEquipaggiamento carta = new CartaEquipaggiamento(nome, mitologia, rar, effetto, image);
                carteByMitologia.add(carta);
            }
        } catch (Exception e) {
            gestisciEccezione(e);
        }

        return carteByMitologia;
    }
    
    public static ArrayList<CartaEquipaggiamento> getCarteEquipaggiamentoOrdinatePerMitologia() {
	    ArrayList<CartaEquipaggiamento> carteOrdinate = new ArrayList<>();

	    String query = "SELECT * FROM carte_equipaggiamento";

	    try (Connection conn = DriverManager.getConnection("jdbc:sqlite:database/carteEquipaggiamento.db");
	         Statement stmt = conn.createStatement();
	         ResultSet rs = stmt.executeQuery(query)) {

	        while (rs.next()) {
	            String nome = rs.getString("nome");
	            String mitologia = rs.getString("mitologia");
	            String rarita = rs.getString("rarita");
	            String effetto = rs.getString("effetto");
	            String immaginePath = rs.getString("immagine");
	            Image image = new Image(immaginePath);
	            TipiMitologie mit = TipiMitologie.valueOf(mitologia.toUpperCase());
                RaritaCartaGiocabile rar = RaritaCartaGiocabile.valueOf(rarita.toUpperCase());
                
	            CartaEquipaggiamento carta = new CartaEquipaggiamento(nome, mit, rar, effetto, image);
	            carteOrdinate.add(carta);
	        }
	    } catch (Exception e) {
            gestisciEccezione(e);
        }

	    // Ordina le carte per mitologia
	    Collections.sort(carteOrdinate, Comparator.comparing(carta -> carta.getMitologia()));

	    return carteOrdinate;
	}
    
    public static void aggiornaRaritaCartaEquipaggiamento(String nomeCarta, String nuovaRarita) {
        String updateQuery = "UPDATE carte_equipaggiamento SET rarita = ? WHERE nome = ?";

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:database/carteEquipaggiamento.db");
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
    
    public static void aggiornaMitologiaCartaEquipaggiamento(String nomeCarta, String nuovaMitologia) {
        String updateQuery = "UPDATE carte_equipaggiamento SET mitologia = ? WHERE nome = ?";

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:database/carteEquipaggiamento.db");
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
    
    public static void aggiornaCartaEquipaggiamento(String oldNome, String newNome, String mitologia, String rarita, String effetto, String immagine) {
        String updateSQL = "UPDATE carte_equipaggiamento SET nome = ?, mitologia = ?, rarita = ?, effetto = ?, immagine = ? WHERE nome = ?";

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:database/carteEquipaggiamento.db");
             PreparedStatement pstmt = conn.prepareStatement(updateSQL)) {

            pstmt.setString(1, newNome);
            pstmt.setString(2, mitologia);
            pstmt.setString(3, rarita);
            pstmt.setString(4, effetto);
            pstmt.setString(5, immagine);
            pstmt.setString(6, oldNome);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Carta equipaggiamento aggiornata con successo: " + newNome);
            } else {
                System.out.println("Nessuna carta equipaggiamento aggiornata.");
            }
        } catch (Exception e) {
            gestisciEccezione(e);
        }
    }
    
    public static CartaEquipaggiamento getCartaEquipaggiamentoByNome(String nomeCarta) {
        String query = "SELECT * FROM carte_equipaggiamento WHERE nome = ?";

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:database/carteEquipaggiamento.db");
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, nomeCarta);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String mitologia = rs.getString("mitologia");
                String rarita = rs.getString("rarita");
                String effetto = rs.getString("effetto");
                String immagine = rs.getString("immagine");
                Image image=new Image(immagine);
                TipiMitologie mit = TipiMitologie.valueOf(mitologia.toUpperCase());
                RaritaCartaGiocabile rar = RaritaCartaGiocabile.valueOf(rarita.toUpperCase());
                
	           return  new CartaEquipaggiamento(nomeCarta, mit, rar, effetto, image);
            }
        } catch (Exception e) {
            gestisciEccezione(e);
        }

        return null; 
    }

    public static void rimuoviCartaEquipaggiamento(String nome) {
        String deleteSQL = "DELETE FROM carte_equipaggiamento WHERE nome = ?";

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:database/carteEquipaggiamento.db");
             PreparedStatement pstmt = conn.prepareStatement(deleteSQL)) {

            pstmt.setString(1, nome);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Carta equipaggiamento rimossa con successo: " + nome);
            } else {
                System.out.println("Nessuna carta equipaggiamento rimossa. La carta potrebbe non esistere nel database.");
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
