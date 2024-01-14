import React, { useEffect, useState } from 'react';
import tokenService from '../../services/token.service';

const jwt = tokenService.getLocalAccessToken();
const user = tokenService.getUser();

const Pagination = ({ gamesPerPage, totalGames, paginate, currentPage }) => {
    const pageNumbers = [];

    for (let i = 1; i <= Math.ceil(totalGames / gamesPerPage); i++) {
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

export default function PlayerGamesList() {
    const [playerGames, setPlayerGames] = useState([]);
    const [loading, setLoading] = useState(true);
    const [currentPage, setCurrentPage] = useState(1);
    const [gamesPerPage] = useState(5);
    const NotWinnerImage = "https://cdn-icons-png.flaticon.com/128/5978/5978100.png";

    useEffect(() => {
        async function fetchData() {
            try {
                const playerResponse = await fetch(`/api/v1/players/${user.id}/games`, {
                    headers: { Authorization: `Bearer ${jwt}` },
                });
                if (!playerResponse.ok) {
                    throw new Error(`HTTP error! status: ${playerResponse.status}`);
                }
                const playerData = await playerResponse.json();
                setPlayerGames(playerData);
                setLoading(false);
            } catch (error) {
                console.error('Error fetching data:', error);
                setLoading(false);
            }
        }

        fetchData();
    }, [jwt, user.id]);

    const sortedGames = playerGames ? [...playerGames].sort((a, b) => (!b.winner ? 1 : 0) - (!a.winner ? 1 : 0)) : [];

    const indexOfLastGame = currentPage * gamesPerPage;
    const indexOfFirstGame = indexOfLastGame - gamesPerPage;
    const currentGames = sortedGames.slice(indexOfFirstGame, indexOfLastGame);

    const paginate = pageNumber => setCurrentPage(pageNumber);

    if (loading) {
        return <div className="loading">Loading...</div>;
    }

    return (
        <div className="home-page-container">
            <div className="hero-div">
                <h1>My Games</h1>
                <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center', width: '100%', marginBottom: '20px' }}>
                    <div style={{ display: 'flex', width: '100%', padding: '10px', justifyContent: 'space-between', color: "#EF87E0" }}>
                        <span style={{ flex: 2.5, textAlign: 'center', margin: '0 10px' }}>{currentGames.length > 0 ? "Game" : ""}</span>
                        <span style={{ flex: 6.5, textAlign: 'center', margin: '0 10px' }}>{currentGames.length > 0 ? "Winner" : ""}</span>
                        <span style={{ flex: 5, textAlign: 'center', margin: '0 10px' }}>{currentGames.length > 0 ? "Players" : ""}</span>
                    </div>
                    {currentGames.length > 0 ? (
                        currentGames.map((game) => (
                            <div key={game.id} style={{ display: 'flex', width: '100%', padding: '10px', borderBottom: '1px solid #ddd', justifyContent: 'space-between' }}>
                                <span style={{ flex: 2.5, textAlign: 'center', margin: '0 10px' }}>{game.id}</span>
                                <span style={{ flex: 6.5, textAlign: 'center', margin: '0 10px' }}>
                                    <img
                                        src={game.winner ? game.winner.avatar : NotWinnerImage}
                                        alt=""
                                        style={{ borderRadius: "50%", width: "35px", height: "35px", marginRight: "15px" }} 
                                    />
                                    <span>{game.winner ? game.winner.nickname : "----"}</span>
                                </span>
                                <span style={{ flex: 5, textAlign: 'center', margin: '0 10px' }}>{game.gamePlayers.map(p => p.player.nickname).join(' ')}</span>
                            </div>
                        ))
                    ) : (
                        <div style={{ textAlign: 'center', width: '100%' }}>There are no games yet</div>
                    )}
                </div>
                <Pagination
                    gamesPerPage={gamesPerPage}
                    totalGames={playerGames ? playerGames.length : 0}
                    paginate={paginate}
                    currentPage={currentPage}
                />
            </div>
        </div>
    );
}
