package client.model;

import client.ClientGameStateMonitor;
import common.model.PlayerIdentity;

//TODO: implement class so it returns a color based on input of PlayerIdentity
public class PlayerIdentityColorConverter {
	private static String color;
	private static PlayerIdentity player;

	public PlayerIdentityColorConverter(PlayerIdentity player) {
		this.player = player;
	}

	public static String getColor() {
		if (player == PlayerIdentity.ONE) {
			color = "blue";
			
		} else if (player == PlayerIdentity.TWO) {
			color = "green";
		}
		
		else if (player == PlayerIdentity.THREE) {
			color = "pink";
		}
		
		else if (player == PlayerIdentity.FOUR) {
			color = "orange";
		}
		
		return color;
	}
}
