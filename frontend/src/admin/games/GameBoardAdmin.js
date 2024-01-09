import React, { useEffect, useState } from "react";
import FinishPopup from "../../player/games/services/GameFinishPopUpService.js";
import tokenService from "../../services/token.service.js";
import "../../static/css/board/Board.css";
import { gameLogicAdmin, getRotationStyle } from "./services/GameBoardServiceAdmin.js";

function Box({ content }) {
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
  return (
    <button
      className={`box`}
    >
      {content ? <img src={content.image} alt="Card" className={`card-image ${getRotationClass(content.orientation)}`} /> : null}
    </button>
  );
}

export default function AdminBoard() {
  const jwt = tokenService.getLocalAccessToken();
  const user = tokenService.getUser();
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
  const [energyCards, setEnergyCards] = useState([]);
  const [handCardsPlayer1, setHandCardsPlayer1] = useState([]);
  const [handCardsPlayer2, setHandCardsPlayer2] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [visible, setVisible] = useState(false);

  useEffect(() => {
    const interval = setInterval(() => {
      gameLogicAdmin(gameId, jwt, setDataGamePlayer, setHandCardsPlayer1, setHandCardsPlayer2, setBoard,
        setIsLoading, setEnergyCards, setVisible);
    }, 1000);
    return () => clearInterval(interval);

  }, [gameId, jwt, user]);

  if (isLoading) {
    return <div className="home-page-container">
      <div className="loading-container">Loading...</div>;
    </div>;
  }

  return (
    <div className="background">
      <div style={{ display: "flex", flexDirection: "row" }}>
        <div className="player-column">
          <div style={{ marginTop: "1%", marginBottom: "2%" }}>
            {dataGamePlayer.length > 0 ? dataGamePlayer[0].player.nickname : null}
          </div>
          {handCardsPlayer1.map((card, index) => (
            <div className="hand" key={index}>
              <img src={card.image} alt="Card" />
            </div>
          ))}
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
        </div>
        <div className="board">
          {board.map((row, i) => (
            <div key={i} className="row2">
              {row.map((boxContent, j) => (
                <Box key={j} content={boxContent} />
              ))}
            </div>
          ))}
        </div>
        <div className="player-column">
          <div style={{ marginTop: "1%", marginBottom: "2%" }}>
            {dataGamePlayer.length > 0 ? dataGamePlayer[1].player.nickname : null}
          </div>
          {handCardsPlayer2.map((card, index) => (
            <div className="hand" key={index}>
              <img src={card.image} alt="Card" />
            </div>
          )
          )}
          <div className="hand">
            <img
              src={energyCards[1].image}
              alt="EnergyCard1"
              style={{
                ...getRotationStyle(dataGamePlayer.length > 0 ? dataGamePlayer[1].energy : 0),
                marginTop: '40px'
              }}
            />
          </div>
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
