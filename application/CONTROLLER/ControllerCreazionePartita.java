package application.CONTROLLER;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import application.Main;
import application.CLASSI.JDBCCaricamento;
import application.CLASSI.JDBCPartite;
import application.CLASSI.JDBCTornei;
import application.CLASSI.JDBCUtenti;
import application.CLASSI.Partita;
import application.CLASSI.RobotIntelligente;
import application.CLASSI.Torneo;
import application.CLASSI.Utente;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.awt.AWTException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.QuadCurve;
import javafx.scene.shape.Rectangle;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.animation.PauseTransition;
import javafx.util.Duration;
import javafx.scene.control.ButtonType;
import java.util.HashSet;
import java.util.Set;
import javafx.stage.Stage;


public class ControllerCreazionePartita implements Initializable{
	

	   	@FXML
	    private Button backButton;

	   	@FXML
	   	private Rectangle sideMenu;
	   	
	   	@FXML
	   	private Button buttonSideGestisciUtenti;
	   	
	   	@FXML
	   	private Button buttonSideCaricaPartita;
	   	
	   	@FXML
	   	private Button buttonSideCreaPartita;
	   	
	   	@FXML
	   	private Button buttonSideGestisciCarte;
	   	
	   	@FXML
	   	private Button buttonSideCreaTorneo;
	   
	    @FXML
	    private Button bottoneCreaPartita;

	    @FXML
	    private Button buttonAggGiocTorneo;

	    @FXML
	    private Button buttonAggGiocatorePart;

	    @FXML
	    private Button buttonCaricaPartita;

	    @FXML
	    private Button buttonCreaTorneo;

	    @FXML
	    private Button buttonCreaUtente;
	    
	    @FXML
	    private Button buttonEliminaUtente;

	    @FXML
	    private Text codicePartita;

	    @FXML
	    private Text confermaAggiunta;

	    @FXML
	    private Text errortext;

	    @FXML
	    private Label labelGenerazione;

	    @FXML
	    private ListView<String> listaUtenti=new ListView<String>();

	    @FXML
	    private Slider numGiocatoriTorneo;

	    @FXML
	    private Slider numGiocatoriPart;

	    @FXML
	    private HBox riquadroCodPartita;

	    @FXML
	    private HBox riquadroCodiceGen;

	    @FXML
	    private HBox riquadroLabelGioc;

	    @FXML
	    private VBox riquadroSlider;

	    @FXML
	    private HBox riquadroTextSlider;

	    @FXML
	    private StackPane sfondoCreaPartita;

	    @FXML
	    private Text testo;

	    @FXML
	    private Text testoUtentePart;

	    @FXML
	    private TextField textNomeUtente;
	    
	    @FXML
	    private CubicCurve curvaUtenti1;
	    
	    @FXML
	    private CubicCurve curvaUtenti2;
	    
	    @FXML
	    private CubicCurve curvaTorneo1;
	    
	    @FXML
	    private CubicCurve curvaTorneo2;
	    
	    @FXML
	    private CubicCurve curvaCreaP1;
	    
	    @FXML
	    private CubicCurve curvaCreaP2;
	    
	    @FXML 
	    private ListView<String> codiciTornei=new ListView<String>();

	    @FXML
	    private CubicCurve curvaCarte1;
	    
	    @FXML
	    private CubicCurve curvaCarte2;
	    
	    @FXML
	    private CubicCurve curvaCaricaP1;
	    
	    @FXML
	    private CubicCurve curvaCaricaP2;
	    
	    @FXML
	    private ImageView iconaCarte;
	    
	    @FXML
	    private ImageView iconaCreaP;
	    
	    @FXML
	    private ImageView iconaTorneo;
	    
	    @FXML
	    private ImageView iconaCaricaP;
	    
	    @FXML
	    private ImageView iconaUtenti;
	    
	    @FXML
	    private Button bottonCaricaTorneo;
	    
	    @FXML
	    private VBox riquadroSlider2;
	    
	    @FXML
	    private Text textSelezioneUtente;
	    
	    @FXML
	    private Text confermaEliminazione;
	    
	    @FXML
	    private Button rimuoviPartita;
	    
	    @FXML
	    private Button rimuoviTorneo;
	    
	    @FXML
	    private Button buttonApriSide;
	    
	    @FXML
	    private ImageView freccia;
	    
	    @FXML
	    private HBox textIniziale;
	    
	    

    public static Partita p;
	
