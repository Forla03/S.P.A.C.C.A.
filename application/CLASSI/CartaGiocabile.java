package application.CLASSI;

import java.util.List;

import javafx.scene.control.Button;
import javafx.scene.image.Image;

public class CartaGiocabile implements Cloneable{

	protected String nome;
	protected TipiMitologie mitologia;
	protected RaritaCartaGiocabile rarita;
	protected String effetto;
	protected int attAgg;
	protected Image immagine;
	protected int target;
	protected int attAggiuntoPrec;
	protected int psAggiuntiPrec;
	
	public CartaGiocabile(String nome, TipiMitologie mitologia, RaritaCartaGiocabile rarita, String effetto, Image immagine) {
		this.nome=nome;
		this.mitologia=mitologia;
		this.rarita=rarita;
		this.effetto=effetto;
		this.immagine=immagine;
		target=1;
	}
	
	public String toString() {
	    return "Carta{" +
	           "nome='" + nome + '\'' +
	           ", mitologia=" + mitologia +
	           ", rarita'=" + rarita +
	           ", effetto='" + effetto + '\'' +
	           ", immaginePath='" + immagine.getUrl() + '\'' +
	           '}';
	}
	
	public String getNome() {
		return nome;
	}
	
	public TipiMitologie getMitologia() {
		return mitologia;
	}

	public String getEffetto() {
		return effetto;
	}

	public String getRichiestaEffetto() {
		return "";
	}
	
	public Image getImmagine() {
		return immagine;
	}
	
	public RaritaCartaGiocabile getRarita() {
		return rarita;
	}
	
	public String getUrlImmagine() {
		return immagine.getUrl();
	}

	public String getMitologiaString() {
			if (mitologia.equals(TipiMitologie.EGIZIA)) {
				return "EGIZIA";
			}
			else if(mitologia.equals(TipiMitologie.GRECA)) {
				return "GRECA";
			}
			else if(mitologia.equals(TipiMitologie.NORRENA)) {
				return "NORRENA";
			}
			else  {
				return "ROMANA";
			}
		} 
	
	public String getRaritaString() {
		if (rarita.equals(RaritaCartaGiocabile.COMUNE)) {
			return "COMUNE";
		}
		else if (rarita.equals(RaritaCartaGiocabile.RARA)) {
			return "RARA";
		}
		else  {
			return "EPICA";
		}
	}
	
	@Override
    public CartaGiocabile clone() {
        try {
            return (CartaGiocabile) super.clone();
        } catch (CloneNotSupportedException e) {
            return null; 
        }
    }
	
	public void effetto(Utente u,CartaPersonaggio c) {
	    	
	}
	    
	public void effetto2(Utente u,CartaPersonaggio c1,CartaPersonaggio c2) {
		
	}
	 
	public void effetto3(Utente u) {
	 	
	}
	
	public void effettoEffetto1(Utente u, CartaPersonaggio c) {
	    	
	}
	 
	public void effettoEffetto2(CartaPersonaggio c, Utente u1, Utente u2, Runnable callback){
		 
	}
	 
    public void effettoEffetto3(CartaPersonaggio c,List<Utente> utenti){
		 
	}
     
    public void effettoEffetto4(List<Utente> utenti){
		 
	}
     
    public void effettoEffetto5(Utente u1,Utente u2, CartaGiocabile c) {
	    	
	}
	    
	public void setTarget(int x) {
		target=x;
	}
	    
	public int getTarget(){
		return target;
	}
	      
	public void setAttAggiuntoPrec() {
	    	
	}
	 
	public int getAttAggiuntoPrec() {
		return attAggiuntoPrec;
	}
	
	public void setPsAggiuntiPrec() {
	    	
	}
	 
	public int getPsAggiuntiPrec() {
		return psAggiuntiPrec;
	}
	 
    public void effettoImprevisto(Utente u, Button b) {
	    	
	}
}