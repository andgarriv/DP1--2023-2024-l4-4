package us.l4_4.dp1.end_of_line.player;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import us.l4_4.dp1.end_of_line.auth.AuthService;
import us.l4_4.dp1.end_of_line.authorities.Authorities;
import us.l4_4.dp1.end_of_line.authorities.AuthoritiesService;
import us.l4_4.dp1.end_of_line.enums.FriendStatus;
import us.l4_4.dp1.end_of_line.friendship.Friendship;

@WebMvcTest(controllers = PlayerController.class, excludeFilters = @ComponentScan.Filter(
    type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class))

class PlayerControllerTests {

    private static final String BASE_URL = "/api/v1/players";
    private static final Integer TEST_PLAYER_ID = 2;
    private static final String TEST_PLAYER_NICKNAME = "playerNickname";
    
    @SuppressWarnings("unused")
    @Autowired
    private PlayerController playerController;

    @MockBean
    private PlayerService playerService;

    @MockBean
    private AuthService authService;

    @MockBean
    private AuthoritiesService authoritiesService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
	private ObjectMapper objectMapper;

    private Player admin;
    private Player player;
    private Player player2;
    private Player player3;
    private Player newPlayer;
    private Authorities authority;
    private Authorities authority2;

    @BeforeEach
    void setup() {
        authority = new Authorities();
        authority.setId(1);
        authority.setAuthority("ADMIN");

        LocalDate birthDate = LocalDate.of(1999, 01, 01);
		String avatar = "https://cdn-icons-png.flaticon.com/512/147/147144.png";

        admin = new Player();
        admin.setId(1);
        admin.setName("adminName");
        admin.setSurname("adminSurname");
        admin.setPassword("Adm1n!");
        admin.setEmail("admin@gmail.com");
        admin.setBirthDate(birthDate);
        admin.setNickname("adminNickname");
        admin.setAuthority(authority);

        authority2 = new Authorities();
        authority.setId(2);
        authority.setAuthority("PLAYER");

        player = new Player();
        player.setId(2);
        player.setName("playerName");
        player.setSurname("playerSurname");
        player.setPassword("Play3r!");
        player.setEmail("player@gmail.com");
        player.setBirthDate(birthDate);
        player.setNickname("playerNickname");
        player.setAuthority(authority2);
        player.setAvatar(avatar);

        player2 = new Player();
        player2.setId(3);
        player2.setName("player2Name");
        player2.setSurname("player2Surname");
        player2.setPassword("PLay3r!");
        player2.setEmail("player2@gmail.com");
        player2.setBirthDate(birthDate);
        player2.setNickname("player2Nickname");
        player2.setAuthority(authority2);
        player2.setAvatar(avatar);

        player3 = new Player();
        player3.setId(4);
        player3.setName("player3Name");
        player3.setSurname("player3Surname");
        player3.setPassword("PLay3r!");
        player3.setEmail("player3@gmail.com");
        player3.setBirthDate(birthDate);
        player3.setNickname("player3Nickname");
        player3.setAuthority(authority2);
        player3.setAvatar(avatar);

        Friendship friendship = new Friendship();
        friendship.setId(1);
        friendship.setSender(player);
        friendship.setReceiver(player2);
        friendship.setFriendState(FriendStatus.ACCEPTED);

        Friendship friendship2 = new Friendship();
        friendship2.setId(2);
        friendship2.setSender(player3);
        friendship2.setReceiver(player);
        friendship2.setFriendState(FriendStatus.PENDING);
    }

    @Test
    @WithMockUser(username = "admin1", password = "Adm1n!")
    void adminShouldFindAllPlayers() throws Exception {
        when(this.playerService.findAll()).thenReturn(List.of(player, player2));

        mockMvc.perform(get(BASE_URL + "/all")).andExpect(status().isOk()).andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[?(@.id == 2)].name").value("playerName"))
                .andExpect(jsonPath("$[?(@.id == 3)].name").value("player2Name"));
    }

    @Test
    @WithMockUser(username = "playerName", password = "Own3r!")
    void playerShouldFindByNickname() throws Exception {
        when(this.playerService.findByNickname(TEST_PLAYER_NICKNAME)).thenReturn(player2);

        mockMvc.perform(get(BASE_URL + "/nickname/{nickname}", TEST_PLAYER_NICKNAME)).andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("player2Name"));
    }

    @Test
    @WithMockUser(username = "playerName", password = "Own3r!")
    void playerShouldFindById() throws Exception {
        when(this.playerService.findById(TEST_PLAYER_ID)).thenReturn(player);

        mockMvc.perform(get(BASE_URL + "/{id}", TEST_PLAYER_ID)).andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("playerName"));
    }

    @Test
    @WithMockUser(username = "playerName", password = "Own3r!")
    void playerShouldFindAllFriendsByPlayerId() throws Exception{
        when(this.playerService.findAllFriendsByPlayerId(TEST_PLAYER_ID)).thenReturn(List.of(player2));

        mockMvc.perform(get(BASE_URL + "/friends/{id}", TEST_PLAYER_ID)).andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[?(@.id == 3)].name").value("player2Name"));
    }

    /* @Test
    @WithMockUser()
    void shouldCreatePlayer() throws Exception {
        newPlayer = new Player();
        newPlayer.setId(4);
        newPlayer.setName("newPlayerName");
        newPlayer.setSurname("newPlayerSurname");
        newPlayer.setPassword("Play3r!");
        newPlayer.setEmail("newplayer@gmail.com");
        newPlayer.setBirthDate(LocalDate.of(1999, 01, 01));
        newPlayer.setNickname("newplayerNickname");
        newPlayer.setAuthority(authority2);
        newPlayer.setAvatar("https://cdn-icons-png.flaticon.com/512/147/147144.png");

        mockMvc.perform(post(BASE_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(player))).andExpect(status().isCreated());
    }

    @Test
	@WithMockUser(username = "playerName", password = "Own3r!")
	void shouldUpdatePlayer() throws Exception {
		player.setName("UPDATED");
		player.setSurname("UPDATED");

		when(this.playerService.findById(TEST_PLAYER_ID)).thenReturn(player);
		when(this.playerService.update(any(Integer.class), any(Player.class))).thenReturn(player);

		mockMvc.perform(put(BASE_URL + "/{id}", TEST_PLAYER_ID).with(csrf()).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(player))).andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("UPDATED"))
				.andExpect(jsonPath("$.surname").value("UPDATED"));
	} */ 

    @Test
	@WithMockUser(username = "admin1", password = "Adm1n!")
	  void shouldDeletePlayer() throws Exception {
		when(this.playerService.findById(TEST_PLAYER_ID)).thenReturn(player);
		
	    doNothing().when(this.playerService).delete(TEST_PLAYER_ID);
	    mockMvc.perform(delete(BASE_URL + "/{id}", TEST_PLAYER_ID).with(csrf()))
	         .andExpect(status().isOk());
	  }
}