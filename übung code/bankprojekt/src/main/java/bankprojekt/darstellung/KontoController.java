package bankprojekt.darstellung;


import bankprojekt.verarbeitung.GesperrtException;
import bankprojekt.verarbeitung.Girokonto;
import bankprojekt.verarbeitung.Kunde;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import java.time.LocalDate;


public class KontoController{
    private Kunde kunde = new Kunde("Max", "Mustermann", "TestAdresse", LocalDate.now());
    private Girokonto konto = new Girokonto(kunde, 1000,1000);
    /**
     * Die View, die das Konto anzeigt
     */
    private Parent view;
    /**
     * Das Hauptfenster der Anwendung
     */
    private Stage stage;

    public KontoController(Stage stage) {
        this.stage = stage;
    }


    /**
     * Hebt den angegebenen Betrag vom Konto ab
     * @param betragString Betrag der abgehoben werden soll
     */
//    public void abheben(String betragString){
//        double betrag = Double.valueOf(betragString);
//        try {
//            konto.abheben(betrag);
//        } catch (GesperrtException e) {
//            e.printStackTrace();
//        }
//    }

    /**
     * Zahlt den angegebenen Betrag auf dem Konto ein
     * @param betragString Wert der eingezahlt werden soll.
     */
//    public void einzahlen(String betragString){
//        double betrag = Double.valueOf(betragString);
//        konto.einzahlen(betrag);
//    }

    /**
     * gibt die aktuell angezeigte Oberfläche zurück
     * @return die aktuell angezeigte Oberfläche
     */
    public Parent getView() {
        return this.view;
    }

    /**
     * setzt die View dieses Controllers
     * @param view die aktuelle View
     */
    protected void setView(Parent view) {
        this.view = view;
    }

    /**
     * Schließen des Fensters
     */
    public void schliessen()
    {
        stage.close();
    }

}
