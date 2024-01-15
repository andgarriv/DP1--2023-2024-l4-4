import React from 'react';
import { Button, Modal } from 'reactstrap';
import tokenService from './services/token.service';
import './static/css/home/home.css';

function SurrenderPopup({ setVisible, visible, gameId }) {
    const jwt = tokenService.getLocalAccessToken();
    const user = tokenService.getUser();

    const handleReject = async () => {
        try {
            const gamePlayerResponse = await fetch(`/api/v1/players/${user.id}/games/${gameId}/gameplayer`, {
                method: 'GET',
                headers: {
                    'Authorization': `Bearer ${jwt}`,
                    'Content-Type': 'application/json',
                }
            });
            if (!gamePlayerResponse.ok) {
                throw new Error(`Error fetching GamePlayer: ${gamePlayerResponse.statusText}`);
            }
            const gamePlayer = await gamePlayerResponse.json();
            const response = await fetch(`/api/v1/games/${gameId}/gameplayers/${gamePlayer.id}`, {
                method: 'DELETE',
                headers: {
                    Authorization: `Bearer ${jwt}`,
                    'Content-Type': 'application/json',
                },
            });

            if (!response.ok) {
                throw new Error('Failed to delete the game');
            }
            setVisible(false);
        } catch (error) {
            console.error('Error deleting game:', error);
        }

    };

    return (
        <Modal className="exit-popup" isOpen={visible} toggle={() => setVisible(false)} centered>
            <div className="modal-content-custom">
                <p>YOU ARE ABOUT TO SURRENDER.</p>
                <p>ARE YOU ABSOLUTELY SURE?</p>
                <div className="modal-footer-custom">
                    <Button
                        className="negative-button"
                        size="lg"
                        onClick={handleReject}>YES</Button>
                    <span style={{ display: "block", margin: "5px 20px" }}></span>
                    <Button
                        className="positive-button"
                        size="lg"
                        onClick={() => setVisible(false)}>NO</Button>
                </div>
            </div>
        </Modal>
    );
}

export default SurrenderPopup;
