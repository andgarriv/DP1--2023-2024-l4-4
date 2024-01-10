import React, { useEffect, useState } from 'react';
import tokenService from "../../services/token.service";
const user = tokenService.getUser();

const Pagination = ({ achievementsPerPage, totalAchievements, paginate, currentPage }) => {
    const pageNumbers = [];
    for (let i = 1; i <= Math.ceil(totalAchievements / achievementsPerPage); i++) {
        pageNumbers.push(i);
    }
    const getPageStyle = (pageNumber) => {
        return {
            backgroundColor: '#343F4B', 
            color: currentPage === pageNumber ? "#75FBFD" : '#EF87E0',
            border: 'none',
            padding: '5px 10px',
            margin: '0 5px',
            borderRadius: '5px',
            cursor: 'pointer'
        };
    };
    return (
        <nav>
            <ul className='pagination'>
                {pageNumbers.map(number => (
                    <li key={number} className='page-item'>
                        <a 
                            onClick={(e) => {
                                e.preventDefault();
                                paginate(number);
                            }} 
                            href="!#"
                            style={getPageStyle(number)}
                            className='page-link'
                        >
                            {number}
                        </a>
                    </li>
                ))}
            </ul>
        </nav>
    );
};

export default function AchievementPlayer() {
    const jwt = tokenService.getLocalAccessToken();
    const [player, setPlayer] = useState(null);
    const [doAchievement, setDoAchievement] = useState([]);
    const [percentageCompleted, setPercentageCompleted] = useState(0);
    const [currentPage, setCurrentPage] = useState(1);
    const [achievementsPerPage] = useState(5); 
    const paginate = pageNumber => setCurrentPage(pageNumber);
    const indexOfLastAchievement = currentPage * achievementsPerPage;
    const indexOfFirstAchievement = indexOfLastAchievement - achievementsPerPage;
    const currentAchievements = doAchievement.slice(indexOfFirstAchievement, indexOfLastAchievement);

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
                <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'center' }}>
                    <h1> Achievements {percentageCompleted}% </h1>
                    {percentageCompleted === 100 && (
                        <img 
                            src="https://cdn-icons-png.flaticon.com/128/5360/5360933.png" 
                            alt="Completed" 
                            style={{ marginLeft: '20px', width: '100px', height: '100px' }} 
                        />
                    )}
                </div>
                <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center', width: '100%', marginBottom: '20px'}}>
                    <div style={{ display: 'flex', width: '100%', padding: '10px', color: "#EF87E0" }}>
                        <span style={{ flex: 3, textAlign: 'center' }}>Name</span>
                        <span style={{ flex: 5, textAlign: 'center' }}>Description</span>
                        <span style={{ flex: 2, textAlign: 'center' }}>Badge Image</span>
                        <span style={{ flex: 2, textAlign: 'center' }}>Achieved Date</span>
                    </div>
                    {currentAchievements.length > 0 ? (
                        currentAchievements.map((achievement) => {
                            const playerAchievementRecord = achievement.playerAchievements.find(pa => 
                                player.playerAchievement.find(pa2 => pa2.id === pa.id));
                            const isAchieved = playerAchievementRecord?.achieveAt;
                            const achievementImage = isAchieved ? achievement.badgeAchieved : achievement.badgeNotAchieved;
                            const formattedDate = isAchieved ? formatDate(playerAchievementRecord.achieveAt) : "Not achieved yet";
    
                            return (
                                <div key={achievement.id} style={{ display: 'flex', width: '100%', padding: '10px', borderBottom: '1px solid #ddd' }}>
                                    <span style={{ flex: 3, textAlign: 'center' }}>{achievement.name}</span>
                                    <span style={{ flex: 5, textAlign: 'center' }}>{achievement.description}</span>
                                    <span style={{ flex: 2, textAlign: 'center' }}>
                                        <img src={achievementImage} alt={achievement.name} style={{ borderRadius: "50%", width: "40px", height:"40px"}} />
                                    </span>
                                    <span style={{ flex: 2, textAlign: 'center' }}>
                                        {formattedDate}
                                    </span>
                                </div>
                            );
                        })
                    ) : (
                        <div style={{ textAlign: 'center', width: '100%' }}>There are not achievements yet</div>
                    )}
                </div>
                <Pagination
                    achievementsPerPage={achievementsPerPage}
                    totalAchievements={doAchievement ? doAchievement.length : 0}
                    paginate={paginate}
                    currentPage={currentPage}
                />
            </div>
        </div>
    );
    
    
}
