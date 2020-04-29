import javafx.scene.layout.Pane;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.paint.Color;

public class ViewLevel1 extends ViewLevel {

    public ViewLevel1(Model model, Pane pane) {
        super(model, pane);

    }

    @Override
    public void init() {
        pane.getChildren().addAll(model.obstacles);
        pane.getChildren().addAll(model.pacman.shape);

    }

    public static int[][] initRandomWall(int[][] wallToChange) throws IOException {
        int[][] wall = wallToChange;

        File file = new File("src/wall/wall1.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String st;

        while((st = br.readLine()) != null) {
            int x = Integer.parseInt(st.split(" ")[0]);
            int y = Integer.parseInt(st.split(" ")[1]);
            wall[x][y] = 1;
        }

        return wall;
    }

    public static List<Rectangle> setRandomWall(int[][] wallToCheck, int squareSize) throws IOException {
        int[][] wall = initRandomWall(wallToCheck);
        List<Rectangle> wallToBuild = new ArrayList<Rectangle>();

        for(int i=0; i<wall.length; i++) {
            for(int j=0; j<wall[i].length; j++) {
                if (wall[i][j] == 1) {
                    Rectangle r = new Rectangle(i*squareSize, j*squareSize, squareSize, squareSize);
                    r.setFill(Color.BLUE);
                    r.setStroke(Color.WHITE);
                    wallToBuild.add(r);
                }
            }
        }

        return wallToBuild;
    }
}
