package application.CONTROLLER;

import java.awt.AWTException;
import java.io.IOException;
import java.io.InputStream;

import javafx.scene.input.*;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.shape.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.animation.PathTransition;
import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import java.util.concurrent.Semaphore;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Handler;
import java.util.Random;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Scene;
import application.CLASSI.*;
import javafx.scene.control.ChoiceDialog;
import javafx.animation.PauseTransition;
import javafx.scene.layout.Background;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.BackgroundFill;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;

public class ControllerPartita implements Initializable { 
	
	@FXML
	private Pane campoCorrente;
	
	@FXML
	private ImageView cartaGiocabileZoom;
	
	@FXML
	private ImageView pgCorrente1;
	
	@FXML
	private ImageView pgCorrente2;
	
	@FXML
	private ImageView pgCorrente3;
	
	@FXML
	private Label labelTurni;
	
	@FXML
	private Pane popUp;
	
	@FXML
	private Label att;
	
	@FXML
	private Label ps;
	
	@FXML
	private Label attAttuale;
	
	@FXML
	private Label psAttuali;
	
	@FXML
	private Text comunicazioneEffetto;
	
	@FXML
	private Button bottoneIncrementa;
	
	@FXML
	private Button bottoneAttacco;
	
	@FXML
	private Button bottoneSalvataggio;
	
	@FXML
	private Pane campoTotale;
	
	@FXML
	private Pane campoCarteGiocabili;
	
	@FXML
	private ImageView mazzo;
	
	@FXML
	private AnchorPane root;
	
	@FXML
	private ImageView imageViewSollevata = null;
	
	@FXML
	private ImageView cartaTerreno;	
	
	@FXML
	private Button bottoneTabellone;
	
	@FXML
	private Button bottoneSalvataggio2;
	
	@FXML
	private VBox vboxTimeline= new VBox();
	
	@FXML
	private Text time1;
	
	@FXML
	private Text time2;
	
	@FXML
	private Text time3;
	
	@FXML
	private Text time4;
	
	@FXML
	private Text time5;
	
	@FXML
	private Text time6;
	
	@FXML
	private Text time7;
	
	@FXML
	private Text pesca;
	
	@FXML
	private Button quitButton;
	
	private List<Utente>utentiOgg;
	private int turni;
	private int turniTotale;
	private CartaPersonaggio pgAttaccante;
	private CubicCurve curve;
	private CartaGiocabile tempEffetto=null;
	private static final int PAUSA_SECONDI = 3;
	private static final String ButtonBar = null;
	private Utente utenteDifensore;
	private int posizioneEliminato;
	private Pane paneSceltaAvv=new Pane();
	private List<Utente>utentiBersaglio;
	private Utente utenteBersaglio=null;	
	private boolean pgEliminato=false;     
	private String timelineS=".";
	private int contaGiocate=0;
	private int pescateQuestoTurno = 0;
		
	
	/*
	 * Metodo per creare un popUp quando si punta un personaggio
	 * Viene modificata una pane popUp in base al personaggio
	 * Vengono mostrai att, psa, attAttaule e psaAttuali e le carte equipaggiate a quel personaggio
	 */
	private void setPopUp(ImageView imageView, Utente utente) {		
		try {
        imageView.setOnMouseEntered(event -> {
            imageView.setCursor(Cursor.HAND);
            if(imageView.getLayoutX()==pgCorrente2.getLayoutX()) {
            	popUp.setLayoutX(popUp.getLayoutX()+150);
            	popUp.setStyle("-fx-background-color: #f3dbb9; -fx-border-color: black; -fx-border-width: 2; -fx-padding: 10px;");
            }
            CartaPersonaggio pg=getPersonaggioDaImmagine(imageView,utente);
            att.setText(""+pg.getAtt());
            ps.setText(""+pg.getDef());
            attAttuale.setText(""+pg.getAttAttuale());
            psAttuali.setText(""+pg.getPsAttuali());
            int pos=10;
            for(CartaEquipaggiamento c:pg.getEquip()) {
            	ImageView image=new ImageView(c.getImmagine());
            	popUp.getChildren().add(image);
            	image.setFitHeight(160);
            	image.setFitWidth(95);
            	image.setLayoutY(185);
            	image.setLayoutX(pos);
            	pos+=100;
            }
            if(!(utente instanceof RobotIntelligente))
            	campoCarteGiocabili.setVisible(false);
            popUp.setVisible(true);
            popUp.setMouseTransparent(true);
        });

        imageView.setOnMouseExited(event -> {
            imageView.setCursor(Cursor.DEFAULT);
            if(imageView.getLayoutX()==pgCorrente2.getLayoutX()) {
            	popUp.setLayoutX(popUp.getLayoutX()-150);
            }
            campoCarteGiocabili.setVisible(true);
            popUp.setVisible(false);
            Iterator<Node> iterator = popUp.getChildren().iterator();
            while (iterator.hasNext()) {
                Node node = iterator.next();
                if (node instanceof ImageView) {
                    iterator.remove();
                }
            }         
        });
		}
		catch(Exception e) {
			e.printStackTrace();
		}
    }
 
	 /*
	  * PopUp che mostra l'att e i psa di un personaggio ma non l'equipaggiamento
	  * Viene mostrato quando si apre il campo totale dove vengono mostrati i personaggi di tutti gli utenti
	  * e si punta col mouse su un personaggio
	  * 
	  */
	 private void setPopUpCampoTot(ImageView imageView, Utente utente) {
		    Pane popUpCampoTot = new Pane();
		    campoTotale.getChildren().add(popUpCampoTot);

		    imageView.setOnMouseEntered(event -> {
		        imageView.setCursor(Cursor.HAND);
		        popUpCampoTot.setVisible(true);
		        popUpCampoTot.setPrefWidth(175);
		        popUpCampoTot.setPrefHeight(125);
		        
		        Point2D imageViewCoords = imageView.localToScene(0, 0);
		        
		        popUpCampoTot.setLayoutX(imageViewCoords.getX() - popUpCampoTot.getPrefWidth() + 230);
		        popUpCampoTot.setLayoutY(imageViewCoords.getY()); 

		        popUpCampoTot.setStyle("-fx-background-color: white; -fx-text-fill: #1c4a6e;");
		        Text att2 = new Text();
		        att2.setText("Att: " + getPersonaggioDaImmagine(imageView, utente).getAtt());
		        att2.setLayoutX(10);
		        att2.setLayoutY(25);
		        Text ps2 = new Text();
		        ps2.setText("Ps: " + getPersonaggioDaImmagine(imageView, utente).getDef());
		        ps2.setLayoutX(10);
		        ps2.setLayoutY(50);
		        Text attAttuale2 = new Text();
		        attAttuale2.setText("Att attuale: " + getPersonaggioDaImmagine(imageView, utente).getAttAttuale());
		        attAttuale2.setLayoutX(10);
		        attAttuale2.setLayoutY(75);
		        Text psAttuali2 = new Text();
		        psAttuali2.setText("Ps attuali: " + getPersonaggioDaImmagine(imageView, utente).getPsAttuali());
		        psAttuali2.setLayoutY(100);
		        psAttuali2.setLayoutX(10);
		        
		        popUpCampoTot.getChildren().addAll(att2, ps2, attAttuale2, psAttuali2);
		    });

		    imageView.setOnMouseExited(event -> {
		        imageView.setCursor(Cursor.DEFAULT);
		        popUpCampoTot.setVisible(false);
		    });
		}

	/*
	 * Metodo che restituisce un oggetto cartaPersonaggio prendendo come input un immagine ed un oggetto utente
	 * Itera sull'hashmap personaggiScelti utilizzando come chiave l'utente, in questo modo itera su tutti i suoi personaggi
	 * Se l'immagine corrisponde a quella della cartaPersonaggio su cui si è ritirato, la ritorna
	 */
	 private CartaPersonaggio getPersonaggioDaImmagine(ImageView imageView, Utente utente) {
		 for (CartaPersonaggio carta : ControllerCreazionePartita.p.getPersonaggiScelti().get(utente)) {
			 if(imageView.getImage().equals(carta.getImmagine())) {
				 return carta;
			 }
		 }
			 return null;
		 }
	 
	 /*
	  * Metodo per passare il turno
	  * aggiorna l'intero turni che tiene traccia del giocatore a cui si sta facendo riferimento
	  * se supera la dimensione dell'arraylist dei giocatori utentiOgg, turni ritorna a 0
	  * Vengono effettuate altre operazioni per resettare la schermata per il prossimo giocaotre
	  * 
	  */
	 public void passaTurno(ActionEvent event) throws AWTException, IOException {
		    pesca.setVisible(false);
		    pescateQuestoTurno = 0;
		 	aggiornaTimeline();   
		 	turni += 1;
		    turniTotale+=1;
		    bottoneAttacco.setVisible(true);
		    comunicazioneEffetto.setVisible(false);
		    if(!ControllerCreazionePartita.p.getMazzo().isEmpty()) {
		    mazzo.setVisible(true);
		    }
		    if (turni >= utentiOgg.size())
		        turni = 0;
		    campoCarteGiocabili.getChildren().clear();		    		    
		    List<CartaPersonaggio> personaggiCorrenti = ControllerCreazionePartita.p.getPersonaggiScelti().get(utentiOgg.get(turni));
		    
		    if(ControllerCreazionePartita.p.getTerreno().get(utentiOgg.get(turni))!=null) {
		    ControllerCreazionePartita.p.getTerreno().get(utentiOgg.get(turni)).effetto3(utentiOgg.get(turni));		    
		    cartaTerreno.setImage(ControllerCreazionePartita.p.getTerreno().get(utentiOgg.get(turni)).getImmagine());
		    }
		    else
		    	cartaTerreno.setImage(null);
		    ricalcoloBuffEquip(utentiOgg.get(turni));
		    
		    if (personaggiCorrenti.size() >= 1) {
		        pgCorrente1.setImage(personaggiCorrenti.get(0).getImmagine());
		        setPopUp(pgCorrente1, utentiOgg.get(turni));
		        pgCorrente2.setImage(null);
		        pgCorrente3.setImage(null);
		        pgCorrente2.setVisible(false);
		        pgCorrente3.setVisible(false);
		    }
		    
		    if (personaggiCorrenti.size() >= 2) {
		    	pgCorrente1.setImage(personaggiCorrenti.get(0).getImmagine());
		        setPopUp(pgCorrente1, utentiOgg.get(turni));
		        pgCorrente2.setImage(personaggiCorrenti.get(1).getImmagine());
		        setPopUp(pgCorrente2, utentiOgg.get(turni));
		        pgCorrente3.setImage(null);
		        pgCorrente2.setVisible(true);
		        pgCorrente3.setVisible(false);
		    }
		    
		    if (personaggiCorrenti.size() >= 3) {
		    	pgCorrente1.setImage(personaggiCorrenti.get(0).getImmagine());
		        setPopUp(pgCorrente1, utentiOgg.get(turni));
		        pgCorrente2.setImage(personaggiCorrenti.get(1).getImmagine());
		        setPopUp(pgCorrente2, utentiOgg.get(turni));
		        pgCorrente3.setImage(personaggiCorrenti.get(2).getImmagine());
		        setPopUp(pgCorrente3, utentiOgg.get(turni));
		        pgCorrente2.setVisible(true);
		        pgCorrente3.setVisible(true);
		    }
		  
		    labelTurni.setText(utentiOgg.get(turni).getNome() + ", è il tuo turno");
		    contaGiocate=0;
		    aggiornamentoMano();
		    
		    if(utentiOgg.get(turni) instanceof RobotIntelligente && utentiOgg.size()>1){
		    	esecuzioneTurnoBot();
		    }
		}
	 
	 
	 /*
	  * Metodo per passare il turno nel caso in cui ci sia stato un utente eliminato
	  * 
	  */
	 public void passaTurnoEliminazione(ActionEvent event) {
		    pesca.setVisible(false);
		    pescateQuestoTurno = 0;
		   	aggiornaTimeline();
		 	bottoneAttacco.setVisible(true);
		    mazzo.setVisible(true);
		    // l'attaccante è il penultimo e il difensore non è l'ultimo
		    if(turni == utentiOgg.size()-1 && posizioneEliminato != utentiOgg.size()) {
		    	turni++;
		    	if(turni > utentiOgg.size()-1) {
		    		turni = 0;
		    	}
		    	turni++;
		    	if(turni < utentiOgg.size()-1)
		    		turni++;
		    }
		    else {
		    	turni++;
		    	if(turni > utentiOgg.size()-1) {
		    		turni = 0;
		    	}
		    }
		    campoCarteGiocabili.getChildren().clear();
		    contaGiocate=0;
		    aggiornamentoMano();		   
		    List<CartaPersonaggio> personaggiCorrenti = ControllerCreazionePartita.p.getPersonaggiScelti().get(utentiOgg.get(turni));
		    
		    if (personaggiCorrenti.size() >= 1) {
		        pgCorrente1.setImage(personaggiCorrenti.get(0).getImmagine());
		        setPopUp(pgCorrente1, utentiOgg.get(turni));
		        pgCorrente2.setImage(null);
		        pgCorrente3.setImage(null);
		        pgCorrente2.setVisible(false);
		        pgCorrente3.setVisible(false);
		    }
		    
		    if (personaggiCorrenti.size() >= 2) {
		    	pgCorrente1.setImage(personaggiCorrenti.get(0).getImmagine());
		        setPopUp(pgCorrente1, utentiOgg.get(turni));
		        pgCorrente2.setImage(personaggiCorrenti.get(1).getImmagine());
		        setPopUp(pgCorrente2, utentiOgg.get(turni));
		        pgCorrente3.setImage(null);
		        pgCorrente2.setVisible(true);
		        pgCorrente3.setVisible(false);
		    }
		    
		    if (personaggiCorrenti.size() >= 3) {
		    	pgCorrente1.setImage(personaggiCorrenti.get(0).getImmagine());
		        setPopUp(pgCorrente1, utentiOgg.get(turni));
		        pgCorrente2.setImage(personaggiCorrenti.get(1).getImmagine());
		        setPopUp(pgCorrente2, utentiOgg.get(turni));
		        pgCorrente3.setImage(personaggiCorrenti.get(2).getImmagine());
		        setPopUp(pgCorrente3, utentiOgg.get(turni));
		        pgCorrente2.setVisible(true);
		        pgCorrente3.setVisible(true);
		    }
		  
		    labelTurni.setText(utentiOgg.get(turni).getNome() + ", è il tuo turno");
		    if(utentiOgg.get(turni) instanceof RobotIntelligente){		    	
		    	esecuzioneTurnoBot();
		    }
		}
	 
