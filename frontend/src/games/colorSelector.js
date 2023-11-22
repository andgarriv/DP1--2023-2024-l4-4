import React, { useState } from "react";
import { gamePlayerFormInputs } from "./form/gamePlayerFormInputs";

export default function ColorSelector() {
  const [selectedColor, setSelectedColor] = useState("");
  const [selectedText, setSelectedText] = useState("");

  const handleSelectColor = (color, label) => {
    setSelectedColor(color);
    setSelectedText(label);
  };

  return (
    <div className="home-page-container">
      <div className="hero-div" style={{
        display: 'flex',
        maxHeight: '80%',
        maxWidth: '50%',
        justifyContent: 'center',
        alignItems: 'center',
        gap: '10px',
      }}>
        <h1 className="title">SELECT COLOR</h1>
        <div className="colors">
          {gamePlayerFormInputs.map((choice) => (
            <div key={choice.color} className="color-image-container">
              <img
                src={choice.image}
                alt={choice.label}
                className={`color-image ${selectedColor === choice.color ? "selected" : ""}`}
                onClick={() => handleSelectColor(choice.color, choice.label)}
                style={{ width: '100%', height: 'auto' }}
              />
            </div>
          ))}
        </div>
        <div className="selected-text">
          {selectedText}
        </div>
      </div>
    </div>
  );
}
