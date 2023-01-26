package com.cts.order.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cts.order.emailUtility.service.EmailServiceImpl;

@Component
public class TriggerEmail {
	@Autowired
	private EmailServiceImpl emailService;

	@Scheduled(cron = "0 0/30 * * * ?")
	public void cronJobSch() {
//      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
//      Date now = new Date();
//      String strDate = sdf.format(now);
		// System.out.println("Java cron job expression:: " + strDate);
		emailService.sendSimpleMail();
	}
}
