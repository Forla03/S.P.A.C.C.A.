package application.CLASSI;

import java.awt.AWTException;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


import javafx.scene.image.Image;

public class JDBCCaricamento {

    private static ArrayList<Utente> utenti = new ArrayList<>();
    private static ArrayList<CartaPersonaggio> cartePersonaggio = new ArrayList<>();
    private static ArrayList<CartaEquipaggiamento> carteEquipaggiamento = new ArrayList<>();
    private static ArrayList<CartaCura> carteCura=new ArrayList<>();
    private static ArrayList<CartaImprevisto> carteImprevisto=new ArrayList<>();
    private static ArrayList<CartaTerreno> carteTerreno=new ArrayList<>();
    private static ArrayList<CartaEffetto> carteEffetto=new ArrayList<>();
    private static ArrayList<Partita> partite= new ArrayList<>();
    private static ArrayList<Torneo> tornei=new ArrayList<>();

    private static Utente utenteNull = new Utente("Nullo", "Nullo");

    public static String getPathComputer() {
    	String jarFilePath = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        String appDirectory = new File(jarFilePath).getParent();
        
        return appDirectory;
    }
    
    public static void caricaUtenti() {
    	utenti.clear();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection("jdbc:sqlite:database/giocatori.db");
            stmt = conn.createStatement();
            String query = "SELECT * FROM giocatori";
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String descrizione = rs.getString("descrizione");
                int punteggio = rs.getInt("punteggio");
                Utente utente = new Utente(nome, descrizione, punteggio);
                System.out.println("Stampa Utente caricato: " + utente.toString());

                if (!utenti.contains(utente)) {
                    utenti.add(utente);
                }
            }
            System.out.println("\n\n");


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

    public static ArrayList<Utente> getUtenti() {
        return utenti;
    }

    public static void stampaUtenti() {
        System.out.println("Size: " + utenti.size());
        for (int i = 0; i < utenti.size(); i++) {
            System.out.println("Stampa Utenti: " + utenti.get(i));
        }

    }

    private static Utente ifContainsUtente(String nome) {
        boolean conferma = false;
        int indice = 0;
        for (int i = 0; i < utenti.size(); i++) {
            if (utenti.get(i).getNome().equals(nome)) {
                conferma = true;
                indice = i;
                break;
            }
        }
        System.out.println(conferma);
        return ifContainsUtente(conferma, indice);
    }

    private static Utente ifContainsUtente(boolean conferma, int indice) {
        if (conferma) {
            return utenti.get(indice);
        } else {
            return utenteNull;
        }
    }

    public static ArrayList<Utente> getUtentiFromList(ArrayList<String> utentiStringa) {
        ArrayList<Utente> utentiOgg = new ArrayList<Utente>();
        System.out.println(utentiStringa + "prova      prova");
        String nome = "";
        for (int i = 0; i < utentiStringa.size(); i++) {
            nome = utentiStringa.get(i);
            utentiOgg.add(ifContainsUtente(nome));
        }

        return utentiOgg;
    }
    
    /* ------------------------------------------------------------------------------------------------------
	   ------------------------------------------------------------------------------------------------------
	   										CARTE PERSONAGGIO
	   ------------------------------------------------------------------------------------------------------
	   ------------------------------------------------------------------------------------------------------
	 */

    public static void caricaCartePersonaggio() {
        cartePersonaggio.clear();
    	Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection("jdbc:sqlite:database/cartePersonaggio.db");
            stmt = conn.createStatement();
            String query = "SELECT * FROM carte";
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                String nome = rs.getString("nome");
                String mitologiaString = rs.getString("tipo");
                String descrizione = rs.getString("descrizione");
                int att = rs.getInt("att");
                int def = rs.getInt("def");
                String raritaString = rs.getString("rarita");
                String immaginePath = rs.getString("immaginePath");
                Image image = new Image(immaginePath);
                TipiMitologie mitologia = TipiMitologie.valueOf(mitologiaString.toUpperCase());
                TipoRaritaCartaPersonaggio rarita = TipoRaritaCartaPersonaggio.valueOf(raritaString.toUpperCase());
                CartaPersonaggio carta = new CartaPersonaggio(nome, mitologia, descrizione, att, def, image, rarita);
                System.out.println("Stampa Carta PERSONAGGIO Caricata: " + carta.toString());

                if (!cartePersonaggio.contains(carta)) {
                    cartePersonaggio.add(carta);
                }
            }
            System.out.println("\n\n");

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

