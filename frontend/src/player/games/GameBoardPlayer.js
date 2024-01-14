import React, { useEffect, useRef, useState } from "react";
import { Button } from "reactstrap";
import tokenService from "../../services/token.service.js";
import "../../static/css/board/Board.css";
import FinishPopup from "./services/GameFinishPopUpService.js";

import {
  changeCardsInHand,
  changeEffect,
  gameLogic,
  getButtonColorStyles,
  getColorStyles,
  getRotationStyle,
  isPlayerAuthorized,
  playCard,
  postMessage,
  updateTurn,
} from "./services/GameBoardServicePlayer.js";

function Box({ content, onClick, isHighlighted, playerColor }) {
  const getRotationClass = (orientation) => {
    switch (orientation) {
      case "N":
        return "rotate-north";
      case "S":
        return "rotate-south";
      case "E":
        return "rotate-east";
      case "W":
        return "rotate-west";
      default:
        return "";
    }
  };

  const highlightStyle = isHighlighted ? getColorStyles(playerColor) : {};

  return (
    <button className={`box`} onClick={onClick} style={highlightStyle}>
      {content ? (
        <img
          src={content.image}
          alt="Card"
          className={`card-image ${getRotationClass(content.orientation)}`}
        />
      ) : null}
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
  const [player1CardPossiblePositions, setPlayer1CardPossiblePositions] =
    useState([]);
  const [player2CardPossiblePositions, setPlayer2CardPossiblePositions] =
    useState([]);
  const [selectedCard, setSelectedCard] = useState(null);
  const [dataGame, setDataGame] = useState([]);
  const [isMyTurn, setIsMyTurn] = useState(false);
  const [playerColor, setPlayerColor] = useState(null);
  const [gamePlayerId, setGamePlayerId] = useState(null);
  const [messages, setMessages] = useState([]);
  const [changeDeckButtonVisible, setChangeDeckButtonVisible] = useState(true);
  const [effectUsedInRound, setEffectUsedInRound] = useState(false);
  const [previousRound, setPreviousRound] = useState(0);
  const [lastMessageCount, setLastMessageCount] = useState(0);
  const [visible, setVisible] = useState(false);
  const [isPositionsListEmpty, setIsPositionsListEmpty] = useState(false);
  const messagesEndRef = useRef(null);
  const scrollToBottom = () => {
    if (messagesEndRef.current) {
      const scrollHeight = messagesEndRef.current.scrollHeight;
      messagesEndRef.current.scrollTop = scrollHeight;
    }
  };

  const predefinedMessages = [
    "HI",
    "GG",
    "WHAT_A_PITY",
    "SORRY",
    "THANKS",
    "GOOD_LUCK",
    "NICE",
    "JAJAJAJA",
  ];
  const handleSendMessage = (message) => {
    postMessage(jwt, gameId, message, playerColor);
  };

  useEffect(() => {
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
      if (dataGamePlayer[0].player.id === user.id && !dataGame.winner && player1CardPossiblePositions.length === 0) {
        setIsPositionsListEmpty(true);
      } else if (dataGamePlayer[1].player.id === user.id && !dataGame.winner && player2CardPossiblePositions.length === 0) {
        setIsPositionsListEmpty(true);
      } else {
        setIsPositionsListEmpty(false);
      }
      if (dataGame.winner)
        setEffectUsedInRound(true);
    }
  }, [dataGamePlayer, setGamePlayerId, user, setIsPositionsListEmpty]);

  const isEffectButtonDisabled = effectUsedInRound ||
    dataGame.round < 5 ||
    (dataGame.round >= 5 && !isMyTurn) ||
    (dataGamePlayer.length > 0 && dataGamePlayer[0].player.id === user.id && dataGamePlayer[0].energy < 1) ||
    (dataGamePlayer.length > 0 && dataGamePlayer[1].player.id === user.id && dataGamePlayer[1].energy < 1);


  if (dataGame.round !== previousRound) {
    setEffectUsedInRound(false);
    setPreviousRound(dataGame.round);
  }

  const handleBoxClick = async (colIndex, rowIndex) => {
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
      try {
        await playCard(selectedCard.id, colIndex, rowIndex, cardOrientation, jwt);
        updateTurn(gameId, gamePlayerId, jwt);
        setSelectedCard(null);
        setEffectUsedInRound(true);
      } catch (error) {
        console.error(error);
      }
    }
  };

  useEffect(() => {
    if (messages.length > lastMessageCount) {
      scrollToBottom();
    }
    setLastMessageCount(messages.length);
  }, [messages, lastMessageCount]);


  useEffect(() => {
    if (dataGamePlayer.length > 0) {
      setIsAuthorized(isPlayerAuthorized(user, dataGamePlayer));
    }
    const interval = setInterval(() => {
      gameLogic(
        gameId,
        jwt,
        user,
        setDataGamePlayer,
        setHandCardsPlayer1,
        setHandCardsPlayer2,
        setBoard,
        setIsLoading,
        setEnergyCards,
        setPlayer1CardPossiblePositions,
        setPlayer2CardPossiblePositions,
        setIsMyTurn,
        setDataGame,
        setMessages,
        setVisible,
      );
    }, 1000); // Actualization every second
    return () => clearInterval(interval);
  }, [dataGamePlayer, gameId, jwt, user]);

  if (isLoading) {
    return <div className="home-page-container">
      <div className="loading-container">Loading...</div>;
    </div>;
  }

  if (!isAuthorized) {
    return <div>You are not authorized to see this game!</div>;
  }

  return (
    <div className="background">
      <div style={{ display: "flex", flexDirection: "row" }}>
        <div className="player-column">
          <div style={{ marginTop: "1%", marginBottom: "5%" }}>
            IN HAND
          </div>
          {/* SHOW PLAYER 1 CARDS IF PLAYER 1 */}
          {dataGamePlayer[0].player.id === user.id &&
            handCardsPlayer1.map((card, index) => (
              <div
                className={`hand ${selectedCard === card ? "selected-card" : ""
                  }`}
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
          {/* SHOW PLAYER 2 CARDS IF PLAYER 2 */}
          {dataGamePlayer[1].player.id === user.id &&
            handCardsPlayer2.map((card, index) => (
              <div
                className={`hand ${selectedCard === card ? "selected-card" : ""
                  }`}
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
          {/* Botón 'Change deck'*/}
          {changeDeckButtonVisible && isMyTurn && (dataGame.round === 1 || dataGame.round === 2) && (
            <Button
              outline
              style={{
                textDecoration: "none",
                ...getButtonColorStyles(playerColor),
                width: "30%",
                marginTop: "5%",
              }}
              onClick={() => {
                setChangeDeckButtonVisible(false);
                changeCardsInHand(jwt, gameId, isMyTurn);
              }}
            >
              CHANGE DECK
            </Button>
          )}
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
                    (isMyTurn &&
                      player1CardPossiblePositions.some(
                        (pos) => pos.row === j && pos.col === i
                      ) &&
                      dataGamePlayer[0].player.id === user.id) ||
                    (isMyTurn &&
                      player2CardPossiblePositions.some(
                        (pos) => pos.row === j && pos.col === i
                      ) &&
                      dataGamePlayer[1].player.id === user.id)
                  }
                  playerColor={
                    (dataGamePlayer[0].player.id === user.id &&
                      dataGamePlayer[0].color) ||
                    (dataGamePlayer[1].player.id === user.id &&
                      dataGamePlayer[1].color)
                  }
                />
              ))}
            </div>
          ))}
        </div>
        <div className="player-column">
          <div
            style={{
              background: "#161616",
              height: "50%",
              width: "80%",
              borderRadius: "5%",
              padding: "2%",
            }}
          >
            <h5
              style={{
                display: "flex",
                justifyContent: "center",
                alignItems: "center",
                color: "#c5c6c6",
                padding: "10px 0",
              }}
            >
              Chat with{" "}
              {dataGamePlayer[0].player.id === user.id
                ? dataGamePlayer[1].player.nickname
                : dataGamePlayer[0].player.nickname}
            </h5>

            <div
              style={{
                overflowY: "scroll",
                maxHeight: "150px",
                minHeight: "150px",
                marginBottom: "10px",
              }}
              className="scrollbar-minimalista"
              ref={messagesEndRef}
            >
              {messages.map((message) => (
                <div
                  key={message.id}
                  style={{
                    color: getButtonColorStyles(message.color).color,
                    padding: "5px",
                    marginLeft: "10px",
                    marginRight: "10px",
                    textAlign: message.color === playerColor ? "right" : "left",
                  }}
                >
                  {message.reaction.replaceAll("_", " ") + "!"}
                </div>
              ))}
              <div ref={messagesEndRef} />
            </div>
            <div
              style={{
                display: "flex",
                justifyContent: "center",
                flexFlow: "row wrap",
                padding: "5px",
              }}
            >
              {predefinedMessages.map((message, index) => (
                <Button
                  key={index}
                  outline
                  style={{
                    textDecoration: "none",
                    ...getButtonColorStyles(playerColor),
                    width: "auto",
                  }}
                  onClick={() => handleSendMessage(message)}
                >
                  {message.replaceAll("_", " ") + "!"}
                </Button>
              ))}
            </div>
          </div>
          <Button
            outline
            style={{
              textDecoration: "none",
              ...getButtonColorStyles(isEffectButtonDisabled || isPositionsListEmpty ? "GREY" : playerColor),
              width: "30%",
              marginTop: "3%",

            }}
            onClick={() => {
              if (dataGame.round >= 5 && !effectUsedInRound && isMyTurn) {
                changeEffect(jwt, gameId, "EXTRA_GAS", isMyTurn);
                setEffectUsedInRound(true);
              }
            }}
          >
            EXTRA GAS
          </Button>
          <Button
            className={isPositionsListEmpty && !isEffectButtonDisabled ? "blink" : ""}
            outline
            style={{
              textDecoration: "none",
              ...getButtonColorStyles(isEffectButtonDisabled ? "GREY" : playerColor),
              width: "30%",
              marginTop: "3%",
            }}
            onClick={() => {
              if (dataGame.round >= 5 && !effectUsedInRound && isMyTurn) {
                changeEffect(jwt, gameId, "REVERSE", isMyTurn);
                setEffectUsedInRound(true);
              }
            }}
          >
            REVERSE
          </Button>
          <Button
            outline
            style={{
              textDecoration: "none",
              ...getButtonColorStyles(isEffectButtonDisabled || isPositionsListEmpty ? "GREY" : playerColor),
              width: "30%",
              marginTop: "3%",
            }}
            onClick={() => {
              if (dataGame.round >= 5 && !effectUsedInRound && isMyTurn) {
                changeEffect(jwt, gameId, "BRAKE", isMyTurn);
                setEffectUsedInRound(true);
              }
            }}
          >
            BRAKE
          </Button>
          <Button
            outline
            style={{
              textDecoration: "none",
              ...getButtonColorStyles(isEffectButtonDisabled || isPositionsListEmpty ? "GREY" : playerColor),
              width: "30%",
              marginTop: "3%",
            }}
            onClick={() => {
              if (dataGame.round >= 5 && !effectUsedInRound && isMyTurn) {
                changeEffect(jwt, gameId, "SPEED_UP", isMyTurn);
                setEffectUsedInRound(true);
              }
            }}
          >
            SPEED UP
          </Button>
          {dataGamePlayer[0].player.id === user.id && (
            <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center' }}>
              <div className="hand">
                <img
                  src={energyCards[0].image}
                  alt="EnergyCard0"
                  style={{
                    ...getRotationStyle(
                      dataGamePlayer.length > 0 ? dataGamePlayer[0].energy : 0
                    ),
                    marginTop: "20%",
                  }} />
              </div>
              <div className="hand">
                <img
                  src={energyCards[1].image}
                  alt="EnergyCard0"
                  style={{
                    ...getRotationStyle(
                      dataGamePlayer.length > 0 ? dataGamePlayer[1].energy : 0
                    ),
                    marginTop: "20%",
                  }} />
              </div>
            </div>
          )}
          {dataGamePlayer[1].player.id === user.id && (
            <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center' }}>
              <div className="hand">
                <img
                  src={energyCards[1].image}
                  alt="EnergyCard0"
                  style={{
                    ...getRotationStyle(
                      dataGamePlayer.length > 0 ? dataGamePlayer[1].energy : 0
                    ),
                    marginTop: "20%",
                  }} />
              </div>
              <div className="hand">
                <img
                  src={energyCards[0].image}
                  alt="EnergyCard0"
                  style={{
                    ...getRotationStyle(
                      dataGamePlayer.length > 0 ? dataGamePlayer[0].energy : 0
                    ),
                    marginTop: "20%",
                  }} />
              </div>
            </div>
          )}
        </div>
      </div>
      <FinishPopup
        visible={visible}
        setVisible={setVisible}
        gameId={gameId}
      />
    </div>
  );
}
