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
        maxGames: 0,
        minGames: 0,
        avgGames: 0,
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
                const gameCountByPlayer = {};
                const colorFrequency = {
                    BLUE: 0, GREEN: 0, RED: 0, YELLOW: 0, VIOLET: 0, ORANGE: 0, WHITE: 0, MAGENTA: 0
                };

                let totalRounds = 0;
                let maxRound = 0;
                let minRound = Number.MAX_SAFE_INTEGER;

                let totalTimePlayedMillis = 0;
                let longestGameMillis = 0;
                let shortestGameMillis = Number.MAX_SAFE_INTEGER;

                let mostCommonColor = 'N/A';
                let leastCommonColor = 'N/A';
                let maxColorCount = 0;
                let minColorCount = Number.MAX_SAFE_INTEGER;

                let totalEnergyUsed = 0;

                data.forEach((game) => {
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
                        const energyUsed = 3 - gamePlayer.energy;
                        totalEnergyUsed += energyUsed;
                        const color = gamePlayer.color;
                        if (colorFrequency.hasOwnProperty(color)) {
                            colorFrequency[color]++;
                        }
                        if (gamePlayer.player && gamePlayer.player.id) {
                            if (!gameCountByPlayer[gamePlayer.player.id]) {
                                gameCountByPlayer[gamePlayer.player.id] = 0;
                            }
                            gameCountByPlayer[gamePlayer.player.id]++;
                        }
                    });
                });
                const totalPlayers = Object.keys(gameCountByPlayer).length;
                const gameCounts = Object.values(gameCountByPlayer);
                const avgGames = totalPlayers > 0 ? Math.round(gamesPlayed / totalPlayers) : 0;
                const minGames = gameCounts.length > 0 ? Math.min(...gameCounts) : 0;
                const maxGames = gameCounts.length > 0 ? Math.max(...gameCounts) : 0;

                const avgRound = gamesPlayed > 0 ? Math.round(totalRounds / gamesPlayed) : 0;

                const avgEnergyUsed = totalPlayers > 0 ? Math.round(totalEnergyUsed / (gamesPlayed * 2)) : 0;

                const averageGameDurationMillis = gamesPlayed > 0 ? totalTimePlayedMillis / gamesPlayed : 0;

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
                    minGames,
                    maxGames,
                    avgGames,
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
                        <span>Players who played:</span>
                        <span style={{ textAlign: 'right' }}>{stats.totalPlayers}</span>

                        <span>Games played:</span>
                        <span style={{ textAlign: 'right' }}>{stats.gamesPlayed}</span>

                        <span>Games finished:</span>
                        <span style={{ textAlign: 'right' }}>{stats.gamesFinished}</span>

                        <span>Games pending:</span>
                        <span style={{ textAlign: 'right' }}>{stats.gamesPending}</span>

                        <span>Average games:</span>
                        <span style={{ textAlign: 'right' }}>{stats.avgGames}</span>

                        <span>Max games:</span>
                        <span style={{ textAlign: 'right' }}>{stats.maxGames}</span>

                        <span>Min games:</span>
                        <span style={{ textAlign: 'right' }}>{stats.minGames}</span>

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
                <p style={{ textAlign: "center" }}>Loading game data...</p>
            )}
        </div>
    );
}
