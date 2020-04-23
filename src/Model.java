import javafx.scene.Node;

import java.util.ArrayList;
import java.util.List;

public class Model{
    // the game state
    final static int STATE_INIT = 1;
    final static int STATE_PLAY = 2;
    final static int STATE_PAUSED = 3;
    int level;
    int state;

    // window related attributes
    int width;
    int height;
    long lastFrame;
    int squareSize;
    //obstacles
    int[][] wall;
    ArrayList<Node> obstacles;

    // Taille du tableau de données
    int[] usedSize = {height, width};

    // Pacman
    Pacman pacman;
    //Gosts
    Blinky blinky;
    Inky inky;
    Clyde clyde;

    List<Sprite> sprites;

    public Model(){
        state = STATE_INIT;
        level = -1;
        width = 1200; // windows width
        height = 600; // window height
        usedSize[0] = height;
        usedSize[1] = width;
        squareSize = 30;
        lastFrame = -1;
        pacman = new Pacman(this);
        blinky = new Blinky(this);
        inky = new Inky(this);
        clyde = new Clyde(this);
        
        System.out.println(usedSize[0]);

        wall = initWall(usedSize[0], usedSize[1], squareSize);
        sprites = new ArrayList<>();
        obstacles = new ArrayList<Node>();
        initWall(height,width,squareSize);

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

    public void startGame(){
        state = STATE_PLAY;
        level = 1;
        blinky.reset();
        inky.reset();
        clyde.reset();
        blinky.setSpeed(5);
        inky.setSpeed(5);
        clyde.setSpeed(5);
        sprites.add(blinky);
        sprites.add(inky);
        sprites.add(clyde);
        sprites.add(pacman);
    }
    public void changeLevel(){
        sprites.clear();
        pacman.reset();
        obstacles.clear();
        // cycle through levels for demo
        level = 1+ (level%3);
        if (level == 1) {

            sprites.add(pacman);
        }
    }


    public void update() {
        for (Sprite s : sprites) {
            s.update();
        }
    }

}