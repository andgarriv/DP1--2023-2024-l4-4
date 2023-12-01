import React, { useEffect, useState } from "react";
import tokenService from "../services/token.service";
import './styles/Board.css';



function Box({ content }) {
    return (
        <div className="box">
            {content ? <img src={content.image} alt="Card" /> : null}
        </div>
    );
}

function importGameCard(color, exit){
    let name = '';
    if(exit === 'START'){
        name = 'C'+color.toUpperCase()[0]+'_START';
    } else {
        name = 'C'+color.toUpperCase()[0]+'_'+exit.replace('EXIT_','').substring(0,2);
    }
    return import(`../static/images/GameCards/${name}.png`);
}



export default function Board() {
    const jwt = tokenService.getLocalAccessToken();

    const [board, setBoard] = useState(
        Array(7).fill(null).map(() => Array(7).fill(null))
    );
    const gameId = window.location.pathname.split("/")[window.location.pathname.split("/").length - 1];
    

    useEffect(() => {
        async function fetchGameCards() {
            try {
                const response = await fetch(`/api/v1/cards/game/${gameId}`, {
                    method: 'GET',
                    headers: {
                        Authorization: `Bearer ${jwt}`,
                        'Content-Type': 'application/json',
                    }
                });
                const responseGamePlayer = await fetch(`/api/v1/gameplayers/game/${gameId}`, {
                    headers: {
                        'Authorization': `Bearer ${jwt}`
                    }
                });
                const dataGamePlayer = await responseGamePlayer.json();
                const data = await response.json();
    
                // Filtrar las cartas de inicio
                const startCards = data.filter(card => card.exit === "START");
    
                // Cargar imágenes para las cartas de inicio
                const startCardImages = await Promise.all(startCards.map(card => 
                    importGameCard(card.color, card.exit).then(module => ({ ...card, image: module.default }))
                ));
    
                // Actualizar el tablero con las cartas de inicio
                setBoard(oldBoard => {
                    const newBoard = oldBoard.map(row => row.slice());
    
                    startCardImages.forEach(card => {
                        // Asumiendo posiciones fijas para las cartas de inicio
                        if (card.color === dataGamePlayer[0].color) {
                            newBoard[card.row][card.column] = { image: card.image };
                        }
                        if (card.color === dataGamePlayer[1].color) {
                            newBoard[card.row][card.column] = { image: card.image };
                        }
                    });
    
                    return newBoard;
                });
            } catch (error) {
                console.error('Error al cargar las cartas', error);
            }
        }
        fetchGameCards();
    }, [gameId, jwt]);
    

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