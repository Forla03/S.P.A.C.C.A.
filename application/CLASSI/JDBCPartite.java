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

public class JDBCPartite {
   
	public static void creaTabellaPartite() {
        Connection conn = null;
        Statement statement = null;

        try {
            conn = DriverManager.getConnection("jdbc:sqlite:database/partite.db");
            statement = conn.createStatement();
            String createTableQuery = "CREATE TABLE IF NOT EXISTS partite (" +
                    "codice TEXT PRIMARY KEY," +
                    "numGiocatori INTEGER," +
                    "nomiGiocatori TEXT," + 
                    "mazzo TEXT," +
                    "turnoGiocatore INTEGER," +
                    "turnoTotale INTEGER," +
                    "timeline TEXT," +
                    "carteManoGioc1 TEXT," +
                    "carteManoGioc2 TEXT," +
                    "carteManoGioc3 TEXT," +
                    "carteManoGioc4 TEXT," +
                    "cartePersGioc1 TEXT," +
                    "cartePersGioc2 TEXT," + 
                    "cartePersGioc3 TEXT," +
                    "cartePersGioc4 TEXT," +
                    "carteTerrGioc1 TEXT," +
                    "carteTerrGioc2 TEXT," +
                    "carteTerrGioc3 TEXT," +
                    "carteTerrGioc4 TEXT)";
            statement.execute(createTableQuery);

            System.out.println("Tabella 'partite' creata correttamente.");
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

    public static void inserisciPartita(Partita partita) {
        String insertPartitaSQL = "INSERT INTO partite (codice, numGiocatori, nomiGiocatori, mazzo, turnoGiocatore, turnoTotale, timeline, carteManoGioc1, carteManoGioc2, carteManoGioc3, carteManoGioc4, cartePersGioc1, cartePersGioc2, cartePersGioc3, cartePersGioc4, carteTerrGioc1, carteTerrGioc2, carteTerrGioc3, carteTerrGioc4 ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:database/partite.db");
            pstmt = conn.prepareStatement(insertPartitaSQL);

            pstmt.setString(1, partita.getCodice());
            pstmt.setInt(2, partita.getNumGioc());
            pstmt.setString(3, partita.getNomiGiocatoriString());
            pstmt.setString(4, "");//mazzo
            pstmt.setInt(5, 0);//turno giocatore
            pstmt.setInt(6, 0);//turno totale
            pstmt.setString(7, "");//timeline
            pstmt.setString(8, "");//carte mano giocatore 1
            pstmt.setString(9, "");//carte mano giocatore 2
            pstmt.setString(10, "");//carte mano giocatore 3
            pstmt.setString(11, "");//carte mano giocatore 4
            pstmt.setString(12, "");//carte pers giocatore 1
            pstmt.setString(13, "");//carte pers giocatore 2
            pstmt.setString(14, "");//carte pers giocatore 3
            pstmt.setString(15, "");//carte pers giocatore 4
            pstmt.setString(16, "");//carte terr giocatore 1
            pstmt.setString(17, "");//carte terr giocatore 2
            pstmt.setString(18, "");//carte terr giocatore 3
            pstmt.setString(19, "");//carte terr giocatore 4

            

            pstmt.executeUpdate();

            System.out.println("Partita inserita correttamente.");
        } catch (Exception e) {
        	gestisciEccezione(e);
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
            	gestisciEccezione(e);
            }
        }
    }
    
    public static void salvaPartitaInCorso(String codice, int numGioc, String nomiGioc, String mazzo, int turnoGioc, int turnoTot, String timeline, String carteManoGioc1, String carteManoGioc2, String carteManoGioc3, String carteManoGioc4, String cartePersGioc1, String cartePersGioc2, String cartePersGioc3, String cartePersGioc4, String carteTerrGioc1, String carteTerrGioc2, String carteTerrGioc3, String carteTerrGioc4 ) {
        Connection conn = null;
        PreparedStatement pstmtSelect = null;
        PreparedStatement pstmtUpdate = null;
        PreparedStatement pstmtInsert = null;
        
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:database/partite.db");
            
            // Check if the record already exists
            String selectPartitaSQL = "SELECT COUNT(*) FROM partite WHERE codice = ?";
            pstmtSelect = conn.prepareStatement(selectPartitaSQL);
            pstmtSelect.setString(1, codice);
            ResultSet rs = pstmtSelect.executeQuery();
            rs.next();
            int count = rs.getInt(1);           
            rs.close();
            
            if (count > 0) {
                // Update existing record
                String updatePartitaSQL = "UPDATE partite SET numGiocatori=?, nomiGiocatori=?, mazzo=?, turnoGiocatore=?, turnoTotale=?, timeline=?, carteManoGioc1=?, carteManoGioc2=?, carteManoGioc3=?, carteManoGioc4=?, cartePersGioc1=?, cartePersGioc2=?, cartePersGioc3=?, cartePersGioc4=?, carteTerrGioc1=?, carteTerrGioc2=?, carteTerrGioc3=?, carteTerrGioc4=? WHERE codice=?";
                pstmtUpdate = conn.prepareStatement(updatePartitaSQL);
                pstmtUpdate.setInt(1, numGioc);
                pstmtUpdate.setString(2, nomiGioc);
                pstmtUpdate.setString(3, mazzo);
                pstmtUpdate.setInt(4, turnoGioc);
                pstmtUpdate.setInt(5, turnoTot);
                pstmtUpdate.setString(6, timeline);
                pstmtUpdate.setString(7, carteManoGioc1);
                pstmtUpdate.setString(8, carteManoGioc2);
                pstmtUpdate.setString(9, carteManoGioc3);
                pstmtUpdate.setString(10, carteManoGioc4);
                pstmtUpdate.setString(11, cartePersGioc1);
                pstmtUpdate.setString(12, cartePersGioc2);
                pstmtUpdate.setString(13, cartePersGioc3);
                pstmtUpdate.setString(14, cartePersGioc4);
                pstmtUpdate.setString(15, carteTerrGioc1);
                pstmtUpdate.setString(16, carteTerrGioc2);
                pstmtUpdate.setString(17, carteTerrGioc3);
                pstmtUpdate.setString(18, carteTerrGioc4);
                pstmtUpdate.setString(19, codice);
                pstmtUpdate.executeUpdate();
            } else {
                // Insert new record
                String insertPartitaSQL = "INSERT INTO partite (codice, numGiocatori, nomiGiocatori, mazzo, turnoGiocatore, turnoTotale, timeline, carteManoGioc1, carteManoGioc2, carteManoGioc3, carteManoGioc4, cartePersGioc1, cartePersGioc2, cartePersGioc3, cartePersGioc4, carteTerrGioc1, carteTerrGioc2, carteTerrGioc3, carteTerrGioc4) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                pstmtInsert = conn.prepareStatement(insertPartitaSQL);
                pstmtInsert.setString(1, codice);
                pstmtInsert.setInt(2, numGioc);
                pstmtInsert.setString(3, nomiGioc);
                pstmtInsert.setString(4, mazzo);
                pstmtInsert.setInt(5, turnoGioc);
                pstmtInsert.setInt(6, turnoTot);
                pstmtInsert.setString(7, timeline);
                pstmtInsert.setString(8, carteManoGioc1);
                pstmtInsert.setString(9, carteManoGioc2);
                pstmtInsert.setString(10, carteManoGioc3);
                pstmtInsert.setString(11, carteManoGioc4);
                pstmtInsert.setString(12, cartePersGioc1);
                pstmtInsert.setString(13, cartePersGioc2);
                pstmtInsert.setString(14, cartePersGioc3);
                pstmtInsert.setString(15, cartePersGioc4);
                pstmtInsert.setString(16, carteTerrGioc1);
                pstmtInsert.setString(17, carteTerrGioc2);
                pstmtInsert.setString(18, carteTerrGioc3);
                pstmtInsert.setString(19, carteTerrGioc4);
                pstmtInsert.executeUpdate();
            }

            System.out.println("Partita salvata correttamente.");
        } catch (Exception e) {
        	gestisciEccezione(e);
        } finally {
            try {
                if (pstmtSelect != null) {
                    pstmtSelect.close();
                }
                if (pstmtUpdate != null) {
                    pstmtUpdate.close();
                }
                if (pstmtInsert != null) {
                    pstmtInsert.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
            	gestisciEccezione(e);
            }
        }
    }

    public static ArrayList<String> getUtentiDaPartita(String codicePartita) {
        ArrayList<String> utenti = new ArrayList<>();

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection("jdbc:sqlite:database/partite.db");
            String query = "SELECT nomiGiocatori FROM partite WHERE codice = ?";
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, codicePartita);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                String nomiGiocatori = rs.getString("nomiGiocatori");
                String[] nomiArray = nomiGiocatori.split(" "); // Supponendo che i nomi siano separati da virgola e spazio
                for (String nome : nomiArray) {
                	utenti.add(nome);
                }

                System.out.println("Lista utenti per la partita con codice " + codicePartita + ": " + utenti);
            } else {
                System.out.println("Partita non trovata con il codice: " + codicePartita);
            }
        } catch (Exception e) {
        	gestisciEccezione(e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
            	gestisciEccezione(e);
            }
        }

        return utenti;
    } 
    
    public static Partita getPartitaDaCodice(String codicePartita) {
    	
       Partita partita=null;
       for(Partita p:JDBCCaricamento.getPartite()) {
    	   if(p.getCodice().equals(codicePartita))
    		   partita=p;
       }
       
       if(partita==null)
    	   System.out.println("Partita non trovata");
       return partita;
       
    }
    
    public static void eliminaPartita(String codicePartita) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DriverManager.getConnection("jdbc:sqlite:database/partite.db");
            String deletePartitaSQL = "DELETE FROM partite WHERE codice = ?";
            pstmt = conn.prepareStatement(deletePartitaSQL);
            pstmt.setString(1, codicePartita);
            int rowCount = pstmt.executeUpdate();

            if (rowCount > 0) {
                System.out.println("Partita con codice " + codicePartita + " eliminata correttamente.");
            } else {
                System.out.println("Nessuna partita trovata con il codice: " + codicePartita);
            }
        } catch (Exception e) {
        	gestisciEccezione(e);
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
            	gestisciEccezione(e);
            }
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

