package tk.monkeycode.redditclone.service;

import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tk.monkeycode.redditclone.exception.RedditException;
import tk.monkeycode.redditclone.model.NotificationEmail;

@Slf4j
@Service
@AllArgsConstructor
public class EmailService {

	private final TemplateEngine templateEngine;
	private final JavaMailSender mailSender;
	
	@Async
	public void sendMail(NotificationEmail notificationEmail) {
		MimeMessagePreparator messagePreparator = mimeMessage -> {
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
			messageHelper.setFrom("arielarmijo@yahoo.es");
			messageHelper.setTo(notificationEmail.getRecipient());
			messageHelper.setSubject(notificationEmail.getSubject());
			messageHelper.setText(buildMailContent(notificationEmail.getBody()));
		};
		try {
			mailSender.send(messagePreparator);
			log.info("Email sent!");
		} catch (MailException e) {
			throw new RedditException("Exception occurred when sending mail to " + notificationEmail.getRecipient());
		}
	}
	
	private String buildMailContent(String message) {
		Context context = new Context();
		context.setVariable("message", message);
		return templateEngine.process("mail-template", context);
	}
	
}
