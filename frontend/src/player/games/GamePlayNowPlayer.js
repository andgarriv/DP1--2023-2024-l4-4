// PlayNowHandler.js
import tokenService from "../../services/token.service";

const PlayNowHandler = async (user, setVisible, setGameId, setOtherPlayer) => {
    try {
        const jwt = tokenService.getLocalAccessToken();
        const playerResponse = await fetch(`/api/v1/players/${user.id}/games/notended`, {
            headers: { Authorization: `Bearer ${jwt}` },
        });

        if (!playerResponse.ok) {
            throw new Error(`HTTP error! status: ${playerResponse.status}`);
        }

        const playerData = await playerResponse.json();
        const ongoingGame = playerData.find((game) => !game.endedAt);

        if (ongoingGame) {
            setGameId(ongoingGame.id);
            setVisible(true);
            setOtherPlayer(ongoingGame.gamePlayers.find((player) => player.player.id !== user.id).player);

        } else {
            window.location.href = "/play";
        }
    } catch (error) {
        console.error('Error fetching player games:', error);
    }
};

export default PlayNowHandler;
