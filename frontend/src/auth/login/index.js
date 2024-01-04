import React, { useState } from "react";
import { Alert } from "reactstrap";
import FormGenerator from "../../components/formGenerator/formGenerator";
import tokenService from "../../services/token.service";
import "../../static/css/auth/authButton.css";
import { loginFormInputs } from "./form/loginFormInputs";

export default function Login() {
  const [message, setMessage] = useState(null)
  const loginFormRef = React.createRef();


  async function handleSubmit({ values }) {

    const reqBody = values;
    setMessage(null);
    await fetch("/api/v1/auth/signin", {
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json"
      },
      method: "POST",
      body: JSON.stringify(reqBody),
    })
      .then(function (response) {
        if (response.status === 200) return response.json();
        else return Promise.reject("Invalid login attempt");
      })
      .then(function (data) {
        tokenService.setUser(data);
        tokenService.updateLocalAccessToken(data.token);
        window.location.href = "/"; //Esto es para que se vaya a la pagina inicio en vez de al dashboard.
      })
      .catch((error) => {
        setMessage(error);
      });
  }


  return (
    <div className="auth-page-container">
      {message ? (
        <Alert color="primary">{message}</Alert>
      ) : (
        <></>
      )}

      <div className="custom-container">
        <h2 style={{ color: "#75FBFD", whiteSpace: "nowrap" }}>Sign In</h2>
        <div className="auth-form-container">
          <FormGenerator
            ref={loginFormRef}
            inputs={loginFormInputs}
            onSubmit={handleSubmit}
            numberOfColumns={1}
            listenEnterKey
            buttonText="Login"
            buttonClassName="auth-button-eol"
          />
        </div>
      </div>
    </div>
  );
}