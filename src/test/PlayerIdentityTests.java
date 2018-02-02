package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

class PlayerIdentityTests {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
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

	@org.junit.jupiter.api.Test
	void testEnumIntConversion() {
		assertEquals(0,common.model.PlayerIdentity.ONE.ordinal());
		assertEquals(1,common.model.PlayerIdentity.TWO.ordinal());
		assertEquals(2,common.model.PlayerIdentity.THREE.ordinal());
		assertEquals(3,common.model.PlayerIdentity.FOUR.ordinal());
	}

}
