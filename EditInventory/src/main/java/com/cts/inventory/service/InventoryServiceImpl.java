package com.cts.inventory.service;

import java.time.LocalDateTime;
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
		return repo.save(model);
	}

	@Override
	public List<InventoryModel> getAllInventory() {
		return (List<InventoryModel>) repo.findAll();
	}

	@Override
	public InventoryModel resetInventoryQty(int id, int resetQty) throws Exception {
		InventoryModel inventory = repo.findById(id).get();
		int aQty = inventory.getAvailableQty();
		inventory.setAvailableQty(aQty + resetQty);
		inventory.setResetQty(inventory.getResetQty() + resetQty);
		inventory.setUpdateDateTime(LocalDateTime.now());
		return repo.save(inventory);
	}

	@Override
	public String deleteInventoryById(int id) {
		repo.deleteById(id);
		return "Inventory with materialId " + id + " deleted!";
	}

	@Override
	public InventoryModel getInventoryByMaterialIdandLocationNbr(int locationNbr, String materialId) {
		return repo.findByLocationNbrAndMaterialId(locationNbr, materialId);

	}

	@Override
	public List<InventoryModel> getInventoryByLocationNbr(int locationNbr) {
		return repo.findByLocationNbr(locationNbr);
	}

	@Override
	public Optional<InventoryModel> getInventoryById(int id) {
		return repo.findById(id);
	}

}
