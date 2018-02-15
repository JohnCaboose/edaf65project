package common.model;

public enum Direction {
	UP, DOWN, LEFT, RIGHT, NONE;
	
	private Direction opposite;

    static {
        UP.opposite = DOWN;
        DOWN.opposite = UP;
        RIGHT.opposite = LEFT;
        LEFT.opposite = RIGHT;
    }

    public Direction getOppositeDirection() {
        return opposite;
    }
}
