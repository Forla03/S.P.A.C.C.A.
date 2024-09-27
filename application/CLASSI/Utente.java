package application.CLASSI;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Utente {

	protected String nome;	
	protected int punteggio;
	protected String descrizione;
	
	public Utente(String nome, String descrizione ) {
		this.nome=nome;	
		this.descrizione=descrizione;
		punteggio=0;
	}
	
	public Utente(String nome, String descrizione,int punteggio) {
		this.nome=nome;
		this.descrizione=descrizione;
		this.punteggio=punteggio;
	}
	
	public static void creaUtente(String username) {
		Utente utente=new Utente(username,"");
	}
	
	public String getNome() {
		return nome;
	}
	
	public String toString() {
		return "Nome: " + nome + ", Descrizione: "+ descrizione + ", Punteggio: " + punteggio;
	}
	
	public void addPunti(int punti) {
		punteggio+=punti;
	}
	
	public int getPunteggio() {
		return punteggio;
	}
	
	public static void eliminaUtente(Utente utente, List<Utente> utenti, List<CartaPersonaggio> personaggiDifensori) {
		if(personaggiDifensori.isEmpty()) {
			utenti.remove(utente);
			System.out.println("L'utente " + utente.getNome() + " e' stato eliminato.");
		}
	}	
}
