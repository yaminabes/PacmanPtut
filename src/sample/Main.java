package sample;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.*;
import javafx.scene.transform.Translate;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.util.Duration;

import java.io.*;
import java.util.concurrent.atomic.AtomicReference;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        // Définition de la fenêtre
        final boolean[] fullScreen = {false};
        Group root = new Group();
        Scene scene = new Scene(root, 1200, 600);
        scene.setFill(Color.BLACK);
        primaryStage.setResizable(false);

        // Ajout de l'icône
        Image applicationIcon = new Image(getClass().getResourceAsStream("pacman.png"));
        primaryStage.getIcons().add(applicationIcon);

        // Constantes
        final int screenHeight = (int) Screen.getPrimary().getVisualBounds().getHeight();
        final int screenWidth = (int)Screen.getPrimary().getVisualBounds().getWidth();
        final int canHeight = (int)scene.getHeight();
        final int canWidth = (int)scene.getWidth();
        final int squareSize = 30;
        final int spaceHeight = (screenHeight - (screenHeight/30)*squareSize)/2;
        final int spaceWidth = (screenWidth - (screenWidth/30)*squareSize)/2;

        final int[] usedSize = {canHeight, canWidth};

        // Initialisation des murs

        int[][] wall = Canvas.initWall(usedSize[0], usedSize[1], squareSize);

        // -----------------------------------------
        
        BorderPane bP = new BorderPane();
        bP.getChildren().addAll(Canvas.Canvas(scene, canHeight, canWidth, squareSize));
        bP.getChildren().addAll(Canvas.setRandomWall(wall, squareSize));
        root.getChildren().add(bP);

        primaryStage.setScene(scene);
        primaryStage.setTitle("PacmanFX");
        primaryStage.show();

        // Pacman & les fantomes

        AtomicReference<Arc> pacman = new AtomicReference<>(Canvas.initPacman(scene, squareSize, bP));
        Image blinkyPic = new Image(getClass().getResourceAsStream("blinky.png"));
        ImageView blinky = new ImageView(blinkyPic);
        blinky.setFitHeight(squareSize);
        blinky.setFitWidth(squareSize);
        blinky.setX(squareSize*5);
        blinky.setY(squareSize*5);
        bP.getChildren().add(blinky);

        // KeyController

        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ESCAPE) {
                primaryStage.close();
            } else if (e.getCode() == KeyCode.F) {
                /* if (fullScreen[0]) {
                    primaryStage.setFullScreen(false);
                    fullScreen[0] = false;
                    bP.getChildren().setAll();
                    bP.getChildren().addAll(Canvas.Canvas(scene, canHeight, canWidth, squareSize));
                    bP.getChildren().add(pacman);
                    usedSize[0] = canHeight;
                    usedSize[1] = canWidth;

                } else {
                    primaryStage.setFullScreen(true);
                    fullScreen[0] = true;
                    bP.getChildren().setAll();
                    bP.getChildren().addAll(Canvas.Canvas(scene, screenHeight, screenWidth, squareSize));
                    bP.getChildren().add(pacman);
                    usedSize[0] = screenHeight;
                    usedSize[1] = screenWidth;
                } */
            } else if (e.getCode() == KeyCode.UP || e.getCode() == KeyCode.Z) {
                if (wall[(int)(pacman.get().getCenterX()/squareSize)][(int)(pacman.get().getCenterY()/squareSize)-1] != 1 && (pacman.get().getCenterY()/squareSize)-1 >= 1) {
                    pacman.get().setRotate(-90);
                    pacman.get().setCenterY(pacman.get().getCenterY()-squareSize);
                    System.out.println("X = " + pacman.get().getCenterX()/squareSize + " & Y = " + pacman.get().getCenterY()/squareSize);
                } else {
                    Rectangle r = new Rectangle(pacman.get().getCenterX()-squareSize/2, pacman.get().getCenterY()-(squareSize/2)*3, squareSize, squareSize);
                    r.setFill(Color.RED);
                    r.setStroke(Color.WHITE);
                    bP.getChildren().addAll(r);
                    FadeTransition fT = new FadeTransition();
                    fT.setNode(r);
                    fT.setDuration(Duration.millis(200));
                    fT.setFromValue(0);
                    fT.setToValue(1);
                    fT.setCycleCount(2);
                    fT.setAutoReverse(true);
                    fT.play();
                    System.out.println("Mur TOP");
                }
            } else if (e.getCode() == KeyCode.DOWN || e.getCode() == KeyCode.S) {
                if (wall[(int)(pacman.get().getCenterX()/squareSize)][(int)(pacman.get().getCenterY()/squareSize)+1] != 1 && (pacman.get().getCenterY()/squareSize)+1 <= wall[(int) pacman.get().getCenterX()/squareSize].length-1) {
                    pacman.get().setRotate(90);
                    pacman.get().setCenterY(pacman.get().getCenterY()+squareSize);
                    System.out.println("X = " + pacman.get().getCenterX()/squareSize + " & Y = " + pacman.get().getCenterY()/squareSize);
                } else {
                    Rectangle r = new Rectangle(pacman.get().getCenterX()-squareSize/2, pacman.get().getCenterY()+squareSize/2, squareSize, squareSize);
                    r.setFill(Color.RED);
                    r.setStroke(Color.WHITE);
                    bP.getChildren().addAll(r);
                    FadeTransition fT = new FadeTransition();
                    fT.setNode(r);
                    fT.setDuration(Duration.millis(200));
                    fT.setFromValue(0);
                    fT.setToValue(1);
                    fT.setCycleCount(2);
                    fT.setAutoReverse(true);
                    fT.play();
                    System.out.println("Mur BOTTOM");
                }
            } else if (e.getCode() == KeyCode.LEFT || e.getCode() == KeyCode.Q) {
                if (wall[(int)(pacman.get().getCenterX()/squareSize)-1][(int)(pacman.get().getCenterY()/squareSize)] != 1 && (pacman.get().getCenterX()/squareSize)-1 >= 1) {
                    pacman.get().setRotate(-180);
                    pacman.get().setCenterX(pacman.get().getCenterX()-squareSize);
                    System.out.println("X = " + pacman.get().getCenterX()/squareSize + " & Y = " + pacman.get().getCenterY()/squareSize);
                } else {
                    Rectangle r = new Rectangle(pacman.get().getCenterX()-(squareSize/2)*3, pacman.get().getCenterY()-squareSize/2, squareSize, squareSize);
                    r.setFill(Color.RED);
                    r.setStroke(Color.WHITE);
                    bP.getChildren().addAll(r);
                    FadeTransition fT = new FadeTransition();
                    fT.setNode(r);
                    fT.setDuration(Duration.millis(200));
                    fT.setFromValue(0);
                    fT.setToValue(1);
                    fT.setCycleCount(2);
                    fT.setAutoReverse(true);
                    fT.play();
                    System.out.println("Mur LEFT");
                }
            } else if (e.getCode() == KeyCode.RIGHT || e.getCode() == KeyCode.D) {
                if (wall[(int)(pacman.get().getCenterX()/squareSize)+1][(int)(pacman.get().getCenterY()/squareSize)] != 1 && (pacman.get().getCenterX()/squareSize)+1 <= wall.length-1) {
                    pacman.get().setRotate(0);
                    pacman.get().setCenterX(pacman.get().getCenterX()+squareSize);
                    System.out.println("X = " + pacman.get().getCenterX()/squareSize + " & Y = " + pacman.get().getCenterY()/squareSize);
                } else {
                    Rectangle r = new Rectangle(pacman.get().getCenterX()+squareSize/2, pacman.get().getCenterY()-squareSize/2, squareSize, squareSize);
                    r.setFill(Color.RED);
                    r.setStroke(Color.WHITE);
                    bP.getChildren().addAll(r);
                    FadeTransition fT = new FadeTransition();
                    fT.setNode(r);
                    fT.setDuration(Duration.millis(200));
                    fT.setFromValue(0);
                    fT.setToValue(1);
                    fT.setCycleCount(2);
                    fT.setAutoReverse(true);
                    fT.play();
                    System.out.println("Mur RIGHT");
                }
            } else if (e.getCode() == KeyCode.R) {
                bP.getChildren().setAll();
                bP.getChildren().addAll(Canvas.Canvas(scene, canHeight, canWidth, squareSize));
                pacman.set(Canvas.initPacman(scene, squareSize, bP));
                try {
                    bP.getChildren().addAll(Canvas.setRandomWall(wall, squareSize));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

    }

    public static void main(String[] args) {
        launch(args);
    }
}
