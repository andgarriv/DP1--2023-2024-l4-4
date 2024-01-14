package us.l4_4.dp1.end_of_line.friendship;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import us.l4_4.dp1.end_of_line.auth.AuthService;
import us.l4_4.dp1.end_of_line.authorities.Authorities;
import us.l4_4.dp1.end_of_line.authorities.AuthoritiesService;
import us.l4_4.dp1.end_of_line.enums.FriendStatus;
import us.l4_4.dp1.end_of_line.player.Player;
import us.l4_4.dp1.end_of_line.player.PlayerService;

@WebMvcTest(controllers = FriendshipController.class, excludeFilters = @ComponentScan.Filter(
    type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class))

class FriendshipControllerTests {
    
    private static final String BASE_URL = "/api/v1/friendships";
    private static final Integer TEST_FRIENDSHIP_ID = 1;
    private static final Integer TEST_PLAYER_ID = 1;

    @SuppressWarnings("unused")
    @Autowired
    private FriendshipController friendshipController;

    @MockBean
    private FriendshipService friendshipService;

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

    private Player player;
    private Player player2;
    private Player player3;
    private Player player4;
    private Authorities authority;
    private Friendship friendship;
    private Friendship friendship2;
    private Friendship friendship3;
    private Friendship friendship4;

    @BeforeEach
    void setup() {
        authority = new Authorities();
        authority.setId(1);
        authority.setAuthority("ADMIN");

        LocalDate birthDate = LocalDate.of(1999, 01, 01);
		String avatar = "https://cdn-icons-png.flaticon.com/512/147/147144.png";

        authority = new Authorities();
        authority.setId(2);
        authority.setAuthority("PLAYER");

        player = new Player();
        player.setId(1);
        player.setName("playerName");
        player.setSurname("playerSurname");
        player.setPassword("Play3r!");
        player.setEmail("player@gmail.com");
        player.setBirthDate(birthDate);
        player.setNickname("playerNickname");
        player.setAuthority(authority);
        player.setAvatar(avatar);

        player2 = new Player();
        player2.setId(2);
        player2.setName("player2Name");
        player2.setSurname("player2Surname");
        player2.setPassword("PLay3r!");
        player2.setEmail("player2@gmail.com");
        player2.setBirthDate(birthDate);
        player2.setNickname("player2Nickname");
        player2.setAuthority(authority);
        player2.setAvatar(avatar);

        player3 = new Player();
        player3.setId(3);
        player3.setName("player3Name");
        player3.setSurname("player3Surname");
        player3.setPassword("PLay3r!");
        player3.setEmail("player3@gmail.com");
        player3.setBirthDate(birthDate);
        player3.setNickname("player3Nickname");
        player3.setAuthority(authority);
        player3.setAvatar(avatar);

        player4 = new Player();
        player4.setId(4);
        player4.setName("player4Name");
        player4.setSurname("player4Surname");
        player4.setPassword("PLay3r!");
        player4.setEmail("player4@gmail.com");
        player4.setBirthDate(birthDate);
        player4.setNickname("player4Nickname");
        player4.setAuthority(authority);
        player4.setAvatar(avatar);

        friendship = new Friendship();
        friendship.setId(1);
        friendship.setSender(player);
        friendship.setReceiver(player2);
        friendship.setFriendState(FriendStatus.ACCEPTED);

        friendship2 = new Friendship();
        friendship2.setId(2);
        friendship2.setSender(player3);
        friendship2.setReceiver(player);
        friendship2.setFriendState(FriendStatus.PENDING);

        friendship3 = new Friendship();
        friendship3.setId(3);
        friendship3.setSender(player2);
        friendship3.setReceiver(player3);
        friendship3.setFriendState(FriendStatus.PENDING);
    }

    @Test
    @WithMockUser(username = "admin1", password = "Adm1n!")
    void adminShouldFindAllFriendships() throws Exception {
        when(this.friendshipService.findAll()).thenReturn(List.of(friendship, friendship2, friendship3));

        mockMvc.perform(get(BASE_URL + "/all")).andExpect(status().isOk()).andExpect(jsonPath("$.size()").value(3))
        .andExpect(jsonPath("$[?(@.id == 1)].sender.name").value("playerName"))
        .andExpect(jsonPath("$[?(@.id == 2)].sender.name").value("player3Name"))
        .andExpect(jsonPath("$[?(@.id == 3)].sender.name").value("player2Name"));
    }

    @Test
    @WithMockUser(username = "playerName", password = "Own3r!")
    void playerShouldFindById() throws Exception {
        when(this.friendshipService.findById(1)).thenReturn(friendship);

        mockMvc.perform(get(BASE_URL + "/{id}", TEST_FRIENDSHIP_ID)).andExpect(status().isOk())
        .andExpect(jsonPath("$.sender.name").value("playerName"))
        .andExpect(jsonPath("$.receiver.name").value("player2Name"));
    }

    /* @Test
    @WithMockUser(username = "playerName", password = "Own3r!")
    void playerShouldCreateFriendship() throws Exception {
        friendship4 = new Friendship();
        friendship4.setId(4);
        friendship4.setSender(player);
        friendship4.setReceiver(player4);
        friendship4.setFriendState(FriendStatus.PENDING);

        mockMvc.perform(post(BASE_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(friendship))).andExpect(status().isCreated());
    }*/ 

    @Test
    @WithMockUser(username = "playerName", password = "Own3r!")
    void playerShouldDeleteFriendship() throws Exception{
        when(this.friendshipService.findById(TEST_FRIENDSHIP_ID)).thenReturn(friendship);

        doNothing().when(this.friendshipService).delete(TEST_FRIENDSHIP_ID);
        mockMvc.perform(delete(BASE_URL + "/{id}", TEST_FRIENDSHIP_ID).with(csrf()))
        .andExpect(status().isOk());
    }

}
