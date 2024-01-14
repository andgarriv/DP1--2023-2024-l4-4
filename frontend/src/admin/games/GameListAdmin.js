import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { Button } from "reactstrap";
import tokenService from "../../services/token.service";
import getErrorModal from "../../util/getErrorModal";
import useFetchState from "../../util/useFetchState";

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
    const [gamesPerPage] = useState(5);
    const NotWinnerImage = "https://cdn-icons-png.flaticon.com/128/5978/5978100.png";

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
                setGames(data);
            } catch (error) {
                setMessage("Error fetching games data");
                setVisible(true);
            }
        };
        fetchData();
    }, [jwt, setGames]);

    const sortedGames = games ? [...games].sort((a, b) => (!b.winner ? 1 : 0) - (!a.winner ? 1 : 0)) : [];

    const indexOfLastGame = currentPage * gamesPerPage;
    const indexOfFirstGame = indexOfLastGame - gamesPerPage;
    const currentGames = sortedGames.slice(indexOfFirstGame, indexOfLastGame);

    const paginate = pageNumber => setCurrentPage(pageNumber);

    const modal = getErrorModal(setVisible, visible, message);

    return (
        <div className="home-page-container">
            <div className="hero-div">
                <h1>Games</h1>
                <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center', width: '100%', marginBottom: '20px' }}>
                    <div style={{ display: 'flex', width: '100%', padding: '10px', justifyContent: 'space-between', color: "#EF87E0" }}>
                        <span style={{ flex: 2.5, textAlign: 'center', margin: '0 10px' }}>{currentGames.length > 0 ? "Game" : ""}</span>
                        <span style={{ flex: 6.5, textAlign: 'center', margin: '0 10px' }}>{currentGames.length > 0 ? "Winner" : ""}</span>
                        <span style={{ flex: 5, textAlign: 'center', margin: '0 10px' }}>{currentGames.length > 0 ? "Players" : ""}</span>
                        <span style={{ flex: 3, textAlign: 'center', margin: '0 10px' }}></span>
                    </div>
                    {currentGames.length > 0 ? (
                        currentGames
                            .map((game) => (
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
                                    <div style={{ flex: 3, textAlign: 'center', margin: '0 10px' }}>
                                        {!game.winner ? (
                                            <Button
                                                size="sm"
                                                tag={Link}
                                                to={`/games/${game.id}`}
                                                className="positive-button">
                                                Watch
                                            </Button>
                                        ) : (
                                            <Button
                                                size="sm"
                                                disabled={true}
                                                className="disabled-button">
                                                Watch
                                            </Button>
                                        )}
                                    </div>
                                </div>
                            ))
                    ) : (
                        <div style={{ textAlign: 'center', width: '100%' }}>There are no games yet</div>
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