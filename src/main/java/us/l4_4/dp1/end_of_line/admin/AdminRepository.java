package us.l4_4.dp1.end_of_line.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends CrudRepository<Admin, Integer> {

    @Query("SELECT a FROM Admin a")
    public List<Admin> findAllAdmins();

    @Query("SELECT a FROM Admin a WHERE a.nickname = :nickname")
    Optional<Admin> findByNickname(String nickname);

    @Query("DELETE FROM Admin a WHERE a.nickname = :nickname")
    public void deleteAdmin(String nickname);

    @Query("SELECT COUNT(*) > 0 FROM Admin a WHERE a.nickname = ?1")
    public Boolean existsByNickname(String nickname);

    @Query("SELECT a FROM Admin a WHERE a.id = :id")
    public Optional<Admin> findById(Integer id);

    @Query("SELECT COUNT(*) > 0 FROM Admin a WHERE a.email = ?1")
    public Boolean existsByEmail(String email);

    @Query("SELECT a FROM Admin a WHERE a.authority.authority = :auth")
    List<Admin> findAllByAuthority(String auth);
}
