package us.l4_4.dp1.end_of_line.player;

import static org.mockito.ArgumentMatchers.*;
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
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import us.l4_4.dp1.end_of_line.auth.AuthService;
import us.l4_4.dp1.end_of_line.authorities.Authorities;
import us.l4_4.dp1.end_of_line.authorities.AuthoritiesService;
import us.l4_4.dp1.end_of_line.enums.Color;
import us.l4_4.dp1.end_of_line.enums.FriendStatus;
import us.l4_4.dp1.end_of_line.enums.Hability;
import us.l4_4.dp1.end_of_line.friendship.Friendship;
import us.l4_4.dp1.end_of_line.friendship.FriendshipService;
import us.l4_4.dp1.end_of_line.game.Game;
import us.l4_4.dp1.end_of_line.game.GameService;
import us.l4_4.dp1.end_of_line.gameplayer.GamePlayer;
import us.l4_4.dp1.end_of_line.gameplayer.GamePlayerService;

@WebMvcTest(controllers = PlayerController.class, excludeFilters = @ComponentScan.Filter(
    type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class))

class PlayerControllerTests {

    private static final String BASE_URL = "/api/v1/players";
    private static final Integer TEST_PLAYER_ID = 2;
    private static final Integer TEST_GAME_ID = 1;
    private static final String TEST_PLAYER_NICKNAME = "playerNickname";
    private static final String avatar = "https://cdn-icons-png.flaticon.com/512/147/147144.png";
    
    @SuppressWarnings("unused")
    @Autowired
    private PlayerController playerController;

    @MockBean
    private PlayerService playerService;

    @MockBean
    private FriendshipService friendshipService;

    @MockBean
    private AuthService authService;

    @MockBean
    private AuthoritiesService authoritiesService;

    @MockBean
    private GamePlayerService gamePlayerService;

    @MockBean
    private GameService gameService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
	private ObjectMapper objectMapper;

    private Player admin, player, player2, player3, newPlayer; 
    private Authorities authority, authority2;
    private Friendship friendship, friendship2;
    private Game game;
    private GamePlayer gamePlayer1, gamePlayer2;
    private List<GamePlayer> gamePlayers;

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
        player2 = createPlayer(3, false);
        player3 = createPlayer(4, false);

        friendship = new Friendship();
        friendship.setId(1);
        friendship.setSender(player);
        friendship.setReceiver(player2);
        friendship.setFriendState(FriendStatus.ACCEPTED);

        friendship2 = new Friendship();
        friendship2.setId(2);
        friendship2.setSender(player3);
        friendship2.setReceiver(player);
        friendship2.setFriendState(FriendStatus.PENDING);

        gamePlayer1 = new GamePlayer();
        gamePlayer1.setId(1);
        gamePlayer1.setPlayer(player);
        gamePlayer1.setColor(Color.RED);
        gamePlayer1.setEnergy(3);
        gamePlayer1.setCards(new ArrayList<>());

        gamePlayer2 = new GamePlayer();
        gamePlayer2.setId(2);
        gamePlayer2.setPlayer(player2);
        gamePlayer2.setColor(Color.BLUE);
        gamePlayer2.setEnergy(3);
        gamePlayer2.setCards(new ArrayList<>());

