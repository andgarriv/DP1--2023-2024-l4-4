import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { Button, Form, Input, Label } from "reactstrap";
import tokenService from "../../services/token.service";
import getErrorModal from "../../util/getErrorModal";

export default function FriendshipEdit() {
    const jwt = tokenService.getLocalAccessToken();
    const user = tokenService.getUser();
    const [message, setMessage] = useState(null);
    const [visible, setVisible] = useState(false);
    const [nickname, setNickname] = useState("");

    const modal = getErrorModal(setVisible, visible, message);

    const handleChange = (event) => {
        setNickname(event.target.value);
    };

    let navigate = useNavigate();

    const handleSubmit = async (event) => {
        event.preventDefault();
        try {
            const idResponse = await fetch(`/api/v1/players/nickname/${nickname}`, {
                headers: {
                    Authorization: `Bearer ${jwt}`,
                    "Content-Type": "application/json",
                },
            });

            if (!idResponse.ok)
                throw new Error(`Player with nickname ${nickname} does not exist.`);

            const player = await idResponse.json();
            const friendshipsResponse = await fetch(`/api/v1/players/${user.id}/friendships/ACCEPTED`, {
                headers: {
                    Authorization: `Bearer ${jwt}`,
                    "Content-Type": "application/json",
                },
            });

            const friendshipsJson = await friendshipsResponse.json();
            if (friendshipsJson.some(friendship => friendship.sender.id === player.id || friendship.receiver.id === player.id))
                throw new Error('You are already friends with this player');

            const pendingFriendshipsResponse = await fetch(`/api/v1/players/${user.id}/friendships/PENDING`, {
                headers: {
                    Authorization: `Bearer ${jwt}`,
                    "Content-Type": "application/json",
                },
            });

            const pendingFriendshipsJson = await pendingFriendshipsResponse.json();
            if (pendingFriendshipsJson.some(friendship => friendship.sender.id === player.id || friendship.receiver.id === player.id))
                throw new Error('You already have a pending friendship request with this player');

            const response = await fetch(`/api/v1/friendships`, {
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
            });

            if (!response.ok)
                throw new Error('Failed to send friendship request.');

            navigate("/friendships");
        } catch (error) {
            setMessage(error.message);
        } finally {
            setVisible(true);
        }
    };


    return (
        <div className="home-page-container">
            <div className="hero-div">
                <h1 className="text-center">Send Friendship</h1>
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
                    <div style={{ display: "flex", justifyContent: "space-between", marginTop: "20px" }}>
                        <Button
                            size="lg"
                            className="negative-button"
                            onClick={() => navigate("/friendships")}
                        >
                            Cancel
                        </Button>
                        <Button
                            size="lg"
                            className="positive-button"
                            type="submit"
                        >
                            Send
                        </Button>
                    </div>
                </Form>
            </div>
        </div>
    );
}
