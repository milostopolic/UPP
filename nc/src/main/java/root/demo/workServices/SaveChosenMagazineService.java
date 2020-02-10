package root.demo.workServices;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import root.demo.model.Casopis;
import root.demo.model.FormSubmissionDto;
import root.demo.model.NaucnaOblast;
import root.demo.repository.CasopisRepository;
import root.demo.repository.KorisnikRepository;
import root.demo.repository.NaucnaOblastRepository;

@Service
public class SaveChosenMagazineService implements JavaDelegate {
	
	@Autowired
	private CasopisRepository crepo;
	
	@Autowired
	private NaucnaOblastRepository norepo;
	
	@Autowired
	private KorisnikRepository krepo;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		List<FormSubmissionDto> casopisForma = (List<FormSubmissionDto>) execution.getVariable("casopisForm");
		execution.setVariable("openAccess", false);
		for (FormSubmissionDto formField : casopisForma) {
			if (formField.getFieldId().equals("chooseMagazineForm")) {
				for(String str : formField.getEnumi()) {
					execution.setVariable("odabraniCasopisId", str);
					System.out.println("123123 13   : " + str);
				}
					
					
				
			}
		}
		
		System.out.println("BRV GOTOVO JE OVO SRANJE");
		
	}

}
