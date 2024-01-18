package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.io.Serializable;
import java.util.*;

/** Class for the world generation. */
public class World implements Serializable {

    /** Width of the world. */
    public static final int WIDTH = 80;

    /** Height of the world. */

    public static final int HEIGHT = 30;

    /** Random object of the world. */
    private final Random RANDOM;

    /** World of the class. */
    private final TETile[][] WORLD;

    /** All rooms in the world. */
    private List<Room> rooms;

    /** World constructor takes in a RANDOM. */
    public World(Random random) {
        this.RANDOM = random;
        this.WORLD = new TETile[WIDTH][HEIGHT];
        this.rooms = new ArrayList<>();
        generateWorld();
    }

    /** Generates the world as follows:
     *  First, fill world with NOTHING.
     *  Second, build rooms at random positions.
     *  Third, connect all rooms with floors.
     *  Last, build walls around all hallway floors.
     */
    public void generateWorld() {
        worldInitializer();
        buildRooms(4, 12);
        for (int i = 0; i < (rooms.size() - 1); i += 1) {
            connect(rooms.get(i), rooms.get(i + 1));
        }
        makeWall();
        fillGap();
        fillLava();
    }

    /** Fills NOTHING with WATER. */
    public void fillWater() {
        for (int i = 0; i < WORLD.length; i++) {
            for (int j = 0; j < WORLD[0].length; j++) {
                if (WORLD[i][j] == Tileset.NOTHING) {
                    WORLD[i][j] = Tileset.WATER;
                }
            }
        }
    }

    /** Fills NOTHING with LAVA. */
    public void fillLava() {
        for (int i = 0; i < WORLD.length; i++) {
            for (int j = 0; j < WORLD[0].length; j++) {
                if (WORLD[i][j] == Tileset.NOTHING) {
                    WORLD[i][j] = Tileset.LAVA;
                }
            }
        }
    }

    /** Connects the two rooms. Here is how it works:
     *  connect the centers of the two rooms with floors.
     *  First starting the first center, reach to the two same x-level
     *  (r1 is guaranteed to be on the left of r2 because it's in sorted list),
     *  then make a turn to the respective y-level.
     *  @params r1, r2 rooms to be connected
     */
    public void connect(Room r1, Room r2) {
        Position center1 = r1.getCenter();
        Position center2 = r2.getCenter();

        int currX = center1.getX();
        int currY = center1.getY();
        while (currX <= center2.getX()) {
            WORLD[currX][currY] = Tileset.FLOOR;
            currX += 1;
        }
        if (currY <= center2.getY()) {
            while (currY <= center2.getY()) {
                WORLD[currX][currY] = Tileset.FLOOR;
                currY += 1;
            }
        } else if (currY >= center2.getY()) {
            while (currY >= center2.getY()) {
                WORLD[currX][currY] = Tileset.FLOOR;
                currY -= 1;
            }
        }
    }

    /** Builds random numbers of rooms with random size,
     *  between MINSIZE and MAXSIZE, at random position. */
    public void buildRooms(int minSize, int maxSize) {

        // builds rooms at random places and stores them in rooms
        for (int i = 0; i < Math.max(WIDTH, HEIGHT); i++) {
            /** @Source Stackoverflow for how to bound random numbers
             * https://stackoverflow.com/questions/5271598/
             * java-generate-random-number-between-two-given-values **/
            int h = Math.abs(RANDOM.nextInt(maxSize - minSize) + minSize);
            int w = Math.abs(RANDOM.nextInt(maxSize - minSize) + minSize);
            Position start = new Position(RandomUtils.uniform(RANDOM, WIDTH),
                    RandomUtils.uniform(RANDOM, HEIGHT));
            Room room = new Room(h, w, start);
            if (room.addRoom(WORLD)) {
                rooms.add(room);
            }
        }

        // sort the rooms from left to right
        rooms.sort(Room::compareTo);
    }