    public static ArrayList<CartaPersonaggio> getCartePersonaggio() {
        return cartePersonaggio;
    }
    
    public static ObservableList<CartaPersonaggio> getCartePersonaggioObs(){
   	 ObservableList<CartaPersonaggio> obs= FXCollections.observableList(cartePersonaggio);
   	 return obs;
    }
    
    /* ------------------------------------------------------------------------------------------------------
	   ------------------------------------------------------------------------------------------------------
	   										CARTE EQUIPAGGIAMENTO
	   ------------------------------------------------------------------------------------------------------
	   ------------------------------------------------------------------------------------------------------
	 */

    public static void caricaCarteEquipaggiamento() {
    	carteEquipaggiamento.clear();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection("jdbc:sqlite:database/carteEquipaggiamento.db");
            stmt = conn.createStatement();
            String query = "SELECT * FROM carte_equipaggiamento";
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                String nome = rs.getString("nome");
                String mitologiaString = rs.getString("mitologia");
                String raritaString = rs.getString("rarita");
                String effetto = rs.getString("effetto");
                String immaginePath = rs.getString("immagine");
                Image image = new Image(immaginePath);
                TipiMitologie mitologia = TipiMitologie.valueOf(mitologiaString.toUpperCase());
                RaritaCartaGiocabile rarita = RaritaCartaGiocabile.valueOf(raritaString.toUpperCase());
                CartaEquipaggiamento carta = new CartaEquipaggiamento(nome, mitologia, rarita, effetto, image);

                System.out.println("Stampa Carta EQUIPAGGIAMENTO Caricata: " + carta.toString());

                if (!carteEquipaggiamento.contains(carta)) {
                	carteEquipaggiamento.add(carta);
                }
            }
           System.out.println("\n\n");

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
    
    public static ArrayList<CartaEquipaggiamento> getCarteEquipaggiamento() {
        return carteEquipaggiamento;
    }
    
    public static ObservableList<CartaEquipaggiamento> getCarteEquipaggiamentoObs(){
   	 ObservableList<CartaEquipaggiamento> obs= FXCollections.observableList(carteEquipaggiamento);
   	 return obs;
    }
    
    /* ------------------------------------------------------------------------------------------------------
	   ------------------------------------------------------------------------------------------------------
	   										CARTE CURA
	   ------------------------------------------------------------------------------------------------------
	   ------------------------------------------------------------------------------------------------------
	 */
    
    public static void caricaCarteCura() {
        carteCura.clear();
    	Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection("jdbc:sqlite:database/carteCura.db");
            stmt = conn.createStatement();
            String query = "SELECT * FROM carte_cura";
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                String nome = rs.getString("nome");
                String mitologiaString = rs.getString("mitologia");
                String raritaString = rs.getString("rarita");
                String effetto = rs.getString("effetto");
                String immaginePath = rs.getString("immagine");
                Image image = new Image(immaginePath);
                TipiMitologie mitologia = TipiMitologie.valueOf(mitologiaString.toUpperCase());
                RaritaCartaGiocabile rarita = RaritaCartaGiocabile.valueOf(raritaString.toUpperCase());
                CartaCura carta = new CartaCura(nome, mitologia, rarita, effetto, image);

                System.out.println("Stampa Carta CURA Caricata: " + carta.toString());

                if (!carteCura.contains(carta)) {
                    carteCura.add(carta);
                }
            }
            System.out.println("\n\n");

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

    public static ArrayList<CartaCura> getCarteCura() {
        return carteCura;
    }
    
    public static ObservableList<CartaCura> getCarteCuraObs(){
   	 ObservableList<CartaCura> obs= FXCollections.observableList(carteCura);
   	 return obs;
    }
    
