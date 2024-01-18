package byow.lab13;

import byow.Core.RandomUtils;
import byow.Core.StdDraw;
// import edu.princeton.cs.introcs.byow.Core.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;

public class MemoryGame {
    /** The width of the window of this game. */
    private int width;
    /** The height of the window of this game. */
    private int height;
    /** The current round the user is on. */
    private int round;
    /** The Random object used to randomly generate Strings. */
    private Random rand;
    /** Whether or not the game is over. */
    private boolean gameOver;
    /** Whether or not it is the player's turn. Used in the last section of the
     * spec, 'Helpful UI'. */
    private boolean playerTurn;
    /** The characters we generate random Strings from. */
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    /** Encouraging phrases. Used in the last section of the spec, 'Helpful UI'. */
    private static final String[] ENCOURAGEMENT = {"You can do this!", "I believe in you!",
                                                   "You got this!", "You're a star!", "Go Bears!",
                                                   "Too easy for you!", "Wow, so impressive!"};

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please enter a seed");
            return;
        }

        long seed = Long.parseLong(args[0]);
        MemoryGame game = new MemoryGame(40, 40, seed);
        game.startGame();


    }

    public MemoryGame(int width, int height, long seed) {
        /* Sets up byow.Core.StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();

        //TODO: Initialize random number generator
    }

    public String generateRandomString(int n) {
        //TODO: Generate random string of letters of length n
        String generatedString = "";
        for (int i = 0; 1 < n; i++) {
            int randomInt = rand.nextInt(26);
//            int index = RandomUtils.uniform(rand, 26);
            generatedString += CHARACTERS[randomInt];
        }
        return generatedString;
    }

    public void drawFrame(String s) {
        //TODO: Take the string and display it in the center of the screen
        //TODO: If game is not over, display relevant game information at the top of the screen
        StdDraw.clear(Color.BLACK);

        if (!gameOver) {
            if (playerTurn) {
                StdDraw.textLeft(width / 8, height / 8, "Type");
            } else {
                StdDraw.textLeft(width / 8, height / 8, "Watch!");
            }
            StdDraw.line(0, height -5, width, height - 5);
        }
        StdDraw.setFont(new Font ("Monaco", Font.BOLD, 30));
        StdDraw.text(width / 2, height / 2, s);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.show();
    }

    public void flashSequence(String letters) {
        //TODO: Display each character in letters, making sure to blank the screen between letters
        for (int i = 0; i < letters.length(); i++) {
            drawFrame(letters.substring(i, i + 1));
            StdDraw.pause(1000);
            drawFrame(""); //clears my string call on empty string
            StdDraw.pause(500);
            playerTurn = false;
        }
        playerTurn = true; //we are typing
    }

    public String solicitNCharsInput(int n) {
        //TODO: Read n letters of player input
        //TODO: Read n letters of player input
        StringBuilder built = new StringBuilder();
        //StringBuilder vs String
        // String += ___ create new string and append it since it immutable
        //StringBuilder are mutable
        while (built.length() < n) {
            if (StdDraw.hasNextKeyTyped()) { //do i have potential keys to process
                built.append(StdDraw.nextKeyTyped()); //returns the next key type
                //display appeared center on string
                drawFrame(built.toString());
            }
        }
        return built.toString();
    }

    public void startGame() {
        //TODO: Set any relevant variables before the game starts
        //TODO: Set any relevant variables before the game starts
        int round = 1;
        String randomString = "";
        String userInput = "";
        while(randomString.equals(userInput)) {
            drawFrame("Round: " + round);
            randomString = generateRandomString(round);
            flashSequence(randomString);
            userInput = solicitNCharsInput(randomString.length());
            round++;
        }
        drawFrame(" Game Over! You made it to round: " + round);
    }
}


