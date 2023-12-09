import { useRef } from "react";
import FormGenerator from "../../components/formGenerator/formGenerator";
import "../../static/css/auth/authButton.css";
import "../../static/css/auth/authPage.css";
import { registerFormPlayerInputs } from "./form/registerFormPlayerInputs";

export default function Register() {
  const registerFormRef = useRef();

  function handleSubmit({ values }) {
    if (!registerFormRef.current.validate()) return;
    const playerData = values;
    console.log("Player data:", playerData);
    playerData["authority"] = { id: 2, authority: "PLAYER" };

    fetch("/api/v1/players", {
      method: "POST",
      headers: { 
        Accept: "application/json",
        "Content-Type": "application/json" 
      },
      body: JSON.stringify(playerData),
    })
      .then(response => {
        if (!response.ok) throw new Error(response.status);
        return response.json();
      })
      .then(data => {
        console.log("Player created:", data);
        window.location.href = "/login";
      })
      .catch(error => {
        console.error("Error creating player:", error);
      });
  }
  return (
    <div className="auth-page-container">
      <div className="hero-div">
        <h2>Register</h2>
        <div style={{ marginTop: "10px" }}>
          <FormGenerator
            ref={registerFormRef}
            inputs={registerFormPlayerInputs}
            onSubmit={handleSubmit}
            numberOfColumns={1}
            listenEnterKey
            buttonText="Save"
            buttonClassName="auth-button"
          />
        </div>
      </div>
    </div>
  );
}
