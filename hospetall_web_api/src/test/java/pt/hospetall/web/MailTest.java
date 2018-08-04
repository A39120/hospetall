package pt.hospetall.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pt.hospetall.web.services.EmailSenderService;
import pt.hospetall.web.services.EmailConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {EmailSenderService.class, EmailConfiguration.class})
public class MailTest {

	@Autowired
	private EmailSenderService emailSenderService;

	@Test
	public void sendMail(){
		//If it fails, it didn't send
		String to = "lariachi@gmail.com";
		String message = "Email", subject = "Email";
		emailSenderService.sendSimpleMessage(to, subject, message);
	}
}
