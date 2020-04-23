package sample;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import org.w3c.dom.css.Rect;

import java.io.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Canvas {
    public static int[][] initRandomWall(int[][] wallToChange) throws IOException {
        int[][] wall = wallToChange;

        File file = new File("src/sample/wall1.txt");
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
                    wallToBuild.add(r);
                }
            }
        }

        return wallToBuild;
    }

    public static Arc initPacman(Scene scene, int squareSize, BorderPane bP) {
        Arc pacman = new Arc();
        pacman.setCenterX(squareSize*8-squareSize/2);
        pacman.setCenterY(squareSize*8-squareSize/2);
        pacman.setRadiusX(squareSize/2);
        pacman.setRadiusY(squareSize/2);
        pacman.setStartAngle(35);
        pacman.setLength(295);
        pacman.setType(ArcType.ROUND);
        pacman.setFill(Color.YELLOW);
        bP.getChildren().add(pacman);

        return pacman;
    }

    public static int[][] initWall(int height, int width, int squareSize) {
        int nbSquareInWidth = width/squareSize;
        int spaceBeforeAfterWidth = (width - nbSquareInWidth*squareSize)/2;

        int nbSquareInHeight = height/30;
        int spaceBeforeAfterHeight = (height - nbSquareInHeight*squareSize)/2;

        if (spaceBeforeAfterHeight == 0) {
            spaceBeforeAfterHeight = squareSize;
        }

        if (spaceBeforeAfterWidth == 0) {
            spaceBeforeAfterWidth = squareSize;
        }
        

        int[][] wall = new int[nbSquareInWidth][nbSquareInHeight];

        for(int i=spaceBeforeAfterWidth; i<width-spaceBeforeAfterWidth; i+=squareSize) {
            for(int j=spaceBeforeAfterHeight; j<height-spaceBeforeAfterHeight; j+=squareSize) {
                wall[i/squareSize][j/squareSize] = 1;
            }
        }

        for(int i=spaceBeforeAfterWidth+squareSize; i<width-spaceBeforeAfterWidth-squareSize; i+=squareSize) {
            for(int j=spaceBeforeAfterHeight+squareSize; j<height-spaceBeforeAfterHeight-squareSize; j+=squareSize) {
                wall[i/squareSize][j/squareSize] = 0;
            }
        }

        // Définition des ouvertures

        wall[1][nbSquareInHeight/2] = 0;
        wall[nbSquareInWidth-2][nbSquareInHeight/2] = 0;
        wall[nbSquareInWidth/2][1] = 0;
        wall[nbSquareInWidth/2][nbSquareInHeight-2] = 0;

        return wall;
    }

    public static List<Rectangle> Canvas(Scene scene, int height, int width, int squareSize) {

        List<Rectangle> border = new ArrayList<Rectangle>();

        int nbSquareInWidth = width/squareSize;
        int spaceBeforeAfterWidth = (width - nbSquareInWidth*squareSize)/2;

        int nbSquareInHeight = height/30;
        int spaceBeforeAfterHeight = (height - nbSquareInHeight*squareSize)/2;

        if (spaceBeforeAfterHeight == 0) {
            spaceBeforeAfterHeight = squareSize;
        }

        if (spaceBeforeAfterWidth == 0) {
            spaceBeforeAfterWidth = squareSize;
        }

        System.out.println("Taille du carré : " + squareSize);
        System.out.println("Espaces en largeur : " + spaceBeforeAfterWidth);
        System.out.println("Espaces en hauteur : " + spaceBeforeAfterHeight);
        System.out.println("Nombre de carré en largeur : " + nbSquareInWidth);
        System.out.println("Nombre de carré en hauteur : " + nbSquareInHeight);

        for(int i=spaceBeforeAfterWidth; i<width-spaceBeforeAfterWidth; i+=squareSize) {
            for(int j=spaceBeforeAfterHeight; j<height-spaceBeforeAfterHeight; j+=squareSize) {
                if (i/squareSize != nbSquareInWidth/2) {
                    if (j/squareSize != nbSquareInHeight/2) {
                        Rectangle r = new Rectangle(i, j, squareSize, squareSize);
                        r.setFill(Color.BLUE);
                        r.setStroke(Color.RED);
                        border.add(r);
                    }
                }
            }
        }

        for(int i=spaceBeforeAfterWidth+squareSize; i<width-spaceBeforeAfterWidth-squareSize; i+=squareSize) {
            for(int j=spaceBeforeAfterHeight+squareSize; j<height-spaceBeforeAfterHeight-squareSize; j+=squareSize) {
                Rectangle r = new Rectangle(i, j, squareSize, squareSize);
                r.setFill(Color.BLACK);
                r.setStroke(Color.WHITE);
                border.add(r);
            }
        }

        return border;
    }
}
