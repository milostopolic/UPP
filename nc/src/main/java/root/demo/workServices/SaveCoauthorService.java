package root.demo.workServices;

import java.util.List;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import root.demo.model.FormSubmissionDto;
import root.demo.model.Koautor;
import root.demo.model.NaucniRad;
import root.demo.repository.CasopisRepository;
import root.demo.repository.KoautorRepository;
import root.demo.repository.KorisnikRepository;
import root.demo.repository.NaucnaOblastRepository;
import root.demo.repository.NaucniRadRepository;

@Service
public class SaveCoauthorService implements JavaDelegate {

	@Autowired
	private CasopisRepository crepo;
	
	@Autowired
	private NaucnaOblastRepository norepo;
	
	@Autowired
	private KorisnikRepository krepo;
	
	@Autowired
	private NaucniRadRepository nrrepo;
	
	@Autowired
	private KoautorRepository korepo;
	
	

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		List<FormSubmissionDto> casopisForma = (List<FormSubmissionDto>) execution.getVariable("coauthorForm");
		NaucniRad nr = new NaucniRad();
		Long nid = (Long) execution.getVariable("nrId");
		nr = nrrepo.getOne(nid);
		
		Koautor ka = new Koautor();
		for (FormSubmissionDto formField : casopisForma) {
			if (formField.getFieldId().equals("coauthorFormName")) {
				if(formField.getFieldValue() != null) {
					ka.setIme(formField.getFieldValue());
				} else {
					break;
				}
			}
			if (formField.getFieldId().equals("coauthorFormEmail")) {
				if(formField.getFieldValue() != null) {
					ka.setEmail(formField.getFieldValue());
				} else {
					break;
				}
			}
			if (formField.getFieldId().equals("coauthorFormAddress")) {
				if(formField.getFieldValue() != null) {
					ka.setAdresa(formField.getFieldValue());
				} else {
					break;
				}
			}
		}
		
		nr.getKoautori().add(ka);
		korepo.save(ka);
		nrrepo.save(nr);
		
		Long broj = (Long) execution.getVariable("workFormCoauthors");
		execution.setVariable("workFormCoauthors", broj-1);
		
	}

}
