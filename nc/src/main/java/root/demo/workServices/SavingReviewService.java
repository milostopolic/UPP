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
public class SavingReviewService implements JavaDelegate {

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
		List<FormSubmissionDto> casopisForma = (List<FormSubmissionDto>) execution.getVariable("recenzentovReview");
		
		List<String> listaKomentaraZaUrednika = (List<String>) execution.getVariable("listaKomentaraZaUrednika");
		List<String> listaKomentaraZaAutora = (List<String>) execution.getVariable("listaKomentaraZaAutora");
		List<String> listaPreporuka = (List<String>) execution.getVariable("listaPreporuka");
		
		for (FormSubmissionDto formField : casopisForma) {
			if (formField.getFieldId().equals("reviewingFormCommForAuthor")) {
				if(formField.getFieldValue() != null) {
					listaKomentaraZaAutora.add(formField.getFieldValue());
				} else {
					break;
				}
			}
			if (formField.getFieldId().equals("reviewingFormCommForEditor")) {
				if(formField.getFieldValue() != null) {
					listaKomentaraZaUrednika.add(formField.getFieldValue());
				} else {
					break;
				}
			}
			if (formField.getFieldId().equals("reviewingFormRecommendation")) {
				if(formField.getFieldValue() != null) {
					listaPreporuka.add(formField.getFieldValue());
				} else {
					break;
				}
			}
			
			if (formField.getFieldId().equals("newReviewingFormCommForAuthor")) {
				if(formField.getFieldValue() != null) {
					listaKomentaraZaAutora.add(formField.getFieldValue());
				} else {
					break;
				}
			}
			if (formField.getFieldId().equals("newReviewingFormCommForEditor")) {
				if(formField.getFieldValue() != null) {
					listaKomentaraZaUrednika.add(formField.getFieldValue());
				} else {
					break;
				}
			}
			if (formField.getFieldId().equals("newReviewingFormRecommendation")) {
				if(formField.getFieldValue() != null) {
					listaPreporuka.add(formField.getFieldValue());
				} else {
					break;
				}
			}
		}
		execution.setVariable("listaKomentaraZaUrednika", listaKomentaraZaUrednika);
		execution.setVariable("listaKomentaraZaAutora", listaKomentaraZaAutora);
		execution.setVariable("listaPreporuka", listaPreporuka);
		
	}

}
