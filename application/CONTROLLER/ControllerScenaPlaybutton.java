package application.CONTROLLER;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import java.io.IOException;
import javafx.scene.paint.Color;
import application.Main;

public class ControllerScenaPlaybutton {
    
    @FXML
    private TextField codicePartita;
    
    @FXML
    private Text errorLogin;
    
    @FXML
    private Button giocaButton;
    
    private static String ultimoCodInserito;
        
    public void goBack(ActionEvent event) throws IOException{
        goBackCheck();
    }
    
    public void goBackCheck() throws IOException {
        Main m = new Main();
        m.setScena("SCENE/SCENA-SCHERMATA-INIZIALE-2.fxml");
    }
    
    public void logIn(ActionEvent event) throws IOException {
        try {
            checkLogin();
        } catch (IOException e) {
            gestisciEccezioni(e);
        }
    }

    public void checkLogin() throws IOException {
        Main m = new Main();
        String codicePartitaText = codicePartita.getText();

        if (codicePartitaText.equals(ControllerCreazionePartita.p.getCodice()) && !ControllerCreazionePartita.p.getTorneo()) {
            ultimoCodInserito = codicePartitaText;
            if (ControllerCreazionePartita.p.getTurnoTot() >= 1) {
                m.setScena("SCENE/SCENA-PARTITA.fxml");
            } else {
                m.setScena("SCENE/SCENA-SCHERMATA-SCELTA2PERSONAGGI.fxml");
            }
        } else {
            errorLogin.setText("Il codice inserito non è valido");
            errorLogin.setFill(Color.RED);
        }
    }
    
    public static String getUltimoCodice() {
        return ultimoCodInserito;
    }
    
    private void gestisciEccezioni(Exception e) {
        if (e instanceof IOException) {
            System.out.println("Errore di I/O: " + e.getMessage());
        } else {
            System.out.println("Si è verificato un errore: " + e.getClass().getName() + ": " + e.getMessage());
        }
    }

}
