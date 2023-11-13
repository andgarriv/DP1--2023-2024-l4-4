import React, { useState, useEffect } from 'react';
import tokenService from "../services/token.service";

const imgnotfound = "https://cdn-icons-png.flaticon.com/512/5778/5778223.png";
const user = tokenService.getUser();

export default function AchievementPlayer() {
    const jwt = tokenService.getLocalAccessToken();
    const [player, setPlayer] = useState(null); // Inicializa player como null
    const [doAchievement, setDoAchievement] = useState([]); // Inicializa doAchievement como un arreglo vacío

    useEffect(() => {
        async function fetchData() {
            try {
                // Fetch player data
                const playerResponse = await fetch(`/api/v1/player/${user.id}`, {
                    headers: { Authorization: `Bearer ${jwt}` },
                });
                if (!playerResponse.ok) {
                    throw new Error(`HTTP error! status: ${playerResponse.status}`);
                }
                const playerData = await playerResponse.json();
                setPlayer(playerData);

                // Fetch achievements data
                const achievementsResponse = await fetch(`/api/v1/achievements`, {
                    headers: { Authorization: `Bearer ${jwt}` },
                });
                if (!achievementsResponse.ok) {
                    throw new Error(`HTTP error! status: ${achievementsResponse.status}`);
                }
                const achievementsData = await achievementsResponse.json();
                setDoAchievement(achievementsData);
            } catch (error) {
                console.error("Error fetching data:", error);
            }
        }

        fetchData();
    }, [jwt, user.id]);

    if (!player) {
        return <div>Loading player data...</div>;
    }

    return (
        <div className="home-page-container">
            <div className="hero-div">
                <h1 style={{ textAlign: 'center' }}>Achievements</h1>
                <div>
                    {doAchievement && doAchievement.length > 0 ? (
                        doAchievement.map((achievement) => (
                            <div key={achievement.id} style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', marginBottom: '20px' }}>
                                <img 
                                    src={achievement.badgeImage || imgnotfound} 
                                    alt={achievement.name} 
                                    width="50px" 
                                    style={{ marginRight: '10px' }}
                                />
                                <strong style={{ marginRight: '10px' }}>{achievement.name}</strong>
                                <span style={{ marginRight: '10px' }}>{achievement.description}</span>
                                <span>
                                    {player.playerAchievement.find(a => a.achievement.id === achievement.id)?.achieveAt ? (
                                        `${player.playerAchievement.find(a => a.achievement.id === achievement.id)?.achieveAt}`
                                    ) : (
                                        <span style={{ color: 'red' }}>Not completed</span>
                                    )}
                                </span>
                            </div>
                        ))
                    ) : (
                        <p>No achievements found.</p>
                    )}
                </div>
            </div>
        </div>
    );
}
