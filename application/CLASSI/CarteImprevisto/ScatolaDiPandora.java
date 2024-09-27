package application.CLASSI.CarteImprevisto;

import application.CLASSI.CartaImprevisto;
import application.CLASSI.CartaPersonaggio;
import application.CLASSI.RaritaCartaGiocabile;
import application.CLASSI.TipiMitologie;
import application.CLASSI.Utente;
import application.CONTROLLER.ControllerCreazionePartita;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import java.util.Random;

public class ScatolaDiPandora extends CartaImprevisto{
	
	public ScatolaDiPandora(String nome, TipiMitologie mitologia, RaritaCartaGiocabile rarita, String effetto, Image immagine) {
        super(nome, mitologia, rarita, effetto, immagine);
        target=2;
    }
	
	public void effettoImprevisto(Utente u, Button b) {
	    Random random=new Random();
	    int x=random.nextInt(3);
	    for(CartaPersonaggio c:ControllerCreazionePartita.p.getPersonaggiScelti().get(u)) {
	    	if(x==0)
	    		c.lessPs(500);
	    	else if(x==1)
	    		c.lessAtt(500);
	    	if(c.getPsAttuali()<=0)
	    		c.setPsAttuali(1);
	    }
	    if(x>1)
	    	b.setVisible(false);
	}
}