	@FXML
	private ListView<String> codiciPartite=new ListView<String>();
	public static Torneo t;
	private ObservableList<String> lista = FXCollections.observableArrayList();
	private String nomeGiocatoriPartita="";
	private static String codiceTorneo;
	private static String codicePart;
	private ObservableList<String> listaCodici=FXCollections.observableArrayList();
	private ObservableList<String> listaCodiciTornei=FXCollections.observableArrayList();
	private ArrayList<Utente> giocatori=new ArrayList<Utente>();
	private boolean checkSide=false;

	public void copiaCodiceClipboardPartita() throws IOException {
        codicePartita.setText(codicePart);
        labelGenerazione.setText("Clicca sul codice per copiarlo negli appunti");
        codicePartita.setOnMouseEntered(e -> {
            codicePartita.setCursor(javafx.scene.Cursor.HAND);
        });
        codicePartita.setOnMouseExited(e -> {
            codicePartita.setCursor(javafx.scene.Cursor.DEFAULT);
        });
        codicePartita.setOnMouseClicked(e -> {
            Clipboard clipboard = Clipboard.getSystemClipboard();
            ClipboardContent content = new ClipboardContent();
            content.putString(codicePart);
            clipboard.setContent(content);
            labelGenerazione.setText("Codice copiato correttamente");
        });
    }

    public String generaCodicePartita() throws IOException {
        Random rand = new Random();
        codicePart = "";
        boolean z = false;
        char temp;
        for (int i = 0; i < 8; i++) {
            z = rand.nextBoolean();
            if (z) {
                temp = (char) ('a' + rand.nextInt(26));
                codicePart += temp;
            } else {
            	codicePart += rand.nextInt(9);
            }
        }
        return codicePart;
    }
	
	
	public void creaUtente(){
		errortext.setVisible(true);
		String nome=textNomeUtente.getText();
		if(nome.contains(" ")) {
        	errortext.setText("Inserisci un username senza spazi");
        	errortext.setFill(Color.RED);
        	PauseTransition pauseTransition = new PauseTransition(Duration.seconds(3));
	        pauseTransition.setOnFinished(event -> {
	            errortext.setVisible(false);
	        });
	        errortext.setVisible(true); 
	        pauseTransition.play();
        }
		else if(nome.isEmpty()) {
			errortext.setText("Inserisci un username");
			errortext.setFill(Color.RED);
			PauseTransition pauseTransition = new PauseTransition(Duration.seconds(3));
	        pauseTransition.setOnFinished(event -> {
	        	errortext.setVisible(false);
	        });
	        errortext.setVisible(true); 
	        pauseTransition.play();
		}
		else if(controlloDoppioni(nome)==false){
			errortext.setText("Nome utente già utilizzato");
			errortext.setFill(Color.RED);
			PauseTransition pauseTransition = new PauseTransition(Duration.seconds(3));
	        pauseTransition.setOnFinished(event -> {
	        	errortext.setVisible(false);
	        });
	        errortext.setVisible(true); 
	        pauseTransition.play();
		}
		else {
			//errortext.setText("Utente creato correttamente");
			JDBCUtenti.inserisciGiocatore(nome, "");
			JDBCCaricamento.caricaUtenti();
			caricaUtenti();
			//initialize(null, null);
			lista.clear();
	        String[] temp = caricaUtenti();
	        for (int i = 0; i < temp.length; i++) {
	            lista.add(temp[i]);
	        }
			PauseTransition pauseTransition = new PauseTransition(Duration.seconds(3));
	        pauseTransition.setOnFinished(event -> {
	            errortext.setVisible(false);
	        });
	        errortext.setVisible(true);
	        pauseTransition.play();
		}
	}
	
	public void eliminaUtente() {
		String utenteSelezionato = listaUtenti.getSelectionModel().getSelectedItem();
		if (utenteSelezionato != null) {
			JDBCUtenti.eliminaUtente(utenteSelezionato);
            JDBCCaricamento.caricaUtenti();
            caricaUtenti();
            lista.clear();
	        String[] temp = caricaUtenti();
	        for (int i = 0; i < temp.length; i++) {
	            lista.add(temp[i]);
	        }
            //confermaEliminazione.setText("Utente eliminato correttamente");
		}
	}
	
	public boolean controlloDoppioni(String nome) {
	    String[] elenco = JDBCUtenti.getGiocatori();
	    boolean controllo = true;
	    for (int i = 0; i < elenco.length; i++) {
	        if (elenco[i].equals(nome)) {
	            controllo = false;
	            break; 
	        }
	    }
	    return controllo;
	}
	
	public String[] caricaUtenti() {
		JDBCCaricamento.caricaUtenti();
		String utenti=JDBCUtenti.stampaTuttiGiocatoriList();
		String[] split=utenti.split(" ");
		
		return split;
	}
	
