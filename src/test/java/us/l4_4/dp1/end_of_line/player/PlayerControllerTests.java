package us.l4_4.dp1.end_of_line.player;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
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
    private static final String avatar = "https://cdn-icons-png.flaticon.com/512/147/147144.png";
    
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

    private Player admin, player, player2, player3, newPlayer; 
    private Authorities authority, authority2;

    private Player createPlayer(Integer id, Boolean isAdmin) {
        Player player = new Player();
        player.setId(id);
        player.setName("Name" + id);
        player.setSurname("Surname" + id);
        player.setPassword("Player" + id + "!");
        player.setEmail("player" + id + "@gmail.com");
        player.setBirthDate(LocalDate.of(1999, 01, 01));
        player.setNickname("Nickname" + id);
        if (isAdmin) {
            player.setAuthority(authority);
        } else {
            player.setAuthority(authority2);
        }
        player.setAvatar(avatar);
        return player;
    }

    @BeforeEach
    void setup() {
        authority = new Authorities();
        authority.setId(1);
        authority.setAuthority("ADMIN");

        authority2 = new Authorities();
        authority.setId(2);
        authority.setAuthority("PLAYER");

        admin = createPlayer(1, true);
        player = createPlayer(2, false);
        player2 = createPlayer(3, false);
        player3 = createPlayer(4, false);

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
    @WithMockUser(username = "Name1", password = "Player1!")
    void adminShouldFindAllPlayers() throws Exception {
        when(this.playerService.findAll()).thenReturn(List.of(player, player2, player3));

        mockMvc.perform(get(BASE_URL + "/all")).andExpect(status().isOk()).andExpect(jsonPath("$.size()").value(3))
                .andExpect(jsonPath("$[?(@.id == 2)].name").value("Name2"))
                .andExpect(jsonPath("$[?(@.id == 3)].name").value("Name3"))
                .andExpect(jsonPath("$[?(@.id == 4)].name").value("Name4"));
    }

    @Test
    @WithMockUser(username = "Name2", password = "Player2!")
    void playerShouldFindByNickname() throws Exception {
        when(this.playerService.findByNickname(TEST_PLAYER_NICKNAME)).thenReturn(player);

        mockMvc.perform(get(BASE_URL + "/nickname/{nickname}", TEST_PLAYER_NICKNAME)).andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Name2"));
    }

    @Test
    @WithMockUser(username = "Name2", password = "Player2!")
    void playerShouldFindById() throws Exception {
        when(this.playerService.findById(TEST_PLAYER_ID)).thenReturn(player);

        mockMvc.perform(get(BASE_URL + "/{id}", TEST_PLAYER_ID)).andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Name2"));
    }

    @Test
    @WithMockUser(username = "Name2", password = "Player2!")
    void playerShouldFindAllFriendsByPlayerId() throws Exception{
        when(this.playerService.findAllFriendsByPlayerId(TEST_PLAYER_ID)).thenReturn(List.of(player2));

        mockMvc.perform(get(BASE_URL + "/{id}/friends", TEST_PLAYER_ID)).andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[?(@.id == 3)].name").value("Name3"));
    }

    @Test
    @WithMockUser()
    void shouldCreatePlayer() throws Exception {
        newPlayer = createPlayer(5, false);

        mockMvc.perform(post(BASE_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(newPlayer))).andExpect(status().isCreated());
    }

    @Test
	@WithMockUser(username = "Name2", password = "Player2!")
	void shouldUpdatePlayer() throws Exception {
		player.setName("UPDATED");
		player.setSurname("UPDATED");

		when(this.playerService.findById(TEST_PLAYER_ID)).thenReturn(player);
		when(this.playerService.update(any(Integer.class), any(Player.class))).thenReturn(player);

		mockMvc.perform(put(BASE_URL + "/{id}", TEST_PLAYER_ID).with(csrf()).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(player))).andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("UPDATED"))
				.andExpect(jsonPath("$.surname").value("UPDATED"));
	}

    @Test
	@WithMockUser(username = "Name1", password = "Player1!")
	  void shouldDeletePlayer() throws Exception {
		when(this.playerService.findById(TEST_PLAYER_ID)).thenReturn(player);
		
	    doNothing().when(this.playerService).delete(TEST_PLAYER_ID);
	    mockMvc.perform(delete(BASE_URL + "/{id}", TEST_PLAYER_ID).with(csrf()))
	         .andExpect(status().isOk());
	  }
}