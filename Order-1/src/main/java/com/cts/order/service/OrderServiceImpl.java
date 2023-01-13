package com.cts.order.service;

import java.time.LocalDateTime;
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
	
	int n=1;
	@Override
	public List<OrderModel> fetchAllOrder() {
		return repo.findAll();
	}

	@Override
	public OrderModel saveOrder(int locationNbr,String materialId,int orderQty) {
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
		InventoryModel inventoryModel=inventoryFeign.getByLocationNbrAndMaterialId(locationNbr,materialId);
		OrderModel model =new OrderModel();
		if(inventoryModel.getAvailableQty()>=orderQty) {
		model.setOrderId(id);
		model.setLocationNbr(locationNbr);
		model.setMaterialId(materialId);
		model.setMaterialName(inventoryModel.getMaterialName());
		model.setOrderQty(orderQty);
		model.setOrderDateTime(LocalDateTime.now());
		model.setOrderStatus("InProgress");
		inventoryFeign.updateAvailbaleqty(locationNbr, materialId, orderQty);
		n++;
		System.out.println(model.toString());
		
		return repo.save(model);
		}
		else {
			return null;
		}
		
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
	public OrderModel fetchOrderByOrderId(String orderId) {
		OrderModel order=new OrderModel();
		return repo.findById(orderId).get();
	}

	@Override
	public List<OrderModel> fetchTop5OrdersWithinThreeHours() {
		return null;
	}

	@Override
	public OrderModel sendOrderQuantityToInventoryTable(int locationNbr, String materialId, int orderQty) {
		return null;
	}


	
	@Override
	public OrderModel processOrder(String orderId, OrderModel response) {
			OrderModel model=repo.findById(orderId).get();
			if(response.getOrderStatus()=="Completed") {
			model.setOrderStatus("Completed");
			}
			else {
				model.setOrderStatus("Canceled");
				cancelOrder(response.getOrderId());
			}
			model.setOrderDateTime(LocalDateTime.now());
			
			return repo.save(model);
		}

	@Override
	public OrderModel fetchOrderByLocationNbrMaterialIdOrderId(int locationNbr, String materialId, String orderId) {
		return repo.findByLocationNbrAndMaterialIdAndOrderId(locationNbr, materialId, orderId);
	}

	@Override
	public void cancelOrder(String id) {
		   OrderModel ord=repo.findById(id).get();
//	        InventoryModel inven=inventoryFeign.getByLocationNbrAndMaterialId(ord.getLocationNbr(), ord.getMaterialId());
//	        InventoryModel inventory= new InventoryModel();
//	        inventory.setId(inven.getId());
//	        inventory.setLocationNbr(inven.getLocationNbr());
//	        inventory.setMaterialId(inven.getMaterialId());
//	        inventory.setOrderQty(inven.getOrderQty()-ord.getOrderQty());
//	        inventory.setUpdateDateTime(inven.getUpdateDateTime());
//	      	//write logic to send inventory model to inventory controller
		   inventoryFeign.updateAvailbaleqtyAfterCancel(ord.getLocationNbr(), ord.getMaterialId(), ord.getOrderQty());
	}
	


}
