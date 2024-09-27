package application.CONTROLLER;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import application.Main;
import application.CLASSI.*;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;

public class ControllerScenaCarte implements Initializable {

    @FXML
    private Text attCarta;

    @FXML
    private Button backButton;

    @FXML
    private Button bottoneCarte;

    @FXML
    private Button bottoneEffetto;

    @FXML
    private Button bottoneImprevisto;

    @FXML
    private Button bottoneTerreno;

    @FXML
    private Text defCarta;

    @FXML
    private Text descrizioneCarta;

    @FXML
    private CheckBox egiziaMitologia;

    @FXML
    private CheckBox grecaMitologia;

    @FXML
    private ImageView imm1;

    @FXML
    private ImageView imm2;

    @FXML
    private ImageView immagineCarta;

    @FXML
    private Text mitologiaCarta;

    @FXML
    private Text nomeCarta;

    @FXML
    private CheckBox norrenaMitologia;

    @FXML
    private Text numCartPrec;

    @FXML
    private Text numCartPrinc;

    @FXML
    private Text numCartSucc;

    @FXML
    private Pane popUp;

    @FXML
    private ImageView principale;

    @FXML
    private Text raritaCarta;

    @FXML
    private HBox riquadro;

    @FXML
    private CheckBox romanaMitologia;
    
    @FXML
    private TextField cercaNome;
    
    private HashMap<Integer, CartaPersonaggio> cartePersonaggio = new HashMap<>();
    private HashMap<Integer,CartaEquipaggiamento> carteEquipaggiamento=new HashMap<>();
    private HashMap<Integer,CartaCura> carteCura=new HashMap<>();
    private HashMap<Integer,CartaEffetto> carteEffetto=new HashMap<>();
    private HashMap<Integer,CartaImprevisto> carteImprevisto=new HashMap<>();
    private HashMap<Integer,CartaTerreno> carteTerreno=new HashMap<>();
    
    
    private List<CheckBox> checkBoxList = new ArrayList<>();

    private int indice=1;
    private int segnaTipo=1;
    

    
    public void goBack(ActionEvent event) throws IOException {
        goBackCheck();
    }

    public void goBackCheck() throws IOException {
    	try {
    	Main m = new Main();
        m.setScena("SCENE/SCENA-SCHERMATA-INIZIALE-2.fxml");}
    	catch(Exception e) {
    		gestisciEccezioni(e);
    	}
    }

    
    public void successivo() {
    	try {
    	int grandezza=0;
    	if(segnaTipo==2) {
        	grandezza=carteEquipaggiamento.size();
        }
        else if (segnaTipo==1) {
        	grandezza=cartePersonaggio.size();
        }
        else if(segnaTipo==3) {
        	grandezza=carteCura.size();
        }
        else if(segnaTipo==4) {
        	grandezza=carteEffetto.size();
        }
        else if(segnaTipo==5) {
        	grandezza=carteImprevisto.size();
        }
        else if(segnaTipo==6) {
        	grandezza=carteTerreno.size();
        }
    	indice++;

        if (indice > grandezza) {
            indice = 1;
        }

        // Calcola gli indici della carta corrente, precedente e successiva
        int indicePrecedente = indice - 1;
        int indiceSuccessivo = indice + 1;

        if (indicePrecedente < 1) {
            indicePrecedente = grandezza;
        }
        if (indiceSuccessivo > grandezza) {
            indiceSuccessivo = 1;
        }

        aggiornaImmaginiCarte(indice, indicePrecedente, indiceSuccessivo);

        numCartPrinc.setText(String.valueOf(indice));
        numCartPrec.setText(String.valueOf(indicePrecedente));
        numCartSucc.setText(String.valueOf(indiceSuccessivo));}
		catch(Exception e) {
		gestisciEccezioni(e);
		}
    }
    
    public void precedente() {
    	try {
        int grandezza=0;
    	if(segnaTipo==2) {
        	grandezza=carteEquipaggiamento.size();
        }
        else if(segnaTipo==1){
        	grandezza=cartePersonaggio.size();
        }
    	else if(segnaTipo==3) {
        	grandezza=carteCura.size();
        }
    	else if(segnaTipo==4) {
        	grandezza=carteEffetto.size();
        }
    	else if(segnaTipo==5) {
        	grandezza=carteImprevisto.size();
        }
    	else if(segnaTipo==6) {
        	grandezza=carteTerreno.size();
        }
    	indice--;

        if (indice < 1) {
            indice = grandezza;
        }

        int indicePrecedente = indice - 1;
        int indiceSuccessivo = indice + 1;

        if (indicePrecedente < 1) {
            indicePrecedente = grandezza;
        }
        if (indiceSuccessivo > grandezza) {
            indiceSuccessivo = 1;
        }

        aggiornaImmaginiCarte(indice, indicePrecedente, indiceSuccessivo);

        // Aggiorna i numeri delle carte
        numCartPrinc.setText(String.valueOf(indice));
        numCartPrec.setText(String.valueOf(indicePrecedente));
        numCartSucc.setText(String.valueOf(indiceSuccessivo));}
	catch(Exception e) {
		gestisciEccezioni(e);
	}
    }
    
