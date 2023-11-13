import React from "react";
import { Table } from "reactstrap";
import tokenService from "../services/token.service";
import useFetchState from "../util/useFetchState";

const imgnotfound = "https://cdn-icons-png.flaticon.com/512/5778/5778223.png";
const jwt = tokenService.getLocalAccessToken();
const user = tokenService.getUser();

export default function AdminGamesList() {
    const [games, setGames] = useFetchState([], `/api/v1/games/player/${user.id}`, jwt);
    console.log(games);
    console.log(user.id);

    const gameList = games.map((game) => (


        <tr key={game.id}>
            <td className="text-center" colSpan="2">
                <div style={{ marginRight: "50px", marginBottom: "15px" }}>
                    {game.id}
                    <br />
                </div>
            </td>

            <td className="text-center" colSpan="2">
                <div style={{ marginRight: "40px", marginBottom: "15px" }}>
                    {game.winner ? game.winner.nickname : "----"}
                    <br />
                </div>
            </td>

            <td className="text-center" colSpan="2">
                <div style={{ marginRight: "0px", marginBottom: "15px" }}>
                    {game.gamePlayers[0].player.nickname}
                    <br />
                </div>
                <div style={{ marginRight: "0px", marginBottom: "15px" }}>
                    {game.gamePlayers[1].player.nickname}
                    <br />
                </div>
            </td>


        </tr>

    ));
    return (
        <div className="home-page-container">
            <div className="hero-div">
                <h1 className="text-center">Games</h1>
                <tr>
                    <td className="text-center" colSpan="2">
                        <div style={{ color: "magenta", marginRight: "35px", marginLeft: "0px", marginBottom: "15px" }}>
                            {"Game"}
                            <br />
                        </div>
                    </td>

                    <td className="text-center" colSpan="2">
                        <div style={{ color: "magenta", marginRight: "70px", marginLeft: "1px", marginBottom: "15px" }}>
                            {"Winner"}
                            <br />
                        </div>
                    </td>

                    <td className="text-center" colSpan="2">
                        <div style={{ color: "magenta", marginRight: "25px", marginBottom: "15px" }}>
                            {"Players"}
                            <br />
                        </div>
                    </td>
                </tr>



                {games.length > 0 ? <tbody>{gameList}</tbody>:"There are no games to show"}
                
            </div>
        </div>
    );
}
