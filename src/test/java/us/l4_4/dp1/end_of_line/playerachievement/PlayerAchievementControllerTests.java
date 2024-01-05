package us.l4_4.dp1.end_of_line.playerachievement;

import static org.mockito.Mockito.when;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import us.l4_4.dp1.end_of_line.authorities.Authorities;
import us.l4_4.dp1.end_of_line.player.Player;

@WebMvcTest(controllers = PlayerAchievementController.class, excludeFilters = @ComponentScan.Filter(
    type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class))

public class PlayerAchievementControllerTests {
    
    private static final String BASE_URL = "/api/v1/playerachievements";
    private static final String avatar = "https://cdn-icons-png.flaticon.com/512/147/147144.png";

    @SuppressWarnings("unused")
    @Autowired
    private PlayerAchievementController playerAchievementController;

    @MockBean
    private PlayerAchievementService playerAchievementService;

    @Autowired
    private MockMvc mockMvc;

    private PlayerAchievement playerAchievement, playerAchievement2;
    private Player admin, player; 
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

        playerAchievement = new PlayerAchievement();
        playerAchievement.setId(1);
        playerAchievement.setAchieveAt(LocalDate.of(2023, 9, 01));

        playerAchievement2 = new PlayerAchievement();
        playerAchievement2.setId(2);
        playerAchievement2.setAchieveAt(LocalDate.of(2023, 2, 12));
    }

    @Test
    @WithMockUser(username = "Name1", password = "Player1!")
    void adminShouldFindAllPlayerAchievements() throws Exception {
        when(this.playerAchievementService.findAll()).thenReturn(List.of(playerAchievement, playerAchievement2));

        mockMvc.perform(get(BASE_URL)).andExpect(status().isOk()).andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[?(@.id == 1)].achieveAt").value(LocalDate.of(2023, 9, 01).toString()))
                .andExpect(jsonPath("$[?(@.id == 2)].achieveAt").value(LocalDate.of(2023, 2, 12).toString()));
    }
}
