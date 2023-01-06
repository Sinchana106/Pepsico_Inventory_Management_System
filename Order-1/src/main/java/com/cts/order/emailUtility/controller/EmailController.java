package com.cts.order.emailUtility.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

//Java Program to Create Rest Controller that
//Defines various API for Sending Mail


//Importing required classes
import com.cts.order.emailUtility.service.EmailService;

//Annotation
@RestController
//Class
public class EmailController {

	@Autowired private EmailService emailService;

	// Sending a simple Email
	@PostMapping("/sendMail")
	public String
	sendMail()
	{
		String status
			= emailService.sendSimpleMail();

		return status;
	}

	// Sending email with attachment
//	@PostMapping("/sendMailWithAttachment")
//	public String sendMailWithAttachment(
//		@RequestBody EmailDetails details)
//	{
//		String status
//			= emailService.sendMailWithAttachment(details);
//
//		return status;
//	}
}

