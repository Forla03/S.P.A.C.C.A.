package application.CLASSI.CarteImprevisto;

import application.CLASSI.CartaImprevisto;
import application.CLASSI.CartaPersonaggio;
import application.CLASSI.RaritaCartaGiocabile;
import application.CLASSI.TipiMitologie;
import application.CLASSI.Utente;
import application.CONTROLLER.ControllerCreazionePartita;
import javafx.scene.control.Button;
import javafx.scene.image.Image;

public class ViaggioDiEnea extends CartaImprevisto{
	
	public ViaggioDiEnea(String nome, TipiMitologie mitologia, RaritaCartaGiocabile rarita, String effetto, Image immagine) {
        super(nome, mitologia, rarita, effetto, immagine);
        target=2;
    }
	
	public void effettoImprevisto(Utente u, Button b) {
	    int x=0;
	    for(CartaPersonaggio c:ControllerCreazionePartita.p.getPersonaggiScelti().get(u)) {
	    	if(c.getMitologia().equals(TipiMitologie.ROMANA))
	    		x+=1;
	    }
	    if(x<1)
	    	b.setVisible(false);
	}
}