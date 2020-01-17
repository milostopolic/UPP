package root.demo.services;

import java.util.List;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import root.demo.model.FormSubmissionDto;
import root.demo.model.Korisnik;
import root.demo.model.RoleName;
import root.demo.repository.KorisnikRepository;
import root.demo.repository.RoleRepository;

@Service
public class ReviewerConfirmationService implements JavaDelegate {
	
	@Autowired
	private KorisnikRepository krepo;
	
	@Autowired
	private RoleRepository rrepo;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		List<FormSubmissionDto> forma = (List<FormSubmissionDto>) execution.getVariable("revconfirmation");
		Korisnik korisnik = new Korisnik();
		String usern = "";
		Long idk = 0L;
		boolean potvrda = false;
		for (FormSubmissionDto formField : forma) {
			if (formField.getFieldId().equals("recId")) {
				idk = Long.parseLong(formField.getFieldValue());
				korisnik = krepo.getOne(idk);
			}
			if (formField.getFieldId().equals("recUsername")) {
				usern = formField.getFieldValue();
			}			
			if(formField.getFieldId().equals("adminPotvrda")) {
				if(formField.getFieldValue().equals("true")) {
					potvrda = true;
				} else {
					potvrda = false;
				}	
			}
		}
//		korisnik = krepo.getOne(idk);
		if(potvrda) {
//			if(korisnik.getUsername().equals(usern)) {
//				korisnik.setRoles(Collections.singleton(rrepo.findByName(RoleName.REVIEWER)));
				korisnik.getRoles().add(rrepo.findByName(RoleName.REVIEWER));
//			}
		}
		
		krepo.save(korisnik);		
		
	}

}
