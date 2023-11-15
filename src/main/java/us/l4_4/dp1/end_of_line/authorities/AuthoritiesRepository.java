package us.l4_4.dp1.end_of_line.authorities;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import us.l4_4.dp1.end_of_line.player.Player;

public interface AuthoritiesRepository extends  CrudRepository<Authorities, String>{
	
	@Query("SELECT DISTINCT auth FROM Authorities auth WHERE auth.authority LIKE :authority%")
	Optional<Authorities> findByName(String authority);

	@Query("SELECT p FROM Player p WHERE p.authority.authority = :authority")
	List<Player> findAllByAuthority(String authority);
	
	@Query("SELECT DISTINCT auth FROM Authorities auth WHERE auth.id = :id")
	Optional<Authorities> findById(Integer id);
}
