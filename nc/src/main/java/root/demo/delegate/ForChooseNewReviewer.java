package root.demo.delegate;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import root.demo.model.Korisnik;
import root.demo.model.NaucniRad;
import root.demo.repository.KorisnikRepository;
import root.demo.repository.NaucniRadRepository;

@Service
public class ForChooseNewReviewer implements TaskListener{
	
	@Autowired
	private NaucniRadRepository nrrepo;
	
	@Autowired
	private KorisnikRepository krepo;

	@SuppressWarnings("unchecked")
	@Override
	public void notify(DelegateTask delegateTask) {
		// TODO Auto-generated method stub
		System.out.println("usao u delegate");
		TaskFormData taskFormData = delegateTask.getExecution().getProcessEngineServices().getFormService().getTaskFormData(delegateTask.getId());
		NaucniRad nr = nrrepo.getOne((Long)delegateTask.getExecution().getVariable("nrId"));
		List<BigInteger> revids = krepo.nadjiRecenzenteNaucneOblasti(nr.getNaucnaOblast().getId(), nr.getCasopis().getId());
		List<FormField> formFields = taskFormData. getFormFields();
		if(formFields!=null){
		   for(FormField field : formFields){
		       if( field.getId().equals("newReviewerForm")){
		           HashMap<String, String> items = (HashMap<String, String>) field.getType().getInformation("values");
		           items.clear();
		           for(BigInteger r : revids){
		        	   Korisnik k = krepo.getOne(r.longValue());
		        	   String ret = k.getIme() + " " + k.getPrezime();
		               items.put(r.toString(), ret);
		           }
		       }
		   }
		}
		
	}

}