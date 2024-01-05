import React, { useState } from "react";
import { Button, Form, Input, Label } from "reactstrap";
import tokenService from "../../services/token.service";
import getErrorModal from "../../util/getErrorModal";
import useFetchState from "../../util/useFetchState";



export default function FriendshipEdit() {
    const jwt = tokenService.getLocalAccessToken();
const user = tokenService.getUser();
    const [message, setMessage] = useState(null);
    const [visible, setVisible] = useState(false);
    const [nickname, setNickname] = useState(""); // Usar un estado separado para el nickname
    const [friendship, setFriendship] = useFetchState(null);

    const modal = getErrorModal(setVisible, visible, message);

    const handleChange = (event) => {
        setNickname(event.target.value); // Actualizar el estado del nickname
    };

    const handleSubmit = async (event) => {
        event.preventDefault(); // Prevenir el comportamiento por defecto del formulario
        try {/* 
            // Verificar si el nickname existe
            const existsResponse = await fetch(`/api/v1/players/exists/${nickname}`, {
                headers: {
                    Authorization: `Bearer ${jwt}`,
                },
            });
            const exists = await existsResponse.json();
            if (!exists) {
                setMessage("Nickname does not exist.");
                setVisible(true);
                return;
            } */

            // Obtener el ID del player por nickname
            const idResponse = await fetch(`/api/v1/players/nickname/${nickname}`, {
                headers: {
                    Authorization: `Bearer ${jwt}`,
                },
            });
            const player = await idResponse.json();
            if (!player) {
                setMessage("Player not found.");
                setVisible(true);
                return;
            }

            // Enviar la solicitud de amistad
            const response = await fetch(
                `/api/v1/friendships`,
                {
                    method: "POST",
                    headers: {
                        Authorization: `Bearer ${jwt}`,
                        "Content-Type": "application/json",
                    },
                    body: JSON.stringify({
                        sender: user.id,
                        receiver: player.id, 
                        friendship_state: "PENDING",
                    })
                }
            );

            if (response.ok) {
                setMessage("Friendship request sent successfully.");
            } else {
                throw new Error('Failed to send friendship request.');
            }
        } catch (error) {
            setMessage(`Error: ${error.message}`);
        } finally {
            setVisible(true);
        }
    };

    return (
        <div className="home-page-container">
            <div className="hero-div">
                <h1 className="text-center">Create Friendship</h1>
                {modal}
                <Form onSubmit={handleSubmit}>
                    <div className="custom-form-input">
                        <Label for="nickname">Friend's Nickname</Label>
                        <Input
                            type="text"
                            required
                            name="nickname"
                            id="nickname"
                            value={nickname}
                            onChange={handleChange}
                        />
                    </div>
                    <Button outline color="success" type="submit">
                        Save
                    </Button>
                </Form>
            </div>
        </div>
    );
}
