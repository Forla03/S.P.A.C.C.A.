package application.CONTROLLER;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import application.CLASSI.Utente;
import application.CLASSI.JDBCPartite;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class ControllerScenaSceltaPersonaggi2Giocatori implements Initializable{

    @FXML
    private Text textgiocatore1;

    @FXML
    private Text textgiocatore2;

    @FXML
    private PasswordField password1;

    @FXML
    private PasswordField password2;

    @FXML
    private Button bottoneAddGioc1;

    @FXML
    private Button bottoneAddGioc2;

    @FXML
    private TextField pronto1;

    @FXML
    private TextField pronto2;

    @FXML
    private Button bottoneAvanzamento;

    @FXML
    private Text textgiocatore3;

    @FXML
    private Text textgiocatore4;

    @FXML
    private Button bottoneAddGioc3;

    @FXML
    private Button bottoneAddGioc4;

    @FXML
    private TextField pronto3;

    @FXML
    private TextField pronto4;

    @FXML
    private Pane contenitore1;

    @FXML
    private Pane contenitore2;

    @FXML
    private Pane contenitore3;

    @FXML
    private Pane contenitore4;

    private static List<Utente> utentiOgg = new ArrayList<Utente>();

    private ArrayList<String> utentiStringa = new ArrayList<String>();

    public static List<Utente> getUtentiOgg() {
        return utentiOgg;
    }

    public void goBack(ActionEvent event) {
        try {
            Main m = new Main();
            m.setScena("SCENE/SCENA-SCHERMATA-PLAYBUTTON.fxml");
        } catch (IOException e) {
            gestisciEccezioni(e);
        }
    }

    public void bottoneAggiungiGioc1() {
        try {
            if (!utentiOgg.isEmpty()) {
                Utente giocatore1 = utentiOgg.get(0);
                textgiocatore1.setText(giocatore1.getNome());
                pronto1.setVisible(true);
                bottoneAvanzamentoVisibility();
            } else {
                System.out.println("La lista utentiOgg è vuota.");
            }
        } catch (Exception e) {
            gestisciEccezioni(e);
        }
    }

    public void bottoneAggiungiGioc2() {
        try {
            if (!utentiOgg.isEmpty()) {
                Utente giocatore2 = utentiOgg.get(1);
                textgiocatore2.setText(giocatore2.getNome());
                pronto2.setVisible(true);
                bottoneAvanzamentoVisibility();
            } else {
                System.out.println("La lista utentiOgg è vuota.");
            }
        } catch (Exception e) {
            gestisciEccezioni(e);
        }
    }

    public void bottoneAggiungiGioc3(){
        try {
            if (!utentiOgg.isEmpty()) {
                Utente giocatore3 = utentiOgg.get(2);
                textgiocatore3.setText(giocatore3.getNome());
                pronto3.setVisible(true);
                bottoneAvanzamentoVisibility();
            } else {
                System.out.println("La lista utentiOgg è vuota.");
            }
        } catch (Exception e) {
            gestisciEccezioni(e);
        }
    }

    public void bottoneAggiungiGioc4(){
        try {
            if (!utentiOgg.isEmpty()) {
                Utente giocatore4 = utentiOgg.get(3);
                textgiocatore4.setText(giocatore4.getNome());
                pronto4.setVisible(true);
                bottoneAvanzamentoVisibility();
            } else {
                System.out.println("La lista utentiOgg è vuota.");
            }
        } catch (Exception e) {
            gestisciEccezioni(e);
        }
    }

    public void bottoneAvanzamentoVisibility() {
        try {
            switch(utentiOgg.size()) {

                case 2:
                    if(pronto1.isVisible()&&pronto2.isVisible())
                        bottoneAvanzamento.setVisible(true);

                case 3:
                    if(pronto1.isVisible()&&pronto2.isVisible()&&pronto3.isVisible())
                        bottoneAvanzamento.setVisible(true);

                case 4:
                    if(pronto1.isVisible()&&pronto2.isVisible()&&pronto3.isVisible()&&pronto4.isVisible())
                        bottoneAvanzamento.setVisible(true);

            }
        } catch (Exception e) {
            gestisciEccezioni(e);
        }
    }

    public void goNext() {
        try {
            Main m=new Main();
            m.setScena("SCENE/SCENA-SCHERMATA-SCELTA3PERSONAGGI.fxml");
        } catch (IOException e) {
            gestisciEccezioni(e);
        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        try {
    	utentiStringa = JDBCPartite.getUtentiDaPartita(ControllerScenaPlaybutton.getUltimoCodice());
		utentiOgg = ControllerCreazionePartita.p.getListaUtenti();
		System.out.println(utentiStringa);
		for (int i = 0; i < utentiOgg.size(); i++) {
		    System.out.println("aaa" + utentiOgg.get(i).getNome());
		}
		if (utentiOgg.size() > 2) {
		    textgiocatore3.setVisible(true);
		    bottoneAddGioc3.setVisible(true);
		}
		if (utentiOgg.size() > 3) {
		    textgiocatore3.setVisible(true);
		    bottoneAddGioc3.setVisible(true);
		    textgiocatore4.setVisible(true);
		    bottoneAddGioc4.setVisible(true);
		}
		System.out.println(utentiOgg.size());}
        catch(Exception e) {
        	gestisciEccezioni(e);
        }
		
    }

    private void gestisciEccezioni(Exception e) {
        if (e instanceof IOException) {
            // Gestione dell'IOException
            System.out.println("Si è verificato un errore di I/O: " + e.getMessage());
        } else if (e instanceof SQLException) {
            // Gestione dell'SQLException
            System.out.println("Si è verificato un errore SQL: " + e.getMessage());
        } else if (e instanceof ClassNotFoundException) {
            // Gestione della ClassNotFoundException
            System.out.println("Classe non trovata: " + e.getMessage());
        } else if (e instanceof IllegalArgumentException) {
            // Gestione dell'IllegalArgumentException
            System.out.println("Argomento illegale: " + e.getMessage());
        } else if (e instanceof IllegalStateException) {
            // Gestione dell'IllegalStateException
            System.out.println("Stato illegale: " + e.getMessage());
        } else {
            // Gestione generica per altre eccezioni non specificate
            System.out.println("Si è verificato un errore: " + e.getClass().getName() + ": " + e.getMessage());
        }
    }

}

