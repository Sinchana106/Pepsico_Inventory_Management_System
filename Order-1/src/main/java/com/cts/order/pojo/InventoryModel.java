package com.cts.order.pojo;

public class InventoryModel {
	private int id;
	
	private int locationNbr;
	
	private String materialId;
	
	private String materialName;
	
	private int resetQty;
	
	private String resetDateTime;
	
	private String updateDateTime;
	
	private int availableQty;
	
	private int orderQty;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getLocationNbr() {
		return locationNbr;
	}

	public void setLocationNbr(int locationNbr) {
		this.locationNbr = locationNbr;
	}

	public String getMaterialId() {
		return materialId;
	}

	public void setMaterialId(String materialId) {
		this.materialId = materialId;
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public int getResetQty() {
		return resetQty;
	}

	public void setResetQty(int resetQty) {
		this.resetQty = resetQty;
	}

	public String getResetDateTime() {
		return resetDateTime;
	}

	public InventoryModel() {
		super();
	}

	public void setResetDateTime(String resetDateTime) {
		this.resetDateTime = resetDateTime;
	}

	public String getUpdateDateTime() {
		return updateDateTime;
	}

	public void setUpdateDateTime(String updateDateTime) {
		this.updateDateTime = updateDateTime;
	}

	public int getAvailableQty() {
		return availableQty;
	}

	public void setAvailableQty(int availableQty) {
		this.availableQty = availableQty;
	}

	public InventoryModel(int id, int locationNbr, String materialId, String materialName, int resetQty,
			String resetDateTime, String updateDateTime, int availableQty, int orderQty) {
		super();
		this.id = id;
		this.locationNbr = locationNbr;
		this.materialId = materialId;
		this.materialName = materialName;
		this.resetQty = resetQty;
		this.resetDateTime = resetDateTime;
		this.updateDateTime = updateDateTime;
		this.availableQty = availableQty;
		this.orderQty = orderQty;
	}

	public int getOrderQty() {
		return orderQty;
	}

	public void setOrderQty(int orderQty) {
		this.orderQty = orderQty;
	}
}