	 //Metodo per gestire le azioni del bot
	 //I vari sleep sono messi per rallentarne l'esecuzione e farne vedere le azioni oltre che a sincronizzarlo con la scena in cui sta agendo
	 //*****************************
	                                              
	 private void esecuzioneTurnoBot() {		   
		    //fai debug per alcune carte effetto lato utente 		    	   
  		    Task<Void> task = new Task<Void>() {
		        @Override
		        protected Void call() throws Exception {
		            Task<Void> pescaTask = new Task<Void>() {
		                @Override
		                protected Void call() throws Exception {
		                	 try {
		         		        Thread.sleep(1000); 
		         		    } catch (InterruptedException e) {
		         		        e.printStackTrace();
		         		    }
		                    ((RobotIntelligente) utentiOgg.get(turni)).robotPesca(mazzo, null); //clicca sull'immagine per pescare
		                    return null;
		                }
		            };

		            pescaTask.setOnFailed(e -> {
		                pescaTask.getException().printStackTrace();
		                System.out.println("Errore durante l'esecuzione del metodo robotPesca");
		            });

		            Thread pescaThread = new Thread(pescaTask);
		            pescaThread.setDaemon(true);
		            pescaThread.start();

		            // attende che pesca finisca
		            try {
		                pescaThread.join();
		            } catch (InterruptedException ex) {
		                ex.printStackTrace();
		            }
		            
		            //parte in cui vengono giocate le carte
		            try {
				        Thread.sleep(1750); 
				    } catch (InterruptedException e) {
				        e.printStackTrace();
				    }
		            if (!ControllerCreazionePartita.p.getMano().get(utentiOgg.get(turni)).isEmpty()) {
		                int psTotali = 0;
		                //conta i ps totali
		                for (CartaPersonaggio c : ControllerCreazionePartita.p.getPersonaggiScelti().get(utentiOgg.get(turni)))
		                    psTotali += c.getPsAttuali();
		                
		                int contaEquip=0;
		                //conta gli equip totali
		                for (CartaPersonaggio c : ControllerCreazionePartita.p.getPersonaggiScelti().get(utentiOgg.get(turni)))
		                    contaEquip+=c.getEquip().size();
		                
		                //crea un array di 2 carte da giocare che è il massimo previsto
		                CartaGiocabile[] carteDaGiocare = new CartaGiocabile[2];
		                for (CartaGiocabile c : ControllerCreazionePartita.p.getMano().get(utentiOgg.get(turni))) {
		                	//se il totale dei ps è minore di 7000 e la carta è una cura la gioca
		                    if (c instanceof CartaCura && psTotali < 7000)
		                        carteDaGiocare[0] = c;
		                    //se ci sono personaggi senza equipaggiamento e la carta è un equipaggiamento, la gioca
		                    if (c instanceof CartaEquipaggiamento && contaEquip<(ControllerCreazionePartita.p.getPersonaggiScelti().get(utentiOgg.get(turni)).size()*3))
		                    	carteDaGiocare[1]=c;
		                    	                    
		                }
		                if(carteDaGiocare[0]==null || carteDaGiocare[1]==null) {
		                for (CartaGiocabile c : ControllerCreazionePartita.p.getMano().get(utentiOgg.get(turni))) {
		                	//se sono rimasti dei posti vuoti nelle carte da giocare ma ci sono altre carte in mano
		                	//ne gioca o una effetto o una terreno
		                	if(c instanceof CartaEffetto || c instanceof CartaTerreno) {
		                		if(carteDaGiocare[0]==null)
		                			carteDaGiocare[0]=c;
		                		if(carteDaGiocare[1]==null)
		                			carteDaGiocare[1]=c;
		                	}
		                 }
		                }
		                if(carteDaGiocare[0]==carteDaGiocare[1])
		                	carteDaGiocare[0]=null;		                
		                try {                           
					        Thread.sleep(500); 
					    } catch (InterruptedException e) {                      
					        e.printStackTrace();                                
					    }	                                                    
		                Semaphore semaphore = new Semaphore(1);
		                List<Node> copiaCarteGiocabili = new ArrayList<>(campoCarteGiocabili.getChildren());  //crea un copia per evitare
		                                                                                                     //concurrentModificationException
		                for (Node n : copiaCarteGiocabili) {
		                    if (n instanceof ImageView) {
		                    	//utilizzo un semaforo quando richiamo i metodi per giocare le carte
		                    	//entro in zona critica quando richiamo i metodi
		                    	//l'esecuzione di questi avviene contemporanemante su carteDaGiocare all'indice 0 e 1 se non uso un semaforo
		                        if (carteDaGiocare[0] != null && carteDaGiocare[0].getImmagine().equals(((ImageView) n).getImage())) {
		                        	try {
		                        	semaphore.acquire();	
		                        	
		                        	if(carteDaGiocare[0] instanceof CartaCura)
		                        		((RobotIntelligente) utentiOgg.get(turni)).giocaCura((ImageView) n, ControllerCreazionePartita.p.getMano().get(utentiOgg.get(turni)), ControllerCreazionePartita.p.getPersonaggiScelti().get(utentiOgg.get(turni)), campoCorrente);
		                        	
		                        	if(carteDaGiocare[0] instanceof CartaEquipaggiamento)
		                        		((RobotIntelligente) utentiOgg.get(turni)).giocaEquipaggiamento((ImageView) n, ControllerCreazionePartita.p.getMano().get(utentiOgg.get(turni)), ControllerCreazionePartita.p.getPersonaggiScelti().get(utentiOgg.get(turni)), campoCorrente);
		                        	
		                        	if(carteDaGiocare[0] instanceof CartaEffetto || carteDaGiocare[0] instanceof CartaTerreno)
		                        		((RobotIntelligente) utentiOgg.get(turni)).giocaEffetto((ImageView) n, ControllerCreazionePartita.p.getMano().get(utentiOgg.get(turni)), ControllerCreazionePartita.p.getPersonaggiScelti().get(utentiOgg.get(turni)), campoCorrente);		                        	 
		                        	carteDaGiocare[0]=null;
		                        	//Thread.sleep(500); 
		                        	}    
		                        	catch (InterruptedException e) {
		                                e.printStackTrace();
		                            } finally {
		                                // Rilascio del semaforo alla fine del metodo
		                                semaphore.release();
		                            }
		                        }
		                       
		                        if (carteDaGiocare[1] != null && carteDaGiocare[1].getImmagine().equals(((ImageView) n).getImage())) {
		                        	try {
		                        	semaphore.acquire();
		                        	if(carteDaGiocare[1] instanceof CartaEquipaggiamento)
		                        		((RobotIntelligente) utentiOgg.get(turni)).giocaEquipaggiamento((ImageView) n, ControllerCreazionePartita.p.getMano().get(utentiOgg.get(turni)), ControllerCreazionePartita.p.getPersonaggiScelti().get(utentiOgg.get(turni)), campoCorrente);
		                        	
		                        	if(carteDaGiocare[1] instanceof CartaEffetto || carteDaGiocare[1] instanceof CartaTerreno)
		                        		((RobotIntelligente) utentiOgg.get(turni)).giocaEffetto((ImageView) n, ControllerCreazionePartita.p.getMano().get(utentiOgg.get(turni)), ControllerCreazionePartita.p.getPersonaggiScelti().get(utentiOgg.get(turni)), campoCorrente);
		                        	//Thread.sleep(500); 
		                        	carteDaGiocare[1]=null;
		                        	}
		                        	catch (InterruptedException e) {
		                                e.printStackTrace();
		                            } finally {
		                                // Rilascio del semaforo alla fine del metodo
		                                semaphore.release();
		                            }
		                        }
		                    }
		                }
		                //una volta che le carte sono state giocate aggiorna la mano
		                Platform.runLater(() -> {
		                    aggiornamentoMano();
		                });

		            }
		            
		            /////////////////////////////////////
		            //se il bottone attacco è visibile attacca altrimenti passa il turno
		            if (bottoneAttacco.isVisible())
		                ((RobotIntelligente) utentiOgg.get(turni)).robotAttacco(campoCorrente, bottoneAttacco);
		            else
		                ((RobotIntelligente) utentiOgg.get(turni)).robotPassa(bottoneIncrementa);
		            return null;
		        }
		    };

		    task.setOnFailed(e -> {
		        task.getException().printStackTrace();
		        System.out.println("Errore durante l'esecuzione del metodo esecuzioneTurnoBot");
		    });

		    Thread thread = new Thread(task);
		    thread.setDaemon(true);
		    thread.start();
		}

	 //*************************
	
	 /*
	  * Metodo che ritorna il personaggio selezionato per l'attacco
	  */
	 private CartaPersonaggio getPersonaggioAttaccante(Utente utente, CartaPersonaggio pgAttaccante) {
		    List<CartaPersonaggio> personaggi = ControllerCreazionePartita.p.getPersonaggiScelti().get(utente);

		    for (CartaPersonaggio carta : personaggi) {
		        if (carta.equals(pgAttaccante)) {
		            return carta;
		        }
		    }
		    return null; 
		}
	 /*
	  * Metodo per effettuare i calcoli dell'attacco
	  * Applica gli effetti degli equipaggiamenti che agiscono durante l'attacco
	  * ad esempio quelli che riducono i danni o che fanno attaccare due volte
	  */
	 public void attacco(int indiceAttaccante, CartaPersonaggio pgDifensore) {
		 if(turni==utentiOgg.size())
			 turni=0;
		    if (pgDifensore != null && pgAttaccante != null) {
		        CartaPersonaggio cpAttaccante = getPersonaggioAttaccante(utentiOgg.get(turni), pgAttaccante);

		        if (cpAttaccante != null) {
		            int differenzaAttDef = cpAttaccante.getAttAttuale() - pgDifensore.getPsAttuali();
		            int riduzione=0;
		            int amplificazione=0;
		            for(CartaEquipaggiamento e:pgDifensore.getEquip()) {
		            	if(e.getTarget()==2) {
		            		riduzione+=e.effettoRiduzioneDanno(differenzaAttDef,utenteDifensore);		            		
		            	}
		            }
		            if(ControllerCreazionePartita.p.getTerreno().get(utenteDifensore)!=null)
		            	riduzione+=ControllerCreazionePartita.p.getTerreno().get(utenteDifensore).effettoRiduzioneDannoTerreno(pgDifensore);
		            for(CartaEquipaggiamento e:pgAttaccante.getEquip()) {
		            	if(e.getTarget()==2) {		            		
		            		amplificazione+=e.effettoAmplificazioneDanno(differenzaAttDef);
		            	}
		            }
		            System.out.println(riduzione+" "+amplificazione);
		            differenzaAttDef=differenzaAttDef-riduzione+amplificazione;
		            for(CartaEquipaggiamento e:pgAttaccante.getEquip()) {
		            	if(e.getTarget()==3) {	
		            	   pgDifensore.lessPs(differenzaAttDef);
		            	}
		            	if(e.getTarget()==4) {		            		
		            		e.effettoCura(differenzaAttDef, pgAttaccante);
		            	}
		            }

		            if (differenzaAttDef > 0) {
		                pgDifensore.lessPs(differenzaAttDef);
		            } else {
		                differenzaAttDef=0;
		            }
		          
		            System.out.println("Attaccante: " + cpAttaccante.getNome() + ", Difensore: " + pgDifensore.getNome() +
		                    ", Differenza Att-Def: " + differenzaAttDef + ", Ps Attuali Difensore: " + pgDifensore.getPsAttuali());
		            String infoAttacco = "Attaccante: " + cpAttaccante.getNome() + "\nDifensore: " + pgDifensore.getNome() + "\nPs Attuali Difensore: " + pgDifensore.getPsAttuali();
		           // if(!(utentiOgg.get(turni)instanceof RobotIntelligente)) {
		            mostraInfoAttacco(infoAttacco, () -> {
		                bottoneAttacco.setVisible(false);
		                pgAttaccante=null;
		            });
		            timelineS+="/" + utentiOgg.get(turni).getNome().toUpperCase() +"\nAttacco: " + cpAttaccante.getNome() + " ---> " + pgDifensore.getNome() + "\nDanni inflitti: " + differenzaAttDef; 
		        }
		    }
		}
	 
