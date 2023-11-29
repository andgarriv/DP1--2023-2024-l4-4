import React, { useEffect, useState } from "react";
import tokenService from "../services/token.service";

export default function PlayerStats() {
  const [stats, setStats] = useState({
    gamesPlayed: 0,
    gamesWon: 0,
    currentWinStreak: 0,
    maxWinStreak: 0,
    totalTimePlayed: "",
    averageGameDuration: "",
    winRatio: 0,
  });
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    async function fetchData() {
      try {
        const jwt = tokenService.getLocalAccessToken();
        const user = tokenService.getUser();

        const playerResponse = await fetch(`/api/v1/games/player/${user.id}`, {
          headers: { Authorization: `Bearer ${jwt}` },
        });
        if (!playerResponse.ok) {
          throw new Error(`HTTP error! status: ${playerResponse.status}`);
        }
        const playerData = await playerResponse.json();

        const gamesWon = playerData.filter(
          (game) => game.winner && game.winner.id === user.id
        ).length;
        const gamesPlayed = playerData.length;
        const winRatio =
          gamesPlayed > 0 ? ((gamesWon / gamesPlayed) * 100).toFixed(0) : 0;

        let currentWinStreak = 0;
        let maxWinStreak = 0;
        for (let i = 0; i < playerData.length; i++) {
          if (playerData[i].winner && playerData[i].winner.id === user.id) {
            currentWinStreak++;
            maxWinStreak = Math.max(maxWinStreak, currentWinStreak);
          } else {
            currentWinStreak = 0;
          }
        }

        let totalTimePlayedMillis = 0;
        playerData.forEach((game) => {
          if (game.startedAt && game.endedAt) {
            totalTimePlayedMillis +=
              new Date(game.endedAt).getTime() -
              new Date(game.startedAt).getTime();
          }
        });

        const averageGameDurationMillis =
          gamesPlayed > 0 ? totalTimePlayedMillis / gamesPlayed : 0;

        let totalTimePlayed = convertMillisToTime(totalTimePlayedMillis);
        let averageGameDuration = convertMillisToTime(
          averageGameDurationMillis
        );

        setStats({
          gamesPlayed,
          gamesWon,
          currentWinStreak,
          maxWinStreak,
          totalTimePlayed,
          averageGameDuration,
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

  return (
    <div className="home-page-container" style={{ color: 'white', backgroundColor: 'black', padding: '20px' }}>
      <div className="hero-div">
        <h1 className="text-center">Stats</h1>
        {!loading ? (
          stats.gamesPlayed === 0 ? (
            <p>You must play a game to see your stats</p>
          ) : (
            <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', textAlign: 'left', gap: '10px' }}>
              <span>Games played:</span>
              <span style={{ textAlign: 'right' }}>{stats.gamesPlayed}</span>

              <span>Wins:</span>
              <span style={{ textAlign: 'right' }}>{stats.gamesWon}</span>

              <span>Winning ratio:</span>
              <span style={{ textAlign: 'right' }}>{stats.winRatio}%</span>

              <span>Winning streak:</span>
              <span style={{ textAlign: 'right' }}>{stats.currentWinStreak}</span>

              <span>Maximum winning streak:</span>
              <span style={{ textAlign: 'right' }}>{stats.maxWinStreak}</span>

              <span>Average duration:</span>
              <span style={{ textAlign: 'right' }}>{stats.averageGameDuration}</span>

              <span>Time played:</span>
              <span style={{ textAlign: 'right' }}>{stats.totalTimePlayed}</span>
            </div>
          )
        ) : (
          <p>Loading player data...</p>
        )}
      </div>
    </div>
  );
}