    /* ------------------------------------------------------------------------------------------------------
	   ------------------------------------------------------------------------------------------------------
	   										CARTE EFFETTO
	   ------------------------------------------------------------------------------------------------------
	   ------------------------------------------------------------------------------------------------------
	 */
    public static void caricaCarteEffetto() {
        carteEffetto.clear();
    	Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection("jdbc:sqlite:database/carteEffetto.db");
            stmt = conn.createStatement();
            String query = "SELECT * FROM carte_effetto";
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                String nome = rs.getString("nome");
                String mitologiaString = rs.getString("mitologia");
                String raritaString = rs.getString("rarita");
                String effetto = rs.getString("effetto");
                String immaginePath = rs.getString("immagine");
                Image image = new Image(immaginePath);
                TipiMitologie mitologia = TipiMitologie.valueOf(mitologiaString.toUpperCase());
                RaritaCartaGiocabile rarita = RaritaCartaGiocabile.valueOf(raritaString.toUpperCase());
                CartaEffetto carta = new CartaEffetto(nome, mitologia, rarita, effetto, image);
                
                System.out.println("Stampa Carta EFFETTO Caricata: " + carta.toString());
                
                
                if (!carteEffetto.contains(carta)) {
                    carteEffetto.add(carta);
                }
            }
            System.out.println("\n\n");

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

    public static ArrayList<CartaEffetto> getCarteEffetto() {
        return carteEffetto;
    }
    
