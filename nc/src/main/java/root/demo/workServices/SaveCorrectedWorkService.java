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
public class SaveCorrectedWorkService implements JavaDelegate {

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
		List<FormSubmissionDto> casopisForma = (List<FormSubmissionDto>) execution.getVariable("workCorrectedInfo");
		Long nid = (Long) execution.getVariable("nrId"); 
		NaucniRad nr = nrrepo.getOne(nid);
		
		for (FormSubmissionDto formField : casopisForma) {			
			if (formField.getFieldId().equals("correctingFormPdf")) {
				if(formField.getFieldValue() != null) {
					nr.setPutanja(formField.getFieldValue());
				} else {
					break;
				}
			}
		}
		
		nrrepo.save(nr);
		
	}

}