    /** Builds walls around all floors. */
    public void makeWall() {
        for (int i = 0; i < WORLD.length; i++) {
            for (int j = 0; j < WORLD[0].length; j++) {
                // checks if it's a floor and fill walls round it
                if (WORLD[i][j] == Tileset.FLOOR) {
                    if (WORLD[i + 1][j] == Tileset.NOTHING) {
                        WORLD[i + 1][j] = Tileset.WALL;
                    }
                    if (WORLD[i - 1][j] == Tileset.NOTHING) {
                        WORLD[i - 1][j] = Tileset.WALL;
                    }
                    if (WORLD[i][j + 1] == Tileset.NOTHING) {
                        WORLD[i][j + 1] = Tileset.WALL;
                    }
                    if (WORLD[i][j - 1] == Tileset.NOTHING) {
                        WORLD[i][j - 1] = Tileset.WALL;
                    }
                }
            }
        }
    }

    /** Fills the gaps of wall with a wall. */
    public void fillGap() {
        for (int i = 0; i < WORLD.length; i++) {
            for (int j = 0; j < WORLD[0].length; j++) {
                // Check if it's a wall
                if (WORLD[i][j] == Tileset.WALL) {
                    // check if it has an gap and fill
                    if (WORLD[i - 1][j] == Tileset.FLOOR
                            && WORLD[i][j + 1] == Tileset.NOTHING) {
                        WORLD[i][j + 1] = Tileset.WALL;
                    }
                    // check if it has an gap and fill
                    if (WORLD[i - 1][j] == Tileset.FLOOR
                            && WORLD[i][j - 1] == Tileset.NOTHING) {
                        WORLD[i][j - 1] = Tileset.WALL;
                    }
                }
            }
        }
    }

    /** Initiates a world filled with NOTHING. */
    private void worldInitializer() {
        for (int i = 0; i < WORLD.length; i++) {
            for (int j = 0; j < WORLD[0].length; j++) {
                WORLD[i][j] = Tileset.NOTHING;
            }
        }
    }

    /** Connecting all the rooms in the given world through hallways and door. */
    public void connectRooms(TETile[][] world) {
//        if (rooms.isEmpty()) {
//            return;
//        }
//        Position currPos = rooms.get(0).getStart();
//        rooms.remove(0);
//
//        while (!rooms.isEmpty()) {
//            int x = currPos.getX();
//            int y = currPos.getY();
//            if (world[x][y] == Tileset.WALL) {
//                makeDoor(x, y, world);
//            } else if (world[x][y] == Tileset.FLOOR) {
//                rooms.remove(0);
//            } else {
//                // top hallway
//                world[x][y+1] = Tileset.WALL;
//                // bot hallway
//                world[x][y-1] = Tileset.WALL;
//            }
//            currPos = new Position(x+7, y);
//        }
        for (int i = 0; i < WORLD.length - 1; i++) {
            for (int j = 1; j < WORLD[0].length - 1; j++) {
                if (world[i][j] == Tileset.WALL) {
                    world[i][j] = Tileset.WALL;
                } else if (world[i][j] == Tileset.NOTHING) {
                    world[i + 1][j + 1] = Tileset.WALL;
                    world[i + 1][j - 1] = Tileset.WALL;
                }
            }
        }
    }

    /** Creates the first room with random size at a random position. */
    public Room makeInitRoom() {
        Position start = new Position(RANDOM.nextInt(WIDTH - 9), RANDOM.nextInt(WIDTH - 9));
        int height = RANDOM.nextInt(7) + 4;
        int width = RANDOM.nextInt(7) + 4;
        return new Room(height, width, start);
    }

    /** Makes a door at the given position of the wall of the given world. */
    public void makeDoor(int x, int y, TETile[][] world) {
        world[x][y] = Tileset.UNLOCKED_DOOR;
    }

    /** Returns the WORLD of the world class. */
    public TETile[][] getWORLD() {
        return this.WORLD;
    }

    /** Returns the rooms of the world. */
    public List<Room> getRooms() {
        return this.rooms;
    }

    /** Uses to test world generation. */
    public static void main(String[] args) {
        TERenderer teRenderer = new TERenderer();
        teRenderer.initialize(Engine.WIDTH, Engine.HEIGHT);

        long seed = 68321;
        Random r = new Random(seed);
        World newWorld = new World(r);

        teRenderer.renderFrame(newWorld.WORLD);
    }
}
