package com.cts.order.schedule;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.cts.order.emailUtility.service.EmailServiceImpl;

@WebMvcTest(value = TriggerEmail.class)
public class TriggerEmailTest {

	@MockBean
	private EmailServiceImpl emailServiceImpl;
	
	@Autowired
	private TriggerEmail email;
	@Test
	public void testCronJobSch() {
		email.cronJobSch();
		verify(emailServiceImpl).sendSimpleMail();
	
	}
}
