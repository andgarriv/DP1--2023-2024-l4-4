import React, { useEffect, useState } from "react";
import { Button } from "reactstrap";
import tokenService from "../services/token.service.js";
import { changeEffect, gameLogic, getButtonColorStyles, getColorStyles, getRotationStyle, isPlayerAuthorized, playCard, updateTurn } from "./services/boardService.js";
import "./styles/Board.css";

function Box({ content, onClick, isHighlighted, playerColor }) {
  const getRotationClass = (orientation) => {
    switch (orientation) {
      case 'N':
        return 'rotate-north';
      case 'S':
        return 'rotate-south';
      case 'E':
        return 'rotate-east';
      case 'W':
        return 'rotate-west';
      default:
        return '';
    }
  };

  const highlightStyle = isHighlighted ? getColorStyles(playerColor) : {};

  return (
    <button
      className={`box`}
      onClick={onClick}
      style={highlightStyle}
    >
      {content ? <img src={content.image} alt="Card" className={`card-image ${getRotationClass(content.orientation)}`} /> : null}
    </button>
  );
}

export default function Board() {
  const jwt = tokenService.getLocalAccessToken();
  const user = tokenService.getUser();
  const [isAuthorized, setIsAuthorized] = useState(false);
  const [board, setBoard] = useState(
    Array(7)
      .fill(null)
      .map(() => Array(7).fill(null))
  );
  const gameId =
    window.location.pathname.split("/")[
    window.location.pathname.split("/").length - 1
    ];
  const [dataGamePlayer, setDataGamePlayer] = useState([]);
  const [handCardsPlayer1, setHandCardsPlayer1] = useState([]);
  const [energyCards, setEnergyCards] = useState([]);
  const [handCardsPlayer2, setHandCardsPlayer2] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [player1CardPossiblePositions, setPlayer1CardPossiblePositions] = useState([]);
  const [player2CardPossiblePositions, setPlayer2CardPossiblePositions] = useState([]);
  const [selectedCard, setSelectedCard] = useState(null);
  const [dataGame, setDataGame] = useState([]);
  const [isMyTurn, setIsMyTurn] = useState(false);
  const [playerColor, setPlayerColor] = useState(null);
  const [gamePlayerId, setGamePlayerId] = useState(null);

  useEffect(() => {
    // Calcular playerColor una vez que dataGamePlayer esté disponible
    if (dataGamePlayer.length > 0) {
      const calculatedPlayerColor =
        (dataGamePlayer[0].player.id === user.id && dataGamePlayer[0].color) ||
        (dataGamePlayer[1].player.id === user.id && dataGamePlayer[1].color);
      setPlayerColor(calculatedPlayerColor);
      if (dataGamePlayer[0].player.id === user.id) {
        setGamePlayerId(dataGamePlayer[0].id);
      }
      if (dataGamePlayer[1].player.id === user.id) {
        setGamePlayerId(dataGamePlayer[1].id);
      }
    }

  }, [dataGamePlayer, setGamePlayerId, user]);

  const handleBoxClick = (colIndex, rowIndex) => {
    if (!isMyTurn) {
      console.log('No es tu turno');
      return;
    }

    if (selectedCard === null) {
      console.log('No has seleccionado una carta');
      return;
    }

    // Verifica si la posición seleccionada coincide con una de las CardPossiblePositions
    const isPlayer1 = dataGamePlayer[0].player.id === user.id;
    const isPlayer2 = dataGamePlayer[1].player.id === user.id;

    const isValidPosition =
      (isPlayer1 &&
        player1CardPossiblePositions.some(
          (pos) => pos.row === rowIndex && pos.col === colIndex
        )) ||
      (isPlayer2 &&
        player2CardPossiblePositions.some(
          (pos) => pos.row === rowIndex && pos.col === colIndex
        ));

    if (isValidPosition) {
      let cardOrientation;
      if (isPlayer1) {
        const position = player1CardPossiblePositions.find(
          (pos) => pos.row === rowIndex && pos.col === colIndex
        );
        if (position) {
          cardOrientation = position.orientation;
        }
      } else {
        const position = player2CardPossiblePositions.find(
          (pos) => pos.row === rowIndex && pos.col === colIndex
        );
        if (position) {
          cardOrientation = position.orientation;
        }
      }
      playCard(selectedCard.id, colIndex, rowIndex, cardOrientation, jwt)
      updateTurn(gameId, gamePlayerId, jwt);
      setSelectedCard(null);
    } else {
      console.log('No es una posición válida');
    }
  };

  useEffect(() => {
    if (dataGamePlayer.length > 0) {
      setIsAuthorized(isPlayerAuthorized(user, dataGamePlayer));
    }
    const interval = setInterval(() => {
      gameLogic(gameId, jwt, user, setDataGamePlayer, setHandCardsPlayer1, setHandCardsPlayer2, setBoard,
        setIsLoading, setEnergyCards, setPlayer1CardPossiblePositions, setPlayer2CardPossiblePositions,
        setIsMyTurn, setDataGame);
    }, 1000); // Actualization every second
    return () => clearInterval(interval);

  }, [dataGamePlayer, gameId, jwt, user]);

  if (isLoading) {
    return <div>Loading...</div>;
  }

  if (!isAuthorized) {
    return <div>You are not authorized to see this game!</div>;
  }

  return (
    <div className="background">
      <br />
      <div style={{ display: "flex", flexDirection: "row" }}>
        <div className="player-column">
          <br />
          IN HAND
          <br />
          <br />

          {/* SHOW PLAYER 1 CARDS IF PLAYER 1 */}
          {dataGamePlayer[0].player.id === user.id &&
            handCardsPlayer1.map((card, index) => (
              <div
                className={`hand ${selectedCard === card ? 'selected-card' : ''}`}
                style={{ marginBottom: "2%" }}
                key={index}
                onClick={() => {
                  if (selectedCard === card) {
                    // Deselecciona la carta si ya está seleccionada
                    setSelectedCard(null);
                  } else {
                    // Selecciona la carta si no está seleccionada
                    setSelectedCard(card);
                    console.log('Has seleccionado una carta')
                  }
                }}
              >
                <img src={card.image} alt="Card" />
              </div>
            ))}
          {/* SHOW PLAYER 2 CARDS IF PLAYER 2 */}
          {dataGamePlayer[1].player.id === user.id &&
            handCardsPlayer2.map((card, index) => (
              <div
                className={`hand ${selectedCard === card ? 'selected-card' : ''}`}
                key={index}
                onClick={() => {
                  if (selectedCard === card) {
                    // Deselecciona la carta si ya está seleccionada
                    setSelectedCard(null);
                  } else {
                    // Selecciona la carta si no está seleccionada
                    setSelectedCard(card);
                    console.log('Has seleccionado una carta', card.id)
                  }
                }}
              >
                <img src={card.image} alt="Card" />
              </div>
            ))}
        </div>
        {/* SHOW BOARD */}
        <div className="board">
          {board.map((row, i) => (
            <div key={i} className="row2">
              {row.map((boxContent, j) => (
                <Box
                  key={j}
                  content={boxContent}
                  onClick={() => handleBoxClick(i, j)}
                  isHighlighted={
                    (isMyTurn && player1CardPossiblePositions.some(pos => pos.row === j && pos.col === i) && dataGamePlayer[0].player.id === user.id)
                    ||
                    (isMyTurn && player2CardPossiblePositions.some(pos => pos.row === j && pos.col === i) && dataGamePlayer[1].player.id === user.id)
                  }
                  playerColor={
                    (dataGamePlayer[0].player.id === user.id && dataGamePlayer[0].color)
                    ||
                    (dataGamePlayer[1].player.id === user.id && dataGamePlayer[1].color)
                  }
                />
              ))}
            </div>
          ))}
        </div>
        <div className="player-column">
          <div style={{ background: "#161616", height: "50%", width: "80%", borderRadius: "5%", padding: "2%" }}>CHAT</div>
          <br />
          <Button
            outline style={{ textDecoration: "none", ...getButtonColorStyles(playerColor), width: "30%" }}
            onClick={() => {
              changeEffect(jwt, gameId, 'EXTRA_GAS');
            }}>
            EXTRA GAS
          </Button>
          <br />
          <Button
            outline style={{ textDecoration: "none", ...getButtonColorStyles(playerColor), width: "30%" }}
            onClick={() => {
              changeEffect(jwt, gameId, 'REVERSE');
            }}>
            REVERSE
          </Button>
          <br />
          <Button
            outline style={{ textDecoration: "none", ...getButtonColorStyles(playerColor), width: "30%" }}
            onClick={() => {
              changeEffect(jwt, gameId, 'BRAKE');
            }}>
            BRAKE
          </Button>
          <br />
          <Button
            outline style={{ textDecoration: "none", ...getButtonColorStyles(playerColor), width: "30%" }}
            onClick={() => {
              changeEffect(jwt, gameId, 'SPEED_UP');
            }}>
            SPEED UP
          </Button>
          <br />
          <br />
          {dataGamePlayer[0].player.id === user.id &&
            <div className="hand">
              <img
                src={energyCards[0].image}
                alt="EnergyCard0"
                style={{
                  ...getRotationStyle(dataGamePlayer.length > 0 ? dataGamePlayer[0].energy : 0),
                  marginTop: '40px'
                }}
              />
            </div>
          }
          {dataGamePlayer[1].player.id === user.id &&
            <div className="hand">
              <img
                src={energyCards[1].image}
                alt="EnergyCard0"
                style={{
                  ...getRotationStyle(dataGamePlayer.length > 0 ? dataGamePlayer[1].energy : 0),
                  marginTop: '40px'
                }}
              />
            </div>
          }
        </div>
      </div>
    </div>
  );
}