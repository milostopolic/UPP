package root.demo.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import root.demo.model.FormSubmissionDto;
import root.demo.model.Korisnik;
import root.demo.model.NaucnaOblast;
import root.demo.model.Role;
import root.demo.model.RoleName;
import root.demo.repository.KorisnikRepository;
import root.demo.repository.NaucnaOblastRepository;
import root.demo.repository.RoleRepository;

@Service
public class UserCreationService implements JavaDelegate {

	@Autowired
	IdentityService identityService;
	@Autowired
	private KorisnikRepository krepo;
	@Autowired
	private NaucnaOblastRepository norepo;
	@Autowired
	private RoleRepository rrepo;
	@Autowired
    PasswordEncoder encoder;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		List<FormSubmissionDto> registration = (List<FormSubmissionDto>) execution.getVariable("registration");
		boolean validation = true;
		User user = identityService.newUser("");
		Korisnik korisnik = new Korisnik();
		korisnik.setNaucneOblasti(new ArrayList<NaucnaOblast>());
		korisnik.setRoles(new HashSet<Role>());
		korisnik.setNonLocked(true);
   		
		korisnik.getRoles().add(rrepo.findByName(RoleName.USER));
		for (FormSubmissionDto formField : registration) {
			if (formField.getFieldId().equals("username")) {
				if(formField.getFieldValue() != null) {
					user.setId(formField.getFieldValue());
					korisnik.setUsername(formField.getFieldValue());
				} else {
					validation = false;
					break;
				}
			}
			if (formField.getFieldId().equals("password")) {
				if(formField.getFieldValue() != null) {
					user.setPassword(formField.getFieldValue());
					korisnik.setPassword(encoder.encode(formField.getFieldValue()));
				} else {
					validation = false;
					break;
				}
			}
			if (formField.getFieldId().equals("ime")) {
				if(formField.getFieldValue() != null) {
					korisnik.setIme(formField.getFieldValue());
				} else {
					validation = false;
					break;
				}
			}
			if (formField.getFieldId().equals("prezime")) {
				if(formField.getFieldValue() != null) {
					korisnik.setPrezime(formField.getFieldValue());
				} else {
					validation = false;
					break;
				}
			}
			if (formField.getFieldId().equals("grad")) {
				if(formField.getFieldValue() != null) {
					korisnik.setGrad(formField.getFieldValue());
				} else {
					validation = false;
					break;
				}
			}
			if (formField.getFieldId().equals("drzava")) {
				if(formField.getFieldValue() != null) {
					korisnik.setDrzava(formField.getFieldValue());
				} else {
					validation = false;
					break;
				}
			}
			if (formField.getFieldId().equals("email")) {
				if(formField.getFieldValue() != null) {
					korisnik.setEmail(formField.getFieldValue());
				} else {
					validation = false;
					break;
				}
			}
			if (formField.getFieldId().equals("recenzent")) {
				if(formField.getFieldValue() != null) {
					if(formField.getFieldValue().equals("true")) {
						korisnik.setRecenzent(true);
					} else {
						korisnik.setRecenzent(false);
					}					
				} else {
					validation = false;
					break;
				}
			}
			if (formField.getFieldId().equals("titula")) {
				if(formField.getFieldValue() != null) {
					korisnik.setTitula(formField.getFieldValue());
				} else {
					validation = false;
					break;
				}
			}
			if (formField.getFieldId().equals("naucneOblasti")) {
				if(formField.getFieldValue() != null) {
					List<String> no = Arrays.asList(formField.getFieldValue().split(","));
					for(String n : no) {
						korisnik.getNaucneOblasti().add(norepo.getOne(Long.parseLong(n)));
					}
				} else {
					validation = false;
					break;
				}
			}
		}
		
		if(validation) {
			execution.setVariable("validation", true);
			execution.setVariable("email", korisnik.getEmail());
			identityService.saveUser(user);
			krepo.save(korisnik);
			execution.setVariable("korisnikId", korisnik.getId());
			execution.setVariable("korisnikUsername", korisnik.getUsername());
		} else {
			execution.setVariable("validation", false);
		}
		
	}
}
