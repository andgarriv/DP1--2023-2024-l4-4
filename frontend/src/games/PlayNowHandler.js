import tokenService from "../services/token.service";

const handlePlayNowClick = async (setMessage, setVisible) => {
    try {
        const jwt = tokenService.getLocalAccessToken();
        const user = tokenService.getUser();
        
      const playerResponse = await fetch(`/api/v1/games/player`, {
        headers: { Authorization: `Bearer ${jwt}` },
      });

      if (!playerResponse.ok) {
        throw new Error(`HTTP error! status: ${playerResponse.status}`);
      }

      const playerData = await playerResponse.json();

      const userGames = playerData.filter(game =>
        game.gamePlayers.some(gp => gp.player.id === user.id)
    );

    const ongoingGame = userGames.find(game => game.endedAt === null);

      if (ongoingGame) {
        if (window.confirm("Do you want to join the ongoing game?")) {
          window.location.href = `/game/${ongoingGame.id}`;
        }
      } else {
        window.location.href = "/play";
      }
    } catch (error) {
      setMessage("Error fetching player games");
      setVisible(true);
    }
};

export default handlePlayNowClick;
