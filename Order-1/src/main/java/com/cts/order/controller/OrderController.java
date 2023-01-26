package com.cts.order.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cts.order.model.OrderModel;
import com.cts.order.service.OrderServiceImpl;

@RestController
@CrossOrigin("*")
public class OrderController {

	int orderQty=0;
	
	@Autowired
	private OrderServiceImpl service;

	@GetMapping("/order")
	@ResponseStatus(HttpStatus.OK)
	public List<OrderModel> getAllOrderDetails() {
		return service.fetchAllOrder();
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/saveOrder/{locationNbr}/{materialId}")
	public OrderModel saveOrder(@PathVariable int locationNbr, @PathVariable String materialId,
			@RequestBody OrderModel orderModel) {
		orderQty=orderModel.getOrderQty();
		String userId=orderModel.getUserId();
		OrderModel order= service.saveOrder(locationNbr, materialId, orderQty,userId);
		if(order!=null) {
			return order;
		}
		else {
			return null;
		}

	}

	@GetMapping("/pendingOrder")
	@ResponseStatus(HttpStatus.OK)
	public List<OrderModel> getAllPendingOrders() {
		return service.fetchAllPendingOrders();
	}

	@GetMapping("/completedWithinThreeHours")
	@ResponseStatus(HttpStatus.OK)
	public List<OrderModel> getAllCompletedOrdersWithinThreeHours() {
		return service.fetchAllCompletedOrdersWithinThreeHours();
	}
	
	@PutMapping("processOrder/{orderId}")
	@ResponseStatus(HttpStatus.OK)
	public OrderModel processOrderStatus( @PathVariable String orderId,
			@RequestBody OrderModel response) throws Exception {
		return service.processOrder(orderId, response);
	}
	@GetMapping("/getorder/{userId}")
	@ResponseStatus(HttpStatus.OK)
	public List<OrderModel> getOrderByUserId(@PathVariable String userId) {
		return service.fetchAllOrderByUserId(userId);
	}
	
	@GetMapping("/order/{orderId}")
	@ResponseStatus(HttpStatus.OK)
	public OrderModel getOrderByOrderId(@PathVariable String orderId) {
		return service.fetchOrderByOrderId(orderId);
	}
	
	
}
