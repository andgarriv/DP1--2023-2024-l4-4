import jwt_decode from "jwt-decode";
import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { Collapse, Nav, NavItem, NavLink, Navbar, NavbarBrand, NavbarToggler } from 'reactstrap';
import ExitPopup from "./ExitPopup";
import SurrenderPopup from './SurrenderPopup';
import tokenService from "./services/token.service";
import './static/css/home/home.css';

function GameNavbar() {
    const jwt = tokenService.getLocalAccessToken();
    const [roles, setRoles] = useState([]);
    const [collapsed, setCollapsed] = useState(true);
    const toggleNavbar = () => setCollapsed(!collapsed);
    const [round, setRound] = useState(0);
    const [gameTime, setGameTime] = useState(0);
    const [turnColor, setTurnColor] = useState(null);
    const [gameId, setgameId] = useState(null);
    const [visibleSurrender, setVisibleSurrender] = useState(false);
    const [visibleExit, setVisibleExit] = useState(false);

    useEffect(() => {
        if (jwt) {
            setRoles(jwt_decode(jwt).authorities);
        };
        async function fetchGameData() {
            const jwt = tokenService.getLocalAccessToken();
            const user = tokenService.getUser();
            const playerResponse = await fetch(`/api/v1/players/${user.id}/games`, {
                headers: { Authorization: `Bearer ${jwt}` },
            });

            const playerData = await playerResponse.json();
            let gameId = 0;

            if (user.id < 3) {
                const uriParts = window.location.pathname.split('/');
                const lastSegment = uriParts[uriParts.length - 1];
                gameId = parseInt(lastSegment, 10);
            } else {
                const playerGame = playerData.reduce((maxGame, game) => {
                    return (maxGame && maxGame.id > game.id) ? maxGame : game;
                }, null);
                if (playerGame) {
                    gameId = playerGame.id;
                }
            }


            const response = await fetch(`/api/v1/games/${gameId}`, {
                method: "GET",
                headers: {
                    Authorization: `Bearer ${jwt}`,
                    "Content-Type": "application/json",
                },
            });

            const data = await response.json();

            setRound(data.round);
            setgameId(gameId);

            if (data.gamePlayers[0].id === data.gamePlayerTurnId) {
                setTurnColor(data.gamePlayers[0].color)
            }
            if (data.gamePlayers[1].id === data.gamePlayerTurnId) {
                setTurnColor(data.gamePlayers[1].color)
            }

            const startedAt = new Date(data.startedAt);
            if (data.endedAt) {
                const timeDifference = new Date(data.endedAt) - startedAt; // in milliseconds
                setGameTime(Math.floor(timeDifference / 1000)); // convert to seconds
            } else {
                const now = new Date();
                const timeDifference = now - startedAt; // in milliseconds
                setGameTime(Math.floor(timeDifference / 1000)); // convert to seconds
                setGameTime(prevGameTime => prevGameTime + 1);
            }

        }

        fetchGameData();

        const intervalId = setInterval(() => {
            fetchGameData();
        }, 1000);

        // Clear interval on component unmount
        return () => clearInterval(intervalId);
    }, [jwt]);

    // Convert gameTime to minutes:seconds format
    const minutes = Math.floor(gameTime / 60);
    const seconds = gameTime % 60;
    const formattedGameTime = `${minutes}:${seconds < 10 ? '0' : ''}${seconds}`;

    let playerLinks = <></>;
    let adminLinks = <></>;

    roles.forEach((role) => {
        if (role === "ADMIN") {
            adminLinks = (
                <>
                    <NavItem className="d-flex">
                        <NavLink className="fuente" style={{ color: "#EF87E0", marginLeft: "150px" }} id="logout" tag={Link} to="">Exit</NavLink>
                    </NavItem>
                </>
            )
        }
        if (role === "PLAYER") {

            playerLinks = (
                <>
                    <NavItem>
                        <NavLink className="fuente" style={{ color: "#75FBFD" }} tag={Link} to={`/games/${gameId}`}>Game</NavLink>
                    </NavItem>
                    <NavItem>
                        <NavLink className="fuente" style={{ color: "#75FBFD" }} tag={Link} to="/rulesInGame">Rules</NavLink>
                    </NavItem>
                    {round > 2 && (
                        <NavItem className="d-flex">
                            <NavLink
                                className="fuente"
                                style={{ color: "#75FBFD" }}
                                tag={Link}
                                onClick={() => setVisibleSurrender(true)}
                            >
                                Surrender
                            </NavLink>
                        </NavItem>
                    )}
                    <span style={{ color: "gray", display: "inline-block", margin: "5px 10px" }}>|</span>
                    <NavItem className="d-flex">
                        <NavLink
                            className="fuente"
                            style={{ color: "#EF87E0" }}
                            tag={Link}
                            onClick={() => setVisibleExit(true)}
                        >
                            Exit
                        </NavLink>
                    </NavItem>
                </>
            )

        }
    })


    return (
        <div>
            <Navbar expand="md" dark color="dark">
                <NavbarBrand href="/">
                    <img alt="logo" src="/eol_logo.png" style={{ height: 30, width: 250 }} />
                </NavbarBrand>
                <NavbarToggler onClick={toggleNavbar} className="ms-2" />
                <div style={{
                    color: 'white',
                    display: 'flex',
                    alignItems: 'left',
                    justifyContent: 'left',
                    height: '100%',
                    width: '100%',
                    marginLeft: '25%',
                    marginRight: '20%'

                }}>
                    <span style={{ minWidth: '120px' }}>ROUND {round}</span>
                    <span style={{ marginLeft: '10%', minWidth: '60px' }}>{formattedGameTime}</span>
                    <span style={{ marginLeft: '10%', minWidth: '200px' }}>{turnColor ? turnColor : 'NONE'}'S TURN</span>
                </div>

                <Collapse isOpen={!collapsed} navbar>
                    <Nav className="ms-auto mb-2 mb-lg-0" navbar>
                        {adminLinks}
                        {playerLinks}
                    </Nav>
                </Collapse>
            </Navbar>
            <ExitPopup
                visible={visibleExit}
                setVisible={setVisibleExit}
            />
            <SurrenderPopup
                visible={visibleSurrender}
                setVisible={setVisibleSurrender}
                gameId={gameId}
            />
        </div>

    );
}

export default GameNavbar;