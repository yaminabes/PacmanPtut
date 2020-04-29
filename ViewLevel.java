import javafx.scene.layout.Pane;
public abstract class ViewLevel{
    Pane pane;
    Model model;

    public ViewLevel(Model model, Pane pane){
        this.model = model;
	    this.pane = pane;
	    pane.getChildren().clear();

        pane.setStyle("-fx-background-color: black;");

        // autocall the init method to setup the level.
        init();
    }

    public abstract void init();
}