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
		List<Snake> snakes = new ArrayList<Snake>(2);
		Coordinate.height = 51;
		Coordinate.width = 52;
		Snake p1 = new Snake(new Coordinate(1, 1), common.model.Direction.RIGHT, 3);
		Snake p2 = new Snake(new Coordinate(10, 10), common.model.Direction.LEFT, 3);

		snakes.add(p1);
		snakes.add(p2);

		GameState expectedstate = new GameState(snakes);

		String jsonState = gson.toJson(expectedstate, GameState.class);
		// System.out.println(jsonState);

		GameState convertedstate = gson.fromJson(jsonState, GameState.class);

		for (int i = 0; i < 2; i++) {
			for (int k = 0; k < 3; k++) {
				assertEquals(expectedstate.getPlayerSnakes().get(i).body.get(k),
						convertedstate.getPlayerSnakes().get(i).body.get(k));
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

}
