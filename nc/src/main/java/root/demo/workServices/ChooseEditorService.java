package root.demo.workServices;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.node.BigIntegerNode;

import root.demo.model.Korisnik;
import root.demo.model.NaucniRad;
import root.demo.repository.KorisnikRepository;
import root.demo.repository.NaucniRadRepository;

@Service
public class ChooseEditorService implements JavaDelegate {

	@Autowired
	public JavaMailSender emailSender;
	
	@Autowired
	private NaucniRadRepository nrrepo;
	
	@Autowired
	private KorisnikRepository krepo;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		System.out.println("DOSAO JE DOVDE !@#!@#!@");
		Long nid = (Long) execution.getVariable("nrId");
		NaucniRad nr = nrrepo.getOne(nid);
		System.out.println("NAUCNI RAD: " + nr.getId() + " " + nr.getNaucnaOblast().getId() + " " + nr.getCasopis().getId());
		List<BigInteger> urednici = new ArrayList<>();
		urednici = krepo.nadjiUrednikeNaucneOblasti(nr.getNaucnaOblast().getId(), nr.getCasopis().getId());
		System.out.println("EO IHH : " + urednici.size() + " A SDAS  " + urednici.get(0));
		
		if(urednici.size() == 0) {
			execution.setVariable("chosenEditor", nr.getCasopis().getGlavniUrednik().getUsername());
			System.out.println("CHOSEN EDITOR JE : " + nr.getCasopis().getGlavniUrednik().getUsername());
		} else {
			execution.setVariable("chosenEditor", krepo.getOne(urednici.get(0).longValue()).getUsername());
			System.out.println("CHOSEN EDITOR JE : " + krepo.getOne(urednici.get(0).longValue()).getUsername());
		}
		
		List<BigInteger> recenzenti = new ArrayList<>();
		recenzenti = krepo.nadjiRecenzenteNaucneOblasti(nr.getNaucnaOblast().getId(), nr.getCasopis().getId());
		System.out.println("EO  RECEC IHH : " + recenzenti.size());
		
		if(recenzenti.size() < 2) {
			execution.setVariable("gotReviewers", false);
			List<String> reviewersList = new ArrayList<>();
			reviewersList.add(nr.getCasopis().getGlavniUrednik().getUsername());
			execution.setVariable("reviewersList", reviewersList);
			System.out.println("NEMA DOVOLJNO RECENZENATA ");
		} else {
			execution.setVariable("gotReviewers", true);
			System.out.println("IMAAA DOVOLJNO RECENZENATA ");
		}
		
		
		
	}

}
