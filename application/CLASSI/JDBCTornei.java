package application.CLASSI;

import java.awt.AWTException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCTornei {

    public static void creaTabellaTornei() {
        Connection conn = null;
        Statement statement = null;

        try {
            conn = DriverManager.getConnection("jdbc:sqlite:database/torneo.db");
            statement = conn.createStatement();
            String createTableQuery = "CREATE TABLE IF NOT EXISTS torneo (" +
                    "codice TEXT," +
                    "giocatori TEXT," +
                    "vincitori TEXT," +
                    "quarti TEXT," +
                    "semi TEXT," +
                    "finale TEXT)";
                    
            statement.execute(createTableQuery);

            System.out.println("Tabella 'tornei' creata correttamente.");
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
    
    public static void inserisciTorneo(Torneo torneo) {
        String insertTorneoSQL = "INSERT INTO torneo (codice, giocatori, vincitori, quarti, semi, finale) VALUES (?, ?, ? ,?, ?, ?)";

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DriverManager.getConnection("jdbc:sqlite:database/torneo.db");
            pstmt = conn.prepareStatement(insertTorneoSQL);

            pstmt.setString(1, torneo.getCodice());
            pstmt.setString(2, torneo.getGiocatoriString());
            pstmt.setString(3, torneo.getVincitoriString());
            pstmt.setString(4, torneo.getCodiciQuarti());
            pstmt.setString(5, torneo.getCodiciSemi());
            pstmt.setString(6, "");

            pstmt.executeUpdate();

            System.out.println("Torneo inserito correttamente.");                           
                            
            for(Partita x:torneo.getPartite()) {
                JDBCPartite.inserisciPartita(x);                    
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

    public static void aggiornaTorneo(Torneo torneo) {
        String updateTorneoSQL = "UPDATE torneo SET giocatori=?, vincitori=?, quarti=?, semi=?, finale=? WHERE codice=?";

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DriverManager.getConnection("jdbc:sqlite:database/torneo.db");
            pstmt = conn.prepareStatement(updateTorneoSQL);

            pstmt.setString(1, torneo.getGiocatoriString());
            pstmt.setString(2, torneo.getVincitoriString());
            pstmt.setString(3, torneo.getCodiciQuarti());
            pstmt.setString(4, torneo.getCodiciSemi());
            pstmt.setString(5, torneo.getCodiceFinale());
            pstmt.setString(6, torneo.getCodice());

            pstmt.executeUpdate();

            System.out.println("Torneo aggiornato correttamente.");
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
    
    public static void eliminaTorneo(String codiceTorneo) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DriverManager.getConnection("jdbc:sqlite:database/torneo.db");
            String deleteTorneoSQL = "DELETE FROM torneo WHERE codice = ?";
            pstmt = conn.prepareStatement(deleteTorneoSQL);
            pstmt.setString(1, codiceTorneo);
            int rowCount = pstmt.executeUpdate();

            if (rowCount > 0) {
                System.out.println("Torneo con codice " + codiceTorneo + " eliminato correttamente.");
            } else {
                System.out.println("Nessun torneo trovato con il codice: " + codiceTorneo);
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


	        
	    
	 
	

