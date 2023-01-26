package com.cts.inventory.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockRequestDispatcher;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.couchbase.client.core.deps.com.fasterxml.jackson.core.JsonProcessingException;
import com.couchbase.client.core.deps.com.fasterxml.jackson.databind.ObjectMapper;
import com.cts.inventory.exception.InternalServerException;
import com.cts.inventory.exception.InventoryAlreadyExist;
import com.cts.inventory.model.InventoryModel;
import com.cts.inventory.service.InventoryServiceImpl;

@WebMvcTest(value = InventoryController.class)
public class InventoryControllerTest {

	@MockBean
	private InventoryServiceImpl serviceImpl;

	@Autowired
	private MockMvc mockMvc;

	/*
	 * @PostMapping("/saveInventory")
	 * 
	 * @ResponseStatus(HttpStatus.CREATED) public InventoryModel
	 * saveInventory(@RequestBody @Valid InventoryModel model) throws
	 * InternalServerException, InventoryAlreadyExist { If Inventory with same
	 * location number and material id is not present
	 */
	@Test
	public void testSaveInventory_withoutSameLocationNbrAndMaterialId() throws Exception {
		InventoryModel input = new InventoryModel(0, 888888, "8888888888", "Pepsi", 10, null, null, 0, 0);
		ObjectMapper mapper = new ObjectMapper();
		String inputStr = mapper.writeValueAsString(input);
		when(serviceImpl.isInventoryPresent(888888, "8888888888")).thenReturn(false);
		MockHttpServletRequestBuilder content = MockMvcRequestBuilders.post("/saveInventory")
				.contentType(MediaType.APPLICATION_JSON).content(inputStr);
		ResultActions perform = mockMvc.perform(content);
		MvcResult result = perform.andReturn();
		MockHttpServletResponse response = result.getResponse();
		int status = response.getStatus();
		assertEquals(201, status);

	}

	@Test
	public void testSaveInventory_withSameLocationNbrAndMaterialId() throws Exception {
		InventoryModel input = new InventoryModel(0, 111111, "1234567890", "Lays", 10, null, null, 0, 0);
		ObjectMapper mapper = new ObjectMapper();
		String inputStr = mapper.writeValueAsString(input);
		when(serviceImpl.isInventoryPresent(111111, "1234567890")).thenReturn(true);
		try {
			MockHttpServletRequestBuilder content = MockMvcRequestBuilders.post("/saveInventory")
					.contentType(MediaType.APPLICATION_JSON).content(inputStr);
		} catch (Exception e) {

			String expectedMessage = "Inventory with same Location number and Material Id already added";
			String actualMessage = e.getMessage();

			assertTrue(actualMessage.contains(expectedMessage));
		}

	}

	@Test
	// @GetMapping("/getInventory/{id}")
	public void testGetInventoryById() throws Exception {
		// Creating model
		Optional<InventoryModel> model = Optional.of(new InventoryModel(1296437386, 55555, "1234567890", "Lays", 100,
				"19-01-2023 19:04:06", "19-01-2023 19:04:06", 100, 0));
		// Defining behavior of serviceimpl function
		when(serviceImpl.getInventoryById(1296437386)).thenReturn(model);
		// Creating a request
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/getInventory/1296437386");
		// Sending a request
		ResultActions perform = mockMvc.perform(requestBuilder);
		// Getting the result
		MvcResult result = perform.andReturn();
		// extracting the response
		MockHttpServletResponse response = result.getResponse();
		// Extracting the status code from response
		int status = response.getStatus();
		// Comparing the status code
		assertEquals(200, status);
	}

	// @GetMapping("/getInventory")
	@Test
	public void testGetAllInventory() throws Exception {
		List<InventoryModel> inventoryModels = new ArrayList<InventoryModel>();
		inventoryModels.add(new InventoryModel(1296437386, 55555, "1234567890", "Lays", 100, "19-01-2023 19:04:06",
				"19-01-2023 19:04:06", 100, 0));
		inventoryModels.add(new InventoryModel(1245678901, 11111, "1234567890", "Lays", 100, "19-01-2023 19:04:06",
				"19-01-2023 19:04:06", 100, 0));
		when(serviceImpl.getAllInventory()).thenReturn(inventoryModels);
		MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.get("/getInventory");
		ResultActions perform = mockMvc.perform(mockHttpServletRequestBuilder);
		MvcResult result = perform.andReturn();
		MockHttpServletResponse response = result.getResponse();
		int status = response.getStatus();
		assertEquals(200, status);
	}

	/*
	 * @DeleteMapping("/{id}")
	 * 
	 * @ResponseStatus(HttpStatus.OK) public String deleteInventory(@PathVariable
	 * int id)
	 */
	@Test
	public void testDeleteInventory() throws Exception {
		String str = "Inventory with Id " + 1296437386 + " deleted!";
		when(serviceImpl.deleteInventoryById(1296437386)).thenReturn(str);
		MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
				.delete("/delete/1296437386");
		ResultActions perform = mockMvc.perform(mockHttpServletRequestBuilder);
		MvcResult result = perform.andReturn();
		MockHttpServletResponse response = result.getResponse();
		int status = response.getStatus();
		assertEquals(200, status);

	}

