package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.io.Serializable;


public class Room implements Comparable<Room>, Serializable {

    /** Height of the room. */
    private int height;

    /** Width of the room. */
    private int width;

    /** Bottom left corner of the room. */
    private Position start;

    /** Center of the room. */
    private Position center;
    private int count;

    /** Room constructor takes in the HEIGHT, WIDTH, and START position
     *  of the room. */
    public Room(int height, int width, Position start) {
        this.height = height;
        this.width = width;
        this.start = start;
        this.center = new Position(start.getX() + (width / 2), start.getY() + (height / 2));
        count = 0;
    }

    /** Adds this room to WORLD. */
    public boolean addRoom(TETile[][] world) {

        int x = this.start.getX();
        int y = this.start.getY();

        if (available(world, this.start, this.width, this.height)) {
            for (int i = x + 1; i <= (x + this.width); i++) {
                for (int j = y + 1; j < (y + this.height); j++) {
                    world[i][j] = Tileset.FLOOR; //floors
                    world[i][y] = Tileset.WALL; //bottom wall
                    world[i][y - 1 + this.height] = Tileset.WALL; //top wall
                }
            }
            // vertical
            for (int j = y + 1; j < (y + this.height); j++) {
                world[x + 1][j] = Tileset.WALL; //left side wall
                world[x + this.width][j] = Tileset.WALL; //right side wall
            }

            return true;
        }
        return false;
    }

// Start of Addition for Phase 2
//    public Point getCenter(Room room) {
//        int xPos = Math.abs((room.start.getX()  + (room.width / 2)));
//        int yPos = Math.abs((room.start.getY()  + (room.height / 2)));
//
//        return new Point(xPos, yPos);
//    }

//    public void addHallway(HashMap<Integer, Point> roomMap, TETile[][] world) {
//        for (int i = 0; i < count - 1; i++) {
//            Point room1 = roomMap.get(i);
//            Point room2 = roomMap.get(i + 1);
//
//            while (room1.getX() < room2.getX()) {
//                horizontalHallway(room1, room2, world);
//            }
//
//            while (room1.getX() > room2.getX()) {
//                horizontalHallway(room2, room1, world);
//            }
////            while (room1.getY() < room2.getY()) {
////                verticalHallway(room1, room2, world);
////            }
////            while (room1.getY() > room2.getY()) {
////                verticalHallway(room2, room1, world);
////            }
//        }
//    }
//
//    public void horizontalHallway(Point room1, Point room2, TETile[][] world) {
//        while(room1.getX() < room2.getX()) { // room1 to the left
//            if (world[(int) room1.getX()][(int) room1.getY()] == Tileset.FLOOR) {
//                for (int i = (int) room1.getX(); i < room2.getX(); i++) {
//                    world[i][(int) room1.getY()] = Tileset.FLOWER;
//                }
//            }
//
//        }
//
//
//    }

//    public void verticalHallway(Point room1, Point room2, TETile[][] world) {
//        int x = room1.start.getX();
//        int y = room2.start.getY();
//        if (available(world, room1.start, room1.width, room1.height)) {
//            int roomMaxWidth = 3;
//            int roomMaxHeight = 5;
//
//            for (int i = x + 1; i <= (x + room1.width); i++) {
//                for (int j = y + 1; j <= (y + room2.height); j++) {
//                    world[i][j] = Tileset.FLOOR; //floors
//                    world[i][y] = Tileset.WALL; //bottom wall
//                    world[i][y - 1 + roomMaxHeight ] = Tileset.WALL; //top wall
//                }
//            }
//            // vertical
//            for (int j = y + 1; j <= (y + room2.height); j++) {
//                world[x + 1][j] = Tileset.WALL; //left side wall
//                world[x + room1.width][j] = Tileset.WALL; //right side wall
//            }
//        } else {
//            return;
//        }
//    }


// End of Addition for Phase 2

    /** Returns TURE if it's possible to build a room with WIDTH and HEIGHT, starting at START.
     * @param start the bottom left position where the room should starting building
     * @param width the width of the room
     * @param height the height of the room
     */
    public static boolean available(TETile[][] world, Position start, int width, int height) {
        if (((start.getX() + width) >= 80) || ((start.getY() + height) >= 30)) { // out of bounds
            return false;
        } else {
            for (int i = start.getX(); i < start.getX() + width; i++) {
                for (int j = start.getY(); j < start.getY() + height; j++) {
                    if ((world[i][j] == Tileset.WALL)) {
                        return false;
                    } else if (((world[i + 1][j] == Tileset.WALL))) {
                        return false;
                    }
                }
            }

            for (int i = start.getX(); i < start.getX() + width; i++) {
                for (int j = start.getY(); j < start.getY() + height; j++) {
                    if ((world[i][j] == Tileset.NOTHING)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /** Returns the start position of the room. */
    public Position getStart() {
        return this.start;
    }

    /** Returns the center position of the room. */
    public Position getCenter() {
        return this.center;
    }

    @Override
    /** Compares this room to OTHERROOM based on the rooms' centers' x-coordinates.
     *  @returns 0 if two rooms lie on the same x-coordinate; a positive number if this room
     *  is at the right of OTHERROOM; a negative number if this room is at the left.
     * @param otherRoom the room to compare to
     */
    public int compareTo(Room otherRoom) {
        Position otherCenter = otherRoom.getCenter();
        int otherX = otherCenter.getX();
        int thisX = this.getCenter().getX();

//        if ((otherCenter.getX() - this.center.getX()) > 0) {
//            return -1;
//        } else if ((otherCenter.getX() - this.center.getX()) < 0) {
//            return 1;
//        } else {
//            return 0;
//        }

        return Integer.compare(thisX, otherX);
    }
}
