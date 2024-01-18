package byow.Core;

import byow.InputDemo.InputSource;
import byow.TileEngine.TERenderer;
// import edu.princeton.cs.introcs.byow.Core.StdDraw;

import java.io.*;
import java.awt.*;

/** Class for UI (User Interaction) implementation.
 *  Interact with the game as follows:
 *  - During game:
 *      - Press the keys "W, S, A, D" to move player1 (white @)
 *          "Up, Down, Left, Right", respectively.
 *      - Press the keys "U, J, H, K" to move player2 (blue @)
 *          "Up, Down, Left, Right", respectively.
 *      - Press 'C' to see the entire world and press 'C' again to see the limited world
 *        i.e. press 'C' to turn the lights on or off.
 *      - Press ':Q' to save and quit the game.
 *  - In the menu:
 *      - Press 'N' to start a new game.
 *      - Press 'L' to load a existing game
 *      - Press 'Q' to quit the game
 */
public class UI {
    static TERenderer ter = new TERenderer();

    public static final int WIDTH = Engine.WIDTH;
    public static final int HEIGHT = Engine.HEIGHT;

    /** Width of the menu. */
    public static final int MENU_WIDTH = 30;

    /** Height of the menu. */
    public static final int MENU_HEIGHT = 30;
    StringBuilder stringB;

    /** Game to be play. */
    private static Game game;

    private static boolean readyToSaveNQuit = false;

    /** Performs action as follows:
     *  - 'N': starts a new game
     *  - 'L': loads a existing game
     *  - 'Q': quits the game
     */
    public static void mainMenuControl(InputSource inputSource) {
        mainMenu();
        //Fill out the rest for different inputs
        char key = 0;
        while (inputSource.possibleNextInput()) {
            key = upperNextKey(inputSource);
            if (key == 'N' || key == 'L' || key == 'Q') {
                break;
            }
        }
        if (key == 'N') {
            long seed = findSeed(inputSource);
            newGame(seed);
            playGame(inputSource);
        } else if (key == 'L') {
            loadGame(inputSource);
            playGame(inputSource);
        } else if (key == 'Q') {
            System.exit(0);
        }
    }

    /** Displaying main menu. */
    public static void mainMenu() {
        StdDraw.setCanvasSize(MENU_WIDTH * 16, MENU_HEIGHT * 16);
        StdDraw.setXscale(0, MENU_WIDTH);
        StdDraw.setYscale(0, MENU_HEIGHT);
        StdDraw.clear(Color.BLACK);

        Font headFont = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(headFont);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text((float) MENU_WIDTH / 2, 19, "CS61B: The Game");

        Font subFont = new Font("Monaco", Font.PLAIN, 20);
        StdDraw.setFont(subFont);
        StdDraw.text((float) MENU_WIDTH / 2, 12, "New Game (N)");
        StdDraw.text((float) MENU_WIDTH / 2, 10, "Load Game (L)");
        StdDraw.text((float) MENU_WIDTH / 2, 8, "Quit Game (Q)");

        StdDraw.show();
    }

    /** Loads the existing game from the save file.
     *  @source
     *  https://examples.
     *  javacodegeeks.com
     *  /core-java/io/file/how-to-read-an-object-from-file-in-java/
     */
    public static void loadGame(InputSource inputSource) {
        File saveGame = new File("./save_game.txt");
        if (!saveGame.exists()) {
            System.exit(0);
        }
        try {
            FileInputStream fis = new FileInputStream(saveGame);
            ObjectInputStream ois = new ObjectInputStream(fis);
            game = (Game) ois.readObject();
        } catch (IOException e) {
            System.out.println(e);
            System.exit(0);
        } catch (ClassNotFoundException e) {
            System.out.println("class not found");
            System.exit(0);
        }
        ter.initialize(WIDTH, HEIGHT);
    }

    /** Generates a new game associated with SEED. */
    public static void newGame(long seed) {
        ter.initialize(WIDTH, HEIGHT);
        game = new Game(seed);
    }

    /** Saves and quits the game. */
    public static void saveNQuit() {
        saveGame();
        System.exit(0);
    }