    private void aggiornaImmaginiCarte(int indiceCorrente, int indicePrecedente, int indiceSuccessivo) {
        try {
    	switch (segnaTipo) {
            case 1: // Carte Personaggio
                aggiornaCartePersonaggio(indiceCorrente, indicePrecedente, indiceSuccessivo);
                break;
            case 2: // Carte Equipaggiamento
                aggiornaCarteEquipaggiamento(indiceCorrente, indicePrecedente, indiceSuccessivo);
                break;
            case 3: // Carte Cura
                aggiornaCarteCura(indiceCorrente, indicePrecedente, indiceSuccessivo);
                break;
            case 4: // Carte Effetto
                aggiornaCarteEffetto(indiceCorrente, indicePrecedente, indiceSuccessivo);
                break;
            case 5: // Carte Imprevisto
                aggiornaCarteImprevisto(indiceCorrente, indicePrecedente, indiceSuccessivo);
                break;
            case 6: // Carte Terreno
                aggiornaCarteTerreno(indiceCorrente, indicePrecedente, indiceSuccessivo);
                break;
            default:
                System.out.println("Tipo di carta non valido");
                break;
        }
        
        aggiornaPopUp();}
		catch(Exception e) {
		gestisciEccezioni(e);
		}
    }

   
    private void aggiornaCartePersonaggio(int indiceCorrente, int indicePrecedente, int indiceSuccessivo) {
        try {
    	CartaPersonaggio cartaCorrente = cartePersonaggio.getOrDefault(indiceCorrente, null);
        CartaPersonaggio cartaPrecedente = cartePersonaggio.getOrDefault(indicePrecedente, null);
        CartaPersonaggio cartaSuccessiva = cartePersonaggio.getOrDefault(indiceSuccessivo, null);

        

        if (cartaCorrente != null) {
            principale.setImage(cartaCorrente.getImmagine());
        }
        
        if (cartePersonaggio.size() > 1) {
        	if (cartaPrecedente != null) {
        		imm1.setImage(cartaPrecedente.getImmagine());
        		numCartPrec.setText(String.valueOf(indicePrecedente));
        	} else {
        		imm1.setImage(null);
        		numCartPrec.setText("");
        	}

        	if (cartaSuccessiva != null) {
        		imm2.setImage(cartaSuccessiva.getImmagine());
        		numCartSucc.setText(String.valueOf(indiceSuccessivo));
        	} else {
        		imm2.setImage(null);
        		numCartSucc.setText("");
        	}    
        } else {
        	imm1.setImage(null);
            numCartPrec.setText(""); 
            imm2.setImage(null);
            numCartSucc.setText(""); 
        }}
		catch(Exception e) {
		gestisciEccezioni(e);
		}
    }

    private void aggiornaCarteEquipaggiamento(int indiceCorrente, int indicePrecedente, int indiceSuccessivo) {
    	try {
    	CartaEquipaggiamento cartaCorrente = carteEquipaggiamento.getOrDefault(indiceCorrente, null);
    	CartaEquipaggiamento cartaPrecedente = carteEquipaggiamento.getOrDefault(indicePrecedente, null);
    	CartaEquipaggiamento cartaSuccessiva = carteEquipaggiamento.getOrDefault(indiceSuccessivo, null);

        if (cartaCorrente != null) {
            principale.setImage(cartaCorrente.getImmagine());
        }
        
        if (carteEquipaggiamento.size() > 1) {
        	if (cartaPrecedente != null) {
        		imm1.setImage(cartaPrecedente.getImmagine());
        		numCartPrec.setText(String.valueOf(indicePrecedente));
        	} else {
        		imm1.setImage(null);
        		numCartPrec.setText("");
        	}

        	if (cartaSuccessiva != null) {
        		imm2.setImage(cartaSuccessiva.getImmagine());
        		numCartSucc.setText(String.valueOf(indiceSuccessivo));
        	} else {
        		imm2.setImage(null);
        		numCartSucc.setText("");
        	}    
        } else {
        	imm1.setImage(null);
            numCartPrec.setText(""); 
            imm2.setImage(null);
            numCartSucc.setText(""); 
        }}
		catch(Exception e) {
		gestisciEccezioni(e);
		}
    }

    private void aggiornaCarteCura(int indiceCorrente, int indicePrecedente, int indiceSuccessivo) {
    	try {
    	CartaCura cartaCorrente = carteCura.getOrDefault(indiceCorrente, null);
    	CartaCura cartaPrecedente = carteCura.getOrDefault(indicePrecedente, null);
    	CartaCura cartaSuccessiva = carteCura.getOrDefault(indiceSuccessivo, null);

        if (cartaCorrente != null) {
            principale.setImage(cartaCorrente.getImmagine());
        }
        
        if (carteCura.size() > 1) {
        	if (cartaPrecedente != null) {
        		imm1.setImage(cartaPrecedente.getImmagine());
        		numCartPrec.setText(String.valueOf(indicePrecedente));
        	} else {
        		imm1.setImage(null);
        		numCartPrec.setText("");
        	}

        	if (cartaSuccessiva != null) {
        		imm2.setImage(cartaSuccessiva.getImmagine());
        		numCartSucc.setText(String.valueOf(indiceSuccessivo));
        	} else {
        		imm2.setImage(null);
        		numCartSucc.setText("");
        	}    
        } else {
        	imm1.setImage(null);
            numCartPrec.setText(""); 
            imm2.setImage(null);
            numCartSucc.setText(""); 
        }}
		catch(Exception e) {
		gestisciEccezioni(e);
		}
    }

