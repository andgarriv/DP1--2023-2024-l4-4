import React, { useEffect, useState } from "react";
import tokenService from "../services/token.service.js";
import { fetchGameCards, isPlayerAuthorized } from "./services/boardService.js";
import "./styles/Board.css";

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

  const rotationClass = content ? getRotationClass(content.orientation) : '';

  return (
    <div className="box">
      {content ? <img src={content.image} alt="Card" className={rotationClass} /> : null}
    </div>
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
  const [handCardsPlayer2, setHandCardsPlayer2] = useState([]);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    if (dataGamePlayer.length > 0) {
      setIsAuthorized(isPlayerAuthorized(user, dataGamePlayer));
    }
    const interval = setInterval(() => {
      fetchGameCards(gameId, jwt, setDataGamePlayer, setHandCardsPlayer1, setHandCardsPlayer2, setBoard, setIsLoading);
    }, 1000); // Actualization every second
    return () => clearInterval(interval);

  }, [jwt, user]);

  if (isLoading) {
    return <div>Loading...</div>;
  }

  if (!isAuthorized) {
    return <div>You are not authorized to see this game</div>;
  }

  return (
    <div className="background">
      <br />
      <div style={{ display: "flex", flexDirection: "row" }}>
        <div className="player-column">
          {dataGamePlayer.length > 0 ? dataGamePlayer[0].player.nickname : null}
          <br />
          {handCardsPlayer1.map((card, index) => (
            <div className="hand" key={index}>
              <img src={card.image} alt="Card" />
            </div>
          ))}
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
          {dataGamePlayer.length > 0 ? dataGamePlayer[1].player.nickname : null}
          <br />
          {handCardsPlayer2.map((card, index) => (
            <div className="hand" key={index}>
              <img src={card.image} alt="Card" />
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}
