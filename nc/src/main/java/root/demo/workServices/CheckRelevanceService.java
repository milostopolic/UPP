package root.demo.workServices;

import java.util.List;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import root.demo.model.FormSubmissionDto;
import root.demo.model.NaucniRad;
import root.demo.repository.KorisnikRepository;
import root.demo.repository.NaucniRadRepository;
import root.demo.repository.RoleRepository;
@Service
public class CheckRelevanceService implements JavaDelegate {
	
	@Autowired
	private KorisnikRepository krepo;
	
	@Autowired
	private RoleRepository rrepo;
	
	@Autowired
	private NaucniRadRepository nrrepo;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		List<FormSubmissionDto> forma = (List<FormSubmissionDto>) execution.getVariable("relevance");
		NaucniRad nr = new NaucniRad();
		String title = "";
		Long idk = 0L;
		boolean potvrda = false;
		for (FormSubmissionDto formField : forma) {
			if (formField.getFieldId().equals("checkFormNaucniRadId")) {
				idk = Long.parseLong(formField.getFieldValue());
				nr = nrrepo.getOne(idk);
			}
			if (formField.getFieldId().equals("checkFormTitle")) {
				title = formField.getFieldValue();
			}			
			if(formField.getFieldId().equals("checkFormWorkRelevant")) {
				if(formField.getFieldValue().equals("true")) {
					potvrda = true;
				} else {
					potvrda = false;
				}	
			}
		}
//		korisnik = krepo.getOne(idk);
		execution.setVariable("checkFormWorkRelevant", potvrda);		
	}

}