    private void aggiornaCarteEffetto(int indiceCorrente, int indicePrecedente, int indiceSuccessivo) {
    	try {
    	CartaEffetto cartaCorrente = carteEffetto.getOrDefault(indiceCorrente, null);
    	CartaEffetto cartaPrecedente = carteEffetto.getOrDefault(indicePrecedente, null);
    	CartaEffetto cartaSuccessiva = carteEffetto.getOrDefault(indiceSuccessivo, null);

        if (cartaCorrente != null) {
            principale.setImage(cartaCorrente.getImmagine());
        }
        
        if (carteEffetto.size() > 1) {
        	if (cartaPrecedente != null) {
        		imm1.setImage(cartaPrecedente.getImmagine());
        		numCartPrec.setText(String.valueOf(indicePrecedente));
        	} else {
        		imm1.setImage(null);
        		numCartPrec.setText("");
        	}

        	if (cartaSuccessiva != null) {
        		imm2.setImage(cartaSuccessiva.getImmagine());
        		numCartSucc.setText(String.valueOf(indiceSuccessivo));
        	} else {
        		imm2.setImage(null);
        		numCartSucc.setText("");
        	}    
        } else {
        	imm1.setImage(null);
            numCartPrec.setText(""); 
            imm2.setImage(null);
            numCartSucc.setText(""); 
        }}
		catch(Exception e) {
		gestisciEccezioni(e);
		}
    }

    private void aggiornaCarteImprevisto(int indiceCorrente, int indicePrecedente, int indiceSuccessivo) {
    	try {
    	CartaImprevisto cartaCorrente = carteImprevisto.getOrDefault(indiceCorrente, null);
    	CartaImprevisto cartaPrecedente = carteImprevisto.getOrDefault(indicePrecedente, null);
    	CartaImprevisto cartaSuccessiva = carteImprevisto.getOrDefault(indiceSuccessivo, null);

        if (cartaCorrente != null) {
            principale.setImage(cartaCorrente.getImmagine());
        }
        
        if (carteImprevisto.size() > 1) {
        	if (cartaPrecedente != null) {
        		imm1.setImage(cartaPrecedente.getImmagine());
        		numCartPrec.setText(String.valueOf(indicePrecedente));
        	} else {
        		imm1.setImage(null);
        		numCartPrec.setText("");
        	}

        	if (cartaSuccessiva != null) {
        		imm2.setImage(cartaSuccessiva.getImmagine());
        		numCartSucc.setText(String.valueOf(indiceSuccessivo));
        	} else {
        		imm2.setImage(null);
        		numCartSucc.setText("");
        	}    
        } else {
        	imm1.setImage(null);
            numCartPrec.setText(""); 
            imm2.setImage(null);
            numCartSucc.setText(""); 
        }}
    	catch(Exception e) {
    		gestisciEccezioni(e);
    	}
    }

    private void aggiornaCarteTerreno(int indiceCorrente, int indicePrecedente, int indiceSuccessivo) {
    	try {
    	CartaTerreno cartaCorrente = carteTerreno.getOrDefault(indiceCorrente, null);
    	CartaTerreno cartaPrecedente = carteTerreno.getOrDefault(indicePrecedente, null);
    	CartaTerreno cartaSuccessiva = carteTerreno.getOrDefault(indiceSuccessivo, null);

        if (cartaCorrente != null) {
            principale.setImage(cartaCorrente.getImmagine());
        }
        
        if (carteTerreno.size() > 1) {
        	if (cartaPrecedente != null) {
        		imm1.setImage(cartaPrecedente.getImmagine());
        		numCartPrec.setText(String.valueOf(indicePrecedente));
        	} else {
        		imm1.setImage(null);
        		numCartPrec.setText("");
        	}

        	if (cartaSuccessiva != null) {
        		imm2.setImage(cartaSuccessiva.getImmagine());
        		numCartSucc.setText(String.valueOf(indiceSuccessivo));
        	} else {
        		imm2.setImage(null);
        		numCartSucc.setText("");
        	}    
        } else {
        	imm1.setImage(null);
            numCartPrec.setText(""); 
            imm2.setImage(null);
            numCartSucc.setText(""); 
        }}
		catch(Exception e) {
		gestisciEccezioni(e);
		}
    }
    
