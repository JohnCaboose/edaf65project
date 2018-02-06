package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PlayerIdentityTests {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
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
	public void testEnumIntConversion() {
		assertEquals(0, common.model.PlayerIdentity.ONE.ordinal());
		assertEquals(1, common.model.PlayerIdentity.TWO.ordinal());
		assertEquals(2, common.model.PlayerIdentity.THREE.ordinal());
		assertEquals(3, common.model.PlayerIdentity.FOUR.ordinal());
	}

}
