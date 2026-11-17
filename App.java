import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Database;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/funcionario.fxml"));
        Scene scene = new Scene(loader.load(), 640, 480);
        stage.setTitle("Sistema CRUD de Funcionários");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() {
        Database.close();
    }

    public static void main(String[] args) {
        launch(args);
    }
}