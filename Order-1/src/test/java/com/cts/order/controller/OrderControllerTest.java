package com.cts.order.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.couchbase.client.core.deps.com.fasterxml.jackson.databind.ObjectMapper;
import com.cts.order.model.OrderModel;
import com.cts.order.service.OrderServiceImpl;

@WebMvcTest(value = OrderController.class)
public class OrderControllerTest {
	@MockBean
	private OrderServiceImpl serviceImpl;

	@Autowired
	private MockMvc mockMvc;

	/*
	 * @GetMapping("/order")
	 * 
	 * @ResponseStatus(HttpStatus.OK) public List<OrderModel> getAllOrderDetails()
	 */
	@Test
	public void testGetAllOrderDetails() throws Exception {
		List<OrderModel> orderModels = new ArrayList<OrderModel>();
		orderModels.add(
				new OrderModel("O-1", "18-01-2023 15:33:18", 111111, "1234567890", "Lays", 10, "Canceled", "Gita123"));
		orderModels.add(
				new OrderModel("O-2", "18-01-2023 16:08:19", 111111, "1234567890", "Lays", 10, "Canceled", "Sita123"));
		when(serviceImpl.fetchAllOrder()).thenReturn(orderModels);
		MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.get("/order");
		ResultActions perform = mockMvc.perform(mockHttpServletRequestBuilder);
		MvcResult result = perform.andReturn();
		MockHttpServletResponse response = result.getResponse();
		int status = response.getStatus();
		assertEquals(200, status);
	}

	/*
	 * @GetMapping("/pendingOrder")
	 * 
	 * @ResponseStatus(HttpStatus.OK) public List<OrderModel> getAllPendingOrders()
	 */
	@Test
	public void testGetAllPendingOrders() throws Exception {
		List<OrderModel> orderModels = new ArrayList<OrderModel>();
		orderModels.add(new OrderModel("O-6", "19-01-2023 19:01:20", 111111, "1234567890", "Lays", 10, "InProgress",
				"Gita123"));
		when(serviceImpl.fetchAllPendingOrders()).thenReturn(orderModels);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/pendingOrder");
		ResultActions perform = mockMvc.perform(requestBuilder);
		MvcResult result = perform.andReturn();
		MockHttpServletResponse response = result.getResponse();
		int status = response.getStatus();
		assertEquals(200, status);
	}

	/*
	 * @GetMapping("/completedWithinThreeHours")
	 * 
	 * @ResponseStatus(HttpStatus.OK) public List<OrderModel>
	 * getAllCompletedOrdersWithinThreeHours()
	 */
	@Test
	public void testGetAllCompletedOrdersWithinThreeHours() throws Exception {
		List<OrderModel> models = new ArrayList<>();
		models.add(
				new OrderModel("O-4", "18-01-2023 16:10:38", 111111, "1234567890", "Lays", 10, "Completed", "Ritu123"));
		when(serviceImpl.fetchAllCompletedOrdersWithinThreeHours()).thenReturn(models);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/completedWithinThreeHours");
		ResultActions perform = mockMvc.perform(requestBuilder);
		MvcResult result = perform.andReturn();
		MockHttpServletResponse response = result.getResponse();
		int status = response.getStatus();
		assertEquals(200, status);

	}

	/*
	 * @GetMapping("/getorder/{userId}")
	 * 
	 * @ResponseStatus(HttpStatus.OK) public List<OrderModel>
	 * getOrderByUserId(@PathVariable String userId)
	 */
	@Test
	public void testGetOrderByUserId() throws Exception {
		List<OrderModel> models = new ArrayList<>();
		models.add(
				new OrderModel("O-3", "18-01-2023 16:02:53", 111111, "1234567890", "Lays", 10, "Canceled", "Ritu123"));
		models.add(
				new OrderModel("O-4", "18-01-2023 16:10:38", 111111, "1234567890", "Lays", 10, "Completed", "Ritu123"));
		models.add(
				new OrderModel("O-5", "19-01-2023 19:04:48", 333333, "1234567890", "Lays", 10, "Canceled", "Ritu123"));
		when(serviceImpl.fetchAllOrderByUserId("Ritu123")).thenReturn(models);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/getorder/Ritu123");
		ResultActions perform = mockMvc.perform(requestBuilder);
		MvcResult result = perform.andReturn();
		MockHttpServletResponse response = result.getResponse();
		int status = response.getStatus();
		assertEquals(200, status);
	}

