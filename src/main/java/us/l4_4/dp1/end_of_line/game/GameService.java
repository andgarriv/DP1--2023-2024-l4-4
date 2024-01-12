package us.l4_4.dp1.end_of_line.game;

import java.time.Duration;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import us.l4_4.dp1.end_of_line.achievement.Achievement;
import us.l4_4.dp1.end_of_line.achievement.AchievementRepository;
import us.l4_4.dp1.end_of_line.achievement.AchievementService;
import us.l4_4.dp1.end_of_line.card.Card;
import us.l4_4.dp1.end_of_line.card.CardRepository;
import us.l4_4.dp1.end_of_line.enums.CardStatus;
import us.l4_4.dp1.end_of_line.enums.Color;
import us.l4_4.dp1.end_of_line.enums.Exit;
import us.l4_4.dp1.end_of_line.enums.Hability;
import us.l4_4.dp1.end_of_line.enums.Orientation;
import us.l4_4.dp1.end_of_line.exceptions.BadRequestException;
import us.l4_4.dp1.end_of_line.exceptions.ResourceNotFoundException;
import us.l4_4.dp1.end_of_line.gameplayer.GamePlayer;
import us.l4_4.dp1.end_of_line.gameplayer.GamePlayerRepository;
import us.l4_4.dp1.end_of_line.message.Message;
import us.l4_4.dp1.end_of_line.message.MessageRepository;
import us.l4_4.dp1.end_of_line.player.Player;
import us.l4_4.dp1.end_of_line.player.PlayerRepository;
import us.l4_4.dp1.end_of_line.player.PlayerService;
import us.l4_4.dp1.end_of_line.playerachievement.PlayerAchievement;
import us.l4_4.dp1.end_of_line.playerachievement.PlayerAchievementService;

@Service
public class GameService {

    GameRepository gameRepository;
    PlayerRepository playerRepository;
    GamePlayerRepository gamePlayerRepository;
    MessageRepository messageRepository;
    CardRepository cardRepository;
    AchievementRepository achievementRepository;
    PlayerAchievementService playerAchievementService;
    AchievementService achievementService;
    PlayerService playerService;
    PlayerAchievement playerAchievement;
    Player player;

    @Autowired
    public GameService(GameRepository gameRepository, PlayerRepository playerRepository,
            MessageRepository messageRepository,
            GamePlayerRepository gamePlayerRepository, CardRepository cardRepository,
            AchievementRepository achievementRepository, AchievementService achievementService,
            PlayerService playerService, PlayerAchievementService playerAchievementService) {
        this.gameRepository = gameRepository;
        this.playerRepository = playerRepository;
        this.messageRepository = messageRepository;
        this.gamePlayerRepository = gamePlayerRepository;
        this.cardRepository = cardRepository;
        this.achievementRepository = achievementRepository;
        this.achievementService = achievementService;
        this.playerService = playerService;
        this.playerAchievementService = playerAchievementService;
    }

    @Transactional
    public Game save(Game game) throws DataAccessException {
        return gameRepository.save(game);
    }

    @Transactional
    public void deleteGame(Integer gameId, Integer gamePlayerId) {
        Game game = gameRepository.findById(gameId).orElse(null);
        if (game == null) {
            throw new BadRequestException("No se puede eliminar una partida que no existe");
        }
        List<GamePlayer> gamePlayers = gamePlayerRepository.findGamePlayersByGameId(gameId);

        if (game.getRound() < 3) {
            gamePlayers.forEach(gamePlayerRepository::delete);

            gameRepository.deleteById(gameId);
            return;
        } else {
            GamePlayer winner = gamePlayers.stream()
                    .filter(gp -> !gp.getId().equals(gamePlayerId))
                    .findFirst()
                    .orElse(null);

            if (winner == null) {
                return;
            }

            game.setWinner(winner.getPlayer());
            game.setEndedAt(Date.from(java.time.Instant.now()));
            gameRepository.save(game);
            return;
        }
    }

