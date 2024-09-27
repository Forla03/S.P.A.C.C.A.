package application.CONTROLLER;

import java.io.IOException;
import application.Main;

public class ControllerPartitaOTorneo {
	
	public void sceltaPartita() throws IOException{
		try {
		Main m=new Main();
		m.setScena("SCENE/SCENA-SCHERMATA-CREAZIONEPARTITA.fxml");
	}catch(Exception e) {
		gestisciEccezioni(e);
	}
	}
	
	public void sceltaTorneo() throws IOException{
		try {
		Main m=new Main();
		m.setScena("SCENE/SCENA-SCHERMATA-CREAZIONE-TORNEO.fxml");
		}catch(Exception e) {
    		gestisciEccezioni(e);
    	}
	}
	
	public void goBack() throws IOException {
    	try {
		Main m = new Main();
    	m.setScena("SCENE/SCENA-SCHERMATA-ZONAAMMINISTRATORE.fxml");
    	}catch(Exception e) {
    		gestisciEccezioni(e);
    	}
    }
	
	private void gestisciEccezioni(Exception e) {
	    if (e instanceof IOException) {
	        System.out.println("Si è verificata un'eccezione di tipo IOException: " + e.getMessage());
	        e.printStackTrace();
	    } else if (e instanceof NullPointerException) {
	        System.out.println("Si è verificata un'eccezione di tipo NullPointerException: " + e.getMessage());
	        e.printStackTrace();
	    } else if (e instanceof IllegalArgumentException) {
	        System.out.println("Si è verificata un'eccezione di tipo IllegalArgumentException: " + e.getMessage());
	        e.printStackTrace();
	    } else if (e instanceof IllegalStateException) {
	        System.out.println("Si è verificata un'eccezione di tipo IllegalStateException: " + e.getMessage());
	        e.printStackTrace();
	    } else {
	        System.out.println("Si è verificata un'eccezione generica: " + e.getMessage());
	        e.printStackTrace();
	    }
	}

}
