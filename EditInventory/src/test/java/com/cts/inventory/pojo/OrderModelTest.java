package com.cts.inventory.pojo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

@WebMvcTest(value = OrderModel.class)
class OrderModelTest {


	private static OrderModel orderModel;
	
	@BeforeAll
	public  static void setup() {
		orderModel=new OrderModel();
		orderModel.setOrderId("O-1");
		orderModel.setLocationNbr(111111);
		orderModel.setMaterialId("1234567890");
		orderModel.setMaterialName("Lays");
		orderModel.setOrderDateTime("18-01-2023 15:33:18");
		orderModel.setOrderQty(100);
		orderModel.setOrderStatus("InProgress");
	}
	
	//NoArgsConstructor
	@Test
	void testNoArgsConstructor() {
		assertNotNull(orderModel);
	}
	
	
	//ToTestOrderId
	@Test
	public void testOrderId() {
		assertEquals("O-1", orderModel.getOrderId());
	}
	//ToTestOrderDateTime
	@Test
	public void testOrderDateTime() {
		assertEquals("18-01-2023 15:33:18", orderModel.getOrderDateTime());
	}
	//ToTestLocationNbr
	@Test
	public void testLocationNbr() {
		assertEquals(111111,orderModel.getLocationNbr());
	}
	//ToTestMaterialId
	@Test
	public void testMaterialId() {
		assertEquals("1234567890", orderModel.getMaterialId());
	}
	//ToTestMaterialName
	@Test
	public void testMaterialName() {
		assertEquals("Lays", orderModel.getMaterialName());
	}
	//ToTestOrderQty
	@Test
	public void testOrderQty() {
		assertEquals(100, orderModel.getOrderQty());
	}
	//ToTestOrderStatus
	@Test
	public void testOrderStatus() {
		assertEquals("InProgress", orderModel.getOrderStatus());
	}
	

}