        game = new Game();
        game.setId(1);
        game.setRound(1);
        game.setWinner(null);
        game.setStartedAt(Date.from(java.time.Instant.now()));
        game.setEndedAt(null);
        game.setMessage(null);
        game.setEffect(Hability.NONE);
        gamePlayers = List.of(gamePlayer1, gamePlayer2);
        game.setGamePlayers(gamePlayers);
    }

    @Test
    @WithMockUser(username = "Name1", password = "Player1!")
    void adminShouldFindAllPlayers() throws Exception {
        when(this.playerService.findAll()).thenReturn(List.of(player, player2, player3));

        mockMvc.perform(get(BASE_URL + "/all")).andExpect(status().isOk()).andExpect(jsonPath("$.size()").value(3))
                .andExpect(jsonPath("$[?(@.id == 2)].name").value("Name2"))
                .andExpect(jsonPath("$[?(@.id == 3)].name").value("Name3"))
                .andExpect(jsonPath("$[?(@.id == 4)].name").value("Name4"));
    }

    @Test
    @WithMockUser(username = "Name2", password = "Player2!")
    void playerShouldFindByNickname() throws Exception {
        when(this.playerService.findByNickname(TEST_PLAYER_NICKNAME)).thenReturn(player);

        mockMvc.perform(get(BASE_URL + "/nickname/{nickname}", TEST_PLAYER_NICKNAME)).andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Name2"));
    }

    @Test
    @WithMockUser(username = "Name2", password = "Player2!")
    void playerShouldFindById() throws Exception {
        when(this.playerService.findById(TEST_PLAYER_ID)).thenReturn(player);

        mockMvc.perform(get(BASE_URL + "/{id}", TEST_PLAYER_ID)).andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Name2"));
    }

    @Test
    @WithMockUser(username = "Name2", password = "Player2!")
    void playerShouldFindAllFriendsByPlayerId() throws Exception{
        when(this.playerService.findAllFriendsByPlayerId(TEST_PLAYER_ID)).thenReturn(List.of(player2));

        mockMvc.perform(get(BASE_URL + "/{id}/friends", TEST_PLAYER_ID)).andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[?(@.id == 3)].name").value("Name3"));
    }

    @Test
    @WithMockUser(username = "playerName", password = "Own3r!")
    void playerShouldFindAllFriendshipsByPlayerId() throws Exception {
        when(this.friendshipService.findAllFriendshipsByPlayerId(TEST_PLAYER_ID, FriendStatus.PENDING)).thenReturn(List.of(friendship2));

        mockMvc.perform(get(BASE_URL + "/{id}/friendships/{friendState}", TEST_PLAYER_ID, FriendStatus.PENDING)).andExpect(status().isOk())
        .andExpect(jsonPath("$.size()").value(1))
        .andExpect(jsonPath("$[?(@.id == 2)].sender.name").value("Name4"))
        .andExpect(jsonPath("$[?(@.id == 2)].receiver.name").value("Name2"));
    }

    @Test
    @WithMockUser(username = "playerName", password = "Own3r!")
    void playerShouldFindGameplayerByGameAndPlayer() throws Exception {
        when(this.gamePlayerService.findGamePlayerByGameAndPlayer(TEST_PLAYER_ID, TEST_GAME_ID)).thenReturn(gamePlayer2);

        mockMvc.perform(get(BASE_URL + "/{id}/games/{gameId}/gameplayer", TEST_PLAYER_ID, TEST_GAME_ID)).andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(2))
        .andExpect(jsonPath("$.color").value("BLUE"))
        .andExpect(jsonPath("$.player.name").value("Name3"));
    }

    @Test
    @WithMockUser(username = "playerName", password = "Play3r!")
    void shouldFindAllGamesByPlayerId() throws Exception {
        when(gameService.findAllGamesByPlayerId(TEST_PLAYER_ID)).thenReturn(List.of(game));

        mockMvc.perform(get(BASE_URL + "/{id}/games", TEST_PLAYER_ID))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.size()").value(1))
               .andExpect(jsonPath("$.[0]id").value(TEST_GAME_ID))
               .andExpect(jsonPath("$.[0]round").value(game.getRound()))
               .andExpect(jsonPath("$.[0]gamePlayers[0].player.name").value(gamePlayer1.getPlayer().getName()))
               .andExpect(jsonPath("$.[0]startedAt").isNotEmpty())
               .andExpect(jsonPath("$.[0]gamePlayers[0].color").value(gamePlayer1.getColor().toString()));
        verify(gameService).findAllGamesByPlayerId(TEST_PLAYER_ID);
    }

    @Test
    @WithMockUser(username = "playerName", password = "Play3r!")
    void shouledFindNotEndedGamesByPlayerId() throws Exception{
        when(gameService.findNotEndedGamesByPlayerId(TEST_PLAYER_ID)).thenReturn(List.of(game));

        mockMvc.perform(get(BASE_URL + "/{id}/games/notended", TEST_PLAYER_ID))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.size()").value(1)) 
               .andExpect(jsonPath("$.[0]id").value(TEST_GAME_ID))
               .andExpect(jsonPath("$.[0]round").value(game.getRound()))
               .andExpect(jsonPath("$.[0]startedAt").isNotEmpty()) 
               .andExpect(jsonPath("$.[0]endedAt").isEmpty()) 
               .andExpect(jsonPath("$.[0]winner").doesNotExist()) 
               .andExpect(jsonPath("$.[0]effect").value(game.getEffect().toString()))
               .andExpect(jsonPath("$.[0]gamePlayers[0].player.name").value(gamePlayer1.getPlayer().getName()))
               .andExpect(jsonPath("$.[0]gamePlayers[0].color").value(gamePlayer1.getColor().toString()))
               .andExpect(jsonPath("$.[0]gamePlayers[0].energy").value(gamePlayer1.getEnergy()));

        verify(gameService).findNotEndedGamesByPlayerId(TEST_PLAYER_ID);
    }

    @Test
    @WithMockUser()
    void shouldCreatePlayer() throws Exception {
        newPlayer = createPlayer(5, false);

        mockMvc.perform(post(BASE_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(newPlayer))).andExpect(status().isCreated());
    }

    @Test
	@WithMockUser(username = "Name2", password = "Player2!")
	void shouldUpdatePlayer() throws Exception {
		player.setName("UPDATED");
		player.setSurname("UPDATED");

		when(this.playerService.findById(TEST_PLAYER_ID)).thenReturn(player);
		when(this.playerService.update(any(Integer.class), any(Player.class))).thenReturn(player);

		mockMvc.perform(put(BASE_URL + "/{id}", TEST_PLAYER_ID).with(csrf()).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(player))).andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("UPDATED"))
				.andExpect(jsonPath("$.surname").value("UPDATED"));
	}

    @Test
	@WithMockUser(username = "Name1", password = "Player1!")
	  void shouldDeletePlayer() throws Exception {
		when(this.playerService.findById(TEST_PLAYER_ID)).thenReturn(player);
		
	    doNothing().when(this.playerService).delete(TEST_PLAYER_ID);
	    mockMvc.perform(delete(BASE_URL + "/{id}", TEST_PLAYER_ID).with(csrf()))
	         .andExpect(status().isOk());
	  }
}