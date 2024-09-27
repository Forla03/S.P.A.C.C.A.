package application.CLASSI;

import java.awt.AWTException;
import java.io.IOException;
import java.sql.*;

public class JDBCUtenti {
    
    public static void creaTabellaGiocatori() {
        Connection conn = null;
        Statement statement = null;

        try {
            conn = DriverManager.getConnection("jdbc:sqlite:database/giocatori.db");
            statement = conn.createStatement();
            String createTableQuery = "CREATE TABLE IF NOT EXISTS giocatori (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "nome TEXT," +
                    "descrizione TEXT, " +
                    "punteggio INTEGER)";
            statement.execute(createTableQuery);

            System.out.println("Tabella 'giocatori' creata correttamente.");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
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

    public static void inserisciGiocatore(String nome, String descrizione) {
        String sql = "INSERT INTO giocatori (nome, descrizione, punteggio) VALUES (?, ?, ?)";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:database/giocatori.db");
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, nome);
            pstmt.setString(2, descrizione);
            pstmt.setInt(3, 0);
            pstmt.executeUpdate();
            System.out.println("Giocatore inserito correttamente.");
        } catch (SQLException e) {
            System.err.println("Errore durante l'inserimento del giocatore: " + e.getMessage());
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

    public static void stampaTuttiGiocatori() {
        String query = "SELECT * FROM giocatori";
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection("jdbc:sqlite:database/giocatori.db");
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String descrizione = rs.getString("descrizione");
                int punteggio=rs.getInt("punteggio");
                System.out.println("\n-----------");

                System.out.println("ID: " + id + ", Nome: " + nome + ", Descrizione: " + descrizione+ ", Punteggio: "+ punteggio);
                System.out.println("-----------\n");

            }
        } catch (Exception e) {
        	gestisciEccezione(e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
            	gestisciEccezione(e);
            }
        }
    }

    public static String stampaTuttiGiocatoriList() {
        String query = "SELECT * FROM giocatori";
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection("jdbc:sqlite:database/giocatori.db");
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);

            StringBuilder ret = new StringBuilder();
            while (rs.next()) {
                String nome = rs.getString("nome");
                ret.append(nome).append(" ");
            }
            return ret.toString();
        } catch (Exception e) {
        	gestisciEccezione(e);
            return "";
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
            	gestisciEccezione(e);
            }
        }
    }

    public static String[] getGiocatori() {
        String[] elenco = new String[getSize()];

        String query = "SELECT * FROM giocatori";
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection("jdbc:sqlite:database/giocatori.db");
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            int i = 0;
            while (rs.next()) {
                String nome = rs.getString("nome");
                elenco[i] = nome;
                i++;
            }
            return elenco;
        } catch (Exception e) {
        	gestisciEccezione(e);
            return new String[0];
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
            	gestisciEccezione(e);
            }
        }
    }

    public static int getSize() {
        int i = 0;
        String query = "SELECT * FROM giocatori";
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection("jdbc:sqlite:database/giocatori.db");
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                i++;
            }
            return i;
        } catch (Exception e) {
        	gestisciEccezione(e);
            return 0;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
            	gestisciEccezione(e);
            }
        }
    }
    
    public static String getDescrizioneDaNome(String nome) {
        String descrizione = null;

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection("jdbc:sqlite:database/giocatori.db");
            String query = "SELECT descrizione FROM giocatori WHERE nome = ?";
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, nome);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                descrizione = rs.getString("descrizione");
                System.out.println("Descrizione  dell'utente " + nome + ": " + descrizione);
            } else {
                System.out.println("Utente non trovato con il nome: " + nome);
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

        return descrizione;
    }
    
    public static void aggiornaDescrizione(String nome, String nuovaDescrizione) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DriverManager.getConnection("jdbc:sqlite:database/giocatori.db");
            String updateQuery = "UPDATE giocatori SET descrizione = ? WHERE nome = ?";
            pstmt = conn.prepareStatement(updateQuery);
            pstmt.setString(1, nuovaDescrizione);
            pstmt.setString(2, nome);
            int rowCount = pstmt.executeUpdate();

            if (rowCount > 0) {
                System.out.println("Descrizione aggiornata con successo per l'utente " + nome);
            } else {
                System.out.println("Nessun utente trovato con il nome: " + nome);
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
    
    public static Utente getUtenteDaNome(String nome) {
        Utente utente = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection("jdbc:sqlite:database/giocatori.db");
            String query = "SELECT * FROM giocatori WHERE nome = ?";
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, nome);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                String descrizione = rs.getString("descrizione");
                int punteggio=rs.getInt("punteggio");
                utente = new Utente(nome, descrizione, punteggio);
                System.out.println("Utente trovato con il nome: " + nome);
            } else {
                System.out.println("Nessun utente trovato con il nome: " + nome);
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

        return utente;
    }
    
    public static void eliminaUtente(String nome) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DriverManager.getConnection("jdbc:sqlite:database/giocatori.db");
            String deleteQuery = "DELETE FROM giocatori WHERE nome = ?";
            pstmt = conn.prepareStatement(deleteQuery);
            pstmt.setString(1, nome);
            int rowCount = pstmt.executeUpdate();

            if (rowCount > 0) {
                System.out.println("Utente eliminato con successo");
            } else {
                System.out.println("Nessun utente trovato con il nome: " + nome);
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
   
    public static void aggiornaPunti(String nome, int punti) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DriverManager.getConnection("jdbc:sqlite:database/giocatori.db");
            String updateQuery = "UPDATE giocatori SET punteggio = ? WHERE nome = ?";
            pstmt = conn.prepareStatement(updateQuery);
            pstmt.setInt(1, punti);
            pstmt.setString(2, nome);
            int rowCount = pstmt.executeUpdate();

            if (rowCount > 0) {
                System.out.println("Punteggio aggiornato con successo per l'utente " + nome);
            } else {
                System.out.println("Nessun utente trovato con il nome: " + nome);
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
