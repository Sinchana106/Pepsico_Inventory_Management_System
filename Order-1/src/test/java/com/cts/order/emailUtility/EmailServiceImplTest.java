package com.cts.order.emailUtility;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;

import com.cts.order.emailUtility.service.EmailServiceImpl;
import com.cts.order.model.OrderModel;
import com.cts.order.service.OrderServiceImpl;

@WebMvcTest(value = EmailServiceImpl.class)
public class EmailServiceImplTest {

	@MockBean
	private JavaMailSender javaMailSender;
	@MockBean
	private OrderServiceImpl orderServiceImpl;
	@Autowired
	private EmailServiceImpl emailServiceImpl;

	// To test whether the mail has been sent succesfully
	@Test
	public void testSendSimpleMail() {

		List<OrderModel> orderModels = new ArrayList<OrderModel>();
		orderModels.add(new OrderModel("O-1", "", 111111, "1234567890", "Lays", 10, "InProgress", "Tina123"));
		orderModels.add(new OrderModel("O-2", "", 222222, "2222222222", "Pepsi", 10, "InProgress", "Rina123"));
		when(orderServiceImpl.fetchAllPendingOrders()).thenReturn(orderModels);
		String str = "Mail Sent Successfully...";
		assertEquals(str, emailServiceImpl.sendSimpleMail());

	}
}
