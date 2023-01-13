package com.cts.inventory.controller;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException.InternalServerError;

import com.cts.inventory.exception.InternalServerException;
import com.cts.inventory.exception.InventoryAlreadyExist;
import com.cts.inventory.model.InventoryModel;
import com.cts.inventory.service.InventoryServiceImpl;

@RestController
@CrossOrigin("*")
public class InventoryController {
	@Autowired
	private InventoryServiceImpl service;

	@PostMapping("/saveInventory")
	@ResponseStatus(HttpStatus.CREATED)
	public InventoryModel saveInventory(@RequestBody @Valid InventoryModel model) throws InternalServerException, InventoryAlreadyExist {

		try {
			int locatioNbr=model.getLocationNbr();
			String materialId=model.getMaterialId();
			if(service.isInventoryPresent(locatioNbr, materialId)) {
				throw new InventoryAlreadyExist("Inventory with same Location number and Material Id already added");
			}else {
			model.setResetDateTime(LocalDateTime.now());
			service.saveInventory(model);
			return model;}
		} catch (InternalServerError | NullPointerException e) {
			throw new InternalServerException("Database connectivity Issue");
		}
	}

	@GetMapping("/getInventory")
	@ResponseStatus(HttpStatus.OK)
	public List<InventoryModel> getAllInventory() {
		return service.getAllInventory();
	}
	@GetMapping("/getInventory/{id}")
	@ResponseStatus(HttpStatus.OK)
	public InventoryModel getInventoryById(@PathVariable int id) {
		return service.getInventoryById(id).get();
	}
	

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public String deleteInventory(@PathVariable int id) {
		return service.deleteInventoryById(id);
	}

	@GetMapping("/getDetails/{locationNbr}")
	@ResponseStatus(HttpStatus.OK)
	public List<InventoryModel> getByLocationNbr(@PathVariable int locationNbr) {
		return service.getInventoryByLocationNbr(locationNbr);
	}
	
	@GetMapping("resetInventory/{locationNbr}/{materialId}")
	@ResponseStatus(HttpStatus.OK)
	public InventoryModel getByLocationNbrAndMaterialId(@PathVariable int locationNbr, @PathVariable String materialId) {
		return service.getInventoryByMaterialIdandLocationNbr(locationNbr, materialId);
	}
	
	@PutMapping("resetInventory/{locationNbr}/{materialId}")
	@ResponseStatus(HttpStatus.OK)
	public InventoryModel resetInventory(@PathVariable int locationNbr, @PathVariable String materialId,
			@RequestBody InventoryModel response) throws Exception {
		return service.resetInventoryQty(locationNbr, materialId, response);
	}
	
	@PutMapping("updateAvailableQty/{locationNbr}/{materialId}")
	@ResponseStatus(HttpStatus.OK)
	 public boolean updateAvailbaleqty(@PathVariable int locationNbr, @PathVariable String materialId, @RequestParam("quantity") int qnty) {
	  
	  	return service.updateOrderAndAvailableQuantity(locationNbr, materialId, qnty);

	}
	
	@PutMapping("updateAvailableQtyAfterCancel/{locationNbr}/{materialId}")
	@ResponseStatus(HttpStatus.OK)
	 public boolean updateAvailbaleqtyAfterCancel(@PathVariable int locationNbr, @PathVariable String materialId, @RequestParam("quantity") int qnty) {
	  
	  	return service.updateOrderAndAvailableQuantityAfterCancelation(locationNbr, materialId, qnty);

	}
	
	@GetMapping("search/{locationNbr}/{materialId}")
	@ResponseStatus(HttpStatus.OK)
	public InventoryModel searchByLocationNbrAndMaterialId(@PathVariable int locationNbr,@PathVariable String materialId) {
		return service.getInventoryByMaterialIdandLocationNbr(locationNbr, materialId);
	}
}
