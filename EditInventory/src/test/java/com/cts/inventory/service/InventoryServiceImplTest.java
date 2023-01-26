package com.cts.inventory.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.cts.inventory.model.InventoryModel;
import com.cts.inventory.repository.InventoryRepository;

@WebMvcTest(value = InventoryServiceImpl.class)
public class InventoryServiceImplTest {

	@Autowired
	private InventoryServiceImpl service;
	@MockBean
	private InventoryRepository repository;

	/*
	 * This Method is used to test whether its returning a unique number that is not
	 * present in Database
	 */
	@Test
	public void testRandomGenerator_whenIdIsNotPresentInDB() {

		Optional<InventoryModel> expected = Optional.of(new InventoryModel(-965599576, 222222, "2222222222", "Pepsi",
				30, "16-01-2023 16:41:15", "23-01-2023 18:16:44", 30, 0));
		when(repository.findById(-965599576)).thenReturn(expected);
		assertNotEquals(service.randomGenerator(), -965599576);
	}

	/* This method is used to test save method */
	@Test
	public void testSaveInventory() {
		InventoryModel inputModel = new InventoryModel(0, 555555, "1234567890", "Lays", 100, null, null, 0, 0);

		InventoryModel actualModel = service.saveInventory(inputModel);
		LocalDateTime dateTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		String format = dateTime.format(formatter);
		// To check whether orderQty is set to 0
		assertEquals(0, actualModel.getOrderQty());
		// To check whether availableQty is set to 100
		assertEquals(100, actualModel.getAvailableQty());
		// To check whether its taking current date and time
		assertEquals(format, actualModel.getResetDateTime());

	}

	/* To set Get All Inventory Method */
	@Test
	public void testGetAllInventory() {
		List<InventoryModel> inventoryModels = new ArrayList<InventoryModel>();
		inventoryModels.add(new InventoryModel(-1459091184, 111111, "1234567890", "Lays", 190, "23-01-2023 18:14:38",
				"23-01-2023 18:14:38", 120, 70));
		inventoryModels.add(new InventoryModel(-22060359, 333333, "1234567890", "Lays", 90, "18-01-2023 12:48:47",
				"25-01-2023 21:02:32", 90, 0));
		when(repository.findAll()).thenReturn(inventoryModels);
		assertEquals(inventoryModels, service.getAllInventory());
	}

	/*
	 * This method is used to test whether whatever the quantity is added by user
	 * its adding to available quantity or not and at the same time setting
	 * updateDateTime to currentDateTime
	 */
	@Test
	public void testResetInventoryQty() throws Exception {
		LocalDateTime dateTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		String format = dateTime.format(formatter);
		InventoryModel reset = new InventoryModel(-965599576, 222222, "2222222222", "Pepsi", 30, "16-01-2023 16:41:15",
				"23-01-2023 18:16:44", 30, 0);
		when(repository.findByLocationNbrAndMaterialId(222222, "2222222222")).thenReturn(new InventoryModel(-965599576,
				222222, "2222222222", "Pepsi", 30, "16-01-2023 16:41:15", "23-01-2023 18:16:44", 30, 0));
		InventoryModel actualModel = service.resetInventoryQty(222222, "2222222222", reset);

		// Test whether available quantity is updated
		assertEquals(60, actualModel.getAvailableQty());

		// Test whether reset qty is updated
		assertEquals(60, actualModel.getResetQty());
		// Test whether updateDateTimeIsAdded
		assertEquals(format, actualModel.getUpdateDateTime());
	}

	// To test whether repo will return inventory model at location nbr and material
	// id
	@Test
	public void testGetInventoryByLocationNbrandMaterialId() {
		when(repository.findByLocationNbrAndMaterialId(222222, "2222222222")).thenReturn(new InventoryModel(-965599576,
				222222, "2222222222", "Pepsi", 30, "16-01-2023 16:41:15", "23-01-2023 18:16:44", 30, 0));
		assertNotNull(service.getInventoryByLocationNbrandMaterialId(222222, "2222222222"));
	}

	// To get inventory model by passing location nbr
	@Test
	public void testGetInventoryByLocationNbr() {
		List<InventoryModel> inventoryModels = new ArrayList<InventoryModel>();
		inventoryModels.add(new InventoryModel(-965599576, 222222, "2222222222", "Pepsi", 30, "16-01-2023 16:41:15",
				"23-01-2023 18:16:44", 30, 0));
		when(repository.findByLocationNbr(222222)).thenReturn(inventoryModels);
		assertEquals(inventoryModels, service.getInventoryByLocationNbr(222222));

	}

