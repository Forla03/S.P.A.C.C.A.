package application.CONTROLLER;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

import application.Main;
import application.CLASSI.JDBCCaricamento;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.fxml.Initializable;

public class ControllerScenaRegole implements Initializable {
    @FXML
    TextArea testo;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        try {
            File file;
            String pathPc = ControllerScenaRegole.getPathComputer();
            if (!isRunningFromJar()) {
                file = new File(pathPc + "\\src\\application\\CONTROLLER\\regolamento.txt");
            } else {
                file = new File("regolamento.txt");
            }
            Scanner scanner = new Scanner(file);
            StringBuilder sb = new StringBuilder();
            while (scanner.hasNextLine()) {
                sb.append(scanner.nextLine()).append("\n");
            }
            testo.setWrapText(true); // Imposta il wrapping del testo
            testo.setText(sb.toString());
            testo.setEditable(false); // Imposta la TextArea come non modificabile
            scanner.close();
        } catch (FileNotFoundException e) {
            gestisciEccezioni(e);
            System.out.println("File del regolamento non trovato, verificare il percorso del file");
        } catch (Exception e) {
            gestisciEccezioni(e);
        }
    }

    public void goBack(ActionEvent event) throws IOException {
        goBackCheck();
    }

    public void goBackCheck() throws IOException {
        Main m = new Main();
        m.setScena("SCENE/SCENA-SCHERMATA-INIZIALE-2.fxml");
    }

    public static String getPathComputer() {
        String jarFilePath = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        String appDirectory = new File(jarFilePath).getParent();

        return appDirectory;
    }

    public static boolean isRunningFromJar() {
        String className = JDBCCaricamento.class.getName().replace('.', '/');
        String classJar = JDBCCaricamento.class.getResource("/" + className + ".class").toString();
        return classJar.startsWith("jar:");
    }

    private void gestisciEccezioni(Exception e) {
        if (e instanceof IOException) {
            // Gestione dell'IOException
            System.out.println("Si è verificato un errore di I/O: " + e.getMessage());
        } else if (e instanceof FileNotFoundException) {
            // Gestione del FileNotFoundException
            System.out.println("File non trovato: " + e.getMessage());
        } else {
            // Gestione generica per altre eccezioni non specificate
            System.out.println("Si è verificato un errore: " + e.getClass().getName() + ": " + e.getMessage());
        }
    }
}
