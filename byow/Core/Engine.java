package byow.Core;

import byow.InputDemo.InputSource;
import byow.InputDemo.KeyboardInputSource;
import byow.InputDemo.StringInputDevice;
import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;

public class Engine {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;

//    /** Width of the menu. */
//    public static final int MENU_WIDTH = 30;
//
//    /** Height of the menu. */
//    public static final int MENU_HEIGHT = 30;
//    StringBuilder stringB;
//
//    /** Game to be play. */
//    private Game game;
//
//    private boolean readyToSaveNQuit = false;

    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {
        InputSource inputSource = new KeyboardInputSource();
        UI.mainMenuControl(inputSource);
    }

    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     *
     * Recall that strings ending in ":q" should cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     *
     * In other words, both of these calls:
     *   - interactWithInputString("n123sss:q")
     *   - interactWithInputString("lww")
     *
     * should yield the exact same world state as:
     *   - interactWithInputString("n123sssww")
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] interactWithInputString(String input) {

//        stringB = new StringBuilder();
//        String seed = "";
//        if (input == null) {
//            seed = "";
//        }
//        if (input.charAt(0) == 'N' || input.charAt(0) == 'n' && input != null) {
//            for (int i = 1; i < input.length(); i++) {
//                if (input.charAt(i) == 'S' || input.charAt(i) == 's') {
//                    break;
//                } else {
//                    seed += input.charAt(i);
//                }
//            }
//        }
//        // seed = stringB.toString();
//        TERenderer ter = new TERenderer(); ///
//        ter.initialize(WIDTH, HEIGHT); ///
//
//        long item = Long.parseLong(seed);
//        Random r = new Random(item);
//        World world = new World(r, Engine.WIDTH, Engine.HEIGHT);
//        TETile[][] finalWorldFrame = world.getWORLD();
//
//        ter.renderFrame(finalWorldFrame); ///
//
//        return finalWorldFrame;
        InputSource inputSource = new StringInputDevice(input);
        UI.mainMenuControl(inputSource);
        TETile[][] finalWorldFrame = UI.getGame().getWORLD();
        return finalWorldFrame;
    }

//    /** Performs action as follows:
//     *  - 'N': starts a new game
//     *  - 'L': loads a existing game
//     *  - 'Q': quits the game
//     */
//    public void mainMenuControl(InputSource inputSource) {
//        mainMenu();
//
//        char key = 1;
//        while (inputSource.possibleNextInput()) {
//            key = upperNextKey(inputSource);
//            if (key == 'N' || key == 'L' || key == 'Q') {
//                break;
//            }
//        }
//        if (key == 'N') {
//            long seed = findSeed(inputSource);
//            newGame(seed);
//            playGame(inputSource);
//        } else if (key == 'L') {
//            loadGame(inputSource);
//            playGame(inputSource);
//        } else if (key == 'Q') {
//            System.exit(0);
//        }
//    }
//
//    /** Displaying main menu. */
//    public void mainMenu() {
//        byow.Core.StdDraw.setCanvasSize(MENU_WIDTH * 16, MENU_HEIGHT * 16);
//        byow.Core.StdDraw.setXscale(0, MENU_WIDTH);
//        byow.Core.StdDraw.setYscale(0, MENU_HEIGHT);
//        byow.Core.StdDraw.clear(Color.BLACK);
//
//        Font headFont = new Font("Monaco", Font.BOLD, 30);
//        byow.Core.StdDraw.setFont(headFont);
//        byow.Core.StdDraw.setPenColor(Color.WHITE);
//        byow.Core.StdDraw.text((float) Engine.MENU_WIDTH / 2, 19, "CS61B: The Game");
//
//        Font subFont = new Font("Monaco", Font.PLAIN, 20);
//        byow.Core.StdDraw.setFont(subFont);
//        byow.Core.StdDraw.text((float) Engine.MENU_WIDTH / 2, 12, "New Game (N)");
//        byow.Core.StdDraw.text((float) Engine.MENU_WIDTH / 2, 10, "Load Game (L)");
//        byow.Core.StdDraw.text((float) Engine.MENU_WIDTH / 2, 8, "Quit Game (Q)");
//
//        byow.Core.StdDraw.show();
//    }
//
//    public void loadGame(InputSource inputSource) {
//        File SAVEGAME = new File("./save_game.txt");
//        if (!SAVEGAME.exists()) {
//            return;
//        }

//        try {
//            FileInputStream fis = new FileInputStream(SAVEGAME);
//            ObjectInputStream ois = new ObjectInputStream(fis);
//            this.game = (Game) ois.readObject();
//        } catch (IOException e) {
//            System.out.println(e);
//            System.exit(0);
//        } catch (ClassNotFoundException e) {
//            System.out.println("class not found");
//            System.exit(0);
//        }
//        ter.initialize(WIDTH, HEIGHT);
//    }
//
//    /** Generates a new game associated with SEED. */
//    public void newGame(long seed) {
//        ter.initialize(WIDTH, HEIGHT);
//        game = new Game(seed);
//    }
//
//    /** Saves and quits the game. */
//    public void saveNQuit() {
//        saveGame();
//        System.exit(0);
//    }


    /** For testing purpose. */
    public static void main(String[] args) {
        Engine eng = new Engine();
        eng.interactWithInputString("LWWWDDD");
    }
}
