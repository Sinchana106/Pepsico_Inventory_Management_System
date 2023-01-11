package com.cts.inventory.feign;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "auth-service",path = "http://localhost:8080")
public interface InventoryFeign {

}