    @Transactional
    public Game createNewGame(Integer playerID1, Integer playerID2, Color c1, Color c2) throws DataAccessException {

        if (checkOnlyOneGameForEachPlayer(playerID1)) {
            throw new BadRequestException("No se puede crear una partida ya que el jugador tiene una en curso");
        }
        Game game = new Game();
        game.setRound(1);
        game.setWinner(null);
        game.setStartedAt(Date.from(java.time.Instant.now()));
        game.setEndedAt(null);
        game.setMessage(null);
        game.setEffect(Hability.NONE);
        GamePlayer p1 = new GamePlayer();
        p1.setColor(c1);
        p1.setEnergy(3);
        p1.setPlayer(playerRepository.findById(playerID1).get());
        List<Card> cardsC1 = cardRepository.findAllTemplatedCardsByColor(c1);
        List<Card> cardsC1Duplicated = new ArrayList<>();
        for (Card card : cardsC1) {
            Card newCard = new Card();
            if (card.getExit() == Exit.START) {
                newCard.setColumn(2);
                newCard.setRow(4);
                newCard.setCardState(CardStatus.ON_BOARD);
                newCard.setUpdatedAt(Date.from(java.time.Instant.now()));
            } else {
                newCard.setColumn(null);
                newCard.setRow(null);
                newCard.setCardState(CardStatus.IN_DECK);
            }
            newCard.setInitiative(card.getInitiative());
            newCard.setColor(card.getColor());
            newCard.setExit(card.getExit());
            newCard.setOrientation(card.getOrientation());
            cardsC1Duplicated.add(newCard);
            cardRepository.save(newCard);
        }
        p1.setCards(cardsC1Duplicated);

        GamePlayer p2 = new GamePlayer();
        p2.setColor(c2);
        p2.setEnergy(3);
        p2.setPlayer(playerRepository.findById(playerID2).get());
        List<Card> cardsC2 = cardRepository.findAllTemplatedCardsByColor(c2);
        List<Card> cardsC2Duplicated = new ArrayList<>();
        for (Card card : cardsC2) {
            Card newCard = new Card();
            if (card.getExit() == Exit.START) {
                newCard.setColumn(4);
                newCard.setRow(4);
                newCard.setCardState(CardStatus.ON_BOARD);
                newCard.setUpdatedAt(Date.from(java.time.Instant.now()));
            } else {
                newCard.setColumn(null);
                newCard.setRow(null);
                newCard.setCardState(CardStatus.IN_DECK);

            }
            newCard.setInitiative(card.getInitiative());
            newCard.setColor(card.getColor());
            newCard.setExit(card.getExit());
            newCard.setOrientation(card.getOrientation());
            cardsC2Duplicated.add(newCard);
            cardRepository.save(newCard);
        }
        p2.setCards(cardsC2Duplicated);
        gamePlayerRepository.save(p1);
        gamePlayerRepository.save(p2);
        List<GamePlayer> gamePlayers = List.of(p1, p2);
        game.setGamePlayers(gamePlayers);
        game.setGamePlayerTurnId(p1.getId());
        updateFiveRandomCards(p1.getId());
        updateFiveRandomCards(p2.getId());
        gameRepository.save(game);
        return game;
    }

    @Transactional(readOnly = true)
    public Iterable<Game> findAllGames() throws DataAccessException {
        return gameRepository.findAll();
    }

    private Boolean checkOnlyOneGameForEachPlayer(Integer id1) {
        Boolean res = false;
        if (!gameRepository.findNotEndedGamesByPlayerId(id1).isEmpty()) {
            res = true;
        }
        return res;
    }

