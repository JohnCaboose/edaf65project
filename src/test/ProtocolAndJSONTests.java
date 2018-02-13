package test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.gson.Gson;

import common.model.Coordinate;
import common.model.Direction;
import common.model.GameState;

import common.model.PacketType;
import common.model.Snake;


public class ProtocolAndJSONTests {

	private static Gson gson;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		gson = new Gson();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testStateConversion() {
		GameState expectedstate = new GameState(2,10,10);

		String jsonState = gson.toJson(expectedstate, GameState.class);
		// System.out.println(jsonState);

		GameState convertedstate = gson.fromJson(jsonState, GameState.class);

		for (int i = 0; i < 2; i++) {
			for (int k = 0; k < 3; k++) {
				assertTrue(expectedstate.getPlayerSnakes().get(i).equals(convertedstate.getPlayerSnakes().get(i)));
			}
		}
		assertEquals(expectedstate.getTickCounter(), convertedstate.getTickCounter());

	}
	
	@Test
	public void testEnumConversion() {
		assertEquals("DIRECTION", PacketType.DIRECTION.name());
		assertEquals("GAMESTATE", PacketType.GAMESTATE.name());
		assertEquals("PLAYERIDENTITY", PacketType.PLAYERIDENTITY.name());
	}
	
	@Test
	public void testGameTick() {
		GameState gameState = new GameState(2,common.constants.Constants.BOARDWIDTH,common.constants.Constants.BOARDHEIGHT);
		gameState.performGameTick();
		String expected = "{\"tickCounter\":0,\"playerSnakes\":[{\"body\":[{\"x\":0,\"y\":20},{\"x\":0,\"y\":21},{\"x\":0,\"y\":22}],\"travelDirection\":\"UP\",\"alive\":true},{\"body\":[{\"x\":23,\"y\":3},{\"x\":23,\"y\":2},{\"x\":23,\"y\":1}],\"travelDirection\":\"DOWN\",\"alive\":true}]}";
		String actual = gson.toJson(gameState);
		assertEquals(expected, actual);
	}
	@Test
	public void testSnakeMovement() {
		Snake s = new Snake(new Coordinate(0,0), Direction.RIGHT,3);
		s.moveForward();
		
		Coordinate[] expectedLocations = new Coordinate[3];
		expectedLocations[0] = new Coordinate(3,0);
		expectedLocations[1] = new Coordinate(2,0);
		expectedLocations[2] = new Coordinate(1,0);
		assertArrayEquals(expectedLocations,s.getBody().toArray());
	}
	@Test
	public void testSnakeCreation() {
		Snake s = new Snake(new Coordinate(0,0), Direction.RIGHT,3);
		Coordinate[] expectedLocations = new Coordinate[3];
		expectedLocations[0] = new Coordinate(2,0);
		expectedLocations[1] = new Coordinate(1,0);
		expectedLocations[2] = new Coordinate(0,0);
		assertArrayEquals(expectedLocations,s.getBody().toArray());
	}
	
	
	

}
