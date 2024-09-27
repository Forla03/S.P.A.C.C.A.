package application.CLASSI.CarteEffetto;

import java.util.ArrayList;
import java.util.List;

import application.CLASSI.CartaCura;
import application.CLASSI.CartaEffetto;
import application.CLASSI.CartaEquipaggiamento;
import application.CLASSI.CartaGiocabile;
import application.CLASSI.CartaPersonaggio;
import application.CLASSI.RaritaCartaGiocabile;
import application.CLASSI.TipiMitologie;
import application.CLASSI.Utente;
import application.CONTROLLER.ControllerCreazionePartita;
import javafx.scene.image.Image;
import java.util.Random;


public class IraDelReDellOlimpo extends CartaEffetto {
	
	public IraDelReDellOlimpo(String nome, TipiMitologie mitologia, RaritaCartaGiocabile rarita, String effetto, Image immagine) {
        super(nome, mitologia, rarita, effetto, immagine);  
        target=5;
    }
	
	public void effettoEffetto4(List<Utente> utenti) {
	    CartaPersonaggio bersaglio = ControllerCreazionePartita.p.getPersonaggiScelti().get(utenti.get(0)).get(0);
	    Utente utenteBersaglio = utenti.get(0);
	    Random random = new Random();
	    
	    for (Utente u : utenti) {
	        for (CartaPersonaggio c : ControllerCreazionePartita.p.getPersonaggiScelti().get(u)) {
	            if (bersaglio.getMitologia().equals(TipiMitologie.GRECA) && c.getMitologia().equals(TipiMitologie.GRECA) && c.getPsAttuali() < bersaglio.getPsAttuali()) {
	                bersaglio = c;
	                utenteBersaglio = u;
	            } else if (bersaglio.getMitologia().equals(TipiMitologie.GRECA) && c.getMitologia().equals(TipiMitologie.GRECA) && c.getPsAttuali() == bersaglio.getPsAttuali()) {
	                int x = random.nextInt(2);
	                if (x > 0) {
	                    bersaglio = c;
	                    utenteBersaglio = u;
	                }
	            } else if (!bersaglio.getMitologia().equals(TipiMitologie.GRECA) && c.getMitologia().equals(TipiMitologie.GRECA)) {
	                bersaglio = c;
	                utenteBersaglio = u;
	            } else if (!bersaglio.getMitologia().equals(TipiMitologie.GRECA) && !c.getMitologia().equals(TipiMitologie.GRECA) && c.getPsAttuali() < bersaglio.getPsAttuali()) {
	                bersaglio = c;
	                utenteBersaglio = u;
	            } else if (!bersaglio.getMitologia().equals(TipiMitologie.GRECA) && !c.getMitologia().equals(TipiMitologie.GRECA) && c.getPsAttuali() == bersaglio.getPsAttuali()) {
	                int x = random.nextInt(2);
	                if (x > 0) {
	                    bersaglio = c;
	                    utenteBersaglio = u;
	                }
	            }
	        }
	    }
	    
	    for (Utente u : utenti) {
	        if (ControllerCreazionePartita.p.getPersonaggiScelti().get(u).contains(bersaglio) && u.equals(utenteBersaglio))
	            ControllerCreazionePartita.p.getPersonaggiScelti().get(u).remove(bersaglio);
	    }
	}
}