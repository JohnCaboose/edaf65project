package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.gson.Gson;

import common.model.Coordinate;
import common.model.GameState;
import common.model.Snake;

class JSONTests {

	static Gson gson;
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		gson = new Gson();
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testStateConversion() {
		List<Snake> snakes = new ArrayList<Snake>(2);
		Coordinate.height = 51;
		Coordinate.width = 52;
		Snake p1 = new Snake(new Coordinate (1,1), common.model.Direction.RIGHT,3);
		Snake p2 = new Snake(new Coordinate (10,10), common.model.Direction.LEFT,3);		
		
		snakes.add(p1);
		snakes.add(p2);
		
		
		GameState expectedstate = new GameState(snakes);
		
		
		String jsonState = gson.toJson(expectedstate, GameState.class);
		//System.out.println(jsonState);
		
		GameState convertedstate = gson.fromJson(jsonState, GameState.class);
		
		
		for(int i = 0; i < 2; i++) {
			for(int k = 0; k < 3; k++) {
				assertEquals(expectedstate.getPlayerSnakes().get(i).body.get(k), convertedstate.getPlayerSnakes().get(i).body.get(k));
			}
		}
		assertEquals(expectedstate.getTickCounter(), convertedstate.getTickCounter());
		
	}

}
