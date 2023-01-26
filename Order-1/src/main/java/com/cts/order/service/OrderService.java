package com.cts.order.service;

import java.util.List;
import java.util.Optional;

import com.cts.order.model.OrderModel;

public interface OrderService {
	public List<OrderModel> fetchAllOrder();

	public OrderModel fetchOrderByOrderId(String orderId);

	public List<OrderModel> fetchAllOrderByUserId(String userId);

	public OrderModel saveOrder(int locationNbr, String materialId, int orderQty,String userId);

	public List<OrderModel> fetchAllPendingOrders();

	public List<OrderModel> fetchAllCompletedOrdersWithinThreeHours();

	public OrderModel processOrder(String orderId, OrderModel response);

	public void cancelOrder(String id);
}
