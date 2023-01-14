package com.cts.order.pojo;

import java.time.LocalDateTime;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.data.couchbase.core.mapping.Field;

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

	public int getOrderQty() {
		return orderQty;
	}

	public void setOrderQty(int orderQty) {
		this.orderQty = orderQty;
	}
}
