import React, { useEffect, useState } from 'react';
import { Button, Modal } from "reactstrap";
import tokenService from "../../../services/token.service.js";
import '../../../static/css/home/home.css';

function FinishPopup({ setVisible, visible, gameId }) {
    const jwt = tokenService.getLocalAccessToken();
    const [winner, setWinner] = useState('');
    const [round, setRound] = useState(null);
    const [gameTime, setGameTime] = useState(null);

    useEffect(() => {
        if (!visible) {
            return;
        }
        async function fetchGameDetails() {
            try {
                const responseGame = await fetch(`/api/v1/games/${gameId}`, {
                    method: 'GET',
                    headers: {
                        Authorization: `Bearer ${jwt}`,
                        'Content-Type': 'application/json',
                    },
                });

                if (!responseGame.ok) {
                    throw new Error('Error al cargar los datos del juego.');
                }

                const dataGame = await responseGame.json();
                setWinner(dataGame.winner.nickname);
                setRound(dataGame.round);
                const startedAt = new Date(dataGame.startedAt);
                const endedAt = new Date(dataGame.endedAt);
                const timeDifference = endedAt - startedAt; 
                setGameTime(Math.floor(timeDifference / 1000));


            } catch (error) {
                console.error('Error al cargar los datos del juego.', error);
            }
        }

        fetchGameDetails();
    }, [gameId, jwt, visible]);

    const minutes = Math.floor(gameTime / 60);
    const seconds = gameTime % 60;
    const formattedGameTime = `${minutes}:${seconds < 10 ? '0' : ''}${seconds}`;


    return (
        <Modal className="exit-popup" isOpen={visible} toggle={() => setVisible(false)} centered>
            <div className="modal-content-win">
                <h1>{winner} Wins!</h1>
                <br />
                <p style={{ color: "white" }}>Time: {formattedGameTime}</p>
                <p style={{ color: "white" }}>Rounds: {round}</p>
                <div className="modal-footer-custom">
                    <Button
                        className="win-button"
                        size="lg"
                        onClick={() => window.location.href = '/'}>Go Home</Button>
                </div>
            </div>
        </Modal>
    );
}

export default FinishPopup;
