import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { Button } from "reactstrap";
import tokenService from "../../services/token.service";
import getErrorModal from "../../util/getErrorModal";
import useFetchState from "../../util/useFetchState";

const imgNotFound = "https://cdn-icons-png.flaticon.com/512/5556/5556468.png";
const user = tokenService.getUser();


export default function PlayerProfile() {
  const jwt = tokenService.getLocalAccessToken();
  const [player, setPlayer] = useFetchState(null, `/api/v1/players/{id}`, jwt);
  const [message, setMessage] = useState("");
  const [visible, setVisible] = useState(false);

  let navigate = useNavigate();

  const handleSubmit = () => {
    navigate("/profile/edit");
  };


  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await fetch(`/api/v1/players/${user.id}`, {
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

  const formatDate = (date) => {
    const options = { year: 'numeric', month: '2-digit', day: '2-digit' };
    return new Date(date).toLocaleDateString(undefined, options);
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
              <p style={{ color: "white" }}>Birthdate:</p>
            </div>
            <div style={{ textAlign: "center", marginInlineStart: "25px" } }>
              <p style={{ color: "white" }}>{player.name || 'No name provided'}</p>
              <p style={{ color: "white" }}>{player.surname || 'No surname provided'}</p>
              <p style={{ color: "white" }}>{player.nickname || 'No nickname provided'}</p>
              <p style={{ color: "white" }}>{player.email || 'No email provided'}</p>
              <p style={{ color: "white" }}>{formatDate(player.birthDate) || 'No birthdate provided'}</p>
            </div>
            </div>
          </div>
        ) : (
          <p style={{color: "white"}}>Loading player data...</p>
        )}
        <Button 
        className="normal-button"
        size="lg"
        onClick={handleSubmit}
        >
        Edit Profile
        </Button>
        {modal}
      </div>
    </div>
  );
}
