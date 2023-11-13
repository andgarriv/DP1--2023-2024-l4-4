import React, { useEffect, useState } from "react";
import { Button } from "reactstrap";
import { Link } from "react-router-dom";
import tokenService from "../services/token.service";
import useFetchState from "../util/useFetchState";
import getErrorModal from "../util/getErrorModal";

const imgNotFound = "https://cdn-icons-png.flaticon.com/512/5778/5778223.png";
const user = tokenService.getUser();


export default function PlayerProfile() {
  const jwt = tokenService.getLocalAccessToken();
  const [player, setPlayer] = useFetchState(null, `/api/v1/player/{id}`, jwt);
  const [message, setMessage] = useState("");
  const [visible, setVisible] = useState(false);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await fetch(`/api/v1/player/${user.id}`, {
          headers: {
            Authorization: `Bearer ${jwt}`,
          },
        });
        const data = await response.json();
        setPlayer(data);
      } catch (error) {
        setMessage("Error fetching player data");
        setVisible(true);
      }
    };
    fetchData();
  }, [jwt, setPlayer]);

  const modal = getErrorModal(setVisible, visible, message);

  const date_format = (date) => {
    // eslint-disable-next-line no-new-wrappers
    const d = new String(date);
    const year = d.substring(0, 4);
    const month = d.substring(5, 7);
    const day = d.substring(8, 10);
    return `${day}/${month}/${year}`;
    };

  return (
    <div className="home-page-container">
      <div className="hero-div">
        <h1 className="text-center">My Profile</h1>
        {player ? (
          <div>
            <div style={{ textAlign: "center", marginBottom: "15px" }}>
              <img 
                src={player.avatar || imgNotFound} 
                alt="avatar" 
                className="avatar" 
                style={{ borderRadius: "50%" }}
                width="100px" />
            </div>
            <div style={{ display: "flex", justifyContent: "space-between" }}>
            <div style={{ textAlign: "left" }}>
              <p style={{ color: "white" }}>Name:</p>
              <p style={{ color: "white" }}>Surname:</p>
              <p style={{ color: "white" }}>Nickname:</p>
              <p style={{ color: "white" }}>Email:</p>
              <p style={{ color: "white" }}>Birth Date:</p>
            </div>
            <div style={{ textAlign: "center", marginInlineStart: "25px" } }>
              <p style={{ color: "white" }}>{player.name}</p>
              <p style={{ color: "white" }}>{player.surname}</p>
              <p style={{ color: "white" }}>{player.nickname}</p>
              <p style={{ color: "white" }}>{player.email}</p>
              <p style={{ color: "white" }}>{date_format(player.birthDate)}</p>
            </div>
            </div>
          </div>
        ) : (
          <p style={{color: "white"}}>Loading player data...</p>
        )}
        <Button outline color="warning">
          <Link to="/profile/edit" className="btn sm" style={{ textDecoration: "none", color: "white"}}>
            Edit Profile
          </Link>
        </Button>
        {modal}
      </div>
    </div>
  );
}
