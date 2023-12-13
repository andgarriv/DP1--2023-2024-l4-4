package us.l4_4.dp1.end_of_line.achievements;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import us.l4_4.dp1.end_of_line.achievement.Achievement;
import us.l4_4.dp1.end_of_line.achievement.AchievementController;
import us.l4_4.dp1.end_of_line.achievement.AchievementService;
import us.l4_4.dp1.end_of_line.enums.Category;

@WebMvcTest(controllers = AchievementController.class, excludeFilters = @ComponentScan.Filter(
    type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class))

class AchievementControllerTests {
    
    private static final String BASE_URL = "/api/v1/achievements";

    @SuppressWarnings("unused")
    @Autowired
    private AchievementController achievementController;

    @MockBean
    private AchievementService achievementService;

    @Autowired
    private MockMvc mockMvc;

    private Achievement achievement;
    private Achievement achievement2;

    @BeforeEach
    void setUp() {
        achievement = new Achievement();
        achievement.setId(1);
        achievement.setName("achievementName");
        achievement.setDescription("achievementDescription");
        achievement.setThreshold(10.0);
        achievement.setCategory(Category.VICTORIES);

        achievement2 = new Achievement();
        achievement2.setId(2);
        achievement2.setName("achievementName2");
        achievement2.setDescription("achievementDescription2");
        achievement2.setThreshold(20.0);
        achievement2.setCategory(Category.VICTORIES);
    }

    @Test
    @WithMockUser(username = "admin", password = "Adm1n!")
    void adminShouldFindAllAchievements() throws Exception {
        when(this.achievementService.findAll()).thenReturn(List.of(achievement, achievement2));

        mockMvc.perform(get(BASE_URL + "all")).andExpect(status().isOk()).andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[?(@id == 1)].name").value("achievementName"))
                .andExpect(jsonPath("$[?(@id == 2)].name").value("achievement2"));
    }
}
