package root.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import root.demo.model.RoleName;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO {
	private Long id;
	private RoleName name;


    public RoleDTO(RoleName name) {
        this.name = name;
    }
}
