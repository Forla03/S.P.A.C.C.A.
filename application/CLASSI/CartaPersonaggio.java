package application.CLASSI;


import java.util.List;

import javafx.scene.image.Image;
import java.util.ArrayList;

public class CartaPersonaggio implements Cloneable{

	protected TipiMitologie mitologia;
	protected String nome;
	protected String descrizione;
	protected int att;
	protected int def;
	protected Image immagine;
	protected TipoRaritaCartaPersonaggio rarita;
	protected int attAttuale;
	protected int psAttuali;
	protected ArrayList<CartaEquipaggiamento> equipaggiamento=new ArrayList<>(1);
	
	public CartaPersonaggio(String nome, TipiMitologie mitologia, String descrizione, int att, int def, Image immagine, TipoRaritaCartaPersonaggio rarita) {
		this.nome=nome;
		this.mitologia=mitologia;
		this.rarita=rarita;
		this.descrizione=descrizione;
		this.att=att;
		this.def=def;
		this.immagine=immagine;
		attAttuale=att;
		psAttuali=def;
	}
	
	public CartaPersonaggio(String nome, TipiMitologie mitologia, String descrizione, int att, int def, Image immagine, TipoRaritaCartaPersonaggio rarita, int attAttuale, int psAttuali) {
		this.nome=nome;
		this.mitologia=mitologia;
		this.rarita=rarita;
		this.descrizione=descrizione;
		this.att=att;
		this.def=def;
		this.immagine=immagine;
		this.attAttuale=attAttuale;
		this.psAttuali=psAttuali;
	}
	
	public String toString() {
	    return "Carta{" +
	           "nome='" + nome + '\'' +
	           ", mitologia=" + mitologia +
	           ", rarita'=" + rarita +
	           ", descrizione='" + descrizione + '\'' +
	           ", att=" + att +
	           ", def=" + def +
	           ", immaginePath='" + immagine.getUrl() + '\'' +
	           '}';
	}
	
	public String getUrlImmagine() {
		return immagine.getUrl();
	}
	
	public Image getImmagine() {
        return immagine;
    }
	
	public String getNome() {
		return nome;
	}
	
	public TipiMitologie getMitologia() {
		return mitologia;
	}
	
	public String getTipoString() {
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
	
	public String getDescrizione() {
		return descrizione;
	}
	
	public String getAttString() {
		return att+"";
	}
	
	public String getDefString() {
		return def+"";
	}
	
	public int getAtt() {
		return att;
	}
	
	public int getDef() {
		return def;
	}
	
	public TipoRaritaCartaPersonaggio getRarita() {
		return rarita;
	}
	
	public String getRaritaString() {
		if(rarita.equals(TipoRaritaCartaPersonaggio.NORMALE)) {
			return "NORMALE";
		}
		else {
			return "SPECIALE";
		}
	}
	
	public int getAttAttuale() {
		return attAttuale;
	}
	
	public int getPsAttuali() {
		return psAttuali;
	}
	
	public void setAttAttuale(int x) {
		attAttuale=x;
	}
	
	public void setPsAttuali(int x) {
		psAttuali=x;
	}
	
	public void addAtt(int x) {
		attAttuale+=x;
	}
	
	public void addPs(int x) {
		psAttuali+=x;
	}
	
	public void lessAtt(int x) {
		attAttuale-=x;
	}
	
	public void lessPs(int x) {
		psAttuali-=x;
	}
	
	public static void rimuoviPersonaggio(CartaPersonaggio personaggio, List<CartaPersonaggio> listaPersonaggi) {
        if (personaggio.getPsAttuali() <= 0) {
            listaPersonaggi.remove(personaggio);
            System.out.println("Personaggio " + personaggio.getNome() + " rimosso dalla lista");
        }
	}
	
	public void addEquip(CartaEquipaggiamento e) {
		equipaggiamento.add(e);
	}
	
	public void addEquipLista(ArrayList<CartaEquipaggiamento> e) {
		for(CartaEquipaggiamento c:e) {
			equipaggiamento.add(c);
		}
	}
	
	public ArrayList<CartaEquipaggiamento> getEquip(){
		return equipaggiamento;
	}
	
	@Override
	public CartaPersonaggio clone() {
	    try {
	        CartaPersonaggio copia = (CartaPersonaggio) super.clone();
	        
	        // Creiamo una nuova lista per la copia
	        copia.equipaggiamento = new ArrayList<>();
	        
	        // Cloniamo ogni elemento della lista
	        for (CartaGiocabile equip : this.equipaggiamento) {
	            copia.equipaggiamento.add((CartaEquipaggiamento)equip.clone());
	        }
	        return copia;
	    } catch (CloneNotSupportedException e) {
	    	System.out.println("Operazione di clonazione non supportata");
	    	e.printStackTrace();
	        return null; 
	    }
	}
}
