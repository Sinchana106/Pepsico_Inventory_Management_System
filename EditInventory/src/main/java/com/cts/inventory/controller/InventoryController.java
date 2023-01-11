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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException.InternalServerError;

import com.cts.inventory.exception.InternalServerException;
import com.cts.inventory.model.InventoryModel;
import com.cts.inventory.service.InventoryServiceImpl;

@RestController
@CrossOrigin("http://localhost:4200")
public class InventoryController {
	@Autowired
	private InventoryServiceImpl service;

	@PostMapping("/saveInventory")
	@ResponseStatus(HttpStatus.CREATED)
	public InventoryModel saveInventory(@RequestBody @Valid InventoryModel model) throws InternalServerException {

		try {

			model.setResetDateTime(LocalDateTime.now());
			service.saveInventory(model);
			return model;
		} catch (InternalServerError | NullPointerException e) {
			throw new InternalServerException("Database connectivity Issue");
		}
	}

	@GetMapping("/getInventory")
	@ResponseStatus(HttpStatus.OK)
	public List<InventoryModel> getAllInventory() {
		return service.getAllInventory();
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

	@PutMapping("resetInventory/{locationNbr}/{materialId}")
	@ResponseStatus(HttpStatus.OK)
	public InventoryModel getByLocationNbrAndMaterialId(@PathVariable int locationNbr, @PathVariable String materialId,
			@RequestBody InventoryModel response) throws Exception {
		return service.resetInventoryQty(locationNbr, materialId, response);
	}

}
