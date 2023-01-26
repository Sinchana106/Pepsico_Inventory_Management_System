package com.cts.order.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
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

import com.cts.order.feign.InventoryFeign;
import com.cts.order.model.OrderModel;
import com.cts.order.pojo.InventoryModel;
import com.cts.order.repository.OrderRepository;

@WebMvcTest(value = OrderServiceImpl.class)
public class OrderServiceImplTest {
	@MockBean
	private OrderRepository repository;
	@MockBean
	private InventoryFeign feign;

	@Autowired
	private OrderServiceImpl service;

	int n = 1;

	/*
	 * To test whether the service calls findAll() from repository which must return
	 * the list of order models
	 */
	@Test
	public void testFetchAllOrder() {
		List<OrderModel> orderModels = new ArrayList<OrderModel>();
		orderModels.add(new OrderModel("O-1", "", 111111, "1234567890", "Lays", 10, "InProgress", "Tina123"));
		orderModels.add(new OrderModel("O-2", "", 222222, "2222222222", "Pepsi", 10, "Completed", "Gita123"));
		when(repository.findAll()).thenReturn(orderModels);
		assertEquals(orderModels, service.fetchAllOrder());
	}

	/*
	 * 1.Random number is generated for orderId, if its present in inventory then
	 * new random number is generated 2.We fetch the inventory model by extracting
	 * location number and material id from order model with the help of feign 3.We
	 * set the various properties of order model and return it
	 * 
	 */
	@Test
	public void testSaveOrder_UniqueIdGenerated() {
		Optional<OrderModel> optional = Optional
				.of(new OrderModel("O-1", "", 111111, "1234567890", "Lays", 10, "InProgress", "Tina123"));
		when(repository.findById("O-1")).thenReturn(optional);
		InventoryModel inventoryModel = new InventoryModel(1, 111111, "1234567890", "Lays", 30, "", "", 30, 0);
		when(feign.getByLocationNbrAndMaterialId(111111, "1234567890")).thenReturn(inventoryModel);
		when(feign.updateAvailbaleqty(111111, "1234567890", 10)).thenReturn(true);
		OrderModel model = service.saveOrder(111111, "1234567890", 10, "Tina123");
		int expectedOrderQty = 10;
		LocalDateTime dateTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		String expectedOrderDateTime = dateTime.format(formatter);
		String expectedOrderStatus = "InProgress";
		assertEquals(expectedOrderQty, model.getOrderQty());
		assertEquals(expectedOrderStatus, model.getOrderStatus());
		assertEquals(expectedOrderDateTime, model.getOrderDateTime());

	}

	/*
	 * To test whether this method will call findAllOrderByUserId() from repository
	 */
	@Test
	public void testFetchAllOrderByUserId() {
		List<OrderModel> orderModels = new ArrayList<OrderModel>();
		orderModels.add(new OrderModel("O-1", "", 111111, "1234567890", "Lays", 10, "InProgress", "Tina123"));
		orderModels.add(new OrderModel("O-2", "", 222222, "2222222222", "Pepsi", 10, "Completed", "Tina123"));
		when(repository.findByUserId("Tina123")).thenReturn(orderModels);
		List<OrderModel> output = service.fetchAllOrderByUserId("Tina123");
		assertEquals(orderModels, output);
	}

	/*
	 * To test whether this method will call findAllOrderByOrderStatus from
	 * repository
	 */
	@Test
	public void testFetchAllPendingOrders() {
		List<OrderModel> orderModels = new ArrayList<OrderModel>();
		orderModels.add(new OrderModel("O-1", "", 111111, "1234567890", "Lays", 10, "InProgress", "Tina123"));
		orderModels.add(new OrderModel("O-2", "", 222222, "2222222222", "Pepsi", 10, "InProgress", "Rina123"));
		when(repository.findAllByOrderStatus("InProgress")).thenReturn(orderModels);
		List<OrderModel> output = service.fetchAllPendingOrders();
		assertEquals(orderModels, output);
	}

	/* This method should call findById() from repository */
	@Test
	public void testFetchOrderByOrderId() {
		Optional<OrderModel> model = Optional
				.of(new OrderModel("O-1", "", 111111, "1234567890", "Lays", 10, "InProgress", "Tina123"));
		when(repository.findById("O-1")).thenReturn(model);
		OrderModel actualModel = service.fetchOrderByOrderId("O-1");
		assertNotNull(actualModel);
	}

	/*
	 * This method set the order status based on user input and performs appropriate
	 * actions and also updates date and time
	 */
	@Test
	public void testProcessOrder_Completed() {
		Optional<OrderModel> model = Optional
				.of(new OrderModel("O-1", "", 111111, "1234567890", "Lays", 10, "InProgress", "Tina123"));
		when(repository.findById("O-1")).thenReturn(model);
		OrderModel input = new OrderModel("O-1", "", 111111, "1234567890", "Lays", 10, "Completed", "Tina123");
		OrderModel actual = service.processOrder("O-1", input);
		LocalDateTime dateTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		String expectedOrderDateTime = dateTime.format(formatter);
		assertEquals("Completed", actual.getOrderStatus());
		assertEquals(expectedOrderDateTime, actual.getOrderDateTime());
	}

	@Test
	public void testProcessOrder_Canceled() {
		Optional<OrderModel> model = Optional
				.of(new OrderModel("O-1", "", 111111, "1234567890", "Lays", 10, "InProgress", "Tina123"));
		when(repository.findById("O-1")).thenReturn(model);
		OrderModel input = new OrderModel("O-1", "", 111111, "1234567890", "Lays", 10, "Canceled", "Tina123");

		OrderModel actual = service.processOrder("O-1", input);
		LocalDateTime dateTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		String expectedOrderDateTime = dateTime.format(formatter);
		assertEquals("Canceled", actual.getOrderStatus());
		assertEquals(expectedOrderDateTime, actual.getOrderDateTime());
	}

	@Test
	public void testCancelOrder() {
		Optional<OrderModel> model = Optional
				.of(new OrderModel("O-1", "", 111111, "1234567890", "Lays", 10, "InProgress", "Tina123"));
		when(repository.findById("O-1")).thenReturn(model);
		service.cancelOrder(model.get().getOrderId());
		verify(feign).updateAvailbaleqtyAfterCancel(model.get().getLocationNbr(), model.get().getMaterialId(),
				model.get().getOrderQty());
	}
}
