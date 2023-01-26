package com.cts.inventory.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.inventory.model.InventoryModel;
import com.cts.inventory.repository.InventoryRepository;

@Service
public class InventoryServiceImpl implements InventoryService {

	@Autowired
	private InventoryRepository repo;

	@Autowired
	// private OrderFeign orderFeign;

	public int randomGenerator() {
		Random random = new Random();
		int n = random.nextInt();

		while (true) {
			Optional<InventoryModel> id = repo.findById(n);
			if (id.isPresent()) {
				n = random.nextInt();

			} else {
				break;
			}
		}
		System.out.println(n);
		return n;

	}

	@Override
	public InventoryModel saveInventory(InventoryModel model) {
		model.setId(randomGenerator());
		model.setOrderQty(0);
		model.setAvailableQty(model.getResetQty() - model.getOrderQty());
		LocalDateTime dateTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		String format = dateTime.format(formatter);
		model.setResetDateTime(format);
		 repo.save(model);
		 return model;
	}

	@Override
	public List<InventoryModel> getAllInventory() {
		return (List<InventoryModel>) repo.findAll();
	}

	@Override
	public InventoryModel resetInventoryQty(int locationNbr, String materialId, InventoryModel inventory)
			throws Exception {
		InventoryModel model = getInventoryByLocationNbrandMaterialId(locationNbr, materialId);
		int aQty = model.getAvailableQty();
		model.setAvailableQty(aQty + inventory.getResetQty());
		LocalDateTime dateTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		String format = dateTime.format(formatter);
		model.setResetQty(inventory.getResetQty() + model.getResetQty());
		model.setResetDateTime(format);

		model.setUpdateDateTime(format);
		 repo.save(model);
		 return model;
	}

	@Override
	public String deleteInventoryById(int id) {
		repo.deleteById(id);
		return "Inventory with Id " + id + " deleted!";
	}

	@Override
	public InventoryModel getInventoryByLocationNbrandMaterialId(int locationNbr, String materialId) {
		InventoryModel inventoryModel= repo.findByLocationNbrAndMaterialId(locationNbr, materialId);
		return inventoryModel;

	}

	@Override
	public List<InventoryModel> getInventoryByLocationNbr(int locationNbr) {
		return repo.findByLocationNbr(locationNbr);
	}

	@Override
	public Optional<InventoryModel> getInventoryById(int id) {
		return repo.findById(id);
	}

	@Override
	public boolean updateOrderAndAvailableQuantity(int locationNbr, String materialId, int orderQty) {
		System.out.println("in to this method");
		InventoryModel inv = repo.findByLocationNbrAndMaterialId(locationNbr, materialId);
		if(inv==null) {
			return false;
		}
		LocalDateTime dateTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		String format = dateTime.format(formatter);
		inv.setUpdateDateTime(format);
		inv.setOrderQty(inv.getOrderQty() + orderQty);
		inv.setAvailableQty(inv.getAvailableQty() - orderQty);
		 repo.save(inv);
		return true;
	}

	@Override
	public boolean isInventoryPresent(int locationNbr, String materialId) {
		boolean isPresent = false;
		InventoryModel inventoryModel = repo.findByLocationNbrAndMaterialId(locationNbr, materialId);
		if (inventoryModel != null) {
			isPresent = true;
		}
		return isPresent;
	}

	@Override
	public boolean updateOrderAndAvailableQuantityAfterCancelation(int locationNbr, String materialId, int orderQty) {
		System.out.println("in to this method");
		InventoryModel inv = repo.findByLocationNbrAndMaterialId(locationNbr, materialId);
		if(inv==null) {
			return false;
		}
		inv.setOrderQty(inv.getOrderQty() - orderQty);
		inv.setAvailableQty(inv.getAvailableQty() + orderQty);
		LocalDateTime dateTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		String format = dateTime.format(formatter);
		inv.setUpdateDateTime(format);
		 repo.save(inv);
		
		return true;
	}

}
