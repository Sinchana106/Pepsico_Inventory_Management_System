package com.cts.inventory.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

@WebMvcTest(value = InventoryModel.class)
public class InventoryModelTest {

	public static InventoryModel model;

	@BeforeAll
	public static void setUp() {
		model = new InventoryModel();
		model.setId(1);
		model.setLocationNbr(111111);
		model.setMaterialId("1234567890");
		model.setMaterialName("Lays");
		model.setMaterialId("1234567890");
		model.setResetQty(100);
		model.setResetDateTime("19-01-2023 19:04:06");
		model.setUpdateDateTime("19-01-2023 19:04:06");
		model.setAvailableQty(100);
	}

	// To test No Argument Constructor
	@Test
	void testNoArgsConstructor() {
		assertNotNull(model);
	}

	// To test All Argument Constructor
	@Test
	void testAllArgsConstructor() {
		InventoryModel inventoryModel = new InventoryModel(1296437386, 55555, "1234567890", "Lays", 100,
				"19-01-2023 19:04:06", "19-01-2023 19:04:06", 100, 0);
		assertEquals("Lays", inventoryModel.getMaterialName());
	}

	// To test Inventory Id
	@Test
	void testId() {

		assertEquals(1, model.getId());
	}

	// To test Location Number
	@Test
	void testLocationNbr() {

		assertEquals(111111, model.getLocationNbr());
	}

	// To test MaterialId
	@Test
	void testMaterialId() {

		assertEquals("1234567890", model.getMaterialId());
	}

	// To test MaterialName
	@Test
	void testMaterialName() {
		model.setMaterialName("Lays");
		assertEquals("Lays", model.getMaterialName());
	}

	// To test ResetQty
	@Test
	void testResetQty() {

		assertEquals(100, model.getResetQty());
	}

	// To test "ResetDateTime"
	@Test
	void testResetDateTime() {

		assertEquals("19-01-2023 19:04:06", model.getResetDateTime());
	}

	// To test UpdateDateTime
	@Test
	void testUpdateDateTime() {

		assertEquals("19-01-2023 19:04:06", model.getUpdateDateTime());
	}

	// To test AvailableQuantity
	@Test
	void testAvailableQuantity() {

		assertEquals(100, model.getAvailableQty());
	}

	// To test OrderQty
	@Test
	void testOrderQty() {

		assertEquals(0, model.getOrderQty());
	}

	// To Test toString()
	@Test
	void testToString() {
		String str = "InventoryModel [id=" + model.getId() + ", locationNbr=" + model.getLocationNbr() + ", materialId="
				+ model.getMaterialId() + ", materialName=" + model.getMaterialName() + ", resetQty="
				+ model.getResetQty() + ", resetDateTime=" + model.getResetDateTime() + ", updateDateTime="
				+ model.getUpdateDateTime() + ", availableQty=" + model.getAvailableQty() + ", orderQty="
				+ model.getOrderQty() + "]";
		assertEquals(str, model.toString());
	}

}
