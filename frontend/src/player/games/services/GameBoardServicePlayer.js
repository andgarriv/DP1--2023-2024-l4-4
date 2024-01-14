/* Board Service
 * =============
 * Script to manage the board of the game. The long functions of board are here.
 */

export async function gameLogic(
  gameId,
  jwt,
  user,
  setDataGamePlayer,
  setHandCardsPlayer1,
  setHandCardsPlayer2,
  setBoard,
  setIsLoading,
  setEnergyCards,
  setPlayer1CardPossiblePositions,
  setPlayer2CardPossiblePositions,
  setIsMyTurn,
  setDataGame,
  setMessages,
  setVisible
) {
  try {
    const responseGame = await fetch(`/api/v1/games/${gameId}`, {
      method: "GET",
      headers: {
        Authorization: `Bearer ${jwt}`,
        "Content-Type": "application/json",
      },
    });

    if (!responseGame.ok) {
      throw new Error("Error al cargar los datos del juego.");
    }

    const dataGame = await responseGame.json();

    if (dataGame.winner) {
      setVisible(true);
      const playerId1 = dataGame.gamePlayers[0].player.id;
      const playerId2 = dataGame.gamePlayers[1].player.id;
      try {
        await fetch(
          `/api/v1/players/${playerId1}/playerachievements`,
          {
            method: "POST",
            headers: {
              Authorization: `Bearer ${jwt}`,
              "Content-Type": "application/json",
            },
          }
        );
        await fetch(
          `/api/v1/players/${playerId2}/playerachievements`,
          {
            method: "POST",
            headers: {
              Authorization: `Bearer ${jwt}`,
              "Content-Type": "application/json",
            },
          }
        );
      } catch (error) {
        console.error(error);
      }
    }

    setDataGame(dataGame);

    const response = await fetch(`/api/v1/games/${gameId}/cards`, {
      method: "GET",
      headers: {
        Authorization: `Bearer ${jwt}`,
        "Content-Type": "application/json",
      },
    });

    if (!response.ok) {
      throw new Error("Error al cargar las cartas.");
    }

    const responseGamePlayer = await fetch(
      `/api/v1/games/${gameId}/gameplayers`,
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
        card.color === dataGamePlayer[0].color && card.cardState === "IN_HAND"
    );
    const handCardsPlayer2 = data.filter(
      (card) =>
        card.color === dataGamePlayer[1].color && card.cardState === "IN_HAND"
    );

    setHandCardsPlayer(handCardsPlayer1, setHandCardsPlayer1);
    setHandCardsPlayer(handCardsPlayer2, setHandCardsPlayer2);
    setEnergyCards(
      dataGamePlayer[0].color,
      dataGamePlayer[1].color,
      setEnergyCards
    );
    importEnergyCards(
      dataGamePlayer[0].color,
      dataGamePlayer[1].color,
      setEnergyCards
    );

    const cardsOnBoard = data.filter((card) => card.cardState === "ON_BOARD");

    const cardsOnBoardImages = await Promise.all(
      cardsOnBoard.map((card) =>
        importGameCard(card.color, card.exit, card.initiative).then(
          (module) => ({
            ...card,
            image: module.default,
            orientation: card.orientation,
          })
        )
      )
    );

    // Actualizar el tablero con las cartas en el tablero
    setBoard((oldBoard) => {
      const newBoard = oldBoard.map((row) => row.slice());

      cardsOnBoardImages.forEach((card) => {
        if (card.color === dataGamePlayer[0].color) {
          newBoard[card.row][card.column] = {
            image: card.image,
            orientation: card.orientation,
          };
        }
        if (card.color === dataGamePlayer[1].color) {
          newBoard[card.row][card.column] = {
            image: card.image,
            orientation: card.orientation,
          };
        }
      });
      return newBoard;
    });

    // Actualizar las posiciones posibles de las cartas
    try {
      const responsePlayer1CardPossiblePositions = await fetch(
        `/api/v1/games/${gameId}/gameplayers/${dataGamePlayer[0].id}/cards/positions`,
        {
          headers: {
            Authorization: `Bearer ${jwt}`,
          },
        }
      );
      if (!responsePlayer1CardPossiblePositions.ok) {
        throw new Error(
          "Error al cargar las posiciones posibles de las cartas del jugador 1."
        );
      }
      const responsePlayer2CardPossiblePositions = await fetch(
        `/api/v1/games/${gameId}/gameplayers/${dataGamePlayer[1].id}/cards/positions`,
        {
          headers: {
            Authorization: `Bearer ${jwt}`,
          },
        }
      );
      if (!responsePlayer2CardPossiblePositions.ok) {
        throw new Error(
          "Error al cargar las posiciones posibles de las cartas del jugador 2."
        );
      }
      const cardPlayer1PossiblePositions =
        await responsePlayer1CardPossiblePositions.json();
      const cardPlayer2PossiblePositions =
        await responsePlayer2CardPossiblePositions.json();

      const parsedPlayer1Positions = cardPlayer1PossiblePositions.map((pos) => {
        const parts = pos.split(",");
        return {
          row: parseInt(parts[0], 10), // Convertir la fila a número
          col: parseInt(parts[1], 10), // Convertir la columna a número
          orientation: parts[2], // Obtener la orientación
        };
      });

      const parsedPlayer2Positions = cardPlayer2PossiblePositions.map((pos) => {
        const parts = pos.split(",");
        return {
          row: parseInt(parts[0], 10), // Convertir la fila a número
          col: parseInt(parts[1], 10), // Convertir la columna a número
          orientation: parts[2], // Obtener la orientación
        };
      });
      setPlayer1CardPossiblePositions(parsedPlayer1Positions);
      setPlayer2CardPossiblePositions(parsedPlayer2Positions);
    } catch (error) {
      console.error(error);
    }

    const myGamePlayer = dataGamePlayer.find(
      (gamePlayer) => gamePlayer.player.id === user.id
    );

    if (myGamePlayer !== undefined) {
      if (myGamePlayer.id === dataGame.gamePlayerTurnId) {
        setIsMyTurn(true);
      } else {
        setIsMyTurn(false);
      }
    }

    setIsLoading(false);
  } catch (error) {
    window.location.href = "/";
    console.error("Error al cargar los datos del juego.", error);
  }

  try {
    const responseMessages = await fetch(`/api/v1/games/${gameId}/messages`, {
      method: "GET",
      headers: {
        Authorization: `Bearer ${jwt}`,
        "Content-Type": "application/json",
      },
    });

    if (!responseMessages.ok) {
      throw new Error("Error al cargar los mensajes.");
    }

    const messages = await responseMessages.json();
    setMessages(messages);
  } catch (error) {
    console.error(error);
  }
}