    public static ObservableList<CartaEffetto> getCarteEffettoObs(){
   	 ObservableList<CartaEffetto> obs= FXCollections.observableList(carteEffetto);
   	 return obs;
    }
    /* ------------------------------------------------------------------------------------------------------
    ------------------------------------------------------------------------------------------------------
                                        CARTE TERRENO
    ------------------------------------------------------------------------------------------------------
    ------------------------------------------------------------------------------------------------------
    */
	 public static void caricaCarteTerreno() {
	     carteTerreno.clear();
		 Connection conn = null;
	     Statement stmt = null;
	     ResultSet rs = null;
	
	     try {
	         conn = DriverManager.getConnection("jdbc:sqlite:database/carteTerreno.db");
	         stmt = conn.createStatement();
	         String query = "SELECT * FROM carte_terreno";
	         rs = stmt.executeQuery(query);
	
	         while (rs.next()) {
	             String nome = rs.getString("nome");
	             String tipologia = rs.getString("tipologia");
	             String raritaString = rs.getString("rarita");
	             String effetto = rs.getString("effetto");
	             String immaginePath = rs.getString("immagine");
	             Image image = new Image(immaginePath);
	             TipiMitologie mitologia= TipiMitologie.valueOf(tipologia.toUpperCase());
	             RaritaCartaGiocabile rarita = RaritaCartaGiocabile.valueOf(raritaString.toUpperCase());
	             CartaTerreno carta = new CartaTerreno(nome, mitologia, rarita, effetto, image);
	
	             System.out.println("Stampa Carta TERRENO Caricata: " + carta.toString());
	
	             if (!carteTerreno.contains(carta)) {
	                 carteTerreno.add(carta);
	             }
	         }
	         System.out.println("\n\n");
	
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

 	public static ArrayList<CartaTerreno> getCarteTerreno() {
     return carteTerreno;
 	}
 	
 	public static ObservableList<CartaTerreno> getCarteTerrenoObs(){
 		 ObservableList<CartaTerreno> obs= FXCollections.observableList(carteTerreno);
 		 return obs;
 	 }
 	
 	/* ------------------------------------------------------------------------------------------------------
    ------------------------------------------------------------------------------------------------------
                                        CARTE IMPREVISTO
    ------------------------------------------------------------------------------------------------------
    ------------------------------------------------------------------------------------------------------
   */
	 public static void caricaCarteImprevisto() {
		 carteImprevisto.clear();
		 Connection conn = null;
	     Statement stmt = null;
	     ResultSet rs = null;
	
	     try {
	         conn = DriverManager.getConnection("jdbc:sqlite:database/carteImprevisto.db");
	         stmt = conn.createStatement();
	         String query = "SELECT * FROM carte_imprevisto";
	         rs = stmt.executeQuery(query);
	
	         while (rs.next()) {
	             String nome = rs.getString("nome");
	             String mitologiaString = rs.getString("mitologia");
	             String raritaString = rs.getString("rarita");
	             String effetto = rs.getString("effetto");
	             String immaginePath = rs.getString("immagine");
	             Image image = new Image(immaginePath);
	             TipiMitologie mitologia = TipiMitologie.valueOf(mitologiaString.toUpperCase());
	             RaritaCartaGiocabile rarita = RaritaCartaGiocabile.valueOf(raritaString.toUpperCase());
	             CartaImprevisto carta = new CartaImprevisto(nome, mitologia, rarita, effetto, image);
	
	             System.out.println("Stampa Carta IMPREVISTO Caricata: " + carta.toString());
	
	             if (!carteImprevisto.contains(carta)) {
	                 carteImprevisto.add(carta);
	             }
	         }
	         System.out.println("\n\n");
	
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
	
	 public static ArrayList<CartaImprevisto> getCarteImprevisto() {
	     return carteImprevisto;
	 }
	 
	 public static ObservableList<CartaImprevisto> getCarteImprevistoObs(){
		 ObservableList<CartaImprevisto> obs= FXCollections.observableList(carteImprevisto);
		 return obs;
	 }
	 
	
	
	 
	 public static void caricaPartita() throws AWTException {
		    partite.clear();
		    Connection conn = null;
		    Statement stmt = null;
		    ResultSet rs = null;
		    try {
		        conn = DriverManager.getConnection("jdbc:sqlite:database/partite.db");
		        stmt = conn.createStatement();
		        String query = "SELECT * FROM partite";
		        rs = stmt.executeQuery(query);
		        String codice;
		        int numGiocatori;
		        String[]nomiGiocatori;
		        String[] mazzo;
		        int turnoGiocatore;
		        int turnoTotale;
		        String timeline;
		        String[] carteManoGioc1;
		        String[] carteManoGioc2;
		        String[] carteManoGioc3;
		        String[] carteManoGioc4;
		        HashMap<String,ArrayList<String>>cartePg1Equip=new HashMap<String,ArrayList<String>>();
		        HashMap<String,int[]>cartePg1Attributi=new HashMap<String,int[]>();
		        ArrayList<String>cartePg1=new ArrayList<String>();
		        HashMap<String,ArrayList<String>>cartePg2Equip=new HashMap<String,ArrayList<String>>();
		        HashMap<String,int[]>cartePg2Attributi=new HashMap<String,int[]>();
		        ArrayList<String>cartePg2=new ArrayList<String>();
		        HashMap<String,ArrayList<String>>cartePg3Equip=new HashMap<String,ArrayList<String>>();
		        HashMap<String,int[]>cartePg3Attributi=new HashMap<String,int[]>();
		        ArrayList<String>cartePg3=new ArrayList<String>();
		        HashMap<String,ArrayList<String>>cartePg4Equip=new HashMap<String,ArrayList<String>>();
		        HashMap<String,int[]>cartePg4Attributi=new HashMap<String,int[]>();
		        ArrayList<String>cartePg4=new ArrayList<String>();
		        String carteTerrGioc1;
		        String carteTerrGioc2;
		        String carteTerrGioc3;
		        String carteTerrGioc4;        
	
		        while (rs.next()) {
		        	codice=rs.getString("codice");
		        	numGiocatori=rs.getInt("numGiocatori");
		            nomiGiocatori=rs.getString("nomiGiocatori").split(" ");
		            mazzo=rs.getString("mazzo").split("\\|");
		            turnoGiocatore=rs.getInt("turnoGiocatore");
		            turnoTotale=rs.getInt("turnoTotale");
		            timeline=rs.getString("timeline");
		            carteManoGioc1=rs.getString("carteManoGioc1").split("\\|");
		            carteManoGioc2=rs.getString("carteManoGioc2").split("\\|");
		            carteManoGioc3=rs.getString("carteManoGioc3").split("\\|");
		            carteManoGioc4=rs.getString("carteManoGioc4").split("\\|");
		            
		            String[]cartePg1Temp=rs.getString("cartePersGioc1").split("\\|");		            
		            for(int i=0;i<cartePg1Temp.length;i++) {
		            	String[]temp=cartePg1Temp[i].split("/");
		            	cartePg1.add(temp[0]);
		            	cartePg1Attributi.put(temp[0],new int[2]);
		            	if(temp.length>1) {
		            	cartePg1Attributi.get(temp[0])[0]=Integer.parseInt(temp[1]);
		            	cartePg1Attributi.get(temp[0])[1]=Integer.parseInt(temp[2]);
		            	}
		            	cartePg1Equip.put(temp[0], new ArrayList<String>());
		            	if(temp.length>3) {
		            		for(int z=3;z<temp.length;z++) {
		            			cartePg1Equip.get(temp[0]).add(temp[z]);
		            		}
		            	}
		            } 
		            String[]cartePg2Temp=rs.getString("cartePersGioc2").split("\\|");		            
		            for(int i=0;i<cartePg2Temp.length;i++) {
		            	String[]temp=cartePg2Temp[i].split("/");
		            	cartePg2.add(temp[0]);
		            	cartePg2Attributi.put(temp[0],new int[2]);
		            	if(temp.length>1) {
		            	cartePg2Attributi.get(temp[0])[0]=Integer.parseInt(temp[1]);
		            	cartePg2Attributi.get(temp[0])[1]=Integer.parseInt(temp[2]);
		            	}
		            	cartePg2Equip.put(temp[0], new ArrayList<String>());
		            	if(temp.length>3) {
		            		for(int z=3;z<temp.length;z++) {
		            			cartePg2Equip.get(temp[0]).add(temp[z]);
		            		}
		            	}
		            } 
		            
		            String[]cartePg3Temp=rs.getString("cartePersGioc3").split("\\|");		            
		            for(int i=0;i<cartePg3Temp.length;i++) {
		            	String[]temp=cartePg3Temp[i].split("/");
		            	cartePg3.add(temp[0]);
		            	cartePg3Attributi.put(temp[0],new int[2]);
		            	if(temp.length>1) {
		            	cartePg3Attributi.get(temp[0])[0]=Integer.parseInt(temp[1]);
		            	cartePg3Attributi.get(temp[0])[1]=Integer.parseInt(temp[2]);
		            	}
		            	cartePg3Equip.put(temp[0], new ArrayList<String>());
		            	if(temp.length>3) {
		            		for(int z=3;z<temp.length;z++) {
		            			cartePg3Equip.get(temp[0]).add(temp[z]);
		            		}
		            	}
		            } 
		            
		            String[]cartePg4Temp=rs.getString("cartePersGioc4").split("\\|");		            
		            for(int i=0;i<cartePg4Temp.length;i++) {
		            	String[]temp=cartePg4Temp[i].split("/");
		            	cartePg4.add(temp[0]);
		            	cartePg4Attributi.put(temp[0],new int[2]);
		            	if(temp.length>1) {
		            	cartePg4Attributi.get(temp[0])[0]=Integer.parseInt(temp[1]);
		            	cartePg4Attributi.get(temp[0])[1]=Integer.parseInt(temp[2]);
		            	}
		            	cartePg4Equip.put(temp[0], new ArrayList<String>());
		            	if(temp.length>3) {
		            		for(int z=3;z<temp.length;z++) {
		            			cartePg4Equip.get(temp[0]).add(temp[z]);
		            		}
		            	}
		            } 
		        carteTerrGioc1=rs.getString("carteTerrGioc1").split("\\|")[0];  
		        carteTerrGioc2=rs.getString("carteTerrGioc2").split("\\|")[0];   
		        carteTerrGioc3=rs.getString("carteTerrGioc3").split("\\|")[0];  
		        carteTerrGioc4=rs.getString("carteTerrGioc4").split("\\|")[0]; 	        
		        Partita partita=new Partita(codice,numGiocatori,nomiGiocatori,mazzo,turnoGiocatore,turnoTotale,timeline,carteManoGioc1,carteManoGioc2,carteManoGioc3,carteManoGioc4,cartePg1Equip,cartePg1Attributi,cartePg1,cartePg2Equip,cartePg2Attributi,cartePg2,cartePg3Equip,cartePg3Attributi,cartePg3,cartePg4Equip,cartePg4Attributi,cartePg4,carteTerrGioc1, carteTerrGioc2, carteTerrGioc3, carteTerrGioc4);
		        if(partita.getCodice().charAt(0)=='!')
		        	partita.torneoOn();
		        
		        if (!partite.contains(partita)) {
	                partite.add(partita);
	                if(partita.getMazzo().isEmpty()) {
	                	partita.inizializzaMazzo();
	                }
	                	
	            }
		              
		        
		        cartePg1Attributi.clear();
		        cartePg1Equip.clear();
		        cartePg1.clear();
		        cartePg2Attributi.clear();
		        cartePg2Equip.clear();
		        cartePg2.clear();
		        cartePg3Attributi.clear();
		        cartePg3Equip.clear();
		        cartePg3.clear();
		        cartePg4Attributi.clear();
		        cartePg4Equip.clear();
		        cartePg4.clear();
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
    
    public static ArrayList<Partita>getPartite(){
    	return partite;
    }

    /* ------------------------------------------------------------------------------------------------------
    ------------------------------------------------------------------------------------------------------
											TORNEO
    ------------------------------------------------------------------------------------------------------
    ------------------------------------------------------------------------------------------------------
   */
    public static void caricaTornei() throws AWTException {
    	tornei.clear();
        String selectTorneiSQL = "SELECT codice, giocatori,vincitori, quarti, semi, finale FROM torneo";

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection("jdbc:sqlite:database/torneo.db");
            stmt = conn.createStatement();
            rs = stmt.executeQuery(selectTorneiSQL);
            ArrayList<Utente> giocatori=new ArrayList<>();
            ArrayList<Utente> vincitori=new ArrayList<>();
            while (rs.next()) {
                String codice = rs.getString("codice");
                String giocatoriString = rs.getString("giocatori");
                String vincitoriString = rs.getString("vincitori");
                String quarti[] = rs.getString("quarti").split(",");
                String semi[] = rs.getString("semi").split(",");
                String finale = rs.getString("finale");
                
                String[] g=giocatoriString.split(",");
                for(int i=0; i<g.length; i++) {
                	Utente u=JDBCUtenti.getUtenteDaNome(g[i]);
                	if(u!=null) {
                	  giocatori.add(u);
                	}
                	else if(u==null && g[i].length()>=3) {
                		if(g[i].substring(0, 3).equals("BOT"))
                			giocatori.add(new RobotIntelligente(g[i],""));
                	}
                }
                String[] v=vincitoriString.split(",");
                for(int i=0; i<v.length; i++) {
                	Utente u=JDBCUtenti.getUtenteDaNome(v[i]);
                	if(u!=null) {
                	  vincitori.add(u);
                	}
                	else if(u==null && v[i].length()>=3) {
                		 if(v[i].substring(0, 3).equals("BOT")) {
                		    for(Utente utente:giocatori) {
                			  if(utente.getNome().equals(v[i]))
                				vincitori.add(utente);
                		}
                	  }
                	}
                }
                
                Torneo torneo = new Torneo(giocatori, codice, vincitori);
                ArrayList<Partita> temp=new ArrayList<Partita>();
                if(quarti!=null) {
                for(int i=0;i<quarti.length;i++) {
                	if(JDBCPartite.getPartitaDaCodice(quarti[i])!=null)
                		temp.add(JDBCPartite.getPartitaDaCodice(quarti[i]));
                 }
                }
                if(semi!=null) {
                    for(int i=0;i<semi.length;i++) {
                    	if(JDBCPartite.getPartitaDaCodice(semi[i])!=null)
                    		temp.add(JDBCPartite.getPartitaDaCodice(semi[i]));
                   }
                 }
                if(finale!="") {
                	if(JDBCPartite.getPartitaDaCodice(finale)!=null)
                		temp.add(JDBCPartite.getPartitaDaCodice(finale));
                }
                
                torneo.setPartite(temp);
                giocatori.clear();
                vincitori.clear();
                
                tornei.add(torneo);
            }

            System.out.println("Tornei caricati correttamente.");
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
    
    public static ArrayList<Torneo> getTornei(){
    	return tornei;
    }
    
    private static void gestisciEccezione(Exception e) {
        if (e instanceof AWTException) {
            System.out.println("Si è verificata un'eccezione AWT: " + e.getMessage());
        } else if (e instanceof IOException) {
            System.out.println("Si è verificata un'eccezione di I/O: " + e.getMessage());
        } else if (e instanceof NullPointerException) {
            System.out.println("Si è verificata una eccezione NullPointer: " + e.getMessage());
        } else if(e instanceof ArrayIndexOutOfBoundsException) {
        	System.out.println("Si è verificata una eccezione OutOfBounds: " + e.getMessage());
        }
        else if (e instanceof SQLException) {
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
	
