package application;
	
import java.awt.AWTException;
import java.io.File;
import java.io.IOException;
import application.CLASSI.JDBCCaricamento;
import application.CLASSI.JDBCCarteCura;
import application.CLASSI.JDBCCarteEffetto;
import application.CLASSI.JDBCCarteEquipaggiamento;
import application.CLASSI.JDBCCarteImprevisto;
import application.CLASSI.JDBCCartePersonaggio;
import application.CLASSI.JDBCCarteTerreno;
import application.CLASSI.JDBCPartite;
import application.CLASSI.JDBCTornei;
import application.CLASSI.JDBCUtenti;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

public class Main extends Application {
	
	
	public static Stage stage;
	private double originalWidth = 900;
    private double originalHeight = 600;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			stage = primaryStage;
			Parent root1 = FXMLLoader.load(getClass().getResource("SCENE/SCENA-SCHERMATA-INIZIALE-2.fxml"));
			Scene scene1 = new Scene(root1);
			scene1.getStylesheets().add(getClass().getResource("CSSStyleSheets/Stile-Scena-Iniziale.css").toExternalForm());
			primaryStage.setScene(scene1);
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setScena(String fxml) throws IOException {
	    Parent parent = FXMLLoader.load(getClass().getResource(fxml));
	    Scene newScene = new Scene(parent);
	    stage.setScene(newScene);

	    if (fxml.equals("SCENE/SCENA-PARTITA.fxml")) {
	    	stage.setHeight(830);
	    	stage.setWidth(1400);
	    	stage.centerOnScreen();
	    	stage.setResizable(false);
	    }
	}
	
	public void resetDimensioniOriginali() {
        stage.setHeight(originalHeight);
        stage.setWidth(originalWidth);
        stage.centerOnScreen();
    	stage.setResizable(false);
    	stage.sizeToScene();
    }
	
	public void adattaSchermata() {
	    stage.sizeToScene();
	}
	
	public static void main(String[] args) throws AWTException {
		System.out.println(JDBCCaricamento.getPathComputer());
		
		
		//CREAZIONI TABELLE SE NON PRESENTI
		JDBCUtenti.creaTabellaGiocatori();
		JDBCPartite.creaTabellaPartite();
		JDBCCarteEquipaggiamento.creaTabellaCarteEquipaggiamento();
		JDBCCarteCura.creaTabellaCarteCura();
		JDBCCartePersonaggio.creaTabellaCartePersonaggio();
		JDBCCarteEffetto.creaTabellaCarteEffetto();
		JDBCCarteTerreno.creaTabellaCarteTerreno();
		JDBCCarteImprevisto.creaTabellaCarteImprevisto();
		JDBCTornei.creaTabellaTornei();
		
		//AGGIORNA PERCORSI DATABASE
		JDBCCartePersonaggio.aggiornaPercorsiImmaginiPersonaggio();
		JDBCCarteEquipaggiamento.aggiornaPercorsiImmaginiEquipaggiamento();
		JDBCCarteCura.aggiornaPercorsiImmaginiCura();
		JDBCCarteEffetto.aggiornaPercorsiImmaginiEffetto();
		JDBCCarteImprevisto.aggiornaPercorsiImmaginiImprevisto();
		JDBCCarteTerreno.aggiornaPercorsiImmaginiTerreno();
		
		//CARICA DATABASE
		System.out.println("Utenti caricati: ");
		JDBCCaricamento.caricaUtenti();
		System.out.println("Carte personaggio caricate: ");
		JDBCCaricamento.caricaCartePersonaggio();
		System.out.println("Carte equipaggiamento caricate: ");
		JDBCCaricamento.caricaCarteEquipaggiamento();
		System.out.println("Carte cura caricate: ");
		JDBCCaricamento.caricaCarteCura();
		System.out.println("Carte effetto caricate: ");
		JDBCCaricamento.caricaCarteEffetto();
		System.out.println("Carte imprevisto caricate: ");
		JDBCCaricamento.caricaCarteImprevisto();
		System.out.println("Carte terreno caricate: ");
		JDBCCaricamento.caricaCarteTerreno();
		System.out.println("Partite caricate");
		JDBCCaricamento.caricaPartita();
		System.out.println("Tornei caricati");
		JDBCCaricamento.caricaTornei();
		//JDBCCartePersonaggio.stampaTutteLeCarte();
		//JDBCPartite.stampaTuttePartite();
		//JDBCUtenti.stampaTuttiGiocatori();
		launch();

	}
}
