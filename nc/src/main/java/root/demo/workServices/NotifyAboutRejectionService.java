package root.demo.workServices;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import root.demo.model.NaucniRad;
import root.demo.repository.KorisnikRepository;
import root.demo.repository.NaucniRadRepository;
@Service
public class NotifyAboutRejectionService implements JavaDelegate {

	@Autowired
	public JavaMailSender emailSender;
	
	@Autowired
	private NaucniRadRepository nrrepo;
	
	@Autowired
	private KorisnikRepository krepo;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		Long nesto = (Long) execution.getVariable("nrId");
		System.out.println("WWW  : : " + nesto);
		NaucniRad nr = nrrepo.getOne(nesto);
		System.out.println("VECERAO SI GA DS  : : " + nesto);
		SimpleMailMessage message1 = new SimpleMailMessage();
		message1.setTo(nr.getAutor().getEmail());
		message1.setSubject("Obavestenje o relevantnosti rada");
		message1.setText("Vas rad je odbijen. Razlog: nije relevantan za naucnu oblast.");
//		emailSender.send(message1);
		
	}

}
