import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Model model = new Model();
        View view = new View(model);
        Controller control = new Controller(model,view);

        Image applicationIcon = new Image(getClass().getResourceAsStream("images/pacman.png"));
        stage.getIcons().add(applicationIcon);
        stage.setTitle("PacmanFX");
        
        Scene scene = new Scene(view.root, model.width, model.height);
        stage.setScene(scene);
        stage.show();


    }
}
