package us.l4_4.dp1.end_of_line.game;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.With;
import us.l4_4.dp1.end_of_line.authorities.Authorities;
import us.l4_4.dp1.end_of_line.authorities.AuthoritiesService;
import us.l4_4.dp1.end_of_line.card.Card;
import us.l4_4.dp1.end_of_line.card.CardService;
import us.l4_4.dp1.end_of_line.enums.CardStatus;
import us.l4_4.dp1.end_of_line.enums.Color;
import us.l4_4.dp1.end_of_line.enums.Exit;
import us.l4_4.dp1.end_of_line.enums.Hability;
import us.l4_4.dp1.end_of_line.enums.Orientation;
import us.l4_4.dp1.end_of_line.enums.Reaction;
import us.l4_4.dp1.end_of_line.gameplayer.GamePlayer;
import us.l4_4.dp1.end_of_line.gameplayer.GamePlayerService;
import us.l4_4.dp1.end_of_line.message.Message;
import us.l4_4.dp1.end_of_line.message.MessageService;
import us.l4_4.dp1.end_of_line.player.Player;
import us.l4_4.dp1.end_of_line.player.PlayerService;

@WebMvcTest(controllers = GameController.class, excludeFilters = @ComponentScan.Filter(
    type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class))
public class GameControllerTests {

    private static final String BASE_URL = "/api/v1/games";

    private Player player; 
    private Player player2;
    private GamePlayer gamePlayer1;
    private GamePlayer gamePlayer2; 
    private List<GamePlayer> gamePlayers;
    private Authorities authority;
    private Card card; 
    private Card card2;
    private Card card3;
    private Card card4;
    private Card updateCard;
    private Game game; 
    private Game game2;
    private Game updateGame;
    private Message message;
    private Message message2;
    private Game newGame;

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

    private Game createGame(Integer id, Integer id2, Color color, Color color2) {
        Game game = new Game();
        game.setId(id);
        game.setRound(1);
        game.setWinner(null);
        game.setStartedAt(Date.from(java.time.Instant.now()));
        game.setEndedAt(null);
        game.setMessage(null);
        game.setEffect(Hability.NONE);
        game.setGamePlayers(null);
        return game;
    }



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

        when(playerService.findById(1)).thenReturn(player);

        gamePlayer1 = new GamePlayer();
        gamePlayer1.setId(1);
        gamePlayer1.setColor(Color.RED);
        gamePlayer1.setEnergy(3);
        gamePlayer1.setPlayer(player);

        player2 = new Player();
        player2.setId(2);
        player2.setName("playerName2");
        player2.setSurname("playerSurname2");
        player2.setPassword("Play3r2!");
        player2.setEmail("player2@gmail.com");
        player2.setBirthDate(birthDate);
        player2.setNickname("playerNickname2");
        player2.setAuthority(authority);
        player2.setAvatar(avatar);

        
        
        when(playerService.findById(2)).thenReturn(player2);
        
        gamePlayer2 = new GamePlayer();
        gamePlayer2.setId(2);
        gamePlayer2.setColor(Color.BLUE);
        gamePlayer2.setEnergy(3);
        gamePlayer2.setPlayer(player2);

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


        gamePlayers = List.of(gamePlayer1,gamePlayer2);

        message = new Message();
        message.setId(1);
        message.setColor(Color.RED);
        message.setReaction(Reaction.GG);

        message2 = new Message();
        message2.setId(2);
        message2.setColor(Color.BLUE);
        message2.setReaction(Reaction.GG);


        game = new Game();
        game.setId(1);
        game.setRound(1);
        game.setWinner(null);
        game.setStartedAt(Date.from(java.time.Instant.now()));
        game.setEndedAt(null);
        game.setMessage(null);
        game.setEffect(Hability.NONE);
        game.setGamePlayers(gamePlayers);
        

        game2 = new Game();
        game.setId(2);
        game2.setRound(10);
        game2.setWinner(player2);
        game2.setStartedAt(Date.from(java.time.Instant.now()));
        game2.setEndedAt(Date.from(java.time.Instant.now()));
        game2.setMessage(null);
        game2.setEffect(Hability.NONE);
        game2.setGamePlayers(gamePlayers);

    }
    @Test
