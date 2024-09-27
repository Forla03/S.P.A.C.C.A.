package application.CONTROLLER;

import java.awt.AWTException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import application.CLASSI.CartaPersonaggio;
import application.CLASSI.JDBCCaricamento;
import application.CLASSI.RobotIntelligente;
import application.CLASSI.TipoRaritaCartaPersonaggio;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;
import application.CLASSI.Utente;
import java.util.concurrent.atomic.AtomicInteger;
import javafx.animation.PauseTransition;
import javafx.concurrent.Task;



public class Controller2SceltaPersonaggi implements Initializable {
	
	@FXML
    private Text numCartPrec;
	
    @FXML
    private Text numCartPrinc;
    
    @FXML
    private Text numCartSucc;
    
    @FXML
    private ImageView principale;
    
    @FXML
    private ImageView imm1;
    
    @FXML
    private ImageView imm2;
    
    @FXML
    private Label labelTitolo;
    
    @FXML
    private Button bottonePartita;
    
    @FXML
    private Button bottonePrecedente;
    
    @FXML
    private Button bottoneSuccessivo;
    
    @FXML
    private Label labelCarta;
    
    ArrayList<String>url=new ArrayList<>();
    private ArrayList<CartaPersonaggio> carteCaricate;
    private HashMap<Integer, CartaPersonaggio> carte = new HashMap<>();
    private int indice =1;
    private boolean cartaSpeciale = false;
    
    public void goBack(ActionEvent event) throws IOException {
        goBackCheck();
    }

    public void goBackCheck() throws IOException {
       try {
    	Main m = new Main();
        if(ControllerCreazionePartita.p.getTorneo()) {
            m.setScena("SCENE/SCENA-TORNEO.fxml");
        }
        else {
        	m.setScena("SCENE/SCENA-SCHERMATA-INIZIALE-2.fxml");
        }
       }
       catch(Exception e) {
       	gestisciEccezione(e);
       }
    }
    
   public ArrayList<CartaPersonaggio> sceltaCarte(ArrayList<String>url) {
	  
	   ArrayList<CartaPersonaggio>scelte=new ArrayList<>();
	   if (url.size()>3) {
		   System.out.println("Troppi url immessi");
		   return scelte;
	   }
	   else {
		   for(CartaPersonaggio cp:carteCaricate) {
			   if(url.contains(cp.getUrlImmagine())) {
				   CartaPersonaggio c=new CartaPersonaggio(cp.getNome(),cp.getMitologia(),cp.getDescrizione(),cp.getAtt(),cp.getDef(),cp.getImmagine(),cp.getRarita(),cp.getAttAttuale(),cp.getPsAttuali());
				   scelte.add(c);
			   }
		   }
		   
		   return scelte;
	   }
	   
   }
    
    public void successivo() {
        try {
    	indice++;

        // Se l'indice supera la dimensione della lista, riparti da 1
        if (indice > carte.size()) {
            indice = 1;
        }

        // Calcola gli indici della carta corrente, precedente e successiva
        int indicePrecedente = indice - 1;
        int indiceSuccessivo = indice + 1;

        // Gestisci il "wrap-around" per la carta precedente e successiva
        if (indicePrecedente < 1) {
            indicePrecedente = carte.size();
        }
        if (indiceSuccessivo > carte.size()) {
            indiceSuccessivo = 1;
        }

        // Aggiorna le immagini delle carte
        aggiornaImmaginiCarte(indice, indicePrecedente, indiceSuccessivo);

        // Aggiorna i numeri delle carte
        numCartPrinc.setText(String.valueOf(indice));
        numCartPrec.setText(String.valueOf(indicePrecedente));
        numCartSucc.setText(String.valueOf(indiceSuccessivo));
        }
        catch(Exception e) {
        	gestisciEccezione(e);
        }
    }
    
