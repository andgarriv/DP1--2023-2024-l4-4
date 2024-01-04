import tokenService from "../services/token.service";

const handlePlayNowClick = async (setMessage, setVisible) => {
    try {
        const jwt = tokenService.getLocalAccessToken();
        const user = tokenService.getUser();
        
      const playerResponse = await fetch(`/api/v1/games/players/${user.id}/notended`, {
        headers: { Authorization: `Bearer ${jwt}` },
      });

      if (!playerResponse.ok) {
        throw new Error(`HTTP error! status: ${playerResponse.status}`);
      }

      const playerData = await playerResponse.json();
      const ongoingGame = playerData.find((game) => !game.endedAt);

      if (ongoingGame) {
        if (window.confirm("Do you want to join the ongoing game?")) {
          window.location.href = `/games/${ongoingGame.id}`;
        }
      } else {
        window.location.href = "/play";
      }
    } catch (error) {
      console.error('Error fetching player games:', error);
    }
};

export default handlePlayNowClick;
