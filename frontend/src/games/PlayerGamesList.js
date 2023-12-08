import React, { useEffect, useState } from 'react';
import tokenService from '../services/token.service';


const jwt = tokenService.getLocalAccessToken();
const user = tokenService.getUser();

export default function AdminGamesList() {
    const [playerGames, setPlayerGames] = useState([]);
    const [loading, setLoading] = useState(true); 

    useEffect(() => {
        async function fetchData() {
            try {
                setTimeout(async () => {
                    const playerResponse = await fetch(`/api/v1/games/players/${user.id}`, {
                        headers: { Authorization: `Bearer ${jwt}` },
                    });
                    if (!playerResponse.ok) {
                        throw new Error(`HTTP error! status: ${playerResponse.status}`);
                    }
                    const playerData = await playerResponse.json();
                    setPlayerGames(playerData);
                    setLoading(false); 
                }, 250);
            } catch (error) {
                console.error('Error fetching data:', error);
                setLoading(false); 
            }
        }

        fetchData();
    }, [jwt, user.id]);

    console.log(playerGames);

    if (loading) {
        return <div className="loading">Loading...</div>;
    }


    const GameListContainer = ({ children }) => (
        <div style={{
            maxHeight: '470px', 
            overflowY: 'auto',
            marginBottom: 'auto',
            width: '40%',
        }} className="scrollbar-minimalista">
            {children}
        </div>
    );
    


    const gameList = playerGames.map((game) => (
        <tr key={game.id}>
            <td className="text-center" colSpan="2">
                <div style={{ marginRight: "50px", marginBottom: "15px" }}>
                    {game.id}
                    <br />
                    <br />
                    <br />
                </div>
            </td>

            <td className="text-center" colSpan="2">
                <div style={{ marginRight: "40px", marginBottom: "15px" }}>
                    {game.winner ? game.winner.nickname : "----"}
                    <br />
                    <br />
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
                    <br />
                    <br />
                </div>
            </td>


        </tr>
        

    ));

    

    return (
        <div className="home-page-container">
            <h1 className="title">Games</h1>
            <GameListContainer>
            <div className="hero-div">
                
                <tr>
                    <td className="text-center" colSpan="2">
                        <div style={{ color: "magenta", marginRight: "35px", marginLeft: "0px", marginBottom: "15px" }}>
                            {"Game"}
                            <br />
                        </div>
                    </td>

                    <td className="text-center" colSpan="2">
                        <div style={{ color: "magenta", marginRight: "55px", marginLeft: "1px", marginBottom: "15px" }}>
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



                {playerGames.length > 0 ? <tbody>{gameList}</tbody>:"There are no games to show"}
                
            </div>
            </GameListContainer>
        </div>
    );
}