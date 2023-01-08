package com.cts.order.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cts.order.feign.InventoryFeign;
import com.cts.order.model.OrderModel;
import com.cts.order.pojo.InventoryModel;
import com.cts.order.service.OrderServiceImpl;

@RestController
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private OrderServiceImpl service;
	
	
	@Autowired
	private InventoryFeign inventoryFeign;
	
	@GetMapping
	public List<OrderModel> getAllOrderDetails(){
		return service.fetchAllOrder();
	}
	
	@PostMapping("/getDetails")
	public String saveOrder(@RequestBody OrderModel model) {
		InventoryModel inventoryModel=inventoryFeign.getByLocationNbrandMaterialId(model.getLocationNbr(), model.getMaterialId());
		int availableQty=inventoryModel.getAvailableQty();
		int orderQty=model.getOrderQty();
		if(availableQty>=orderQty) {
			availableQty-=orderQty;
			//inventoryModel.setAvailableQty(availableQty);
			//inventoryModel.setOrderQty(inventoryModel.getOrderQty()+orderQty);
			service.saveOrder(model);
			return "Order Saved Successfully";
		}
		else {
			return "Order Cannot be Accepted";
		}
	}
	
	@GetMapping("/orderStatus")
	public List<OrderModel> getAllPendingOrders(){
		return service.fetchAllPendingOrders();
	}
	
	@GetMapping("/completedWithinThreeHours")
	public List<OrderModel> getAllCompletedOrdersWithinThreeHours(){
		return service.fetchAllCompletedOrdersWithinThreeHours();
	}
	
//	@GetMapping("/getDetails")
//	public InventoryModel getByLocationNbrAndMaterialId(@RequestParam int locationNbr,@RequestParam String materialId) {
//		 return inventoryFeign.getByLocationNbrandMaterialId(locationNbr,materialId);
//	}

}
