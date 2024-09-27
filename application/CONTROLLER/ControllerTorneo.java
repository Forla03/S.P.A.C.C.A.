package application.CONTROLLER;

import java.awt.AWTException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import application.Main;
import application.CLASSI.JDBCPartite;
import application.CLASSI.JDBCTornei;
import application.CLASSI.JDBCUtenti;
import application.CLASSI.Partita;
import application.CLASSI.Utente;

public class ControllerTorneo implements Initializable {

    @FXML
    private AnchorPane root;

    @FXML
    private Label labelVincitore;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        try {
            // Codice iniziale
            ArrayList<Utente> giocatori = ControllerCreazionePartita.t.getGiocatori();
            
            if (ControllerCreazionePartita.t.getVincitori().size() * 2 == giocatori.size()) {
                giocatori.clear();
                giocatori.addAll(ControllerCreazionePartita.t.getVincitori());
                ControllerCreazionePartita.t.getVincitori().clear();	 	       
                ControllerCreazionePartita.t.getPartite().clear();
                ControllerCreazionePartita.t.nuovoTurno();   
            }
            
            if(giocatori.size() == 1) {
                giocatori.get(0).addPunti(50);
                JDBCUtenti.aggiornaPunti(giocatori.get(0).getNome(), giocatori.get(0).getPunteggio());
                labelVincitore.setText(giocatori.get(0).getNome() + " complimenti per la vittoria del torneo!");
            } else {
                int numPartite = giocatori.size() / 2;
                int numRighe = (int) Math.sqrt(numPartite);
                int numColonne = numPartite / numRighe;
                int xOffset = 300;
                int xMargin = -25;

                for (int i = 0; i < numPartite; i++) {   
                    double xPosition = (root.getPrefWidth() / 2) - ((numColonne * 200 + (numColonne - 1) * xOffset) / 2) + xMargin;
                    Pane pane = new Pane(); 
                    pane.setLayoutX(xPosition + (i % numColonne) * (200 + xOffset));
                    pane.setLayoutY((i / numColonne) * 100 + 200);

                    Label label = new Label(giocatori.get(i*2).getNome() + " vs " + giocatori.get(i*2+1).getNome());
                    label.setAlignment(Pos.CENTER);
                    label.setLayoutX(0);
                    label.setLayoutY(0);
                    pane.getChildren().add(label);

                    Button button = new Button("Inizia partita");
                    button.setOnAction(arg01 -> {
                        try {
                            onButtonClick(arg01);
                        } catch (IOException e) {                    
                            e.printStackTrace();
                        }
                    });
                    button.setAlignment(Pos.CENTER);
                    button.setLayoutY(label.getHeight()+50);
                    pane.getChildren().add(button);

                    if(ControllerCreazionePartita.t.getVincitori().contains((giocatori.get(i*2))) || ControllerCreazionePartita.t.getVincitori().contains((giocatori.get(i*2+1)))) {
                        button.setVisible(false);
                    }

                    root.getChildren().add(pane);
                }
            }
            fixVisibilityBottoni(); 
        } catch (Exception e) {
            gestisciEccezione(e);
        }
    }
    

    public void fixVisibilityBottoni() {
        try {
            String s="";
            for(Node n:root.getChildren()) {
                if(n instanceof Pane) {
                    for(Node n2:((Pane) n).getChildren()) {
                        if(n2 instanceof Label)
                            s=((Label) n2).getText();
                        if(n2 instanceof Button) {
                            String[] temp=s.split(" vs ");
                            for(Utente u:ControllerCreazionePartita.t.getVincitori()) {
                                if(temp[0].equals(u.getNome()) || temp[1].equals(u.getNome()))
                                    n2.setVisible(false);
                            }
                        }
                    }
                }
                s="";
            }
        } catch (Exception e) {
            gestisciEccezione(e);
        }
    }

    

    

    public void salvaTorneo() {
        try {
            JDBCTornei.aggiornaTorneo(ControllerCreazionePartita.t);
            for (Partita x : ControllerCreazionePartita.t.getPartite()) {
                String codice = x.getCodice();
                int numGiocatori = x.getNumGioc();
                String nomiGiocatori = "";
                for (Utente u : x.getListaUtenti()) {
                    nomiGiocatori += u.getNome() + " ";
                }
                String mazzo = x.getMazzoString();
                int turnoGioc = x.getTurnoGioc();
                int turnoTot = x.getTurnoTot();
                String timeline = "";
                String carteManoGioc1 = x.getCarteManoGioc1String(x.getListaUtenti());
                String carteManoGioc2 = x.getCarteManoGioc2String(x.getListaUtenti());
                String carteManoGioc3 = x.getCarteManoGioc3String(x.getListaUtenti());
                String carteManoGioc4 = x.getCarteManoGioc4String(x.getListaUtenti());
                String cartePersGioc1 = x.getCartePersGioc1(x.getListaUtenti());
                String cartePersGioc2 = x.getCartePersGioc2(x.getListaUtenti());
                String cartePersGioc3 = x.getCartePersGioc3(x.getListaUtenti());
                String cartePersGioc4 = x.getCartePersGioc4(x.getListaUtenti());
                String cartaTerrGioc1 = x.getCartaTerrGioc1String(x.getListaUtenti());
                String cartaTerrGioc2 = x.getCartaTerrGioc2String(x.getListaUtenti());
                String cartaTerrGioc3 = x.getCartaTerrGioc3String(x.getListaUtenti());
                String cartaTerrGioc4 = x.getCartaTerrGioc4String(x.getListaUtenti());
                JDBCPartite.salvaPartitaInCorso(codice, numGiocatori, nomiGiocatori, mazzo, turnoGioc, turnoTot, timeline, carteManoGioc1, carteManoGioc2, carteManoGioc3, carteManoGioc4, cartePersGioc1, cartePersGioc2, cartePersGioc3, cartePersGioc4, cartaTerrGioc1, cartaTerrGioc2, cartaTerrGioc3, cartaTerrGioc4);
            }
        } catch (Exception e) {
            gestisciEccezione(e);
        }
    }

    public void onButtonClick(ActionEvent event) throws IOException {
        try {
            if (event.getSource() instanceof Button) {
                String testoLabel = "";
                Button button = (Button) event.getSource();
                Pane pane = (Pane) button.getParent();

                for (Node node : pane.getChildren()) {
                    if (node instanceof Label) {
                        Label label = (Label) node;
                        testoLabel = label.getText();
                        break;
                    }
                }
                String[] nomiTemp = testoLabel.split(" vs ");

                esterno:
                for (Partita partita : ControllerCreazionePartita.t.getPartite()) {
                    for (Utente u : partita.getListaUtenti()) {
                        if (u.getNome().equals(nomiTemp[0]) || u.getNome().equals(nomiTemp[1])) {
                            ControllerCreazionePartita.p = partita;
                            break esterno;
                        }
                    }
                }
                ControllerCreazionePartita.p.torneoOn();
                Main m = new Main();
                if (ControllerCreazionePartita.p.getTurnoTot() == 0)
                    m.setScena("SCENE/SCENA-SCHERMATA-SCELTA3PERSONAGGI.fxml");
                else
                    m.setScena("SCENE/SCENA-PARTITA.fxml");
            }
        } catch (Exception e) {
            gestisciEccezione(e);
        }
    }
    	
    private void gestisciEccezione(Exception e) {
        if (e instanceof AWTException) {
            System.out.println("Si è verificata un'eccezione AWT: " + e.getMessage());
        } else if (e instanceof IOException) {
            System.out.println("Si è verificata un'eccezione di I/O: " + e.getMessage());
        } else if (e instanceof SQLException) {
            System.out.println("Si è verificata un'eccezione SQL: " + e.getMessage());
        } else {
            System.out.println("Si è verificata un'eccezione generica: " + e.getMessage());
        }
        e.printStackTrace();
    }

}
