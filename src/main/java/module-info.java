module edu.smc.javafx {
    requires javafx.controls;
    requires javafx.fxml;


    opens edu.smc.javafx to javafx.fxml;
    exports edu.smc.javafx;
}