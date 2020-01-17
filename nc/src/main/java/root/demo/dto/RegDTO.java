package root.demo.dto;

import java.util.List;

import root.demo.model.FormSubmissionDto;

public class RegDTO {
	
	private List<FormSubmissionDto> fsdto; 
	
	private List<Long> selectedValues;

	public RegDTO() {
		super();
	}

	public RegDTO(List<FormSubmissionDto> fsdto, List<Long> selectedValues) {
		super();
		this.fsdto = fsdto;
		this.selectedValues = selectedValues;
	}

	public List<FormSubmissionDto> getFsdto() {
		return fsdto;
	}

	public void setFsdto(List<FormSubmissionDto> fsdto) {
		this.fsdto = fsdto;
	}

	public List<Long> getSelectedValues() {
		return selectedValues;
	}

	public void setSelectedValues(List<Long> selectedValues) {
		this.selectedValues = selectedValues;
	}
	
	

}
