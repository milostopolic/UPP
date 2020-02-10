package root.demo.workServices;

import java.util.ArrayList;
import java.util.List;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import root.demo.model.FormSubmissionDto;
import root.demo.repository.KorisnikRepository;
import root.demo.repository.NaucniRadRepository;
@Service
public class SetReviewingDeadlineService implements JavaDelegate {

	@Autowired
	public JavaMailSender emailSender;
	
	@Autowired
	private NaucniRadRepository nrrepo;
	
	@Autowired
	private KorisnikRepository krepo;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		List<FormSubmissionDto> forma = (List<FormSubmissionDto>) execution.getVariable("deadlineForm");
		boolean potvrda = false;
		for (FormSubmissionDto formField : forma) {
			if (formField.getFieldId().equals("reviewingDeadline")) {
				execution.setVariable("reviewingDeadlineForUser", formField.getFieldValue());
				execution.setVariable("reviewingDeadline", formField.getFieldValue() + "T12:00:00Z");
			}			
		}
		
		System.out.println("DEADLINE SET : " + execution.getVariable("reviewingDeadline"));
		
		List<String> listaKomentaraZaUrednika = new ArrayList<>();
		List<String> listaKomentaraZaAutora = new ArrayList<>();
		List<String> listaPreporuka = new ArrayList<>();
		
		execution.setVariable("listaKomentaraZaUrednika", listaKomentaraZaUrednika);
		execution.setVariable("listaKomentaraZaAutora", listaKomentaraZaAutora);
		execution.setVariable("listaPreporuka", listaPreporuka);
	}

}
