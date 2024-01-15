package us.l4_4.dp1.end_of_line.messages;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.Date;

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

import us.l4_4.dp1.end_of_line.authorities.Authorities;
import us.l4_4.dp1.end_of_line.enums.Color;
import us.l4_4.dp1.end_of_line.enums.Reaction;
import us.l4_4.dp1.end_of_line.game.Game;
import us.l4_4.dp1.end_of_line.message.Message;
import us.l4_4.dp1.end_of_line.message.MessageController;
import us.l4_4.dp1.end_of_line.message.MessageDTO;
import us.l4_4.dp1.end_of_line.message.MessageService;
import us.l4_4.dp1.end_of_line.player.Player;
@WebMvcTest(controllers  = MessageController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class))
class MessageControllerTest {

private static final String BASE_URL = "/api/v1/messages";
        private Game game;
        private Message message;

        private Player player;
        private Authorities authority;

        @MockBean
        private MessageService messageService;
        @Autowired
        private MockMvc mockMvc;
        @Autowired
        private ObjectMapper objectMapper;

        @BeforeEach
        void setUp() {

        LocalDate birthDate = LocalDate.of(1999, 01, 01);
        String avatar = "https://cdn-icons-png.flaticon.com/512/147/147144.png";
        authority = new Authorities();
        authority.setId(1);
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

        game = new Game();
        game.setId(1);
        game.setRound(1);
        game.setWinner(null);
        game.setStartedAt(Date.from(java.time.Instant.now()));
        game.setEndedAt(null);

        MessageDTO dto = new MessageDTO();
        dto.setGameId(1);
        dto.setColor(Color.RED);
        dto.setReaction(Reaction.GG);
        message = messageService.save(dto);



    }

    @Test
    @WithMockUser(username = "playerName", password = "Play3r!")
    void shouldCreateMessage() throws Exception {

        MessageDTO dto = new MessageDTO();
        dto.setGameId(1);
        dto.setColor(Color.RED);
        dto.setReaction(Reaction.GG);

        mockMvc.perform(post(BASE_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto))).andExpect(status().isCreated());
    }

}
