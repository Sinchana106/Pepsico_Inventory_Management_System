package com.cts.inventory.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.cts.inventory.pojo.OrderModel;

@FeignClient(name = "order-service", path = "http://localhost:8082")
public interface OrderFeign {

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/saveOrder/{locationNbr}/{materialId}")
	public OrderModel saveOrder(@PathVariable int locationNbr, @PathVariable String materialId,
			@RequestBody OrderModel model);
}