	public String[]caricaCodici(){
		try {
			JDBCCaricamento.caricaPartita();
		} catch (AWTException e) {
			e.printStackTrace();
		}
		String s="";
		for(Partita x:JDBCCaricamento.getPartite()) {
			if(x.getTurnoTot()>=1 && x.getTorneo()==false) {
			s+=x.getCodice()+",";
			}
		}
		String[] split=s.split(",");
		return split;
	}
	
	public String[] caricaCodiciTornei() {
		String s="";
		for(Torneo x:JDBCCaricamento.getTornei()) {			
			s+=x.getCodice()+",";			
		}
		String[] split=s.split(",");
		return split;
	}
	
	public void aggiungiGiocPartita() throws IOException {
		String elementoSelezionato = listaUtenti.getSelectionModel().getSelectedItem();
		if(nomeGiocatoriPartita.contains(elementoSelezionato)) {
			confermaAggiunta.setText("Utente già aggiunto alla partita");
			confermaAggiunta.setFill(Color.RED);
			PauseTransition pauseTransition = new PauseTransition(Duration.seconds(3));
	        pauseTransition.setOnFinished(event -> {
	            confermaAggiunta.setVisible(false);
	        });
	        confermaAggiunta.setVisible(true); 
	        pauseTransition.play();
		}
		else {
			nomeGiocatoriPartita+=elementoSelezionato + " ";
			confermaAggiunta.setText("Elemento aggiunto con successo");
			confermaAggiunta.setFill(Color.BLACK);
			PauseTransition pauseTransition = new PauseTransition(Duration.seconds(3));
	        pauseTransition.setOnFinished(event -> {
	            confermaAggiunta.setVisible(false);
	        });
	        confermaAggiunta.setVisible(true); 
	        pauseTransition.play();
		}
	}
	
	public void caricaPartita() throws IOException {
		String elementoSelezionato = codiciPartite.getSelectionModel().getSelectedItem();
		for(Partita x:JDBCCaricamento.getPartite()) {
			if(x.getCodice().equals(elementoSelezionato)) {
				p=x;
				Clipboard clipboard = Clipboard.getSystemClipboard();
		        ClipboardContent content = new ClipboardContent();
		        content.putString(x.getCodice());
		        clipboard.setContent(content);
				break;
			}
		}
		
	}
	
	public void creaPartita() throws IOException, AWTException {
		p=null;
		confermaAggiunta.setVisible(true);
		confermaAggiunta.setText("");
		//String nomeGiocatoriPartitaNoSpazioFinale=nomeGiocatoriPartita.substring(0, nomeGiocatoriPartita.length() - 1);
	    String nomeGiocatoriPartitaNoSpazioFinale = nomeGiocatoriPartita.trim();
	    
	    if (nomeGiocatoriPartitaNoSpazioFinale.isEmpty()) {
	        int numGiocatori = (int) numGiocatoriPart.getValue();
	        for (int i = 0; i < numGiocatori; i++) {
	            nomeGiocatoriPartita += "BOT" + (i + 1) + " ";
	        }
	        p = new Partita(generaCodicePartita(), numGiocatori, nomeGiocatoriPartita.trim());
	        JDBCPartite.inserisciPartita(p);
	        copiaCodiceClipboardPartita();
	    } 
	    else if (nomeGiocatoriPartitaNoSpazioFinale.split(" ").length < numGiocatoriPart.getValue() && nomeGiocatoriPartitaNoSpazioFinale.split(" ").length>=1) {
	        //crea qua avviso per far aggiungere i bot
	        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
	        alert.setTitle("Conferma aggiunta bot");
	        alert.setHeaderText(null);
	        alert.setContentText("Il numero dei giocatori scelti è inferiore a quello selezionato, riempire con i bot?");
	        
	        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
	        stage.getScene().getRoot().setStyle("-fx-background-color: #3498db;");
	        
	        ButtonType buttonTypeSi = new ButtonType("SI");
	        ButtonType buttonTypeNo = new ButtonType("NO");

	        alert.getButtonTypes().setAll(buttonTypeSi, buttonTypeNo);

	        Optional<ButtonType> result = alert.showAndWait();
	        if (result.get() == buttonTypeSi){
	            // ... user chose SI
	            int size=(int)numGiocatoriPart.getValue()-nomeGiocatoriPartitaNoSpazioFinale.split(" ").length;
	            for(int i=0;i<size;i++) {
	                nomeGiocatoriPartita+="BOT"+(i+1)+" ";
	            }            
	            p=new Partita(generaCodicePartita(), (int)numGiocatoriPart.getValue(), nomeGiocatoriPartita.trim());
	            JDBCPartite.inserisciPartita(p);
	            copiaCodiceClipboardPartita();
	        } else {
	            // ... user chose NO or closed the dialog
	            confermaAggiunta.setText("Numero di giocatori diverso da quello selezionato");
	            confermaAggiunta.setFill(Color.RED);
	            PauseTransition pauseTransition = new PauseTransition(Duration.seconds(3));
		        pauseTransition.setOnFinished(event -> {
		            confermaAggiunta.setVisible(false);
		        });
		        confermaAggiunta.setVisible(true); 
		        pauseTransition.play();
	        }
	        
	    }

	    else if(nomeGiocatoriPartitaNoSpazioFinale.split(" ").length > numGiocatoriPart.getValue()) {
	    	confermaAggiunta.setText("Il numero di giocatori non corrisponde a quello selezionato, reinserire i giocatori");
	    	confermaAggiunta.setFill(Color.RED);
	    	nomeGiocatoriPartita="";
	    	PauseTransition pauseTransition = new PauseTransition(Duration.seconds(3));
	        pauseTransition.setOnFinished(event -> {
	            confermaAggiunta.setVisible(false);
	        });
	        confermaAggiunta.setVisible(true); 
	        pauseTransition.play();
	    }
	    
	    else {
	        p = new Partita(generaCodicePartita(), (int)numGiocatoriPart.getValue(), nomeGiocatoriPartitaNoSpazioFinale);
	        JDBCPartite.inserisciPartita(p);
	        copiaCodiceClipboardPartita();
	    }	    
	}
	
