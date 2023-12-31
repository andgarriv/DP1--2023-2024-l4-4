import React, { useEffect, useState } from 'react';
import tokenService from "../services/token.service";

const imgnotfound = "https://cdn-icons-png.flaticon.com/512/5778/5778223.png";
const user = tokenService.getUser();

export default function AchievementPlayer() {
    const jwt = tokenService.getLocalAccessToken();
    const [player, setPlayer] = useState(null); // Inicializa player como null
    const [doAchievement, setDoAchievement] = useState([]); // Inicializa doAchievement como un arreglo vacío
    const [percentageCompleted, setPercentageCompleted] = useState(0);

    useEffect(() => {
        async function fetchData() {
            try {
                // Fetch player data
                const playerResponse = await fetch(`/api/v1/players/${user.id}`, {
                    headers: { Authorization: `Bearer ${jwt}` },
                });
                if (!playerResponse.ok) {
                    throw new Error(`HTTP error! status: ${playerResponse.status}`);
                }
                const playerData = await playerResponse.json();
                console.log(playerData);
                setPlayer(playerData);

                // Fetch achievements data
                const achievementsResponse = await fetch(`/api/v1/achievements`, {
                    headers: { Authorization: `Bearer ${jwt}` },
                });
                if (!achievementsResponse.ok) {
                    throw new Error(`HTTP error! status: ${achievementsResponse.status}`);
                }
                const achievementsData = await achievementsResponse.json();
                console.log(achievementsData);
                setDoAchievement(achievementsData);
            } catch (error) {
                console.error("Error fetching data:", error);
            }
        }

        fetchData();
    }, [jwt, user.id]);

    if (!player) {
        return <div className="loading">Loading player data...</div>;
    }

    return (
        <div className="home-page-container">
            <div className="hero-div">
                <h1 style={{ textAlign: 'center' }}>Achievements {percentageCompleted}%</h1>
                
                <table>
                    <tbody>
                        {doAchievement && doAchievement.length > 0 ? (
                            doAchievement.map((achievement) => (
                                <tr key={achievement.id}>
                                    <td className="text-center">
                                        <div style={{ marginRight: "40px", marginBottom: "15px" }}>
                                            <img src={achievement.badgeImage || imgnotfound} alt={achievement.name} width="50px" />
                                        </div>
                                        
                                    </td>
                                    <td className="text-center" colSpan="2">
                                        <div style={{ marginRight: "40px", marginBottom: "15px" }}>
                                            <strong>{achievement.name}</strong>
                                            <br />
                                            <span style={{ fontSize: "13px" }}>{achievement.description}</span>
                                        </div>
                                    </td>
                                    <td className="text-center">
                                        <div style={{ marginRight: "40px", marginBottom: "15px" }}>
                                        {achievement.playerAchievements.find(pa => player.playerAchievement.find(pa2 => pa2.id === pa.id))?.achieveAt ? 
                                        (achievement.playerAchievements.find(pa => player.playerAchievement.find(pa2 => pa2.id === pa.id))?.achieveAt) :
                                        <span style={{ fontSize: "13px" }}>Not achieved yet</span>}
                                        </div>
                                    </td>

                                </tr>
                            ))
                        ) : (
                            <tr>
                                <td colSpan="4">No achievements found.</td>
                            </tr>
                        )}
                    </tbody>
                </table>
            </div>
        </div>
    );
}
