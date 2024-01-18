package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.io.Serializable;
import java.util.List;
import java.util.Random;

/** Class for game playing. Details as follows:
 *  - Players:
 *      - Avatar, denoted by a white @, can move around using
 *        the keys "W, S, A, D" for "Up, Down, Left, Right", respectively.
 *      - AvatarBlue, denoted by a blue @, can move around using
 *        the keys "U, J, H, K" for "Up, Down, Left, Right", respectively.
 *  - Lost:
 *      - If either player run into the wall, the health is reduced by 1;
 *        when the health runs out, the players loss the game.
 *  - Win:
 *      - If the players successfully collected all the coins in the world,
 *        they win the game.
 */
public class Game implements Serializable {

    /** Random object of the game. */
    private Random RANDOM;

    /** World object of the game. */
    private World world;

    /** World of the game. */
    private TETile[][] WORLD;

    /** Position of the avatar. */
    private Position avatar;

    /** Position of the blue avatar. */
    private Position avatarBlue;

    /** Total number of the coin. */
    private int coins;

    /**Avatar's health*/
    private int health;

    /**updates total coins the avatar collected */
    private int score;

    /*Time to Update HUD*/
    private Boolean updateHUD;

    /** gameOver Boolean to check health == 0 */
    private Boolean gameOver;

    /** Indicates the state of the game: -1 = lost, 0 = on, 1 = win */
    private int gameState;

    /** Lights on or not. */
    private boolean lightsOn;

    /** The world the players see. */
    private TETile[][] limitedWorld;

    /** Game constructor takes in a SEED and saves the current state of
     *  the game associated with that SEED. */
    public Game(long seed) {
        this.RANDOM = new Random(seed);
        this.world = new World(RANDOM);
        this.WORLD = world.getWORLD();
        //        Random xRand = new Random();
        setAvatar();
        setCoins();
        gameState = 0;
        score = 0;
        lightsOn = false;
    }

    /** Makes a new world. */
    public void makeNewWorld() {
        this.RANDOM = new Random(RANDOM.nextInt());
        World newWorld = new World(RANDOM);
    }

    /** Players can only see the world within LIMIT.
     *  If LIMIT <= 0, then the player can see the whole world.
     */
    public void limitWorld(int limit) {
        int left1, right1, top1, bottom1;
        int left2, right2, top2, bottom2;
        if (limit <= 0) {
            left1 = 0;
            right1 = Engine.WIDTH - 1;
            top1 = Engine.HEIGHT - 1;
            bottom1 = 0;
            left2 = 0;
            right2 = Engine.WIDTH - 1;
            top2 = Engine.HEIGHT - 1;
            bottom2 = 0;
        } else {
            left1 = avatar.getX() - limit;
            right1 = avatar.getX() + limit;
            top1 = avatar.getY() + limit;
            bottom1 = avatar.getY() - limit;
            left2 = avatarBlue.getX() - limit;
            right2 = avatarBlue.getX() + limit;
            top2 = avatarBlue.getY() + limit;
            bottom2 = avatarBlue.getY() - limit;
        }

        for (int i = 0; i < Engine.WIDTH; i++) {
            for (int j = 0; j < Engine.HEIGHT; j++) {
                if (i >= left1 && i <= right1 && j <= top1 && j >= bottom1) {
                    limitedWorld[i][j] = WORLD[i][j];
                } else if (i >= left2 && i <= right2 && j <= top2 && j >= bottom2) {
                    limitedWorld[i][j] = WORLD[i][j];
                } else {
                    limitedWorld[i][j] = Tileset.NOTHING;
                }
            }
        }
    }

    /** Spawns the avatar at the center of the leftest room. */
    public void setAvatar() {
        // center of leftest room
        List<Room> rooms = world.getRooms();
        Position avaPos = rooms.get(0).getCenter();
        Position avaBluePos = rooms.get(rooms.size() - 1).getCenter();
        replaceTile(avaPos, Tileset.AVATAR);
        replaceTile(avaBluePos, Tileset.AVATAR_BLUE);
        avatar = avaPos;
        avatarBlue = avaBluePos;
        health = 2;
    }


