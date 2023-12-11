import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { Collapse, Nav, NavItem, NavLink, Navbar, NavbarBrand, NavbarToggler } from 'reactstrap';
import tokenService from "./services/token.service";
import './static/css/home/home.css';

function GameNavbar() {
    const jwt = tokenService.getLocalAccessToken();

    const [collapsed, setCollapsed] = useState(true);

    const toggleNavbar = () => setCollapsed(!collapsed);
    const [round, setRound] = useState(0);
    const [gameTime, setGameTime] = useState(0);
    const [turnId, setTurnId] = useState(0);
    const [ongoingGameId, setOngoingGameId] = useState(null);

    useEffect(() => {
        async function fetchGameData() {
            const jwt = tokenService.getLocalAccessToken();
            const user = tokenService.getUser();
            const playerResponse = await fetch(`/api/v1/games/players/${user.id}/notended`, {
                headers: { Authorization: `Bearer ${jwt}` },
            });
            const playerData = await playerResponse.json();

            const ongoingGame = playerData.find((game) => !game.endedAt);
            const response = await fetch(`/api/v1/games/${ongoingGame.id}`, {
                method: "GET",
                headers: {
                    Authorization: `Bearer ${jwt}`,
                    "Content-Type": "application/json",
                },
            });
            const data = await response.json();
            setRound(data.round);
            setTurnId(data.gamePlayerTurnId);
            setOngoingGameId(data.id);

            const startedAt = new Date(data.startedAt);
            const now = new Date();
            const timeDifference = now - startedAt; // in milliseconds
            setGameTime(Math.floor(timeDifference / 1000)); // convert to seconds
        }

        fetchGameData();

        const intervalId = setInterval(() => {
            setGameTime(prevGameTime => prevGameTime + 1);
        }, 1000);

        // Clear interval on component unmount
        return () => clearInterval(intervalId);
    }, [jwt]);


    // Convert gameTime to minutes:seconds format
    const minutes = Math.floor(gameTime / 60);
    const seconds = gameTime % 60;
    const formattedGameTime = `${minutes}:${seconds < 10 ? '0' : ''}${seconds}`;

    let playerLinks = <></>;
    playerLinks = (
        <>
                <NavItem>
                    <NavLink className="fuente" style={{ color: "#75FBFD" }} tag={Link} to={`/game/${ongoingGameId}`}>Game</NavLink>
                </NavItem>
            <NavItem>
                <NavLink className="fuente" style={{ color: "#75FBFD" }} tag={Link} to="/rulesInGame">Rules</NavLink>
            </NavItem>
            <span style={{ color: "gray", display: "inline-block", margin: "5px 10px" }}>|</span>
            <NavItem className="d-flex">
                <NavLink className="fuente" style={{ color: "#EF87E0" }} id="logout" tag={Link} to="">Exit</NavLink>
            </NavItem>
        </>
    )

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
                    justifyContent: 'center',
                    alignItems: 'center',
                    height: '100%',
                    width: '100%',
                    marginRight: '5%',
                }}>
                    <span>ROUND {round}</span>
                    <span style={{ marginLeft: '10%' }}>{formattedGameTime}</span>
                    <span style={{ marginLeft: '10%' }}>TURN {turnId ? turnId : 'NONE'}</span>
                </div>

                <Collapse isOpen={!collapsed} navbar>
                    <Nav className="ms-auto mb-2 mb-lg-0" navbar>

                        {playerLinks}
                    </Nav>
                </Collapse>
            </Navbar>
        </div>
    );
}

export default GameNavbar;