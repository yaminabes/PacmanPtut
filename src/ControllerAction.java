import javafx.event.*;

public class ControllerAction implements EventHandler<ActionEvent> {

    protected Model model;
    protected View view;
    protected Controller control;

    public ControllerAction(Model model, View view, Controller control) {
        this.model = model;
        this.view = view;
        this.control = control;
        // Attach this listener to different view elements
        view.btn.setOnAction(this);

    }

    public void handle(ActionEvent event) {

        if (event.getSource() == view.btn) {
            if (model.state == Model.STATE_INIT) {
                control.startGame();
            }
        }
    }
}
