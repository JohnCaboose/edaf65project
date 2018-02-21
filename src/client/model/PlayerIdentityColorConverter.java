package client.model;

import common.model.PlayerIdentity;

public class PlayerIdentityColorConverter {
	private static final String player1Color = "blue";
	private static final String player2Color = "green";
	private static final String player3Color = "pink";
	private static final String player4Color = "orange";

	public static String getColor(PlayerIdentity player) {
		switch (player) {
		case ONE:
			return player1Color;
		case TWO:
			return player2Color;
		case THREE:
			return player3Color;
		case FOUR:
			return player4Color;
		default:
			return "black"; /* Should not happen */
		}
	}
}
