package us.l4_4.dp1.end_of_line.achievements;

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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import us.l4_4.dp1.end_of_line.achievement.Achievement;
import us.l4_4.dp1.end_of_line.achievement.AchievementController;
import us.l4_4.dp1.end_of_line.achievement.AchievementService;
import us.l4_4.dp1.end_of_line.authorities.Authorities;
import us.l4_4.dp1.end_of_line.enums.Category;
import us.l4_4.dp1.end_of_line.player.Player;

@WebMvcTest(controllers = AchievementController.class, excludeFilters = @ComponentScan.Filter(
    type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class))

class AchievementControllerTests {
    
    private static final String BASE_URL = "/api/v1/achievements";
    public static final Integer TEST_ACHIEVEMENT_ID = 1;
    private static final String avatar = "https://cdn-icons-png.flaticon.com/512/147/147144.png";
    private static final String badgeAchieved = "https://cdn-icons-png.flaticon.com/128/5730/5730459.png";
    private static final String badgeNotAchieved = "https://cdn-icons-png.flaticon.com/512/5778/5778223.png";

    @SuppressWarnings("unused")
    @Autowired
    private AchievementController achievementController;

    @MockBean
    private AchievementService achievementService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
	private ObjectMapper objectMapper;

    private Achievement achievement;
    private Achievement achievement2;
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
    void setUp() {
        authority = new Authorities();
        authority.setId(1);
        authority.setAuthority("ADMIN");

        authority2 = new Authorities();
        authority.setId(2);
        authority.setAuthority("PLAYER");

        admin = createPlayer(1, true);
        player = createPlayer(2, false);

        achievement = new Achievement();
        achievement.setId(1);
        achievement.setName("achievementName");
        achievement.setDescription("achievementDescription");
        achievement.setBadgeAchieved(badgeAchieved);
        achievement.setBadgeNotAchieved(badgeNotAchieved);
        achievement.setThreshold(10.0);
        achievement.setCategory(Category.VICTORIES);

        achievement2 = new Achievement();
        achievement2.setId(2);
        achievement2.setName("achievementName2");
        achievement2.setDescription("achievementDescription2");
        achievement2.setBadgeAchieved(badgeAchieved);
        achievement2.setBadgeNotAchieved(badgeNotAchieved);
        achievement2.setThreshold(20.0);
        achievement2.setCategory(Category.VICTORIES);
    }

    @Test
    @WithMockUser(username = "Name1", password = "Player1!")
    void adminShouldFindAllAchievements() throws Exception {
        when(this.achievementService.findAll()).thenReturn(List.of(achievement, achievement2));

        mockMvc.perform(get(BASE_URL)).andExpect(status().isOk()).andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[?(@.id == 1)].name").value("achievementName"))
                .andExpect(jsonPath("$[?(@.id == 2)].name").value("achievementName2"));
    }

    @Test
    @WithMockUser(username = "Name2", password = "Player2!")
    void playerShouldFindAllAchievements() throws Exception {
        when(this.achievementService.findAll()).thenReturn(List.of(achievement, achievement2));

        mockMvc.perform(get(BASE_URL)).andExpect(status().isOk()).andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[?(@.id == 1)].name").value("achievementName"))
                .andExpect(jsonPath("$[?(@.id == 2)].name").value("achievementName2"));
    }

    @Test
    @WithMockUser(username = "Name1", password = "Player1!")
    void adminShouldFindAchievementById() throws Exception {
        when(this.achievementService.findById(TEST_ACHIEVEMENT_ID)).thenReturn(achievement);

        mockMvc.perform(get(BASE_URL + "/{id}", TEST_ACHIEVEMENT_ID)).andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("achievementName"));
    }

    @Test
    @WithMockUser(username = "Name2", password = "Player2!")
    void playerShouldFindAchievementById() throws Exception {
        when(this.achievementService.findById(TEST_ACHIEVEMENT_ID)).thenReturn(achievement);

        mockMvc.perform(get(BASE_URL + "/{id}", TEST_ACHIEVEMENT_ID)).andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("achievementName"));
    }

    @Test
    @WithMockUser(username = "Name1", password = "Player1!")
    void adminShouldCreateAchievement() throws Exception {
        Achievement achievement3 = new Achievement();
        achievement3.setId(3);
        achievement3.setName("achievementName3");
        achievement3.setDescription("achievementDescription3");
        achievement3.setBadgeAchieved(badgeAchieved);
        achievement3.setBadgeNotAchieved(badgeNotAchieved);
        achievement3.setThreshold(30.0);
        achievement3.setCategory(Category.VICTORIES);

        mockMvc.perform(post(BASE_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(achievement3))).andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username = "Name1", password = "Player1!")
    void adminShouldUpdateAchievement() throws Exception {
        achievement.setName("UPDATED");
        achievement.setDescription("DESCRIPTION UPDATED");

        when(this.achievementService.findById(TEST_ACHIEVEMENT_ID)).thenReturn(achievement);
        when(this.achievementService.update(any(Integer.class), any(Achievement.class))).thenReturn(achievement);

        mockMvc.perform(put(BASE_URL + "/{id}", TEST_ACHIEVEMENT_ID).with(csrf()).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(achievement))).andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("UPDATED"))
                .andExpect(jsonPath("$.description").value("DESCRIPTION UPDATED"));
    }

    @Test
    @WithMockUser(username = "Name1", password = "Player1!")
    void adminShouldDeleteAchievement() throws Exception {
        when(this.achievementService.findById(TEST_ACHIEVEMENT_ID)).thenReturn(achievement);
        
        doNothing().when(this.achievementService).delete(TEST_ACHIEVEMENT_ID);
        mockMvc.perform(delete(BASE_URL + "/{id}", TEST_ACHIEVEMENT_ID).with(csrf()))
                .andExpect(status().isOk());
    }

    
}
