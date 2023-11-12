import {
    Table, Button
} from "reactstrap";
import { Link } from "react-router-dom";
import tokenService from "../services/token.service";
import useFetchState from "../util/useFetchState";
import deleteFromList from "./../util/deleteFromList";
import { useState, useEffect} from "react";
import getErrorModal from "./../util/getErrorModal";
import jwt_decode from "jwt-decode";



const imgnotfound = "https://cdn-icons-png.flaticon.com/512/5778/5778223.png";
const jwt = tokenService.getLocalAccessToken();


export default function AchievementList() {
    const [roles, setRoles] = useState([]);
    const [username, setUsername] = useState("");
    const jwt = tokenService.getLocalAccessToken();
    useEffect(() => {
        if (jwt) {
            setRoles(jwt_decode(jwt).authorities);
            setUsername(jwt_decode(jwt).sub);
        }
    }, [jwt]);

    
    const [achievements, setAchievements] = useFetchState(
        [],
        `/api/v1/achievements`,
        jwt
    );
    

    const [message, setMessage] = useState(null);
    const [visible, setVisible] = useState(false);
    const [alerts, setAlerts] = useState([]);
    const achievementList =
    achievements.map((a) => {
        return (
            <tr key={a.id} >
                 <td className="text-center">
                    <div style={{marginRight: "40px", marginBottom:"15px"}} >
                    <img src={a.badgeImage ? a.badgeImage : imgnotfound} alt={a.name} width="50px" />
                    </div>
                </td>
                <td className="text-center" colSpan="2">
                    <div style={{marginRight: "40px", marginBottom:"15px"}}>
                        {a.name}
                         <br />
                         <span style={{ fontSize: "13px" }}>{a.description}</span>
                     </div>
                    </td>
                <td className="text-center">
                    <div style={{marginRight: "40px", marginBottom:"15px"}}>
                    <Button outline color="" >
                        <Link
                            to={`/achievements/` + a.id} className="auth-button-eol-achievements-edit"
                            style={{ textDecoration: "none" }}>Edit</Link>
                    </Button>
                    </div>
                </td>
                <td className="text-center">
                    <div style={{marginRight: "40px", marginBottom:"15px"}} >
                    <Button outline color="" 
                        onClick={() =>
                            deleteFromList(
                                `/api/v1/achievements/${a.id}`,
                                a.id,
                                [achievements, setAchievements],
                                [alerts, setAlerts],
                                setMessage,
                                setVisible
                            )}>
                                <div className="auth-button-eol-achievements-delete">
                        Delete
                        </div>
                    </Button>
                    </div>
                </td>

            </tr>
        );
    });

    const modal = getErrorModal(setVisible, visible, message);
    return (
        <div className="home-page-container">
            <div className="hero-div">
                <h1 className="text-center">Achievements</h1>
                <div >
                        <tbody>{achievementList}</tbody>
                    <Button outline color="">
                        <Link to="/achievements/new" className="auth-button-eol-achievements-create" style={{ textDecoration: "none" }}>
                            Create Achievement
                        </Link>
                    </Button>
                </div>
            </div>
        </div>
    );
}