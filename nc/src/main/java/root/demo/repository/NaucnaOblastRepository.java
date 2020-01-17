package root.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import root.demo.model.NaucnaOblast;

@Repository
public interface NaucnaOblastRepository extends JpaRepository<NaucnaOblast, Long>{

}
