package test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PlayerIdentityTests {

	@Test
	public void testEnumIntConversion() {
		assertEquals(0, common.model.PlayerIdentity.ONE.ordinal());
		assertEquals(1, common.model.PlayerIdentity.TWO.ordinal());
		assertEquals(2, common.model.PlayerIdentity.THREE.ordinal());
		assertEquals(3, common.model.PlayerIdentity.FOUR.ordinal());
	}

}
