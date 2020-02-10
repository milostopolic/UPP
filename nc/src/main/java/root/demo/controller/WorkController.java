package root.demo.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import root.demo.dto.StringDTO;
import root.demo.model.FormFieldsDto;
import root.demo.model.FormSubmissionDto;
import root.demo.model.Korisnik;
import root.demo.model.NaucniRad;
import root.demo.model.TaskDto;
import root.demo.repository.CasopisRepository;
import root.demo.repository.KorisnikRepository;
import root.demo.repository.NaucniRadRepository;

@Controller
@RequestMapping("/work")
@CrossOrigin
public class WorkController {
	
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
	
	@Autowired
	NaucniRadRepository nrrepo;
	
	@Autowired
	private CasopisRepository crepo;
	
	private HashMap<String, Object> mapListToDto(List<FormSubmissionDto> list)
	{
		HashMap<String, Object> map = new HashMap<String, Object>();
		for(FormSubmissionDto temp : list){
			map.put(temp.getFieldId(), temp.getFieldValue());
		}
		
		return map;
	}
	
	@GetMapping(path = "/get/{uid}", produces = "application/json")
    public @ResponseBody FormFieldsDto get(@PathVariable Long uid) {
		Korisnik k = krepo.getOne(uid);
		
		ProcessInstance pi = runtimeService.startProcessInstanceByKey("Process_ObradaPodnetogTeksta");		
		Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).list().get(0);
		runtimeService.setVariable(pi.getId(), "initiator", k.getUsername());
		TaskFormData tfd = formService.getTaskFormData(task.getId());
		List<FormField> properties = tfd.getFormFields();
		for(FormField fp : properties) {
			System.out.println(fp.getId() + fp.getType());
		}
		
        return new FormFieldsDto(task.getId(), pi.getId(), properties);
    }
	
	@PostMapping(path = "/post/{taskId}/{uid}", produces = "application/json")
    public @ResponseBody ResponseEntity<?> post(@RequestBody List<FormSubmissionDto> dto, @PathVariable String taskId, @PathVariable String uid) {
		HashMap<String, Object> map = this.mapListToDto(dto);		
		
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();
		runtimeService.setVariable(processInstanceId, "casopisForm", dto);
		runtimeService.setVariable(processInstanceId, "uid", uid);
		
		formService.submitTaskForm(taskId, map);
        return new ResponseEntity<>(HttpStatus.OK);
    }
	
	@GetMapping(path = "/getNextUserTask/{id}", produces = "application/json")
    public @ResponseBody FormFieldsDto getUserTask(@PathVariable String id) {
		System.out.println("OOOOOO");
		Task task = taskService.createTaskQuery().processInstanceId(id).list().get(0);
		TaskFormData tfd = formService.getTaskFormData(task.getId());
		List<FormField> properties = tfd.getFormFields();
		for(FormField fp : properties) {
			System.out.println(fp.getId() + fp.getType());
		}
		
        return new FormFieldsDto(task.getId(), id, properties);
    }
	