	/*
	 * @GetMapping("/getDetails/{locationNbr}")
	 * 
	 * @ResponseStatus(HttpStatus.OK) public List<InventoryModel>
	 * getByLocationNbr(@PathVariable int locationNbr)
	 */
	@Test
	public void testGetByLocationNbr() throws Exception {
		List<InventoryModel> inventoryModels = new ArrayList<InventoryModel>();
		inventoryModels.add(new InventoryModel(-965599576, 222222, "2222222222", "Pepsi", 30, "16-01-2023 16:41:15",
				"16-01-2023 16:41:15", 30, 0));
		inventoryModels.add(new InventoryModel(820615220, 222222, "1234567890", "Lays", 40, "17-01-2023 16:25:32",
				"17-01-2023 16:25:32", 40, 0));
		when(serviceImpl.getInventoryByLocationNbr(222222)).thenReturn(inventoryModels);
		MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.get("/getDetails/222222");
		ResultActions perform = mockMvc.perform(mockHttpServletRequestBuilder);
		MvcResult result = perform.andReturn();
		MockHttpServletResponse response = result.getResponse();
		int status = response.getStatus();
		assertEquals(200, status);
	}

	/*
	 * @GetMapping("resetInventory/{locationNbr}/{materialId}")
	 * 
	 * @ResponseStatus(HttpStatus.OK) public InventoryModel
	 * getByLocationNbrAndMaterialId(@PathVariable int locationNbr, @PathVariable
	 * String materialId)
	 */
	@Test
	public void testGetByLocationNbrAndMaterialId() throws Exception {
		InventoryModel inventoryModel = new InventoryModel(-1459091184, 111111, "1234567890", "Lays", 180,
				"19-01-2023 19:03:30", "19-01-2023 19:03:30", 140, 40);
		when(serviceImpl.getInventoryByLocationNbrandMaterialId(111111, "1234567890")).thenReturn(inventoryModel);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/resetInventory/111111/1234567890");
		ResultActions perform = mockMvc.perform(requestBuilder);
		MvcResult result = perform.andReturn();
		MockHttpServletResponse response = result.getResponse();
		int status = response.getStatus();
		assertEquals(200, status);
	}

	/*
	 * @GetMapping("search/{locationNbr}/{materialId}")
	 * 
	 * @ResponseStatus(HttpStatus.OK) public InventoryModel
	 * searchByLocationNbrAndMaterialId(@PathVariable int locationNbr,@PathVariable
	 * String materialId)
	 */
	@Test
	public void testSearchByLocationNbrAndMaterialId() throws Exception {
		InventoryModel inventoryModel = new InventoryModel(-1459091184, 111111, "1234567890", "Lays", 180,
				"19-01-2023 19:03:30", "19-01-2023 19:03:30", 140, 40);
		when(serviceImpl.getInventoryByLocationNbrandMaterialId(111111, "1234567890")).thenReturn(inventoryModel);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/search/111111/1234567890");
		ResultActions perform = mockMvc.perform(requestBuilder);
		MvcResult result = perform.andReturn();
		MockHttpServletResponse response = result.getResponse();
		int status = response.getStatus();
		assertEquals(200, status);
	}

	/*
	 * @PutMapping("resetInventory/{locationNbr}/{materialId}")
	 * 
	 * @ResponseStatus(HttpStatus.OK) public InventoryModel
	 * resetInventory(@PathVariable int locationNbr, @PathVariable String
	 * materialId, @RequestBody InventoryModel response) throws Exception
	 * 
	 */
	@Test
	public void testResetInventory() throws Exception {
		InventoryModel inventoryModel = new InventoryModel();
		inventoryModel.setResetQty(30);
		ObjectMapper mapper = new ObjectMapper();
		String inputString = mapper.writeValueAsString(inventoryModel);
		InventoryModel expectedModel = new InventoryModel(-965599576, 222222, "2222222222", "Pepsi", 30,
				"16-01-2023 16:41:15", "16-01-2023 16:41:15", 30, 0);
		when(serviceImpl.resetInventoryQty(222222, "2222222222", inventoryModel)).thenReturn(expectedModel);
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put("/resetInventory/222222/2222222222")
				.contentType(MediaType.APPLICATION_JSON).content(inputString);
		ResultActions perform = mockMvc.perform(builder);
		MvcResult result = perform.andReturn();
		MockHttpServletResponse response = result.getResponse();
		int status = response.getStatus();
		assertEquals(200, status);
	}

	/*
	 * @PutMapping("updateAvailableQty/{locationNbr}/{materialId}")
	 * 
	 * @ResponseStatus(HttpStatus.OK) public boolean
	 * updateAvailbaleqty(@PathVariable int locationNbr, @PathVariable String
	 * materialId,
	 * 
	 * @RequestParam("quantity") int qnty) {
	 */

	@Test
	public void testUpdateAvailbaleqty() throws Exception {
		when(serviceImpl.updateOrderAndAvailableQuantity(222222, "2222222222", 10)).thenReturn(true);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.put("/updateAvailableQty/222222/2222222222?quantity=10");
		ResultActions perform = mockMvc.perform(requestBuilder);
		MvcResult result = perform.andReturn();
		MockHttpServletResponse response = result.getResponse();
		int status = response.getStatus();
		assertEquals(200, status);
	}

	/*
	 * @PutMapping("updateAvailableQtyAfterCancel/{locationNbr}/{materialId}")
	 * 
	 * @ResponseStatus(HttpStatus.OK) public boolean
	 * updateAvailbaleqtyAfterCancel(@PathVariable int locationNbr, @PathVariable
	 * String materialId,@RequestParam("quantity") int qnty) {
	 */

	@Test
	public void testUpdateAvailbaleqtyAfterCancel() throws Exception {
		when(serviceImpl.updateOrderAndAvailableQuantityAfterCancelation(222222, "2222222222", 10)).thenReturn(true);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.put("/updateAvailableQtyAfterCancel/222222/2222222222?quantity=10");
		ResultActions perform = mockMvc.perform(requestBuilder);
		MvcResult result = perform.andReturn();
		MockHttpServletResponse response = result.getResponse();
		int status = response.getStatus();
		assertEquals(200, status);

	}
}
