
package byow.Core;

import java.io.Serializable;

/** Class for position with (x, y) coordinates. */
public class Position implements Serializable {
    private int x;
    private int y;

    /** Position constructor takes in X and Y. */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /** Returns the X-coordinate. */
    public int getX() {
        return this.x;
    }

    /** Returns the Y-coordinate. */
    public int getY() {
        return this.y;
    }

    /** Returns the position to the north. */
    public Position north() {
        return new Position(x, y + 1);
    }

    /** Returns the position to the south. */
    public Position south() {
        return new Position(x, y - 1);
    }

    /** Returns the position to the west. */
    public Position west() {
        return new Position(x - 1, y);
    }

    /** Returns the position to the east. */
    public Position east() {
        return new Position(x + 1, y);
    }
}
