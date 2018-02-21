package client.model;

import common.constants.Constants;
import common.model.PlayerIdentity;

public class PlayerIdentityColorConverter {

	public static String getColor(PlayerIdentity player) {
		switch (player) {
		case ONE:
			return Constants.playerColors[0];
		case TWO:
			return Constants.playerColors[1];
		case THREE:
			return Constants.playerColors[2];
		case FOUR:
			return Constants.playerColors[3];
		default:
			System.err.println("Error in PlayerIdentityColorConverter.getColor");
			return "black"; /* Should not happen */
		}
	}
}