	public static Partita getPartita() {
		return p;
	}
	
	public static String getCodicePartita() {
		return codicePart;
	}
	
	public void creaCarte() throws IOException {
		Main m= new Main();
		m.setScena("SCENE/SCENA-AGG-RIM-CARTE.fxml");
	}
	
	public void goBack() throws IOException{
		Main m = new Main();
    	m.setScena("SCENE/SCENA-SCHERMATA-INIZIALE-2.fxml");
    }
	
    public void abilitaTorneo() {
    	p.torneoOn();
    }
    
    public void rimuoviPartita() throws IOException {
        String codiceSelezionato = codiciPartite.getSelectionModel().getSelectedItem();
        if (codiceSelezionato != null) {
            JDBCPartite.eliminaPartita(codiceSelezionato);
            listaCodici.remove(codiceSelezionato);
            codiciPartite.refresh();
        }
    }
    
    private void setAllOff() {
    	riquadroSlider.setVisible(false);
		riquadroTextSlider.setVisible(false);
		riquadroLabelGioc.setVisible(false);
		listaUtenti.setVisible(false);
		testoUtentePart.setVisible(false);
		buttonAggGiocatorePart.setVisible(false);
		buttonAggGiocTorneo.setVisible(false);
		riquadroCodPartita.setVisible(false);
		bottoneCreaPartita.setVisible(false);
		riquadroCodiceGen.setVisible(false);
		buttonCaricaPartita.setVisible(false);
		codiciPartite.setVisible(false);
		codiciTornei.setVisible(false);
		textNomeUtente.setVisible(false);
		buttonCreaUtente.setVisible(false);
		buttonCreaTorneo.setVisible(false);
		bottonCaricaTorneo.setVisible(false);
		riquadroSlider2.setVisible(false);
		buttonEliminaUtente.setVisible(false);
		textSelezioneUtente.setVisible(false);
		confermaEliminazione.setVisible(false);
		rimuoviPartita.setVisible(false);
		rimuoviTorneo.setVisible(false);
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		setAllOff();
		checkIcon();
        updateButtonStyles(57, "", "", "", "", "", "button-round");
        freccia.setMouseTransparent(true);
		lista.clear();
		String[] temp = caricaUtenti();
	    for (int i = 0; i < temp.length; i++) {
	        lista.add(temp[i]);
	    }
	    listaUtenti.setItems(lista);
	    
	    listaCodici.clear();
	    String[] temp2=caricaCodici();
	    for(int i=0;i<temp2.length; i++) {
	    	listaCodici.add(temp2[i]);
	    }
	    codiciPartite.setItems(listaCodici);
	    
	    listaCodiciTornei.clear();
	    String[] temp3=caricaCodiciTornei();
	    for(int i=0;i<temp3.length; i++) {
	    	listaCodiciTornei.add(temp3[i]);
	    }
	    codiciTornei.setItems(listaCodiciTornei);

	}
	
