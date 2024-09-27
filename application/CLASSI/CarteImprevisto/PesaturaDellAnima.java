package application.CLASSI.CarteImprevisto;

import application.CLASSI.CartaImprevisto;
import application.CLASSI.CartaPersonaggio;
import application.CLASSI.RaritaCartaGiocabile;
import application.CLASSI.TipiMitologie;
import application.CLASSI.Utente;
import application.CONTROLLER.ControllerCreazionePartita;
import javafx.scene.image.Image;

public class PesaturaDellAnima extends CartaImprevisto{
	
	public PesaturaDellAnima(String nome, TipiMitologie mitologia, RaritaCartaGiocabile rarita, String effetto, Image immagine) {
        super(nome, mitologia, rarita, effetto, immagine);
    }
	
	public void effetto3(Utente u) {
	    int x = 0;
	    int y = 0;
	    for(CartaPersonaggio c: ControllerCreazionePartita.p.getPersonaggiScelti().get(u)) {
	        x += c.getAtt();
	        while (x / 1000 > y) {
	            y++;
	        }
	    }
	    int danno = y * 100;
	    for(CartaPersonaggio c: ControllerCreazionePartita.p.getPersonaggiScelti().get(u)) {
	    	c.lessPs(danno);
	    	if(c.getPsAttuali()<=0)
	    		c.setPsAttuali(1);
	    }
	}
}
