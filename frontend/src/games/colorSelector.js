import React, { useState } from "react";
import { Link } from "react-router-dom";
import tokenService from "../services/token.service";
import getErrorModal from "../util/getErrorModal";
import { gamePlayerFormInputs } from "./form/gamePlayerFormInputs";

const user = tokenService.getUser();

export default function ColorSelector() {
  const [selectedColor, setSelectedColor] = useState("");
  const [selectedText, setSelectedText] = useState("");
  const [message, setMessage] = useState("");
  const [visible, setVisible] = useState(false);

  const modal = getErrorModal(setVisible, visible, message);

  const handleSelectColor = (color, label) => {
    setSelectedColor(color);
    setSelectedText(label);
  };

  const handleSaveColor = async () => {
    try {
      const response = await fetch(`/api/v1/gamePlayer/${user.id}`, {
      method : "POST",
      });
      if (response.ok) {
        console.log("Player created successfully");
        window.location.href = "/game";
      } else {
        const data = await response.json();
        setMessage(data.message || "Error creating player");
        setVisible(true);
      }
    } catch (error) {
      setMessage("Error creating player. Please try again.");
      setVisible(true);
    }
  };

  return (
    <div className="home-page-container">
      <div
        className="hero-div"
        style={{
          display: "flex",
          maxHeight: "80%",
          maxWidth: "50%",
          justifyContent: "center",
          alignItems: "center",
          gap: "10px",
        }}
      >
        <h1 className="title">SELECT COLOR</h1>
        <div className="colors">
          {gamePlayerFormInputs.map((choice) => (
            <div
              key={choice.color}
              className={`color-image-container ${
                selectedColor === choice.color ? "selected-container" : ""
              }`}
            >
              <img
                src={choice.image}
                alt={choice.label}
                className={`color-image ${
                  selectedColor === choice.color ? "selected" : ""
                }`}
                onClick={() => handleSelectColor(choice.color, choice.label)}
                style={{ width: "100%", height: "auto" }}
              />
            </div>
          ))}
        </div>
        <div className="selected-text">Currently: {selectedText}</div>
        {modal}
        <div className="button-container">
          <Link to={"/game"}>
            <button className="fuente button-style" onClick={handleSaveColor}>
              Continue
            </button>
          </Link>
        </div>
      </div>
    </div>
  );
}
