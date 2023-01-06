package com.cts.order.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.order.model.OrderModel;
import com.cts.order.repository.OrderRepository;
@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository repo;
	
	int n=1;
	@Override
	public List<OrderModel> fetchAllOrder() {
		return repo.findAll();
	}

	@Override
	public OrderModel saveOrder(OrderModel model) {
		String id="O-"+n;
		while(true) {
			
			Optional<OrderModel> order=repo.findById(id);
			if(order.isPresent()) {
				n++;
				id="O-"+n;
			}
			else {
				break;
			}
		}
		model.setOrderId(id);
		model.setOrderDateTime(LocalDateTime.now());
		model.setOrderStatus("InProgress");
		n++;
		System.out.println(model.toString());
		return repo.save(model);
		
	}

	@Override
	public List<OrderModel> fetchAllOrderByUserId(String userId) {
		return null;
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
	public Optional<OrderModel> fetchOrderByOrderId(String orderId) {
		return repo.findById(orderId);
	}

	@Override
	public List<OrderModel> fetchTop5OrdersWithinThreeHours() {
		return null;
	}

}
