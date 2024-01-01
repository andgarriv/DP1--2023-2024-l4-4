import React, { useEffect, useState } from 'react';
import tokenService from "../services/token.service";

const user = tokenService.getUser();

export default function AchievementPlayer() {
    const jwt = tokenService.getLocalAccessToken();
    const [player, setPlayer] = useState(null);
    const [doAchievement, setDoAchievement] = useState([]);
    const [percentageCompleted, setPercentageCompleted] = useState(0);

    const formatDate = (date) => {
  const options = { year: 'numeric', month: '2-digit', day: '2-digit' };
  return new Date(date).toLocaleDateString(undefined, options);
};


useEffect(() => {
    async function fetchData() {
        try {
            const playerResponse = await fetch(`/api/v1/players/${user.id}`, {
                headers: { Authorization: `Bearer ${jwt}` },
            });
            if (!playerResponse.ok) {
                throw new Error(`HTTP error! status: ${playerResponse.status}`);
            }
            const playerData = await playerResponse.json();
            setPlayer(playerData);

            const achievementsResponse = await fetch(`/api/v1/achievements`, {
                headers: { Authorization: `Bearer ${jwt}` },
            });
            if (!achievementsResponse.ok) {
                throw new Error(`HTTP error! status: ${achievementsResponse.status}`);
            }
            const achievementsData = await achievementsResponse.json();
            const achievementsCount = achievementsData.length;
            const playerAchievementsCount = playerData.playerAchievement.length;
            const percentage = (playerAchievementsCount / achievementsCount) * 100;
            setPercentageCompleted(Math.round(percentage));
            setDoAchievement(achievementsData);
        } catch (error) {
            console.error("Error fetching data:", error);
        }
    }

    fetchData();
}, [jwt]);
    
    

    if (!player || doAchievement.length === 0) {
        return <div className="loading">Loading player data...</div>;
    }

    return (
        <div className="home-page-container">
            <div className="hero-div">
                <h1 style={{ textAlign: 'center' }}>Achievements {percentageCompleted}%</h1>
                <table>
                    <tbody>
                        {doAchievement && doAchievement.length > 0 ? (
                            doAchievement.map((achievement) => {
                                const playerAchievementRecord = achievement.playerAchievements.find(pa => 
                                    player.playerAchievement.find(pa2 => pa2.id === pa.id));
                                const isAchieved = playerAchievementRecord?.achieveAt;
                                const achievementImage = isAchieved ? achievement.badgeAchieved : achievement.badgeNotAchieved;
                                const formattedDate = isAchieved ? formatDate(playerAchievementRecord.achieveAt) : "Not achieved yet";

                                return (
                                    <tr key={achievement.id}>
                                        <td className="text-center">
                                            <div style={{ marginRight: "40px", marginBottom: "15px" }}>
                                                <img src={achievementImage} alt={achievement.name} width="50px" />
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
                                                <span style={{ fontSize: "13px" }}>{formattedDate}</span>
                                            </div>
                                        </td>
                                    </tr>
                                );
                            })
                        ) : (
                            <tr>
                                <td colSpan="4">No achievements found</td>
                            </tr>
                        )}
                    </tbody>
                </table>
            </div>
        </div>
    );
}