    /** Saves the game to file named "save_game.txt".
     *  @source https://examples.javacodegeeks.com
     *  /core-java/io/fileoutputstream/how-to-write-an-object-to-file-in-java/
     */
    public static void saveGame() {
        File saveGame = new File("./save_game.txt");
        try {
            if (!saveGame.exists()) {
                saveGame.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(saveGame, false);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(game);
        } catch (IOException e) {
            System.out.println(e);
            System.exit(0);
        }
    }

    /** Plays the game as follows:
     *  Avatar, denoted by a white @, can move around using
     *  the keys "W, S, A, D" for "Up, Down, Left, Right", respectively.
     *  AvatarBlue, denoted by a blue @, can move around using
     *  the keys "U, J, H, K" for "Up, Down, Left, Right", respectively.
     *  Press 'C' to see the entire world and press it again to see the limited world
     *  i.e. press 'C' to turn the light on or off.
     */
    public static void playGame(InputSource inputSource) {
        // we want the users able to press key anytime until it quit or saveNQuit
        while (true) {
            while (inputSource.possibleNextInput()) {
                if (StdDraw.hasNextKeyTyped()) {
                    char key = upperNextKey(inputSource);
                    if (key == 'W') {
                        game.move(key);
                    } else if (key == 'S') {
                        game.move(key);
                    } else if (key == 'A') {
                        game.move(key);
                    } else if (key == 'D') {
                        game.move(key);
                    }

                    if (key == 'U') {
                        game.move(key);
                    } else if (key == 'J') {
                        game.move(key);
                    } else if (key == 'H') {
                        game.move(key);
                    } else if (key == 'K') {
                        game.move(key);
                    }

                    if (key == 'C') {
                        game.switchLight();
                    }
                }
//                char key = upperNextKey(inputSource);
//                if (key == 'W') {
//                    game.move(key);
//                } else if (key == 'S') {
//                    game.move(key);
//                } else if (key == 'A') {
//                    game.move(key);
//                } else if (key == 'D') {
//                    game.move(key);
//                }
                hud();
                gameOver();
                ter.renderFrame(game.getWORLD());
            }
            break;
        }
        StdDraw.pause(1000);
        System.exit(0);
    }

    /** Returns the next key in uppercase with the exception that it is
     *  quit ('Q') or saveNQuit (':Q') which just perform the respective actions.
     */
    public static char upperNextKey(InputSource inputSource) {
        char key = Character.toUpperCase(inputSource.getNextKey());
        if (key == ':') {
            readyToSaveNQuit = true;
        } else if (readyToSaveNQuit) {
            if (key == 'Q') {
                saveNQuit();
            } else {
                readyToSaveNQuit = false;
            }
        }
        return key;
    }

    /** Finds and returns the seed from the INPUTSOURCE.
     *  i.e. "N123456SWASDAWQ" -> "123456"
     */
    public static long findSeed(InputSource inputSource) {
        enterSeed("");
        StringBuilder sb = new StringBuilder();
        while (inputSource.possibleNextInput()) {
            char key = upperNextKey(inputSource);
            if (Character.isDigit(key)) {
                sb.append(key);
                enterSeed(sb.toString());
            } else if (key == 'S' && sb.length() > 0) {
                break;
            }
        }
        String seedString = sb.toString();
        long seed = Long.parseLong(seedString);
        return seed;
    }

    /** Returns the game of the UI. */
    public static Game getGame() {
        return game;
    }

    /** Head-Up-Display (HUD): Description of the moused-over tile in the upper left corner. */
    public static void hud() {
        int x = (int) StdDraw.mouseX();
        int y = (int) StdDraw.mouseY();
        String tileDescription = "";
        if (x < WIDTH && y < HEIGHT) {
            tileDescription = "You see: " + game.getWORLD()[x][y].description();
        }
        String healthDescription = "Health: " + game.getHealth();
        String scoreDescription = "Score: " + game.getScore();
        StdDraw.setPenColor(Color.WHITE);
        Font font = new Font("Monaco", Font.BOLD, 16);
        StdDraw.setFont(font);
        StdDraw.textLeft(1.5, HEIGHT - 1, tileDescription);
        StdDraw.textLeft(1.5, HEIGHT - 2, healthDescription);
        StdDraw.textLeft(1.5, HEIGHT - 3, scoreDescription);
        StdDraw.show();
        StdDraw.pause(10);
    }

    /** Game over window display. */
    public static void gameOver() {
        if (game.getGameState() != 0) {
            StdDraw.clear(Color.BLACK);

            Font headFont = new Font("Monaco", Font.BOLD, 30);
            StdDraw.setFont(headFont);
            StdDraw.setPenColor(Color.WHITE);

            String lostDescription = "Game over! You lost.";
            String winDescription = "Congratulations! You win!";

            if (game.getGameState() == -1) {
                StdDraw.text((float) WIDTH / 2, (float) HEIGHT / 2, lostDescription);
            } else if (game.getGameState() == 1) {
                StdDraw.text((float) WIDTH / 2, (float) HEIGHT / 2, winDescription);
            }

            StdDraw.show();
            StdDraw.pause(2500);
            System.exit(0);
        }
    }

    /** Enter seed window display with SEED. */
    public static void enterSeed(String seed) {
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.setFont(new Font("Monaco", Font.PLAIN, 20));
        StdDraw.text((float) MENU_WIDTH / 2, (float) MENU_HEIGHT / 2,
                "Enter a seed and press (S) to play");
        StdDraw.setFont(new Font("Monaco", Font.PLAIN, 18));
        StdDraw.text((float) MENU_WIDTH / 2, (float) MENU_HEIGHT / 2 - 2, seed);
        StdDraw.show();
    }
}
