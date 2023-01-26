package com.cts.inventory.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;


@Document
public class InventoryModel {
	@Id
	private int id;
	@NotNull
	@Min(value = 100000,message = "Locationnumber should contains 6 digit")
	@Max(value = 999999,message = "Locationnumber should contains 6 digit")
	@Field(name = "LocationNbr")
	private int locationNbr;
	@NotNull
	@Pattern(regexp = "^[0-9]{10}$",message = "MaterialId should have length of 10 Digits.")
	@Field(name = "MaterialId")
	private String materialId;
	@Field(name = "MaterialName")
	private String materialName;
	@Max(value=1000,message = "Reset quantity should not be greater than 1000")
	@Field(name = "ResetQty")
	private int resetQty;
	@Field(name = "ResetDateTime")
	private String resetDateTime;
	@Field(name = "UpdateDateTime")
	private String updateDateTime;
	@Field(name = "AvailableQuantity")
	private int availableQty;
	@Field(name = "OrderQty")
	private int orderQty;
	public InventoryModel(int id,
			@NotNull @Min(value = 100000, message = "Locationnumber should contains 6 digit") @Max(value = 999999, message = "Locationnumber should contains 6 digit") int locationNbr,
			@NotNull @Pattern(regexp = "^[0-9]{10}$", message = "MaterialId should have length of 10 Digits.") String materialId,
			String materialName,
			@Max(value = 1000, message = "Reset quantity should not be greater than 1000") int resetQty,
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
	public InventoryModel() {
		super();
	}
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
	@Override
	public String toString() {
		return "InventoryModel [id=" + id + ", locationNbr=" + locationNbr + ", materialId=" + materialId
				+ ", materialName=" + materialName + ", resetQty=" + resetQty + ", resetDateTime=" + resetDateTime
				+ ", updateDateTime=" + updateDateTime + ", availableQty=" + availableQty + ", orderQty=" + orderQty
				+ "]";
	}
	
	
}