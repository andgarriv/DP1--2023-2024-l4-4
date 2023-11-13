import React, { useEffect, useState } from "react";
import tokenService from "../../services/token.service";
import useFetchState from "../../util/useFetchState";
import getErrorModal from "../../util/getErrorModal";
import { Button } from "reactstrap";
import deleteFromList from "../../util/deleteFromList";

export default function PlayerList() {
    const jwt = tokenService.getLocalAccessToken();
    const [players, setPlayers] = useFetchState(null, "/api/v1/player/all", jwt);
    const [message, setMessage] = useState("");
    const [visible, setVisible] = useState(false);
    const [alerts, setAlerts] = useState([]);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await fetch("/api/v1/player/all", {
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

    const modal = getErrorModal(setVisible, visible, message);

    return (
        <div className="home-page-container">
            <div className="hero-div">
                <h1 style={{ color: "#EF87E0" }}>Registered Players</h1>
                <div style={{
                    display: 'flex',
                    justifyContent: 'space-between',
                }}>
                    <div style={{ flex: 1, textAlign: 'center', minWidth: '200px', margin: '0 10px' }}>Nickname</div>
                    <div style={{ flex: 1, textAlign: 'center', minWidth: '200px', margin: '0 10px' }}>Email</div>
                    <div style={{ flex: 1, textAlign: 'center', minWidth: '200px', margin: '0 10px' }}>Avatar</div>
                </div>
                {players ? (
                    <div>
                        {players.map((player) => (
                            <div key={player.id} style={{
                                display: 'flex',
                                justifyContent: 'space-between'
                            }}>
                                <div style={{ flex: 1, textAlign: 'left', minWidth: '150px', margin: '0 10px' }}>{player.nickname}</div>
                                <div style={{ flex: 1, textAlign: 'left', minWidth: '150px', margin: '0 10px' }}>{player.email}</div>
                                <div style={{ flex: 1, textAlign: 'center', minWidth: '150px', margin: '0 10px' }}>
                                    <img
                                        src={player.avatar}
                                        alt="avatar"
                                        style={{ borderRadius: "40%", width: "40px", height: "40px" }} />
                                </div>
                                <Button
                                    aria-label={"delete-" + player.id}
                                    size="sm"
                                    color="danger"
                                    onClick={() =>
                                        deleteFromList(
                                            `/api/v1/player/${player.id}`,
                                            player.id,
                                            [players, setPlayers],
                                            [alerts, setAlerts],
                                            setMessage,
                                            setVisible
                                        )
                                    }
                                >
                                    Delete
                                </Button>
                            </div>
                        ))}
                    </div>
                ) : (
                    <div>
                        <p style={{ textAlign: 'center', color: "#75FBFD" }}>Loading...</p>
                    </div>
                )}
                {modal}
            </div>
        </div>
    );

};