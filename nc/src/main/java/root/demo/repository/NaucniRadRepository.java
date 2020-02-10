package root.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import root.demo.model.NaucniRad;

@Repository
public interface NaucniRadRepository extends JpaRepository<NaucniRad, Long> {

}
