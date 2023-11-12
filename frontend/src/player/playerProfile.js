import {
    Table, Button
} from "reactstrap";
import { Link } from "react-router-dom";
import tokenService from "../services/token.service";
import useFetchState from "../util/useFetchState";
import deleteFromList from "../util/deleteFromList";
import { useState } from "react";
import getErrorModal from "../util/getErrorModal";

const imgnotfound = "https://cdn-icons-png.flaticon.com/512/5778/5778223.png";
const jwt = tokenService.getLocalAccessToken();

export default function AchievementList() {
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
                    <img src={a.badgeImage ? a.badgeImage : imgnotfound} alt={a.name} width="50px" />
                </td>
                <td className="text-center" colSpan="2">
                        {a.name}
                         <br />
                     {a.description}
                    </td>
                <td className="text-center">
                    <Button outline color="warning" >
                        <Link
                            to={`/achievements/` + a.id} className="btn sm"
                            style={{ textDecoration: "none" }}>Edit</Link>
                    </Button>
                </td>
                <td className="text-center">
                    <Button outline color="danger"
                        onClick={() =>
                            deleteFromList(
                                `/api/v1/achievements/${a.id}`,
                                a.id,
                                [achievements, setAchievements],
                                [alerts, setAlerts],
                                setMessage,
                                setVisible
                            )}>
                        Delete
                    </Button>
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
                    <Button outline color="success">
                        <Link to="/achievements/new" className="btn sm" style={{ textDecoration: "none" }}>
                            Create Achievement
                        </Link>
                    </Button>
                </div>
            </div>
        </div>
    );
}