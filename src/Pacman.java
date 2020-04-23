import javafx.scene.Node;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.geometry.*;
import java.util.*;

public class Pacman extends Sprite{

    Arc shape = new Arc();
    double squareSize = 30;
    double x;
    double y;
    double speed; // the speed (in pixels/ms)
    double angle; // the direction of movement
    int state; // 0=stop, 1=moving, 2= falling...

    public Pacman(Model model) {
        super(model);
        speed = 4;
        angle = 90; // going down
        x = squareSize*8-squareSize/2;
        y = squareSize*8-squareSize/2;
        shape.setCenterX(x);
        shape.setCenterY(y);
        shape.setRadiusX(squareSize/2);
        shape.setRadiusY(squareSize/2);
        shape.setStartAngle(35);
        shape.setLength(295);
        shape.setType(ArcType.ROUND);
        shape.setFill(Color.YELLOW);
        state = 0;
    }

    public void moveLeft() {
        speed = 1;
        angle = 180;
        shape.setRotate(180);
        state = 1;
    }
    public void moveRight() {
        speed = 1;
        angle = 0;
        shape.setRotate(0);
        state = 1;
    }
    public void moveUp() {
        speed = 1;
        angle = 270;
        shape.setRotate(-90);
        state = 1;
    }
    public void moveDown() {
        speed = 1;
        angle = 90;
        shape.setRotate(90);
        state = 1;
    }

    public void stop() {
        speed = 0;
        angle = 0;
        state = 0;
    }

    public void reset() {

        speed = 4;
        angle = 0;
        state = 1;

    }

    private double getNewX() {
        double xx = x;
        xx += Math.cos(Math.toRadians(angle))*speed;
        return xx;
    }

    private double getNewY() {
        double yy = y;
        yy += Math.sin(Math.toRadians(angle))*speed;
        return yy;
    }


    public void update() {
        double xx = x;
        double yy = y;

        if (model.obstacles.isEmpty()) {
            // no obstacles => mandatory walk
            state = 1;
            angle = 0;
            x = getNewX();
            y = getNewY();
            shape.setCenterX(x);
            shape.setCenterY(y);
            return;
        }

        if (state == 0) {
            return; // no movement
        }
        else { // moving

            Circle shape1 = new Circle(x,y,squareSize/3,Color.BLACK);

            if (state == 1) {
                // NO WALL DETECTION for actual position: just move perso one pixel down
                shape1.setCenterX(x);
                shape1.setCenterY(y+1);
                boolean isFloor = false;
                for(Node s : model.obstacles) {
                    Shape inter = Shape.intersect(shape1, (Shape) s);
                }
            }
            xx = getNewX();
            yy = getNewY();

            // COLLISION DETECTION
            shape1.setCenterX(xx);
            shape1.setCenterY(yy);
            // check against all obstacles to determine which ones are
            // in collision with the Wall
            List<Shape> lst = new ArrayList<>();
            for(Node s : model.obstacles) {
                Shape inter = Shape.intersect(shape1, (Shape) s);
                Bounds b = inter.getBoundsInLocal();
                if (b.getWidth() != -1) {
                    lst.add((Shape) s);
                }
            }
            // list not empty => collision
            // search for the minimal move to get a collision
            double ss = speed;
            if (!lst.isEmpty()) {
                int i;
                for(i=1;i<=ss;i++) {
                    speed = i;
                    xx = getNewX();
                    yy = getNewY();
                    shape1.setCenterX(xx);
                    shape1.setCenterY(yy);
                    boolean collide = false;
                    for(Shape s : lst) {
                        Shape inter = Shape.intersect(shape1,s);
                        Bounds b = inter.getBoundsInParent();
                        if (b.getWidth() != -1) {
                            collide = true;
                        }
                    }
                    if (collide) {
                        state = 0; // stop moving
                        speed = i-1;
                        xx = getNewX();
                        yy = getNewY();
                        speed = 0;
                        break;
                    }
                }
                speed = ss;
            }
        }
        // RENDER
        x = xx;
        y = yy;
        shape.setCenterX(x);
        shape.setCenterY(y);
    }
}
