package application.CLASSI;

import javafx.scene.image.Image;

public class CartaTerreno extends CartaGiocabile {
    
    public CartaTerreno(String nome, TipiMitologie mitologia, RaritaCartaGiocabile rarita, String effetto, Image immagine) {
        super(nome, mitologia, rarita, effetto, immagine);
    }
    
    public int effettoRiduzioneDannoTerreno(CartaPersonaggio c) {		
		return 0;
	}	
}
