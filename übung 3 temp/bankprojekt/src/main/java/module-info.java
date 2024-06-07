module bankprojekt.bankprojekt {
    requires javafx.controls;
    requires javafx.fxml;


    opens bankprojekt.bankprojekt to javafx.fxml;
    exports bankprojekt.bankprojekt;
}