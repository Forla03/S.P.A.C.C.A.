package application.CLASSI.CarteEffetto;

import application.CLASSI.CartaEffetto;
import application.CLASSI.CartaGiocabile;
import application.CLASSI.RaritaCartaGiocabile;
import application.CLASSI.TipiMitologie;
import application.CLASSI.Utente;
import application.CONTROLLER.ControllerCreazionePartita;
import javafx.scene.image.Image;


public class EbbrezzaDiBacco extends CartaEffetto {
	
	public EbbrezzaDiBacco(String nome, TipiMitologie mitologia, RaritaCartaGiocabile rarita, String effetto, Image immagine) {
        super(nome, mitologia, rarita, effetto, immagine);  
        target=3;
    }
	
	public void effetto3(Utente u) {
	    boolean romano = false;
	    if (ControllerCreazionePartita.p.getMazzo().size() >= 2) {
	        for (int i = 0; i < 2; i++) {
	            if (ControllerCreazionePartita.p.getMano().get(u).size() < 8) {
	                CartaGiocabile c = ControllerCreazionePartita.p.getMazzo().remove(0);
	                ControllerCreazionePartita.p.getMano().get(u).add(c);
	                if (c.getMitologia().equals(TipiMitologie.ROMANA))
	                    romano = true;
	            } else {
	                System.out.println("Mano piena");
	            }
	        }
	    } else {
	        if (!ControllerCreazionePartita.p.getMazzo().isEmpty() && 
	            ControllerCreazionePartita.p.getMano().get(u).size() < 8) {
	            CartaGiocabile c = ControllerCreazionePartita.p.getMazzo().remove(0);
	            ControllerCreazionePartita.p.getMano().get(u).add(c);
	            if (c.getMitologia().equals(TipiMitologie.ROMANA))
	                romano = true;
	        } else {
	            System.out.println("Impossibile pescare");
	        }
	    }
	    if (romano) {
	        if (ControllerCreazionePartita.p.getMazzo().isEmpty() || 
	            ControllerCreazionePartita.p.getMano().get(u).size() >= 8) {
	            System.out.println("Impossibile pescare carta aggiuntiva");
	        } else {
	            ControllerCreazionePartita.p.getMano().get(u).add(ControllerCreazionePartita.p.getMazzo().remove(0));
	        }
	    }
	}
}