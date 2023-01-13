package com.cts.inventory.exception;

public class InventoryAlreadyExist extends Exception {
	private static final long serialVersionUID=1L;
	public InventoryAlreadyExist(String msg) {
		super(msg);
		
	}
}
