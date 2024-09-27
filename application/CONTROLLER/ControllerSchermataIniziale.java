package application.CONTROLLER;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.ProgressBar;
import java.util.Timer;
import java.util.TimerTask;
import application.Main;

public class ControllerSchermataIniziale {

    @FXML
    private Button bottonePlay;

    @FXML
    private ProgressBar barraProgresso;

    private Timer timer;
    private double progress = 0.0;

    public void playButton(ActionEvent event) {
        try {
            playButtonCheck();
        } catch (IOException e) {
            gestisciEccezioni(e);
        }
    }

    private void updateButtonBorder() {
        if (progress < 0.1) {
            bottonePlay.setStyle("-fx-border-color: rgb(0,0,0,0)");
        } else {
            bottonePlay.setStyle("-fx-border-color: #000000");
        }
    }

    public void progressBar(MouseEvent event) {
        try {
            // Assicurati che il timer sia null o cancellalo se è attivo
            if (timer != null) {
                timer.cancel();
            }

            // Inizializza un nuovo timer
            timer = new Timer();

            // Crea un compito da eseguire ogni 10 millisecondi
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    try {
                        // Aumenta il progresso
                        progress += 0.01;

                        // Controlla se il progresso ha raggiunto il massimo
                        if (progress >= 1.0) {
                            progress = 1.0;
                            timer.cancel();  // Interrompi il timer quando il progresso raggiunge il massimo
                        }

                        // Aggiorna la barra con il nuovo progresso
                        barraProgresso.setProgress(progress);
                        updateButtonBorder();
                    } catch (Exception e) {
                        gestisciEccezioni(e);
                    }
                }
            };

            // Esegui il compito ogni 10 millisecondi
            timer.scheduleAtFixedRate(task, 0, 10);
        } catch (Exception e) {
            gestisciEccezioni(e);
        }
    }

    public void progressBarRemove(MouseEvent event) {
        try {
            // Assicurati che il timer sia null o cancellalo se è attivo
            if (timer != null) {
                timer.cancel();
            }

            // Inizializza un nuovo timer
            timer = new Timer();

            // Crea un compito da eseguire ogni 10 millisecondi
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    try {
                        // Diminuisci il progresso
                        progress -= 0.01;

                        // Controlla se il progresso è inferiore a 0
                        if (progress <= 0.0) {
                            progress = 0.0;
                            timer.cancel();  // Interrompi il timer quando il progresso è inferiore a 0
                        }

                        // Aggiorna la barra con il nuovo progresso
                        barraProgresso.setProgress(progress);
                        updateButtonBorder();
                    } catch (Exception e) {
                        gestisciEccezioni(e);
                    }
                }
            };

            // Esegui il compito ogni 10 millisecondi
            timer.scheduleAtFixedRate(task, 0, 10);
        } catch (Exception e) {
            gestisciEccezioni(e);
        }
    }

    public void playButtonCheck() throws IOException {
        Main m = new Main();
        m.setScena("SCENE/SCENA-ACCESSO-PARTITAOTORNEO.fxml");
    }

    public void zonaAmministratoreButton(ActionEvent event) {
        try {
            zonaAmministratoreButtonCheck();
        } catch (IOException e) {
            gestisciEccezioni(e);
        }
    }

    public void zonaAmministratoreButtonCheck() throws IOException {
        Main m = new Main();
        m.setScena("SCENE/SCENA-SCHERMATA-ZONAAMMINISTRATORE.fxml");
    }

    public void regoleButton(ActionEvent event) {
        try {
            regoleButtonCheck();
        } catch (IOException e) {
            gestisciEccezioni(e);
        }
    }

    public void regoleButtonCheck() throws IOException {
        Main m = new Main();
        m.setScena("SCENE/SCENA-SCHERMATA-REGOLE.fxml");
    }

    public void carteButton(ActionEvent event) {
        try {
            carteButtonCheck();
        } catch (IOException e) {
            gestisciEccezioni(e);
        }
    }

    public void carteButtonCheck() throws IOException {
        Main m = new Main();
        m.setScena("SCENE/SCENA-SCHERMATA-CARTE.fxml");
    }

    public void impostazioniButton(ActionEvent event) {
        try {
            impostazioniButtonCheck();
        } catch (IOException e) {
            gestisciEccezioni(e);
        }
    }

    public void impostazioniButtonCheck() throws IOException {
        Main m = new Main();
        m.setScena("SCENE/SCENA-SCHERMATA-LEADERBOARD.fxml");
    }

    private void gestisciEccezioni(Exception e) {
        if (e instanceof IOException) {
            System.out.println("Si è verificata un'eccezione di I/O: " + e.getMessage());
        } else if (e instanceof NullPointerException) {
            System.out.println("Si è verificata un'eccezione di NullPointerException: " + e.getMessage());
        } else if (e instanceof ClassCastException) {
            System.out.println("Si è verificata un'eccezione di ClassCastException: " + e.getMessage());
        } else if (e instanceof IllegalStateException) {
            System.out.println("Si è verificata un'eccezione di IllegalStateException: " + e.getMessage());
        } else if (e instanceof SecurityException) {
            System.out.println("Si è verificata un'eccezione di SecurityException: " + e.getMessage());
        } else if (e instanceof IllegalArgumentException) {
            System.out.println("Si è verificata un'eccezione di IllegalArgumentException: " + e.getMessage());
        } else {
            System.out.println("Si è verificata un'eccezione generica: " + e.getMessage());
        }
        e.printStackTrace();
    }
}