export function setHandCardsPlayer(cards, setHandCardsFunction) {
  const handCardImages = cards.map((card) => {
    const module = importGameCard(card.color, card.exit, card.initiative);
    return module.then((mod) => ({ ...card, image: mod.default }));
  });

  Promise.all(handCardImages).then((images) => {
    setHandCardsFunction(images);
  });
}

export function importGameCard(color, exit, initiative) {
  let name = "";
  let exitSubstring = exit.replace("EXIT_", "").substring(0, 3);
  if (exit === "START") {
    name = "C" + color.toUpperCase()[0] + "_START";
  } else {
    if (exitSubstring === "111") {
      name = "C" + color.toUpperCase()[0] + "_111_" + initiative;
    } else {
      name = "C" + color.toUpperCase()[0] + "_" + exitSubstring;
    }
  }
  return import(`../../../static/images/GameCards/${name}.png`);
}

function importEnergyCard(color) {
  return import(
    `../../../static/images/GameCards/C${color.toUpperCase()[0]}_ENERGY.png`
  );
}

export function importEnergyCards(color1, color2, setEnergyCards) {
  importEnergyCard(color1).then((module) => {
    const energyCard1 = { image: module.default };
    importEnergyCard(color2).then((module) => {
      const energyCard2 = { image: module.default };
      setEnergyCards([energyCard1, energyCard2]);
    });
  });
}

export function isPlayerAuthorized(user, dataGamePlayer) {
  return (
    user.id === dataGamePlayer[0].player.id ||
    user.id === dataGamePlayer[1].player.id
  );
}

export function getRotationStyle(energy) {
  switch (energy) {
    case 3:
      return { transform: "rotate(0deg)" };
    case 2:
      return { transform: "rotate(90deg)" };
    case 1:
      return { transform: "rotate(180deg)" };
    case 0:
      return { transform: "rotate(270deg)" };
    default:
      return {};
  }
}