    public void precedente() {
        try {
    	indice--;

        // Se l'indice diventa minore di 1, spostati all'ultima carta
        if (indice < 1) {
            indice = carte.size();
        }

        // Calcola gli indici della carta corrente, precedente e successiva
        int indicePrecedente = indice - 1;
        int indiceSuccessivo = indice + 1;

        // Gestisci il "wrap-around" per la carta precedente e successiva
        if (indicePrecedente < 1) {
            indicePrecedente = carte.size();
        }
        if (indiceSuccessivo > carte.size()) {
            indiceSuccessivo = 1;
        }

        // Aggiorna le immagini delle carte
        aggiornaImmaginiCarte(indice, indicePrecedente, indiceSuccessivo);

        // Aggiorna i numeri delle carte
        numCartPrinc.setText(String.valueOf(indice));
        numCartPrec.setText(String.valueOf(indicePrecedente));
        numCartSucc.setText(String.valueOf(indiceSuccessivo));
        }
        catch(Exception e) {
        	gestisciEccezione(e);
        }
    }
    
    private void aggiornaImmaginiCarte(int indiceCorrente, int indicePrecedente, int indiceSuccessivo) {
        try {
    	CartaPersonaggio temp=carte.get(indiceCorrente);
    	Image immCorrente = temp.getImmagine();
        principale.setImage(immCorrente);

        temp=carte.get(indicePrecedente);
    	Image immPrecedente = temp.getImmagine();
        imm1.setImage(immPrecedente);

        temp=carte.get(indiceSuccessivo);
    	Image immSuccessiva = temp.getImmagine();        
    	imm2.setImage(immSuccessiva);
        }
        catch(Exception e) {
        	gestisciEccezione(e);
        }
    	
    }
    
    public void entraInPartita(ActionEvent event) throws IOException {
    	Main m=new Main();
    	m.setScena("SCENE/SCENA-PARTITA.fxml");
    }
    
    private boolean verificaUnaSolaSpeciale(ArrayList<String> urlCliccati) {
        int contaSpeciali = 0;
        TipoRaritaCartaPersonaggio t = TipoRaritaCartaPersonaggio.SPECIALE;
        
        for (String url : urlCliccati) {
            for (CartaPersonaggio cp : carteCaricate) {
                if (url.equals(cp.getUrlImmagine()) && cp.getRarita() == t) {
                    contaSpeciali += 1;
                    if (contaSpeciali > 1) {
                        return false; 
                    }
                }
            }
        }
        
        return true;
    }
    