    public List<Card> updateFiveRandomCards(Integer gamePlayerId) {
        if (gamePlayerRepository.findById(gamePlayerId) == null) {
            throw new ResourceNotFoundException("GamePlayer", "id", gamePlayerId);
        }
        List<Card> cards = gamePlayerRepository.findById(gamePlayerId).get().getCards();
        if (cards.stream().anyMatch(card -> card.getCardState() == CardStatus.IN_HAND)) {
            return new ArrayList<>();
        }
        cards = cards.stream()
                .filter(card -> card.getCardState() != CardStatus.ON_BOARD)
                .collect(Collectors.toList());
        Collections.shuffle(cards);
        List<Card> randomCards = cards.subList(0, Math.min(5, cards.size()));
        for (Card card : randomCards) {
            card.setCardState(CardStatus.IN_HAND);
            cardRepository.save(card);
        }
        return randomCards;
    }

    @Transactional(readOnly = true)
    public Game findById(Integer id) throws ResourceNotFoundException {
        return gameRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Game", "id", id));
    }

    @Transactional
    public Game updateGame(Integer id, GameDTO gameDTO) throws DataAccessException {
        Game game = gameRepository.findById(id).get();
        game.setRound(gameDTO.getRounds());
        if (gameDTO.getWinner_id() != null) {
            game.setWinner(playerRepository.findById(gameDTO.getWinner_id()).get());
        }
        game.setStartedAt(gameDTO.getStartedAt());
        if (gameDTO.getEndedAt() != null) {
            game.setEndedAt(gameDTO.getEndedAt());
        }
        if (gameDTO.getMessage_id() != null) {
            List<Message> messages = gameDTO.getMessage_id()
                    .stream()
                    .map(messageId -> messageRepository.findById(messageId)
                            .orElseThrow(() -> new ResourceNotFoundException("Message", "id", messageId)))
                    .collect(Collectors.toList());
            game.setMessage(messages);
        }
        List<GamePlayer> gamePlayers = gameDTO.getGamePlayers_ids()
                .stream()
                .map(gamePlayerId -> gamePlayerRepository.findById(gamePlayerId).get())
                .collect(Collectors.toList());
        game.setGamePlayers(gamePlayers);
        return gameRepository.save(game);
    }

    @Transactional(readOnly = true)
    public Integer whoIsNext(Integer gameId) {
        Game game = gameRepository.findById(gameId).get();
        List<GamePlayer> gamePlayers = game.getGamePlayers();
        Integer id1 = gamePlayers.get(0).getId();
        Integer id2 = gamePlayers.get(1).getId();
        Integer res = id1;
        List<Card> player1Cards = gamePlayers.get(0).getCards().stream()
                .filter(card -> card.getCardState() == CardStatus.ON_BOARD)
                .sorted(Comparator.comparing(Card::getUpdatedAt).reversed())
                .collect(Collectors.toList());

        List<Card> player2Cards = gamePlayers.get(1).getCards().stream()
                .filter(card -> card.getCardState() == CardStatus.ON_BOARD)
                .sorted(Comparator.comparing(Card::getUpdatedAt).reversed())
                .collect(Collectors.toList());

        Integer maxSize = Math.min(player1Cards.size(), player2Cards.size());

        for (int i = 0; i < maxSize; i++) {
            if (player1Cards.get(i).getInitiative() > player2Cards.get(i).getInitiative()) {
                res = id2;
                break;
            } else if (player1Cards.get(i).getInitiative() < player2Cards.get(i).getInitiative()) {
                res = id1;
                break;
            }
        }
        return res;
    }

    @Transactional(readOnly = true)
    public List<Game> findNotEndedGamesByPlayerId(Integer playerId) {
        return gameRepository.findNotEndedGamesByPlayerId(playerId);
    }

    @Transactional(readOnly = true)
    public List<Game> findAllGamesByPlayerId(Integer playerId) {
        return gameRepository.findGamesByPlayerId(playerId);
    }

    @Transactional
    public List<String> findPosiblePositionOfAGamePlayerGiven(Integer gamePlayerId, Integer gameId) {
        return findPosiblePositionOfAGamePlayerGiven(gamePlayerId, gameId, false);
    }

