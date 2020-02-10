package root.demo.workServices;

import java.util.List;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import root.demo.model.FormSubmissionDto;
import root.demo.model.NaucniRad;
import root.demo.repository.KorisnikRepository;
import root.demo.repository.NaucniRadRepository;
@Service
public class NotifyAboutCorrectionService implements JavaDelegate {

	@Autowired
	public JavaMailSender emailSender;
	
	@Autowired
	private NaucniRadRepository nrrepo;
	
	@Autowired
	private KorisnikRepository krepo;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		List<FormSubmissionDto> forma = (List<FormSubmissionDto>) execution.getVariable("commentForm");
		boolean potvrda = false;
		for (FormSubmissionDto formField : forma) {
			if (formField.getFieldId().equals("correctionFormComment")) {
				execution.setVariable("correctionFormComment", formField.getFieldValue());
			}
			if (formField.getFieldId().equals("correctionFormDeadline")) {
				execution.setVariable("correctionFormDeadlineForUser", formField.getFieldValue());
				execution.setVariable("correctionFormDeadline", formField.getFieldValue() + "T12:00:00Z");
			}			
		}
		
		Long nesto = (Long) execution.getVariable("nrId");
		System.out.println("WWW  : : " + nesto);
		NaucniRad nr = nrrepo.getOne(nesto);
		System.out.println("WWW  : : " + nesto);
		System.out.println("ODBIOJOJ DS  : : " + nesto);
		SimpleMailMessage message1 = new SimpleMailMessage();
		message1.setTo(nr.getAutor().getEmail());
		message1.setSubject("Obavestenje o relevantnosti rada");
		message1.setText("Vas rad je odbijen. Razlog: nije relevantan za naucnu oblast.");
//		emailSender.send(message1);
		
	}

}