@WithMockUser(username = "playerName", password = "Play3r!")    
void shouldFindGameStatistics() throws Exception {
    Map<String, String> mockStats = new HashMap<>();
    mockStats.put("gamesPending", "2");
    mockStats.put("gamesFinished", "15");
    mockStats.put("totalPlayers", "10");
    mockStats.put("totalGames", "17");
    mockStats.put("averageGameDuration", "43h 34m 36s");
    mockStats.put("avgGames", "1,7");
    mockStats.put("maxRounds", "27");
    mockStats.put("leastUsedColor", "YELLOW");
    mockStats.put("avgRounds", "21,6");
    mockStats.put("averageEnergyUsed", "0,86");
    mockStats.put("totalGameDuration", "653h 39m 0s");
    mockStats.put("minGamesPlayed", "1");
    mockStats.put("mostUsedColor", "RED");
    mockStats.put("maxGamesPlayed", "12");
    mockStats.put("minGameDuration", "0h 15m 49s");
    mockStats.put("maxGameDuration", "648h 3m 31s");
    mockStats.put("minRounds", "16");

    when(gameService.calculateStatistics()).thenReturn(mockStats);
}


    @Test
    @WithMockUser(username = "playerName", password = "Play3r!")
    void shouldFindCardsOfGame() throws Exception{
        when(cardService.findAllCardsOfGame(1)).thenReturn(List.of(card, card2));

        mockMvc.perform(get(BASE_URL + "/{id}/cards", 1))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$[0].id").value(card.getId()))
               .andExpect(jsonPath("$[0].initiative").value(card.getInitiative()))
               .andExpect(jsonPath("$[0].color").value(card.getColor().toString()))
               .andExpect(jsonPath("$[0].exit").value(card.getExit().toString()))
               .andExpect(jsonPath("$[0].isTemplate").value(card.getIsTemplate()))
               .andExpect(jsonPath("$[0].orientation").value(card.getOrientation().toString()))
               .andExpect(jsonPath("$[0].column").value(card.getColumn()))
               .andExpect(jsonPath("$[0].row").value(card.getRow()))
               .andExpect(jsonPath("$[0].cardState").value(card.getCardState().toString()))
               .andExpect(jsonPath("$[1].id").value(card2.getId()))
               .andExpect(jsonPath("$[1].initiative").value(card2.getInitiative()))
               .andExpect(jsonPath("$[1].color").value(card2.getColor().toString()))
               .andExpect(jsonPath("$[1].exit").value(card2.getExit().toString()))
               .andExpect(jsonPath("$[1].isTemplate").value(card2.getIsTemplate()))
               .andExpect(jsonPath("$[1].orientation").value(card2.getOrientation().toString()))
               .andExpect(jsonPath("$[1].column").value(card2.getColumn()))
               .andExpect(jsonPath("$[1].row").value(card2.getRow()))
               .andExpect(jsonPath("$[1].cardState").value(card2.getCardState().toString()));
    }

    @Test
    @WithMockUser(username = "playerName", password = "Play3r!")
    void shouldFindGamePlayersByGameId() throws Exception{
        when(gamePlayerService.findGamePlayersByGameId(1)).thenReturn(List.of(gamePlayer1, gamePlayer2));

        mockMvc.perform(get(BASE_URL + "/{id}/gameplayers", 1))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$", hasSize(2))) 
               .andExpect(jsonPath("$[0].id").value(gamePlayer1.getId()))
               .andExpect(jsonPath("$[0].color").value(gamePlayer1.getColor().toString()))
               .andExpect(jsonPath("$[0].energy").value(gamePlayer1.getEnergy()))
               .andExpect(jsonPath("$[0].player.name").value(gamePlayer1.getPlayer().getName()))
               .andExpect(jsonPath("$[1].id").value(gamePlayer2.getId()))
               .andExpect(jsonPath("$[1].color").value(gamePlayer2.getColor().toString()))
               .andExpect(jsonPath("$[1].energy").value(gamePlayer2.getEnergy()))
               .andExpect(jsonPath("$[1].player.name").value(gamePlayer2.getPlayer().getName()));

        verify(gamePlayerService).findGamePlayersByGameId(1);
    }

    @Test
    @WithMockUser(username = "playerName", password = "Play3r!")
    void shouldFindAllMessagesByGameId() throws Exception {
        when(messageService.findAllMessagesByGameId(1)).thenReturn(List.of(message, message2));

        mockMvc.perform(get(BASE_URL + "/{id}/messages", 1))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$", hasSize(2)))
               .andExpect(jsonPath("$[0].id").value(message.getId()))
               .andExpect(jsonPath("$[0].color").value(message.getColor().toString()))
               .andExpect(jsonPath("$[0].reaction").value(message.getReaction().toString()))
               .andExpect(jsonPath("$[1].id").value(message2.getId()))
               .andExpect(jsonPath("$[1].color").value(message2.getColor().toString()))
               .andExpect(jsonPath("$[1].reaction").value(message2.getReaction().toString()));
        verify(messageService).findAllMessagesByGameId(1);
    }

    @Test
    @WithMockUser(username = "playerName", password = "Play3r!")
    void shouldFindAllGames() throws Exception {
        when(gameService.findAllGames()).thenReturn(List.of(game, game2));

        mockMvc.perform(get(BASE_URL + "/all"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$", hasSize(2)))
               .andExpect(jsonPath("$[0].id").value(game.getId()))
               .andExpect(jsonPath("$[0].round").value(game.getRound()))
               .andExpect(jsonPath("$[0].startedAt").isNotEmpty())
               .andExpect(jsonPath("$[0].endedAt").isEmpty()) 
               .andExpect(jsonPath("$[0].winner").doesNotExist()) 
               .andExpect(jsonPath("$[0].gamePlayers[0].player.name").value(gamePlayer1.getPlayer().getName()))
               .andExpect(jsonPath("$[0].gamePlayers[0].color").value(gamePlayer1.getColor().toString()))
               .andExpect(jsonPath("$[1].id").value(game2.getId()))
               .andExpect(jsonPath("$[1].round").value(game2.getRound()))
               .andExpect(jsonPath("$[1].startedAt").isNotEmpty())
               .andExpect(jsonPath("$[1].endedAt").isNotEmpty())
               .andExpect(jsonPath("$[1].gamePlayers[0].player.name").value(gamePlayer1.getPlayer().getName()))
               .andExpect(jsonPath("$[1].gamePlayers[0].color").value(gamePlayer1.getColor().toString()));
             
        verify(gameService).findAllGames();
    }

    @Test
    @WithMockUser(username = "playerName", password = "Play3r!")
    void shouldDeteleGame() throws Exception {
        when(gameService.findById(1)).thenReturn(game);

        doNothing().when(this.gameService).deleteGame(1, 1);
        mockMvc.perform(delete(BASE_URL + "/{gameId}/gameplayers/{gamePlayerId}", 1, 1).with(csrf()))
               .andExpect(status().isOk());

    }

    @Test
    @WithMockUser(username = "playerName", password = "Play3r!")
    void shouldFindById() throws Exception {
        when(gameService.findById(1)).thenReturn(game);

        mockMvc.perform(get(BASE_URL + "/{id}", 1))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").value(game.getId()))
               .andExpect(jsonPath("$.round").value(game.getRound()))
               .andExpect(jsonPath("$.startedAt").isNotEmpty()) // Verifica la fecha de inicio
               .andExpect(jsonPath("$.endedAt").isEmpty()) // Verifica si el juego ha terminado
               .andExpect(jsonPath("$.winner").doesNotExist()) // Verifica si hay un ganador
               .andExpect(jsonPath("$.effect").value(game.getEffect().toString()))
               .andExpect(jsonPath("$.gamePlayers", hasSize(game.getGamePlayers().size()))) // Verifica la cantidad de jugadores
               .andExpect(jsonPath("$.gamePlayers[0].player.name").value(gamePlayer1.getPlayer().getName()))
               .andExpect(jsonPath("$.gamePlayers[0].color").value(gamePlayer1.getColor().toString()))
               .andExpect(jsonPath("$.gamePlayers[0].energy").value(gamePlayer1.getEnergy()))
               // Verifica cualquier otro campo relevante de gamePlayer1
               .andExpect(jsonPath("$.gamePlayers[1].player.name").value(gamePlayer2.getPlayer().getName()))
               .andExpect(jsonPath("$.gamePlayers[1].color").value(gamePlayer2.getColor().toString()))
               .andExpect(jsonPath("$.gamePlayers[1].energy").value(gamePlayer2.getEnergy()));
               // Verifica cualquier otro campo relevante de gamePlayer2

        // Verifica que el servicio fue llamado correctamente
        verify(gameService).findById(1);
    } 

    @Test
    @WithMockUser(username = "playerName", password = "Play3r!")
    void shouldUpdateGameTurn() throws Exception { 
        game.setRound(5);
        game.setWinner(null);
        game.setStartedAt(Date.from(java.time.Instant.now()));
        game.setEndedAt(null);
        game.setMessage(null);
        game.setEffect(Hability.NONE);
        game.setGamePlayers(List.of(gamePlayer1, gamePlayer2));

        when(gameService.findById(1)).thenReturn(game);
        when(gameService.updateGameTurn(any(Integer.class))).thenReturn(game);

        // Realiza la llamada al endpoint y verifica la respuesta
        mockMvc.perform(put(BASE_URL + "/{gameId}", 1)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(game)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.round").value(game.getRound()))
               .andExpect(jsonPath("$.winner").isEmpty())
               .andExpect(jsonPath("$.startedAt").isNotEmpty())
               .andExpect(jsonPath("$.endedAt").isEmpty())
               .andExpect(jsonPath("$.message").isEmpty())
               .andExpect(jsonPath("$.effect").value(game.getEffect().toString()))
               .andExpect(jsonPath("$.gamePlayers", hasSize(2)))
               .andExpect(jsonPath("$.gamePlayers[0].player.name").value(gamePlayer1.getPlayer().getName()))
               .andExpect(jsonPath("$.gamePlayers[1].player.name").value(gamePlayer2.getPlayer().getName()));
    }


    @Test
    @WithMockUser(username = "playerName", password = "Play3r!")
    void shouldChangeEffect() throws Exception {
        // Configura el juego y los mocks
        game.setRound(5);
        game.setWinner(null);
        game.setStartedAt(Date.from(java.time.Instant.now()));
        game.setEndedAt(null);
        game.setMessage(null);
        game.setEffect(Hability.NONE);
        game.setGamePlayers(List.of(gamePlayer1, gamePlayer2));

        when(gameService.findById(1)).thenReturn(game);
        when(gameService.updateGameEffect(any(Integer.class), any(EffectDTO.class))).thenReturn(game);
        mockMvc.perform(put(BASE_URL + "/{gameId}/effect", 1)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(game)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.round").value(game.getRound()))
                // Verifica la ausencia de un ganador
                .andExpect(jsonPath("$.winner").isEmpty())
                .andExpect(jsonPath("$.startedAt").isNotEmpty())
                .andExpect(jsonPath("$.endedAt").isEmpty())
                .andExpect(jsonPath("$.message").isEmpty())
                .andExpect(jsonPath("$.effect").value(game.getEffect().toString()))
                // Verifica los detalles de los jugadores
                .andExpect(jsonPath("$.gamePlayers", hasSize(2)))
                .andExpect(jsonPath("$.gamePlayers[0].player.name").value(gamePlayer1.getPlayer().getName()))
                .andExpect(jsonPath("$.gamePlayers[1].player.name").value(gamePlayer2.getPlayer().getName()));
    }

    @Test
    @WithMockUser(username = "playerName", password = "Play3r!")
    void shouldChangeCardsInHand() throws Exception{ 
        when(gameService.findById(1)).thenReturn(game);
        when(gameService.changeCardsInHand(any(Integer.class))).thenReturn(List.of(card, card2));

        mockMvc.perform(put(BASE_URL + "/{gameId}/cards/discard", 1).with(csrf()).contentType(MediaType.APPLICATION_JSON)
               .content(objectMapper.writeValueAsString(game))).andExpect(status().isOk())
               .andExpect(jsonPath("$[0].id").value(card.getId()))
               .andExpect(jsonPath("$[0].initiative").value(card.getInitiative()))
               .andExpect(jsonPath("$[0].color").value(card.getColor().toString()))
               .andExpect(jsonPath("$[0].exit").value(card.getExit().toString()))
               .andExpect(jsonPath("$[0].isTemplate").value(card.getIsTemplate()))
               .andExpect(jsonPath("$[0].orientation").value(card.getOrientation().toString()))
               .andExpect(jsonPath("$[0].column").value(card.getColumn()))
               .andExpect(jsonPath("$[0].row").value(card.getRow()))
               .andExpect(jsonPath("$[0].cardState").value(card.getCardState().toString()))
               .andExpect(jsonPath("$[1].id").value(card2.getId()))
               .andExpect(jsonPath("$[1].initiative").value(card2.getInitiative()))
               .andExpect(jsonPath("$[1].color").value(card2.getColor().toString()))
               .andExpect(jsonPath("$[1].exit").value(card2.getExit().toString()))
               .andExpect(jsonPath("$[1].isTemplate").value(card2.getIsTemplate()))
               .andExpect(jsonPath("$[1].orientation").value(card2.getOrientation().toString()))
               .andExpect(jsonPath("$[1].column").value(card2.getColumn()))
               .andExpect(jsonPath("$[1].row").value(card2.getRow()))
               .andExpect(jsonPath("$[1].cardState").value(card2.getCardState().toString()));
    }

    @Test
    @WithMockUser(username = "playerName", password = "Play3r!")
    void shouldCardsPossiblePositions() throws Exception { 
        when(gameService.findById(1)).thenReturn(game);
        when(gameService.findPosiblePositionOfAGamePlayerGiven(any(Integer.class), any(Integer.class))).thenReturn(List.of("1,1,W", "1,2,E"));

        mockMvc.perform(get(BASE_URL + "/{gameId}/gameplayers/{gamePlayerId}/cards/positions", 1, 1))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$", hasSize(2))) // Verifica que se devuelven dos posiciones
               .andExpect(jsonPath("$[0]").value("1,1,W"))
               .andExpect(jsonPath("$[1]").value("1,2,E"));
    }
}
