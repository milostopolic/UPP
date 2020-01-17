package root.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import root.demo.model.Role;
import root.demo.model.RoleName;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

	Role findByName(RoleName name);
	
	boolean existsByName(RoleName name);
	
}
