package root.demo.workServices;

import java.util.ArrayList;
import java.util.List;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import root.demo.model.FormSubmissionDto;
import root.demo.model.NaucniRad;
import root.demo.repository.CasopisRepository;
import root.demo.repository.KorisnikRepository;
import root.demo.repository.NaucnaOblastRepository;
import root.demo.repository.NaucniRadRepository;

@Service
public class SaveReviewersService implements JavaDelegate {
	
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
		List<FormSubmissionDto> casopisForma = (List<FormSubmissionDto>) execution.getVariable("revsForm");
		List<String> reviewersList = new ArrayList<>();
		for (FormSubmissionDto formField : casopisForma) {
			if (formField.getFieldId().equals("reviewersForm")) {
				for(String str : formField.getEnumi()) {
					reviewersList.add(krepo.getOne(Long.parseLong(str)).getUsername());
				}
			}

		}
		
		System.out.println("REVIEWERA IMAAA OVOLIKO: " + reviewersList.size() + " " + reviewersList.get(0));
		execution.setVariable("reviewersList", reviewersList);
		
		
	}

}
