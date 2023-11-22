import React, { useState } from "react";
import { gamePlayerFormInputs } from "./form/gamePlayerFormInputs";

export default function ColorSelector({ onSelectColor }) {
  const [selectedColor, setSelectedColor] = useState("");

  const handleSelectColor = (color) => {
    setSelectedColor(color);
    onSelectColor(color);
  };

  return (
    <div className="home-page-container">
      {/* Ajusta el contenedor principal para que utilice flexbox */}
      <div className="hero-div" style={{
        display: 'flex',
        flexWrap: 'wrap',
        maxHeight: '30%', // Ajusta la altura máxima del contenedor
        justifyContent: 'center', // centra las imágenes en el contenedor
        alignItems: 'center', // centra las imágenes verticalmente
        gap: '10px', // establece un espacio entre las imágenes
      }}>
              <h1 className="title">SELECT COLOR</h1>
        {gamePlayerFormInputs.map((choice) => (
          // Ajusta el estilo del contenedor de la imagen para 4 columnas
          <div key={choice.color} className="color-image-container" style={{ 
            flex: '1 0 11%', // Permite que cada imagen ocupe aproximadamente el 25% del contenedor, ajustado para el espacio
            maxWidth: '11%', // Esto asegura que el contenido no exceda el 25% del ancho
          }}>
            <img
              src={choice.image}
              alt={choice.label}
              className={`color-image ${selectedColor === choice.color ? "selected" : ""}`}
              onClick={() => handleSelectColor(choice.color)}
              style={{ width: '100%', height: 'auto' }} // Hace que la imagen sea responsive dentro de su contenedor
            />
          </div>
        ))}
      </div>
    </div>
  );
}
