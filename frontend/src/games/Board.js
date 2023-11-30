import React, { useEffect, useState } from "react";
import cardBackImage from '../static/images/GameCards/CB_BACK.png';
import cardBackImage2 from '../static/images/GameCards/CG_BACK.png';
import './styles/Board.css';
import tokenService from "../services/token.service";



function Box({ content }) {
    return (
        <div className="box">
            {content ? <img src={content.image} alt="Card" /> : null}
        </div>
    );
}

function importGameCard(color, exit){
    let name = '';
    if(exit == 'START'){
        name = 'C'+color.touppperCase()[0]+'_START';
    } else {
        name = 'C'+color.touppperCase()[0]+'_'+exit.replace('EXIT_','').substring(0,2);
    }
    return import(`../static/images/GameCards/${name}.png`);
}



export default function Board() {
    const jwt = tokenService.getLocalAccessToken();
    
    const user = tokenService.getUser();

    const [board, setBoard] = useState(
        Array(7).fill(null).map(() => Array(7).fill(null))
    );
    const gameId = window.location.pathname.split("/")[window.location.pathname.split("/").length - 1];
    

    useEffect(() => {
        // const Card = { image: "../static/images/GameCards/CB_BACK.png" };
        const Card = { image: cardBackImage };
        const Card2 = { image: cardBackImage2 };

        // Function to get the game cards
        async function fetchGameCards() {
            try {
                const response = await fetch(
                    `http://localhost:8080/api/v1/cards/game/${gameId}`,
                    {
                        method: 'GET',
                        headers: {
                            Authorization: `Bearer ${jwt}`,
                            'Content-Type': 'application/json',
                        }});
                const data = await response.json();
                console.log(data);
            } catch (error) {
                console.error('Error al cargar las cartas' + error);
            }
        }


        setBoard((oldBoard) => {
            const newBoard = oldBoard.map(fila => fila.slice());
            newBoard[0][0] = Card;
            newBoard[0][1] = Card;
            newBoard[0][2] = Card;
            newBoard[0][3] = Card;
            newBoard[0][4] = Card;
            newBoard[0][5] = Card;
            newBoard[0][6] = Card;

            newBoard[1][0] = Card;
            newBoard[1][1] = Card;
            newBoard[1][2] = Card;
            newBoard[1][3] = Card;
            newBoard[1][4] = Card;
            newBoard[1][5] = Card;
            newBoard[1][6] = Card;

            newBoard[2][0] = Card;
            newBoard[2][1] = Card;
            newBoard[2][2] = Card;
            newBoard[2][3] = Card;
            newBoard[2][4] = Card;
            newBoard[2][5] = Card;
            newBoard[2][6] = Card;

            newBoard[3][0] = Card;
            newBoard[3][1] = Card;
            newBoard[3][2] = Card;
            newBoard[3][3] = Card;
            newBoard[3][4] = Card;
            newBoard[3][5] = Card;
            newBoard[3][6] = Card;

            newBoard[4][0] = Card;
            newBoard[4][1] = Card;
            newBoard[4][2] = Card;
            newBoard[4][3] = Card;
            newBoard[4][4] = Card;
            newBoard[4][5] = Card;
            newBoard[4][6] = Card;

            newBoard[5][0] = Card;
            newBoard[5][1] = Card;
            newBoard[5][2] = Card;
            newBoard[5][3] = Card;
            newBoard[5][4] = Card;
            newBoard[5][5] = Card;
            newBoard[5][6] = Card;

            newBoard[6][0] = Card2; // Ya asignado
            newBoard[6][1] = Card;
            newBoard[6][2] = Card;
            newBoard[6][3] = Card;
            newBoard[6][4] = Card;
            newBoard[6][5] = Card;
            newBoard[6][6] = Card2; // Ya asignado

            return newBoard;
        });
        fetchGameCards();
    }, [gameId, jwt, user.id]);

    return (
        <div className="background">
            {/* ...otro contenido... */}
            <br />
            <div className="board">
                {board.map((row, i) => (
                    <div key={i} className="row2">
                        {row.map((boxContent, j) => (
                            <Box
                                key={j}
                                content={boxContent}
                            />
                        ))}
                    </div>
                ))}
            </div>
        </div>
    );
}