    /** Spawns the coin at the center of the rightest room. */
    public void setCoins() {
        // center of the rightest room
        List<Room> rooms = world.getRooms();
        // rooms.size() - 1
        for (int i = 1; i < rooms.size() - 1; i++) {
            Position coinPos = rooms.get(i).getCenter();
            replaceTile(coinPos, Tileset.COIN);
            coins += 1;
        }
    }

    /** The avatar will be collecting the most coins to get a high score */
    public void highestScore() {

    }

    /** Updates avatar's score by +/-1 based off of boolean update. */
    public void updateScore(Boolean scoreBoolean) {
        if (scoreBoolean) {
            score += 1;
        } else {
            score -= 1;
        }
    }

    /** Updates avatar's health by +/-1 base off of boolean update. */
    public void updateHealth(Boolean healthBoolean) {
        if (healthBoolean) {
            health += 1;
        } else {
            health -= 1;
        }
    }

    /** Ends the game if the Avatar Health = 0*/
    public void endGame() {
        if (health == 0) {
            gameState = -1;
        }
    }

//    public void drawFrame(String s) {
//        byow.Core.StdDraw.clear(Color.BLACK);
//
//        if (!gameOver) {
//            if (updateHUD) {
//                byow.Core.StdDraw.textLeft( Engine.WIDTH / 8, Engine.HEIGHT / 8, "Type");
//            } else {
//                byow.Core.StdDraw.textLeft(Engine.WIDTH / 8, Engine.HEIGHT / 88, "Watch!");
//            }
//            byow.Core.StdDraw.line(0, Engine.HEIGHT -5, Engine.WIDTH, Engine.HEIGHT - 5);
//        }
//        byow.Core.StdDraw.setFont(new Font ("Monaco", Font.BOLD, 30));
//        byow.Core.StdDraw.text(Engine.WIDTH / 2, Engine.HEIGHT / 2, s);
//        byow.Core.StdDraw.setPenColor(Color.WHITE);
//        byow.Core.StdDraw.show();
//    }

    /** Generate Random coordinates uniformly to place TETile */
    public TETile generateCoordinates() {

        /** Rand X/ Width coordinate*/
//        Random xRand = new Random();
        int xRand = RandomUtils.uniform(this.RANDOM, 30);
        /** Rand Y/Height coordinate*/
//        Random yRand = new Random();
        int yRand = RandomUtils.uniform(this.RANDOM, 30);

        TETile ranTile = WORLD[xRand][yRand];
        if (!ranTile.description().equals("floor")) {
            generateCoordinates();
        }
        return ranTile;
    }


    /** Generates Random coins within bounds */
    public void generateRandCoins() {

    }


    /** Replaces POSITION with TILE. */
    public void replaceTile(Position position, TETile tile) {
        WORLD[position.getX()][position.getY()] = tile;
    }

    //** Moves the Avatar Efficiently, reduces code */
    public void moveAvatarHelper(Position targetPos) {
        replaceTile(avatar, Tileset.FLOOR);
        replaceTile(targetPos, Tileset.AVATAR);
        avatar = targetPos;
    }

    /** increase health and score and moves the avatar*/
    private void increaseAndMove(Position targetPos) {
        moveAvatarHelper(targetPos);
        updateHealth(true);
        updateScore(true);
    }

    /** decrease health and score and moves the avatar*/
    private void decreaseAndMove(Position targetPos) {
        moveAvatarHelper(targetPos);
        updateHealth(false);
        updateScore(false);
    }

