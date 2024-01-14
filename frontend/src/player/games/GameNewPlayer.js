import React, { useEffect, useState } from "react";
import { Button } from "reactstrap";
import tokenService from "../../services/token.service";
import getErrorModal from "../../util/getErrorModal";
import { GameGamePlayerFormInputs } from "./forms/GameGamePlayerFormInputs";

export default function NewGame() {
  const user = tokenService.getUser();
  const [selectedColor1, setSelectedColor1] = useState("");
  const [selectedColor2, setSelectedColor2] = useState("");
  const jwt = tokenService.getLocalAccessToken();
  const [players, setPlayers] = useState(null);
  const [message, setMessage] = useState("");
  const [visible, setVisible] = useState(false);
  const [p2, setP2] = useState(null);
  const [p2name, setP2name] = useState(null);
  const [currentPage, setCurrentPage] = useState(1);
  const [loading, setLoading] = useState(true);
  const playersPerPage = 5;

  const modal = getErrorModal(setVisible, visible, message);

  const indexOfLastPlayer = currentPage * playersPerPage;
  const indexOfFirstPlayer = indexOfLastPlayer - playersPerPage;
  const currentPlayers = players ? players.slice(indexOfFirstPlayer, indexOfLastPlayer) : [];

  const paginate = (pageNumber) => setCurrentPage(pageNumber);

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

  const handlePlayerSelect = (playerId, name) => {
    setP2(playerId);
    setP2name(name);
  };

  const deletePlayerSelect = () => {
    setP2(null);
    setP2name(null);
  };

  const handleStartGame = async () => {
    if (p2 && selectedColor1 && selectedColor2) {
      try {
        const requestBody = {
          player1Id: user.id,
          player2Id: p2,
          player1Color: selectedColor1,
          player2Color: selectedColor2,
        };



        const response = await fetch(
          `/api/v1/games`,
          {
            method: "POST",
            headers: {
              Authorization: `Bearer ${jwt}`,
              "Content-Type": "application/json",
            },
            body: JSON.stringify(requestBody),
          }
        );
        if (!response.ok) {
          throw new Error("Network response was not ok");
        }
        window.location.href = '/games/' + (await response.json()).id;
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
    async function fetchData() {
      try {
        const response = await fetch(`/api/v1/players/${user.id}/friends`, {
          headers: {
            Authorization: `Bearer ${jwt}`,
          },
        });
        if (!response.ok) {
          throw new Error("Network response was not ok");
        }
        const data = await response.json();
        setPlayers(data);
        setLoading(false);
      } catch (error) {
        console.error('Error fetching players data:', error);
      }
    };
    fetchData();

  }, [jwt, user.id]);

  if (loading) {
    return <div className="home-page-container"> <div className="hero-div">Loading...</div> </div>;
  }

  return (
    <div className="home-page-container">
      <div className="hero-div"
        style={{ width: '80%' }}>
        <h1>NEW GAME</h1>
        <div style={{marginTop: "2%"}}>
          {players.length > 0 ? (
            <>
              <div style={{
                display: "flex",
                flexDirection: "row",
                justifyContent: "space-evenly",
              }} >
                {currentPlayers.map((player) => (
                  <div
                    key={player.id}
                    style={{
                      display: "flex",
                      flexDirection: "column",
                      alignItems: "center",
                      justifyContent: "space-between",
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
                    <div
                      style={{
                        textAlign: "center",
                        minWidth: "150px",
                        margin: "10px",
                      }}
                    >
                      {player.nickname}
                    </div>
                    <Button
                      size="xs"
                      style={{
                        backgroundColor: "#2C2C2C",
                        color: player.id === p2 ? "#e21c24" : "#4bb25b",
                        borderRadius: "40%",
                        width: "40px",
                        height: "40px",
                        display: "flex",
                        alignItems: "center",
                        justifyContent: "center",
                        border:
                          player.id === p2
                            ? "2px solid #e21c24"
                            : "2px solid #4bb25b",
                      }}
                      onClick={() =>
                        player.id === p2
                          ? deletePlayerSelect()
                          : handlePlayerSelect(player.id, player.nickname)
                      }
                    >
                      {player.id === p2 ? "x" : "+"}
                    </Button>
                  </div>
                ))}
              </div>
              <div className="pagination" style={{
                display: "flex",
                justifyContent: "flex-end",
                marginTop: "2%",
              }}>
                {[...Array(Math.ceil(players.length / playersPerPage)).keys()].map(number => (
                  <button
                    key={number}
                    onClick={() => paginate(number + 1)}
                    style={{
                      backgroundColor: number + 1 === currentPage ? '#2596be' : '#2C2C2C',
                      color: number + 1 === currentPage ? '#2C2C2C' : '#2596be',
                      borderRadius: "10%",
                      width: "40px",
                      height: "40px",
                      display: "flex",
                      alignItems: "center",
                      justifyContent: "center",
                      margin: "5px",
                      border:
                        number + 1 === currentPage ? "2px solid #2C2C2C"
                          : "2px solid #2596be",
                    }}
                  >
                    {number + 1}
                  </button>
                ))}
              </div>

              <div className="row">
                <div className="colors"> You </div>
                <div className="colors"> {p2name ? (
                  <>{p2name}</>
                ) : (
                  <>Your friend</>
                )}</div>
              </div>

              <div className="row">
                <div className="colors">
                  {GameGamePlayerFormInputs.map((choice) => (
                    <div
                      key={choice.color}
                      className={`color-image-container ${selectedColor1 === choice.color
                        ? "selected-container"
                        : ""
                        }`}
                    >
                      <img
                        src={choice.image}
                        alt={choice.label}
                        className={`color-image ${selectedColor1 === choice.color ? "selected" : ""
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
                  {GameGamePlayerFormInputs.map((choice) => (
                    <div
                      key={choice.color}
                      className={`color-image-container ${selectedColor2 === choice.color
                        ? "selected-container"
                        : ""
                        }`}
                    >
                      <img
                        src={choice.image}
                        alt={choice.label}
                        className={`color-image ${selectedColor2 === choice.color ? "selected" : ""
                          }`}
                        onClick={() =>
                          handleSelectColor2(choice.color, choice.label)
                        }
                        style={{ width: "100%", height: "auto" }}
                      />
                    </div>
                  ))}
                </div>
              </div>
              {modal}
              <div className="button-container">
                <button className="fuente button-style" onClick={handleStartGame}>
                  Continue
                </button>
              </div>
            </>
          ) : (
            <div>
              <p style={{ textAlign: "center" }}>
                You need to have at least one friend</p>
            </div>
          )}
        </div>
        {modal}
      </div>
    </div>
  );
}
