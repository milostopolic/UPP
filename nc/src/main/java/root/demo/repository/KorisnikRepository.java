package root.demo.repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import root.demo.model.Korisnik;

@Repository
public interface KorisnikRepository extends JpaRepository<Korisnik, Long> {
	
	@EntityGraph(value = "Korisnik.Roles.Permissions")
	Optional<Korisnik> findByEmail(String email);
	
	boolean existsByEmail(String username);
	
	Korisnik findByUsername(String u);

	@Query("select u from Korisnik u where u.id = ?#{principal.id}")
	Korisnik findCurrentUser();
	
	@Query("select u from Korisnik u where u.id != ?#{principal.id}")
	List<Korisnik> findUsersExceptSelf();
	
	@Modifying
	@Query(value = "select * from korisnik k, korisnik_roles cr where cr.role_id=2 and k.id=cr.korisnik_id", nativeQuery=true)
	List<Korisnik> findMagazineReviewers();
	
	@Modifying
	@Query(value = "select * from korisnik k, korisnik_roles cr where cr.role_id=3 and k.id=cr.korisnik_id", nativeQuery = true)
	List<Korisnik> findMagazineEditors();
	
	@Modifying
	@Query(value = "SELECT kr.korisnik_id FROM  casopis_urednici cu, korisnik_roles kr, korisnik_naucna_oblast kno where kr.role_id=3 and kr.korisnik_id=cu.korisnik_id and cu.casopis_id=?2 and kno.korisnik_id=cu.korisnik_id and kno.naucna_oblast_id=?1", nativeQuery = true)
	List<BigInteger> nadjiUrednikeNaucneOblasti(Long noid, Long cid);
	
	@Modifying
	@Query(value = "SELECT kr.korisnik_id FROM  casopis_recenzenti cu, korisnik_roles kr, korisnik_naucna_oblast kno where kr.role_id=2 and kr.korisnik_id=cu.korisnik_id and cu.casopis_id=?2 and kno.korisnik_id=cu.korisnik_id and kno.naucna_oblast_id=?1", nativeQuery = true)
	List<BigInteger> nadjiRecenzenteNaucneOblasti(Long noid, Long cid);
}
