package us.l4_4.dp1.end_of_line.game;

import static org.mockito.Mockito.*;
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

import lombok.With;
import us.l4_4.dp1.end_of_line.authorities.Authorities;
import us.l4_4.dp1.end_of_line.authorities.AuthoritiesService;
import us.l4_4.dp1.end_of_line.card.Card;
import us.l4_4.dp1.end_of_line.card.CardController;
import us.l4_4.dp1.end_of_line.card.CardService;
import us.l4_4.dp1.end_of_line.enums.CardStatus;
import us.l4_4.dp1.end_of_line.enums.Color;
import us.l4_4.dp1.end_of_line.enums.Exit;
import us.l4_4.dp1.end_of_line.enums.Hability;
import us.l4_4.dp1.end_of_line.enums.Orientation;
import us.l4_4.dp1.end_of_line.gameplayer.GamePlayer;
import us.l4_4.dp1.end_of_line.gameplayer.GamePlayerService;
import us.l4_4.dp1.end_of_line.message.Message;
import us.l4_4.dp1.end_of_line.message.MessageService;
import us.l4_4.dp1.end_of_line.player.Player;
import us.l4_4.dp1.end_of_line.player.PlayerService;

@WebMvcTest(controllers = GameController.class, excludeFilters = @ComponentScan.Filter(
    type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class))
public class GameControllerTests {

    private static final String BASE_URL = "api/v1/games";

    private Player player; 
    private Player player2;
    private GamePlayer gamePlayer1;
    private GamePlayer gamePlayer2; 
    private Authorities authority;
    private Card card; 
    private Card card2;
    private Card card3;
    private Card card4;
    private Card updateCard;
    private Game game; 
    private Game game2;

    @SuppressWarnings("unused")
    @Autowired
    private GameController gameController;

    @MockBean
    private GameService gameService;

    @MockBean
    private PlayerService playerService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    
    @MockBean
    private CardService cardService;

    @MockBean
    private AuthoritiesService authoritiesService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private GamePlayerService gamePlayerService;

    @MockBean
    private MessageService messageService;



    @BeforeEach
    void setUp() { 
        LocalDate birthDate = LocalDate.of(1999, 12, 12);
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

        gamePlayer1 = new GamePlayer();
        gamePlayer1.setId(1);
        gamePlayer1.setColor(Color.RED);
        gamePlayer1.setEnergy(3);
        gamePlayer1.setPlayer(playerService.findById(1));

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

        
        gamePlayer2 = new GamePlayer();
        gamePlayer2.setId(2);
        gamePlayer2.setColor(Color.BLUE);
        gamePlayer2.setEnergy(3);
        gamePlayer2.setPlayer(playerService.findById(2));

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

        updateCard = new Card();
        updateCard.setId(5);
        updateCard.setInitiative(4);
        updateCard.setColor(Color.BLUE);
        updateCard.setExit(Exit.EXIT_101_B);
        updateCard.setIsTemplate(true);
        updateCard.setOrientation(Orientation.S);
        
        
        List<Card> cards = new ArrayList<>();
        cards.add(card);
        cards.add(card2);
        gamePlayer1.setCards(cards);

        
        List<Card> cards2 = new ArrayList<>();
        cards2.add(card3);
        cards2.add(card4);
        gamePlayer2.setCards(cards2);


        List<GamePlayer> gamePlayers = List.of(gamePlayer1,gamePlayer2);

        game = new Game();
        game.setRound(1);
        game.setWinner(null);
        game.setStartedAt(Date.from(java.time.Instant.now()));
        game.setEndedAt(null);
        game.setMessage(null);
        game.setEffect(Hability.NONE);
        game.setGamePlayers(gamePlayers);
        

        game2 = new Game();
        game2.setRound(10);
        game2.setWinner(playerService.findById(1));
        game2.setStartedAt(Date.from(java.time.Instant.now()));
        game2.setEndedAt(Date.from(java.time.Instant.now()));
        game2.setMessage(null);
        game2.setEffect(Hability.NONE);
        game2.setGamePlayers(gamePlayers);

    }

    @Test
    @WithMockUser(username = "playerName", password = "Play3r!")
    void shouldFindAllGamesByPlayerId() throws Exception{
        when (gameService.findAllGamesByPlayerId(1)).thenReturn(List.of(game,game2));

        mockMvc.perform(get(BASE_URL + "/player/{id}", 1)).andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.round").value(1))
        .andExpect(jsonPath("$.winner").value("null"))
        .andExpect(jsonPath("$.startedAt").exists())
        .andExpect(jsonPath("$.endedAt").value("null"))
        .andExpect(jsonPath("$.message").value("null"))
        .andExpect(jsonPath("$.effect").value("NONE"))
        .andExpect(jsonPath("$.gamePlayers[0].id").value(1))
        .andExpect(jsonPath("$.gamePlayers[0].color").value("RED"))
        .andExpect(jsonPath("$.gamePlayers[0].energy").value(3))
        .andExpect(jsonPath("$.gamePlayers[0].player.id").value(1))
        .andExpect(jsonPath("$.gamePlayers[0].player.name").value("playerName"))
        .andExpect(jsonPath("$.gamePlayers[0].player.surname").value("playerSurname"))
        .andExpect(jsonPath("$.gamePlayers[0].player.password").value("Play3r!"))
        .andExpect(jsonPath("$.gamePlayers[0].player.email").value(""));
    }



}