    @Transactional
    public List<String> findPosiblePositionOfAGamePlayerGiven(Integer gamePlayerId, Integer gameId, Boolean reverse) {
        GamePlayer gp = gamePlayerRepository.findById(gamePlayerId).get();
        Game game = gameRepository.findById(gameId).get();
        Integer round = game.getRound();
        Integer cardsInHand = gamePlayerRepository.findById(gamePlayerId).get().getCards().stream()
                .filter(card -> card.getCardState() == CardStatus.IN_HAND)
                .collect(Collectors.toList()).size();
        Integer energy = gp.getEnergy();
        Boolean conditions = cardsInHand.equals(5) && energy > 0 && round > 4;
        List<String> cartasON_BOARD = gamePlayerRepository.findGamePlayersByGameId(gameId)
                .get(0)
                .getCards()
                .stream()
                .filter(card -> card.getCardState() == CardStatus.ON_BOARD)
                .map(card -> card.getColumn() + "," + card.getRow())
                .collect(Collectors.toList());
        cartasON_BOARD.addAll(gamePlayerRepository.findGamePlayersByGameId(gameId)
                .get(1)
                .getCards()
                .stream()
                .filter(card -> card.getCardState() == CardStatus.ON_BOARD)
                .map(card -> card.getColumn() + "," + card.getRow())
                .collect(Collectors.toList()));

        List<Card> ultimasCartasEchadas = gp.getCards().stream()
                .filter(card -> card.getCardState() == CardStatus.ON_BOARD)
                .sorted(Comparator.comparing(Card::getUpdatedAt).reversed())
                .collect(Collectors.toList());

        Card ultimaCartaEchada = ultimasCartasEchadas.get(0);
        if (reverse && conditions)
            ultimaCartaEchada = ultimasCartasEchadas.get(1);

        if (game.getEffect() == Hability.REVERSE && conditions)
            ultimaCartaEchada = ultimasCartasEchadas.get(1);

        Integer n = ultimaCartaEchada.getColumn();
        Integer m = ultimaCartaEchada.getRow();

        List<String> res = new ArrayList<>();

        String norte = n + "," + (m - 1);
        if ((m - 1) < 0)
            norte = n + "," + 6;

        String sur = n + "," + (m + 1);
        if ((m + 1) > 6)
            sur = n + "," + 0;

        String este = (n + 1) + "," + m;
        if ((n + 1) > 6)
            este = 0 + "," + m;

        String oeste = (n - 1) + "," + m;
        if ((n - 1) < 0)
            oeste = 6 + "," + m;

        List<Integer> salidas = extraerNumerosDeSalida(ultimaCartaEchada.getExit().toString());

        if (ultimaCartaEchada.getOrientation().equals(Orientation.S)) {
            if (ultimaCartaEchada.getExit().equals(Exit.START)) {
                if (!cartasON_BOARD.contains(norte))
                    res.add(norte + ",S");
            } else {
                if (salidas.get(0) == 1 && !cartasON_BOARD.contains(oeste)) {
                    res.add(oeste + ",E");
                }
                if (salidas.get(1) == 1 && !cartasON_BOARD.contains(norte)) {
                    res.add(norte + ",S");
                }
                if (salidas.get(2) == 1 && !cartasON_BOARD.contains(este)) {
                    res.add(este + ",W");
                }
            }
        }

        if (ultimaCartaEchada.getOrientation().equals(Orientation.N)) {
            if (salidas.get(0) == 1 && !cartasON_BOARD.contains(este)) {
                res.add(este + ",W");
            }
            if (salidas.get(1) == 1 && !cartasON_BOARD.contains(sur)) {
                res.add(sur + ",N");
            }
            if (salidas.get(2) == 1 && !cartasON_BOARD.contains(oeste)) {
                res.add(oeste + ",E");
            }
        }

        if (ultimaCartaEchada.getOrientation().equals(Orientation.E)) {
            if (salidas.get(0) == 1 && !cartasON_BOARD.contains(sur)) {
                res.add(sur + ",N");
            }
            if (salidas.get(1) == 1 && !cartasON_BOARD.contains(oeste)) {
                res.add(oeste + ",E");
            }
            if (salidas.get(2) == 1 && !cartasON_BOARD.contains(norte)) {
                res.add(norte + ",S");

            }
        }

        if (ultimaCartaEchada.getOrientation().equals(Orientation.W)) {
            if (salidas.get(0) == 1 && !cartasON_BOARD.contains(norte)) {
                res.add(norte + ",S");
            }
            if (salidas.get(1) == 1 && !cartasON_BOARD.contains(este)) {
                res.add(este + ",W");
            }
            if (salidas.get(2) == 1 && !cartasON_BOARD.contains(sur)) {
                res.add(sur + ",N");

            }
        }
        return res;
    }