    /** Moves the PLAYER to the desired position, TARGETPOS.
     *  If the target position is a wall, avatar remains at the same position.
     */
    public void moveAvatar(Position targetPos, String player) {
        if (player.equals("avatar")) {
            TETile currTile = WORLD[avatar.getX()][avatar.getY()];
            TETile nextTile = WORLD[targetPos.getX()][targetPos.getY()];


            // make sure the game is on
            if (gameState == 0) {
                // make sure it's not a wall, using description instead because of bug before
                if (!nextTile.description().equals("wall")) {
                    if (nextTile.description().equals("player 2")) {
                        return;
                    }
                    if (nextTile.description().equals("coin")) {
                        score += 1;
                        if (score >= coins) {
                            gameState = 1;
                        }
                    }
                    replaceTile(avatar, Tileset.FLOOR);
                    replaceTile(targetPos, Tileset.AVATAR);
                    avatar = targetPos;
                } else {
                    if (health <= 1) {
                        gameState = -1;
                    }
                    health -= 1;
                }
            }
        } else if (player.equals("avatarBlue")) {
            TETile currTile = WORLD[avatarBlue.getX()][avatarBlue.getY()];
            TETile nextTile = WORLD[targetPos.getX()][targetPos.getY()];


            // make sure the game is on
            if (gameState == 0) {
                // make sure it's not a wall, using description instead because of bug before
                if (!nextTile.description().equals("wall")) {
                    if (nextTile.description().equals("player 1")) {
                        return;
                    }
                    if (nextTile.description().equals("coin")) {
                        score += 1;
                        if (score >= coins) {
                            gameState = 1;
                        }
                    }
                    replaceTile(avatarBlue, Tileset.FLOOR);
                    replaceTile(targetPos, Tileset.AVATAR_BLUE);
                    avatarBlue = targetPos;
                } else {
                    if (health <= 1) {
                        gameState = -1;
                    }
                    health -= 1;
                }
            }
        }
//        updateHealth(false);
    }

    /** Moves avatars as follows:
     *  Pressed "W, S, A, D" -> avatar moves "Up, Down, Left, Right", respectively.
     *  Pressed "U, J, H, K" -> avatarBlue moves "Up, Down, Left, Right", respectively.
     */
    public void move(char key) {
        if (key == 'W') {
            moveAvatar(avatar.north(), "avatar");
        } else if (key == 'S') {
            moveAvatar(avatar.south(), "avatar");
        } else if (key == 'A') {
            moveAvatar(avatar.west(), "avatar");
        } else if (key == 'D') {
            moveAvatar(avatar.east(), "avatar");
        }

        if (key == 'U') {
            moveAvatar(avatarBlue.north(), "avatarBlue");
        } else if (key == 'J') {
            moveAvatar(avatarBlue.south(), "avatarBlue");
        } else if (key == 'H') {
            moveAvatar(avatarBlue.west(), "avatarBlue");
        } else if (key == 'K') {
            moveAvatar(avatarBlue.east(), "avatarBlue");
        }
    }

    /** Changes the LIMITEDWORLD to WORLD. */
    public void showWorld() {
        this.limitedWorld = WORLD;
    }

    /** Returns the WORLD of the game depending whether the light is on or off.
     *  If the light is off, returns the limited world;
     *  else, returns the full world. */
    public TETile[][] getWORLD() {
        limitedWorld = new TETile[Engine.WIDTH][Engine.HEIGHT];
        limitWorld(3);
        if (lightsOn) {
            return this.WORLD;
        } else {
            return this.limitedWorld;
        }
    }

    /** Returns the health of the game. */
    public int getHealth() {
        return this.health;
    }

    /** Returns the state of the game. */
    public int getGameState() {
        return this.gameState;
    }

    /** Returns the score of the game. */
    public int getScore() {
        return this.score;
    }

//    /** Generate and returns the limited World. */
//    public TETile[][] getLimitedWorld() {
//        limitedWorld = new TETile[Engine.WIDTH][Engine.HEIGHT];
//        limitWorld(3);
//        if (lightsOn) {
//            return this.WORLD;
//        } else {
//            return this.limitedWorld;
//        }
//    }

    /** Switch the light on or off. */
    public void switchLight() {
        if (lightsOn) {
            lightsOn = false;
        } else {
            lightsOn = true;
        }
    }

    /** Mouse detection: check if currTile == newTile and updates */
    public Boolean updateTile() {
        return false;
    }

    /** Rendering the coordinates. */
    public void renderingMouseDetection() {
//        while(mouse isn't moving)

    }

    /** For testing purpose. */
    public static void main(String[] args) {
        TERenderer teRenderer = new TERenderer();
        teRenderer.initialize(Engine.WIDTH, Engine.HEIGHT);

        long seed = 68321;
        Game g = new Game(seed);

        teRenderer.renderFrame(g.WORLD);
    }
}
