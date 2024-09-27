package application.CLASSI;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.application.Platform;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import application.CONTROLLER.ControllerCreazionePartita;

// Classe RobotIntelligente che estende Utente
public class RobotIntelligente extends Utente {
	
	// Istanza di Robot per simulare azioni dell'utente
	Robot robot;
	
	// Costruttore della classe RobotIntelligente
	public RobotIntelligente(String nome, String descrizione) throws AWTException {
		super(nome, descrizione);
		robot = new Robot();
	}
	
	// Metodo per ottenere l'istanza del robot
	public Robot getRobot() {
		return robot;
	}
	
	// Metodo per la selezione dei personaggi
	public void sceltaPersonaggi(ImageView principale, Button successivo, Label labelCarta) throws AWTException, IOException {
	    int numClic = 0; // Contatore per tenere traccia del numero di clic
	    Random random = new Random();
	    TipiMitologie[] mitologie = TipiMitologie.values(); // Array delle mitologie disponibili
	    
	    // Ciclo finché non sono stati effettuati 3 clic
	    while (numClic < 3) {
	        TipiMitologie mitologia = mitologie[random.nextInt(mitologie.length)]; // Seleziona una mitologia casuale
	        
	        // Primo clic
	        while (numClic == 0) {
	            if (JDBCCartePersonaggio.getPersonaggioDaImmagine(principale).getMitologia().equals(mitologia) && 
	                JDBCCartePersonaggio.getPersonaggioDaImmagine(principale).getRarita().equals(TipoRaritaCartaPersonaggio.SPECIALE)) {
	                clicca(robot, principale);
	                clicca(robot, successivo);
	                numClic++;
	            } else {
	                clicca(robot, successivo);
	            }
	            try {
	                Thread.sleep(750); // Pausa di 750ms tra i clic
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }
	        
	        // Secondo clic
	        while (numClic == 1) {
	            if (JDBCCartePersonaggio.getPersonaggioDaImmagine(principale).getMitologia().equals(mitologia) && 
	                JDBCCartePersonaggio.getPersonaggioDaImmagine(principale).getRarita().equals(TipoRaritaCartaPersonaggio.NORMALE)) {
	                clicca(robot, principale);
	                clicca(robot, successivo);
	                numClic++;
	            } else {
	                clicca(robot, successivo);
	            }
	            try {
	                Thread.sleep(750); // Pausa di 750ms tra i clic
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }
	        
	        // Gestisce i clic casuali
	        int x = random.nextInt(6);
	        int y = 0;
	        while (y < x) {
	            clicca(robot, successivo);
	            y++;
	        }        

	        // Terzo clic
	        if (JDBCCartePersonaggio.getPersonaggioDaImmagine(principale).getRarita().equals(TipoRaritaCartaPersonaggio.NORMALE)) {
	        	clicca(robot, principale);
	 	        numClic++;
	        } else {
	        	while (JDBCCartePersonaggio.getPersonaggioDaImmagine(principale).getRarita().equals(TipoRaritaCartaPersonaggio.SPECIALE))
		        	clicca(robot, successivo);
	        	clicca(robot, principale);
	        	numClic++;
	        }
	        if (labelCarta.getText().equals("Puoi selezionare solo una carta speciale")) {
	        	clicca(robot, successivo);
	        	clicca(robot, principale);
	        }
	        	       
	        try {
                Thread.sleep(2000); // Pausa di 2 secondi
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
	    }
	}

	// Metodo per simulare un clic su un elemento
	private void clicca(Robot robot, Node elemento) {
	    // Ottenere le coordinate dell'elemento sulla schermata
	    Bounds bounds = elemento.localToScreen(elemento.getBoundsInLocal());
	    int x = (int) (bounds.getMinX() + bounds.getWidth() / 2);
	    int y = (int) (bounds.getMinY() + bounds.getHeight() / 2);
	    
	    // Muovere il mouse alle coordinate e fare clic
	    robot.mouseMove(x, y);
	    robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
	    robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);	    
	}
	
	// Metodo per gestire l'attacco del robot
	public void robotAttacco(Pane campoCorrente, Button bottoneAttacco) throws AWTException, IOException {
	    CartaPersonaggio attaccante = null;
	    ImageView attImmagine = null;
	    CartaPersonaggio temp = null;

	    // Trova il personaggio con l'attacco attuale più alto
	    for (Node n : campoCorrente.getChildren()) {
	        if (n instanceof ImageView) {
	        	if (!(((ImageView) n).getImage() == null)) {
	            	temp = JDBCCartePersonaggio.getPersonaggioDaImmagine((ImageView) n);
	        	}
	            if (attaccante == null || temp.getAttAttuale() > attaccante.getAttAttuale()) {
	                attaccante = temp;
	                attImmagine = (ImageView) n;
	            }
	        }
	    }

	    try {
	        Thread.sleep(750); // Pausa di 750ms
	    } catch (InterruptedException e) {
	        e.printStackTrace();
	    }

	    clicca(robot, attImmagine);

	    try {
	        Thread.sleep(750); // Pausa di 750ms
	    } catch (InterruptedException e) {
	        e.printStackTrace();
	    }

	    clicca(robot, bottoneAttacco);
	}
	
	// Metodo per far pescare una carta al robot
	public void robotPesca(ImageView carta, Runnable runnable) {		 
		clicca(robot, carta);
		try {
	        Thread.sleep(750); // Pausa di 750ms
	    } catch (InterruptedException e) {
	        e.printStackTrace();
	    }
	}
	
	// Metodo per far passare il turno al robot
	public void robotPassa(Button passa) {
		try {
	        Thread.sleep(750); // Pausa di 750ms
	    } catch (InterruptedException e) {
	        e.printStackTrace();
	    }
		clicca(robot, passa);
	}
	
	// Metodo per riconoscere una carta nella mano del giocatore
	private CartaGiocabile riconoscimentoCarta(ImageView img, ArrayList<CartaGiocabile> mano) {
		CartaGiocabile temp = null;
		esterno: for (CartaGiocabile c : mano) {
			if (c.getImmagine().equals(img.getImage())) {
				temp = c;
				break esterno;
			}
		}
		return temp;
	}
	
	// Metodo per giocare una carta di cura
	public void giocaCura(ImageView img, ArrayList<CartaGiocabile> mano, ArrayList<CartaPersonaggio> personaggi, Pane campoCorrente) throws AWTException, IOException {
		CartaGiocabile temp = riconoscimentoCarta(img, mano);
		for (int i = 0; i < personaggi.size() - 1; i++) {   
		    for (int j = 0; j < personaggi.size() - i - 1; j++) { // Ordina i personaggi in base ai PS attuali in ordine crescente
		        if (personaggi.get(j).getPsAttuali() > personaggi.get(j + 1).getPsAttuali()) {
		            CartaPersonaggio pgTemp = personaggi.get(j);
		            personaggi.set(j, personaggi.get(j + 1));
		            personaggi.set(j + 1, pgTemp);
		        }
		    }
		}
		
		if (temp != null) {			
			clicca(robot, img);
			try {
		        Thread.sleep(750); // Pausa di 750ms
		    } catch (InterruptedException e) {                                
		        e.printStackTrace();
		    }  
			
			// Cura di un singolo target
			if (temp.getTarget() == 1) {
				for (Node n : campoCorrente.getChildren()) {
					if (n instanceof ImageView) {
						if (personaggi.get(0).getImmagine().equals(((ImageView) n).getImage())) {
							try {
						        Thread.sleep(750); // Pausa di 750ms
						    } catch (InterruptedException e) {
						        e.printStackTrace();
						    }
							clicca(robot, n);
						}
					}
				}
			}
			
			// Cura di due target
			if (temp.getTarget() == 2) {
				for (Node n : campoCorrente.getChildren()) {
					if (n instanceof ImageView) {
						if (personaggi.size() >= 2) {
							if (personaggi.get(0).getImmagine().equals(((ImageView) n).getImage()) || 
								personaggi.get(1).getImmagine().equals(((ImageView) n).getImage())) {
								try {
							        Thread.sleep(750); // Pausa di 750ms
							    } catch (InterruptedException e) {
							        e.printStackTrace();
							    }
	          				    clicca(robot, n);
						 	}
					   } else {
							clicca(robot, n);						
							clicca(robot, n);
						}
					}
				}
			}			
		}		
	}
	
	// Metodo per giocare una carta di equipaggiamento
	public void giocaEquipaggiamento(ImageView img, ArrayList<CartaGiocabile> mano, ArrayList<CartaPersonaggio> personaggi, Pane campoCorrente) throws AWTException, IOException {
		CartaGiocabile temp = riconoscimentoCarta(img, mano);
		for (int i = 0; i < personaggi.size() - 1; i++) {   
		    for (int j = 0; j < personaggi.size() - i - 1; j++) { // Ordina i personaggi in base all'attacco attuale in ordine crescente
		        if (personaggi.get(j).getAttAttuale() > personaggi.get(j + 1).getAttAttuale()) {
		            CartaPersonaggio pgTemp = personaggi.get(j);
		            personaggi.set(j, personaggi.get(j + 1));
		            personaggi.set(j + 1, pgTemp);
		        }
		    }
		}
		
		if (temp != null) {						
			clicca(robot, img);	
			try {
		        Thread.sleep(750); // Pausa di 750ms
		    } catch (InterruptedException e) {                                
		        e.printStackTrace();
		    }  
			
			// Cicla attraverso i personaggi in ordine inverso per trovare il primo con meno di 3 equipaggiamenti
			esterno: for (int i = personaggi.size() - 1; i >= 0; i--) {
				if (personaggi.get(i).getEquip().size() < 3) {
					for (Node n : campoCorrente.getChildren()) {
						if (n instanceof ImageView) {
							if (personaggi.get(i).getImmagine().equals(((ImageView) n).getImage())) {
								try {
							        Thread.sleep(750); // Pausa di 750ms
							    } catch (InterruptedException e) {                                
							        e.printStackTrace();
							    }                                                   
								clicca(robot, n);  
								break esterno; // Esce dal ciclo una volta trovato il personaggio
							}
						}
					}
				}
			}
		}		
	}
	
	// Metodo per giocare una carta di effetto
	public void giocaEffetto(ImageView img, ArrayList<CartaGiocabile> mano, ArrayList<CartaPersonaggio> personaggi, Pane campoCorrente) throws AWTException, IOException {
		CartaGiocabile temp = riconoscimentoCarta(img, mano);
		for (int i = 0; i < personaggi.size() - 1; i++) {   
		    for (int j = 0; j < personaggi.size() - i - 1; j++) { // Ordina i personaggi in base all'attacco attuale in ordine crescente
		        if (personaggi.get(j).getAttAttuale() > personaggi.get(j + 1).getAttAttuale()) {
		            CartaPersonaggio pgTemp = personaggi.get(j);
		            personaggi.set(j, personaggi.get(j + 1));
		            personaggi.set(j + 1, pgTemp);
		        }
		    }
		}
		
		if (temp != null) {			
			clicca(robot, img);
			try {
		        Thread.sleep(750); // Pausa di 750ms
		    } catch (InterruptedException e) {                                
		        e.printStackTrace();
		    } 
			
			// Applica l'effetto a un target specifico
			if (temp.getTarget() == 4) {
				for (Node n : campoCorrente.getChildren()) {
					if (n instanceof ImageView) {
						if (personaggi.get(personaggi.size() - 1).getImmagine().equals(((ImageView) n).getImage())) {
							try {
						        Thread.sleep(750); // Pausa di 750ms
						    } catch (InterruptedException e) {                                
						        e.printStackTrace();
						    }                                                    
							clicca(robot, n);  							           
						}
					}
				}
			}
		}
	}
}
