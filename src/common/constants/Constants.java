package common.constants;

public class Constants {

	/**
	 * Defined as the amount of tiles on the play field in the horizontal
	 * direction.
	 */
	public static final int BOARD_WIDTH = 24;

	/**
	 * Defined as the amount of tiles on the play field in the vertical
	 * direction.
	 */
	public static final int BOARD_HEIGHT = 24;

	/** Defined as the time difference between any two given server ticks. */
	public static final int MILLIS_PER_STATE_FRAME = 500;

	/**
	 * The amount of ticks that are "negative" before the game starts to make it
	 * count for five seconds.
	 */
	public static final int TICKS_UNTIL_ZERO = 5000 / MILLIS_PER_STATE_FRAME;

	/**
	 * Defined as the frequency at which all snakes will grow. This value is
	 * divisible by the server tick frequency, i.e.
	 * <code>Constants.MILLIS_PER_STATE_FRAME</code>.
	 */
	public static final int MILLIS_BETWEEN_SNAKE_GROWTHS = MILLIS_PER_STATE_FRAME * 10;

	/**
	 * Defines what player should get what color on their snake. The first
	 * element of this array denotes the color of player one, the second element
	 * for player two and so on.
	 */
	public static final String[] playerColors = { "blue", "magenta", "pink", "orange" };

}
