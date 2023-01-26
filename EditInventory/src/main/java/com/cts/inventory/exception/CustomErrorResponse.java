package com.cts.inventory.exception;

import java.time.LocalDateTime;

public class CustomErrorResponse {
	private String msg;
	private LocalDateTime dateTime;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

	public CustomErrorResponse(String msg, LocalDateTime dateTime) {
		super();
		this.msg = msg;
		this.dateTime = dateTime;
	}

	public CustomErrorResponse() {
		super();
	}

}
