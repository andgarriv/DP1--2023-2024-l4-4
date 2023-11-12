import {
    Table, Button
} from "reactstrap";
import { Link } from "react-router-dom";
import tokenService from "../services/token.service";
import useFetchState from "../util/useFetchState";
import deleteFromList from "./../util/deleteFromList";
import { useState } from "react";
import getErrorModal from "./../util/getErrorModal";

const imgnotfound = "https://cdn-icons-png.flaticon.com/512/5778/5778223.png";
const jwt = tokenService.getLocalAccessToken();

export default function AchievementList() {
    const [games, setGames] = useFetchState(
        [],
        `/api/v1/games/admin`,
        jwt
    );
    
    const achievementList =
    games.map((a) => {
        return (
            <tr key={a.id} >
                 <td className="text-center">
                </td>
                <td className="text-center" colSpan="2">
                        {a.winner}
                         <br />
                     {a.rounds}
                    </td>

            </tr>
        );
    });
    return (
        <div className="home-page-container">
            <div className="hero-div">
                <h1 className="text-center">Games</h1>
                <div >
                        <tbody>{achievementList}</tbody>
                </div>
            </div>
        </div>
    );
}