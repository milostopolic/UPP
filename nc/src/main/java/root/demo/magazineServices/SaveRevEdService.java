package root.demo.magazineServices;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import root.demo.model.Casopis;
import root.demo.model.FormSubmissionDto;
import root.demo.repository.CasopisRepository;
import root.demo.repository.KorisnikRepository;

@Service
public class SaveRevEdService implements JavaDelegate {

	@Autowired
	private KorisnikRepository krepo;

	@Autowired
	private CasopisRepository crepo;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		List<FormSubmissionDto> casopisForma = (List<FormSubmissionDto>) execution.getVariable("ur");
		Casopis casopis = crepo.getOne((Long) execution.getVariable("casopisId"));
		casopis.setRecenzenti(new ArrayList<>());
		casopis.setUrednici(new ArrayList<>());
		for (FormSubmissionDto formField : casopisForma) {
			if (formField.getFieldId().equals("urednici")) {
				if(!formField.getFieldValue().equals("")) {
					List<String> no = Arrays.asList(formField.getFieldValue().split(","));
					for (String n : no) {
						casopis.getUrednici().add(krepo.getOne(Long.parseLong(n)));
					}
				}
			}
			if (formField.getFieldId().equals("recenzenti")) {
				List<String> no = Arrays.asList(formField.getFieldValue().split(","));
				for (String n : no) {
					casopis.getRecenzenti().add(krepo.getOne(Long.parseLong(n)));
				}
			}
		}
		
		crepo.save(casopis);
	}

}
