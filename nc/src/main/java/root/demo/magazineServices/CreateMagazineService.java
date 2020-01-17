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
import root.demo.model.NaucnaOblast;
import root.demo.repository.CasopisRepository;
import root.demo.repository.KorisnikRepository;
import root.demo.repository.NaucnaOblastRepository;
@Service
public class CreateMagazineService implements JavaDelegate {
	
	@Autowired
	private CasopisRepository crepo;
	
	@Autowired
	private NaucnaOblastRepository norepo;
	
	@Autowired
	private KorisnikRepository krepo;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		List<FormSubmissionDto> casopisForma = (List<FormSubmissionDto>) execution.getVariable("casopisForma");
		Casopis casopis = new Casopis();
		casopis.setNaucneOblasti(new ArrayList<NaucnaOblast>());
		boolean validation = true;
		
		for (FormSubmissionDto formField : casopisForma) {
			if (formField.getFieldId().equals("naziv")) {
				if(formField.getFieldValue() != null) {
					casopis.setNaziv(formField.getFieldValue());
				} else {
					validation = false;
					break;
				}
			}	
			if (formField.getFieldId().equals("issn")) {
				if(formField.getFieldValue() != null) {
					casopis.setIssn(formField.getFieldValue());
				} else {
					validation = false;
					break;
				}
			}	
			if (formField.getFieldId().equals("nacinPlacanja")) {
				if(formField.getFieldValue() != null) {
					casopis.setNacinPlacanja(formField.getFieldValue());
				} else {
					validation = false;
					break;
				}
			}	
			if (formField.getFieldId().equals("naucneOblasti")) {
				if(formField.getFieldValue() != null) {
					List<String> no = Arrays.asList(formField.getFieldValue().split(","));
					for(String n : no) {
						casopis.getNaucneOblasti().add(norepo.getOne(Long.parseLong(n)));
					}
				} else {
					validation = false;
					break;
				}
			}
		}
		
		String korid =  (String) execution.getVariable("uid");
		
		casopis.setGlavniUrednik(krepo.getOne(Long.parseLong(korid)));
		
		if(validation) {
			execution.setVariable("validation", true);
			crepo.save(casopis);
			execution.setVariable("casopisId", casopis.getId());
		} else {
			execution.setVariable("validation", false);
		}
		
		
	}

}