	public void buttonCreaPartita() {
		setAllOff();
		textIniziale.setVisible(false);
		riquadroSlider.setVisible(true);
		numGiocatoriPart.setVisible(true);
		numGiocatoriTorneo.setVisible(false);
		riquadroTextSlider.setVisible(true);
		riquadroLabelGioc.setVisible(true);
		listaUtenti.setVisible(true);
		testoUtentePart.setVisible(true);
		buttonAggGiocatorePart.setVisible(true);
		riquadroCodPartita.setVisible(true);
		bottoneCreaPartita.setVisible(true);
		riquadroCodiceGen.setVisible(true);
	}
	
	public void buttonCaricaPartita() {
		setAllOff();
		textIniziale.setVisible(false);
		buttonSideCaricaPartita.setVisible(true);
		codiciTornei.setVisible(true);
	    bottonCaricaTorneo.setVisible(true);
	    codiciPartite.setVisible(true);
	    buttonCaricaPartita.setVisible(true);
	    rimuoviPartita.setVisible(true);
	    rimuoviTorneo.setVisible(true);
	}
	
	public void buttonGestisciUtenti() {
		setAllOff();
		textIniziale.setVisible(false);
		textNomeUtente.setVisible(true);
		buttonCreaUtente.setVisible(true);
		listaUtenti.setVisible(true);
		buttonEliminaUtente.setVisible(true);
		textSelezioneUtente.setVisible(true);
		confermaEliminazione.setVisible(true);
	}
	
	public void buttonCreaTorneo() {
		setAllOff();
		textIniziale.setVisible(false);
		riquadroSlider2.setVisible(true);
		numGiocatoriTorneo.setVisible(true);
		numGiocatoriPart.setVisible(false);
		riquadroTextSlider.setVisible(true);
		riquadroLabelGioc.setVisible(true);
		listaUtenti.setVisible(true);
		testoUtentePart.setVisible(true);
		buttonAggGiocTorneo.setVisible(true);
		riquadroCodPartita.setVisible(true);
		buttonCreaTorneo.setVisible(true);
		riquadroCodiceGen.setVisible(true);
	}
	
	
	///////////////////////////////////// TORNEI ////////////////////////////
	public void aggiungiGiocTorneo() throws IOException {
		confermaAggiunta.setVisible(true);
		confermaAggiunta.setText("");
		String elementoSelezionato = listaUtenti.getSelectionModel().getSelectedItem();
		if(nomeGiocatoriPartita.contains(elementoSelezionato)) {
			confermaAggiunta.setText("Utente già aggiunto al torneo");
			confermaAggiunta.setFill(Color.RED);
			PauseTransition pauseTransition = new PauseTransition(Duration.seconds(3));
	        pauseTransition.setOnFinished(event -> {
	            confermaAggiunta.setVisible(false);
	        });
	        confermaAggiunta.setVisible(true); 
	        pauseTransition.play();
		}
		else {
			nomeGiocatoriPartita+=elementoSelezionato + " ";
			confermaAggiunta.setText("Elemento aggiunto con successo");
			confermaAggiunta.setFill(Color.BLACK);
			PauseTransition pauseTransition = new PauseTransition(Duration.seconds(3));
	        pauseTransition.setOnFinished(event -> {
	            confermaAggiunta.setVisible(false);
	        });
	        confermaAggiunta.setVisible(true); 
	        pauseTransition.play();
		}
	}
	
	public void copiaCodiceClipboardTorneo() throws IOException {
		codicePartita.setText(codiceTorneo);
        labelGenerazione.setText("Clicca sul codice per copiarlo negli appunti");
        codicePartita.setOnMouseEntered(e -> {
            codicePartita.setCursor(javafx.scene.Cursor.HAND);
        });
        codicePartita.setOnMouseExited(e -> {
            codicePartita.setCursor(javafx.scene.Cursor.DEFAULT);
        });
        codicePartita.setOnMouseClicked(e -> {
            Clipboard clipboard = Clipboard.getSystemClipboard();
            ClipboardContent content = new ClipboardContent();
            content.putString(codiceTorneo);
            clipboard.setContent(content);
            labelGenerazione.setText("Codice copiato correttamente");
            PauseTransition pauseTransition = new PauseTransition(Duration.seconds(3));
            pauseTransition.setOnFinished(event -> {
                labelGenerazione.setText("Genera il codice della partita/torneo"); 
            });
            pauseTransition.play();
        });
	}
        
	
	 public String generaCodicePartitaTorneo() throws IOException {
	        Random rand = new Random();
	        codiceTorneo = "T";
	        boolean z = false;
	        char temp;
	        for (int i = 0; i < 8; i++) {
	            z = rand.nextBoolean();
	            if (z) {
	                temp = (char) ('a' + rand.nextInt(26));
	                codiceTorneo += temp;
	            } else {
	                codiceTorneo += rand.nextInt(9);
	            }
	        }
	        return codiceTorneo;
	    }
	 