    public void aggiornaPopUp() {
    	try {
    	if(segnaTipo==2) {
    		CartaEquipaggiamento temp=carteEquipaggiamento.get(indice);
        	String nome="Nome:\n"+temp.getNome();
        	String effetto="Effetto:\n" +temp.getEffetto();
        	String rarita="Rarità:\n"+ temp.getRaritaString();
        	String mitologia="Mitologia:\n"+temp.getMitologiaString();
        	Image immagine=temp.getImmagine();
        	immagineCarta.setLayoutX(0); 
            immagineCarta.setLayoutY(0);
        	nomeCarta.setText(nome);
        	mitologiaCarta.setText(mitologia);
        	immagineCarta.setImage(immagine);
        	descrizioneCarta.setText(effetto);
        	raritaCarta.setText(rarita);
        	attCarta.setVisible(false);
            defCarta.setVisible(false);
            VBox scritteContainer = new VBox(30); 
            scritteContainer.getChildren().addAll(nomeCarta, mitologiaCarta, descrizioneCarta, raritaCarta);
            scritteContainer.setAlignment(Pos.CENTER);
            scritteContainer.setTranslateX(280); 
            scritteContainer.setTranslateY(50); 
            popUp.getChildren().add(scritteContainer);
    	}
    	else if(segnaTipo==1){
    		CartaPersonaggio temp=cartePersonaggio.get(indice);
    		if(temp!=null) {
	    		String nome="Nome:\n" +temp.getNome();
	    		String descrizione="Descrizione:\n" +temp.getDescrizione();
	    		String attString="Attacco:\n"+temp.getAttString();
	    		String rarita="Rarità:\n"+ temp.getRaritaString();
	    		String defString="Difesa:\n"+temp.getDefString();
	    		String mitologia="Mitologia:\n"+temp.getTipoString();
	    		Image immagine=temp.getImmagine();
	    		immagineCarta.setLayoutX(0); 
	            immagineCarta.setLayoutY(0);
	    		nomeCarta.setText(nome);
	    		mitologiaCarta.setText(mitologia);
	    		immagineCarta.setImage(immagine);
	    		descrizioneCarta.setText(descrizione);
	    		raritaCarta.setText(rarita);
	    		attCarta.setVisible(true);
	            defCarta.setVisible(true);
	    		attCarta.setText(attString);
	    	    defCarta.setText(defString);
	    	    HBox scritteAttDef = new HBox(30); 
	            scritteAttDef.getChildren().addAll(attCarta, defCarta);
	            scritteAttDef.setAlignment(Pos.CENTER);
	    	    VBox scritteContainer = new VBox(30); 
	            scritteContainer.getChildren().addAll(nomeCarta, mitologiaCarta, descrizioneCarta, raritaCarta, scritteAttDef);
	            scritteContainer.setAlignment(Pos.CENTER);
	            scritteContainer.setTranslateX(280); 
	            scritteContainer.setTranslateY(30); 
	            popUp.getChildren().add(scritteContainer);
    		}
    	}
    	else if(segnaTipo==3) {
    		CartaCura temp=carteCura.get(indice);
        	String nome="Nome:\n"+temp.getNome();
        	String effetto="Effetto:\n" +temp.getEffetto();
        	String rarita="Rarità:\n"+ temp.getRaritaString();
        	String mitologia="Mitologia:\n"+temp.getMitologiaString();
        	Image immagine=temp.getImmagine();
        	immagineCarta.setLayoutX(0); 
            immagineCarta.setLayoutY(0);
        	nomeCarta.setText(nome);
        	mitologiaCarta.setText(mitologia);
        	immagineCarta.setImage(immagine);
        	descrizioneCarta.setText(effetto);
        	raritaCarta.setText(rarita);
        	attCarta.setVisible(false);
            defCarta.setVisible(false);
            VBox scritteContainer = new VBox(30); 
            scritteContainer.getChildren().addAll(nomeCarta, mitologiaCarta, descrizioneCarta, raritaCarta, attCarta, defCarta);
            scritteContainer.setAlignment(Pos.CENTER);
            scritteContainer.setTranslateX(280); 
            scritteContainer.setTranslateY(50); 
            popUp.getChildren().add(scritteContainer);
    	}
    	else if(segnaTipo==4) {
    		CartaEffetto temp=carteEffetto.get(indice);
        	String nome="Nome:\n"+temp.getNome();
        	String effetto="Effetto:\n" +temp.getEffetto();
        	String rarita="Rarità:\n"+ temp.getRaritaString();
        	String mitologia="Mitologia:\n"+temp.getMitologiaString();
        	Image immagine=temp.getImmagine();
        	immagineCarta.setLayoutX(0); 
            immagineCarta.setLayoutY(0);
        	nomeCarta.setText(nome);
        	mitologiaCarta.setText(mitologia);
        	immagineCarta.setImage(immagine);
        	descrizioneCarta.setText(effetto);
        	raritaCarta.setText(rarita);
        	attCarta.setVisible(false);
            defCarta.setVisible(false);
            VBox scritteContainer = new VBox(30); 
            scritteContainer.getChildren().addAll(nomeCarta, mitologiaCarta, descrizioneCarta, raritaCarta);
            scritteContainer.setAlignment(Pos.CENTER);
            scritteContainer.setTranslateX(280); 
            scritteContainer.setTranslateY(50); 
            popUp.getChildren().add(scritteContainer);
    	}
    	else if(segnaTipo==5) {
    		CartaImprevisto temp=carteImprevisto.get(indice);
        	String nome="Nome:\n"+temp.getNome();
        	String effetto="Effetto:\n" +temp.getEffetto();
        	String rarita="Rarità:\n"+ temp.getRaritaString();
        	String mitologia="Mitologia:\n"+temp.getMitologiaString();
        	Image immagine=temp.getImmagine();
        	immagineCarta.setLayoutX(0); 
            immagineCarta.setLayoutY(0);
        	nomeCarta.setText(nome);
        	mitologiaCarta.setText(mitologia);
        	immagineCarta.setImage(immagine);
        	descrizioneCarta.setText(effetto);
        	raritaCarta.setText(rarita);
        	attCarta.setVisible(false);
            defCarta.setVisible(false);
            VBox scritteContainer = new VBox(30); 
            scritteContainer.getChildren().addAll(nomeCarta, mitologiaCarta, descrizioneCarta, raritaCarta);
            scritteContainer.setAlignment(Pos.CENTER);
            scritteContainer.setTranslateX(280); 
            scritteContainer.setTranslateY(50); 
            popUp.getChildren().add(scritteContainer);
    	}
    	else if(segnaTipo==6) {
    		CartaTerreno temp=carteTerreno.get(indice);
        	String nome="Nome:\n"+temp.getNome();
        	String effetto="Effetto:\n" +temp.getEffetto();
        	String rarita="Rarità:\n"+ temp.getRaritaString();
        	String mitologia="Mitologia:\n"+temp.getMitologiaString();
        	Image immagine=temp.getImmagine();
        	nomeCarta.setText(nome);
        	mitologiaCarta.setText(mitologia);
        	immagineCarta.setImage(immagine);
        	descrizioneCarta.setText(effetto);
        	raritaCarta.setText(rarita);
        	attCarta.setVisible(false);
            defCarta.setVisible(false);
            VBox scritteContainer = new VBox(30); 
            scritteContainer.getChildren().addAll(nomeCarta, mitologiaCarta, descrizioneCarta, raritaCarta);
            scritteContainer.setAlignment(Pos.CENTER);
            scritteContainer.setTranslateX(280); 
            scritteContainer.setTranslateY(50); 
            popUp.getChildren().add(scritteContainer);
    	}
    	popUp.setStyle("-fx-background-color: #4682B4;");}
    	catch(Exception e) {
    		gestisciEccezioni(e);
    	}
    }
    
    
    public void onEnterPopUp(MouseEvent event) {
    	try {
    	aggiornaPopUp();
    	popUp.setVisible(true);
    	popUp.setMouseTransparent(true);}
    	catch(Exception e) {
    		gestisciEccezioni(e);
    	}
    }
    
