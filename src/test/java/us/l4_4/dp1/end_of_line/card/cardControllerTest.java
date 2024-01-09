package us.l4_4.dp1.end_of_line.card;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.util.ArrayList;
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
import us.l4_4.dp1.end_of_line.enums.Hability;
import us.l4_4.dp1.end_of_line.enums.Orientation;
import us.l4_4.dp1.end_of_line.game.Game;
import us.l4_4.dp1.end_of_line.gameplayer.GamePlayer;
import us.l4_4.dp1.end_of_line.player.Player;
import us.l4_4.dp1.end_of_line.player.PlayerService;


@WebMvcTest(controllers = CardController.class, excludeFilters = @ComponentScan.Filter(
    type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class))
class cardControllerTest {
    private static final String BASE_URL = "/api/v1/cards";

    private Player player;
    private Player player2;
    private Authorities authority;
    private Card card;
    private Card card2;
    private Card card3;
    private Card card4;
    private Game game;
    @SuppressWarnings("unused")
    @Autowired
    private CardController cardController;
    @MockBean
    private CardService cardService;
    @MockBean
    private PlayerService playerService;
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

        GamePlayer gp1 = new GamePlayer();
        gp1.setId(1);
        gp1.setColor(Color.RED);
        gp1.setEnergy(3);
        gp1.setPlayer(playerService.findById(1));
        


        player2 = new Player();
        player2.setId(1);
        player2.setName("playerName");
        player2.setSurname("playerSurname");
        player2.setPassword("Play3r!");
        player2.setEmail("player@gmail.com");
        player2.setBirthDate(birthDate);
        player2.setNickname("playerNickname");
        player2.setAuthority(authority);
        player2.setAvatar(avatar);

        GamePlayer gp2 = new GamePlayer();
        gp1.setId(2);
        gp1.setColor(Color.BLUE);
        gp1.setEnergy(3);
        gp1.setPlayer(playerService.findById(2));

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
        
        card2 = new Card();
        card2.setId(2);
        card2.setInitiative(2);
        card2.setColor(Color.RED);
        card2.setExit(Exit.EXIT_101_A);
        card2.setIsTemplate(false);
        card2.setOrientation(Orientation.S);
        card2.setColumn(1);
        card2.setRow(2);
        card2.setCardState(CardStatus.IN_HAND);

        card3 = new Card();
        card3.setId(3);
        card3.setInitiative(3);
        card3.setColor(Color.BLUE);
        card3.setExit(Exit.EXIT_011_A);
        card3.setIsTemplate(false);
        card3.setOrientation(Orientation.S);
        card3.setColumn(1);
        card3.setRow(3);
        card3.setCardState(CardStatus.IN_HAND);


        card4 = new Card();
        card4.setId(4);
        card4.setInitiative(4);
        card4.setColor(Color.BLUE);
        card4.setExit(Exit.EXIT_100_B);
        card4.setIsTemplate(false);
        card4.setOrientation(Orientation.S);
        card4.setColumn(1);
        card4.setRow(3);
        card4.setCardState(CardStatus.IN_HAND);
        
        
        List<Card> cards = new ArrayList<>();
        cards.add(card);
        cards.add(card2);
        gp1.setCards(cards);



        List<Card> cards2 = new ArrayList<>();
        cards2.add(card3);
        cards2.add(card4);
        gp2.setCards(cards2);


        List<GamePlayer> gamePlayers = List.of(gp1,gp2);

        game = new Game();
        game.setRound(1);
        game.setWinner(null);
        game.setStartedAt(Date.from(java.time.Instant.now()));
        game.setEndedAt(null);
        game.setMessage(null);
        game.setEffect(Hability.NONE);
        game.setGamePlayers(gamePlayers);


        
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

    @Test
    @WithMockUser(username = "playerName", password = "Play3r!")
    void shouldFindAllGameCards()throws Exception{
        when(this.cardService.findAllCardsOfGame(1)).thenReturn(List.of(card, card2, card3,card4));

        mockMvc.perform(get(BASE_URL + "/game/{id}", 1)).andExpect(status().isOk())
        .andExpect(jsonPath("$.size()").value(4))
        .andExpect(jsonPath("$[?(@.id == 1)].initiative").value(1))
        .andExpect(jsonPath("$[?(@.id == 2)].initiative").value(2))
        .andExpect(jsonPath("$[?(@.id == 3)].initiative").value(3))
        .andExpect(jsonPath("$[?(@.id == 4)].initiative").value(4))
        
        .andExpect(jsonPath("$[?(@.id == 1)].color").value(Color.RED.toString()))
        .andExpect(jsonPath("$[?(@.id == 2)].color").value(Color.RED.toString()))
        .andExpect(jsonPath("$[?(@.id == 3)].color").value(Color.BLUE.toString()))
        .andExpect(jsonPath("$[?(@.id == 4)].color").value(Color.BLUE.toString()))

        .andExpect(jsonPath("$[?(@.id == 1)].exit").value(Exit.EXIT_001_A.toString()))
        .andExpect(jsonPath("$[?(@.id == 2)].exit").value(Exit.EXIT_101_A.toString()))
        .andExpect(jsonPath("$[?(@.id == 3)].exit").value(Exit.EXIT_011_A.toString()))
        .andExpect(jsonPath("$[?(@.id == 4)].exit").value(Exit.EXIT_100_B.toString()))

        .andExpect(jsonPath("$[?(@.id == 1)].isTemplate").value(false))
        .andExpect(jsonPath("$[?(@.id == 2)].isTemplate").value(false))
        .andExpect(jsonPath("$[?(@.id == 3)].isTemplate").value(false))
        .andExpect(jsonPath("$[?(@.id == 4)].isTemplate").value(false))

        .andExpect(jsonPath("$[?(@.id == 1)].orientation").value(Orientation.S.toString()))
        .andExpect(jsonPath("$[?(@.id == 2)].orientation").value(Orientation.S.toString()))
        .andExpect(jsonPath("$[?(@.id == 3)].orientation").value(Orientation.S.toString()))
        .andExpect(jsonPath("$[?(@.id == 4)].orientation").value(Orientation.S.toString()))

        .andExpect(jsonPath("$[?(@.id == 1)].column").value(3))
        .andExpect(jsonPath("$[?(@.id == 2)].column").value(1))
        .andExpect(jsonPath("$[?(@.id == 3)].column").value(1))
        .andExpect(jsonPath("$[?(@.id == 4)].column").value(1))

        .andExpect(jsonPath("$[?(@.id == 1)].row").value(4))
        .andExpect(jsonPath("$[?(@.id == 2)].row").value(2))
        .andExpect(jsonPath("$[?(@.id == 3)].row").value(3))
        .andExpect(jsonPath("$[?(@.id == 4)].row").value(3))

        .andExpect(jsonPath("$[?(@.id == 1)].cardState").value(CardStatus.IN_HAND.toString()))
        .andExpect(jsonPath("$[?(@.id == 2)].cardState").value(CardStatus.IN_HAND.toString()))
        .andExpect(jsonPath("$[?(@.id == 3)].cardState").value(CardStatus.IN_HAND.toString()))
        .andExpect(jsonPath("$[?(@.id == 4)].cardState").value(CardStatus.IN_HAND.toString()));
    }
    
}
