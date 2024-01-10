import React, { useEffect, useState } from "react";
import { Button } from "reactstrap";
import tokenService from "../../services/token.service";
import deleteFromList from "../../util/deleteFromList";
import getErrorModal from "../../util/getErrorModal";
import useFetchState from "../../util/useFetchState";

const Pagination = ({ playersPerPage, totalPlayers, paginate, currentPage }) => {
    const pageNumbers = [];

    for (let i = 1; i <= Math.ceil(totalPlayers / playersPerPage); i++) {
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

export default function PlayerList() {
    const jwt = tokenService.getLocalAccessToken();
    const [players, setPlayers] = useFetchState(null, "/api/v1/players/all", jwt);
    const [currentPage, setCurrentPage] = useState(1);
    const [playersPerPage] = useState(5);

    const [message, setMessage] = useState("");
    const [visible, setVisible] = useState(false);
    const [alerts, setAlerts] = useState([]);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await fetch("/api/v1/players/all", {
                    headers: {
                        Authorization: `Bearer ${jwt}`,
                    },
                });
                const data = await response.json();
                setPlayers(data);
            } catch (error) {
                setMessage("Error fetching players data");
                setVisible(true);
            }
        };
        fetchData();
    }, [jwt, setPlayers]);

    const indexOfLastPlayer = currentPage * playersPerPage;
    const indexOfFirstPlayer = indexOfLastPlayer - playersPerPage;
    const currentPlayers = players ? players.slice(indexOfFirstPlayer, indexOfLastPlayer) : [];

    const paginate = pageNumber => setCurrentPage(pageNumber);

    const modal = getErrorModal(setVisible, visible, message);

    return (
        <div className="home-page-container">
            <div className="hero-div">
            <h1>Registered Players</h1>
                <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center', width: '100%', marginBottom: '20px' }}>
                    <div style={{ display: 'flex', width: '100%', padding: '10px', color: "#EF87E0" }}>
                        <span style={{ flex: 2, textAlign: 'center', paddingLeft: '10px' }}>{currentPlayers.length > 0 ? "Nickname" : ""}</span>
                        <span style={{ flex: 3, textAlign: 'center', paddingLeft: '10px' }}>{currentPlayers.length > 0 ? "Email" : ""}</span>
                        <span style={{ flex: 1.5, textAlign: 'center' }}>{currentPlayers.length > 0 ? "Avatar" : ""}</span>
                        <span style={{ flex: 1, textAlign: 'center' }}></span>
                    </div>
                    {currentPlayers.length > 0 ? (
                        currentPlayers.map((player) => (
                            <div key={player.id} style={{ display: 'flex', width: '100%', padding: '10px', borderBottom: '1px solid #ddd' }}>
                                <span style={{ flex: 2, textAlign: 'center', paddingLeft: '10px' }}>{player.nickname}</span>
                                <span style={{ flex: 3, textAlign: 'center', paddingLeft: '10px' }}>{player.email}</span>
                                <span style={{ flex: 1.5, textAlign: 'center' }}>
                                    <img src={player.avatar} alt="avatar" style={{ borderRadius: "50%", width: "40px", height: "40px" }} />
                                </span>
                                <span style={{ flex: 1, textAlign: 'center' }}>
                                    <Button
                                        aria-label={"delete-" + player.id}
                                        size="sm"
                                        className="negative-button"
                                        onClick={() => deleteFromList(
                                            `/api/v1/players/${player.id}`,
                                            player.id,
                                            [players, setPlayers],
                                            [alerts, setAlerts],
                                            setMessage,
                                            setVisible
                                        )}
                                    >
                                        Delete
                                    </Button>
                                </span>
                            </div>
                        ))
                    ) : (
                        <div style={{ textAlign: 'center', width: '100%' }}>There are not registered players yet</div>
                    )}
                </div>
                <Pagination
                    playersPerPage={playersPerPage}
                    totalPlayers={players ? players.length : 0}
                    paginate={paginate}
                    currentPage={currentPage}
                />
                {modal}
            </div>
        </div>
    );
};