export function getColorStyles(colorName) {
  const colors = {
    RED: {
      border: "2px solid rgb(225, 28, 36)",
      backgroundColor: "rgba(225, 28, 36, 0.2)",
    },
    ORANGE: {
      border: "2px solid rgb(239, 145, 20)",
      backgroundColor: "rgba(239, 145, 20, 0.2)",
    },
    YELLOW: {
      border: "2px solid rgb(251, 235, 68)",
      backgroundColor: "rgba(251, 235, 68, 0.2)",
    },
    GREEN: {
      border: "2px solid rgb(75, 178, 91)",
      backgroundColor: "rgba(75, 178, 91, 0.2)",
    },
    BLUE: {
      border: "2px solid rgb(4, 163, 227)",
      backgroundColor: "rgba(4, 163, 227, 0.2)",
    },
    MAGENTA: {
      border: "2px solid rgb(225, 12, 123)",
      backgroundColor: "rgba(225, 12, 123, 0.2)",
    },
    VIOLET: {
      border: "2px solid rgb(192, 138, 184)",
      backgroundColor: "rgba(192, 138, 184, 0.2)",
    },
    WHITE: {
      border: "2px solid rgb(194, 194, 194)",
      backgroundColor: "rgba(194, 194, 194, 0.2)",
    },
  };

  return (
    colors[colorName] || {
      border: "2px solid rgb(0, 0, 0)",
      backgroundColor: "rgba(0, 0, 0, 0.2)",
    }
  );
}

export function getButtonColorStyles(colorName) {
  const colors = {
    RED: {
      rgb: "rgb(225, 28, 36)",
    },
    ORANGE: {
      rgb: "rgb(239, 145, 20)",
    },
    YELLOW: {
      rgb: "rgb(251, 235, 68)",
    },
    GREEN: {
      rgb: "rgb(75, 178, 91)",
    },
    BLUE: {
      rgb: "rgb(4, 163, 227)",
    },
    MAGENTA: {
      rgb: "rgb(225, 12, 123)",
    },
    VIOLET: {
      rgb: "rgb(192, 138, 184)",
    },
    WHITE: {
      rgb: "rgb(194, 194, 194)",
    },
    GREY: {
      rgb: "rgb(128, 128, 128)",
    },
  };

  const colorStyles = colors[colorName] || {};
  const rgbValue = colorStyles.rgb || "rgb(0, 0, 0)";

  return {
    color: rgbValue,
    outlineColor: rgbValue,
    border: `2px solid ${rgbValue}`,
  };
}

export async function playCard(cardId, row, column, orientation, jwt) {
  const response = await fetch(`/api/v1/cards/${cardId}`, {
    method: "PUT",
    headers: {
      Authorization: `Bearer ${jwt}`,
      "Content-Type": "application/json",
    },
    body: JSON.stringify({ row, column, orientation }),
  });

  if (!response.ok) {
    throw new Error("Error al jugar la carta.");
  }
}

export async function updateTurn(gameId, gamePlayerId, jwt) {
  const response = await fetch(`/api/v1/games/${gameId}`, {
    method: "PUT",
    headers: {
      Authorization: `Bearer ${jwt}`,
      "Content-Type": "application/json",
    },
  });

  if (!response.ok) {
    throw new Error("Error al actualizar el turno.");
  }
}

export async function changeEffect(jwt, gameId, effect, isMyTurn) {
  try {
    const response = await fetch(`/api/v1/games/${gameId}/effect`, {
      method: "PUT",
      headers: {
        Authorization: `Bearer ${jwt}`,
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ effect }),
    });

    if (!response.ok) {
      throw new Error("Error al cambiar el efecto.");
    }
  } catch (error) {
    console.error(error);
  }
}

export async function changeCardsInHand(jwt, gameId, isMyTurn) {
  try {
    const response = await fetch(`/api/v1/games/${gameId}/cards/discard`, {
      method: "PUT",
      headers: {
        Authorization: `Bearer ${jwt}`,
        "Content-Type": "application/json",
      },
    });

    if (!response.ok) {
      throw new Error("Error al cambiar el mazo.");
    }
  } catch (error) {
    console.error(error);
  }
}

export async function postMessage(jwt, gameId, reaction, color) {
  try {
    const response = await fetch(`/api/v1/messages`, {
      method: "POST",
      headers: {
        Authorization: `Bearer ${jwt}`,
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ gameId, reaction, color }),
    });

    if (!response.ok) {
      throw new Error("Error al enviar el mensaje.");
    }
  } catch (error) {
    console.error(error);
  }
}
