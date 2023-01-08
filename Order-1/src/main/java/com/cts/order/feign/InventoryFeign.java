package com.cts.order.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.cts.order.pojo.InventoryModel;
import com.cts.order.pojo.UserCredentials;

@FeignClient(name="inventory-service",url="http://localhost:8081/inventory")
public interface InventoryFeign {

	@GetMapping("/{locationNbr}/{materialId}")
	@ResponseStatus(HttpStatus.OK)
	public InventoryModel getByLocationNbrAndMaterialId(@PathVariable int locationNbr,@PathVariable String materialId);
	
	@PostMapping("/login")
	public String login(@RequestBody UserCredentials userCredentials) ;
}