    private static ArrayList<Integer> extraerNumerosDeSalida(String texto) {

        ArrayList<Integer> digitos = new ArrayList<>();

        Pattern patron = Pattern.compile("_(\\d+)_");
        Matcher coincidencias = patron.matcher(texto);

        if (coincidencias.find()) {

            String secuencia = coincidencias.group(1);

            for (char digito : secuencia.toCharArray()) {
                digitos.add(Character.getNumericValue(digito));
            }
        }

        return digitos;
    }

    @Transactional
    public Game updateGameEffect(Integer gameId, ChangeEffectRequest changeEffectRequest) {
        Game game = gameRepository.findById(gameId).get();
        GamePlayer gp = gamePlayerRepository.findById(game.getGamePlayerTurnId()).get();

        if (game.getEffect() != Hability.NONE) {
            System.out.println("No se puede cambiar el efecto porque ya hay uno activo");

        } else if (gp.getCards().stream().filter(card -> card.getCardState() == CardStatus.IN_HAND).count() == 5) {
            if (gp.getEnergy() <= 0) {
                System.out.println("No tienes suficiente energia para cambiar el efecto");
            } else if (changeEffectRequest.getEffect() != null && game.getRound() > 4) {
                Hability effect = Hability.valueOf(changeEffectRequest.getEffect());
                game.setEffect(effect);
                gp.setEnergy(gp.getEnergy() - 1);
                if (effect == Hability.EXTRA_GAS) {
                    extraGasEffect(gameId);
                }
            } else {
                game.setEffect(Hability.NONE);
            }
        }

        return gameRepository.save(game);
    }

    @Transactional
    public Card extraGasEffect(Integer gameId) {
        Game game = gameRepository.findById(gameId).get();
        Integer gamePlayerId = game.getGamePlayerTurnId();
        List<Card> cards = gamePlayerRepository.findById(gamePlayerId).get().getCards();
        List<Card> cardsInHand = cards.stream()
                .filter(card -> card.getCardState() == CardStatus.IN_HAND)
                .collect(Collectors.toList());
        Card cardToAdd = new Card();
        if (cardsInHand.size() < 6) {
            cardToAdd = cards.stream()
                    .filter(card -> card.getCardState() == CardStatus.IN_DECK)
                    .findFirst()
                    .orElseThrow(() -> new ResourceNotFoundException("Card", "id", gamePlayerId));
            cardToAdd.setCardState(CardStatus.IN_HAND);
            cardRepository.save(cardToAdd);
        }

        return cardToAdd;
    }

    @Transactional
    public List<Card> changeCardsInHand(Integer gameId) {
        List<Card> newCardsInHand = new ArrayList<>();
        Game game = gameRepository.findById(gameId).get();
        Integer gamePlayerId = game.getGamePlayerTurnId();
        List<Card> cards = gamePlayerRepository.findById(gamePlayerId).get().getCards();
        List<Card> allCardsInHand = cards.stream()
                .filter(card -> card.getCardState() == CardStatus.IN_HAND)
                .collect(Collectors.toList());
        if (game.getRound() == 1 || game.getRound() == 2) {
            for (Card card : allCardsInHand) {
                if (card.getCardState() == CardStatus.IN_HAND) {
                    card.setCardState(CardStatus.IN_DECK);
                    cardRepository.save(card);
                }
            }
            Collections.shuffle(cards);
            for (Card card : cards) {
                if (card.getCardState() == CardStatus.IN_DECK) {
                    card.setCardState(CardStatus.IN_HAND);
                    cardRepository.save(card);
                    newCardsInHand.add(card);
                }
                if (newCardsInHand.size() == 5) {
                    break;
                }
            }
        }
        return newCardsInHand;
    }

