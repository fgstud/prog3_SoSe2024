package bankprojekt.darstellung;


import bankprojekt.verarbeitung.GesperrtException;
import bankprojekt.verarbeitung.Girokonto;
import bankprojekt.verarbeitung.Kunde;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import java.time.LocalDate;


public class KontoController extends Application{
    private Kunde kunde = new Kunde("Max", "Mustermann", "TestAdresse", LocalDate.now());
    private Girokonto konto = new Girokonto(kunde, 1000,1000);

    /**
     * Das Hauptfenster der Anwendung
     */
    private Stage stage;

    public KontoController(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private Text kontoNummer;

    @FXML private Text kontoStand;

    @FXML private CheckBox gesperrt;

    @FXML
    private TextArea adresse;

    @FXML private TextField betrag;

    @FXML public void initialize() {
//        kontoNummer.setText(konto.getKontonummerFormatiert());
//        kontoStand.textProperty().bind(konto.kontostandProperty().asString());
//        kontoStand.fillProperty().bind(Bindings.when(konto.kontoNegativProperty())
//                .then(Color.RED).otherwise(Color.GREEN));
//
//        gesperrt.selectedProperty().bindBidirectional(konto.gesperrtProperty());
//        adresse.textProperty().bindBidirectional(konto.getInhaber().adresseProperty());
    }

    public KontoController() {
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("kontoController.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 320, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Bank");
        primaryStage.show();
    }

    /**
     * Hebt den angegebenen Betrag vom Konto ab
     * @param betragString Betrag der abgehoben werden soll
     */
    public void abheben(String betragString){
        double betrag = Double.valueOf(betragString);
        try {
            konto.abheben(betrag);
        } catch (GesperrtException e) {
            e.printStackTrace();
        }
    }

    /**
     * Zahlt den angegebenen Betrag auf dem Konto ein
     * @param betragString Wert der eingezahlt werden soll.
     */
    public void einzahlen(String betragString){
        double betrag = Double.valueOf(betragString);
        konto.einzahlen(betrag);
    }

//    /**
//     * gibt die aktuell angezeigte Oberfläche zurück
//     * @return die aktuell angezeigte Oberfläche
//     */
//    public Parent getView() {
//        return this.view;
//    }
//
//    /**
//     * setzt die View dieses Controllers
//     * @param view die aktuelle View
//     */
//    protected void setView(Parent view) {
//        this.view = view;
//    }

    /**
     * Schließen des Fensters
     */
    public void schliessen()
    {
        stage.close();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
