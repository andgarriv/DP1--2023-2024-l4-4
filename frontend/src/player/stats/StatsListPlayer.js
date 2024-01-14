import React, { useEffect, useState } from "react";
import tokenService from "../../services/token.service";
import "../../static/css/home/home.css";

export default function PlayerStats() {
  const [stats, setStats] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    async function fetchData() {
      try {
        const jwt = tokenService.getLocalAccessToken();
        const user = tokenService.getUser();

        const playerResponse = await fetch(`/api/v1/players/${user.id}/statistics`, {
          headers: { Authorization: `Bearer ${jwt}` },
        });
        if (!playerResponse.ok) {
          throw new Error(`HTTP error! status: ${playerResponse.status}`);
        }
        const playerStats = await playerResponse.json();
        setStats(playerStats);
        setLoading(false);

      } catch (error) {
        console.error("Error fetching data:", error);
        setLoading(false);
      }
    }

    fetchData();
  }, []);


  return (
    <div style={{ height: "90%", width: "100%" }}>
      <h1 className="text-center" style={{ marginBottom: "3%" }}>My Stats</h1>
      {!loading ? (
        !stats ? (
          <p style={{ textAlign: "center" }}>You must play a game to see your stats</p>
        ) : (
          <div className="scrollable-content">

            <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', textAlign: 'left', gap: '10px' }}>
              <span>Games played:</span>
              <span style={{ textAlign: 'right' }}>{stats.totalGames}</span>

              <span>Wins:</span>
              <span style={{ textAlign: 'right' }}>{stats.gamesWon}</span>

              <span>Winning ratio:</span>
              <span style={{ textAlign: 'right' }}>{stats.winRatio}</span>

              <span>Current streak:</span>
              <span style={{ textAlign: 'right' }}>{stats.currentWinStreak}</span>

              <span>Max streak:</span>
              <span style={{ textAlign: 'right' }}>{stats.maxWinStreak}</span>

              <span>Average rounds:</span>
              <span style={{ textAlign: 'right' }}>{stats.avgRounds}</span>

              <span>Max rounds:</span>
              <span style={{ textAlign: 'right' }}>{stats.maxRounds}</span>

              <span>Min rounds:</span>
              <span style={{ textAlign: 'right' }}>{stats.minRounds}</span>

              <span>Average energy used:</span>
              <span style={{ textAlign: 'right' }}>{stats.averageEnergyUsed}</span>

              <span>Most color used:</span>
              <span style={{ textAlign: 'right' }}>{stats.mostUsedColor}</span>

              <span>Least color used:</span>
              <span style={{ textAlign: 'right' }}>{stats.leastUsedColor}</span>

              <span>Average duration:</span>
              <span style={{ textAlign: 'right' }}>{stats.avgGameDuration}</span>

              <span>Longest duration:</span>
              <span style={{ textAlign: 'right' }}>{stats.maxGameDuration}</span>

              <span>Shortest duration:</span>
              <span style={{ textAlign: 'right' }}>{stats.minGameDuration}</span>

              <span>Time played:</span>
              <span style={{ textAlign: 'right' }}>{stats.totalGameDuration}</span>
            </div>
          </div>
        )
      ) : (
        <p style={{ textAlign: "center" }}>Loading player data...</p>
      )}
    </div>

  );
}