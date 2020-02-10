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
public class SaveWorkBasicInfoService implements JavaDelegate {

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
		List<FormSubmissionDto> casopisForma = (List<FormSubmissionDto>) execution.getVariable("workBasicInfo");
		NaucniRad nr = new NaucniRad();
		nr.setKoautori(new ArrayList());
		
		for (FormSubmissionDto formField : casopisForma) {
			if (formField.getFieldId().equals("workFormScientificArea")) {
				for(String str : formField.getEnumi()) {
					nr.setNaucnaOblast(norepo.getOne(Long.valueOf(str)));
				}
			}
			if (formField.getFieldId().equals("workFormTitle")) {
				if(formField.getFieldValue() != null) {
					nr.setTitle(formField.getFieldValue());
				} else {
					break;
				}
			}
			if (formField.getFieldId().equals("workFormKeywords")) {
				if(formField.getFieldValue() != null) {
					nr.setKljucniPojmovi(formField.getFieldValue());
				} else {
					break;
				}
			}
			if (formField.getFieldId().equals("workFormAbstract")) {
				if(formField.getFieldValue() != null) {
					nr.setApstrakt(formField.getFieldValue());
				} else {
					break;
				}
			}
			if (formField.getFieldId().equals("workFormCoauthors")) {
				if(formField.getFieldValue() != null) {
					nr.setBrojKoautora(Integer.parseInt(formField.getFieldValue()));
				} else {
					break;
				}
			}
			if (formField.getFieldId().equals("workFormPdf")) {
				if(formField.getFieldValue() != null) {
					nr.setPutanja(formField.getFieldValue());
				} else {
					break;
				}
			}
		}
		Long cid = Long.valueOf((String) execution.getVariable("odabraniCasopisId")); 
		nr.setCasopis(crepo.getOne(cid));
		nr.setAutor(krepo.getOne(Long.valueOf((String) execution.getVariable("uid"))));
		nrrepo.save(nr);
		execution.setVariable("nrId", nr.getId());
		execution.setVariable("chiefEditor", nr.getCasopis().getGlavniUrednik().getUsername());
		execution.setVariable("workFormTitle", nr.getTitle());
		execution.setVariable("workFormAbstract", nr.getApstrakt());
		execution.setVariable("workFormKeywords", nr.getKljucniPojmovi());
		
	}

}
