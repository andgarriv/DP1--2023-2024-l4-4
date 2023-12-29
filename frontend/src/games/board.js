import React, { useEffect, useState } from "react";
import tokenService from "../services/token.service.js";
import { fetchGameCards, getRotationStyle, isPlayerAuthorized } from "./services/boardService.js";
import "./styles/Board.css";

function Box({ content, onClick, isHighlighted }) {
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

  const rotationClass = content ? getRotationClass(content.orientation) : '';
  const highlightClass = isHighlighted ? 'highlight' : '';


  return (
    <button className={`box ${rotationClass} ${highlightClass}`} onClick={onClick}>
      {content ? <img src={content.image} alt="Card" className={rotationClass} /> : null}
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
  const [selectedPosition, setSelectedPosition] = useState(null);


  const handleBoxClick = (rowIndex, colIndex) => {
    console.log(`Casilla clickeada: fila ${rowIndex}, columna ${colIndex}`);
  
    // Verifica si hay una carta seleccionada y una posición seleccionada
    if (selectedCard && selectedPosition) {
      // Verifica si la posición seleccionada coincide con una de las CardPossiblePositions
      const isPlayer1 = dataGamePlayer[0].player.id === user.id;
      const isPlayer2 = dataGamePlayer[1].player.id === user.id;
  
      const isValidPosition =
        (isPlayer1 &&
          player1CardPossiblePositions.some(
            (pos) => pos.row === selectedPosition.row && pos.col === selectedPosition.col
          )) ||
        (isPlayer2 &&
          player2CardPossiblePositions.some(
            (pos) => pos.row === selectedPosition.row && pos.col === selectedPosition.col
          ));
  
      if (isValidPosition) {
        // Coloca la carta en la posición seleccionada en el tablero
        const newBoard = [...board];
        newBoard[selectedPosition.row][selectedPosition.col] = selectedCard;
        setBoard(newBoard);
  
        // Limpia la carta seleccionada y la posición seleccionada
        setSelectedCard(null);
        setSelectedPosition(null);
      }
    } else {
      // Si no hay carta seleccionada, registra la posición como seleccionada
      setSelectedPosition({ row: rowIndex, col: colIndex });
    }
  };
  
  

  useEffect(() => {
    if (dataGamePlayer.length > 0) {
      setIsAuthorized(isPlayerAuthorized(user, dataGamePlayer));
    }
    const interval = setInterval(() => {
      fetchGameCards(gameId, jwt, setDataGamePlayer, setHandCardsPlayer1, setHandCardsPlayer2, setBoard, setIsLoading, setEnergyCards, setPlayer1CardPossiblePositions, setPlayer2CardPossiblePositions);
    }, 1000); // Actualization every second
    return () => clearInterval(interval);

  }, [jwt, user]);

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
          In Hand
          <br />
          {/* SHOW PLAYER 1 CARDS IF PLAYER 1 */}
          {dataGamePlayer[0].player.id === user.id &&
            handCardsPlayer1.map((card, index) => (
              <div
              className={`hand ${selectedCard === card ? 'selected' : ''}`}
              key={index}
              onClick={() => {
                if (selectedCard === card) {
                  // Deselecciona la carta si ya está seleccionada
                  setSelectedCard(null);
                } else {
                  // Selecciona la carta si no está seleccionada
                  setSelectedCard(card);
                }
              }}
            >
              <img src={card.image} alt="Card" />
            </div>
          ))}
          {dataGamePlayer[0].player.id == user.id &&
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
          {/* SHOW PLAYER 2 CARDS IF PLAYER 2 */}
          {dataGamePlayer[1].player.id == user.id &&
            handCardsPlayer2.map((card, index) => (
              <div
              className={`hand ${selectedCard === card ? 'selected' : ''}`}
              key={index}
              onClick={() => {
                if (selectedCard === card) {
                  // Deselecciona la carta si ya está seleccionada
                  setSelectedCard(null);
                } else {
                  // Selecciona la carta si no está seleccionada
                  setSelectedCard(card);
                }
              }}
            >
              <img src={card.image} alt="Card" />
            </div>
          ))}
          {dataGamePlayer[1].player.id == user.id &&
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
        {/* SHOW BOARD */}
        <div className="board">
          {board.map((row, i) => (
            <div key={i} className="row2">
              {row.map((boxContent, j) => (
                <Box
                  key={j}
                  content={boxContent}
                  onClick={() => handleBoxClick(i, j)}
                  // Caso de ser jugador 1
                  isHighlighted={(player1CardPossiblePositions.some(pos => pos.row === j && pos.col === i) && dataGamePlayer[0].player.id === user.id)
                    ||
                    (player2CardPossiblePositions.some(pos => pos.row === j && pos.col === i) && dataGamePlayer[1].player.id === user.id)}

                />
              ))}
            </div>
          ))}
        </div>
        <div className="player-column">

        </div>
      </div>
    </div>
  );
}