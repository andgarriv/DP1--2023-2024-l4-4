package us.l4_4.dp1.end_of_line.gameplayer;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.time.LocalDate;

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
import us.l4_4.dp1.end_of_line.player.Player;
import us.l4_4.dp1.end_of_line.player.PlayerService;


@WebMvcTest(controllers = GamePlayerController.class, excludeFilters = @ComponentScan.Filter(
    type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class))

public class GamePlayerControllerTests {
    private static final String BASE_URL = "/api/v1/gameplayers";

    @SuppressWarnings("unused")
    @Autowired
    private GamePlayerController gamePlayerController;

    @MockBean
    private GamePlayerService gamePlayerService;

    @MockBean
    private PlayerService playerService;
    @Autowired
    private MockMvc mockMvc;

    private Player player;
    private Player player2;
    private GamePlayer gamePlayer;
    private GamePlayer gamePlayer2;
    private GamePlayer updateGamePlayer;


    private Authorities authority;

    @Autowired
	private ObjectMapper objectMapper;


   @BeforeEach
    void setUp(){
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

        gamePlayer = new GamePlayer();
        gamePlayer.setId(1);
        gamePlayer.setColor(Color.RED);
        gamePlayer.setEnergy(3);
        gamePlayer.setPlayer(playerService.findById(1));
        


        player2 = new Player();
        player2.setId(2);
        player2.setName("playerName2");
        player2.setSurname("playerSurname2");
        player2.setPassword("Play3r!2");
        player2.setEmail("player2@gmail.com");
        player2.setBirthDate(birthDate);
        player2.setNickname("playerNickname2");
        player2.setAuthority(authority);
        player2.setAvatar(avatar);

        gamePlayer2 = new GamePlayer();
        gamePlayer2.setId(2);
        gamePlayer2.setColor(Color.BLUE);
        gamePlayer2.setEnergy(3);
        gamePlayer2.setPlayer(playerService.findById(2));
}

    @Test
    @WithMockUser(username = "playerName", password = "Play3r!")
    void shouldFindGamePlayerById() throws Exception {
        when(this.gamePlayerService.findById(1)).thenReturn(gamePlayer);

        mockMvc.perform(get(BASE_URL + "/{id}", 1)).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.color").value("RED"))
                .andExpect(jsonPath("$.energy").value(3));
    }

    @Test
    @WithMockUser(username = "playerName", password = "Play3r!")
    void shouldFindGamePlayerByGameAndPlayer() throws Exception {
        when(this.gamePlayerService.findGamePlayerByGameAndPlayer(1, 1)).thenReturn(gamePlayer);

        mockMvc.perform(get(BASE_URL + "/{gameId}/{playerId}", 1, 1)).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.color").value("RED"))
                .andExpect(jsonPath("$.energy").value(3));    
    }

    @Test
    @WithMockUser(username = "playerName", password = "Play3r!")
    void shouldUpdateGamePlayer() throws Exception {
        GamePlayerDTO gamePlayerDTO = new GamePlayerDTO();
        gamePlayerDTO.setColor(Color.RED);
        gamePlayerDTO.setEnergy(3);
        gamePlayerDTO.setPlayer_id(1);

        when(this.gamePlayerService.findById(5)).thenReturn(gamePlayer);

        when(this.gamePlayerService.update(any(GamePlayerDTO.class),any(Integer.class))).thenReturn(gamePlayer);

        mockMvc.perform(put(BASE_URL + "/{id}", 1).with(csrf()).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(gamePlayerDTO)))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.color").value("RED"))
                .andExpect(jsonPath("$.energy").value(3));
    }
}
