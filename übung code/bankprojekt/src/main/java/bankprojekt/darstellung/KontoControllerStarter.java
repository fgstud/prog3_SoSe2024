package bankprojekt.darstellung;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Arrays;

public class KontoControllerStarter extends Application{
    @Override
    public void start(Stage primaryStage) {
        KontoController controller = new KontoController(primaryStage);
        Parent lc = controller.getView();
        Scene scene = new Scene(lc);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Bank");
        primaryStage.show();
    }
    /**
     * Main methode
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(Arrays.toString(args));
        launch(args);
    }
}
