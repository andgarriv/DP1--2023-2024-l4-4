import React, { useEffect, useState } from "react";
import { Button } from "reactstrap";
import tokenService from "../services/token.service";
import getErrorModal from "../util/getErrorModal";
import { gamePlayerFormInputs } from "./form/gamePlayerFormInputs";

export default function NewGame() {
  const user = tokenService.getUser();
  const [selectedColor1, setSelectedColor1] = useState("");
  const [selectedColor2, setSelectedColor2] = useState("");
  const jwt = tokenService.getLocalAccessToken();
  const [players, setPlayers] = useState(null);
  const [message, setMessage] = useState("");
  const [visible, setVisible] = useState(false);
  const [p2, setP2] = useState(null);

  const modal = getErrorModal(setVisible, visible, message);

  const handleSelectColor1 = (color) => {
    if (color === selectedColor2) {
      setMessage("Please choose a different color for each player");
      setVisible(true);
    } else {
      setSelectedColor1(color);
      setVisible(false);
    }
  };

  const handleSelectColor2 = (color) => {
    if (color === selectedColor1) {
      setMessage("Please choose a different color for each player");
      setVisible(true);
    } else {
      setSelectedColor2(color);
      setVisible(false);
    }
  };

  const handlePlayerSelect = (playerId) => {
    setP2(playerId);
  };

  const handleStartGame = async () => {
    if (p2 && selectedColor1 && selectedColor2) {
      try {
        const response = await fetch(
          `/api/v1/games/new/${user.id}/${p2}/${selectedColor1}/${selectedColor2}`,
          {
            method: "POST",
            headers: {
              Authorization: `Bearer ${jwt}`,
              "Content-Type": "application/json",
            },
          }
        );
        if (!response.ok) {
          throw new Error("Network response was not ok");
        }
        window.location.href = "/game";
      } catch (error) {
        setMessage("Error starting new game");
        setVisible(true);
      }
    } else {
      setMessage("Please select all required options");
      setVisible(true);
    }
  };

  useEffect(() => {
    if (user && user.id) {
      const fetchData = async () => {
        try {
          const response = await fetch(`/api/v1/player/allExcept/${user.id}`, {
            headers: {
              Authorization: `Bearer ${jwt}`,
            },
          });
          if (!response.ok) {
            throw new Error("Network response was not ok");
          }
          const data = await response.json();
          setPlayers(data);
        } catch (error) {
          setMessage("Error fetching players data");
          setVisible(true);
        }
      };
      fetchData();
    }
  }, [jwt, user]);

  return (
    <div className="home-page-container">
      <div className="hero-div">
        <h1>NEW GAME</h1>
        {players ? (
          <div>
            {players.map((player) => (
              <div
                key={player.id}
                style={{
                  display: "flex",
                  justifyContent: "space-between",
                }}
              >
                <div
                  style={{
                    flex: 1,
                    textAlign: "left",
                    minWidth: "150px",
                    margin: "10px",
                  }}
                >
                  {player.nickname}
                </div>
                <div
                  style={{
                    flex: 1,
                    textAlign: "center",
                    minWidth: "150px",
                    margin: "10px",
                  }}
                >
                  <img
                    src={player.avatar}
                    alt="avatar"
                    style={{
                      borderRadius: "40%",
                      width: "40px",
                      height: "40px",
                    }}
                  />
                </div>
                <Button
                  size="xs"
                  style={{
                    marginTop: "10px",
                    backgroundColor: "green",
                    borderRadius: "40%",
                    width: "40px",
                    height: "40px",
                    display: "flex",
                    alignItems: "center",
                    justifyContent: "center",
                  }}
                  onClick={() => handlePlayerSelect(player.id)}
                >
                  +
                </Button>
              </div>
            ))}
            <div className="colors">
              {gamePlayerFormInputs.map((choice) => (
                <div
                  key={choice.color}
                  className={`color-image-container ${
                    selectedColor1 === choice.color ? "selected-container" : ""
                  }`}
                >
                  <img
                    src={choice.image}
                    alt={choice.label}
                    className={`color-image ${
                      selectedColor1 === choice.color ? "selected" : ""
                    }`}
                    onClick={() =>
                      handleSelectColor1(choice.color, choice.label)
                    }
                    style={{ width: "100%", height: "auto" }}
                  />
                </div>
              ))}
            </div>
            <div className="colors">
              {gamePlayerFormInputs.map((choice) => (
                <div
                  key={choice.color}
                  className={`color-image-container ${
                    selectedColor2 === choice.color ? "selected-container" : ""
                  }`}
                >
                  <img
                    src={choice.image}
                    alt={choice.label}
                    className={`color-image ${
                      selectedColor2 === choice.color ? "selected" : ""
                    }`}
                    onClick={() =>
                      handleSelectColor2(choice.color, choice.label)
                    }
                    style={{ width: "100%", height: "auto" }}
                  />
                </div>
              ))}
            </div>
            {modal}
            <div className="button-container">
              <button className="fuente button-style" onClick={handleStartGame}>
                Continue
              </button>
            </div>
          </div>
        ) : (
          <div>
            <p style={{ textAlign: "center", color: "#75FBFD" }}>Loading...</p>
          </div>
        )}
        {modal}
      </div>
    </div>
  );
}
