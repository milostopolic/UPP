package root.demo.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequestDTO {
	@Email
	@NotBlank
	@Size(min = 3)
	private String email;

	@NotBlank
	private String password;

	@NotBlank
	private String confirmPassword;
	
	
}
