package com.cts.order.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.cts.order.pojo.InventoryModel;

@FeignClient(name="inventory-service",url="http://localhost:8081/inventory")
public interface InventoryFeign {

	@GetMapping("/getDetails")
	@ResponseStatus(HttpStatus.OK)
	public InventoryModel getByLocationNbrandMaterialId(@RequestParam int locationNbr, @RequestParam String materialId);
}