	 public void creaTorneo() throws IOException, AWTException {
			t=null;
			confermaAggiunta.setText("");
			Set<String> nomeGiocatoriSet = new HashSet<>();
		    String[]tempGioc=nomeGiocatoriPartita.split(" ");
		    giocatori.clear();
		    
		    for(int i=0;i<tempGioc.length;i++) {
		    	nomeGiocatoriSet.add(tempGioc[i]);
		    }
		    
		    for (String nomeGiocatore : nomeGiocatoriSet) {
		        for (Utente u : JDBCCaricamento.getUtenti()) {
		            if (nomeGiocatore.equals(u.getNome())) {
		                giocatori.add(u);
		                break; 
		            }
		        }
		    }
		    
		    if (giocatori.size()<1) {
		    	confermaAggiunta.setVisible(true);
		    	confermaAggiunta.setText("Aggiungi almeno un giocatore al torneo");
		    	confermaAggiunta.setFill(Color.RED);
		    	PauseTransition pauseTransition = new PauseTransition(Duration.seconds(3));
		        pauseTransition.setOnFinished(event -> {
		            confermaAggiunta.setVisible(false);
		        });
		        confermaAggiunta.setVisible(true); 
		        pauseTransition.play();
		    } else if (giocatori.size() < numGiocatoriTorneo.getValue() && giocatori.size()>=1) {
		        //uguale qua
		        //crea qua avviso per far aggiungere i bot
		        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		        alert.setTitle("Conferma aggiunta bot");
		        alert.setHeaderText(null);
		        alert.setContentText("Il numero dei giocatori scelti è inferiore a quello selezionato, riempire con i bot?");

		        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		        stage.getScene().getRoot().setStyle("-fx-background-color: #3498db;");
		        
		        ButtonType buttonTypeSi = new ButtonType("SI");
		        ButtonType buttonTypeNo = new ButtonType("NO");

		        alert.getButtonTypes().setAll(buttonTypeSi, buttonTypeNo);

		        Optional<ButtonType> result = alert.showAndWait();
		        if (result.get() == buttonTypeSi){
		            // ... user chose SI
		            int size=(int)numGiocatoriTorneo.getValue()-giocatori.size();
		            for(int i=0;i<size;i++) {
		                giocatori.add(new RobotIntelligente("BOT"+(i+1),""));
		            }
		            Collections.shuffle(giocatori);
		            t=new Torneo(giocatori, generaCodicePartitaTorneo());
		            JDBCTornei.inserisciTorneo(t);				        
		            copiaCodiceClipboardTorneo();	
		            giocatori.clear();
		        } else {
		            // ... user chose NO or closed the dialog	
		        	confermaAggiunta.setVisible(true);
		            confermaAggiunta.setText("Numero di giocatori diverso da quello selezionato");
		            confermaAggiunta.setFill(Color.RED);
		            PauseTransition pauseTransition = new PauseTransition(Duration.seconds(3));
			        pauseTransition.setOnFinished(event -> {
			            confermaAggiunta.setVisible(false);
			        });
			        confermaAggiunta.setVisible(true); 
			        pauseTransition.play();
		        }
		    }
	    
		    else if(giocatori.size() > numGiocatoriTorneo.getValue()) {
		    	confermaAggiunta.setVisible(true);
		    	confermaAggiunta.setText("Il numero di giocatori non corrisponde a quello selezionato, reinserire i giocatori");
		    	confermaAggiunta.setFill(Color.RED);
		    	giocatori.clear();
		    	PauseTransition pauseTransition = new PauseTransition(Duration.seconds(3));
		        pauseTransition.setOnFinished(event -> {
		            confermaAggiunta.setVisible(false);
		        });
		        confermaAggiunta.setVisible(true); 
		        pauseTransition.play();
		    }
		    else {
		    	Collections.shuffle(giocatori);
		        t = new Torneo(giocatori, generaCodicePartitaTorneo());
		        JDBCTornei.inserisciTorneo(t);
		        giocatori.clear();
		        copiaCodiceClipboardTorneo();
		    }
		}
	 
	   public void caricaTorneo() throws IOException {
		   try {
			JDBCCaricamento.caricaTornei();
		} catch (AWTException e) {
			e.printStackTrace();
		}
			String elementoSelezionato = codiciTornei.getSelectionModel().getSelectedItem();
			for(Torneo x:JDBCCaricamento.getTornei()) {
				if(x.getCodice().equals(elementoSelezionato)) {
					t=x;
					for(Partita p:x.getPartite()) {
						System.out.println(p.getCodice());
					}
					Clipboard clipboard = Clipboard.getSystemClipboard();
			        ClipboardContent content = new ClipboardContent();
			        content.putString(x.getCodice());
			        clipboard.setContent(content);
					break;
				}
			}			
		}
		
