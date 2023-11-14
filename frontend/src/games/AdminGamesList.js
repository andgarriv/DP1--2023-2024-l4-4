import React from "react";
import tokenService from "../services/token.service";
import { useState,useEffect } from "react";
const jwt = tokenService.getLocalAccessToken();

export default function AdminGamesList() {
    const [games, setGames] = useState([]);
    const [loading, setLoading] = useState(true); 

    useEffect(() => {
        async function fetchData() {
            try {
                setTimeout(async () => {
                    const playerResponse = await fetch(`/api/v1/games/admin`, {
                        headers: { Authorization: `Bearer ${jwt}` },
                    });
                    if (!playerResponse.ok) {
                        throw new Error(`HTTP error! status: ${playerResponse.status}`);
                    }
                    const playerData = await playerResponse.json();
                    setGames(playerData);
                    setLoading(false); 
                }, 1000);
            } catch (error) {
                console.error('Error fetching data:', error);
                setLoading(false); 
            }
        }

        fetchData();
    }, [jwt]);

    if (loading) {
        return <div>Loading...</div>;
    }


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

