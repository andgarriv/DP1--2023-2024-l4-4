package us.l4_4.dp1.end_of_line.player;

import static org.mockito.Mockito.*;
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

import us.l4_4.dp1.end_of_line.auth.AuthService;
import us.l4_4.dp1.end_of_line.authorities.Authorities;
import us.l4_4.dp1.end_of_line.authorities.AuthoritiesService;

@WebMvcTest(controllers = PlayerController.class, excludeFilters = @ComponentScan.Filter(
    type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class))

class PlayerControllerTests {

    private static final String BASE_URL = "/api/v1/players";
    
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

    private Player admin;
    private Player player;
    private Player player2;
    private Player newPlayer;
    private Authorities authority;
    private Authorities authority2;

    @BeforeEach
    void setup() {
        Authorities authority = new Authorities();
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
    }

    @Test
    @WithMockUser(username = "admin1", password = "Adm1n!")
    void adminShouldFindAllPlayers() throws Exception {
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

        when(this.playerService.findAllPlayers()).thenReturn(List.of(player, player2, newPlayer));

        mockMvc.perform(get(BASE_URL + "/all")).andExpect(status().isOk()).andExpect(jsonPath("$.size()").value(3))
                .andExpect(jsonPath("$[?(@.id == 2)].name").value("playerName"))
                .andExpect(jsonPath("$[?(@.id == 3)].name").value("player2Name"))
                .andExpect(jsonPath("$[?(@.id == 4)].name").value("newPlayerName"));
    }

    /* @Test
    @WithMockUser(username = "admin1", password = "Adm1n!") */
}