		public static Torneo getTorneo() {
			return t;
		}
		
		public static String getCodiceTorneo() {
			return codiceTorneo;
		}

		public void rimuoviTorneo() throws IOException {
		        String codiceSelezionato = codiciTornei.getSelectionModel().getSelectedItem();
		        if (codiceSelezionato != null) {
		            JDBCTornei.eliminaTorneo(codiceSelezionato);
		            listaCodiciTornei.remove(codiceSelezionato);
		            codiciTornei.refresh();
		        }
		 }
		
		/////////////////////// SIDE MENU ///////////////////////////
		
		private void checkIcon() {
		    if (checkSide) { // Sidebar aperta
		        // Nasconde tutte le icone
		        iconaCarte.setVisible(false);
		        iconaUtenti.setVisible(false);
		        iconaCreaP.setVisible(false);
		        iconaCaricaP.setVisible(false);
		        iconaTorneo.setVisible(false);
		        return; // Non fare ulteriori controlli o modifiche
		    }

		    // Il codice seguente è eseguito solo se la sidebar è chiusa
		    //CARTE
		    iconaCarte.setVisible(!buttonSideGestisciCarte.isHover());
		    buttonSideGestisciCarte.setText(buttonSideGestisciCarte.isHover() ? "Gestisci Carte" : "");

		    //UTENTI
		    iconaUtenti.setVisible(!buttonSideGestisciUtenti.isHover());
		    buttonSideGestisciUtenti.setText(buttonSideGestisciUtenti.isHover() ? "Area Utenti" : "");

		    //CREA P
		    iconaCreaP.setVisible(!buttonSideCreaPartita.isHover());
		    buttonSideCreaPartita.setText(buttonSideCreaPartita.isHover() ? "Crea Partita" : "");

		    //CARICA P
		    iconaCaricaP.setVisible(!buttonSideCaricaPartita.isHover());
		    buttonSideCaricaPartita.setText(buttonSideCaricaPartita.isHover() ? "Caricamenti" : "");

		    //TORNEO
		    iconaTorneo.setVisible(!buttonSideCreaTorneo.isHover());
		    buttonSideCreaTorneo.setText(buttonSideCreaTorneo.isHover() ? "Crea Torneo" : "");
		}
		
		public void sideMenu() {
		    if (!checkSide) {
		        // Apertura della sidebar
		        checkIcon(); // Sarà chiamato comunque, ma le icone saranno visibili
		        sideMenu.setWidth(118);
		        updateButtonStyles(118, "Gestisci Utenti", "Gestisci Carte", "Carica Partita/Torneo", "Crea Torneo", "Crea Partita", "button-square");
		        buttonApriSide.setTranslateX(50.0);
		        freccia.setTranslateX(50.0);
		        Image image = new Image(getClass().getResourceAsStream("/application/CSSStyleSheets/freccia_sinistra.png"));
		        freccia.setImage(image);
		        // Nascondi tutte le icone
		        iconaCarte.setVisible(false);
		        iconaUtenti.setVisible(false);
		        iconaCreaP.setVisible(false);
		        iconaCaricaP.setVisible(false);
		        iconaTorneo.setVisible(false);
		        checkSide = true;
		    } else {
		        // Chiusura della sidebar
		        sideMenu.setWidth(57);
		        updateButtonStyles(57, "", "", "", "", "", "button-round");
		        buttonApriSide.setTranslateX(0);
		        freccia.setTranslateX(0);
		        Image image = new Image(getClass().getResourceAsStream("/application/CSSStyleSheets/freccia_destra.png"));
		        freccia.setImage(image);
		        // Ristabilisci la visibilità delle icone in base al loro stato
		        checkSide = false;
		        checkIcon();

		    }
		}

		private void updateButtonStyles(double width, String textGestisciUtenti, String textGestisciCarte, String textCaricaPartita, String textCreaTorneo, String textCreaPartita, String buttonStyleClass) {
		    updateButtonStyle(buttonSideGestisciUtenti, width, textGestisciUtenti, buttonStyleClass);
		    updateButtonStyle(buttonSideGestisciCarte, width, textGestisciCarte, buttonStyleClass);
		    updateButtonStyle(buttonSideCreaPartita, width, textCreaPartita, buttonStyleClass);
		    updateButtonStyle(buttonSideCreaTorneo, width, textCreaTorneo, buttonStyleClass);
		    updateButtonStyle(buttonSideCaricaPartita, width, textCaricaPartita, buttonStyleClass);
		}