    private boolean verificaSpeciale(String url) {
        TipoRaritaCartaPersonaggio t = TipoRaritaCartaPersonaggio.SPECIALE;
        
        for (CartaPersonaggio cp : carteCaricate) {
            if (url.equals(cp.getUrlImmagine()) && cp.getRarita() == t) {
                return true;
            }
        }
        
        return false;
    }
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        try {
            List<Utente> utentiOgg = ControllerCreazionePartita.p.getListaUtenti();
            labelTitolo.setText(utentiOgg.get(0).getNome() + ", scegli 3 personaggi");

            carteCaricate = JDBCCaricamento.getCartePersonaggio();

            for (int i = 0; i < carteCaricate.size(); i++) {
                CartaPersonaggio carta = carteCaricate.get(i);
                carte.put(i + 1, carta);
            }
            for (int i = 0; i < carteCaricate.size(); i++) {
                url.add(carteCaricate.get(i).getUrlImmagine());
            }

            principale.setOnMouseEntered(event -> {
                principale.setCursor(Cursor.HAND);
            });

            principale.setOnMouseExited(event -> {
                principale.setCursor(Cursor.DEFAULT);
            });

            ArrayList<String> urlCliccati = new ArrayList<>();
            AtomicInteger contatore = new AtomicInteger(0);
            labelTitolo.setText("" + utentiOgg.get(contatore.get()).getNome() + ", scegli 3 personaggi");

            principale.setOnMouseClicked(event -> {
                labelCarta.setText("");
                if (contatore.get() < utentiOgg.size()) {
                    if (indice <= carte.size()) {
                        String urlSelezionato = url.get(indice - 1);
                        if (!urlCliccati.contains(urlSelezionato)) {
                            if (verificaSpeciale(urlSelezionato)) {
                                if (!cartaSpeciale) {
                                    urlCliccati.add(urlSelezionato);
                                    cartaSpeciale = true;
                                } else {
                                    //System.out.println("Puoi selezionare solo una carta speciale");
                                    labelCarta.setText("Puoi selezionare solo una carta speciale");
                                    labelCarta.setTextFill(Color.RED);
                                }
                            } else {
                                urlCliccati.add(urlSelezionato);
                            }
                        } else {
                            //System.out.println("Carta per l'utente gia' selezionata");
                            labelCarta.setText("Carta per l'utente gia' selezionata");
                            labelCarta.setTextFill(Color.RED);
                        }

                        if (urlCliccati.size() == 3) {
                            ControllerCreazionePartita.p.aggiungiPersonaggi(utentiOgg.get(contatore.getAndIncrement()), sceltaCarte(urlCliccati));
                            if (contatore.get() < utentiOgg.size()) {
                                labelTitolo.setText(utentiOgg.get(contatore.get()).getNome() + ", scegli 3 personaggi"); 
                                if (utentiOgg.get(contatore.get()) instanceof RobotIntelligente) {
                                    Task<Void> task = new Task<Void>() {
                                        @Override
                                        protected Void call() throws Exception {
                                            ((RobotIntelligente) utentiOgg.get(contatore.get())).sceltaPersonaggi(principale, bottoneSuccessivo,labelCarta);
                                            return null;
                                        }
                                    };

                                    task.setOnFailed(e -> {
                                        task.getException().printStackTrace();
                                        System.out.println("Errore durante l'esecuzione del metodo sceltaPersonaggi del robot");
                                    });

                                    Thread thread = new Thread(task);
                                    thread.setDaemon(true); // Imposta il thread come daemon per terminarlo quando termina l'applicazione
                                    thread.start();
                                }
                            }
                            urlCliccati.clear();
                            cartaSpeciale = false;
                        }
                    }

                    if (contatore.get() == utentiOgg.size()) {
                        labelTitolo.setText("Ora è tutto pronto, giocate!");
                        System.out.println(ControllerCreazionePartita.p.getPersonaggiScelti().toString());
                        numCartPrec.setVisible(false);
                        numCartPrinc.setVisible(false);
                        numCartSucc.setVisible(false);
                        principale.setVisible(false);
                        imm1.setVisible(false);
                        imm2.setVisible(false);
                        bottonePartita.setVisible(true);
                        bottonePrecedente.setVisible(false);
                        bottoneSuccessivo.setVisible(false);
                    }
                } else {
                    System.out.println("Scelta personaggi completa");
                }
            });

            System.out.println(ControllerCreazionePartita.p.getPersonaggiScelti().toString());
            numCartPrinc.setText("1");
            numCartPrec.setText(carte.size() + "");
            numCartSucc.setText("2");
            aggiornaImmaginiCarte(indice, carte.size(), indice + 1);
            
            PauseTransition pause = new PauseTransition(Duration.millis(500));
            pause.setOnFinished(event -> {
                if (utentiOgg.get(0) instanceof RobotIntelligente) {
                    Task<Void> task = new Task<Void>() {
                        @Override
                        protected Void call() throws Exception {
                            ((RobotIntelligente) utentiOgg.get(contatore.get())).sceltaPersonaggi(principale, bottoneSuccessivo,labelCarta);
                            return null;
                        }
                    };

                    task.setOnFailed(e -> {
                        task.getException().printStackTrace();
                        System.out.println("Errore durante l'esecuzione del metodo sceltaPersonaggi del robot");
                    });

                    Thread thread = new Thread(task);
                    thread.setDaemon(true);
                    thread.start();
                }
            });
            pause.play();
        }
        catch(Exception e) {
        	gestisciEccezione(e);
        }
    }
    
    private void gestisciEccezione(Exception e) {
        if (e instanceof AWTException) {
            System.out.println("Si è verificata un'eccezione AWT: " + e.getMessage());
        } else if (e instanceof IOException) {
            System.out.println("Si è verificata un'eccezione di I/O: " + e.getMessage());
        } else if (e instanceof SQLException) {
            System.out.println("Si è verificata un'eccezione SQL: " + e.getMessage());
        } else if (e instanceof IndexOutOfBoundsException) {
            System.out.println("Si è verificata un'eccezione di indicizzazione fuori limite: " + e.getMessage());
        } else if (e instanceof NullPointerException) {
            System.out.println("Si è verificata un'eccezione di puntatore nullo: " + e.getMessage());
        } else {
            System.out.println("Si è verificata un'eccezione generica: " + e.getMessage());
        }
        e.printStackTrace();
    }


}
    
    
    
            
           