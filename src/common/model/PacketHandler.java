package common.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.Gson;

public class PacketHandler {
	
	private final static Gson gson;
	private final static Pattern packetPattern;
	private final static String packetRegex;
	
	static {
		gson = new Gson();
		packetRegex = "<GAMEPACKET.*?TYPE=(DIRECTION|GAMESTATE|PLAYERIDENTITY)>(.*?)</GAMEPACKET>";
		packetPattern = Pattern.compile(packetRegex);
	}
	/**
	 * Creates a packet using this applications protocol based on the provided type and JSON String
	 * @param type the type of the JSON object in jsonContents
	 * @param jsonContents the JSON object to send
	 * @return a packet on the form <br /> {@code<GAMEPACKET TYPE=type>jsonContents</GAMEPACKET>}
	 */
	public static String createProtocolPacket(PacketType type, String jsonContents) {
		StringBuilder sb = new StringBuilder();
		sb.append("<GAMEPACKET TYPE=").append(type.name()).append(">").append(jsonContents).append("</GAMEPACKET>");
		return sb.toString();
	}
	/**
	 * Finds out what packet type the string has
	 * @param packet the packet to be examined
	 * @return the type of packet, or null if the packet type could not be determined for some reason
	 */
	public static PacketType getProtocolPacketType(String packet) {
		if(stringContainsProtocolPacket(packet)) {
			Matcher m = packetPattern.matcher(packet);
			m.find();
			switch(m.group(1)) {
				case "DIRECTION":
					return PacketType.DIRECTION;
				case "PLAYERIDENTITY":
					return PacketType.PLAYERIDENTITY;
				case "GAMESTATE":
					return PacketType.GAMESTATE;
				default:
					return null;
			}
		} else {
			return null;
		}
	}
	/**
	 * Examines the packet and returns the GameState contained within it
	 * @param packet the packet to examine
	 * @return the GameState contained in the packet, or null if it did not have a GameState
	 */
	public static GameState getStateFromProtocolPacket(String packet) {
		if(stringContainsProtocolPacket(packet) && getProtocolPacketType(packet) == PacketType.GAMESTATE) {
			String json = extractJsonString(packet);
			return gson.fromJson(json, GameState.class);
		} else {
			return null;
		}
	}
	/**
	 * Examines the packet and returns the Direction contained within it
	 * @param packet the packet to examine
	 * @return the Direction contained in the packet, or null if it did not have a Direction
	 */
	public static Direction getDirectionFromProtocolPacket(String packet) {
		if(stringContainsProtocolPacket(packet) && getProtocolPacketType(packet) == PacketType.DIRECTION) {
			String json = extractJsonString(packet);
			return gson.fromJson(json, Direction.class);
		} else {
			return null;
		}
		
	}
	/**
	 * Examines the packet and returns the PlayerIdentity contained within it
	 * @param packet the packet to examine
	 * @return the PlayerIdentity contained in the packet, or null if it did not have a PlayerIdentity
	 */
	public static PlayerIdentity getPlayerIdentityFromProtocolPacket(String packet) {
		if(stringContainsProtocolPacket(packet) && getProtocolPacketType(packet) == PacketType.PLAYERIDENTITY) {
			String json = extractJsonString(packet);
			return gson.fromJson(json, PlayerIdentity.class);
		}  else {
			return null;
		}
	}
	/**
	 * Checks if a string contains a protocol packet
	 * @param input the string to check
	 * @return true if input contains one or more protocol packets, otherwise false
	 */
	public static boolean stringContainsProtocolPacket(String input) {
		return input.matches(packetRegex);
	}
	/**
	 * When a StringBuilder contains more than one packet this function is useful to extract the first packet as a separate string. The StringBuilder will have the first packet removed from it.
	 * @param stringBuilder the StringBuilder with packets in it
	 * @return the first packet from the StringBuilder, or null if no packets in stringBuilder
	 */
	public static String extractFirstPacketFromMultiPacketStringBuilder(StringBuilder stringBuilder) {
		String stringRepresentationOfInput = stringBuilder.toString();
		if(stringRepresentationOfInput.matches(packetRegex)) {
			Matcher m = packetPattern.matcher(stringRepresentationOfInput);
			m.find();
			String packet = m.group(0);
			stringBuilder.delete(m.start(), m.end());
			return packet;
		}
		return null;
	}
	
	
	private static String extractJsonString(String packet) {
		Matcher m = packetPattern.matcher(packet);
		m.find();
		return m.group(2);
	}

}
