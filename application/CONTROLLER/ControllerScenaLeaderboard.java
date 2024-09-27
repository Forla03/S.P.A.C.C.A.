package application.CONTROLLER;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Properties;
import application.CLASSI.Utente;
import application.CLASSI.JDBCCaricamento;
import application.Main;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;

public class ControllerScenaLeaderboard {

    @FXML
    private TableColumn<Utente, String> colonnaGioc;

    @FXML
    private TableColumn<Utente, Integer> colonnaPunti;

    @FXML
    private TableView<Utente> tabella;

    @FXML
    private TextField mailText;

    private static StringBuilder leaderboardString = new StringBuilder();
    private static String filePath = "leaderboard.csv";

    public void initialize() {
        try {
            colonnaGioc.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNome()));
            colonnaPunti.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getPunteggio()).asObject());

            ArrayList<Utente> listaUtenti = JDBCCaricamento.getUtenti();

            Collections.sort(listaUtenti, Comparator.comparingInt(Utente::getPunteggio).reversed());

            tabella.setItems(FXCollections.observableArrayList(listaUtenti));

            createCSVFile(filePath);

            for (Utente utente : listaUtenti) {
                leaderboardString.append(utente.getNome())
                                 .append(" - ")
                                 .append(utente.getPunteggio())
                                 .append("\n");
            }
        } catch (Exception e) {
            gestisciEccezioni(e);
        }
    }

    private void createCSVFile(String filePath) {
        try {
            ArrayList<Utente> listaUtenti = JDBCCaricamento.getUtenti();

            Collections.sort(listaUtenti, Comparator.comparingInt(Utente::getPunteggio).reversed());

            try (FileWriter writer = new FileWriter(filePath)) {
                writer.append("Nome, Punteggio\n");

                for (Utente utente : listaUtenti) {
                    writer.append(utente.getNome())
                          .append(", ")
                          .append(String.valueOf(utente.getPunteggio()))
                          .append("\n");
                }
            }
        } catch (Exception e) {
            gestisciEccezioni(e);
        }
    }

    public void goBack(ActionEvent event) throws IOException {
        goBackCheck();
    }

    public void goBackCheck() throws IOException {
        try {
            Main m = new Main();
            m.setScena("SCENE/SCENA-SCHERMATA-INIZIALE-2.fxml");
        } catch (Exception e) {
            gestisciEccezioni(e);
        }
    }

    public  void sendEmail(String recipient, String filePath) {
        try {
            Properties properties = new Properties();
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.starttls.enable", "true");
            properties.put("mail.smtp.host", "smtp.gmail.com");
            properties.put("mail.smtp.port", "587");

            final String myAccountEmail = "S.P.A.C.C.A.CardGame@gmail.com";
            final String password = "jqqq kmoi fuab whus";

            Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(myAccountEmail, password);
                }
            });

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject("Leaderboard S.P.A.C.C.A");

            Multipart multipart = new MimeMultipart();

            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setText("Ecco la leaderboard che hai richiesto:\n" + leaderboardString);

            MimeBodyPart attachmentPart = new MimeBodyPart();
            FileDataSource source = new FileDataSource(filePath);
            attachmentPart.setDataHandler(new DataHandler(source));
            attachmentPart.setFileName(new File(filePath).getName());

            multipart.addBodyPart(textPart);
            multipart.addBodyPart(attachmentPart);
            message.setContent(multipart);

            Transport.send(message);
            System.out.println("Email sent successfully.");
        } catch (Exception e) {
            gestisciEccezioni(e);
        }
    }

    public void mandaMail() {
        try {
            sendEmail(mailText.getText(), filePath);
        } catch (Exception e) {
            gestisciEccezioni(e);
        }
    }

    private void gestisciEccezioni(Exception e) {
        if (e instanceof IOException) {
            System.out.println("Si è verificato un errore di I/O: " + e.getMessage());
        } else if (e instanceof MessagingException) {
            System.out.println("Si è verificato un errore durante l'invio dell'email: " + e.getMessage());
        } else if (e instanceof NullPointerException) {
            System.out.println("Si è verificato un puntatore nullo: " + e.getMessage());
        } else {
            System.out.println("Si è verificato un errore: " + e.getMessage());
        }
    }

}
