import javafx.animation.*;
import javafx.scene.paint.ImagePattern;

import java.io.IOException;

public class Controller {

    protected Model model;
    protected View view;
    protected ControllerKeyboard keyboard;
    protected ControllerAction actions;
    protected AnimationTimer animator;

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
        keyboard = new ControllerKeyboard(model,view,this);
        actions = new ControllerAction(model,view,this);

        animator = new AnimationTimer(){

            @Override
            public void handle(long arg0) {

                if (model.lastFrame == -1) {
                    model.lastFrame = arg0;
                }
                else {
                    if ((arg0-model.lastFrame) < 10000000) return;

                    model.lastFrame = arg0;
                }
                model.update();
            }
        };
    }

    /* all the methods to control the game behavior */

    public void startGame() {
        model.startGame();
        view.startGame();

	/* CAUTION: since starting the game implies to
	   remove the intro pane from root, then root has no more
	   children. It seems that this removal causes a focus lost
	   which must be set once again in ordre to catch keyboard events.
	*/
        view.root.setFocusTraversable(true);
        view.root.requestFocus();
        animator.start();
    }

    public void changeLevel() {
        animator.stop();
        model.changeLevel();
        view.changeLevel();
       // view.changeLevel();

        try {
            Thread.sleep(1000);
        }
        catch(InterruptedException e) {}
        animator.start();

    }



    public void persoMoveLeft() {
        model.pacman.moveLeft();
    }
    public void persoMoveRight() {
        model.pacman.moveRight();
    }
    public void persoMoveUp() {
        model.pacman.moveUp();
    }
    public void persoMoveDown() {
        model.pacman.moveDown();
    }
    public void persoStop() {
        model.pacman.stop();
        model.blinky.stop();
        model.inky.stop();
        model.clyde.stop();
    }
    public void persoStart() {
        model.pacman.start();
        model.blinky.start();
        model.inky.start();
        model.clyde.start();
    }


}