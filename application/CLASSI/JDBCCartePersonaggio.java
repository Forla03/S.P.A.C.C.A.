package application.CLASSI;

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
import javafx.scene.image.ImageView;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.awt.AWTException;
import java.io.File;
import java.io.IOException;



public class JDBCCartePersonaggio {

	
	public static void creaTabellaCartePersonaggio() {
        Connection conn = null;
        Statement statement = null;

        try {
            conn = DriverManager.getConnection("jdbc:sqlite:database/cartePersonaggio.db");
            statement = conn.createStatement();
            String createTableQuery = "CREATE TABLE IF NOT EXISTS carte (" +
                    "nome TEXT PRIMARY KEY," +
                    "mitologia TEXT," +
                    "descrizione TEXT," +
                    "att INTEGER," +
                    "def INTEGER," +
                    "rarita TEXT," + 
                    "immaginePath TEXT)";
            statement.execute(createTableQuery);

            System.out.println("Tabella 'carte_personaggio' creata correttamente.");
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

	public static void aggiungiCartaPersonaggio(String nome, String tipo, String descrizione, int att, int def, String rarita, String immaginePath) {
	    String insertSQL = "INSERT INTO carte (nome, tipo, descrizione, att, def, rarita, immaginePath) VALUES (?, ?, ?, ?, ?, ?, ?)";

	    try (Connection conn = DriverManager.getConnection("jdbc:sqlite:database/cartePersonaggio.db");
	         PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {

	        pstmt.setString(1, nome);
	        pstmt.setString(2, tipo);
	        pstmt.setString(3, descrizione);
	        pstmt.setInt(4, att);
	        pstmt.setInt(5, def);
	        pstmt.setString(6, rarita); // Aggiunta della rarita
	        pstmt.setString(7, immaginePath);

	        int affectedRows = pstmt.executeUpdate();
	        if (affectedRows > 0) {
	            System.out.println("Carta personaggio aggiunta con successo: " + nome);
	        } else {
	            System.out.println("Nessuna carta personaggio aggiunta.");
	        }
	    } catch (Exception e) {
		       gestisciEccezione(e);
		  }
	}

	public static boolean cartaPersonaggioEsiste(String nome) {
	    String query = "SELECT COUNT(*) FROM carte WHERE nome = ?";

	    try (Connection conn = DriverManager.getConnection("jdbc:sqlite:database/cartePersonaggio.db");
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
	 
	public static void stampaTutteLeCartePersonaggio() {
	    String query = "SELECT * FROM carte";

	    try (Connection conn = DriverManager.getConnection("jdbc:sqlite:database/cartePersonaggio.db");
	         Statement stmt = conn.createStatement();
	         ResultSet rs = stmt.executeQuery(query)) {

	        while (rs.next()) {
	            String nome = rs.getString("nome");
	            String tipo = rs.getString("tipo");
	            String descrizione = rs.getString("descrizione");
	            int att = rs.getInt("att");
	            int def = rs.getInt("def");
	            String rarità= rs.getString("rarita");
	            String immaginePath = rs.getString("immaginePath");

	            System.out.println("Nome: " + nome + ", Tipo: " + tipo + ", Descrizione: " + descrizione +
	                               ", Attacco: " + att + ", Difesa: " + def + ", Rarita': " + rarità +", Percorso immagine: " + immaginePath);
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
	
	public static void aggiornaPercorsiImmaginiPersonaggio() {
		String pathPc = JDBCCaricamento.getPathComputer();		
		System.out.println(pathPc);
		
        String query = "SELECT nome, immaginePath FROM carte";
        String updateQuery = "UPDATE carte SET immaginePath = ? WHERE nome = ?";
        
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:database/cartePersonaggio.db");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query);
             PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {

            while (rs.next()) {
                String nome = rs.getString("nome");
                String pathFinale="";
                if(isRunningFromJar()) {
                	String vecchioPath = rs.getString("immaginePath");
                    pathFinale = "file:" + pathPc + "\\carte\\Personaggi\\" + nome +".jpg"; 
                }
                else {
                    pathFinale ="file:"+pathPc + "\\src\\application\\Carte\\Personaggi\\" + nome +".jpg";
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
	
	public static void aggiornaRaritaCartaPersonaggio(String nomeCarta, String nuovaRarita) {
        String updateQuery = "UPDATE carte SET rarita = ? WHERE nome = ?";

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:database/cartePersonaggio.db");
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
	
	public static void aggiornaMitologiaCartaPersonaggio(String nomeCarta, String nuovaMitologia) {
        String updateQuery = "UPDATE carte SET rarita = ? WHERE nome = ?";

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:database/cartePersonaggio.db");
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
	
	public static void rimuoviCartaPersonaggio(String nomeCarta) {
        String deleteSQL = "DELETE FROM carte WHERE nome = ?";

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:database/cartePersonaggio.db");
             PreparedStatement pstmt = conn.prepareStatement(deleteSQL)) {

            pstmt.setString(1, nomeCarta);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Carta rimossa con successo: " + nomeCarta);
            } else {
                System.out.println("Nessuna carta trovata o rimossa con il nome: " + nomeCarta);
            }
        } catch (Exception e) {
 	       gestisciEccezione(e);
 	    }
    }
	
	public static ArrayList<CartaPersonaggio> getCarteByMitologia(String mitologia) {
        ArrayList<CartaPersonaggio> carteByMitologia = new ArrayList<>();

        String query = "SELECT * FROM carte WHERE tipo = ?";

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:database/cartePersonaggio.db");
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, mitologia);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String nome = rs.getString("nome");
                String tipo = rs.getString("tipo");
                String descrizione = rs.getString("descrizione");
                int att = rs.getInt("att");
                int def = rs.getInt("def");
                String rarita = rs.getString("rarita");
                String immaginePath = rs.getString("immaginePath");
                Image image = new Image(immaginePath);
                TipiMitologie mit = TipiMitologie.valueOf(tipo.toUpperCase());
                TipoRaritaCartaPersonaggio rar = TipoRaritaCartaPersonaggio.valueOf(rarita.toUpperCase());                
                
                CartaPersonaggio carta = new CartaPersonaggio(nome, mit, descrizione, att, def, image, rar);
                carteByMitologia.add(carta);
            }
        } catch (Exception e) {
 	       gestisciEccezione(e);
 	    }

        return carteByMitologia;
    }
	
	public static ArrayList<CartaPersonaggio> getCarteOrdinatePerMitologia() {
        ArrayList<CartaPersonaggio> carteOrdinate = new ArrayList<>();

        String query = "SELECT * FROM carte";

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:database/cartePersonaggio.db");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String nome = rs.getString("nome");
                String tipo = rs.getString("tipo");
                String descrizione = rs.getString("descrizione");
                int att = rs.getInt("att");
                int def = rs.getInt("def");
                String rarita = rs.getString("rarita");
                String immaginePath = rs.getString("immaginePath");
                Image image = new Image(immaginePath);
                TipiMitologie mit = TipiMitologie.valueOf(tipo.toUpperCase());
                TipoRaritaCartaPersonaggio rar = TipoRaritaCartaPersonaggio.valueOf(rarita.toUpperCase());

                CartaPersonaggio carta = new CartaPersonaggio(nome, mit, descrizione, att, def, image, rar);
                carteOrdinate.add(carta);
            }
        } catch (Exception e) {
 	       gestisciEccezione(e);
 	    }

        Collections.sort(carteOrdinate, Comparator.comparing(carta -> carta.getMitologia().toString()));

        return carteOrdinate;
    }
	
	public static CartaPersonaggio getCartaPersonaggioByNome(String nomeCarta) {
	    String query = "SELECT * FROM carte WHERE nome = ?";

	    try (Connection conn = DriverManager.getConnection("jdbc:sqlite:database/cartePersonaggio.db");
	         PreparedStatement pstmt = conn.prepareStatement(query)) {

	        pstmt.setString(1, nomeCarta);
	        ResultSet rs = pstmt.executeQuery();

	        if (rs.next()) {
	            String tipo = rs.getString("tipo");
	            String descrizione = rs.getString("descrizione");
	            int att = rs.getInt("att");
	            int def = rs.getInt("def");
	            String rarita = rs.getString("rarita");
	            String immaginePath = rs.getString("immaginePath");
	            Image image = new Image(immaginePath);
	            TipiMitologie mit = TipiMitologie.valueOf(tipo.toUpperCase());
	            TipoRaritaCartaPersonaggio rar = TipoRaritaCartaPersonaggio.valueOf(rarita.toUpperCase());

	            return new CartaPersonaggio(nomeCarta, mit, descrizione, att, def, image, rar);
	        }
	    } catch (Exception e) {
		       gestisciEccezione(e);
		  }

	    return null; 
	}

	public static void aggiornaCartaPersonaggio(String oldName, String newName, String tipo, String descrizione, int att, int def, String rarita, String immaginePath) {
	    String updateSQL = "UPDATE carte SET nome = ?, tipo = ?, descrizione = ?, att = ?, def = ?, rarita = ?, immaginePath = ? WHERE nome = ?";

	    try (Connection conn = DriverManager.getConnection("jdbc:sqlite:database/cartePersonaggio.db");
	         PreparedStatement pstmt = conn.prepareStatement(updateSQL)) {

	        pstmt.setString(1, newName);
	        pstmt.setString(2, tipo);
	        pstmt.setString(3, descrizione);
	        pstmt.setInt(4, att);
	        pstmt.setInt(5, def);
	        pstmt.setString(6, rarita);
	        pstmt.setString(7, immaginePath);
	        pstmt.setString(8, oldName);

	        int affectedRows = pstmt.executeUpdate();
	        if (affectedRows > 0) {
	            System.out.println("Carta personaggio aggiornata con successo: " + newName);
	        } else {
	            System.out.println("Nessuna carta personaggio aggiornata.");
	        }
	    } catch (Exception e) {
	       gestisciEccezione(e);
	    }
	}
    
	public static CartaPersonaggio getPersonaggioDaImmagine(ImageView img) {
		CartaPersonaggio c=null;
		ciclo: for(CartaPersonaggio cp:JDBCCaricamento.getCartePersonaggio()) {
			if(cp.getImmagine().equals(img.getImage())) {
				c=cp;
				break ciclo;
			}				
		}
		return c;
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
