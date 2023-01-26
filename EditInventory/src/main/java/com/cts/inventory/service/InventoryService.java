package com.cts.inventory.service;

import java.util.List;
import java.util.Optional;

import com.cts.inventory.model.InventoryModel;

public interface InventoryService {

	public InventoryModel saveInventory(InventoryModel model);

	public List<InventoryModel> getAllInventory();

	public Optional<InventoryModel> getInventoryById(int id);

	public InventoryModel getInventoryByLocationNbrandMaterialId(int locationNbr, String materialId);

	public List<InventoryModel> getInventoryByLocationNbr(int locationNbr);

	public InventoryModel resetInventoryQty(int locationNbr, String materialId, InventoryModel model) throws Exception;

	public String deleteInventoryById(int id);

	public boolean updateOrderAndAvailableQuantity(int locationNbr, String materialId, int orderQty);

	public boolean isInventoryPresent(int locationNbr, String materialId);

	public boolean updateOrderAndAvailableQuantityAfterCancelation(int locationNbr, String materialId, int orderQty);

}
