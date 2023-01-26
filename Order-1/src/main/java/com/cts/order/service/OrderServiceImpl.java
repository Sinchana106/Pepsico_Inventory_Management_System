package com.cts.order.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.order.feign.InventoryFeign;
import com.cts.order.model.OrderModel;
import com.cts.order.pojo.InventoryModel;
import com.cts.order.repository.OrderRepository;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository repo;

	@Autowired
	private InventoryFeign inventoryFeign;

	int n = 1;

	@Override
	public List<OrderModel> fetchAllOrder() {
		return repo.findAll();
	}

	@Override
	public OrderModel saveOrder(int locationNbr, String materialId, int orderQty, String userId) {
		String id = "O-" + n;
		while (true) {

			Optional<OrderModel> order = repo.findById(id);
			if (order.isPresent()) {
				n++;
				id = "O-" + n;
			} else {
				break;
			}
		}
		InventoryModel inventoryModel = inventoryFeign.getByLocationNbrAndMaterialId(locationNbr, materialId);
		OrderModel model = new OrderModel();
		if (inventoryModel.getAvailableQty() >= orderQty) {
			model.setOrderId(id);
			model.setLocationNbr(locationNbr);
			model.setMaterialId(materialId);
			model.setMaterialName(inventoryModel.getMaterialName());
			model.setOrderQty(orderQty);
			model.setUserId(userId);
			LocalDateTime dateTime = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
			String format = dateTime.format(formatter);
			model.setOrderDateTime(format);
			model.setOrderStatus("InProgress");
			inventoryFeign.updateAvailbaleqty(locationNbr, materialId, orderQty);
			n++;
			System.out.println(model.toString());

			repo.save(model);
			return model;
		} else {
			return null;
		}

	}

	@Override
	public List<OrderModel> fetchAllOrderByUserId(String userId) {
		return repo.findByUserId(userId);
	}

	@Override
	public List<OrderModel> fetchAllPendingOrders() {
		return repo.findAllByOrderStatus("InProgress");
	}

	@Override
	public List<OrderModel> fetchAllCompletedOrdersWithinThreeHours() {
		return repo.findAllByOrderStatusAndOrderDateTimeGreaterThan("Completed", LocalDateTime.now().minusHours(3));
	}

	@Override
	public OrderModel fetchOrderByOrderId(String orderId) {
		return repo.findById(orderId).get();
	}

	@Override
	public OrderModel processOrder(String orderId, OrderModel response) {
		OrderModel model = repo.findById(orderId).get();
		if (response.getOrderStatus().equalsIgnoreCase("Completed")) {
			System.out.println("Completed block:" + response.getOrderStatus());
			model.setOrderStatus("Completed");
		} else {
			System.out.println("Canceled block:" + response.getOrderStatus());
			model.setOrderStatus("Canceled");
			cancelOrder(response.getOrderId());
		}
		LocalDateTime dateTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		String format = dateTime.format(formatter);
		model.setOrderDateTime(format);

		repo.save(model);
		return model;
	}

	@Override
	public void cancelOrder(String id) {
		OrderModel ord = repo.findById(id).get();
		inventoryFeign.updateAvailbaleqtyAfterCancel(ord.getLocationNbr(), ord.getMaterialId(), ord.getOrderQty());
	}

}
