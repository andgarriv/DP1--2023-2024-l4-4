import React, { useEffect, useState } from "react";
import tokenService from "../services/token.service.js";
import { gameLogic, getRotationStyle, isPlayerAuthorized } from "./services/boardService.js";
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

  const rotationClass = content ? getRotationClass(content.orientation) : '';

  function getColorStyles(colorName) {
    switch (colorName) {
        case 'RED':
            return {
              border: '2px solid rgb((225,28,36)',
            backgroundColor: 'rgb(255, 0, 0, 0.2)'
          };
        case 'ORANGE':
          return {
            border: 'rgb(255, 0, 0)',
          backgroundColor: 'rgb(255, 0, 0, 0.2)'
        };
         case 'YELLOW':
          return {
            border: 'rgb(255, 0, 0)',
          backgroundColor: 'rgb(255, 0, 0, 0.2)'
        };
                case 'GREEN':
                  return {
                    border: 'rgb(255, 0, 0)',
                  backgroundColor: 'rgb(255, 0, 0, 0.2)'
                };
                case 'BLUE':

                return {
                  border: '2px solid rgb(4,163,227)',
                backgroundColor: 'rgba(4,163,227, 0.2)'
              };
                case 'MAGENTA':

                return {
                  border: 'rgb(255, 0, 0)',
                backgroundColor: 'rgb(255, 0, 0, 0.2)'
              };

                case 'VIOLET':

                return {
                  border: '2px solid rgb(192, 138, 184)',
                backgroundColor: 'rgba(192, 138, 184, 0.2)'
              };
                case 'WHITE':

        return {
          border: 'rgb(255, 0, 0)',
        backgroundColor: 'rgb(255, 0, 0, 0.2)'
      };
        default:

        return {
          border: 'rgb(255, 0, 0)',
        backgroundColor: 'rgb(255, 0, 0, 0.2)'
      };
      }
}

const colorStyles = getColorStyles(playerColor);

  const highlightStyle = isHighlighted
    ? colorStyles
    : {};

return (
  <button
    className={`box ${rotationClass}`}
    onClick={onClick}
    style={highlightStyle}
  >
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
  const [dataGame, setDataGame] = useState([]);
  const [isMyTurn, setIsMyTurn] = useState(false);


  const handleBoxClick = (rowIndex, colIndex) => {
    if(!isMyTurn) {
      console.log('No es tu turno');
      return;
    }
    if(selectedCard === null) {
      console.log('No has seleccionado una carta');
      return;
    }
    
    console.log('Has seleccionado una carta y una posición');
    console.log(`Casilla seleccionada: ${rowIndex}, ${colIndex}`);
    console.log(`Carta seleccionada: ${selectedCard.id}`);
  
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
        //setSelectedCard(null);
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
      gameLogic(gameId, jwt, user, setDataGamePlayer, setHandCardsPlayer1, setHandCardsPlayer2, setBoard, 
        setIsLoading, setEnergyCards, setPlayer1CardPossiblePositions, setPlayer2CardPossiblePositions,
        setDataGame, setIsMyTurn);
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
      className={`hand ${selectedCard === card ? 'selected-card' : ''}`}
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
          {/* SHOW PLAYER 2 CARDS IF PLAYER 2 */}
          {dataGamePlayer[1].player.id === user.id &&
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
                  console.log('Has seleccionado una carta', card.id)
                }
              }}
            >
              <img src={card.image} alt="Card" />
            </div>
          ))}
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

        </div>
      </div>
    </div>
  );
}