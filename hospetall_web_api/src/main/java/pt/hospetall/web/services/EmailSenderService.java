package pt.hospetall.web.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@Component
public class EmailSenderService {

	private final JavaMailSender emailSender;

	@Autowired
	public EmailSenderService(@Qualifier("getJavaMailSender") JavaMailSender emailSender) {
		this.emailSender = emailSender;
	}

	public void sendSimpleMessage(
			String to, String subject, String text) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setSubject(subject);
		message.setText(text);
		emailSender.send(message);
	}

}