    private List<Achievement> findAchievementsNotAchieved(Integer playerId) {
        List<Achievement> achievements = StreamSupport.stream(achievementRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
        List<Achievement> res = List.copyOf(achievements);
        Player player = playerRepository.findById(playerId).get();
        List<PlayerAchievement> playerAchievements = player.getPlayerAchievement();
        for (int i = 0; i < achievements.size(); i++) {
            for (PlayerAchievement pa : playerAchievements) {
                if (achievements.get(i).getPlayerAchievements().contains(pa)) {
                    res.remove(i);
                }
            }
        }
        return res;
    }

    @Transactional
    public void createPlayerAchievement(Integer playerId) {
        List<Achievement> achievements = findAchievementsNotAchieved(playerId);
        if (achievements.isEmpty())
            return;
        Player player = playerRepository.findById(playerId).get();
        for (Achievement achievement : achievements) {
            String category = achievement.getCategory().toString();
            playerAchievement = new PlayerAchievement();
            switch (category) {
                case "GAMES_PLAYED":
                    Integer gamesPlayed = gameRepository.findGamesByPlayerId(playerId).size();
                    if (achievement.getThreshold() <= gamesPlayed) {
                        playerAchievement
                                .setAchieveAt(java.time.Instant.now().atZone(ZoneId.systemDefault()).toLocalDate());
                        playerAchievementService.save(playerAchievement);
                        achievement.getPlayerAchievements().add(playerAchievement);
                        player.getPlayerAchievement().add(playerAchievement);
                        achievementService.update(achievement.getId(), achievement);
                    }
                    break;
                case "VICTORIES":
                    Integer victories = gameRepository.findGamesByPlayerId(playerId).stream()
                            .filter(g -> g.getWinner().getId().equals(playerId)).toList().size();
                    if (achievement.getThreshold() <= victories) {
                        playerAchievement
                                .setAchieveAt(java.time.Instant.now().atZone(ZoneId.systemDefault()).toLocalDate());
                        playerAchievementService.save(playerAchievement);
                        achievement.getPlayerAchievements().add(playerAchievement);
                        player.getPlayerAchievement().add(playerAchievement);
                        achievementService.update(achievement.getId(), achievement);
                    }
                    break;
                default:
                    Integer totalMiliTime = gameRepository.findGamesByPlayerId(playerId).stream()
                            .mapToInt(g -> (int) (g.getEndedAt().getTime() - g.getStartedAt().getTime())).sum();
                    Double totalHourTime = totalMiliTime / (1000 * 3600.0);
                    if (achievement.getThreshold() <= totalHourTime) {
                        playerAchievement
                                .setAchieveAt(java.time.Instant.now().atZone(ZoneId.systemDefault()).toLocalDate());
                        playerAchievementService.save(playerAchievement);
                        achievement.getPlayerAchievements().add(playerAchievement);
                        player.getPlayerAchievement().add(playerAchievement);
                        achievementService.update(achievement.getId(), achievement);
                    }
            }
        }
        playerService.update(playerId, player);
    }

    private Game nextRound(Game game, Integer cardsToGive) {
        Integer round = game.getRound();
        Integer turnGamePlayerId = game.getGamePlayerTurnId();
        Integer otherGamePlayerId = game.getGamePlayers().stream()
                .filter(gp -> !gp.getId().equals(turnGamePlayerId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("GamePlayer", "id", turnGamePlayerId))
                .getId();
        Integer nextGamePlayerId = otherGamePlayerId;

        if (round != 1 && round % 2 == 0) {
            nextGamePlayerId = whoIsNext(game.getId());
        }
        giveCards(turnGamePlayerId, cardsToGive);
        game.setGamePlayerTurnId(nextGamePlayerId);
        game.setEffect(Hability.NONE);
        game.setRound(round + 1);

        gameRepository.save(game);
        return game;
    }

    @Transactional
    public Game updateGameTurn(Integer gameId) {
        Game game = gameRepository.findById(gameId).get();
        Hability effect = game.getEffect();
        Integer round = game.getRound();
        Integer turnGamePlayerId = game.getGamePlayerTurnId();
        List<Card> cards = gamePlayerRepository.findById(turnGamePlayerId).get().getCards().stream()
                .filter(card -> card.getCardState() == CardStatus.IN_HAND)
                .collect(Collectors.toList());
        Integer cardsInHand = cards.size();

        switch (round) {
            case 1, 2:
                game = nextRound(game, 1);
                break;
            case 3, 4:
                if (cardsInHand <= 3) {
                    game = nextRound(game, 2);
                }
                break;
            default:
                if (effect == Hability.SPEED_UP) {
                    if (cardsInHand <= 2) {
                        game = nextRound(game, 3);
                    }
                } else if (effect == Hability.BRAKE || effect == Hability.EXTRA_GAS) {
                    if (cardsInHand <= 4) {
                        game = nextRound(game, 1);
                    }
                } else {
                    if (cardsInHand <= 3) {
                        game = nextRound(game, 2);
                    }
                }
        }

        if (findPosiblePositionOfAGamePlayerGiven(turnGamePlayerId, gameId).isEmpty()
                && (findPosiblePositionOfAGamePlayerGiven(turnGamePlayerId, gameId, true).isEmpty())) {
            Integer otherGamePlayerId = game.getGamePlayers().stream()
                    .filter(gp -> !gp.getId().equals(turnGamePlayerId))
                    .findFirst()
                    .orElseThrow(() -> new ResourceNotFoundException("GamePlayer", "id", turnGamePlayerId))
                    .getId();
            game.setEndedAt(Date.from(java.time.Instant.now()));
            game.setWinner(gamePlayerRepository.findById(otherGamePlayerId).get().getPlayer());
            gameRepository.save(game);
        }
        gameRepository.save(game);
        return game;
    }

    @Transactional
    private void giveCards(Integer gamePlayerId, Integer numCards) {
        GamePlayer gamePlayer = gamePlayerRepository.findById(gamePlayerId).get();
        List<Card> cards = gamePlayer.getCards();
        List<Card> cardsInDeck = cards.stream()
                .filter(card -> card.getCardState() == CardStatus.IN_DECK)
                .collect(Collectors.toList());
        List<Card> newCards = new ArrayList<>();
        for (Card card : cards) {
            newCards.add(card);
        }
        Collections.shuffle(cardsInDeck);
        Integer max = Math.min(cardsInDeck.size(), numCards);
        List<Card> randomCards = cardsInDeck.subList(0, max);
        for (Card card : randomCards) {
            newCards.remove(card);
            card.setCardState(CardStatus.IN_HAND);
            newCards.add(card);
            try {
                cardRepository.save(card);
            } catch (Exception e) {
                System.out.println("Error al guardar la carta: " + e.getMessage());
            }
        }
        gamePlayer.setCards(cards);
        gamePlayerRepository.save(gamePlayer);
    }

    @Transactional(readOnly = true)
    public Map<String, String> calculateStatistics() {
        Map<String, String> stats = new HashMap<>();

        Integer numberOfPlayers = gameRepository.getNumberOPlayers();
        Integer numberOfGames = gameRepository.getTotalNumberOfGames();
        Integer numberOfGamesFinished = gameRepository.getNumberOfGamesFinished();
        Integer numberOfGamesPending = gameRepository.getTotalNumberOfGames() - numberOfGamesFinished;
        Duration maxGameDuration = gameRepository.getMaxGameDuration();
        Duration minGameDuration = gameRepository.getMinGameDuration();
        Double averageGameDurationInNanoseconds = gameRepository.getAverageGameDuration();
        Long totalGameDurationInNanoseconds = gameRepository.getTotalGameDuration();

        Map<Color, Long> colorUsageMap = new HashMap<>();
        for (Color color : Color.values()) {
            colorUsageMap.put(color, 0L);
        }

        List<Object[]> colorUsage = gameRepository.getColorUsage();
        for (Object[] usage : colorUsage) {
            colorUsageMap.put((Color) usage[0], (Long) usage[1]);
        }

        Map.Entry<Color, Long> mostUsedColor = Collections.max(colorUsageMap.entrySet(), Map.Entry.comparingByValue());
        Map.Entry<Color, Long> leastUsedColor = Collections.min(colorUsageMap.entrySet(), Map.Entry.comparingByValue());

        if (numberOfGames != null) {
            stats.put("totalPlayers", String.valueOf(numberOfPlayers));
            stats.put("totalGames", String.valueOf(numberOfGames));
            stats.put("gamesFinished", String.valueOf(numberOfGamesFinished));
            stats.put("gamesPending", String.valueOf(numberOfGamesPending));
            stats.put("avgGames",
                    String.format("%.1f", gameRepository.getTotalNumberOfGames().floatValue()
                            / gameRepository.getNumberOPlayers().floatValue()));
            stats.put("maxRounds", String.valueOf(gameRepository.getMaxRounds()));
            stats.put("minRounds", String.valueOf(gameRepository.getMinRounds()));
            stats.put("avgRounds", String.format("%.1f", gameRepository.getAverageRounds()));
            stats.put("maxGamesPlayed", String.valueOf(gameRepository.getMaxGamesPlayedByPlayer()));
            stats.put("minGamesPlayed", String.valueOf(gameRepository.getMinGamesPlayedByPlayer()));
            stats.put("averageEnergyUsed", String.format("%.2f", gameRepository.getAverageEnergyUsed()));
            stats.put("mostUsedColor", mostUsedColor.getKey().name());
            stats.put("leastUsedColor", leastUsedColor.getKey().name());
        } else {
            stats.put("totalPlayers", "N/A");
            stats.put("totalGames", "N/A");
            stats.put("gamesFinished", "N/A");
            stats.put("gamesPending", "N/A");
            stats.put("avgGames", "N/A");
            stats.put("maxRounds", "N/A");
            stats.put("minRounds", "N/A");
            stats.put("avgRounds", "N/A");
            stats.put("maxGamesPlayed", "N/A");
            stats.put("minGamesPlayed", "N/A");
            stats.put("averageEnergyUsed", "N/A");
            stats.put("mostUsedColor", "N/A");
            stats.put("leastUsedColor", "N/A");
        }

        if (maxGameDuration != null) {
            stats.put("maxGameDuration", formatDuration(maxGameDuration));
        } else {
            stats.put("maxGameDuration", "N/A");
        }

        if (minGameDuration != null) {
            stats.put("minGameDuration", formatDuration(minGameDuration));
        } else {
            stats.put("minGameDuration", "N/A");
        }

        if (averageGameDurationInNanoseconds != null) {
            Long averageGameDurationInSeconds = averageGameDurationInNanoseconds.longValue() / 1_000_000_000;
            Duration averageGameDuration = Duration.ofSeconds(averageGameDurationInSeconds);
            stats.put("averageGameDuration", formatDuration(averageGameDuration));
        } else {
            stats.put("averageGameDuration", "N/A");
        }

        if (totalGameDurationInNanoseconds != null) {
            Long totalGameDurationInSeconds = totalGameDurationInNanoseconds / 1_000_000_000;
            Duration totalGameDuration = Duration.ofSeconds(totalGameDurationInSeconds);
            stats.put("totalGameDuration", formatDuration(totalGameDuration));
        } else {
            stats.put("totalGameDuration", "N/A");
        }

        return stats;
    }

    private String formatDuration(Duration duration) {
        return String.format("%dh %dm %ds", duration.toHours(), duration.toMinutesPart(), duration.toSecondsPart());
    }

}