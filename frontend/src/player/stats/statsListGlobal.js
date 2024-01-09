import React, { useEffect, useState } from "react";
import tokenService from "../../services/token.service";

export default function GlobalStats() {
    const [stats, setStats] = useState({
        gamesPlayed: 0,
        totalTimePlayed: "",
        averageGameDuration: "",
        totalPlayers: 0,
        longestGameDuration: "",
        shortestGameDuration: "",
        gamesFinished: 0,
        gamesPending: 0,
    });
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        async function fetchData() {
            try {
                const jwt = tokenService.getLocalAccessToken();
                const response = await fetch(`/api/v1/games/all`, {
                    headers: { Authorization: `Bearer ${jwt}` },
                });
                if (!response.ok) {
                    throw new Error(`HTTP error! status: ${response.status}`);
                }
                const data = await response.json();

                const gamesPlayed = data.length;
                const gamesFinished = data.filter(game => game.winner).length;
                const gamesPending = gamesPlayed - gamesFinished;

                let totalTimePlayedMillis = 0;
                let longestGameMillis = 0;
                let shortestGameMillis = Number.MAX_SAFE_INTEGER;

                const playerSet = new Set();

                data.forEach((game) => {
                    if (game.startedAt && game.endedAt) {
                        const gameDuration = new Date(game.endedAt).getTime() - new Date(game.startedAt).getTime();
                        totalTimePlayedMillis += gameDuration;
                        longestGameMillis = Math.max(longestGameMillis, gameDuration);
                        shortestGameMillis = Math.min(shortestGameMillis, gameDuration);
                    }
                    game.gamePlayers.forEach((gamePlayer) => {
                        if (gamePlayer.player && gamePlayer.player.id) {
                            playerSet.add(gamePlayer.player.id);
                        }
                    });
                });

                const totalPlayers = playerSet.size;
                const averageGameDurationMillis = gamesPlayed > 0 ? totalTimePlayedMillis / gamesPlayed : 0;

                const totalTimePlayed = convertMillisToTime(totalTimePlayedMillis);
                const averageGameDuration = convertMillisToTime(averageGameDurationMillis);
                const longestGameDuration = convertMillisToTime(longestGameMillis);
                const shortestGameDuration = shortestGameMillis !== Number.MAX_SAFE_INTEGER ? convertMillisToTime(shortestGameMillis) : "N/A";

                setStats({
                    gamesPlayed,
                    totalTimePlayed,
                    averageGameDuration,
                    totalPlayers,
                    longestGameDuration,
                    shortestGameDuration,
                    gamesFinished,
                    gamesPending,
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
        if (milliseconds === 0) {
            return "0h 0m 0s";
        }
        let seconds = Math.floor(milliseconds / 1000);
        let minutes = Math.floor(seconds / 60);
        let hours = Math.floor(minutes / 60);

        seconds %= 60;
        minutes %= 60;

        return `${hours}h ${minutes}m ${seconds}s`;
    }

    return (
        <div>
            <h1 className="text-center" style={{ marginBottom: "3%" }}>Global Stats</h1>
            {!loading ? (
                stats.gamesPlayed === 0 ? (
                    <p style={{ textAlign: "center" }}>No games played yet</p>
                ) : (
                    <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', textAlign: 'left', gap: '10px' }}>
                        <span>Total players:</span>
                        <span style={{ textAlign: 'right' }}>{stats.totalPlayers}</span>

                        <span>Games played:</span>
                        <span style={{ textAlign: 'right' }}>{stats.gamesPlayed}</span>

                        <span>Games finished:</span>
                        <span style={{ textAlign: 'right' }}>{stats.gamesFinished}</span>

                        <span>Games pending:</span>
                        <span style={{ textAlign: 'right' }}>{stats.gamesPending}</span>

                        <span>Average duration:</span>
                        <span style={{ textAlign: 'right' }}>{stats.averageGameDuration}</span>

                        <span>Longest duration:</span>
                        <span style={{ textAlign: 'right' }}>{stats.longestGameDuration}</span>

                        <span>Shortest duration:</span>
                        <span style={{ textAlign: 'right' }}>{stats.shortestGameDuration}</span>

                        <span>Time played:</span>
                        <span style={{ textAlign: 'right' }}>{stats.totalTimePlayed}</span>
                    </div>
                )
            ) : (
                <p style={{ textAlign: "center" }}>Loading game data...</p>
            )}
        </div>
    );
}
