import React, { useEffect, useState } from "react";
import tokenService from "../../services/token.service";
import "../../static/css/home/home.css";

export default function GlobalStats() {
    const [stats, setStats] = useState(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        async function fetchData() {
            try {
                const jwt = tokenService.getLocalAccessToken();
                const response = await fetch(`/api/v1/games/statistics`, {
                    headers: { Authorization: `Bearer ${jwt}` },
                });
                if (!response.ok) {
                    throw new Error(`HTTP error! status: ${response.status}`);
                }
                const stats = await response.json();
                setStats(stats);
                setLoading(false);

            } catch (error) {
                console.error("Error fetching data:", error);
                setLoading(false);
            }
        }

        fetchData();
    }, []);



    return (
        <div style={{ height: "90%", width: "100%", }}>
            <h1 className="text-center" style={{ marginBottom: "3%" }}>Global Stats</h1>
            {!loading ? (
                !stats ? (
                    <p style={{ textAlign: "center" }}>No games played yet</p>
                ) : (
                    <div className="scrollable-content">

                        <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', textAlign: 'left', gap: '10px' }}>
                            <span>Players who played:</span>
                            <span style={{ textAlign: 'right' }}>{stats.totalPlayers}</span>

                            <span>Games played:</span>
                            <span style={{ textAlign: 'right' }}>{stats.totalGames}</span>

                            <span>Games finished:</span>
                            <span style={{ textAlign: 'right' }}>{stats.gamesFinished}</span>

                            <span>Games pending:</span>
                            <span style={{ textAlign: 'right' }}>{stats.gamesPending}</span>

                            <span>Average games:</span>
                            <span style={{ textAlign: 'right' }}>{stats.avgGames}</span>

                            <span>Max games:</span>
                            <span style={{ textAlign: 'right' }}>{stats.maxGamesPlayed}</span>

                            <span>Min games:</span>
                            <span style={{ textAlign: 'right' }}>{stats.minGamesPlayed}</span>

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
                            <span style={{ textAlign: 'right' }}>{stats.averageGameDuration}</span>

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
                <p style={{ textAlign: "center" }}>Loading game data...</p>
            )}
        </div>
    );
}
