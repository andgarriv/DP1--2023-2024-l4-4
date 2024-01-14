import { useRef, useState } from "react";
import FormGenerator from "../../components/formGenerator/formGenerator";
import "../../static/css/auth/authButton.css";
import "../../static/css/auth/authPage.css";
import { registerFormInputs } from "./form/registerFormInputs";

export default function Register() {
  const registerFormRef = useRef();
  const [avatarUrl, setAvatarUrl] = useState("https://cdn-icons-png.flaticon.com/512/5556/5556468.png");
  const defaultAvatarUrl = "https://upload.wikimedia.org/wikipedia/commons/a/a3/Image-not-found.png";
  const handleImageError = (e) => {
    e.target.src = defaultAvatarUrl;
  };
  const updatedRegisterFormInputs = registerFormInputs.map(input => {
    if (input.name === "avatar") {
        return {
            ...input,
            onChange: (value) => setAvatarUrl(value)
        };
    }
    return input;
});


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
        window.location.href = "/login";
      })
      .catch(error => {
        console.error("Error creating player:", error);
      });
  }
  return (
    <div className="auth-page-container">
      <div className="custom-container">
        <h2 style={{ color: "#75FBFD", whiteSpace: "nowrap" }}>Register</h2>
        <div className="auth-form-container">
          <FormGenerator
            ref={registerFormRef}
            inputs={updatedRegisterFormInputs}
            onSubmit={handleSubmit}
            numberOfColumns={1}
            listenEnterKey
            buttonText="Save"
            buttonClassName="auth-button-eol">
            {avatarUrl && (
              <div style={{ textAlign: "center", marginTop: "10px" }}>
                <img
                  src={avatarUrl}
                  alt="Avatar Preview"
                  style={{ width: "100px", height: "100px", borderRadius: "50%" }}
                  onError={handleImageError}
                />
              </div>
            )}
          </FormGenerator>
          
        </div>
      </div>
    </div>
  );
}