    public void onExitPopUp(MouseEvent event) {
    	try {
    	popUp.setVisible(false);
    	popUp.setMouseTransparent(false);}
    	catch(Exception e) {
    		gestisciEccezioni(e);
    	}
    }
    
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
    	try {
    		//System.out.println(cartePersonaggio);
    		popUp.setPrefHeight(430);
    		popUp.setLayoutY(100);
    		checkBoxList.add(egiziaMitologia);
    		checkBoxList.add(grecaMitologia);
    		checkBoxList.add(norrenaMitologia);
    		checkBoxList.add(romanaMitologia);
    		if(segnaTipo==2) {
    			if(carteEquipaggiamento.isEmpty()) {
    				ArrayList<CartaEquipaggiamento> carteCaricate = JDBCCaricamento.getCarteEquipaggiamento();

    				for (int i = 0; i < carteCaricate.size(); i++) {
    					CartaEquipaggiamento carta = carteCaricate.get(i);
    					carteEquipaggiamento.put(i + 1, carta);
    				}
    				System.out.println("Carte equipaggiamento visualizzate con successo");
    				numCartPrinc.setText("1");
    				numCartPrec.setText(carteEquipaggiamento.size()+"");
    				numCartSucc.setText("2");
    				aggiornaImmaginiCarte(indice, carteEquipaggiamento.size(), indice + 1);
    			} else {
    				numCartPrinc.setText("1");
                    numCartPrec.setText(carteEquipaggiamento.size()+"");
                    numCartSucc.setText("2");
                    aggiornaImmaginiCarte(indice, carteEquipaggiamento.size(), indice + 1);

    			}
    		}
    		else if(segnaTipo==1){
    			if(cartePersonaggio.isEmpty()) {
    				ArrayList<CartaPersonaggio> carteCaricate = JDBCCaricamento.getCartePersonaggio();
    				for (int i = 0; i < carteCaricate.size(); i++) {
    					CartaPersonaggio carta = carteCaricate.get(i);
    					cartePersonaggio.put(i + 1, carta);
    				}
    				System.out.println("Carte personaggio visualizzate con successo");
    				numCartPrinc.setText("1");
    				numCartPrec.setText(cartePersonaggio.size()+"");
    				numCartSucc.setText("2");
    				aggiornaImmaginiCarte(indice, cartePersonaggio.size(), indice + 1);
    			}
    			else {
    				numCartPrinc.setText("1");
                    numCartPrec.setText(cartePersonaggio.size()+"");
                    numCartSucc.setText("2");
                    aggiornaImmaginiCarte(indice, cartePersonaggio.size(), indice + 1);
    				}
    				
    			}
    		else if(segnaTipo==3) {
    			if (carteCura.isEmpty()) {
	    			ArrayList<CartaCura> carteCaricate= JDBCCaricamento.getCarteCura();
	    			 for (int i = 0; i < carteCaricate.size(); i++) {
	                     CartaCura carta = carteCaricate.get(i);
	                     carteCura.put(i + 1, carta);
	                 }
	                 System.out.println("Carte cura visualizzate con successo");
	                 numCartPrinc.setText("1");
	                 numCartPrec.setText(carteCura.size()+"");
	                 numCartSucc.setText("2");
	                 aggiornaImmaginiCarte(indice, carteCura.size(), indice + 1);
	    		}
    			else {
    				numCartPrinc.setText("1");
                    numCartPrec.setText(carteCura.size()+"");
                    numCartSucc.setText("2");
                    aggiornaImmaginiCarte(indice, carteCura.size(), indice + 1);
    			}
    		}
    		else if(segnaTipo==4) {
    			if (carteEffetto.isEmpty()) {
	    			ArrayList<CartaEffetto> carteCaricate= JDBCCaricamento.getCarteEffetto();
	    			 for (int i = 0; i < carteCaricate.size(); i++) {
	                     CartaEffetto carta = carteCaricate.get(i);
	                     //System.out.println(carta.toString()+"\n");
	                     carteEffetto.put(i + 1, carta);
	                 }
	                 System.out.println("Carte effetto visualizzate con successo");
	                 numCartPrinc.setText("1");
	                 numCartPrec.setText(carteEffetto.size()+"");
	                 numCartSucc.setText("2");
	                 aggiornaImmaginiCarte(indice, carteEffetto.size(), indice + 1);
    			}
    			else {
    				numCartPrinc.setText("1");
                    numCartPrec.setText(carteEffetto.size()+"");
                    numCartSucc.setText("2");
                    aggiornaImmaginiCarte(indice, carteEffetto.size(), indice + 1);
    			}
    		}
    		else if(segnaTipo==5) {
    			if (carteImprevisto.isEmpty()) {
	    			ArrayList<CartaImprevisto> carteCaricate= JDBCCaricamento.getCarteImprevisto();
	    			 for (int i = 0; i < carteCaricate.size(); i++) {
	                     CartaImprevisto carta = carteCaricate.get(i);
	                     //System.out.println(carta.toString()+"\n");
	                     carteImprevisto.put(i + 1, carta);
	                 }
	                 System.out.println("Carte imprevisto visualizzate con successo");
	                 numCartPrinc.setText("1");
	                 numCartPrec.setText(carteImprevisto.size()+"");
	                 numCartSucc.setText("2");
	                 aggiornaImmaginiCarte(indice, carteImprevisto.size(), indice + 1);
    			}
    			else {
    				numCartPrinc.setText("1");
                    numCartPrec.setText(carteImprevisto.size()+"");
                    numCartSucc.setText("2");
                    aggiornaImmaginiCarte(indice, carteImprevisto.size(), indice + 1);
    			}
    		}
    		else if(segnaTipo==6) {
    			if (carteTerreno.isEmpty()) {
	    			ArrayList<CartaTerreno> carteCaricate= JDBCCaricamento.getCarteTerreno();
	    			 for (int i = 0; i < carteCaricate.size(); i++) {
	                     CartaTerreno carta = carteCaricate.get(i);
	                     carteTerreno.put(i + 1, carta);
	                 }
	                 System.out.println("Carte terreno visualizzate con successo");
	                 numCartPrinc.setText("1");
	                 numCartPrec.setText(carteTerreno.size()+"");
	                 numCartSucc.setText("2");
	                 aggiornaImmaginiCarte(indice, carteTerreno.size(), indice + 1);
    			}
    			else {
    				numCartPrinc.setText("1");
                    numCartPrec.setText(carteTerreno.size()+"");
                    numCartSucc.setText("2");
                    aggiornaImmaginiCarte(indice, carteTerreno.size(), indice + 1);
    			}
    			
    			
    		}		
        } catch (Exception e) {
        	gestisciEccezioni(e);
        }
    }
    
    public void onEgiziaPersonaggioChanged() {
        gestisciAzioneCasellaControllo(egiziaMitologia, "Egizia");
    	initialize(null, null);
    }
    
    public void onGrecaPersonaggioChanged() {
        gestisciAzioneCasellaControllo(grecaMitologia, "Greca");
    	initialize(null, null);
    }
    
    public void onNorrenaPersonaggioChanged() {
        gestisciAzioneCasellaControllo(norrenaMitologia, "Norrena");
    	initialize(null, null);
    }
    
    public void onRomanaPersonaggioChanged() {
        gestisciAzioneCasellaControllo(romanaMitologia, "Romana");
    	initialize(null, null);
    }

    public void gestisciAzioneCasellaControllo(CheckBox checkBox, String nomeCasella) {
    	try {
    	if (checkBox.isSelected()) {
		            for (CheckBox otherCheckBox : checkBoxList) {
		                if (otherCheckBox != checkBox) {
		                    otherCheckBox.setSelected(false);
		                }
		            }
		             if(segnaTipo==1) {
		            	 cartePersonaggio.clear();
		            	 ArrayList<CartaPersonaggio> mit=JDBCCartePersonaggio.getCarteByMitologia(nomeCasella);
		            	 for(int i=0; i<mit.size(); i++) {
		            		 CartaPersonaggio temp=mit.get(i);
		            		 cartePersonaggio.put(i+1, temp);
		            	 }
		             }
		             else if(segnaTipo==2) {
		            	 carteEquipaggiamento.clear();
		            	 ArrayList<CartaEquipaggiamento> mit=JDBCCarteEquipaggiamento.getCarteEquipaggiamentoByMitologia(nomeCasella);
		            	 for(int i=0; i<mit.size(); i++) {
		            		 CartaEquipaggiamento temp=mit.get(i);
		            		 carteEquipaggiamento.put(i+1, temp);
		            	 }
		             }
		             else if(segnaTipo==3) {
		            	 carteCura.clear();
		            	 ArrayList<CartaCura> mit=JDBCCarteCura.getCarteCuraByMitologia(nomeCasella);
		            	 for(int i=0; i<mit.size(); i++) {
		            		 CartaCura temp=mit.get(i);
		            		 carteCura.put(i+1, temp);
		            	 }
		             }
		             else if(segnaTipo==4) {
		            	 carteEffetto.clear();
		            	 ArrayList<CartaEffetto> mit=JDBCCarteEffetto.getCarteEffettoByMitologia(nomeCasella);
		            	 for(int i=0; i<mit.size(); i++) {
		            		 CartaEffetto temp=mit.get(i);
		            		 carteEffetto.put(i+1, temp);
		            	 }
		             }
		             else if(segnaTipo==5) {
		            	 carteImprevisto.clear();
		            	 ArrayList<CartaImprevisto> mit=JDBCCarteImprevisto.getCarteImprevistoByMitologia(nomeCasella);
		            	 for(int i=0; i<mit.size(); i++) {
		            		 CartaImprevisto temp=mit.get(i);
		            		 carteImprevisto.put(i+1, temp);
		            	 }
		             }
		             else if(segnaTipo==6) {
		            	 carteTerreno.clear();
		            	 ArrayList<CartaTerreno> mit=JDBCCarteTerreno.getCarteTerrenoByTipologia(nomeCasella);
	        				//System.out.println(mit);
		            	 for(int i=0; i<mit.size(); i++) {
		            		 CartaTerreno temp=mit.get(i);
		            		 carteTerreno.put(i+1, temp);
		            	 }
		             }
		             
        } else {
        			if(segnaTipo==1) {
        				cartePersonaggio.clear();
        				ArrayList<CartaPersonaggio> carte=JDBCCaricamento.getCartePersonaggio();
        				for (int i=0; i<carte.size(); i++) {
        					CartaPersonaggio temp=carte.get(i);
        					cartePersonaggio.put(i+1,temp);
        				}
        			}
        			else if(segnaTipo==2) {
        				carteEquipaggiamento.clear();
        				ArrayList<CartaEquipaggiamento> carte=JDBCCaricamento.getCarteEquipaggiamento();
        				for (int i=0; i<carte.size(); i++) {
        					CartaEquipaggiamento temp=carte.get(i);
        					carteEquipaggiamento.put(i+1,temp);
        				}
        			}
        			else if(segnaTipo==3) {
        				carteCura.clear();
        				ArrayList<CartaCura> carte=JDBCCaricamento.getCarteCura();
        				for (int i=0; i<carte.size(); i++) {
        					CartaCura temp=carte.get(i);
        					carteCura.put(i+1,temp);
        				}
        			}
        			else if(segnaTipo==4) {
        				carteEffetto.clear();
        				ArrayList<CartaEffetto> carte=JDBCCaricamento.getCarteEffetto();
        				for (int i=0; i<carte.size(); i++) {
        					CartaEffetto temp=carte.get(i);
        					carteEffetto.put(i+1,temp);
        				}
        			}
        			else if(segnaTipo==5) {
        				carteImprevisto.clear();
        				ArrayList<CartaImprevisto> carte=JDBCCaricamento.getCarteImprevisto();
        				for (int i=0; i<carte.size(); i++) {
        					CartaImprevisto temp=carte.get(i);
        					carteImprevisto.put(i+1,temp);
        				}
        			}
        			else if(segnaTipo==6) {
        				carteTerreno.clear();
        				ArrayList<CartaTerreno> carte=JDBCCaricamento.getCarteTerreno();
        				for (int i=0; i<carte.size(); i++) {
        					CartaTerreno temp=carte.get(i);
        					carteTerreno.put(i+1,temp);
        				}
        			}
        }}
    	catch(Exception e) {
    		gestisciEccezioni(e);
    	}
    }
		
    public void cercaPerNome() {
    	try {
    	String ricerca = cercaNome.getText().toLowerCase();

        cartePersonaggio.clear();
        carteEquipaggiamento.clear();
        carteCura.clear();
        carteEffetto.clear();
        carteImprevisto.clear();
        carteTerreno.clear();
        
        indice = 1;

        for (CartaPersonaggio carta : JDBCCaricamento.getCartePersonaggio()) {
            String nomeCarta = carta.getNome().toLowerCase();
            if (nomeCarta.contains(ricerca)) {
                cartePersonaggio.put(cartePersonaggio.size() + 1, carta);
            }
        }
        
        for (CartaEquipaggiamento carta : JDBCCaricamento.getCarteEquipaggiamento()) {
            String nomeCarta = carta.getNome().toLowerCase();
            if (nomeCarta.contains(ricerca)) {
                carteEquipaggiamento.put(carteEquipaggiamento.size() + 1, carta);
            }
        }
        
        for (CartaCura carta : JDBCCaricamento.getCarteCura()) {
            String nomeCarta = carta.getNome().toLowerCase();
            if (nomeCarta.contains(ricerca)) {
                carteCura.put(carteCura.size() + 1, carta);
            }
        }
        
        for (CartaEffetto carta : JDBCCaricamento.getCarteEffetto()) {
            String nomeCarta = carta.getNome().toLowerCase();
            if (nomeCarta.contains(ricerca)) {
                carteEffetto.put(carteEffetto.size() + 1, carta);
            }
        }
        
        for (CartaImprevisto carta : JDBCCaricamento.getCarteImprevisto()) {
            String nomeCarta = carta.getNome().toLowerCase();
            if (nomeCarta.contains(ricerca)) {
                carteImprevisto.put(carteImprevisto.size() + 1, carta);
            }
        }
        
        for (CartaTerreno carta : JDBCCaricamento.getCarteTerreno()) {
            String nomeCarta = carta.getNome().toLowerCase();
            if (nomeCarta.contains(ricerca)) {
                carteTerreno.put(carteTerreno.size() + 1, carta);
            }
        }

        initialize(null, null);
    }
	catch(Exception e) {
		gestisciEccezioni(e);
	 }
    }

    public void bottoneCarteEquip() {
    	segnaTipo=2;
        indice=1;
        carteEquipaggiamento.clear();
        for (CheckBox otherCheckBox : checkBoxList) {
                otherCheckBox.setSelected(false);}
        
    	initialize(null, null);
    }
    
    public void bottoneCartePers() {
    	segnaTipo=1;
        indice=1;
        cartePersonaggio.clear();
        for (CheckBox otherCheckBox : checkBoxList) {
            otherCheckBox.setSelected(false);}
    	initialize(null, null);
    }
    
    public void bottoneCarteCura() {
    	segnaTipo=3;
        indice=1;
        carteCura.clear();
        for (CheckBox otherCheckBox : checkBoxList) {
            otherCheckBox.setSelected(false);}
    	initialize(null,null);
    }
    
    public void bottoneCarteEffetto() {
    	segnaTipo=4;
        indice=1;
        carteEffetto.clear();
        for (CheckBox otherCheckBox : checkBoxList) {
            otherCheckBox.setSelected(false);}
    	initialize(null,null);
    }
    
    public void bottoneCarteImprevisto() {
    	segnaTipo=5;
        indice=1;
        carteImprevisto.clear();
        for (CheckBox otherCheckBox : checkBoxList) {
            otherCheckBox.setSelected(false);}
    	initialize(null,null);
    }
    
    public void bottoneCarteTerreno() {
    	segnaTipo=6;
        indice=1;
        carteTerreno.clear();
        for (CheckBox otherCheckBox : checkBoxList) {
            otherCheckBox.setSelected(false);}
    	initialize(null,null);
    }
    
    
    private void gestisciEccezioni(Exception e) {
        if (e instanceof IOException) {
            System.out.println("Si è verificata un'eccezione di I/O: " + e.getMessage());
        } else if (e instanceof NullPointerException) {
            System.out.println("Si è verificata un'eccezione di puntatore nullo: " + e.getMessage());
        } else if (e instanceof IndexOutOfBoundsException) {
            System.out.println("Si è verificata un'eccezione di indice fuori dal limite: " + e.getMessage());
        } else if (e instanceof NumberFormatException) {
            System.out.println("Si è verificata un'eccezione di formato numerico non valido: " + e.getMessage());
        } else if (e instanceof IllegalArgumentException) {
            System.out.println("Si è verificata un'eccezione di argomento non valido: " + e.getMessage());
        } else if (e instanceof ClassCastException) {
            System.out.println("Si è verificata un'eccezione di cast non valido: " + e.getMessage());
        } else if (e instanceof ArithmeticException) {
            System.out.println("Si è verificata un'eccezione di aritmetica: " + e.getMessage());
        } else {
            System.out.println("Si è verificata un'eccezione generica: " + e.getMessage());
        }
        e.printStackTrace();
    }

    
}

    
