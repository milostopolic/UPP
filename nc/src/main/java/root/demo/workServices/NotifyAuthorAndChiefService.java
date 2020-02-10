package root.demo.workServices;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import root.demo.model.Korisnik;
import root.demo.model.NaucniRad;
import root.demo.repository.KorisnikRepository;
import root.demo.repository.NaucniRadRepository;
@Service
public class NotifyAuthorAndChiefService implements JavaDelegate {

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
		System.out.println("ASKDJLSAK  : : " + nesto);
		SimpleMailMessage message1 = new SimpleMailMessage();
		message1.setTo(nr.getAutor().getEmail());
		message1.setSubject("Obavestenje o podnetom radu");
		message1.setText("Vas rad je podnet, ceka se na odobravanje.");
//		emailSender.send(message1);
		
		SimpleMailMessage message2 = new SimpleMailMessage();
		message2.setTo(nr.getCasopis().getGlavniUrednik().getEmail());
		message2.setSubject("Obavestenje o podnetom radu");
		message2.setText("Dodat je novi rad na odobravanje.");
//		emailSender.send(message2);
	}

}
