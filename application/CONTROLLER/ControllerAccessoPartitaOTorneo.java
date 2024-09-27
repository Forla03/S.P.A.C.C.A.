package application.CONTROLLER;

import java.io.IOException;

import application.Main;

public class ControllerAccessoPartitaOTorneo {
	
	public void accessoPartita() {
	    try {
	        Main m = new Main();
	        m.setScena("SCENE/SCENA-SCHERMATA-PLAYBUTTON.fxml");
	    } catch (Exception e) {
	        gestisciEccezioni(e);
	    }
	}

	public void accessoTorneo() {
	    try {
	        Main m = new Main();
	        m.setScena("SCENE/SCENA-VERIFICA-CODICE-TORNEO.fxml");
	    } catch (Exception e) {
	        gestisciEccezioni(e);
	    }
	}

	public void goBack() {
	    try {
	        Main m = new Main();
	        m.setScena("SCENE/SCENA-SCHERMATA-INIZIALE-2.fxml");
	    } catch (Exception e) {
	        gestisciEccezioni(e);
	    }
	}

	
	public void gestisciEccezioni(Exception e) {
	    if (e instanceof IOException) {
	        System.err.println("Errore di I/O durante l'accesso alla risorsa.");
	    } else {
	        System.err.println("Si è verificata un'eccezione non gestita.");
	    }
	}

}