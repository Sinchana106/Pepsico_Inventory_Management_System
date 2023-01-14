package com.cts.order.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;
@Document
public class OrderModel {

	@Id
	@Field(name="OrderId")
	private String orderId;
	
	
	@Field(name="OrderDateTime")
	private String orderDateTime;
	
	@NotNull(message = "Location number cannot be blank")
	@Min(value =6  ,message= "Location number should contain 6 digits")
	@Field(name="LocationNbr")
	private int locationNbr;
	
	@NotNull(message = "Material Id cannot be blank")
	@Size(min = 10,max=10,message = "Material Id should contain 10 digits")
	@Field(name="MaterialId")
	private String materialId;
	
	@Field(name = "MaterialName")
	private String materialName;
	
	@NotNull(message = "Order quantity cannot be blank")
	@Min(value = 1)
	@Field(name="OrderQty")
	private int orderQty;
	
	@Field(name="OrderStatus")
	private String orderStatus;
	
	@Field(name="UserId")
	private String userId;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderDateTime() {
		return orderDateTime;
	}

	public void setOrderDateTime(String orderDateTime) {
		this.orderDateTime = orderDateTime;
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

	public int getOrderQty() {
		return orderQty;
	}

	public void setOrderQty(int orderQty) {
		this.orderQty = orderQty;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public OrderModel(@NotNull(message = "Order Id cannot be blank") String orderId, String orderDateTime,
			@NotNull(message = "Location number cannot be blank") @Min(value = 6, message = "Location number should contain 6 digits") int locationNbr,
			@NotNull(message = "Material Id cannot be blank") @Size(min = 10, max = 10, message = "Material Id should contain 10 digits") String materialId,
			String materialName, @NotNull(message = "Order quantity cannot be blank") @Min(1) int orderQty,
			String orderStatus, String userId) {
		super();
		this.orderId = orderId;
		this.orderDateTime = orderDateTime;
		this.locationNbr = locationNbr;
		this.materialId = materialId;
		this.materialName = materialName;
		this.orderQty = orderQty;
		this.orderStatus = orderStatus;
		this.userId = userId;
	}

	
	@Override
	public String toString() {
		return " orderId=" + orderId + " is pending from past 30 minutes, Please complete the order\n" ;
	}

	public OrderModel() {
		super();
	}

	
	
}
