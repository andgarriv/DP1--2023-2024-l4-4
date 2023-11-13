import React from 'react';
import tokenService from "../services/token.service";
import useFetchState from "../util/useFetchState";
import jwt_decode from "jwt-decode";

const imgnotfound = "https://cdn-icons-png.flaticon.com/512/5778/5778223.png";
const user = tokenService.getUser();

export default function AchievementPlayer() {
    const jwt = tokenService.getLocalAccessToken();
    const [player, setPlayer] = useFetchState([], `/api/v1/player/${user.id}`, jwt);
    const [doAchievement, setDoAchievement] = useFetchState([], `/api/v1/achievements`, jwt);

    const achievementsWithStatus = doAchievement.map(achievement => {
        const achieveAt = player.playerAchievement.find(a => a.achievement.id === achievement.id)?.achieveAt || null;
        return {
            ...achievement,
            achieveAt
        };
    });

    return (
        <div className="home-page-container">
            <div className="hero-div">
                <h1 className="text-center">Achievements</h1>
                <div>
                    {achievementsWithStatus && achievementsWithStatus.length > 0 ? (
                        achievementsWithStatus.map((achievement) => (
                            <div key={achievement.id} style={{ display: 'flex', alignItems: 'center', marginBottom: '20px' }}>
                                <img 
                                    src={achievement.badgeImage || imgnotfound} 
                                    alt={achievement.name} 
                                    width="50px" 
                                    style={{ marginRight: '15px' }}
                                />
                                <div style={{ flex: 1, marginRight: '15px' }}>
                                    <strong>{achievement.name}</strong>
                                    <span style={{ marginLeft: '10px' }}>{achievement.description}</span>
                                </div>
                                <div style={{ minWidth: '150px', textAlign: 'center', marginLeft: '15px' }}>
                                    {achievement.achieveAt ? (
                                        <span>{achievement.achieveAt}</span>
                                    ) : (
                                        <span style={{ color: 'red' }}>Not completed</span>
                                    )}
                                </div>
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
