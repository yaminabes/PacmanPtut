import javafx.event.*;
import javafx.scene.input.*;

public class ControllerKeyboard implements EventHandler<KeyEvent> {

    protected Model model;
    protected View view;
    protected Controller control;

    public ControllerKeyboard(Model model, View view, Controller control) {
        this.model = model;
        this.view = view;
        this.control = control;
        // Attach KeyEvent listening to a Node that has focus.
        view.root.setFocusTraversable(true);
        view.root.requestFocus();
        view.root.setOnKeyPressed(this);

    }

    public void handle(KeyEvent arg0) {

        if (model.state == Model.STATE_INIT) return;

        System.out.println("kecode = "+arg0.getCode());

        // change level
        if (arg0.getCode() == KeyCode.L ) {
            System.out.println("changing level");
            control.changeLevel();
        }
        else if (arg0.getCode() == KeyCode.LEFT ) {
            control.persoMoveLeft();
        }
        else if (arg0.getCode() == KeyCode.RIGHT ) {
            control.persoMoveRight();
        }
        else if (arg0.getCode() == KeyCode.UP ) {
            control.persoMoveUp();
        }
        else if (arg0.getCode() == KeyCode.DOWN ) {
            control.persoMoveDown();
        }
        else if (arg0.getCode() == KeyCode.SPACE || arg0.getCode() == KeyCode.ESCAPE) {
            if(control.model.pacman.state == 0){
                control.persoStart();
                //lancer le chronometre et point ...
            } else {
                control.persoStop();
                //stoper le chronometre
            }
        }
    }
}