	// To test whether repo will return inventory model by passing Id
	@Test
	public void testGetInventoryById() {
		Optional<InventoryModel> inventoryModel = Optional.of(new InventoryModel(820615220, 222222, "1234567890",
				"Lays", 40, "17-01-2023 16:25:32", "17-01-2023 16:25:32", 40, 0));
		when(repository.findById(820615220)).thenReturn(inventoryModel);
		assertEquals(inventoryModel, service.getInventoryById(820615220));
	}

	/*
	 * To test the whether available quantity,order quantity and update date time is
	 * updated once a customer places a order when when location number and material
	 * is present in database
	 */
	@Test
	public void testUpdateOrderAndAvailableQuantity_ifLocationNumberAndMaterialIdIsPresent() {
		InventoryModel expected = new InventoryModel(-965599576, 222222, "2222222222", "Pepsi", 30,
				"16-01-2023 16:41:15", "23-01-2023 18:16:44", 30, 0);
		when(repository.findByLocationNbrAndMaterialId(222222, "2222222222")).thenReturn(expected);
		when(repository.save(expected)).thenReturn(expected);

		assertTrue(service.updateOrderAndAvailableQuantity(222222, "2222222222", 10));
	}

	/*
	 * To test the whether available quantity,order quantity and update date time is
	 * updated once a customer places a order when location number and material is
	 * not present in database
	 */
	@Test
	public void testUpdateOrderAndAvailableQuantity_ifLocationNumberAndMaterialIdIsNotPresent() {
		when(repository.findByLocationNbrAndMaterialId(777777, "2222222222")).thenReturn(null);
		when(repository.save(null)).thenReturn(null);

		assertFalse(service.updateOrderAndAvailableQuantity(777777, "2222222222", 10));
	}

	/*
	 * To test the whether available quantity inventory is present when location
	 * number and material is present in database
	 */
	@Test
	public void testInventoryPresent_ifLocationNumberAndMaterialIdIsPresent() {
		InventoryModel expected = new InventoryModel(-965599576, 222222, "2222222222", "Pepsi", 30,
				"16-01-2023 16:41:15", "23-01-2023 18:16:44", 30, 0);
		when(repository.findByLocationNbrAndMaterialId(222222, "2222222222")).thenReturn(expected);

		assertTrue(service.isInventoryPresent(222222, "2222222222"));
	}

	/*
	 * To test the whether available quantity inventory is present when location
	 * number and material is not present in database
	 */
	@Test
	public void testInventoryPresent_ifLocationNumberAndMaterialIdIsNotPresent() {

		when(repository.findByLocationNbrAndMaterialId(777777, "2222222222")).thenReturn(null);

		assertFalse(service.isInventoryPresent(777777, "2222222222"));
	}

	/*
	 * To test the whether available quantity,order quantity and update date time is
	 * updated when admin cancels it
	 */
	@Test
	public void testUpdateOrderAndAvailableQuantityAfterCancel_ifLocationNumberAndMaterialIdIsPresent() {
		InventoryModel expected = new InventoryModel(-965599576, 222222, "2222222222", "Pepsi", 30,
				"16-01-2023 16:41:15", "23-01-2023 18:16:44", 30, 0);
		when(repository.findByLocationNbrAndMaterialId(222222, "2222222222")).thenReturn(expected);
		when(repository.save(expected)).thenReturn(expected);

		assertTrue(service.updateOrderAndAvailableQuantityAfterCancelation(222222, "2222222222", 10));
	}

	@Test
	public void testUpdateOrderAndAvailableQuantityAfterCancel_ifLocationNumberAndMaterialIdIsNotPresent() {

		when(repository.findByLocationNbrAndMaterialId(222222, "2222222222")).thenReturn(null);
		// when(repository.save(null)).thenReturn(null);

		assertFalse(service.updateOrderAndAvailableQuantityAfterCancelation(222222, "2222222222", 10));
	}
	
	@Test
	public void testDeleteInventoryById() {
		String str="Inventory with Id " +-965599576  + " deleted!";
		assertEquals(service.deleteInventoryById(-965599576), str);
	}

}
