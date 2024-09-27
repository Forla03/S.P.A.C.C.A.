package application.CONTROLLER;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.Main;
import application.CLASSI.CartaCura;
import application.CLASSI.CartaEffetto;
import application.CLASSI.CartaEquipaggiamento;
import application.CLASSI.CartaGiocabile;
import application.CLASSI.CartaImprevisto;
import application.CLASSI.CartaPersonaggio;
import application.CLASSI.CartaTerreno;
import application.CLASSI.JDBCCaricamento;
import application.CLASSI.JDBCCarteCura;
import application.CLASSI.JDBCCarteEffetto;
import application.CLASSI.JDBCCarteEquipaggiamento;
import application.CLASSI.JDBCCarteImprevisto;
import application.CLASSI.JDBCCartePersonaggio;
import application.CLASSI.JDBCCarteTerreno;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;

public class ControllerScenaAggRimCarte implements Initializable {
	  	
	 	@FXML
	    private Text attacco;

	    @FXML
	    private TextField attaccoCarta;

	    @FXML
	    private Button buttonAggiungiCarta;

	    @FXML
	    private Text descrizione;

	    @FXML
	    private TextField descrizioneCarta;

	    @FXML
	    private Text difesa;

	    @FXML
	    private TextField difesaCarta;

	    @FXML
	    private Text errorText;

	    @FXML
	    private HBox hboxMitologia;

	    @FXML
	    private HBox hboxRaritaPers;
	    
	    @FXML
	    private HBox hboxRaritaEquip;

	    @FXML
	    private HBox hboxTipoCarta;

	    @FXML
	    private ImageView immagineCarta;

	    @FXML
	    private Text mitologia;

	    @FXML
	    private Text nome;

	    @FXML
	    private TextField nomeCarta;

	    @FXML
	    private RadioButton radioComune;

	    @FXML
	    private RadioButton radioEpica;

	    @FXML
	    private RadioButton radioEquipaggiamento;

	    @FXML
	    private ToggleGroup radioMitologia;

	    @FXML
	    private RadioButton radioNormale;

	    @FXML
	    private RadioButton radioPersonaggio;

	    @FXML
	    private RadioButton radioRara;
	    
	    @FXML
	    private RadioButton radioGreca;
	    
	    @FXML
	    private RadioButton radioRomana;
	    
	    @FXML
	    private RadioButton radioEgizia;
	    
	    @FXML
	    private RadioButton radioNorrena;

	    @FXML
	    private RadioButton radioSpeciale;

	    @FXML
	    private Text rarita;

	    @FXML
	    private ToggleGroup raritaEquipaggiamento;

	    @FXML
	    private ToggleGroup raritaPersonaggio;

	    @FXML
	    private Text textImm;
	    
	    @FXML
	    private HBox riquadro;

	    @FXML
	    private ToggleGroup tipoCarta;
	    
	    @FXML
	    private ImageView immListView;
	    
	    @FXML 
	    private ListView<String> listCarte=new ListView<String>();
	    
	    @FXML
	    private Button rimuoviButton;
	    
	    @FXML
	    private Button selezionaButton;
	    
	    @FXML
	    private Button bottoneCrea;
	    
	    @FXML
	    private Button bottoneAggiorna;

	    private int indiceTipoCarta=0;
	    
	    private ArrayList<CartaPersonaggio> cartePersonaggio;
	    private ArrayList<CartaEquipaggiamento> carteEquipaggiamento;
	    private ArrayList<CartaCura> carteCura;
	    private ArrayList<CartaEffetto> carteEffetto;
	    private ArrayList<CartaTerreno> carteTerreno;
	    private ArrayList<CartaImprevisto> carteImprevisto;

	    private int segnaTipo=1;
	    
	    private String nomeTemp;
	    
	    @FXML
	    private void handleDragOver(DragEvent event) {
	        if (event.getDragboard().hasFiles()) {
	            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
	        }
	        event.consume();
	    }

