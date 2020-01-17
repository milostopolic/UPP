package root.demo.services;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import root.demo.model.Korisnik;
import root.demo.repository.KorisnikRepository;
@Service
public class UserActivationService implements JavaDelegate {
	
	@Autowired
	private KorisnikRepository krepo;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		Long id = (Long) execution.getVariable("korisnikId");
		Korisnik korisnik = krepo.getOne(id);
		korisnik.setAktiviran(true);
		korisnik.setEnabled(true);
		krepo.save(korisnik);
	}

}
