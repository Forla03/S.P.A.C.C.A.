package application.CLASSI.CarteEquipaggiamento;

import application.CLASSI.CartaEquipaggiamento;
import application.CLASSI.CartaPersonaggio;
import application.CLASSI.RaritaCartaGiocabile;
import application.CLASSI.TipiMitologie;
import application.CLASSI.Utente;
import application.CONTROLLER.ControllerCreazionePartita;
import javafx.scene.image.Image;

public class PettineDoroDiAfrodite extends CartaEquipaggiamento{
	
	public PettineDoroDiAfrodite(String nome, TipiMitologie mitologia, RaritaCartaGiocabile rarita, String effetto, Image immagine) {
        super(nome, mitologia, rarita, effetto, immagine);
        target=4;
    }
	
    public void effettoCura(int x,CartaPersonaggio c) {
    	int y=0;
    	int t=0;
    	for(int i=0;i<x;i++) {
    		if(i==t+500) {
    			y+=1;
    			t=i;
    		}
    			
    	}
    	c.addPs(100*y);
    }
    
	public String getRichiestaEffetto() {
		return "Clicca su un personaggio";
	}
}

