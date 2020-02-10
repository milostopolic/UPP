package root.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import root.demo.model.Koautor;

@Repository
public interface KoautorRepository extends JpaRepository<Koautor, Long> {

}
