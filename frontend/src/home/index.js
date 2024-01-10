import jwt_decode from "jwt-decode";
import React, { useEffect, useState } from "react";
import "../App.css";
import OnGoingGamePopup from "../OnGoingGamePopup";
import PlayNowHandler from "../player/games/GamePlayNowPlayer";
import tokenService from "../services/token.service";
import "../static/css/home/home.css";

export default function Home() {
  const [roles, setRoles] = useState([]);
  const [username, setUsername] = useState("");
  const jwt = tokenService.getLocalAccessToken();
  const [visible, setVisible] = useState(false);
  const [gameId, setGameId] = useState(null);
  const [user, setUser] = useState(null);
  const [otherPlayer, setOtherPlayer] = useState(null);

  useEffect(() => {
    if (jwt) {
      setRoles(jwt_decode(jwt).authorities);
      setUsername(jwt_decode(jwt).sub);
      setUser(tokenService.getUser());
    }
  }, [jwt]);

  const handleClick = () => {
    PlayNowHandler(user, setVisible, setGameId, setOtherPlayer);
  };

  let indexNotLogged = null;
  let indexLogged = null;
  roles.forEach((role) => {
    if (role === "PLAYER") {
      indexLogged = (
        <div className="home-page-container">
          <div className="hero-div">
            <h1 style={{ marginBottom: "40px" }}>Welcome {username}!</h1>
            <h4>YOU WILL HAVE ONE SOLE OBJECTIVE: CUT YOUR</h4>
            <h4>OPPONENT'S LINE BEFORE THEY CUT YOURS</h4>
          </div>
          <div className="button-container">
            <button
              onClick={handleClick}
              className="fuente button-style"
            >
              Play Now!
            </button>
          </div>
          {visible && otherPlayer && <OnGoingGamePopup setVisible={setVisible} visible={visible} gameId={gameId} player={otherPlayer} />}
        </div>
        
      );
    } else {
      indexLogged = (
        <div className="home-page-container">
          <div className="hero-div">
            <h1 style={{ marginBottom: "40px" }}>Welcome {username}!</h1>
            <h4>YOU WILL HAVE ONE SOLE OBJECTIVE: CUT YOUR</h4>
            <h4>OPPONENT'S LINE BEFORE THEY CUT YOURS</h4>
          </div>
        </div>
      );
    }
  });

  if (!jwt) {
    indexNotLogged = (
      <div className="home-page-container">
        <div className="hero-div">
          <h1 style={{ marginBottom: "40px" }}>END OF LINE</h1>
          <h4>YOU WILL HAVE ONE SOLE OBJECTIVE: CUT YOUR</h4>
          <h4>OPPONENT'S LINE BEFORE THEY CUT YOURS</h4>
        </div>
      </div>
    );
  }

  return jwt ? indexLogged : indexNotLogged;
}
