package root.demo.workServices;

import java.util.List;

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
public class NotifyAboutChangesNeededService implements JavaDelegate {

	@Autowired
	public JavaMailSender emailSender;
	
	@Autowired
	private NaucniRadRepository nrrepo;
	
	@Autowired
	private KorisnikRepository krepo;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		Long nesto = (Long) execution.getVariable("nrId");
		List<String> komentari = (List<String>) execution.getVariable("listaKomentaraZaAutora");
		String kom = "";
		for(String str : komentari) {
			kom += "str ";
		}
		System.out.println("WWW  : : " + nesto);
		NaucniRad nr = nrrepo.getOne(nesto);
		System.out.println("TRERBA GA MENJATI SI GA DS  : : " + nesto);
		SimpleMailMessage message1 = new SimpleMailMessage();
		message1.setTo(nr.getAutor().getEmail());
		message1.setSubject("Obavestenje o potrebnim izmenama rada");
		message1.setText("Neophodne su izmene u vasem radu. Komentari recenzenata: " + kom);
//		emailSender.send(message1);
		
	}

}
