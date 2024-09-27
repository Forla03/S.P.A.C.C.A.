module SPACCA_FINALE {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires java.sql;
	requires javafx.graphics;
	requires java.logging;
	requires java.desktop;
	requires java.mail;
	requires java.activation;
    
	opens application.CONTROLLER to javafx.graphics, javafx.fxml;
    opens application to javafx.graphics, javafx.fxml;
    opens application.CLASSI to javafx.graphics, javafx.fxml;
}