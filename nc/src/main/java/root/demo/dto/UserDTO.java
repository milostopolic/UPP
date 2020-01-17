package root.demo.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import root.demo.model.Korisnik;
import root.demo.model.Role;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

	 Long id;
	 String name;
	 String surname;
	 String email;
	 String password;
	 boolean enabled;
	 boolean nonLocked;
	 List<RoleDTO> roles;
	

	public UserDTO(Long id, String name, String surname, String email, String password,
			boolean enabled, boolean nonLocked) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.password = password;
		this.enabled = enabled;
		this.nonLocked = nonLocked;
	}
	
	public UserDTO(Korisnik u) {
		this.id = u.getId();
		this.name = u.getIme();
		this.surname = u.getPrezime();
		this.email = u.getEmail();
		this.password = u.getPassword();
		this.enabled = u.isEnabled();
		this.nonLocked = u.isNonLocked();
		
		roles = new ArrayList<>();
		if(u.getRoles() != null) {
			for(Role r : u.getRoles()) {
				roles.add(new RoleDTO(r.getName()));
			}
		}
	}

	
	
}