	/*
	 * @GetMapping("/order/{orderId}")
	 * 
	 * @ResponseStatus(HttpStatus.OK) public OrderModel
	 * getOrderByOrderId(@PathVariable String orderId)
	 */
	@Test
	public void testGetOrderByOrderId() throws Exception {
		OrderModel orderModel = new OrderModel("O-3", "18-01-2023 16:02:53", 111111, "1234567890", "Lays", 10,
				"Canceled", "Ritu123");
		when(serviceImpl.fetchOrderByOrderId("O-3")).thenReturn(orderModel);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/getorder/Ritu123");
		ResultActions perform = mockMvc.perform(requestBuilder);
		MvcResult result = perform.andReturn();
		MockHttpServletResponse response = result.getResponse();
		int status = response.getStatus();
		assertEquals(200, status);
	}

	/*
	 * @ResponseStatus(HttpStatus.CREATED)
	 * 
	 * @PostMapping("/saveOrder/{locationNbr}/{materialId}") public OrderModel
	 * saveOrder(@PathVariable int locationNbr, @PathVariable String materialId,
	 * 
	 * @RequestBody OrderModel orderModel)
	 */
	@Test
	public void testSaveOrder() throws Exception {
		OrderModel model = new OrderModel("O-6", "20-01-2023 19:45:53", 111111, "1234567890", "Lays", 10, "InProgress",
				"Ritu123");
		OrderModel order = new OrderModel();
		order.setOrderQty(10);
		ObjectMapper mapper = new ObjectMapper();
		String orderJsonString = mapper.writeValueAsString(order);
		when(serviceImpl.saveOrder(111111, "1234567890", 10, "Ritu123")).thenReturn(model);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/saveOrder/111111/1234567890")
				.contentType(MediaType.APPLICATION_JSON).content(orderJsonString);
		ResultActions perform = mockMvc.perform(requestBuilder);
		MvcResult result = perform.andReturn();
		MockHttpServletResponse response = result.getResponse();
		int status = response.getStatus();
		assertEquals(201, status);
	}

	/*
	 * @PutMapping("processOrder/{orderId}")
	 * 
	 * @ResponseStatus(HttpStatus.OK) public OrderModel
	 * resetInventory( @PathVariable String orderId,
	 * 
	 * @RequestBody OrderModel response) throws Exception
	 */
	@Test
	public void testProcessOrderStatus_Completed() throws Exception {
		OrderModel respondCancel = new OrderModel("O-6", "20-01-2023 19:45:53", 111111, "1234567890", "Lays", 10,
				"Canceled", "Ritu123");
		OrderModel respondCompleted = new OrderModel("O-6", "20-01-2023 19:45:53", 111111, "1234567890", "Lays", 10,
				"Completed", "Ritu123");
		// completed
		OrderModel order = new OrderModel("O-6", "20-01-2023 19:45:53", 111111, "1234567890", "Lays", 10, "InProgress",
				"Ritu123");
		ObjectMapper mapper = new ObjectMapper();
		String orderJsonString = mapper.writeValueAsString(order);
		when(serviceImpl.processOrder(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(respondCompleted);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/processOrder/O-6")
				.contentType(MediaType.APPLICATION_JSON).content(orderJsonString);
		ResultActions perform = mockMvc.perform(requestBuilder);
		MvcResult result = perform.andReturn();
		MockHttpServletResponse response = result.getResponse();
		int status = response.getStatus();
		assertEquals(200, status);
	}

	@Test
	public void testProcessOrderStatus_Canceled() throws Exception {
		OrderModel respondCancel = new OrderModel("O-6", "20-01-2023 19:45:53", 111111, "1234567890", "Lays", 10,
				"Canceled", "Ritu123");

		// completed
		OrderModel order = new OrderModel("O-6", "20-01-2023 19:45:53", 111111, "1234567890", "Lays", 10, "InProgress",
				"Ritu123");
		ObjectMapper mapper = new ObjectMapper();
		String orderJsonString = mapper.writeValueAsString(order);
		when(serviceImpl.processOrder(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(respondCancel);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/processOrder/O-6")
				.contentType(MediaType.APPLICATION_JSON).content(orderJsonString);
		ResultActions perform = mockMvc.perform(requestBuilder);
		MvcResult result = perform.andReturn();
		MockHttpServletResponse response = result.getResponse();
		int status = response.getStatus();
		assertEquals(200, status);
	}
}
