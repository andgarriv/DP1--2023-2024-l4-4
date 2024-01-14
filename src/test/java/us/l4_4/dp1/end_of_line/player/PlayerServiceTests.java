package us.l4_4.dp1.end_of_line.player;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import jakarta.transaction.Transactional;
import us.l4_4.dp1.end_of_line.authorities.AuthoritiesService;
import us.l4_4.dp1.end_of_line.exceptions.ResourceNotFoundException;

//@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@SpringBootTest
@AutoConfigureTestDatabase
class PlayerServiceTests {

	@Autowired
	private PlayerService playerService;

	@Autowired
	private AuthoritiesService authoritiesService;

	private Player createPlayer(){
		LocalDate birthDate = LocalDate.of(1999, 01, 01);
		String avatar = "https://cdn-icons-png.flaticon.com/512/147/147144.png";

		Player player = new Player();
		player.setName("Sam");
		player.setSurname("Winter");
		player.setNickname("Samer");
		player.setPassword("Sam3r!");
		player.setEmail("sam@gmail.com");
		player.setBirthDate(birthDate);
		player.setAuthority(authoritiesService.findByAuthority("PLAYER"));
		player.setAvatar(avatar);

		return this.playerService.save(player);
	}

	@Test
	void shouldFindAllPlayers() {
		List<Player> players = (List<Player>) this.playerService.findAll();
		List<Player> players2 = this.authoritiesService.findAllByAuthority("PLAYER");
		assertEquals(20, players.size());
		assertEquals(players.size(), players2.size());
	}

	@Test
	void shouldFindAllAdmins() {
		List<Player> admins = this.authoritiesService.findAllByAuthority("ADMIN");
		assertEquals(5, admins.size());
	}

	@Test
	void shouldFindPlayerById() {
		Player player = this.playerService.findById(4);
		assertEquals("Jorge_ADD", player.getNickname());
	}

	@Test
	void shouldNotFindUserWithBadUserId() {
		assertThrows(ResourceNotFoundException.class, () -> this.playerService.findById(404));
	}

	@Test
	void shouldExistsUserByNickname() {
		assertEquals(true, this.playerService.existsByNickname("Angelgares"));
	}

	@Test
	void shouldNotExistUserByNickname() {
		assertEquals(false, this.playerService.existsByNickname("UserNotExists"));
	}

	@Test
	void shouldFindPlayerByNickname() {
		Player player = this.playerService.findByNickname("Angelgares");
		assertEquals("Angelgares", player.getNickname());
	}

	@Test
	void shouldNotFindUserWithBadUserNickname() {
		assertThrows(ResourceNotFoundException.class, () -> this.playerService.findByNickname("UserNotExists"));
	}

	@Test
	void shouldExistsUserByEmail() {
		assertEquals(true, this.playerService.existsByEmail("angelgares6424@gmail.com"));
	}

	@Test
	void shouldNotExistsUserByEmail() {
		assertEquals(false, this.playerService.existsByEmail("notexists@gmail.com"));
	}

	@Test
	@WithMockUser(username = "admin1", password = "Adm1n!")
	void shouldFindCurrentAdmin() {
		Player admin = this.playerService.findCurrentPlayer();
		assertEquals("admin1", admin.getNickname());
	}
  
	@Test
	@WithMockUser(username = "Angelgares", password = "Own3r!")
	void shouldFindCurrentPlayer() {
		Player player = this.playerService.findCurrentPlayer();
		assertEquals("Angelgares", player.getNickname());
	}

	@Test
	@WithMockUser(username = "prueba")
	void shouldNotFindCorrectCurrentPlayer() {
		assertThrows(ResourceNotFoundException.class, () -> this.playerService.findCurrentPlayer());
	}

	@Test
	void shouldNotFindAuthenticated() {
		assertThrows(ResourceNotFoundException.class, () -> this.playerService.findCurrentPlayer());
	}
 
	@Test
	@Transactional
	void shouldInsertUser() {
		int count = ((Collection<Player>) this.playerService.findAll()).size();
		createPlayer();
		int finalCount = ((Collection<Player>) this.playerService.findAll()).size();
		assertEquals(count + 1, finalCount);
	}

	@Test
	@Transactional
	void shouldUpdateUser() {
		Player player = this.playerService.findById(4);
		player.setNickname("Change");
		playerService.update(4, player);
		assertEquals("Change", player.getNickname());
	}

	@Test
	@Transactional
	void shouldDeleteUser() {
		Integer firstCount = ((Collection<Player>) playerService.findAll()).size();
		Player player = createPlayer();

		Integer secondCount = ((Collection<Player>) playerService.findAll()).size();
		assertEquals(firstCount + 1, secondCount);

		playerService.delete(player.getId());
		Integer lastCount = ((Collection<Player>) playerService.findAll()).size();
		assertEquals(firstCount, lastCount);
	}
}