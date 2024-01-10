import React, { useEffect, useState } from "react";
import tokenService from "../../services/token.service";

export default function PlayerStats() {
  const [stats, setStats] = useState({
    gamesPlayed: 0,
    gamesWon: 0,
    currentWinStreak: 0,
    maxWinStreak: 0,
    totalTimePlayed: "",
    averageGameDuration: "",
    winRatio: 0,
    longestGameDuration: "",
    shortestGameDuration: "",
    maxRounds: 0,
    minRounds: 0,
    avgRounds: 0,
    mostCommonColor: '',
    leastCommonColor: '',
    avgEnergyUsed: 0,
  });
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    async function fetchData() {
      try {
        const jwt = tokenService.getLocalAccessToken();
        const user = tokenService.getUser();

        const playerResponse = await fetch(`/api/v1/games/players/${user.id}`, {
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
        const colorFrequency = {
          BLUE: 0, GREEN: 0, RED: 0, YELLOW: 0, VIOLET: 0, ORANGE: 0, WHITE: 0, MAGENTA: 0
        };

        let totalRounds = 0;
        let maxRound = 0;
        let minRound = Number.MAX_SAFE_INTEGER;

        let mostCommonColor = 'N/A';
        let leastCommonColor = 'N/A';
        let maxColorCount = 0;
        let minColorCount = Number.MAX_SAFE_INTEGER;

        let totalTimePlayedMillis = 0;
        let longestGameMillis = 0;
        let shortestGameMillis = Number.MAX_SAFE_INTEGER;

        let totalEnergyUsed = 0;

        playerData.forEach((game) => {
          if (game.startedAt && game.endedAt) {
            const gameDuration = new Date(game.endedAt).getTime() - new Date(game.startedAt).getTime();
            totalTimePlayedMillis += gameDuration;
            longestGameMillis = Math.max(longestGameMillis, gameDuration);
            shortestGameMillis = Math.min(shortestGameMillis, gameDuration);
          }
          totalRounds += game.round;
          maxRound = Math.max(maxRound, game.round);
          minRound = Math.min(minRound, game.round);
          game.gamePlayers.forEach((gamePlayer) => {
            if (gamePlayer.player.id === user.id) {
              const energyUsed = 3 - gamePlayer.energy;
              totalEnergyUsed += energyUsed;
              const color = gamePlayer.color;
              if (colorFrequency.hasOwnProperty(color)) {
                colorFrequency[color]++;
              }
            }
          });
        });

        const avgRound = gamesPlayed > 0 ? Math.round(totalRounds / gamesPlayed) : 0;

        const avgEnergyUsed = gamesPlayed > 0 ? Math.round(totalEnergyUsed / gamesPlayed) : 0;

        const averageGameDurationMillis = gamesPlayed > 0 ? totalTimePlayedMillis / gamesPlayed : 0;

        const totalTimePlayed = convertMillisToTime(totalTimePlayedMillis);
        const averageGameDuration = convertMillisToTime(averageGameDurationMillis);
        const longestGameDuration = convertMillisToTime(longestGameMillis);
        const shortestGameDuration = shortestGameMillis !== Number.MAX_SAFE_INTEGER ? convertMillisToTime(shortestGameMillis) : "N/A";

        Object.entries(colorFrequency).forEach(([color, count]) => {
          if (count > maxColorCount) {
            maxColorCount = count;
            mostCommonColor = color;
          }
          if (count < minColorCount) {
            minColorCount = count;
            leastCommonColor = color;
          }
        });

        setStats({
          gamesPlayed,
          gamesWon,
          currentWinStreak,
          maxWinStreak,
          totalTimePlayed,
          averageGameDuration,
          winRatio,
          longestGameDuration,
          shortestGameDuration,
          maxRound,
          minRound,
          avgRound,
          mostCommonColor,
          leastCommonColor,
          avgEnergyUsed,
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
    <div>
      <h1 className="text-center" style={{ marginBottom: "3%" }}>My Stats</h1>
      {!loading ? (
        stats.gamesPlayed === 0 ? (
          <p style={{ textAlign: "center" }}>You must play a game to see your stats</p>
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

            <span>Average rounds:</span>
            <span style={{ textAlign: 'right' }}>{stats.avgRound}</span>

            <span>Max rounds:</span>
            <span style={{ textAlign: 'right' }}>{stats.maxRound}</span>

            <span>Min rounds:</span>
            <span style={{ textAlign: 'right' }}>{stats.minRound}</span>

            <span>Average duration:</span>
            <span style={{ textAlign: 'right' }}>{stats.averageGameDuration}</span>

            <span>Longest duration:</span>
            <span style={{ textAlign: 'right' }}>{stats.longestGameDuration}</span>

            <span>Shortest duration:</span>
            <span style={{ textAlign: 'right' }}>{stats.shortestGameDuration}</span>

            <span>Time played:</span>
            <span style={{ textAlign: 'right' }}>{stats.totalTimePlayed}</span>

            <span>Average energy used:</span>
            <span style={{ textAlign: 'right' }}>{stats.avgEnergyUsed}</span>

            <span>Most color used:</span>
            <span style={{ textAlign: 'right' }}>{stats.mostCommonColor}</span>

            <span>Least color used:</span>
            <span style={{ textAlign: 'right' }}>{stats.leastCommonColor}</span>
          </div>
        )
      ) : (
        <p style={{ textAlign: "center" }}>Loading player data...</p>
      )}
    </div>

  );
}