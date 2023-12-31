import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import tokenService from "../services/token.service";
import getErrorModal from "../util/getErrorModal";
import useFetchState from "../util/useFetchState";

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

export default function AdminGamesList() {
    const jwt = tokenService.getLocalAccessToken();
    const [games, setGames] = useFetchState(null, "/api/v1/games/all", jwt);
    const [currentPage, setCurrentPage] = useState(1);
    const [gamesPerPage] = useState(4);

    const [message, setMessage] = useState("");
    const [visible, setVisible] = useState(false);
    const [alerts, setAlerts] = useState([]);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await fetch("/api/v1/games/all", {
                    headers: {
                        Authorization: `Bearer ${jwt}`,
                    },
                });
                const data = await response.json();
                console.log(data);
                setGames(data);
            } catch (error) {
                setMessage("Error fetching games data");
                setVisible(true);
            }
        };
        fetchData();
    }, [jwt, setGames]);

    const indexOfLastGame = currentPage * gamesPerPage;
    const indexOfFirstGame = indexOfLastGame - gamesPerPage;
    const currentGames = games ? games.slice(indexOfFirstGame, indexOfLastGame) : [];

    const paginate = pageNumber => setCurrentPage(pageNumber);

    const modal = getErrorModal(setVisible, visible, message);

    return (
        <div className="home-page-container">
            <div className="hero-div">
            <h1 style={{ textAlign: 'center', color: "#EF87E0" }}>Games</h1>
                <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center', width: '100%', marginBottom: '20px' }}>
                    <div style={{ display: 'flex', width: '100%', padding: '10px' }}>
                        <span style={{ flex: 1, textAlign: 'center', paddingLeft: '10px' }}>Game</span>
                        <span style={{ flex: 2, textAlign: 'center', paddingLeft: '10px', paddingRight: '10px' }}>Winner</span>
                        <span style={{ flex: 2, textAlign: 'center', paddingLeft: '20px' }}>Players</span>
                        <span style={{ flex: 2, textAlign: 'center' }}></span>
                    </div>
                    {currentGames.length > 0 ? (
                        currentGames.map((game) => (
                            <div key={game.id} style={{ display: 'flex', width: '100%', padding: '10px', borderBottom: '1px solid #ddd' }}>
                                <span style={{ flex: 2, textAlign: 'center', paddingLeft: '10px' }}>{game.id}</span>
                                <span style={{ flex: 5, textAlign: 'center', paddingLeft: '15px' }}>{game.winner ? game.winner.nickname : "----"}</span>
                                <span style={{ flex: 5, textAlign: 'center', paddingLeft: '20px' }}>{game.gamePlayers[0].player.nickname + "\n" + game.gamePlayers[1].player.nickname}</span>
                                <span style={{ flex: 5, textAlign: 'center'}}> 
                                  {game.winner ? 
                                  (
                                    ""
                                  ) : (
                                    
                                    <Link
                                      className="auth-button-eol-watch"
                                      to={`/game/${game.id}`}
                                      style={{ textDecoration: "none" }}
                                    >
                                      Watch
                                    </Link>
                                  )}
                                  <br />
                                  <br />
                                  <br />
                                </span>
                            </div>
                        ))
                    ) : (
                        <div style={{ textAlign: 'center', width: '100%' }}>There are no games to show</div>
                    )}
                </div>
                <Pagination
                    gamesPerPage={gamesPerPage}
                    totalGames={games ? games.length : 0}
                    paginate={paginate}
                    currentPage={currentPage}
                />
                {modal}
            </div>
        </div>
    );
};