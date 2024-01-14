package us.l4_4.dp1.end_of_line.card;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import us.l4_4.dp1.end_of_line.authorities.Authorities;
import us.l4_4.dp1.end_of_line.enums.CardStatus;
import us.l4_4.dp1.end_of_line.enums.Color;
import us.l4_4.dp1.end_of_line.enums.Exit;
import us.l4_4.dp1.end_of_line.enums.Hability;
import us.l4_4.dp1.end_of_line.enums.Orientation;
import us.l4_4.dp1.end_of_line.game.Game;
import us.l4_4.dp1.end_of_line.gameplayer.GamePlayer;
import us.l4_4.dp1.end_of_line.player.Player;
import us.l4_4.dp1.end_of_line.player.PlayerService;


@WebMvcTest(controllers = CardController.class, excludeFilters = @ComponentScan.Filter(
    type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class))
class cardControllerTests {
    private static final String BASE_URL = "/api/v1/cards";

    private Player player;
    private Player player2;
    private Authorities authority;
    private Card card;
    private Card card2;
    private Card card3;
    private Card card4;
    private Card updateCard;
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

        GamePlayer gp1 = new GamePlayer();
        gp1.setId(1);
        gp1.setColor(Color.RED);
        gp1.setEnergy(3);
        gp1.setPlayer(playerService.findById(1));
        


        player2 = new Player();
        player2.setId(2);
        player2.setName("playerName2");
        player2.setSurname("playerSurname2");
        player2.setPassword("Play3r!");
        player2.setEmail("player2@gmail.com");
        player2.setBirthDate(birthDate);
        player2.setNickname("playerNickname2");
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
    void shouldUpdateCard() throws Exception{
        
        updateCard.setColumn(4);
        updateCard.setRow(3);
        updateCard.setOrientation(Orientation.N);

        when(this.cardService.findById(1)).thenReturn(updateCard);
        when(this.cardService.update(any(Integer.class), any(CardDTO.class))).thenReturn(updateCard);


        mockMvc.perform(put(BASE_URL + "/{id}", 1).with(csrf()).contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(updateCard))).andExpect(status().isOk())
        .andExpect(jsonPath("$.column").value(4))
        .andExpect(jsonPath("$.row").value(3))
        .andExpect(jsonPath("$.orientation").value(Orientation.N.toString()));

    }


    











    
    
}
