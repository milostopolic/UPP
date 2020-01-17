package root.demo.magazineServices;

import java.util.List;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import root.demo.model.Casopis;
import root.demo.model.FormSubmissionDto;
import root.demo.repository.CasopisRepository;
@Service
public class ActivateMagazineService implements JavaDelegate{
	
	@Autowired
	private CasopisRepository crepo;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		List<FormSubmissionDto> forma = (List<FormSubmissionDto>) execution.getVariable("magconfirmation");
		Casopis casopis = new Casopis();
		String casname = "";
		Long idk = 0L;
		boolean potvrda = false;
		for (FormSubmissionDto formField : forma) {
			if (formField.getFieldId().equals("potvrdaId")) {
				idk = Long.parseLong(formField.getFieldValue());
				casopis = crepo.getOne(idk);
			}
			if (formField.getFieldId().equals("potvrdaNaziv")) {
				casname = formField.getFieldValue();
			}			
			if(formField.getFieldId().equals("potvrdaAktivacija")) {
				if(formField.getFieldValue().equals("true")) {
					potvrda = true;
				} else {
					potvrda = false;
				}	
			}
		}
//		casopis = crepo.getOne(idk);
		if(potvrda) {
//			if(casopis.getcasnameame().equals(casname)) {
//				casopis.setRoles(Collections.singleton(rrepo.findByName(RoleName.REVIEWER)));
				casopis.setAktiviran(true);
//			}
		}
		
		crepo.save(casopis);	
	}

}
