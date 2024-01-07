// PlayNowHandler.js
import tokenService from "../../services/token.service";

const PlayNowHandler = async (user, setVisible, setGameId) => {
    try {
        const jwt = tokenService.getLocalAccessToken();
        const playerResponse = await fetch(`/api/v1/games/players/${user.id}/notended`, {
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
        } else {
            window.location.href = "/play";
        }
    } catch (error) {
        console.error('Error fetching player games:', error);
    }
};

export default PlayNowHandler;
