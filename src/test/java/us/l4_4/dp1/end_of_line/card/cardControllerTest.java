package us.l4_4.dp1.end_of_line.card;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.util.Date;
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
import us.l4_4.dp1.end_of_line.enums.CardStatus;
import us.l4_4.dp1.end_of_line.enums.Color;
import us.l4_4.dp1.end_of_line.enums.Exit;
import us.l4_4.dp1.end_of_line.enums.FriendStatus;
import us.l4_4.dp1.end_of_line.enums.Orientation;
import us.l4_4.dp1.end_of_line.player.Player;
import us.l4_4.dp1.end_of_line.player.PlayerService;


@WebMvcTest(controllers = CardController.class, excludeFilters = @ComponentScan.Filter(
    type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class))
class cardControllerTest {
    private static final String BASE_URL = "/api/v1/cards";

    private Player player;
    private Authorities authority;
    private Card card;

    @Autowired
    private CardController cardController;
    @MockBean
    private CardService cardService;
    @Autowired
    private MockMvc mockMvc;



    @BeforeEach
    void setUp(){
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


        card = new Card();
        card.setId(1);
        card.setInitiative(1);
        card.setColor(Color.RED);
        card.setExit(Exit.EXIT_001_A);
        card.setIsTemplate(false);
        card.setOrientation(Orientation.S);
        card.setColumn(3);
        card.setRow(4);
        card.setCardState(CardStatus.IN_HAND);
        
    }

    @Test
    @WithMockUser(username = "playerName", password = "Play3r!")
    void shouldFindCardById() throws Exception{
        when(this.cardService.findById(1)).thenReturn(card);

        mockMvc.perform(get(BASE_URL + "/{id}", 1)).andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.initiative").value(1))
        .andExpect(jsonPath("$.color").value(Color.RED.toString()))
        .andExpect(jsonPath("$.exit").value(Exit.EXIT_001_A.toString()))
        .andExpect(jsonPath("$.isTemplate").value(false))
        .andExpect(jsonPath("$.orientation").value(Orientation.S.toString()))
        .andExpect(jsonPath("$.column").value(3))
        .andExpect(jsonPath("$.row").value(4))
        .andExpect(jsonPath("$.cardState").value(CardStatus.IN_HAND.toString()));
    }
    
}
