import React, { useState, useEffect } from 'react';
import tokenService from '../services/token.service';
import jwt_decode from "jwt-decode";
import '../App.css';
import '../static/css/home/home.css';

export default function Home() {
    const [roles, setRoles] = useState([]);
    const [username, setUsername] = useState("");
    const jwt = tokenService.getLocalAccessToken();

    useEffect(() => {
        if (jwt) {
            setRoles(jwt_decode(jwt).authorities);
            setUsername(jwt_decode(jwt).sub);
        }
    }, [jwt]);

    let indexNotLogged = null;
    let indexLogged = null;

    if (!jwt) {
        indexNotLogged = (
            <div className="home-page-container">
                <div className="hero-div">
                    <h1 style={{ marginBottom: "40px" }}>END OF LINE</h1>
                    <h4>YOU WILL HAVE ONE SOLE OBJECTIVE: CUT YOUR</h4>
                    <h4>OPPONENT'S LINE BEFORE THEY CUT YOURS</h4>
                </div>
            </div>
        );
    } else {
        indexLogged = (
            <div className="home-page-container">
                <div className="hero-div">
                    <h1 style={{ marginBottom: "40px" }}>Welcome {username}!</h1>
                    <h4>YOU WILL HAVE ONE SOLE OBJECTIVE: CUT YOUR</h4>
                    <h4>OPPONENT'S LINE BEFORE THEY CUT YOURS</h4>
                </div>
                <div className="button-container">
                    <button className="fuente button-style">Play Now!</button>
                </div>
            </div>
        );
    }

    return jwt ? indexLogged : indexNotLogged;
}
