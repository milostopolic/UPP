package root.demo.workServices;

import java.util.ArrayList;
import java.util.List;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import root.demo.model.FormSubmissionDto;
import root.demo.model.Korisnik;
import root.demo.repository.CasopisRepository;
import root.demo.repository.KorisnikRepository;
import root.demo.repository.NaucnaOblastRepository;
import root.demo.repository.NaucniRadRepository;

@Service
public class SaveNewReviewerService implements JavaDelegate {
	
	@Autowired
	private CasopisRepository crepo;
	
	@Autowired
	private NaucnaOblastRepository norepo;
	
	@Autowired
	private KorisnikRepository krepo;
	
	@Autowired
	private NaucniRadRepository nrrepo;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		System.out.println("USAO U SERVIS ZA SAVEREVIEWRES");
		List<FormSubmissionDto> casopisForma = (List<FormSubmissionDto>) execution.getVariable("newRevForm");
		List<String> reviewersList = new ArrayList<>();
		String novi = "";
		Korisnik k = new Korisnik();
		for (FormSubmissionDto formField : casopisForma) {
			if (formField.getFieldId().equals("newReviewerForm")) {
				for(String str : formField.getEnumi()) {
					novi = krepo.getOne(Long.parseLong(str)).getUsername();
				}
			}
			if (formField.getFieldId().equals("newReviewerFormDeadline")) {
				execution.setVariable("newReviewerFormDeadlineForUser", formField.getFieldValue());
				execution.setVariable("newReviewerFormDeadline", formField.getFieldValue() + "T12:00:00Z");
			}	

		}
		
		System.out.println("NOVI REVIEWIE IMAAA OVOLIKO: " + novi);
		execution.setVariable("newReviewerForm", novi);
		
		
	}

}