//	@PostMapping(value="/uploadPDF")
//	public Path createRole(@RequestParam("pdf") MultipartFile file) throws IOException{
//		Path filePath = Paths.get("src/main/resources" + "/" + file.getOriginalFilename());
//	
//		Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
//		return filePath;
//	}
	
	@PostMapping(path = "/postWorkBasicInfo/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity postChooseMagazine(@RequestBody List<FormSubmissionDto> dto, @PathVariable String taskId) {
		HashMap<String, Object> map = this.mapListToDto(dto);		
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();
		runtimeService.setVariable(processInstanceId, "workBasicInfo", dto);
		formService.submitTaskForm(taskId, map);
        return new ResponseEntity<>(HttpStatus.OK);
    }
	
	@PostMapping(path = "/postCorrected/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity postCorrected(@RequestBody List<FormSubmissionDto> dto, @PathVariable String taskId) {
		HashMap<String, Object> map = this.mapListToDto(dto);		
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();
		runtimeService.setVariable(processInstanceId, "workCorrectedInfo", dto);
		formService.submitTaskForm(taskId, map);
        return new ResponseEntity<>(HttpStatus.OK);
    }
	
	@PostMapping(path = "/postMinor/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity postMinor(@RequestBody List<FormSubmissionDto> dto, @PathVariable String taskId) {
		HashMap<String, Object> map = this.mapListToDto(dto);		
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();
		runtimeService.setVariable(processInstanceId, "workMinor", dto);
		formService.submitTaskForm(taskId, map);
        return new ResponseEntity<>(HttpStatus.OK);
    }
	
	@PostMapping(path = "/postMajor/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity postMajor(@RequestBody List<FormSubmissionDto> dto, @PathVariable String taskId) {
		HashMap<String, Object> map = this.mapListToDto(dto);		
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();
		runtimeService.setVariable(processInstanceId, "workMajor", dto);
		formService.submitTaskForm(taskId, map);
        return new ResponseEntity<>(HttpStatus.OK);
    }
	
	@RequestMapping(value = "/uploadPDF", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<?> uploadFile(@RequestParam("File") MultipartFile request) {
		System.out.print("pogodio pdf");
		String returnValue ="";
		//Rad r = new Rad();
		//r = radService.storeFile(request);
		try {
			returnValue = saveImage(request);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new ResponseEntity<>(returnValue, HttpStatus.OK);

	}

	

	public String saveImage(MultipartFile file) throws IOException {
		String folder = "src/main/resources/";
		byte[] bytes = file.getBytes();
		Path path = Paths.get(folder + file.getOriginalFilename());
		System.out.println(path.toAbsolutePath());
		Files.write(path, bytes);
		return path.toAbsolutePath().toString();
	}
	
	@GetMapping(path = "/getChiefEditorsTasks", produces = "application/json")
	public @ResponseBody ResponseEntity<List<TaskDto>> getAdminTasks(){
		List<Task> tasks = new ArrayList<Task>();
		tasks.addAll(taskService.createTaskQuery().processDefinitionKey("Process_ObradaPodnetogTeksta").taskAssignee("chief").list());
		
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
		Long s = (Long) runtimeService.getVariable(task.getProcessInstanceId(),"nrId");
        return new FormFieldsDto(task.getId(), properites, id, s);
//		return new FormFieldsDto(task.getId(),task.getProcessInstanceId(),properites);
	}
	
	@PostMapping(path = "/postRelevance/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity<?> postRelevance(@RequestBody List<FormSubmissionDto> dto, @PathVariable String taskId) {
		HashMap<String, Object> map = this.mapListToDto(dto);
		
		
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();
		runtimeService.setVariable(processInstanceId, "relevance", dto);
		formService.submitTaskForm(taskId, map);
        return new ResponseEntity<>(HttpStatus.OK);
    }
	
	@PostMapping(path = "/postFormat/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity<?> postFormat(@RequestBody List<FormSubmissionDto> dto, @PathVariable String taskId) {
		HashMap<String, Object> map = this.mapListToDto(dto);
		
		
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();
		runtimeService.setVariable(processInstanceId, "format", dto);
		formService.submitTaskForm(taskId, map);
        return new ResponseEntity<>(HttpStatus.OK);
    }
	
	@PostMapping(path = "/postComment/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity<?> postComment(@RequestBody List<FormSubmissionDto> dto, @PathVariable String taskId) {
		HashMap<String, Object> map = this.mapListToDto(dto);
		
		
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();
		runtimeService.setVariable(processInstanceId, "commentForm", dto);
		formService.submitTaskForm(taskId, map);
        return new ResponseEntity<>(HttpStatus.OK);
    }
	
	@GetMapping(path = "/getMyTasks/{uid}", produces = "application/json")
	public @ResponseBody ResponseEntity<List<TaskDto>> getMyTasks(@PathVariable Long uid){
		List<Task> tasks = new ArrayList<Task>();
		tasks.addAll(taskService.createTaskQuery().processDefinitionKey("Process_ObradaPodnetogTeksta").taskAssignee(krepo.getOne(uid).getUsername()).list());
		tasks.addAll(taskService.createTaskQuery().processDefinitionKey("Process_Registracija").taskAssignee(krepo.getOne(uid).getUsername()).list());
		tasks.addAll(taskService.createTaskQuery().processDefinitionKey("Process_DodavanjeCasopisa").taskAssignee(krepo.getOne(uid).getUsername()).list());
		
		List<TaskDto> taskDTO = new ArrayList<TaskDto>();
		for(Task task : tasks) {
			TaskDto ts = new TaskDto(task.getId(), task.getName(), task.getAssignee());
			taskDTO.add(ts);
		}
		return new ResponseEntity<List<TaskDto>>(taskDTO, HttpStatus.OK);
	}
	
	@PostMapping(path = "/postChosenReviewers/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity<?> postChosenReviewers(@RequestBody List<FormSubmissionDto> dto, @PathVariable String taskId) {
		HashMap<String, Object> map = this.mapListToDto(dto);
		System.out.println("POST CHOESN REVIEWRES CONTROLLER USAP");
		
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();
		runtimeService.setVariable(processInstanceId, "revsForm", dto);
		formService.submitTaskForm(taskId, map);
        return new ResponseEntity<>(HttpStatus.OK);
    }
	
	@PostMapping(path = "/postNewRev/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity<?> postNewRev(@RequestBody List<FormSubmissionDto> dto, @PathVariable String taskId) {
		HashMap<String, Object> map = this.mapListToDto(dto);
		System.out.println("POST CHOESN REVIEWRES CONTROLLER USAP");
		
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();
		runtimeService.setVariable(processInstanceId, "newRevForm", dto);
		formService.submitTaskForm(taskId, map);
        return new ResponseEntity<>(HttpStatus.OK);
    }
	
	@PostMapping(path = "/postDeadline/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity<?> postDeadline(@RequestBody List<FormSubmissionDto> dto, @PathVariable String taskId) {
		HashMap<String, Object> map = this.mapListToDto(dto);
		System.out.println("POST CHOESN REVIEWRES CONTROLLER USAP");
		
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();
		runtimeService.setVariable(processInstanceId, "deadlineForm", dto);
		formService.submitTaskForm(taskId, map);
        return new ResponseEntity<>(HttpStatus.OK);
    }
	
	@PostMapping(path = "/postYourReview/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity<?> postYourReview(@RequestBody List<FormSubmissionDto> dto, @PathVariable String taskId) {
		HashMap<String, Object> map = this.mapListToDto(dto);
		System.out.println("POST CHOESN REVIEWRES CONTROLLER USAP");
		
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();
		runtimeService.setVariable(processInstanceId, "recenzentovReview", dto);
		formService.submitTaskForm(taskId, map);
        return new ResponseEntity<>(HttpStatus.OK);
    }
	
	@GetMapping("/pdfname/{wid}")
	public ResponseEntity<StringDTO> getp(@PathVariable Long wid) {
		NaucniRad r = nrrepo.getOne(wid);
		return new ResponseEntity<StringDTO>(new StringDTO(r.getPutanja()), HttpStatus.OK);
	}
	
	@PostMapping(path = "/postCoauthor/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity postCoauthor(@RequestBody List<FormSubmissionDto> dto, @PathVariable String taskId) {
		HashMap<String, Object> map = this.mapListToDto(dto);		
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();
		runtimeService.setVariable(processInstanceId, "coauthorForm", dto);
		formService.submitTaskForm(taskId, map);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
