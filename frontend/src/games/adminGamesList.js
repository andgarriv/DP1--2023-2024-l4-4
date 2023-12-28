import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import tokenService from "../services/token.service";
const jwt = tokenService.getLocalAccessToken();

export default function AdminGamesList() {
  const [games, setGames] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    async function fetchData() {
      try {
        setTimeout(async () => {
          const playerResponse = await fetch(`/api/v1/games/all`, {
            headers: { Authorization: `Bearer ${jwt}` },
          });
          if (!playerResponse.ok) {
            throw new Error(`HTTP error! status: ${playerResponse.status}`);
          }
          const playerData = await playerResponse.json();
          setGames(playerData);
          setLoading(false);
        }, 250);
      } catch (error) {
        console.error("Error fetching data:", error);
        setLoading(false);
      }
    }

    fetchData();
  }, [jwt]);

  if (loading) {
    return <div className="loading">Loading...</div>;
  }

  const GameListContainer = ({ children }) => (
    <div
      style={{
        maxHeight: "470px",
        overflowY: "auto",
        width: "40%",
      }}
      className="scrollbar-minimalista"
    >
      {children}
    </div>
  );

  const gameList = games.map((game) => (
    <tr key={game.id}>
  <td className="text-center" style={{ width: "10%" }}>
        <div style= {{marginRight:"10px"}}>
          {game.id}
          <br />
          <br />
          <br />
        </div>
      </td>

      <td className="text-center" style={{ width: "30%"}}>
      <div style= {{marginRight:"10px"}}>
          {game.winner ? game.winner.nickname : "----"}
          <br />
          <br />
          <br />
        </div>
      </td>

      <td className="text-center" style={{ width: "30%"}}>
      <div style= {{marginRight:"20px"}}>
          {game.gamePlayers[0].player.nickname}
          <br />
        </div>
        <div>
          {game.gamePlayers[1].player.nickname}
          <br />
          <br />
          <br />
        </div>
      </td>

      <td className="text-center" style={{ width: "30%"}}>
      <div>
          {game.winner ? (
            ""
          ) : (
            <Link
              className="auth-button-eol"
              to={`/game/${game.id}`}
              style={{ textDecoration: "none" }}
            >
              WATCH
            </Link>
          )}
          <br />
          <br />
          <br />
        </div>
      </td>
    </tr>
  ));

return (
    <div className="home-page-container">
      <GameListContainer>
        <div className="hero-div">
          <h1 className="text-center">Games</h1>
          <table>
            <thead>
              <tr style={{color: "magenta", textAlign:"center", marginBottom: "5px"}}>
                <th style={{ width: "25%" }}>Game</th>
                <th style={{ width: "25%" }}>Winner</th>
                <th style={{ width: "25%" }}>Players</th>
                <th style={{ width: "25%" }}></th>
              </tr>
            </thead>
            <tbody>{gameList}</tbody>
          </table>
          {games.length === 0 && "There are no games to show"}
        </div>
      </GameListContainer>
    </div>
  );
}
