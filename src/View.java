import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import java.io.IOException;
public class View {

    Button btn;
    Pane paneIntro;
    Model model ;
    Group root;
    Pane paneGame;
    ViewLevel currentLevel;

    public View(Model model) {

        this.model = model;
        root = new Group();
        paneIntro = new Pane();
        paneIntro.setPrefSize(model.width,model.height);
        paneIntro.setStyle("-fx-background-color: black;");
        paneGame = new Pane();
        paneGame.setPrefSize(model.width,model.height);

        Image image = new Image(getClass().getResourceAsStream("pacman.png"));
        ImageView imageView = new ImageView(image);
        imageView.setX(50);
        imageView.setY(25);
        imageView.setFitHeight(455);
        imageView.setFitWidth(500);
        imageView.setPreserveRatio(true);
        paneIntro.getChildren().add(imageView);

        btn = new Button();
        btn.setText("Start the game");
        btn.setStyle("-fx-background-color: transparent; -fx-text-fill: #ffff00; -fx-border-color:  #ffff00;-fx-border-width: 1px; -fx-border-style: solid; -fx-border-radius: 30;	-fx-font-size: 20px;");
        btn.setLayoutX(225);
        btn.setLayoutY(250);
        paneIntro.getChildren().add(btn);
        root.getChildren().add(paneIntro);
    }
    public void startGame()  {

        // remove intro pane from the scene
        root.getChildren().clear();
        // add game panel to the scene
        currentLevel = new ViewLevel1(model,paneGame);
        root.getChildren().add(paneGame);
        try {
            model.obstacles.addAll(ViewLevel1.setRandomWall(model.wall, model.squareSize));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            paneGame.getChildren().addAll(ViewLevel1.setRandomWall(model.wall, model.squareSize));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void changeLevel() {
        if (model.level == 1) {
            currentLevel = new ViewLevel1(model,paneGame);
        }
    }
}
