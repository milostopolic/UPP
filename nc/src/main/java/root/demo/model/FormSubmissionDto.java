package root.demo.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FormSubmissionDto implements Serializable{
	
	String fieldId;
	String fieldValue;
	List<String> enumi = new ArrayList();
	
	
	public FormSubmissionDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FormSubmissionDto(String fieldId, String fieldValue) {
		super();
		this.fieldId = fieldId;
		this.fieldValue = fieldValue;
	}

	public String getFieldId() {
		return fieldId;
	}

	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
	}

	public String getFieldValue() {
		return fieldValue;
	}

	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}

	public List<String> getEnumi() {
		return enumi;
	}

	public void setEnumi(List<String> enumi) {
		this.enumi = enumi;
	}

	
	
}
