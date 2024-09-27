package application.CLASSI;

import javafx.scene.image.Image;

public class CartaEquipaggiamento extends CartaGiocabile {
    
    public CartaEquipaggiamento(String nome, TipiMitologie mitologia, RaritaCartaGiocabile rarita, String effetto, Image immagine) {
        super(nome, mitologia, rarita, effetto, immagine);      
    }
    
    public int effettoRiduzioneDanno(int x, Utente u) {
    	return 0;
    }
    
    public int effettoAmplificazioneDanno(int x) {
    	return 0;
    }
    
    public void effettoCura(int x,CartaPersonaggio c) {
    	
    }
    
    public void ricalcolo(Utente u,CartaPersonaggio c) {
    	
    }
}

