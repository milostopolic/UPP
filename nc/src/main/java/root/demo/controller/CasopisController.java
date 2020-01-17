package root.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import root.demo.dto.RecUrednikDTO;
import root.demo.model.FormFieldsDto;
import root.demo.model.FormSubmissionDto;
import root.demo.model.Korisnik;
import root.demo.model.TaskDto;
import root.demo.repository.KorisnikRepository;

@Controller
@RequestMapping("/casopis")
@CrossOrigin
public class CasopisController {
	
	@Autowired
	IdentityService identityService;
	
	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	private KorisnikRepository krepo;
	
	@Autowired
	TaskService taskService;
	
	@Autowired
	FormService formService;
	
	private HashMap<String, Object> mapListToDto(List<FormSubmissionDto> list)
	{
		HashMap<String, Object> map = new HashMap<String, Object>();
		for(FormSubmissionDto temp : list){
			map.put(temp.getFieldId(), temp.getFieldValue());
		}
		
		return map;
	}
	
	@GetMapping(path = "/get", produces = "application/json")
    public @ResponseBody FormFieldsDto get() {
		ProcessInstance pi = runtimeService.startProcessInstanceByKey("Process_DodavanjeCasopisa");
		
		Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).list().get(0);
		
		TaskFormData tfd = formService.getTaskFormData(task.getId());
		List<FormField> properties = tfd.getFormFields();
		for(FormField fp : properties) {
			System.out.println(fp.getId() + fp.getType());
		}
		
        return new FormFieldsDto(task.getId(), pi.getId(), properties);
    }
	
	@PostMapping(path = "/post/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity<?> post(@RequestBody List<FormSubmissionDto> dto, @PathVariable String taskId) {
		HashMap<String, Object> map = this.mapListToDto(dto);		
		
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();
		runtimeService.setVariable(processInstanceId, "casopisForma", dto);
		formService.submitTaskForm(taskId, map);
        return new ResponseEntity<>(HttpStatus.OK);
    }
	
	@GetMapping(path = "/getRevsAndEditors/{processId}", produces = "application/json")
    public @ResponseBody FormFieldsDto getRaE(@PathVariable String processId) {
		
		Task task = taskService.createTaskQuery().processInstanceId(processId).list().get(0);
		TaskFormData tfd = formService.getTaskFormData(task.getId());
		List<FormField> properties = tfd.getFormFields();
		for(FormField fp : properties) {
			System.out.println(fp.getId() + fp.getType());
		}
		
        return new FormFieldsDto(task.getId(), processId, properties);
    }
	
	@GetMapping(path = "/getMagazineEditors/{cid}", produces = "application/json")
    public @ResponseBody ResponseEntity<List<RecUrednikDTO>> getMagEditors(@PathVariable String cid) {
		List<Korisnik> kor = krepo.findMagazineEditors();
		List<RecUrednikDTO> r = new ArrayList<>();
		for(Korisnik k : kor) {
			r.add(new RecUrednikDTO(k));
		}
		
		return new ResponseEntity<List<RecUrednikDTO>>(r, HttpStatus.OK);
    }
	
	@GetMapping(path = "/getMagazineReviewers/{cid}", produces = "application/json")
    public @ResponseBody ResponseEntity<List<RecUrednikDTO>> getMagReviewers(@PathVariable String cid) {
		List<Korisnik> kor = krepo.findMagazineReviewers();
		List<RecUrednikDTO> r = new ArrayList<>();
		for(Korisnik k : kor) {
			r.add(new RecUrednikDTO(k));
		}
		
		return new ResponseEntity<List<RecUrednikDTO>>(r, HttpStatus.OK);
    }
	
	@PostMapping(path = "/postRevsAndEditors/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity<?> postRaE(@RequestBody List<FormSubmissionDto> dto, @PathVariable String taskId) {
		
		HashMap<String, Object> map = this.mapListToDto(dto);		
		
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();
		runtimeService.setVariable(processInstanceId, "ur", dto);
		formService.submitTaskForm(taskId, map);
        return new ResponseEntity<>(HttpStatus.OK);
    }
	
	@GetMapping(path = "/getAdminTasks", produces = "application/json")
	public @ResponseBody ResponseEntity<List<TaskDto>> getAdminTasks(){
		List<Task> tasks = new ArrayList<Task>();
		tasks.addAll(taskService.createTaskQuery().processDefinitionKey("Process_DodavanjeCasopisa").taskAssignee("demo").list());
		
		List<TaskDto> taskDTO = new ArrayList<TaskDto>();
		for(Task task : tasks) {
			TaskDto ts = new TaskDto(task.getId(), task.getName(), task.getAssignee());
			taskDTO.add(ts);
		}
		return new ResponseEntity<List<TaskDto>>(taskDTO, HttpStatus.OK);
	}
	
	@GetMapping(path = "/getTask/{id}", produces = "application/json")  
    public @ResponseBody FormFieldsDto getOneTask(@PathVariable String id) {
		Task task = taskService.createTaskQuery().taskId(id).singleResult();
		TaskFormData tfData = formService.getTaskFormData(task.getId());
		
		List<FormField> properites = tfData.getFormFields();
		
		return new FormFieldsDto(task.getId(),task.getProcessInstanceId(),properites);
	}
	
	@PostMapping(path = "/postMagConfirmation/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity<?> postMagConfirmation(@RequestBody List<FormSubmissionDto> dto, @PathVariable String taskId) {
		HashMap<String, Object> map = this.mapListToDto(dto);
		
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();
		runtimeService.setVariable(processInstanceId, "magconfirmation", dto);
		formService.submitTaskForm(taskId, map);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
