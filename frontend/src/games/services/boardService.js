/* Board Service
 * =============
 * Script to manage the board of the game. The long functions of board are here.
*/

export async function fetchGameCards(gameId, jwt, setDataGamePlayer, setHandCardsPlayer1, setHandCardsPlayer2, setBoard, setIsLoading) {
    try {
      const response = await fetch(`/api/v1/cards/games/${gameId}`, {
        method: "GET",
        headers: {
          Authorization: `Bearer ${jwt}`,
          "Content-Type": "application/json",
        },
      });

      if(!response.ok) {
        throw new Error('Error al cargar las cartas.');
      }
      
      const responseGamePlayer = await fetch(
        `/api/v1/gameplayers/games/${gameId}`,
        {
          headers: {
            Authorization: `Bearer ${jwt}`,
          },
        }
      );
      if (!responseGamePlayer.ok) {
        throw new Error("Error al cargar los gameplayers.");
      }

      const dataGamePlayer = await responseGamePlayer.json();
      setDataGamePlayer(dataGamePlayer);
      const data = await response.json();
      const handCardsPlayer1 = data.filter(
        (card) =>
          card.color === dataGamePlayer[0].color &&
          card.cardState === "IN_HAND"
      );
      const handCardsPlayer2 = data.filter(
        (card) =>
          card.color === dataGamePlayer[1].color &&
          card.cardState === "IN_HAND"
      );
  
      setHandCardsPlayer(handCardsPlayer1, setHandCardsPlayer1);
      setHandCardsPlayer(handCardsPlayer2, setHandCardsPlayer2);
  
      const cardsOnBoard = data.filter((card) => card.cardState === "ON_BOARD");
  
      const cardsOnBoardImages = await Promise.all(
        cardsOnBoard.map((card) =>
          importGameCard(card.color, card.exit, card.iniciative).then(
            (module) => ({ ...card, image: module.default, orientation: card.orientation })
          )
        )
      );
  
      // Actualizar el tablero con las cartas en el tablero
      setBoard((oldBoard) => {
        const newBoard = oldBoard.map((row) => row.slice());
  
        cardsOnBoardImages.forEach((card) => {
          if (card.color === dataGamePlayer[0].color) {
            newBoard[card.row][card.column] = { image: card.image, orientation: card.orientation };
          }
          if (card.color === dataGamePlayer[1].color) {
            newBoard[card.row][card.column] = { image: card.image, orientation: card.orientation };
          }
        });
  
        return newBoard;
      });
  
      setIsLoading(false);
    } catch (error) {
      console.error("Error al cargar los datos del juego.", error);
    }
  }

export function setHandCardsPlayer(cards, setHandCardsFunction) {
  const handCardImages = cards.map((card) => {
    const module = importGameCard(card.color, card.exit, card.iniciative);
    return module.then((mod) => ({ ...card, image: mod.default }));
  });

  Promise.all(handCardImages).then((images) => {
    setHandCardsFunction(images);
  });
}

export function importGameCard(color, exit, iniciative) {
  let name = "";
  let exitSubstring = exit.replace("EXIT_", "").substring(0, 3);
  if (exit === "START") {
    name = "C" + color.toUpperCase()[0] + "_START";
  } else {
    if (exitSubstring === "111") {
      name = "C" + color.toUpperCase()[0] + "_111_" + iniciative;
    } else {
      name = "C" + color.toUpperCase()[0] + "_" + exitSubstring;
    }
  }
  return import(`../../static/images/GameCards/${name}.png`);
}

export function isPlayerAuthorized(user, dataGamePlayer) {
  return (
    user.id === dataGamePlayer[0].player.id ||
    user.id === dataGamePlayer[1].player.id
  );
}