package application.CONTROLLER;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.control.PasswordField;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;
import application.Main;

import java.io.IOException;

public class ControllerZonaAmministratore {
    
    @FXML
    private Button loginButton;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private StackPane sfondo;
    
    @FXML
    private Text errorLogin;
    
    @FXML
    public void logIn(ActionEvent event) throws IOException {
        checkLogin();
    }

    public void checkLogin() throws IOException {
        Main m = new Main();
        String usernameText = username.getText();
        String passwordText = password.getText();

        if (usernameText.isEmpty() || passwordText.isEmpty()) {
            errorLogin.setText("Inserisci Username e Password");
            errorLogin.setFill(Color.RED);
        } else if (usernameText.equals("admin") && passwordText.equals("admin")) {
            try {
                m.setScena("SCENE/SCENA-SCHERMATA-CREAZIONEPARTITA.fxml");
            } catch (IOException e) {
                errorLogin.setText("Errore nel caricamento della scena");
                errorLogin.setFill(Color.RED);
            }
        } else {
            errorLogin.setText("Username o Password sbagliati o utente non registrato");
            errorLogin.setFill(Color.RED);
        }
    }
    
    @FXML
    public void goBack(ActionEvent event) throws IOException {
        try {
            goBackCheck();
        } catch (IOException e) {
            errorLogin.setText("Errore nel caricamento della scena iniziale");
            errorLogin.setFill(Color.RED);
        }
    }
    
    public void goBackCheck() throws IOException {
        Main m = new Main();
        m.setScena("SCENE/SCENA-SCHERMATA-INIZIALE-2.fxml");
    }
}
