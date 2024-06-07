module com.prog3.bankprojekt {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.prog3.bankprojekt to javafx.fxml;
    exports com.prog3.bankprojekt;
}