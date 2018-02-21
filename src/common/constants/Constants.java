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
	public static final int MILLIS_PER_STATE_FRAME = 400;

	/**
	 * Defined as the frequency at which all snakes will grow. This value is
	 * divisible by the server tick frequency, i.e.
	 * <code>Constants.MILLIS_PER_STATE_FRAME</code>.
	 */
	public static final int MILLIS_BETWEEN_SNAKE_GROWTHS = MILLIS_PER_STATE_FRAME * 10;

}