	 /*
	  *Metodo per verificare le condizioni del giocatore e del personaggio attaccato
	  *e rimuoverli nel caso soddisfino le condizioni per l'eleiminaziokne
	  */
	 public void attaccoCheck(ActionEvent event) throws IOException {		 
		    boolean utenteRimosso = false;

		    if (pgAttaccante != null && utenteDifensore != null) {
		        List<CartaPersonaggio> personaggiDifensori = ControllerCreazionePartita.p.getPersonaggiScelti().get(utenteDifensore);

		        if (!personaggiDifensori.isEmpty()) {
		            CartaPersonaggio pgDifensore = getPersonaggioCasuale(personaggiDifensori);
		            attacco(turni, pgDifensore);		                       
		            ricalcoloBuffEquip(utenteDifensore);
                  
		            if (pgDifensore.getPsAttuali() <= 0) {
		            	pgEliminato=true;
		            	posizioneEliminato = utentiOgg.indexOf(utenteDifensore);
		                CartaPersonaggio.rimuoviPersonaggio(pgDifensore, personaggiDifensori);
		                ricalcoloBuffEquip(utenteDifensore);
		                mostraEliminazionePersonaggio(pgDifensore.getNome(), utenteDifensore.getNome());
		                }
		            }

		            if (personaggiDifensori.isEmpty()) {
		                Utente.eliminaUtente(utenteDifensore, utentiOgg, personaggiDifensori);
		                utentiBersaglio.remove(utenteDifensore);
		                utenteRimosso = true;
		            }

		            if (utenteRimosso) {
		                if (utentiOgg.size() > 1) {
		                		mostraEliminazioneUtente(utenteDifensore.getNome(), () -> {
		                    	passaTurnoEliminazione(null);
		                    });
		                    return;
		                
		                } else {
		                	if(utentiOgg.size() == 1) {		                		
		                		labelTurni.setText("Complimenti, " + utentiOgg.get(0).getNome() + " hai vinto!");
	                			bottoneIncrementa.setVisible(false);
	                			bottoneAttacco.setVisible(false);
	                			if(!ControllerCreazionePartita.p.getTorneo()) {
	                				utentiOgg.get(0).addPunti(25);
	                				JDBCUtenti.aggiornaPunti(utentiOgg.get(0).getNome(), utentiOgg.get(0).getPunteggio());
	                			}
	                			if(ControllerCreazionePartita.p.getTorneo()) {	
	                				utentiOgg.get(0).addPunti(10);
	                				JDBCUtenti.aggiornaPunti(utentiOgg.get(0).getNome(), utentiOgg.get(0).getPunteggio());
	                				ControllerCreazionePartita.t.getVincitori().add(utentiOgg.get(0));
	                				ControllerCreazionePartita.t.getPartite().remove(ControllerCreazionePartita.p);
	                				bottoneTabellone.setVisible(true);
	                				
	                			}
	                			int turniVerifica=turni;
			                	if(turni==utentiOgg.size())
			                		turniVerifica=turni-1;
	                			if(!(utentiOgg.get(turniVerifica)instanceof RobotIntelligente)) {
	                			
		                		mostraEliminazioneUtente(utenteDifensore.getNome(), () -> {
		                			
		                    });
		                    return;
	                			}
		                	}	                	
		                }
		            }
		            comunicazioneEffetto.setVisible(false);
		        }
		    else {
		        System.out.println("Nessun personaggio selezionato per l'attacco");          
		        return; }
		    comunicazioneEffetto.setVisible(false);
		    bottoneAttacco.setVisible(false);
		    try {
		    	if(turni==utentiOgg.size()) 
		    		turni=0;		    	
		        if (!(utentiOgg.get(turni) instanceof RobotIntelligente) && utentiOgg.size()>1) {
		            passaTurno(null);
		        } else {
		            PauseTransition pause = new PauseTransition(Duration.seconds(2));
		            if (pgEliminato) {
		                pause.setDuration(Duration.seconds(5));
		                pgEliminato = false;
		            }

		            if (utenteRimosso) {		            	
		                pause.setDuration(Duration.seconds(7));
		            }
		           

		            pause.setOnFinished(event2 -> {  
		                try {		                	 
		                	
		                    if (utentiOgg.size() > 1) {
		                        passaTurno(null);
		                    } else {
		                    	   return;             					                      
		                    }
		                } catch (AWTException | IOException ex) {
		                    ex.printStackTrace();
		                }
		            });

		            pause.play();
		            aggiornaTimeline();
		        }
		    } catch (AWTException | IOException ex) {
		        ex.printStackTrace();
		    }
		    }

		
	 
	 
	 //metodo che itera sui personaggi di un giocatore e verifica per poi rimuovere se hanno i psa <= 0
	 //e gestisce l'eventuale eliminazione dell'utente
	 //N.B. questo metodo è usato principalmente per gestire la situazione dopo che viene giocata una carta che fa danni ai personaggi
	 private void verificaMorti(Utente u)  {
		 Iterator<CartaPersonaggio> iterator = ControllerCreazionePartita.p.getPersonaggiScelti().get(u).iterator();
		 while (iterator.hasNext()) {
		     CartaPersonaggio c = iterator.next();
		     if (c.getPsAttuali() <= 0) {
		         iterator.remove(); // Rimuove l'elemento corrente in modo sicuro per evitare concurrent mod exception
		     }
		 }

		 if(ControllerCreazionePartita.p.getPersonaggiScelti().get(u).isEmpty()) {
			 int x=utentiOgg.indexOf(utentiOgg.get(turni));
			 int y=utentiOgg.indexOf(u);
			 utentiOgg.remove(u);
			 if(x>y)
				 turni=turni-1;
			 mostraEliminazioneUtente(u.getNome(),()->{
				 if(utentiOgg.size()==1) {
					 labelTurni.setText("Complimenti, " + utentiOgg.get(0).getNome() + " hai vinto!");
	        			bottoneIncrementa.setVisible(false);
	        			bottoneAttacco.setVisible(false);
	        			bottoneSalvataggio.setVisible(false); 
	        			if(!ControllerCreazionePartita.p.getTorneo()) {
	        				utentiOgg.get(0).addPunti(25);
	        				JDBCUtenti.aggiornaPunti(utentiOgg.get(0).getNome(), utentiOgg.get(0).getPunteggio());
	        			}
	        			if(ControllerCreazionePartita.p.getTorneo()) {  
	        				utentiOgg.get(0).addPunti(10);
	            			JDBCUtenti.aggiornaPunti(utentiOgg.get(0).getNome(), utentiOgg.get(0).getPunteggio());
	        				ControllerCreazionePartita.t.getVincitori().add(utentiOgg.get(0));
	        				ControllerCreazionePartita.t.getPartite().remove(ControllerCreazionePartita.p);
	        				bottoneTabellone.setVisible(true);
	        				
	        			}
	        			 String sql = "DELETE FROM partite WHERE id = ?";

	        		        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:partite.db");
	        		             PreparedStatement pstmt = conn.prepareStatement(sql)) {      		            
	        		            pstmt.setString(1, ControllerCreazionePartita.p.getCodice());        		            
	        		            pstmt.executeUpdate();

	        		        } catch (Exception e) {
	        		            System.out.println(e.getMessage());
	        		        }
				 }
			 });
		 }			 	
			 
	 }
	 
	 /*
	  * Metodo per gestire un utente che viene eliminato e l'eventuale fine della partita
	  * 
	  */
	 private void verificaEliminazioni() {
		 Iterator<Utente> iterator = utentiOgg.iterator();
		 while (iterator.hasNext()) {
		     Utente temp = iterator.next();
		     if (ControllerCreazionePartita.p.getPersonaggiScelti().get(temp).isEmpty()) {
		         int x = utentiOgg.indexOf(utentiOgg.get(turni));
		         int y = utentiOgg.indexOf(temp);
		         if (x > y)
	                 turni = turni - 1;
		         if (utentiOgg.indexOf(temp) == turni) {
		        	 iterator.remove();
		             try {
						passaTurno(null);
					} catch (AWTException | IOException e) {						
						e.printStackTrace();
					}
		         }
		         else
		        	 iterator.remove(); 
		         // Rimuovi l'elemento utilizzando l'iteratore		         
				 
				 mostraEliminazioneUtente(temp.getNome(),()->{
					 if(utentiOgg.size()==1) {
						 labelTurni.setText("Complimenti, " + utentiOgg.get(0).getNome() + " hai vinto!");
		        			bottoneIncrementa.setVisible(false);
		        			bottoneAttacco.setVisible(false);
		        			bottoneSalvataggio.setVisible(false); 
		        			if(!ControllerCreazionePartita.p.getTorneo()) {
		        				utentiOgg.get(0).addPunti(25);
		        				JDBCUtenti.aggiornaPunti(utentiOgg.get(0).getNome(), utentiOgg.get(0).getPunteggio());
		        			}
		        			if(ControllerCreazionePartita.p.getTorneo()) {  
		        				utentiOgg.get(0).addPunti(10);
		            			JDBCUtenti.aggiornaPunti(utentiOgg.get(0).getNome(), utentiOgg.get(0).getPunteggio());
		        				ControllerCreazionePartita.t.getVincitori().add(utentiOgg.get(0));
		        				ControllerCreazionePartita.t.getPartite().remove(ControllerCreazionePartita.p);
		        				bottoneTabellone.setVisible(true);
		        				
		        			}
		        			 String sql = "DELETE FROM partite WHERE id = ?";

		        		        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:partite.db");
		        		             PreparedStatement pstmt = conn.prepareStatement(sql)) {      		            
		        		            pstmt.setString(1, ControllerCreazionePartita.p.getCodice());        		            
		        		            pstmt.executeUpdate();

		        		        } catch (Exception e) {
		        		            System.out.println(e.getMessage());
		        		        }
					 }
				 });
			 }
		 }
		 }
		 
	 //metodo che ritorna alla scena del torneo
	 public void tornaAlTabellone() throws IOException {
		 Main m=new Main();
		 m.setScena("SCENE/SCENA-TORNEO.fxml");	
		 m.resetDimensioniOriginali();
		 m.adattaSchermata();
	 }
		
	 //popUp che viene mostrato dopo l'attacco con le informazioni sull'attacco
	 public void mostraInfoAttacco(String infoAttacco, Runnable callback) {	
		 
		    Stage stage = new Stage();
		    stage.setTitle("Informazioni Attacco");
		    stage.initModality(Modality.APPLICATION_MODAL);
		    stage.setResizable(false); 
		    
		    BackgroundFill backgroundFill = new BackgroundFill(Color.rgb(15, 37, 60), CornerRadii.EMPTY, Insets.EMPTY);
		    Background background = new Background(backgroundFill);
		    HBox layout = new HBox();
		    layout.setBackground(background);
		    layout.setPadding(new Insets(20));
		    layout.setSpacing(20);
		    
		    Label label = new Label(infoAttacco);
		    label.setStyle("-fx-font-weight: bold; -fx-font-size: 16; -fx-text-fill: white;");
		    HBox.setMargin(label, new Insets(0, 20, 0, 20));
		    
		    Button okButton = new Button("OK");
		    okButton.setStyle("-fx-font-size: 16; -fx-font-weight: bold; -fx-text-fill: white; -fx-background-color: #9370DB; -fx-background-radius: 3; -fx-border-color: white;");
		    okButton.setPrefWidth(80);
		    okButton.setPrefHeight(40);
		    okButton.setOnAction(event -> {
		        stage.close();
		        callback.run();
		    });
		    HBox.setMargin(okButton, new Insets(20, 0, 0, 0));

		    layout.getChildren().addAll(label, okButton);

		    stage.setScene(new Scene(layout));
		    stage.setOnCloseRequest(event -> event.consume());
		    int turniVerifica=turni;
        	if(turni==utentiOgg.size())
        		turniVerifica=turni-1;
		    
		    if(!(utentiOgg.get(turniVerifica)instanceof RobotIntelligente)) {
		        stage.showAndWait();
		    }
	        else {	
	        	    stage.show();
	                PauseTransition closePause = new PauseTransition(Duration.seconds(2));
	                closePause.setOnFinished(closeEvent -> {
	                    stage.close();
	                    
	                });
	                closePause.play();
	                       
	        }	    
		   }		
	 
	 //popUp che viene mostrato quando viene eliminato un utente
	 private void mostraEliminazioneUtente(String nomeGiocatoreEliminato, Runnable callback) {
		    Stage stage = new Stage();
		    stage.setTitle("Eliminazione Utente");
		    stage.initModality(Modality.APPLICATION_MODAL);
		    stage.setResizable(false); 
		    
		    BackgroundFill backgroundFill = new BackgroundFill(Color.rgb(70, 130, 180), CornerRadii.EMPTY, Insets.EMPTY);
		    Background background = new Background(backgroundFill);
		    VBox layout = new VBox();
	        layout.setBackground(background);
	        layout.setPadding(new Insets(20));
	        layout.setSpacing(20);
	        layout.setAlignment(Pos.CENTER);
		    
		    Label label = new Label(nomeGiocatoreEliminato + ", sei eliminato!");
		    label.setStyle("-fx-font-weight: bold; -fx-font-size: 16;");
		    
		    Button okButton = new Button("OK");
		    okButton.setStyle("-fx-font-size: 16; -fx-font-weight: bold; -fx-text-fill: white; -fx-background-color: #9370DB; -fx-background-radius: 3; -fx-border-color: white;");
		    okButton.setPrefWidth(80);
		    okButton.setPrefHeight(40);
		    okButton.setOnAction(event -> {
		    	stage.close();
		    	callback.run();
		    	});
		    VBox.setMargin(okButton, new Insets(0, 0, 10, 0));

		    layout.getChildren().addAll(label, okButton);

	        stage.setScene(new Scene(layout));
	        stage.setOnCloseRequest(event -> event.consume());
	        int turniVerifica=turni;
        	if(turni==utentiOgg.size())
        		turniVerifica=turni-1;
	        
	        if(!(utentiOgg.get(turniVerifica)instanceof RobotIntelligente)) {
		        stage.showAndWait();
		    }	            
	        else {
	        	
	            PauseTransition showPause = new PauseTransition(Duration.seconds(4));
	            showPause.setOnFinished(event -> {
	                stage.show();

	                
	                PauseTransition closePause = new PauseTransition(Duration.seconds(2));
	                closePause.setOnFinished(closeEvent -> {
	                    stage.close();
	                    callback.run();
	                    
	                });
	                closePause.play();
	            });
	            showPause.play();
	        }	       
	        timelineS+="/Giocatore Eliminato: " + nomeGiocatoreEliminato.toUpperCase() + " dal giocatore " + utentiOgg.get(turniVerifica).getNome().toUpperCase();
	        aggiornaTimeline();
		}
	 
	 //poUp che viene mostrato quando viene eliminato un personaggio e relative informazioni
	 private void mostraEliminazionePersonaggio(String nomePersonaggio, String nomeUtente) {
	        Stage stage = new Stage();
	        stage.setTitle("Eliminazione Personaggio");
	        stage.initModality(Modality.APPLICATION_MODAL);
	        stage.setResizable(false); 

	        BackgroundFill backgroundFill = new BackgroundFill(Color.rgb(15, 37, 60), CornerRadii.EMPTY, Insets.EMPTY);
	        Background background = new Background(backgroundFill);
	        HBox layout = new HBox();
	        layout.setBackground(background);
	        layout.setPadding(new Insets(20));
	        layout.setSpacing(20);

	        Label label = new Label("Il personaggio " + nomePersonaggio + ", di " + nomeUtente + " è stato eliminato!");
	        label.setStyle("-fx-font-weight: bold; -fx-font-size: 16; -fx-text-fill: white");
	        label.setTextFill(Color.WHITE);
	        HBox.setMargin(label, new Insets(0, 20, 0, 20));

	        Button okButton = new Button("OK");
	        okButton.setStyle("-fx-font-size: 16; -fx-font-weight: bold; -fx-text-fill: white; -fx-background-color: #9370DB; -fx-background-radius: 3; -fx-border-color: white;");
	        okButton.setPrefWidth(80);
	        okButton.setPrefHeight(40);
	        okButton.setOnAction(event -> stage.close());
	        HBox.setMargin(okButton, new Insets(20, 0, 0, 0));

	        layout.getChildren().addAll(label, okButton);

	        stage.setScene(new Scene(layout));
	        stage.setOnCloseRequest(event -> event.consume());
	        int turniVerifica=turni;
        	if(turni==utentiOgg.size())
        		turniVerifica=turni-1;
	        if(!(utentiOgg.get(turniVerifica)instanceof RobotIntelligente)) {
		        stage.showAndWait();
		    }
	        else {
	        	
	            PauseTransition showPause = new PauseTransition(Duration.seconds(2));
	            showPause.setOnFinished(event -> {
	                stage.show();

	                
	                PauseTransition closePause = new PauseTransition(Duration.seconds(2));
	                closePause.setOnFinished(closeEvent -> {
	                    stage.close();
	                    
	                });
	                closePause.play();
	            });
	            showPause.play();
	        }
	        timelineS+="/Personaggio Eliminato: "+ nomePersonaggio.toUpperCase() + " di " + nomeUtente.toUpperCase();
	        aggiornaTimeline();
	    }
	 	
	/*
	 * Metodo che ritorna un personaggio casuale
	 * Usato per determinare il personaggio da attaccare dopo aver selezionato un giocatore        
	 */
	 private CartaPersonaggio getPersonaggioCasuale(List<CartaPersonaggio> personaggi) {
		    if (!personaggi.isEmpty()) {
		        Random random = new Random();
		        int indiceCasuale = random.nextInt(personaggi.size());
		        return personaggi.get(indiceCasuale);
		    } else {
		        return null;
		    }
		}
	
	 /*
	  * Metodo per mostrare una schermata con tutti i personaggi di tutti i giocatori ancora in gioco
	  */
	public void visualizzazioneCompleta() {
	    Iterator<Utente> iterator = utentiOgg.iterator();
	    int y = 0;
	    int xCarta = 250;
	    int yCarta = 20;
	    
	    campoTotale.getChildren().clear();
	    
	    while (iterator.hasNext()) {
	        Utente temp = iterator.next();
	        Iterator<CartaPersonaggio> iterator2 = ControllerCreazionePartita.p.getPersonaggiScelti().get(temp).iterator();
	        Pane pane = new Pane();
	        pane.setPrefWidth(830);
	        pane.setPrefHeight(125);
	        pane.setLayoutX(19);
	        pane.setLayoutY(y);
	        
	        Text text = new Text("Campo di" +"\n"+ temp.getNome() + ":");
	        text.setStyle("-fx-font-family: 'Century Gothic'; -fx-font-size: 16; -fx-font-color: #1c4a6e");
	        text.setLayoutX(80);
	        text.setLayoutY(30); 

	        pane.getChildren().add(text);
	        y += 150;

	        while (iterator2.hasNext()) {
	            CartaPersonaggio carta = iterator2.next();
	            ImageView imageView = new ImageView(carta.getImmagine());
	            imageView.setFitWidth(75);
	            imageView.setFitHeight(125);
	            imageView.setLayoutX(xCarta);
	            imageView.setLayoutY(yCarta);
	            setPopUpCampoTot(imageView,temp);
	            pane.getChildren().add(imageView);
	            xCarta += 300;
	        }
	        xCarta = 250;

	        campoTotale.getChildren().add(pane);
	        campoTotale.setStyle("-fx-background-color: #4682B4");
	    }
	    Button buttonX = new Button("X");
	    buttonX.setLayoutX(12);
	    buttonX.setLayoutY(11);
	    buttonX.setPrefHeight(33);
	    buttonX.setPrefWidth(56);
	    buttonX.setOnAction(event -> {
	    	visualizzazioneRistretta();
	    });
	    campoTotale.getChildren().add(buttonX);
	    
	    cartaTerreno.setVisible(false);
	    campoCarteGiocabili.setVisible(false);
	    //mazzo.setVisible(false);
	    campoTotale.setVisible(true);
	    bottoneSalvataggio.setVisible(false);
	    bottoneSalvataggio2.setVisible(false);
	    vboxTimeline.setVisible(false);
	}
	
	/*
	 * metodo per chiudere la schermata del metodo precedente
	 */
	public void visualizzazioneRistretta() {
	    for (javafx.scene.Node node : campoTotale.getChildren()) {
	        if (node instanceof Pane) {
	            Pane innerPane = (Pane) node;
            
	            List<javafx.scene.Node> children = innerPane.getChildren();
	          
	            for (javafx.scene.Node node2 : children) {
	                if (node2 instanceof ImageView) {
	                    ((ImageView) node2).setImage(null);
	                }
	            }
	        }
	    }
	    cartaTerreno.setVisible(true);
	    campoCarteGiocabili.setVisible(true);
	    campoTotale.setVisible(false);
	    //mazzo.setVisible(true);	    
	    vboxTimeline.setVisible(true);
	    if(!ControllerCreazionePartita.p.getTorneo()) {
	       bottoneSalvataggio.setVisible(true);
	    }
	    else {
	    	bottoneSalvataggio2.setVisible(true);
	    	 bottoneSalvataggio.setVisible(true);
	    }
	}
	
	/*
	 * Metodo per scegliere quale avversario attaccante
	 * 
	 */
	private void handleSelezionePersonaggio(ImageView imageView) {
	    pgAttaccante = getPersonaggioDaImmagine(imageView, utentiOgg.get(turni));
	    String headerText = "Personaggio selezionato per l'attacco: " + pgAttaccante.getNome();
	    System.out.println(headerText);
	    
	    if(!(utentiOgg.get(turni) instanceof RobotIntelligente)) {
	    Stage stage = new Stage();
	    stage.setTitle("Selezione avversario");
	    stage.initModality(Modality.APPLICATION_MODAL);
	    stage.setResizable(false);
	    
	    BackgroundFill backgroundFill = new BackgroundFill(Color.rgb(15, 37, 60), CornerRadii.EMPTY, Insets.EMPTY);
	    Background background = new Background(backgroundFill);

	    VBox selectionLayout = new VBox();
	    selectionLayout.setBackground(background);
	    selectionLayout.setPadding(new Insets(10));
	    selectionLayout.setSpacing(10);

	    VBox second = new VBox();
	    second.setPadding(new Insets(10));
	    second.setSpacing(10);

	    Label testo = new Label(headerText + "\n\nSeleziona il giocatore da attaccare:");
	    testo.setStyle("-fx-font-weight: bold; -fx-font-size: 16; -fx-text-fill: white;");
	    second.getChildren().add(testo);

	    Label erroreLabel = new Label("Nessun avversario selezionato");
	    erroreLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14; -fx-text-fill: red;");
	    erroreLabel.setVisible(false);
	    second.getChildren().add(erroreLabel);

	    ToggleGroup toggleGroup = new ToggleGroup();
	    utentiOgg.stream()
	            .filter(utente -> !utente.equals(utentiOgg.get(turni)))
	            .forEach(utente -> {
	                RadioButton radioButton = new RadioButton(utente.getNome());
	                radioButton.setToggleGroup(toggleGroup);
	                radioButton.setStyle("-fx-font-weight: bold; -fx-font-size: 16; -fx-text-fill: white; -fx-color: #9370DB;");
	                second.getChildren().add(radioButton);
	            });

	    Button confirmButton = new Button("Conferma");
	    confirmButton.setStyle("-fx-font-size: 16; -fx-font-weight: bold; -fx-text-fill: white; -fx-background-color: #9370DB; -fx-background-radius: 3; -fx-border-color: white;");
	    confirmButton.setPrefWidth(100);
	    confirmButton.setPrefHeight(40);
	    confirmButton.setOnAction(e -> {
	        RadioButton selectedRadioButton = (RadioButton) toggleGroup.getSelectedToggle();
	        if (selectedRadioButton != null) {
	            String selectedUserName = selectedRadioButton.getText();
	            gestisciSelezioneAvversario(selectedUserName);
	            stage.close();
	        } else {
	            erroreLabel.setVisible(true);
	            System.out.println("Nessun avversario selezionato");
	        }
	    });

	    VBox errorAndConfirmContainer = new VBox();
	    errorAndConfirmContainer.setAlignment(Pos.CENTER); 
	    errorAndConfirmContainer.setSpacing(10);
	    errorAndConfirmContainer.getChildren().addAll(erroreLabel, confirmButton);
	    VBox.setMargin(confirmButton, new Insets(0, 0, 30, 0));
	    selectionLayout.getChildren().addAll(second, errorAndConfirmContainer);

	    Scene selectionScene = new Scene(selectionLayout);
	    stage.setScene(selectionScene);
	    stage.setOnCloseRequest(event -> event.consume());
	    stage.showAndWait();
	    }
	    else {
	    	gestisciSelezioneAvversario("");
	    }
	}
		

	 
	private void gestisciSelezioneAvversario(String nomeGiocatoreAttaccato) {
		if(!(utentiOgg.get(turni) instanceof RobotIntelligente)) {
	    utenteDifensore = utentiOgg.stream()
	            .filter(utente -> utente.getNome().equals(nomeGiocatoreAttaccato))
	            .findFirst()
	            .orElse(null);
	    System.out.println("Giocatore attaccato: " + nomeGiocatoreAttaccato);
		}
		else {
		    do {
		        utenteDifensore = utentiOgg.get(new Random().nextInt(utentiOgg.size()));
		    } while (utenteDifensore.equals(utentiOgg.get(turni)));
		}
	}
	
	/*
	 * Metodo per aggiornare la mano dell'utente attuale
	 * Itera sulle carteGiocabili che ha in mano ed imposta sullo schermo le immagini di queste
	 * Vengono distribuite lungo una curva in modo da formare un ventaglio
	 */
	private void aggiornamentoMano() {
		campoCarteGiocabili.getChildren().clear();
        ArrayList<CartaGiocabile> manoAttuale = ControllerCreazionePartita.p.getMano().get(utentiOgg.get(turni));
        double tStep = 1.0 / (manoAttuale.size() + 1); 
        double t = tStep;

        for (CartaGiocabile c : manoAttuale) {  
            ImageView image = new ImageView(c.getImmagine());
            double larghezzaCarta = 100;
            double altezzaCarta = 175;
            image.setFitWidth(larghezzaCarta);
            image.setFitHeight(altezzaCarta);
         
            double posX = calculateBezierPointX(t);
            double posY = calculateBezierPointY(t);
            image.setLayoutX(posX - larghezzaCarta / 2); 
            image.setLayoutY(posY - altezzaCarta / 2);

            campoCarteGiocabili.getChildren().add(image);

            t += tStep; 
        }
        animazioneCartaSollevata();
        if(contaGiocate<2)
        	associazioneEffetto();
    }
	
	/*
	 * Metodo per gestire le implicazioni grafiche dovute al metodo pesca
	 * Svuota il campo dove vengono mostrate le carte e richiama aggiornamentoMano
	 */
	private void pescaCheck() throws IOException {
	    if (pescateQuestoTurno >= 1) {
	    	pesca.setVisible(true);
	        pesca.setText("Hai già pescato in questo turno");
	        return;
	    }

	    if (campoCarteGiocabili.getChildren().size() < 8) {
	        campoCarteGiocabili.getChildren().clear();
	        ControllerCreazionePartita.p.pesca(utentiOgg.get(turni));
	        if (ControllerCreazionePartita.p.getMano().get(utentiOgg.get(turni)).get(ControllerCreazionePartita.p.getMano().get(utentiOgg.get(turni)).size() - 1) instanceof CartaImprevisto) {
	            aggiornamentoMano();
	        } else {
	            aggiornamentoMano();
	            Node temp = campoCarteGiocabili.getChildren().get(campoCarteGiocabili.getChildren().size() - 1);
	            double x = temp.getLayoutX();
	            double y = temp.getLayoutY();
	            campoCarteGiocabili.getChildren().remove(campoCarteGiocabili.getChildren().size() - 1);
	            root.getChildren().add(temp);
	            temp.setLayoutX(1100);
	            temp.setLayoutY(507);
	            TranslateTransition transition = new TranslateTransition(Duration.seconds(1), temp);
	            transition.setFromX(0);
	            transition.setFromY(0);
	            transition.setToX(0);
	            transition.setToY(0);
	            transition.setOnFinished(event -> {
	                campoCarteGiocabili.getChildren().add(temp);
	                temp.setLayoutX(x);
	                temp.setLayoutY(y);
	                aggiornamentoMano();
	            });
	            transition.play();
	        }
	        pescateQuestoTurno++;
	    } else {
	        System.out.println("Pieno");
	    }
	}

    //Metodi per disegnare la curva
    private double calculateBezierPointX(double t) {
        double startX = 0; 
        double control1X = 200;
        double control2X = 300; 
        double endX = 600; 

        return (1 - t) * (1 - t) * (1 - t) * startX + 3 * (1 - t) * (1 - t) * t * control1X + 3 * (1 - t) * t * t * control2X + t * t * t * endX;
    }
  
    private double calculateBezierPointY(double t) {
        double startY = 60; 
        double control1Y = -50; 
        double control2Y = -80; 
        double endY = 60; 

        return (1 - t) * (1 - t) * (1 - t) * startY + 3 * (1 - t) * (1 - t) * t * control1Y + 3 * (1 - t) * t * t * control2Y + t * t * t * endY;
    }
    
    /*
     * Metodo associato alle carte in mano che vengono "sollevate" quando puntate col mouse
     */
    public void animazioneCartaSollevata() {
    	 campoCarteGiocabili.getChildren().forEach(imageView -> {
             imageView.setOnMouseEntered(event -> {              
                 imageView.setTranslateY(-10); 
                 ImageView temp=(ImageView)imageView;
                 cartaGiocabileZoom.setImage(temp.getImage());
             });

             imageView.setOnMouseExited(event -> {
                 cartaGiocabileZoom.setImage(null);
                 imageView.setTranslateY(0);
             });
         });
    }
    
   /*
    * Metodo per assocaiare gli effetti delle carte alle immagini tenute nella mano
    * Itera sulle immagini nel campoCarteGiocabili ed associa un metodo ad ogni carta
    * itera internamente sulle carteagiocabili tenute nella mano dle giocatore corrente, se  l'immagine del campoCarteGiocabili
    * e quella ottenuta dal metodo getImmagine sono uguali, parte una verifica di che tipo di carta è e quale effetto è associato,
    * dopodichè lo associa al clic della carta, o eventualmente a due clic oppure tre,dipende dall'effetto
    * Le carteGiocabli hanno un parametro target per raggrupparle nei comportamenti che deve tenere l'interfaccia grafica
    * Ad esempio, quelle carte che hanno un effeto che richiede che vengano effettuati due clic, saranno nello stesso blocco if,
    * così come anche quelle che richiedono che venga selezionato un personaggio avversario e così via.
    * I blocchi if che verificano il valore di target sono dentro a degli ulteriori blocchi if per verificare anche il tipo di carta,
    * ovvero se è una cura, un equipaggiamento, ecc... per lo stesso motivo dell'esistenza di target, ovvero il differente comportamento
    * che devono avere graficamente
    */
    public void associazioneEffetto() {   
    	comunicazioneEffetto.setVisible(true);
    	attivazioneImprevisti();
        campoCarteGiocabili.getChildren().forEach(imageView -> {       	      	
            imageView.setOnMouseClicked(event -> {
            	for (CartaGiocabile c : ControllerCreazionePartita.p.getMano().get(utentiOgg.get(turni))) {
                    if (imageView instanceof ImageView && ((ImageView) imageView).getImage().equals(c.getImmagine())) {
                        tempEffetto = c;
                        break;
                    }
                }
                comunicazioneEffetto.setText(tempEffetto.getRichiestaEffetto());  
                if(tempEffetto instanceof CartaCura) {
                if (tempEffetto.getTarget() == 1) {
                    campoCorrente.getChildren().forEach(imageView2 -> {
                        imageView2.setOnMouseClicked(secondEvent -> {
                            ImageView clickedImageView = (ImageView) secondEvent.getSource();
                            tempEffetto.effetto(utentiOgg.get(turni), getPersonaggioDaImmagine(clickedImageView, utentiOgg.get(turni)));
                            System.out.println("Effetto applicato ");
                            ControllerCreazionePartita.p.getMano().get(utentiOgg.get(turni)).remove(tempEffetto);                           
                            if(!(utentiOgg.get(turni)instanceof RobotIntelligente)){
                            	contaGiocate+=1;
                            	aggiornamentoMano();
                            }
                            comunicazioneEffetto.setText("");
                            resettaEventiMousePersonaggi();
                            ricalcoloBuffEquip(utentiOgg.get(turni));                            
                            timelineS+="/" + utentiOgg.get(turni).getNome().toUpperCase() +"\nCarta giocata: " + tempEffetto.getNome();
                            aggiornaTimeline();
                            
                        });
                    });
                } else if (tempEffetto.getTarget() == 2) {
                    campoCorrente.getChildren().forEach(imageView2 -> {
                        imageView2.setOnMouseClicked(secondEvent -> {
                            ImageView clickedImageView1 = (ImageView) secondEvent.getSource();
                            campoCorrente.getChildren().forEach(imageView3 -> {
                                imageView3.setOnMouseClicked(thirdEvent -> {
                                    ImageView clickedImageView2 = (ImageView) thirdEvent.getSource();
                                    tempEffetto.effetto2(utentiOgg.get(turni), getPersonaggioDaImmagine(clickedImageView1, utentiOgg.get(turni)), getPersonaggioDaImmagine(clickedImageView2, utentiOgg.get(turni)));
                                    System.out.println("Effetto applicato ");
                                    ControllerCreazionePartita.p.getMano().get(utentiOgg.get(turni)).remove(tempEffetto);                                   
                                    if(!(utentiOgg.get(turni)instanceof RobotIntelligente)){
                                    	contaGiocate+=1;
                                    	aggiornamentoMano();
                                    }
                                    comunicazioneEffetto.setText("");
                                    resettaEventiMousePersonaggi();
                                    ricalcoloBuffEquip(utentiOgg.get(turni));                                    
                                    timelineS+="/" + utentiOgg.get(turni).getNome().toUpperCase() +"\nCarta giocata: " + tempEffetto.getNome();
                                    aggiornaTimeline();
                                });
                            });
                        });
                    });
                } else {
                    tempEffetto.effetto3(utentiOgg.get(turni));
                    System.out.println("Effetto applicato ");
                    ControllerCreazionePartita.p.getMano().get(utentiOgg.get(turni)).remove(tempEffetto);                   
                    if(!(utentiOgg.get(turni)instanceof RobotIntelligente)){
                    	contaGiocate+=1;
                    	aggiornamentoMano();
                    }
                    ricalcoloBuffEquip(utentiOgg.get(turni));
                    timelineS+="/" + utentiOgg.get(turni).getNome().toUpperCase() +": Carta giocata: " + tempEffetto.getNome();
                    aggiornaTimeline();
                }
                }
        if(tempEffetto instanceof CartaEquipaggiamento) {     //valutazione a seguire se tenere tutto in blocco unico e fare solo un blocco if per aggiungere equip
        	campoCorrente.getChildren().forEach(imageView2 -> {
                imageView2.setOnMouseClicked(secondEvent -> {               	
                    ImageView clickedImageView = (ImageView) secondEvent.getSource();
                    if(getPersonaggioDaImmagine(clickedImageView, utentiOgg.get(turni)).getEquip().size()<3) {
                    getPersonaggioDaImmagine(clickedImageView, utentiOgg.get(turni)).addEquip((CartaEquipaggiamento)tempEffetto);                   
                    System.out.println("Effetto applicato ");
                    ControllerCreazionePartita.p.getMano().get(utentiOgg.get(turni)).remove(tempEffetto);
                    contaGiocate+=1;
                    if(!(utentiOgg.get(turni)instanceof RobotIntelligente)){
                    	aggiornamentoMano();
                    }
                    comunicazioneEffetto.setText("");
                    resettaEventiMousePersonaggi();
                    ricalcoloBuffEquip(utentiOgg.get(turni));
                    timelineS+="/" + utentiOgg.get(turni).getNome().toUpperCase() +"\nCarta giocata: " + tempEffetto.getNome();
                    aggiornaTimeline();
                    }
                    else {
                    	comunicazioneEffetto.setText("Selezione l'equipaggiamento da rimuovere");
                    	Pane paneEquip=new Pane();
                    	paneEquip.setPrefWidth(campoCorrente.getPrefWidth());
                    	paneEquip.setPrefHeight(campoCorrente.getPrefHeight());
                    	paneEquip.setStyle("-fx-background-color: #3498db;");
                    	root.getChildren().add(paneEquip);
                    	int t=0;
                    	for (CartaEquipaggiamento e : getPersonaggioDaImmagine(clickedImageView, utentiOgg.get(turni)).getEquip()) {
                    	    Image img = e.getImmagine();
                    	    ImageView img2 = new ImageView(img);                   	    
                    	    paneEquip.getChildren().add(img2);
                    	    img2.setFitWidth(162);
                    	    img2.setFitHeight(284);
                    	    img2.setLayoutX(t);
                    	    t+=214;
                    	}
                    	paneEquip.getChildren().forEach(imageEquip -> {	
                    		imageEquip.setOnMouseEntered(event2 -> {                           
                        		imageEquip.setCursor(Cursor.HAND);
                            });                     
                    		imageEquip.setOnMouseExited(event2 -> {                           
                    		imageEquip.setCursor(Cursor.DEFAULT);
                            });
                    		imageEquip.setOnMouseClicked(thirdEvent -> {
                    			ImageView clickedEquip = (ImageView) thirdEvent.getSource();
                    			annullamentoEffettiEquip(getPersonaggioDaImmagine(clickedImageView, utentiOgg.get(turni)));
                    			for(CartaEquipaggiamento e : getPersonaggioDaImmagine(clickedImageView, utentiOgg.get(turni)).getEquip()) {
                    				if(e.getImmagine().equals(clickedEquip.getImage())) {
                    					getPersonaggioDaImmagine(clickedImageView, utentiOgg.get(turni)).getEquip().remove(e);
                    					break; 
                    				}
                    			}
                    			getPersonaggioDaImmagine(clickedImageView, utentiOgg.get(turni)).addEquip((CartaEquipaggiamento)tempEffetto);                                
                                System.out.println("Effetto applicato ");
                                ControllerCreazionePartita.p.getMano().get(utentiOgg.get(turni)).remove(tempEffetto);                              
                                if(!(utentiOgg.get(turni)instanceof RobotIntelligente)){
                                	contaGiocate+=1;
                                	aggiornamentoMano();
                                }
                                comunicazioneEffetto.setText("");
                                resettaEventiMousePersonaggi();
                                for(CartaEquipaggiamento e:getPersonaggioDaImmagine(clickedImageView, utentiOgg.get(turni)).getEquip()) {
                                	e.effetto(utentiOgg.get(turni),getPersonaggioDaImmagine(clickedImageView, utentiOgg.get(turni)));
                                }
                                root.getChildren().remove(paneEquip);
                    		});
                    	});
                    	timelineS+="/" + utentiOgg.get(turni).getNome().toUpperCase() +"\nCarta giocata: " + tempEffetto.getNome();
                        aggiornaTimeline();
                    }
                });
            });
        }
        if(tempEffetto instanceof CartaEffetto) {
        	if (tempEffetto.getTarget() == 1) {
        		Utente u=selezionaUtenteCasuale(utentiOgg,utentiOgg.get(turni));       		
        		Random random=new Random();
        		int x=random.nextInt(ControllerCreazionePartita.p.getPersonaggiScelti().get(u).size());
        		tempEffetto.effettoEffetto1(utentiOgg.get(turni),ControllerCreazionePartita.p.getPersonaggiScelti().get(u).get(x));
                System.out.println("Effetto applicato ");
                ControllerCreazionePartita.p.getMano().get(utentiOgg.get(turni)).remove(tempEffetto);              
                if(!(utentiOgg.get(turni)instanceof RobotIntelligente)){
                	contaGiocate+=1;
                	aggiornamentoMano();
                }
                ricalcoloBuffEquip(utentiOgg.get(turni));  
                timelineS+="/" + utentiOgg.get(turni).getNome().toUpperCase() +"\nCarta giocata: " + tempEffetto.getNome();
                aggiornaTimeline();
        	}
        	if(tempEffetto.getTarget()==2) {      		
        		if(!(utentiOgg.get(turni)instanceof RobotIntelligente)) {
        		comunicazioneEffetto.setText("Seleziona l'avversario da bersagliare");
        		paneSceltaAvv.setVisible(true);
        		int x=150;
            	int y=0;
            	utentiBersaglio.remove(utentiOgg.get(turni)); 
            	for(Utente u:utentiBersaglio) {
            		Text indicazioneCampo=new Text("Campo di "+u.getNome());           		
            		paneSceltaAvv.getChildren().add(indicazioneCampo);
            		paneSceltaAvv.setStyle("-fx-background-color: #3498db;");
            		indicazioneCampo.setTabSize(15);
            		indicazioneCampo.setLayoutX(0);
            		indicazioneCampo.setLayoutY(y+100);
            		for(CartaPersonaggio c:ControllerCreazionePartita.p.getPersonaggiScelti().get(u)) {
            			ImageView pg=new ImageView(c.getImmagine());
            			paneSceltaAvv.getChildren().add(pg);
            			pg.setLayoutX(x);
            			pg.setLayoutY(y);
            			pg.setFitWidth(100);
        	            pg.setFitHeight(175);
        	            x+=150;
        	            pg.setOnMouseEntered(event2 -> {                           
                    		pg.setCursor(Cursor.HAND);
                        });
        	            pg.setOnMouseExited(event2 -> {                           
                    		pg.setCursor(Cursor.DEFAULT);
                        });      	           
        	            pg.setOnMouseClicked(event3 -> {       	            	
     	                    utentiBersaglio.add(utentiOgg.get(turni));
        	                tempEffetto.effettoEffetto2(getPersonaggioDaImmagine(pg, u), utentiOgg.get(turni), u, () -> {       	                	
        	                });       	               
        	                	paneSceltaAvv.getChildren().clear();
         	                    paneSceltaAvv.setVisible(false);
         	                    System.out.println("Effetto applicato ");
         	                    annullamentoEffettiEquip(getPersonaggioDaImmagine(pg, u));
         	                    for (CartaEquipaggiamento e : getPersonaggioDaImmagine(pg, u).getEquip()) {
         	                        e.effetto(u, getPersonaggioDaImmagine(pg, u));
         	                    }
         	                    comunicazioneEffetto.setText("");
         	                    ControllerCreazionePartita.p.getMano().get(utentiOgg.get(turni)).remove(tempEffetto);
         	                    if (!(utentiOgg.get(turni) instanceof RobotIntelligente)) {
         	                        contaGiocate += 1;
         	                        aggiornamentoMano();
         	                    }       	                           	                	
         	                   verificaMorti(u);	                               	                       	               
        	            });
        	            
            		}            		                 			                	
            		x=150;
            		y+=200;
            	  }
            	timelineS+="/" + utentiOgg.get(turni).getNome().toUpperCase() +"\nCarta giocata: " + tempEffetto.getNome();
                aggiornaTimeline();
        		}
        		else {
        			utentiBersaglio.remove(utentiOgg.get(turni));
        			CartaPersonaggio temp=null;
        			Utente temp2=null;
        			esterno: for(Utente u:utentiBersaglio) {
        				for(CartaPersonaggio c:ControllerCreazionePartita.p.getPersonaggiScelti().get(u)) {  //prende il primo pg con equip che trova
        					if(!c.getEquip().isEmpty()) {
        						temp2=u;
        						temp=c;
        						break esterno;
        					}
        				}     				
        			}
        			
        			if(temp==null || temp2==null) {      				       				
        				for(Utente u:utentiBersaglio) {
            				for(CartaPersonaggio c:ControllerCreazionePartita.p.getPersonaggiScelti().get(u)) {  //se temp è ancora null prende il pg
            					if(temp==null) {                                                           //con meno psa
            						temp=c; 
            						temp2=u;
            					}
            					
            					else {
            						if(temp.getPsAttuali()>c.getPsAttuali()) {
            							temp=c;          						
                						temp2=u;
            						}
            					}
            					
            				}
        				}
        			}       			
        			tempEffetto.effettoEffetto2(temp, utentiOgg.get(turni), temp2,()->{      				       				
        			});         			
	            	System.out.println("Effetto applicato ");         	            	
	                annullamentoEffettiEquip(temp);
	                for(CartaEquipaggiamento e:temp.getEquip())
	                	e.effetto(temp2,temp);
	                comunicazioneEffetto.setText("");
	                ControllerCreazionePartita.p.getMano().get(utentiOgg.get(turni)).remove(tempEffetto);
	                if(!(utentiOgg.get(turni)instanceof RobotIntelligente)){
                    	aggiornamentoMano();
                    }
	                verificaMorti(temp2);
	                utentiBersaglio.add(utentiOgg.get(turni)); 
	                timelineS+="/" + utentiOgg.get(turni).getNome().toUpperCase() +": Carta giocata: " + tempEffetto.getNome();
                    aggiornaTimeline();       			
        		}
        	}
        	if(tempEffetto.getTarget()==3) {
        		tempEffetto.effetto3(utentiOgg.get(turni));
                System.out.println("Effetto applicato ");
                ControllerCreazionePartita.p.getMano().get(utentiOgg.get(turni)).remove(tempEffetto);
                if(!(utentiOgg.get(turni)instanceof RobotIntelligente)){
                	contaGiocate+=1;
                	aggiornamentoMano();
                }
                ricalcoloBuffEquip(utentiOgg.get(turni));
                timelineS+="/" + utentiOgg.get(turni).getNome().toUpperCase() +"\nCarta giocata: " + tempEffetto.getNome();
                aggiornaTimeline();
            }
        	if(tempEffetto.getTarget()==4) {
        		comunicazioneEffetto.setText(tempEffetto.getRichiestaEffetto());
        		campoCorrente.getChildren().forEach(imageView2 -> {
                    imageView2.setOnMouseClicked(secondEvent -> { 
                    ImageView clickedImageView = (ImageView) secondEvent.getSource();
                    tempEffetto.effettoEffetto3(getPersonaggioDaImmagine(clickedImageView,utentiOgg.get(turni)),utentiOgg);
                    ControllerCreazionePartita.p.getMano().get(utentiOgg.get(turni)).remove(tempEffetto);
                    if(!(utentiOgg.get(turni)instanceof RobotIntelligente)){
                    	contaGiocate+=1;
                    	aggiornamentoMano();
                    }
                    ricalcoloBuffEquip(utentiOgg.get(turni));
                    resettaEventiMousePersonaggi();
                    comunicazioneEffetto.setText("");
                    timelineS+="/" + utentiOgg.get(turni).getNome().toUpperCase() +"\nCarta giocata: " + tempEffetto.getNome();
                    aggiornaTimeline();
        	});
         });                            
        }
        	if(tempEffetto.getTarget()==5) {  
        		tempEffetto.effettoEffetto4(utentiOgg);
                System.out.println("Effetto applicato ");
                ControllerCreazionePartita.p.getMano().get(utentiOgg.get(turni)).remove(tempEffetto);
                if(!(utentiOgg.get(turni)instanceof RobotIntelligente)){
                	contaGiocate+=1;
                	aggiornamentoMano();
                }
                ricalcoloBuffEquip(utentiOgg.get(turni));
                aggiornaCampoCorrente(utentiOgg.get(turni));
                verificaEliminazioni();
                timelineS+="/" + utentiOgg.get(turni).getNome().toUpperCase() +"\nCarta giocata: " + tempEffetto.getNome();
                aggiornaTimeline();
            }
        	if(tempEffetto.getTarget() == 6) {
        	    List<Utente> bersagli = new ArrayList<>();
        	    bersagli.addAll(utentiOgg);
        	    bersagli.remove(utentiOgg.get(turni));  
        	    if(!((utentiOgg.get(turni)) instanceof RobotIntelligente)) {
        	    List<String> nomiUtenti = new ArrayList<>();
        	    for (Utente utente : bersagli) {
        	        nomiUtenti.add(utente.getNome());
        	    }       	    
        	    ChoiceDialog<String> dialog = new ChoiceDialog<>(nomiUtenti.get(0), nomiUtenti);
        	    dialog.getDialogPane().setStyle("-fx-background-color: #3498db;");
        	    dialog.setTitle("Scelta Utente");
        	    dialog.setHeaderText("Scegli un utente:");
        	    dialog.setContentText("Nome utente:");
        	    ControllerCreazionePartita.p.getMano().get(utentiOgg.get(turni)).remove(tempEffetto);
        	    if(!(utentiOgg.get(turni)instanceof RobotIntelligente)){
        	    	contaGiocate+=1;
                	aggiornamentoMano();
                }
        	    Optional<String> result = dialog.showAndWait();
        	    if (result.isPresent()) {       	        
        	        for (Utente utente : bersagli) {
        	            if (utente.getNome().equals(result.get())) {        	                
        	                Utente utenteSelezionato = utente;
        	                tempEffetto.effetto3(utenteSelezionato);
        	                break;
        	            }
        	        }
        	    }
                  ricalcoloBuffEquip(utentiOgg.get(turni));  
                  timelineS+="/" + utentiOgg.get(turni).getNome().toUpperCase() +"\nCarta giocata: " + tempEffetto.getNome();
                  aggiornaTimeline();
        	    }
        	    else {
        	    	Utente bersaglio=bersagli.get(0);
        	    	int x=0;
        	    	int y=0;
        	    	for(Utente u:bersagli) {
        	    	  for(CartaPersonaggio c:ControllerCreazionePartita.p.getPersonaggiScelti().get(u)) { //prende come bersaglio l'utente che
        	    		x+=c.getPsAttuali();                                                        //ha la sommma maggiore di ps dei pg 
        	    	  }
        	    	  if(x>y) {
        	    		  y=x;
        	    		  bersaglio=u;
        	    		  x=0;
        	    	  }
        	    	}
        	    	ControllerCreazionePartita.p.getMano().get(utentiOgg.get(turni)).remove(tempEffetto);
        	    	 if(!(utentiOgg.get(turni)instanceof RobotIntelligente)){
        	    		contaGiocate+=1;
                     	aggiornamentoMano();
                     }
                    tempEffetto.effetto3(bersaglio);
                    timelineS+="/" + utentiOgg.get(turni).getNome().toUpperCase() +"\nCarta giocata: " + tempEffetto.getNome();
                    aggiornaTimeline();
        	    }
        	}
        }
        if(tempEffetto.getTarget() == 7) {
        	List<Utente> bersagli = new ArrayList<>();
    	    bersagli.addAll(utentiOgg);
    	    bersagli.remove(utentiOgg.get(turni));   
    	    if(!((utentiOgg.get(turni)) instanceof RobotIntelligente)) {
    	    List<String> nomiUtenti = new ArrayList<>();
    	    for (Utente utente : bersagli) {
    	        nomiUtenti.add(utente.getNome());
    	    }       	    
    	    ChoiceDialog<String> dialog = new ChoiceDialog<>(nomiUtenti.get(0), nomiUtenti);
    	    dialog.getDialogPane().setStyle("-fx-background-color: #3498db;");
    	    dialog.setTitle("Scelta Utente");
    	    dialog.setHeaderText("Scegli un utente:");
    	    dialog.setContentText("Nome utente:");
    	    ControllerCreazionePartita.p.getMano().get(utentiOgg.get(turni)).remove(tempEffetto);
    	    if(!(utentiOgg.get(turni)instanceof RobotIntelligente)){
    	    	contaGiocate+=1;
            	aggiornamentoMano();
            }
    	    Optional<String> result = dialog.showAndWait();
    	    if (result.isPresent()) {       	        
    	        for (Utente utente : bersagli) {
    	            if (utente.getNome().equals(result.get())) {        	                
    	                utenteBersaglio = utente;
    	                break;
    	            }
    	        }
    	    }
    	    if(!(ControllerCreazionePartita.p.getMano().get(utenteBersaglio).isEmpty())) {
    	    Pane carteAvversario=new Pane();
    	    root.getChildren().add(carteAvversario);
    	    carteAvversario.setPrefHeight(campoCorrente.getPrefHeight());
    	    carteAvversario.setPrefWidth(campoCorrente.getPrefWidth());
    	    carteAvversario.setStyle("-fx-background-color: #3498db;");
    	    int x=0;
    	    int y=0;
    	    for(CartaGiocabile c:ControllerCreazionePartita.p.getMano().get(utenteBersaglio)) {
    	    	ImageView img=new ImageView(c.getImmagine());
    	    	carteAvversario.getChildren().add(img);
    	    	img.setFitWidth(100);
    	    	img.setFitHeight(175);
    	    	img.setLayoutX(x);
    	    	img.setLayoutY(y);
    	    	x+=125;
    	    	if(x>carteAvversario.getPrefWidth()) {
    	    		x=0;
    	    		y+=200;
    	    	}
    	    	img.setOnMouseEntered(event2 -> {                           
            		img.setCursor(Cursor.HAND);
            		cartaGiocabileZoom.setImage(img.getImage());
                });                     
        		img.setOnMouseExited(event2 -> {                           
        		    img.setCursor(Cursor.DEFAULT);
        		    cartaGiocabileZoom.setImage(null);
                });
    	    	img.setOnMouseClicked(secondEvent -> { 
                    tempEffetto.effettoEffetto5(utentiOgg.get(turni), utenteBersaglio, c);
                    ControllerCreazionePartita.p.getMano().get(utentiOgg.get(turni)).remove(tempEffetto);
                    if(!(utentiOgg.get(turni)instanceof RobotIntelligente)){
                    	aggiornamentoMano();
                    }
                    root.getChildren().remove(carteAvversario);
                    comunicazioneEffetto.setText("");
                    });
    	    } 
    	    }
    	    else {
    	    	comunicazioneEffetto.setText("L'utente non ha carte in mano");
    	    	 PauseTransition pause = new PauseTransition(Duration.millis(3000));
    	            pause.setOnFinished(event2 -> {
    	            	comunicazioneEffetto.setText("");
    	            });
    	    }
    	    timelineS+="/" + utentiOgg.get(turni).getNome().toUpperCase() +"\nCarta giocata: " + tempEffetto.getNome();
            aggiornaTimeline();
    	    }
    	    else {
    	    	Utente utenteBersaglio=bersagli.get(0);    //sarebbe ingiusto perchè l'utente non può vedere le carte degli altri giocatori
    	    	                                           //però vabbè
    	    	for(Utente u:bersagli) {
    	    		if(ControllerCreazionePartita.p.getMano().get(u).size()>ControllerCreazionePartita.p.getMano().get(utenteBersaglio).size())
    	    			utenteBersaglio=u;
    	    	}
    	    	CartaGiocabile temp=null;
    	    	for(CartaGiocabile c:ControllerCreazionePartita.p.getMano().get(utenteBersaglio)) {
    	    		int x=0;
    	    		int y=0;
    	    		if(c.getRarita().equals(RaritaCartaGiocabile.COMUNE))
    	    			x=1;
    	    		else if(c.getRarita().equals(RaritaCartaGiocabile.RARA))
    	    			x=2;
    	    		else if(c.getRarita().equals(RaritaCartaGiocabile.EPICA))
    	    			x=3;
    	    		else if(c.getRarita().equals(RaritaCartaGiocabile.UNICA))
    	    			x=4;
    	    		if(temp==null && !(c instanceof CartaImprevisto)) {
    	    			temp=c;
    	    			y=x;
    	    		}
    	    		if(x>y && !(c instanceof CartaImprevisto)) {
    	    			temp=c;
    	    			y=x;
    	    		}
    	    	}
    	    	
    	    	tempEffetto.effettoEffetto5(utentiOgg.get(turni), utenteBersaglio, temp);
                ControllerCreazionePartita.p.getMano().get(utentiOgg.get(turni)).remove(tempEffetto);
                if(!(utentiOgg.get(turni)instanceof RobotIntelligente)){
                	contaGiocate+=1;
                	aggiornamentoMano();
                }
                timelineS+="/" + utentiOgg.get(turni).getNome().toUpperCase() +": Carta giocata: " + tempEffetto.getNome();
                aggiornaTimeline();
    	    }
    	    
        }
        if(tempEffetto instanceof CartaTerreno) {       	        	 
        	 ControllerCreazionePartita.p.getMano().get(utentiOgg.get(turni)).remove(tempEffetto);
        	 ControllerCreazionePartita.p.getTerreno().put(utentiOgg.get(turni),(CartaTerreno)tempEffetto);
        	 cartaTerreno.setImage(tempEffetto.getImmagine());
        	 if(!(utentiOgg.get(turni)instanceof RobotIntelligente)){
        		contaGiocate+=1;
             	aggiornamentoMano();
             }
        	 timelineS+="/" + utentiOgg.get(turni).getNome().toUpperCase() +"\nCarta giocata: " + tempEffetto.getNome();
             aggiornaTimeline();
        }
        
        });
        	
        });     
       }
  
    private void ricalcoloBuffEquip(Utente u) { //l'idea è che dopo l'attacco  vengono ricalcolati i nuovi buff alle nuove condizioni  
    	for(CartaPersonaggio c:ControllerCreazionePartita.p.getPersonaggiScelti().get(u)) {
    		for(CartaEquipaggiamento e:c.getEquip()) { 			
    			c.lessAtt(e.getAttAggiuntoPrec());
    			c.lessPs(e.getPsAggiuntiPrec());
    			e.effetto(u, c);   			
    		}  		
    	}
    }
     //Metodo per annullare gli effetti delgli equipaggiamenti
    private void annullamentoEffettiEquip(CartaPersonaggio c) {
    	for(CartaEquipaggiamento e:c.getEquip()) { 			
			c.lessAtt(e.getAttAggiuntoPrec());
			c.lessPs(e.getPsAggiuntiPrec());			
    	}
    }
    
    //Metodo per aggiornare il campo dell'utente corrente
    private void aggiornaCampoCorrente(Utente u) {
    	List<CartaPersonaggio> personaggiCorrenti = ControllerCreazionePartita.p.getPersonaggiScelti().get(utentiOgg.get(turni));
	    
	    if (personaggiCorrenti.size() >= 1) {
	        pgCorrente1.setImage(personaggiCorrenti.get(0).getImmagine());
	        setPopUp(pgCorrente1, utentiOgg.get(turni));
	        pgCorrente2.setImage(null);
	        pgCorrente3.setImage(null);
	        pgCorrente2.setVisible(false);
	        pgCorrente3.setVisible(false);
	    }
	    
	    if (personaggiCorrenti.size() >= 2) {
	    	pgCorrente1.setImage(personaggiCorrenti.get(0).getImmagine());
	        setPopUp(pgCorrente1, utentiOgg.get(turni));
	        pgCorrente2.setImage(personaggiCorrenti.get(1).getImmagine());
	        setPopUp(pgCorrente2, utentiOgg.get(turni));
	        pgCorrente3.setImage(null);
	        pgCorrente2.setVisible(true);
	        pgCorrente3.setVisible(false);
	    }
	    
	    if (personaggiCorrenti.size() >= 3) {
	    	pgCorrente1.setImage(personaggiCorrenti.get(0).getImmagine());
	        setPopUp(pgCorrente1, utentiOgg.get(turni));
	        pgCorrente2.setImage(personaggiCorrenti.get(1).getImmagine());
	        setPopUp(pgCorrente2, utentiOgg.get(turni));
	        pgCorrente3.setImage(personaggiCorrenti.get(2).getImmagine());
	        setPopUp(pgCorrente3, utentiOgg.get(turni));
	        pgCorrente2.setVisible(true);
	        pgCorrente3.setVisible(true);
	    }
    }

    private void resettaEventiMousePersonaggi() {
        pgCorrente1.setOnMouseClicked(event -> handleSelezionePersonaggio(pgCorrente1));
        pgCorrente2.setOnMouseClicked(event -> handleSelezionePersonaggio(pgCorrente2));
        pgCorrente3.setOnMouseClicked(event -> handleSelezionePersonaggio(pgCorrente3));
    }
    
    /*
     * Metodo per attivare gli effetti delle carte imprevisto quando vengono pescate
     * oppure si cambia turno e le si ha subito in mano
     */
    public void attivazioneImprevisti() {       
    	 ArrayList<CartaGiocabile> carteDaRimuovere = new ArrayList<>();
    		  for(CartaGiocabile c:ControllerCreazionePartita.p.getMano().get(utentiOgg.get(turni))) {
    			  if(c instanceof CartaImprevisto) {
    				  if(c.getTarget()==1) {
    				      c.effetto3(utentiOgg.get(turni));
    				      carteDaRimuovere.add(c);    				      
    				  }
    				  else {
    					  c.effettoImprevisto(utentiOgg.get(turni),bottoneAttacco);
    					  carteDaRimuovere.add(c);
    				  }
    			  }
    		  }   		  
    		  if (!carteDaRimuovere.isEmpty()) {
    			    ControllerCreazionePartita.p.getMano().get(utentiOgg.get(turni)).removeAll(carteDaRimuovere);
    			    if(!(utentiOgg.get(turni)instanceof RobotIntelligente)) {
    			    Pane contenitoreImprevisto=new Pane();
		            root.getChildren().add(contenitoreImprevisto);			            
    			    Timeline timeline = new Timeline();
    			    for (int i = 0; i < carteDaRimuovere.size(); i++) {
    			        CartaGiocabile carta = carteDaRimuovere.get(i);
    			        KeyFrame keyFrame = new KeyFrame(Duration.seconds(1.5 * (i + 1)), event -> {    			           
    			            contenitoreImprevisto.setPrefHeight(367);
    			            contenitoreImprevisto.setPrefWidth(225);
    			            contenitoreImprevisto.setLayoutX(27);
    			            contenitoreImprevisto.setLayoutY(229);
    			            contenitoreImprevisto.setStyle("-fx-background-color: purple;");
    			            ImageView imgImprevisto=new ImageView(carta.getImmagine());
    			            contenitoreImprevisto.getChildren().add(imgImprevisto);
    			            imgImprevisto.setFitWidth(162);
    			            imgImprevisto.setFitHeight(284);   
    			            imgImprevisto.setLayoutX(32);
    			            imgImprevisto.setLayoutY(42);
    			            
    			        });
    			        timeline.getKeyFrames().add(keyFrame);
       	    		 	timelineS+="/"+ utentiOgg.get(turni).getNome().toUpperCase() + "\nCarta Imprevisto pescata: "+ carta.getNome();

    			    }
   			   
    			    KeyFrame finale = new KeyFrame(Duration.seconds(1.5 * (carteDaRimuovere.size() + 1)), event -> {
    			        root.getChildren().remove(contenitoreImprevisto);
    			        aggiornamentoMano();
    			    });
    			    timeline.getKeyFrames().add(finale);

    			    timeline.play();   
    			    }
    			    else {
    			    	Pane contenitoreImprevisto=new Pane();
    		            root.getChildren().add(contenitoreImprevisto);			            
        			    Timeline timeline = new Timeline();
        			    for (int i = 0; i < carteDaRimuovere.size(); i++) {
        			        CartaGiocabile carta = carteDaRimuovere.get(i);
        			        KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.5 * (i + 1)), event -> {    			           
        			            contenitoreImprevisto.setPrefHeight(367);
        			            contenitoreImprevisto.setPrefWidth(225);
        			            contenitoreImprevisto.setLayoutX(27);
        			            contenitoreImprevisto.setLayoutY(229);
        			            contenitoreImprevisto.setStyle("-fx-background-color: purple;");
        			            ImageView imgImprevisto=new ImageView(carta.getImmagine());
        			            contenitoreImprevisto.getChildren().add(imgImprevisto);
        			            imgImprevisto.setFitWidth(162);
        			            imgImprevisto.setFitHeight(284);   
        			            imgImprevisto.setLayoutX(32);
        			            imgImprevisto.setLayoutY(42);
        			            
        			        });
        			        timeline.getKeyFrames().add(keyFrame);
           	    		 	timelineS+="/"+ utentiOgg.get(turni).getNome().toUpperCase() + "\nCarta Imprevisto pescata: "+ carta.getNome();

        			    }
       			   
        			    KeyFrame finale = new KeyFrame(Duration.seconds(0.5 * (carteDaRimuovere.size() + 1)), event -> {
        			        root.getChildren().remove(contenitoreImprevisto);
        			        aggiornamentoMano();
        			    });
        			    timeline.getKeyFrames().add(finale);

        			    timeline.play(); 

    			    }
    			    aggiornaTimeline();
    			}
    }

    /*
     * Metodo per selezionare un utetne casuale
     */
    public Utente selezionaUtenteCasuale(List<Utente> utenti, Utente utenteEscluso) {        
        ArrayList<Utente> utentiDisponibili = new ArrayList<>(utenti);
        utentiDisponibili.remove(utenteEscluso);      
        if (utentiDisponibili.isEmpty()) {
            return null;
        }       
        Random random = new Random();
        int indiceCasuale = random.nextInt(utentiDisponibili.size());       
        return utentiDisponibili.get(indiceCasuale);
    }
    
    public void salvaPartita() {
    	String codice=ControllerCreazionePartita.p.getCodice();
    	int numGiocatori=ControllerCreazionePartita.p.getNumGioc();
    	String nomiGiocatori="";
    	for(Utente u:utentiOgg) {
    		nomiGiocatori+=u.getNome()+" ";
    	}
    	String mazzo=ControllerCreazionePartita.p.getMazzoString();
    	int turnoGioc=turni;
    	int turnoTot=turniTotale;
    	String timeline="";
    	String carteManoGioc1=ControllerCreazionePartita.p.getCarteManoGioc1String(utentiOgg);
    	String carteManoGioc2=ControllerCreazionePartita.p.getCarteManoGioc2String(utentiOgg);
    	String carteManoGioc3=ControllerCreazionePartita.p.getCarteManoGioc3String(utentiOgg);
    	String carteManoGioc4=ControllerCreazionePartita.p.getCarteManoGioc4String(utentiOgg);
    	String cartePersGioc1=ControllerCreazionePartita.p.getCartePersGioc1(utentiOgg);
    	String cartePersGioc2=ControllerCreazionePartita.p.getCartePersGioc2(utentiOgg);
    	String cartePersGioc3=ControllerCreazionePartita.p.getCartePersGioc3(utentiOgg);
    	String cartePersGioc4=ControllerCreazionePartita.p.getCartePersGioc4(utentiOgg);
    	String cartaTerrGioc1=ControllerCreazionePartita.p.getCartaTerrGioc1String(utentiOgg);
    	String cartaTerrGioc2=ControllerCreazionePartita.p.getCartaTerrGioc2String(utentiOgg);
    	String cartaTerrGioc3=ControllerCreazionePartita.p.getCartaTerrGioc3String(utentiOgg);
    	String cartaTerrGioc4=ControllerCreazionePartita.p.getCartaTerrGioc4String(utentiOgg);
    	JDBCPartite.salvaPartitaInCorso(codice, numGiocatori, nomiGiocatori, mazzo, turnoGioc, turnoTot, timeline, carteManoGioc1, carteManoGioc2, carteManoGioc3, carteManoGioc4, cartePersGioc1, cartePersGioc2, cartePersGioc3, cartePersGioc4, cartaTerrGioc1, cartaTerrGioc2, cartaTerrGioc3, cartaTerrGioc4);
    	labelTurni.setText("Partita salvata con successo");
    	PauseTransition pause = new PauseTransition(Duration.millis(2000));
        pause.setOnFinished(event2 -> {
        	labelTurni.setText(utentiOgg.get(turni).getNome()+", è il tuo turno");
        });
    	pause.play();
    }
    
    public void salvaTorneo() {
    	JDBCTornei.aggiornaTorneo(ControllerCreazionePartita.t);
    	salvaPartita();
    }
	 /*
	  * Nell'initialize vengono richiamati i vari metodi per mostrare le varie immagini, come aggiornamentoMano
	  * Setta i valori di turni nel caso in cui la partita sia caricata
	  */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {  
	    try {
	        utentiOgg=ControllerCreazionePartita.p.getListaUtenti();	        
	        vboxTimeline.setStyle("-fx-background-color: #3498db;");
	        time1.setVisible(false);
			time2.setVisible(false);
			time3.setVisible(false);
			time4.setVisible(false);
			time5.setVisible(false);
			time6.setVisible(false);
			time7.setVisible(false);
	        /*double startX = 0;
	        double startY = 88;
	        double control1X = 200;
	        double control1Y = -50;
	        double control2X = 300;
	        double control2Y = -80;
	        double endX = 600;
	        double endY = 88;	        
	        curve = new CubicCurve();
	        curve.setStartX(startX);
	        curve.setStartY(startY);
	        curve.setControlX1(control1X);
	        curve.setControlY1(control1Y);
	        curve.setControlX2(control2X);
	        curve.setControlY2(control2Y);
	        curve.setEndX(endX);
	        curve.setEndY(endY);
	        curve.setStroke(Color.BLACK);
	        curve.setStrokeWidth(2);
	        curve.setFill(Color.TRANSPARENT);
  	        campoCarteGiocabili.getChildren().add(curve);
	        curve.setVisible(false);*/
	        if(ControllerCreazionePartita.p.getTorneo()) {
	        	bottoneSalvataggio.setVisible(false);
	        	bottoneSalvataggio2.setVisible(true);
	        }
	        
	        
	        if(ControllerCreazionePartita.p.getTurnoTot()>=1) {	        			        	
	        	turni=ControllerCreazionePartita.p.getTurnoGioc();
	        	aggiornamentoMano();
	        	turniTotale=ControllerCreazionePartita.p.getTurnoTot();
	        	int sizeTemp=ControllerCreazionePartita.p.getPersonaggiScelti().get(utentiOgg.get(turni)).size();
	        	if(sizeTemp>=3) {
	        		pgCorrente1.setImage(ControllerCreazionePartita.p.getPersonaggiScelti().get(utentiOgg.get(turni)).get(0).getImmagine());
	     	        setPopUp(pgCorrente1,utentiOgg.get(turni));
	     	        pgCorrente2.setImage(ControllerCreazionePartita.p.getPersonaggiScelti().get(utentiOgg.get(turni)).get(1).getImmagine());
	     	        setPopUp(pgCorrente2,utentiOgg.get(turni));
	     	        pgCorrente3.setImage(ControllerCreazionePartita.p.getPersonaggiScelti().get(utentiOgg.get(turni)).get(2).getImmagine());
	     	        setPopUp(pgCorrente3,utentiOgg.get(turni));
	     	       pgCorrente1.setOnMouseClicked(event -> handleSelezionePersonaggio(pgCorrente1));
	               pgCorrente2.setOnMouseClicked(event -> handleSelezionePersonaggio(pgCorrente2));
	               pgCorrente3.setOnMouseClicked(event -> handleSelezionePersonaggio(pgCorrente3));  
	        	}
	        	else if(sizeTemp==2) {
	        		pgCorrente1.setImage(ControllerCreazionePartita.p.getPersonaggiScelti().get(utentiOgg.get(turni)).get(0).getImmagine());
	     	        setPopUp(pgCorrente1,utentiOgg.get(turni));
	     	        pgCorrente2.setImage(ControllerCreazionePartita.p.getPersonaggiScelti().get(utentiOgg.get(turni)).get(1).getImmagine());
	     	        setPopUp(pgCorrente2,utentiOgg.get(turni));	   
	     	       pgCorrente1.setOnMouseClicked(event -> handleSelezionePersonaggio(pgCorrente1));
	               pgCorrente2.setOnMouseClicked(event -> handleSelezionePersonaggio(pgCorrente2));	                
	        	}
	        	else {
	        		pgCorrente1.setImage(ControllerCreazionePartita.p.getPersonaggiScelti().get(utentiOgg.get(turni)).get(0).getImmagine());
	     	        setPopUp(pgCorrente1,utentiOgg.get(turni));
	     	       pgCorrente1.setOnMouseClicked(event -> handleSelezionePersonaggio(pgCorrente1));             
	        	}
	        	
	        	for(Utente u:utentiOgg) {	        		
	        		for(CartaPersonaggio p:ControllerCreazionePartita.p.getPersonaggiScelti().get(u)) {        			
	        			for(CartaEquipaggiamento e:p.getEquip()) {	        				
	        				e.ricalcolo(u,p);
	        			}
	        		}
	        	}
	        }
	        else {
	        ControllerCreazionePartita.p.inizializzaMano();
	        ControllerCreazionePartita.p.stampaMazzo();  
	        ControllerCreazionePartita.p.stampaMano();
	        turni=0;
	        turniTotale=1;
	        pgCorrente1.setImage(ControllerCreazionePartita.p.getPersonaggiScelti().get(utentiOgg.get(turni)).get(0).getImmagine());
	        setPopUp(pgCorrente1,utentiOgg.get(turni));
	        pgCorrente2.setImage(ControllerCreazionePartita.p.getPersonaggiScelti().get(utentiOgg.get(turni)).get(1).getImmagine());
	        setPopUp(pgCorrente2,utentiOgg.get(turni));
	        pgCorrente3.setImage(ControllerCreazionePartita.p.getPersonaggiScelti().get(utentiOgg.get(turni)).get(2).getImmagine());
	        setPopUp(pgCorrente3,utentiOgg.get(turni));	      
	        aggiornamentoMano();
	        pgCorrente1.setOnMouseClicked(event -> handleSelezionePersonaggio(pgCorrente1));
            pgCorrente2.setOnMouseClicked(event -> handleSelezionePersonaggio(pgCorrente2));
            pgCorrente3.setOnMouseClicked(event -> handleSelezionePersonaggio(pgCorrente3)); 
	        }	        	        	        		           	          
           
        	paneSceltaAvv.setPrefWidth(campoCorrente.getPrefWidth());            
        	paneSceltaAvv.setPrefHeight(root.getPrefHeight());
        	paneSceltaAvv.setStyle("-fx-background-color: beige;");
        	root.getChildren().add(paneSceltaAvv);   
        	paneSceltaAvv.setVisible(false);
        	utentiBersaglio=new ArrayList<Utente>();
        	utentiBersaglio.addAll(utentiOgg);
            
            mazzo.setOnMouseEntered(event -> {
                mazzo.setCursor(Cursor.HAND);
            });
            
            mazzo.setOnMouseClicked(event -> {
                try {
                    pescaCheck();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
	        
	        labelTurni.setText(utentiOgg.get(turni).getNome()+", è il tuo turno");	   
	        
	        if(utentiOgg.get(turni) instanceof RobotIntelligente){
		    	esecuzioneTurnoBot();
		    }
	        aggiornaTimeline();
	        AnchorPane.setTopAnchor(quitButton, 10.0);
	        AnchorPane.setRightAnchor(quitButton, 10.0);
	        
	    } catch (Exception e) {
	        System.err.println("Errore durante il caricamento delle immagini.");
	        e.printStackTrace();
	    }
	}
	
	
	
	public void aggiornaTimeline() {
		String[] timeLine = timelineS.split("/");
		if(timeLine.length<2) {
			time1.setText("");
			time2.setText("");
			time3.setText("");
			time4.setText("");
			time5.setText("");
			time6.setText("");
			time7.setText("");
		}
		else if(timeLine.length ==2) {
			time1.setText(timeLine[1]);
			time2.setText("");
			time3.setText("");
			time4.setText("");
			time5.setText("");
			time6.setText("");
			time7.setText("");
		}
		else if(timeLine.length==3) {
			time1.setText(timeLine[1]);
			time2.setText(timeLine[2]);
			time3.setText("");
			time4.setText("");
			time5.setText("");
			time6.setText("");
			time7.setText("");
		}
		else if(timeLine.length==4) {
			time1.setText(timeLine[1]);
			time2.setText(timeLine[2]);
			time3.setText(timeLine[3]);
			time4.setText("");
			time5.setText("");
			time6.setText("");
			time7.setText("");
		}
		else if(timeLine.length==5) {
			time1.setText(timeLine[1]);
			time2.setText(timeLine[2]);
			time3.setText(timeLine[3]);
			time4.setText(timeLine[4]);
			time5.setText("");
			time6.setText("");
			time7.setText("");
		}
		else if(timeLine.length==6) {
			time1.setText(timeLine[1]);
			time2.setText(timeLine[2]);
			time3.setText(timeLine[3]);
			time4.setText(timeLine[4]);
			time5.setText(timeLine[5]);
			time6.setText("");
			time7.setText("");
		}
		else if(timeLine.length==7) {
			time1.setText(timeLine[1]);
			time2.setText(timeLine[2]);
			time3.setText(timeLine[3]);
			time4.setText(timeLine[4]);
			time5.setText(timeLine[5]);
			time6.setText(timeLine[6]);
			time7.setText("");

		}
		else if(timeLine.length==8) {
			time1.setText(timeLine[1]);
			time2.setText(timeLine[2]);
			time3.setText(timeLine[3]);
			time4.setText(timeLine[4]);
			time5.setText(timeLine[5]);
			time6.setText(timeLine[6]);
			time7.setText(timeLine[7]);
		}
		else {
			time1.setText(timeLine[timeLine.length-7]);
			time2.setText(timeLine[timeLine.length-6]);
			time3.setText(timeLine[timeLine.length-5]);
			time4.setText(timeLine[timeLine.length-4]);
			time5.setText(timeLine[timeLine.length-3]);
			time6.setText(timeLine[timeLine.length-2]);
			time7.setText(timeLine[timeLine.length-1]);
		}
		
	}
	
	public void openTimelineMenu(MouseEvent event) {
		vboxTimeline.setOnMouseEntered(e -> {
			vboxTimeline.setMinWidth(300);
			vboxTimeline.setMaxWidth(300);
			time1.setVisible(true);
			time2.setVisible(true);
			time3.setVisible(true);
			time4.setVisible(true);
			time5.setVisible(true);
			time6.setVisible(true);
			time7.setVisible(true);
		});
		
	}
	
	public void closeTimelineMenu(MouseEvent event) {
		vboxTimeline.setOnMouseExited(e ->{
			vboxTimeline.setMinWidth(60);
			vboxTimeline.setMaxWidth(60);
			time1.setVisible(false);
			time2.setVisible(false);
			time3.setVisible(false);
			time4.setVisible(false);
			time5.setVisible(false);
			time6.setVisible(false);
			time7.setVisible(false);
			
		});
		
	}
	
	/*
	 * Metodo per tornare al menu principale
	 * Se si seleziona "SI" si torna al menu principale ed i progressi non salvati vanno perduti
	 * Altrimenti si continua a giocare
	 */
	public void quitta() {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Conferma ritorno al menu principale");
        alert.setHeaderText(null);
        alert.setContentText("Vuoi tornare al menu principale? Tutti i progressi non salvati andranno perduti");
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getScene().getRoot().setStyle("-fx-background-color: #3498db;");
        
        ButtonType buttonTypeSi = new ButtonType("SI");
        ButtonType buttonTypeNo = new ButtonType("NO");

        alert.getButtonTypes().setAll(buttonTypeSi, buttonTypeNo);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeSi) {
        	try {
        	Main m = new Main();
            m.setScena("SCENE/SCENA-SCHERMATA-INIZIALE-2.fxml");
            m.resetDimensioniOriginali();
            ControllerCreazionePartita.p=null;
            ControllerCreazionePartita.t=null;
        	}
        	catch(IOException e) {
        		System.out.println("Eccezione di input/output");
        		e.printStackTrace();
        	}
        }      
	}
	
}
	
