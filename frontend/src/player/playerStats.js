import React, { useEffect, useState } from "react";
import tokenService from "../services/token.service";

export default function PlayerStats() {
  const [stats, setStats] = useState({
    gamesPlayed: 0,
    gamesWon: 0,
    winStreak: 0,
    totalTimePlayed: "",
    winRatio: 0.0,
  });
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    async function fetchData() {
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

        const userGames = playerData.filter((game) =>
          game.gamePlayers.some((gp) => gp.player.id === user.id)
        );
        const gamesWon = userGames.filter(
          (game) => game.winner && game.winner.id === user.id
        ).length;
        const gamesPlayed = userGames.length;
        const winRatio =
          gamesPlayed > 0 ? (gamesWon / gamesPlayed).toFixed(2) : 0;

        let winStreak = 0;
        for (let i = userGames.length - 1; i >= 0; i--) {
          if (userGames[i].winner && userGames[i].winner.id === user.id) {
            winStreak++;
          } else {
            break;
          }
        }

        let totalTimePlayedMillis = 0;
        userGames.forEach((game) => {
          if (game.startedAt && game.endedAt) {
            totalTimePlayedMillis +=
              new Date(game.endedAt).getTime() -
              new Date(game.startedAt).getTime();
          }
        });

        let totalTimePlayed = convertMillisToTime(totalTimePlayedMillis);

        setStats({
          gamesPlayed,
          gamesWon,
          winStreak,
          totalTimePlayed,
          winRatio,
        });

        setLoading(false);
      } catch (error) {
        console.error("Error fetching data:", error);
        setLoading(false);
      }
    }

    fetchData();
  }, []);

  function convertMillisToTime(milliseconds) {
    let seconds = Math.floor(milliseconds / 1000);
    let minutes = Math.floor(seconds / 60);
    let hours = Math.floor(minutes / 60);

    seconds = seconds % 60;
    minutes = minutes % 60;

    return `${hours}h ${minutes}m ${seconds}s`;
  }

  if (loading) {
    return <div>Loading...</div>;
  }

  if (stats.gamesPlayed === 0) {
    return (
      <div className="home-page-container">
        <div className="hero-div">
          <h1 className="text-center">Stats</h1>
          <p>You must play a game to see your stats</p>
        </div>
      </div>
    );
  }

  return (
    <div className="home-page-container">
      <div className="hero-div">
        <h1 className="text-center">Stats</h1>
        <div>
          <p>Games played: {stats.gamesPlayed}</p>
          <p>Wins: {stats.gamesWon}</p>
          <p>Winning streak: {stats.winStreak}</p>
          <p>Time played: {stats.totalTimePlayed}</p>
          <p>Winning ratio: {stats.winRatio}</p>
        </div>
      </div>
    </div>
  );
}
