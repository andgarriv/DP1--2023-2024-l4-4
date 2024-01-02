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
      <div className="custom-container">
      <h2 style={{ color: "#75FBFD", whiteSpace: "nowrap"  }}>Register</h2>
      <div className="auth-form-container">
          <FormGenerator
            ref={registerFormRef}
            inputs={registerFormPlayerInputs}
            onSubmit={handleSubmit}
            numberOfColumns={1}
            listenEnterKey
            buttonText="Save"
            buttonClassName="auth-button-eol"
          />
        </div>
      </div>
    </div>
  );
}
