package root.demo.services;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SendMailService implements JavaDelegate {

	@Autowired
	public JavaMailSender emailSender;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		String link = "http://localhost:4200/confirmRegistration/" + execution.getProcessInstanceId();

		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo((String)execution.getVariable("email"));
		message.setSubject("Confirm registration");
		message.setText("Click the following link to confirm your registration: " + link);
		emailSender.send(message);
	}

}
