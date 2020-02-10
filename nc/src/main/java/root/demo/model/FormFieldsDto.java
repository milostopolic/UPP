package root.demo.model;

import java.util.List;

import org.camunda.bpm.engine.form.FormField;

public class FormFieldsDto {
	String taskId;
	List<FormField> formFields;
	String processInstanceId;
	Long weed;

	public FormFieldsDto(String taskId, String processInstanceId, List<FormField> formFields) {
		super();
		this.taskId = taskId;
		this.formFields = formFields;
		this.processInstanceId = processInstanceId;
	}
	
	public FormFieldsDto(String taskId, List<FormField> formFields, String processInstanceId, Long weed) {
		super();
		this.taskId = taskId;
		this.formFields = formFields;
		this.processInstanceId = processInstanceId;
		this.weed = weed;
	}



	public FormFieldsDto() {
		super();
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public List<FormField> getFormFields() {
		return formFields;
	}

	public void setFormFields(List<FormField> formFields) {
		this.formFields = formFields;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public Long getWeed() {
		return weed;
	}

	public void setWeed(Long weed) {
		this.weed = weed;
	}
	
	
}