		private void updateButtonStyle(Button button, double width, String text, String styleClass) {
		    button.setMinWidth(width);
		    button.setMaxWidth(width);
		    button.setText(text);
		    button.getStyleClass().clear();
		    button.getStyleClass().add(styleClass);
		}

		
		public void buttonMenuUtentiOpen(MouseEvent event) {
		    if (!checkSide) {
		        checkIcon();
		        buttonSideGestisciUtenti.setMinWidth(118);
		        buttonSideGestisciUtenti.setMaxWidth(118);
		        buttonSideGestisciUtenti.getStyleClass().add("button-square");
		        curvaUtenti1.setVisible(true);
		        curvaUtenti2.setVisible(true); 
		    }
		}

		public void buttonMenuUtentiClose(MouseEvent event) {
		    if (!checkSide) {
		        checkIcon();
		        buttonSideGestisciUtenti.setMinWidth(57);
		        buttonSideGestisciUtenti.setMaxWidth(57);
		        curvaUtenti1.setVisible(false);
		        curvaUtenti2.setVisible(false);     
		        updateButtonStyles(57, "", "", "", "", "", "button-round");

		    }
		}

		public void buttonMenuCarteOpen(MouseEvent event) {
		    if (!checkSide) {
		        checkIcon();
		        buttonSideGestisciCarte.setMinWidth(118);
		        buttonSideGestisciCarte.setMaxWidth(118);
		        buttonSideGestisciCarte.getStyleClass().add("button-square");
		        curvaCarte1.setVisible(true);
		        curvaCarte2.setVisible(true);
		    }
		}

		public void buttonMenuCarteClose(MouseEvent event) {
		    if (!checkSide) {
		        checkIcon();
		        buttonSideGestisciCarte.setMinWidth(57);
		        buttonSideGestisciCarte.setMaxWidth(57);
		        curvaCarte1.setVisible(false);
		        curvaCarte2.setVisible(false);
		        updateButtonStyles(57, "", "", "", "", "", "button-round");
		    }
		}

		public void buttonMenuCaricaPOpen(MouseEvent event) {
		    if (!checkSide) {
		        checkIcon();
		        buttonSideCaricaPartita.setMinWidth(118);
		        buttonSideCaricaPartita.setMaxWidth(118);
		        buttonSideCaricaPartita.getStyleClass().add("button-square");
		        curvaCaricaP1.setVisible(true);
		        curvaCaricaP2.setVisible(true);
		    }
		}

		public void buttonMenuCaricaPClose(MouseEvent event) {
		    if (!checkSide) {
		        checkIcon();
		        buttonSideCaricaPartita.setMinWidth(57);
		        buttonSideCaricaPartita.setMaxWidth(57);
		        curvaCaricaP1.setVisible(false);
		        curvaCaricaP2.setVisible(false);
		        updateButtonStyles(57, "", "", "", "", "", "button-round");

		    }
		}

		public void buttonMenuTorneoOpen(MouseEvent event) {
		    if (!checkSide) {
		        checkIcon();
		        buttonSideCreaTorneo.setMinWidth(118);
		        buttonSideCreaTorneo.setMaxWidth(118);
		        buttonSideCreaTorneo.getStyleClass().add("button-square");
		        curvaTorneo1.setVisible(true);
		        curvaTorneo2.setVisible(true);
		    }
		}

		public void buttonMenuTorneoClose(MouseEvent event) {
		    if (!checkSide) {
		        checkIcon();
		        buttonSideCreaTorneo.setMinWidth(57);
		        buttonSideCreaTorneo.setMaxWidth(57);
		        curvaTorneo1.setVisible(false);
		        curvaTorneo2.setVisible(false);
		        updateButtonStyles(57, "", "", "", "", "", "button-round");

		    }
		}

		public void buttonMenuCreaPOpen(MouseEvent event) {
		    if (!checkSide) {
		        checkIcon();
		        buttonSideCreaPartita.setMinWidth(118);
		        buttonSideCreaPartita.setMaxWidth(118);
		        buttonSideCreaPartita.getStyleClass().add("button-square");
		        curvaCreaP1.setVisible(true);
		        curvaCreaP2.setVisible(true);
		    }
		}

		public void buttonMenuCreaPClose(MouseEvent event) {
		    if (!checkSide) {
		        checkIcon();
		        buttonSideCreaPartita.setMinWidth(57);
		        buttonSideCreaPartita.setMaxWidth(57);
		        curvaCreaP1.setVisible(false);
		        curvaCreaP2.setVisible(false);
		        updateButtonStyles(57, "", "", "", "", "", "button-round");
		    }
		}
}