	    @FXML
	    private void handleDragDropped(DragEvent event) {
	        try{
	        	Dragboard db = event.getDragboard();
	        boolean success = false;
	        if (db.hasFiles()) {
	            try {
	                String filePath = db.getFiles().get(0).toURI().toString();
	                Image image = new Image(filePath);
	                immagineCarta.setImage(image);
	                success = true;
	                riquadro.setVisible(false);
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	        event.setDropCompleted(success);
	        event.consume();} 
	        catch (Exception e) {
	            gestisciEccezioni(e);
	        }
	    }

	    public void modificaCarta() {
	    	try{
	    		RadioButton selezioneTipo= (RadioButton) tipoCarta.getSelectedToggle();
	    	
	    	if(selezioneTipo!=null) {
	    		selezionaButton.setVisible(true);
	    		rimuoviButton.setVisible(true);
	    		immListView.setVisible(true);
	    		errorText.setText("");
	    		bottoneCrea.setDisable(true);
	    		bottoneAggiorna.setVisible(true);
	    		if(selezioneTipo.getText().equals("Carta Personaggio")) {
		    			ArrayList<String> nomiA = new ArrayList<>();
		    			for(int i=0; i<cartePersonaggio.size();i++) {
		    				nomiA.add(cartePersonaggio.get(i).getNome());
		    			}
		    			ObservableList<String> nomiO=FXCollections.observableArrayList(nomiA);
		    			listCarte.setItems(nomiO);
		    			}
	    		else if(selezioneTipo.getText().equals("Carta Equipaggiamento")) {
		    			ArrayList<String> nomiA = new ArrayList<>();
		    			for(int i=0; i<carteEquipaggiamento.size();i++) {
		    				nomiA.add(carteEquipaggiamento.get(i).getNome());
		    			}
		    			ObservableList<String> nomiO=FXCollections.observableArrayList(nomiA);
		    			listCarte.setItems(nomiO);}
	    		else if(selezioneTipo.getText().equals("Carta Cura")) {
		    			ArrayList<String> nomiA = new ArrayList<>();
			    		for(int i=0; i<carteCura.size();i++) {
		    				nomiA.add(carteCura.get(i).getNome());
		    			}
		    			ObservableList<String> nomiO=FXCollections.observableArrayList(nomiA);
		    			listCarte.setItems(nomiO);   		}
	    		else if(selezioneTipo.getText().equals("Carta Effetto")) {
		    			ArrayList<String> nomiA = new ArrayList<>();
		    			for(int i=0; i<carteEffetto.size();i++) {
		    				nomiA.add(carteEffetto.get(i).getNome());
		    			}
		    			ObservableList<String> nomiO=FXCollections.observableArrayList(nomiA);
		    			listCarte.setItems(nomiO);   		}
	    		else if(selezioneTipo.getText().equals("Carta Terreno")) {
		    			ArrayList<String> nomiA = new ArrayList<>();
		    			for(int i=0; i<carteTerreno.size();i++) {
		    				nomiA.add(carteTerreno.get(i).getNome());
		    			}
		    			ObservableList<String> nomiO=FXCollections.observableArrayList(nomiA);
		    			listCarte.setItems(nomiO);   		
		    			}
	    		else {
		    			ArrayList<String> nomiA = new ArrayList<>();
		    			for(int i=0; i<carteImprevisto.size();i++) {
		    				nomiA.add(carteImprevisto.get(i).getNome());
		    			}
		    			ObservableList<String> nomiO=FXCollections.observableArrayList(nomiA);
		    			listCarte.setItems(nomiO);	    		
		    			}
	    		}
	    	else {
	    		errorText.setText("SELEZIONARE IL TIPO DI CARTA CHE SI VUOLE MODIFICARE");
	    	}} catch (Exception e) {
	            gestisciEccezioni(e);
	        }
	    }
	    
	    public void selezionaButt() {
	    	try{
	    		String nomeCartaString=listCarte.getSelectionModel().getSelectedItem();
	    	
	    	RadioButton selezioneTipo= (RadioButton) tipoCarta.getSelectedToggle();
	    	if(selezioneTipo!=null) {
	    		errorText.setText("");
		    	initialize(null,null);
	    	if(selezioneTipo.getText().equals("Carta Personaggio")) {	    		
	    		CartaPersonaggio carta=JDBCCartePersonaggio.getCartaPersonaggioByNome(nomeCartaString);
	    		System.out.println(carta.toString()+ " /////////////////////////");
	    		bottoneAggiorna.setVisible(true);
	            rimuoviButton.setVisible(true);
	    		nome.setVisible(true);
    	    	nomeCarta.setVisible(true);
    	    	hboxMitologia.setVisible(true);
    	    	mitologia.setVisible(true);
    	    	hboxRaritaPers.setVisible(true);
    	    	rarita.setVisible(true);
    	    	descrizione.setVisible(true);
    	    	immagineCarta.setVisible(true);
    	    	descrizioneCarta.setVisible(true);
    	    	attacco.setVisible(true);
    	    	difesa.setVisible(true);
    	    	attaccoCarta.setVisible(true);
    	    	difesaCarta.setVisible(true);
    	    	selezionaButton.setVisible(true);
    	    	
    	    	immagineCarta.setImage(carta.getImmagine());
	    		nomeCarta.setText(carta.getNome());
	    		difesaCarta.setText(carta.getDefString());
    	    	attaccoCarta.setText(carta.getAttString());
    	    	descrizioneCarta.setText(carta.getDescrizione());
    	    	if(carta.getTipoString().equals("EGIZIA")) {
    	    		radioMitologia.selectToggle(radioEgizia);
    	    	}
    	    	else if(carta.getTipoString().equals("ROMANA")) {
    	    		radioMitologia.selectToggle(radioRomana);
    	    	}
    	    	else if(carta.getTipoString().equals("GRECA")) {
    	    		radioMitologia.selectToggle(radioGreca);
    	    	}
    	    	else {
    	    		radioMitologia.selectToggle(radioNorrena);
    	    	}
    	    	
    	    	if(carta.getRaritaString().equals("NORMALE")) {
    	    		raritaPersonaggio.selectToggle(radioNormale);
    	    	}
    	    	else {
    	    		raritaPersonaggio.selectToggle(radioSpeciale);
    	    	}
    	    	indiceTipoCarta=1;
    	    	nomeTemp=carta.getNome();
	    	}
	    	
	    	else {
	    		CartaGiocabile carta;
	    		if(selezioneTipo.getText().equals("Carta Equipaggiamento")) {
    	    		indiceTipoCarta=2;
    	    		carta=JDBCCarteEquipaggiamento.getCartaEquipaggiamentoByNome(nomeCartaString);
    	    	}
    	    	else if(selezioneTipo.getText().equals("Carta Cura")) {
    	    		indiceTipoCarta=3;	
    	    		carta=JDBCCarteCura.getCartaCuraByNome(nomeCartaString);
    	    	}
    	    	else if(selezioneTipo.getText().equals("Carta Effetto")) {
    	    		indiceTipoCarta=4;
    	    		carta=JDBCCarteEffetto.getCartaEffettoByNome(nomeCartaString);
    	    	}
    	    	else if(selezioneTipo.getText().equals("Carta Terreno")) {
    	    		indiceTipoCarta=5;
    	    		carta=JDBCCarteTerreno.getCartaTerrenoByNome(nomeCartaString);
    	    	}
    	    	else {
    	    		indiceTipoCarta=6;
    	    		carta=JDBCCarteImprevisto.getCartaImprevistoByNome(nomeCartaString);
    	    	}
	    		bottoneAggiorna.setVisible(true);
	            rimuoviButton.setVisible(true);
	    		nome.setVisible(true);
    	    	nomeCarta.setVisible(true);
    	    	hboxMitologia.setVisible(true);
    	    	mitologia.setVisible(true);
    	    	hboxRaritaEquip.setVisible(true);
    	    	rarita.setVisible(true);
    	    	descrizione.setText("Effetto");
    	    	descrizione.setVisible(true);
    	    	immagineCarta.setVisible(true);
    	    	descrizioneCarta.setVisible(true);
    	    	selezionaButton.setVisible(true);
    	    	
				immagineCarta.setImage(carta.getImmagine());
	    		nomeCarta.setText(carta.getNome());
    	    	descrizioneCarta.setText(carta.getEffetto());
    	    	nomeTemp=carta.getNome();
	    		
    	    	if(carta.getMitologiaString().equals("EGIZIA")) {
    	    		radioMitologia.selectToggle(radioEgizia);
    	    	}
    	    	else if(carta.getMitologiaString().equals("ROMANA")) {
    	    		radioMitologia.selectToggle(radioRomana);
    	    	}
    	    	else if(carta.getMitologiaString().equals("GRECA")) {
    	    		radioMitologia.selectToggle(radioGreca);
    	    	}
    	    	else {
    	    		radioMitologia.selectToggle(radioNorrena);
    	    	}
    	    	
    	    	if(carta.getRaritaString().equals("COMUNE")) {
    	    		raritaEquipaggiamento.selectToggle(radioComune);
    	    	}
    	    	else if(carta.getRaritaString().equals("RARA")){
    	    		raritaEquipaggiamento.selectToggle(radioRara);
    	    	}
    	    	else {
    	    		raritaEquipaggiamento.selectToggle(radioEpica);
    	    	}
    	    	
    	    	
	    	}
	    	}} catch (Exception e) {
	            gestisciEccezioni(e);
	        }
	    }
	    
	   public void aggiornaCarta() {
	       try {
		   RadioButton selezioneTipo = (RadioButton) tipoCarta.getSelectedToggle();
	        if (selezioneTipo != null) {
	            if (indiceTipoCarta == 1) {
	                aggiornaCartaPersonaggio();
	            } else if (indiceTipoCarta == 2) {
	                aggiornaCartaEquipaggiamento();
	            } else if (indiceTipoCarta == 3) {
	                aggiornaCartaCura();
	            } else if (indiceTipoCarta == 4) {
	                aggiornaCartaEffetto();
	            } else if (indiceTipoCarta == 5) {
	                aggiornaCartaTerreno();
	            } else if (indiceTipoCarta == 6) {
	                aggiornaCartaImprevisto();
	            }
	        } else {
	            errorText.setText("SELEZIONARE IL TIPO DI CARTA DA AGGIORNARE");
	        }} catch (Exception e) {
	            gestisciEccezioni(e);
	        }
	    }

	    private void aggiornaCartaPersonaggio() {
	       try {
	    	RadioButton mitologiaSelezionata = (RadioButton) radioMitologia.getSelectedToggle();
	        RadioButton raritaSelezionata = (RadioButton) raritaPersonaggio.getSelectedToggle();

	        if (nomeCarta.getText().isEmpty() || mitologiaSelezionata == null || descrizioneCarta.getText().isEmpty() || attaccoCarta.getText().isEmpty() || difesaCarta.getText().isEmpty() || immagineCarta.getImage() == null || raritaSelezionata == null) {
	            errorText.setText("E' necessario riempire tutti i campi richiesti per poter aggiornare la carta");
	            errorText.setFill(Color.RED);
	            return;
	        } else if (!isInteger(attaccoCarta.getText()) || !isInteger(difesaCarta.getText())) {
	            errorText.setText("Inserire dei valori interi validi come attacco e difesa");
	            errorText.setFill(Color.RED);
	            return;
	        } else {
	            JDBCCartePersonaggio.aggiornaCartaPersonaggio(
	                nomeTemp,
	                nomeCarta.getText(),
	                mitologiaSelezionata.getText(),
	                descrizioneCarta.getText(),
	                Integer.parseInt(attaccoCarta.getText()),
	                Integer.parseInt(difesaCarta.getText()),
	                raritaSelezionata.getText(),
	                immagineCarta.getImage().getUrl()
	            );
	            JDBCCaricamento.caricaCartePersonaggio();
	            int indice = listCarte.getSelectionModel().getSelectedIndex();
	            listCarte.getItems().set(indice, nomeCarta.getText());
	            clearFieldsAndResetView();
	        }} catch (Exception e) {
	            gestisciEccezioni(e);
	        }
	    }
	    
	    private void aggiornaCartaEquipaggiamento() {
	    	try {
	    	RadioButton mitologiaSelezionata = (RadioButton) radioMitologia.getSelectedToggle();
	        RadioButton raritaSelezionata = (RadioButton) raritaEquipaggiamento.getSelectedToggle();

	        if (nomeCarta.getText().isEmpty() || mitologiaSelezionata == null || descrizioneCarta.getText().isEmpty()   || immagineCarta.getImage() == null || raritaSelezionata == null) {
	            errorText.setText("E' necessario riempire tutti i campi richiesti per poter aggiungere una carta");
	            errorText.setFill(Color.RED);
	            return;
	        } else {
	            JDBCCarteEquipaggiamento.aggiornaCartaEquipaggiamento(
	            	nomeTemp,
	                nomeCarta.getText(), 
	                mitologiaSelezionata.getText(), 
	                raritaSelezionata.getText(),
	                descrizioneCarta.getText(), 
	                immagineCarta.getImage().getUrl()
	            );
	            JDBCCaricamento.caricaCarteEquipaggiamento();
	            int indice = listCarte.getSelectionModel().getSelectedIndex();
	            listCarte.getItems().set(indice, nomeCarta.getText());
	            clearFieldsAndResetView();
	        }} catch (Exception e) {
	            gestisciEccezioni(e);
	        }
	    }
	    
	    private void aggiornaCartaCura() {
	    	try{
	    		RadioButton mitologiaSelezionata = (RadioButton) radioMitologia.getSelectedToggle();
	    	
	        RadioButton raritaSelezionata = (RadioButton) raritaEquipaggiamento.getSelectedToggle();

	        if (nomeCarta.getText().isEmpty() || mitologiaSelezionata == null || descrizioneCarta.getText().isEmpty()   || immagineCarta.getImage() == null || raritaSelezionata == null) {
	            errorText.setText("E' necessario riempire tutti i campi richiesti per poter aggiungere una carta");
	            errorText.setFill(Color.RED);
	            return;
	        } else {
	            JDBCCarteCura.aggiornaCartaCura(
	            	nomeTemp,
	                nomeCarta.getText(), 
	                mitologiaSelezionata.getText(), 
	                raritaSelezionata.getText(),
	                descrizioneCarta.getText(), 
	                immagineCarta.getImage().getUrl()
	            );
	            JDBCCaricamento.caricaCarteCura();
	            int indice = listCarte.getSelectionModel().getSelectedIndex();
	            listCarte.getItems().set(indice, nomeCarta.getText());
	            clearFieldsAndResetView();
	        }} catch (Exception e) {
	            gestisciEccezioni(e);
	        }
	    }
	    
	    private void aggiornaCartaEffetto() {
	    	try {
	    	RadioButton mitologiaSelezionata = (RadioButton) radioMitologia.getSelectedToggle();
	        RadioButton raritaSelezionata = (RadioButton) raritaEquipaggiamento.getSelectedToggle();

	        if (nomeCarta.getText().isEmpty() || mitologiaSelezionata == null || descrizioneCarta.getText().isEmpty()   || immagineCarta.getImage() == null || raritaSelezionata == null) {
	            errorText.setText("E' necessario riempire tutti i campi richiesti per poter aggiungere una carta");
	            errorText.setFill(Color.RED);
	            return;
	        } else {
	            JDBCCarteEffetto.aggiornaCartaEffetto(
	            	nomeTemp,
	                nomeCarta.getText(), 
	                mitologiaSelezionata.getText(), 
	                raritaSelezionata.getText(),
	                descrizioneCarta.getText(), 
	                immagineCarta.getImage().getUrl()
	            );
	            JDBCCaricamento.caricaCarteEffetto();
	            int indice = listCarte.getSelectionModel().getSelectedIndex();
	            listCarte.getItems().set(indice, nomeCarta.getText());
	            clearFieldsAndResetView();
	        }} catch (Exception e) {
	            gestisciEccezioni(e);
	        }
	    }
	    
	    private void aggiornaCartaImprevisto() {
	    	try {
	    	RadioButton mitologiaSelezionata = (RadioButton) radioMitologia.getSelectedToggle();
	        RadioButton raritaSelezionata = (RadioButton) raritaEquipaggiamento.getSelectedToggle();

	        if (nomeCarta.getText().isEmpty() || mitologiaSelezionata == null || descrizioneCarta.getText().isEmpty()   || immagineCarta.getImage() == null || raritaSelezionata == null) {
	            errorText.setText("E' necessario riempire tutti i campi richiesti per poter aggiungere una carta");
	            errorText.setFill(Color.RED);
	            return;
	        } else {
	            JDBCCarteImprevisto.aggiornaCartaImprevisto(
	            	nomeTemp,
	                nomeCarta.getText(), 
	                mitologiaSelezionata.getText(), 
	                raritaSelezionata.getText(),
	                descrizioneCarta.getText(), 
	                immagineCarta.getImage().getUrl()
	            );
	            JDBCCaricamento.caricaCarteImprevisto();
	            int indice = listCarte.getSelectionModel().getSelectedIndex();
	            listCarte.getItems().set(indice, nomeCarta.getText());
	            clearFieldsAndResetView();
	        }} catch (Exception e) {
	            gestisciEccezioni(e);
	        }
	    }
	    
	    private void aggiornaCartaTerreno() {
	    	try {
	    	RadioButton mitologiaSelezionata = (RadioButton) radioMitologia.getSelectedToggle();
	        RadioButton raritaSelezionata = (RadioButton) raritaEquipaggiamento.getSelectedToggle();

	        if (nomeCarta.getText().isEmpty() || mitologiaSelezionata == null || descrizioneCarta.getText().isEmpty()   || immagineCarta.getImage() == null || raritaSelezionata == null) {
	            errorText.setText("E' necessario riempire tutti i campi richiesti per poter aggiungere una carta");
	            errorText.setFill(Color.RED);
	            return;
	        } else {
	            JDBCCarteTerreno.aggiornaCartaTerreno(
	            	nomeTemp,
	                nomeCarta.getText(), 
	                mitologiaSelezionata.getText(), 
	                raritaSelezionata.getText(),
	                descrizioneCarta.getText(), 
	                immagineCarta.getImage().getUrl()
	            );
	            JDBCCaricamento.caricaCarteTerreno();
	            int indice = listCarte.getSelectionModel().getSelectedIndex();
	            listCarte.getItems().set(indice, nomeCarta.getText());
	            clearFieldsAndResetView();
	        }} catch (Exception e) {
	            gestisciEccezioni(e);
	        }
	    }
	    
	    private void clearFieldsAndResetView() {
	        errorText.setText("Carta aggiornata correttamente nel database");
	        errorText.setFill(Color.BLACK);
	        nomeCarta.setText("");
	        descrizioneCarta.setText("");
	        attaccoCarta.setText("");
	        difesaCarta.setText("");
	        radioMitologia.selectToggle(null);
	        raritaPersonaggio.selectToggle(null);
	        raritaEquipaggiamento.selectToggle(null);
	        immagineCarta.setImage(null);
	        riquadro.setVisible(true);
	        modificaCarta();
	    }
	    
	    public void creaCarta() {
	    	try {
	    	initialize(null, null);
	    	RadioButton selezioneTipo= (RadioButton) tipoCarta.getSelectedToggle();
	    	if(selezioneTipo!=null) {
	    		errorText.setText("");
	    		if(selezioneTipo.getText().equals("Carta Personaggio")) {
	    	    	nome.setVisible(true);
	    	    	nomeCarta.setVisible(true);
	    	    	hboxMitologia.setVisible(true);
	    	    	mitologia.setVisible(true);
	    	    	hboxRaritaPers.setVisible(true);
	    	    	rarita.setVisible(true);
	    	    	descrizione.setVisible(true);
	    	    	immagineCarta.setVisible(true);
	    	    	descrizioneCarta.setVisible(true);
	    	    	buttonAggiungiCarta.setVisible(true);
	    	    	riquadro.setVisible(true);
	    	    	attacco.setVisible(true);
	    	    	difesa.setVisible(true);
	    	    	attaccoCarta.setVisible(true);
	    	    	difesaCarta.setVisible(true);
	    	    	indiceTipoCarta=1;
	    	    	
	    		}
	    		
	    		else {
	    			nome.setVisible(true);
	    	    	nomeCarta.setVisible(true);
	    	    	hboxMitologia.setVisible(true);
	    	    	mitologia.setVisible(true);
	    	    	hboxRaritaEquip.setVisible(true);
	    	    	rarita.setVisible(true);
	    	    	descrizione.setText("Effetto");
	    	    	descrizione.setVisible(true);
	    	    	immagineCarta.setVisible(true);
	    	    	descrizioneCarta.setVisible(true);
	    	    	buttonAggiungiCarta.setVisible(true);
	    	    	riquadro.setVisible(true);
	    	    	if(selezioneTipo.getText().equals("Carta Equipaggiamento")) {
	    	    		indiceTipoCarta=2;
	    	    	}
	    	    	else if(selezioneTipo.getText().equals("Carta Cura")) {
	    	    		indiceTipoCarta=3;	
	    	    	}
	    	    	else if(selezioneTipo.getText().equals("Carta Effetto")) {
	    	    		indiceTipoCarta=4;
	    	    	}
	    	    	else if(selezioneTipo.getText().equals("Carta Terreno")) {
	    	    		indiceTipoCarta=5;
	    	    	}
	    	    	else if(selezioneTipo.getText().equals("Carta Imprevisto")) {
	    	    		indiceTipoCarta=6;
	    	    	}
	    	    		
	    		}
	    		
	    	}
	    	else {
    			errorText.setText("SELEZIONARE IL TIPO DI CARTA CHE SI VUOLE AGGIUNGERE");
    		}} catch (Exception e) {
    	        gestisciEccezioni(e);
    	    }
	    }
	    
	    public void aggiungiCarta() {
	    	try {
	        if(indiceTipoCarta==1) {
	        	RadioButton mitologiaSelezionata = (RadioButton) radioMitologia.getSelectedToggle();
		        RadioButton raritaSelezionata = (RadioButton) raritaPersonaggio.getSelectedToggle();

		        if (nomeCarta.getText().isEmpty() || mitologiaSelezionata == null || descrizioneCarta.getText().isEmpty() || attaccoCarta.getText().isEmpty() || difesaCarta.getText().isEmpty() || immagineCarta.getImage() == null || raritaSelezionata == null) {
		            errorText.setText("E' necessario riempire tutti i campi richiesti per poter aggiungere una carta");
		            errorText.setFill(Color.RED);
		            return;
		        } else if (!isInteger(attaccoCarta.getText()) || !isInteger(difesaCarta.getText())) {
		            errorText.setText("Inserire dei valori interi validi come attacco e difesa");
		            errorText.setFill(Color.RED);
		            return;
		        } else {
		        	if (JDBCCartePersonaggio.cartaPersonaggioEsiste(nomeCarta.getText())) {
		        		errorText.setText("E' già presente una carta con quel nome nel database");
		        		errorText.setFill(Color.RED);
		        		return;
		        	}
		        	else {
		            JDBCCartePersonaggio.aggiungiCartaPersonaggio(
		                nomeCarta.getText(), 
		                mitologiaSelezionata.getText(), 
		                descrizioneCarta.getText(), 
		                Integer.parseInt(attaccoCarta.getText()), 
		                Integer.parseInt(difesaCarta.getText()), 
		                raritaSelezionata.getText(), 
		                immagineCarta.getImage().getUrl()
		            );
		            
		        	}
		        }
	        }
	        else if(indiceTipoCarta==2) {
		        	RadioButton mitologiaSelezionata = (RadioButton) radioMitologia.getSelectedToggle();
			        RadioButton raritaSelezionata = (RadioButton) raritaEquipaggiamento.getSelectedToggle();

			        // Controlla se tutte le informazioni necessarie sono state inserite
			        if (nomeCarta.getText().isEmpty() || mitologiaSelezionata == null || descrizioneCarta.getText().isEmpty()   || immagineCarta.getImage() == null || raritaSelezionata == null) {
			            errorText.setText("E' necessario riempire tutti i campi richiesti per poter aggiungere una carta");
			            errorText.setFill(Color.RED);
			            return;
			        } else {
			        	if(JDBCCarteEquipaggiamento.cartaEquipaggiamentoEsiste(nomeCarta.getText())) {
			        		errorText.setText("E' già presente una carta con quel nome nel database");
			        		errorText.setFill(Color.RED);
			        		return;
			        	}
			        	else {
			            // Aggiungi la carta usando i valori dei RadioButton
			            JDBCCarteEquipaggiamento.aggiungiCartaEquipaggiamento(
			                nomeCarta.getText(), 
			                mitologiaSelezionata.getText(), 
			                raritaSelezionata.getText(),
			                descrizioneCarta.getText(), 
			                immagineCarta.getImage().getUrl()
			            );
			           
			        }
			        }
		        }
	        else if(indiceTipoCarta==3) {
	        	RadioButton mitologiaSelezionata = (RadioButton) radioMitologia.getSelectedToggle();
		        RadioButton raritaSelezionata = (RadioButton) raritaEquipaggiamento.getSelectedToggle();

		        // Controlla se tutte le informazioni necessarie sono state inserite
		        if (nomeCarta.getText().isEmpty() || mitologiaSelezionata == null || descrizioneCarta.getText().isEmpty()   || immagineCarta.getImage() == null || raritaSelezionata == null) {
		            errorText.setText("E' necessario riempire tutti i campi richiesti per poter aggiungere una carta");
		            errorText.setFill(Color.RED);
		            return;
		       
		        } else {
		        	if(JDBCCarteCura.cartaCuraEsiste(nomeCarta.getText())) {
		        		errorText.setText("E' già presente una carta con quel nome nel database");
		        		errorText.setFill(Color.RED);
		        		return;
		        	}
		        	else {
		            // Aggiungi la carta usando i valori dei RadioButton
		        		JDBCCarteCura.aggiungiCartaCura(
		        		nomeCarta.getText(), 
		                mitologiaSelezionata.getText(), 
		                raritaSelezionata.getText(),
		                descrizioneCarta.getText(), 
		                immagineCarta.getImage().getUrl()
		            );
		            
		        }
		        }
		        }
	        else if(indiceTipoCarta==4) {
	        	RadioButton mitologiaSelezionata = (RadioButton) radioMitologia.getSelectedToggle();
		        RadioButton raritaSelezionata = (RadioButton) raritaEquipaggiamento.getSelectedToggle();

		        // Controlla se tutte le informazioni necessarie sono state inserite
		        if (nomeCarta.getText().isEmpty() || mitologiaSelezionata == null || descrizioneCarta.getText().isEmpty()   || immagineCarta.getImage() == null || raritaSelezionata == null) {
		            errorText.setText("E' necessario riempire tutti i campi richiesti per poter aggiungere una carta");
		            errorText.setFill(Color.RED);
		            return;
		        } else {
		        	if(JDBCCarteEffetto.cartaEffettoEsiste(nomeCarta.getText())) {
		        		errorText.setText("E' già presente una carta con quel nome nel database");
		        		errorText.setFill(Color.RED);
		        		return;
		        	}
		        	else {
		            // Aggiungi la carta usando i valori dei RadioButton
		        		JDBCCarteEffetto.aggiungiCartaEffetto(
		        		nomeCarta.getText(), 
		                mitologiaSelezionata.getText(), 
		                raritaSelezionata.getText(),
		                descrizioneCarta.getText(), 
		                immagineCarta.getImage().getUrl()
		            );
		           
		           
		        }
		        }
		        }
	        else if(indiceTipoCarta==5) {
	        	RadioButton mitologiaSelezionata = (RadioButton) radioMitologia.getSelectedToggle();
		        RadioButton raritaSelezionata = (RadioButton) raritaEquipaggiamento.getSelectedToggle();

		        // Controlla se tutte le informazioni necessarie sono state inserite
		        if (nomeCarta.getText().isEmpty() || mitologiaSelezionata == null || descrizioneCarta.getText().isEmpty()   || immagineCarta.getImage() == null || raritaSelezionata == null) {
		            errorText.setText("E' necessario riempire tutti i campi richiesti per poter aggiungere una carta");
		            errorText.setFill(Color.RED);
		            return;
		        } else {
		        	if(JDBCCarteTerreno.cartaTerrenoEsiste(nomeCarta.getText())) {
		        		errorText.setText("E' già presente una carta con quel nome nel database");
		        		errorText.setFill(Color.RED);
		        		return;
		        	}
		        	else {
		            // Aggiungi la carta usando i valori dei RadioButton
		        		JDBCCarteTerreno.aggiungiCartaTerreno(
		        		nomeCarta.getText(), 
		                mitologiaSelezionata.getText(), 
		                raritaSelezionata.getText(),
		                descrizioneCarta.getText(), 
		                immagineCarta.getImage().getUrl()
		            );
		           
		            
		        }
		        }
		        }
	        else if(indiceTipoCarta==6) {
	        	RadioButton mitologiaSelezionata = (RadioButton) radioMitologia.getSelectedToggle();
		        RadioButton raritaSelezionata = (RadioButton) raritaEquipaggiamento.getSelectedToggle();

		        // Controlla se tutte le informazioni necessarie sono state inserite
		        if (nomeCarta.getText().isEmpty() || mitologiaSelezionata == null || descrizioneCarta.getText().isEmpty()   || immagineCarta.getImage() == null || raritaSelezionata == null) {
		            errorText.setText("E' necessario riempire tutti i campi richiesti per poter aggiungere una carta");
		            errorText.setFill(Color.RED);
		            return;
		        } else {
		        	if(JDBCCarteImprevisto.cartaImprevistoEsiste(nomeCarta.getText())) {
		        		errorText.setText("E' già presente una carta con quel nome nel database");
		        		errorText.setFill(Color.RED);
		        		return;
		        	}
		        	else {
		            // Aggiungi la carta usando i valori dei RadioButton
		        		JDBCCarteImprevisto.aggiungiCartaImprevisto(
		        		nomeCarta.getText(), 
		                mitologiaSelezionata.getText(), 
		                raritaSelezionata.getText(),
		                descrizioneCarta.getText(), 
		                immagineCarta.getImage().getUrl()
		            );
		           
		            
		        }
		        }
		        }
	        errorText.setText("Carta inserita correttamente nel database");
            errorText.setFill(Color.BLACK);
            nomeCarta.setText("");
            descrizioneCarta.setText("");
            attaccoCarta.setText("");
            difesaCarta.setText("");
            radioMitologia.selectToggle(null);
            raritaPersonaggio.selectToggle(null);
            raritaEquipaggiamento.selectToggle(null);
            immagineCarta.setImage(null);
            riquadro.setVisible(true);
            } 
	    	catch (Exception e) {
            gestisciEccezioni(e);
            }
	        }
	    
	    public void rimuoviCarta() {
	    	try {
	    	String nome = nomeCarta.getText();
	        if (!nome.isEmpty()) {
	        	int indice = listCarte.getSelectionModel().getSelectedIndex();
	            if (indiceTipoCarta == 1) {
	                JDBCCartePersonaggio.rimuoviCartaPersonaggio(nome);
	                JDBCCaricamento.caricaCartePersonaggio();
	            } else if (indiceTipoCarta == 2) {
	            	JDBCCarteEquipaggiamento.rimuoviCartaEquipaggiamento(nome);
	                JDBCCaricamento.caricaCarteEquipaggiamento();
	            } else if (indiceTipoCarta == 3) {
	            	JDBCCarteCura.rimuoviCartaCura(nome);
	                JDBCCaricamento.caricaCarteCura();
	            } else if (indiceTipoCarta == 4) {
	            	JDBCCarteEffetto.rimuoviCartaEffetto(nome);
	                JDBCCaricamento.caricaCarteEffetto();
	            } else if (indiceTipoCarta == 5) {
	            	JDBCCarteTerreno.rimuoviCartaTerreno(nome);
	                JDBCCaricamento.caricaCarteTerreno();
	            } else if (indiceTipoCarta == 6) {
	            	JDBCCarteImprevisto.rimuoviCartaImprevisto(nome);
	                JDBCCaricamento.caricaCarteImprevisto();
	            }
	            listCarte.getItems().remove(indice);
	            clearFieldsAndResetView();
	        } else {
	            errorText.setText("SELEZIONARE IL TIPO DI CARTA DA RIMUOVERE");
	        }} catch (Exception e) {
	            gestisciEccezioni(e);
	        }
	    }
	            
	    private boolean isInteger(String str) {
	        if (str == null) {
	            return false;
	        }
	        try {
	            int number = Integer.parseInt(str);
	            return true; // Testo è un intero valido
	        } catch (NumberFormatException e) {
	            return false; // Testo non è un intero
	        }
	    }

	    

		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {
			try {
			cartePersonaggio=JDBCCaricamento.getCartePersonaggio();
			carteCura=JDBCCaricamento.getCarteCura();
			carteEquipaggiamento=JDBCCaricamento.getCarteEquipaggiamento();
			carteEffetto=JDBCCaricamento.getCarteEffetto();
			carteImprevisto=JDBCCaricamento.getCarteImprevisto();
			carteTerreno=JDBCCaricamento.getCarteTerreno();

			//Aggiungi carta
			attacco.setVisible(false);
	    	difesa.setVisible(false);
	    	nome.setVisible(false);
	    	hboxMitologia.setVisible(false);
	    	hboxRaritaPers.setVisible(false);
	    	hboxRaritaEquip.setVisible(false);
	    	rarita.setVisible(false);
	    	mitologia.setVisible(false);
	    	immagineCarta.setVisible(false);
	    	difesaCarta.setVisible(false);
	    	descrizione.setVisible(false);
	    	descrizioneCarta.setVisible(false);
	    	buttonAggiungiCarta.setVisible(false);
	    	attaccoCarta.setVisible(false);
	    	riquadro.setVisible(false);	
	    	nomeCarta.setVisible(false);
	    	
	    	//Rimuovi carta
	    	selezionaButton.setVisible(false);
	    	rimuoviButton.setVisible(false);
	        immListView.setVisible(false);
	        bottoneAggiorna.setVisible(false);
	        } catch (Exception e) {
	            gestisciEccezioni(e);
	        }
	}
		
		
		public void goBack(ActionEvent event) throws IOException{
	    	goBackCheck();
	    }
		
	    public void goBackCheck() throws IOException {
	    	try {
	    	Main m = new Main();
	    	m.setScena("SCENE/SCENA-SCHERMATA-CREAZIONEPARTITA.fxml");
	    }catch (Exception e) {
	        gestisciEccezioni(e);
	    }
	    }
	    
	    private void gestisciEccezioni(Exception e) {
	        if (e instanceof IOException) {
	            System.out.println("Si è verificata un'eccezione di tipo IOException: " + e.getMessage());
	        } else if (e instanceof IllegalArgumentException) {
	            System.out.println("Si è verificata un'eccezione di tipo IllegalArgumentException: " + e.getMessage());
	        } else if (e instanceof NumberFormatException) {
	            System.out.println("Si è verificata un'eccezione di tipo NumberFormatException: " + e.getMessage());
	        } else {
	            System.out.println("Si è verificata un'eccezione: " + e.getClass().getSimpleName() + " - " + e.getMessage());
	        }
	        e.printStackTrace();
	    }

}