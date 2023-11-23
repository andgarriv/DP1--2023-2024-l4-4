package us.l4_4.dp1.end_of_line.user;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.samples.petclinic.owner.Owner;
//import org.springframework.samples.petclinic.owner.OwnerService;
//import org.springframework.samples.petclinic.vet.VetService;
import org.springframework.security.test.context.support.WithMockUser;

import jakarta.transaction.Transactional;
import us.l4_4.dp1.end_of_line.authorities.AuthoritiesService;
import us.l4_4.dp1.end_of_line.exceptions.ResourceNotFoundException;
import us.l4_4.dp1.end_of_line.player.Player;
import us.l4_4.dp1.end_of_line.player.PlayerService;

//@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@SpringBootTest
@AutoConfigureTestDatabase
class UserServiceTests {

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
		player.setAuthority(authoritiesService.findByAuthority("ADMIN"));
		player.setAvatar(avatar);
		return this.playerService.savePlayer(player);
	}

	@Test
	@WithMockUser(username = "admin1", password = "Adm1n!")
	void shouldFindCurrentAdmin() {
		Player player = this.playerService.findCurrentPlayer();
		assertEquals("admin1", player.getNickname());
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
	void shouldFindAllPlayers() {
		List<Player> players = (List<Player>) this.playerService.findAllPlayers();
		assertEquals(6, players.size());
	}
 
	@Test
	void shouldFindPlayersByUsername() {
		Player player = this.playerService.findByNickname("Angelgares");
		assertEquals("Angelgares", player.getNickname());
	}

	@Test
	void shouldFindUsersByAuthority() {
		List<Player> players = this.authoritiesService.findAllByAuthority("PLAYER");
		assertEquals(6, players.size());

		List<Player> admins = this.authoritiesService.findAllByAuthority("PLAYER");
		assertEquals(6, admins.size());
	}
 
	@Test
	void shouldNotFindPlayerByIncorrectUsername() {
		assertThrows(ResourceNotFoundException.class, () -> this.playerService.findByNickname("usernotexists"));
	}
 
	@Test
	void shouldFindSinglePlayerByUsername() {
		Player player = this.playerService.findByNickname("Angelgares");
		assertEquals("Angelgares", player.getNickname());
	}

	@Test
	void shouldNotFindSinglePlayerWithBadUsername() {
		assertThrows(ResourceNotFoundException.class, () -> this.playerService.findByNickname("badusername"));
	}
 
	@Test
	void shouldFindPlayerById() {
		Player player = this.playerService.findUserById(4);
		assertEquals("Jorge_ADD", player.getNickname());
	}
 
	@Test
	void shouldNotFindSingleUserOwnerWithBadUserId() {
		assertThrows(ResourceNotFoundException.class, () -> this.playerService.findUserById(100));
	}

	@Test
	void shouldExistUserByNickname() {
		assertEquals(true, this.playerService.existsByNickname("Angelgares"));
	}

	@Test
	void shouldNotExistUserByNickname() {
		assertEquals(false, this.playerService.existsByNickname("UserNotExists"));
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
	@Transactional
	void shouldUpdateUser() {
		Player player = this.playerService.findUserById(4);
		player.setNickname("Change");
		playerService.updatePlayer(4, player);
		player = this.playerService.findUserById(4);
		assertEquals("Change", player.getNickname());
	}
 
	@Test
	@Transactional
	void shouldInsertUser() {
		int count = ((Collection<Player>) this.playerService.findAllPlayers()).size();
		
		Player player = createPlayer();
		assertNotEquals(0, player.getId().longValue());

		int finalCount = ((Collection<Player>) this.playerService.findAllPlayers()).size();
		assertEquals(count + 1, finalCount);
	}
	
 
	@Test
	@Transactional
	void shouldDeleteUserWithoutOwner() {
		Integer firstCount = ((Collection<Player>) playerService.findAllPlayers()).size();
		LocalDate birthDate = LocalDate.of(1999, 01, 01);
		String avatar = "https://cdn-icons-png.flaticon.com/512/147/147144.png";

		Player player = new Player();
		player.setName("Sam");
		player.setSurname("Winter");
		player.setNickname("Samer");
		player.setPassword("Sam3r!");
		player.setEmail("sam@gmail.com");
		player.setBirthDate(birthDate);
		player.setAuthority(authoritiesService.findByAuthority("ADMIN"));
		player.setAvatar(avatar);
		this.playerService.savePlayer(player);

		/*Integer secondCount = ((Collection<Player>) playerService.findAllPlayers()).size();
		assertEquals(firstCount + 1, secondCount);*/

		playerService.deletePlayer(player.getId());
		Integer lastCount = ((Collection<Player>) playerService.findAllPlayers()).size();
		assertEquals(firstCount, lastCount);
	}
}
