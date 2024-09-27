package application.CONTROLLER;

import java.io.IOException;

import application.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class ControllerVerificaCodiceTorneo {

    @FXML
    private TextField codicePartita;
    
    @FXML
    private Text errorLogin;
    
    @FXML
    private Button giocaButton;
    
    private static String ultimoCodInserito;
    
    public void goBack() {
        try {
            Main m = new Main();
            m.setScena("SCENE/SCENA-ACCESSO-PARTITAOTORNEO.fxml");
        } catch (IOException e) {
            // Gestione dell'errore: log dell'errore, reset dell'interfaccia, ecc.
            System.err.println("Errore nel caricamento della scena di accesso al torneo: " + e.getMessage());
        }
    }
    
    public void checkLogin() {
        Main m = new Main();
        String codicePartitaText = codicePartita.getText();

        if (codicePartitaText == null || codicePartitaText.trim().isEmpty()) {
            // Campo codice partita vuoto
            errorLogin.setText("Inserisci il codice della partita");
            errorLogin.setFill(Color.RED);
        } else {
            try {
                // Verifica del codice partita
                if (codicePartitaText.equals(ControllerCreazionePartita.t.getCodice())) {
                    ultimoCodInserito = codicePartitaText;
                    m.setScena("SCENE/SCENA-TORNEO.fxml");
                } else {
                    // Codice partita non valido
                    errorLogin.setText("Il codice inserito non è valido");
                    errorLogin.setFill(Color.RED);
                }
            } catch (IOException e) {
                // Gestione dell'errore: log dell'errore, reset dell'interfaccia, ecc.
                System.err.println("Errore nel caricamento della scena del torneo: " + e.getMessage());
            } catch (NullPointerException e) {
                // Gestione dell'errore nel caso in cui t o il suo metodo getCodice() sia null
                errorLogin.setText("Errore nel recupero del codice torneo");
                errorLogin.setFill(Color.RED);
                System.err.println("Errore nel recupero del codice torneo: " + e.getMessage());
            }
        }
    }
    
    public static String getUltimoCodice() {
        return ultimoCodInserito;
    }
}
