import React, { useEffect, useState } from "react";
import { Button, Input } from "reactstrap";
import tokenService from "../../services/token.service";
import getErrorModal from "../../util/getErrorModal";
import useFetchState from "../../util/useFetchState";

const user = tokenService.getUser();

export default function EditPlayerProfile() {
  const jwt = tokenService.getLocalAccessToken();
  const [player, setPlayer] = useFetchState(null, `/api/v1/players/${user.id}`, jwt);
  const [message, setMessage] = useState("");
  const [visible, setVisible] = useState(false);

  const [name, setName] = useState("");
  const [nameError, setNameError] = useState("");
  const [surname, setSurname] = useState("");
  const [surnameError, setSurnameError] = useState("");
  const [nickname, setNickname] = useState("");
  const [avatar, setAvatar] = useState("");
  const [email, setEmail] = useState("");
  const [avatarError, setAvatarError] = useState("");
  const [birthDate, setBirthDate] = useState("");

  const scrollbarStyles = {
    maxHeight: "900px",
    overflowY: "auto",
    scrollbarWidth: "thin", 
    scrollbarColor: "#222222",
    WebkitOverflowScrolling: "touch",
    "&::-webkit-scrollbar": {
      width: "1px",
    },
    "&::-webkit-scrollbar-thumb": {
      backgroundColor: "#222",
      borderRadius: "1px",
    },
    "&::-webkit-scrollbar-thumb:hover": {
      backgroundColor: "#555",
    },
    "&::-webkit-scrollbar-track": {
      backgroundColor: "transparent",
    },
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
        setName(data.name);
        setSurname(data.surname);
        setNickname(data.nickname);
        setAvatar(data.avatar);
        setEmail(data.email);
        setBirthDate(data.birthDate);
        
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

  const handleNameChange = (event) => {
    const newName = event.target.value;
    setName(newName);
    if (newName.length < 3 || newName.length > 15) {
      setNameError("Name must be between 3 and 15 characters");
    } else {
      setNameError("");
    }
  };

  const handleSurnameChange = (event) => {
    const newSurname = event.target.value;
    setSurname(newSurname);
    if (newSurname.length < 3 || newSurname.length > 15) {
      setSurnameError("Surname must be between 3 and 15 characters");
    }
    else {
      setSurnameError("");
    }
  };

  const handleAvatarChange = (event) => {
    const newAvatar = event.target.value;
    setAvatar(newAvatar);
    const avatarPattern = /^https?:\/\/.*\.(jpg|png|jpeg)$/i;
    if(newAvatar.length > 0 && !avatarPattern.test(newAvatar)) {
      setAvatarError("Invalid avatar URL");
    } else {
      setAvatarError("");
    }
  };

  const handleSaveChanges = async () => {
    if (avatarError || nameError || surnameError) {
      setMessage("Invalid data");
      setVisible(true);
      return;
    }
    try {
      const response = await fetch(`/api/v1/players/${user.id}`, {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${jwt}`,
        },
        body: JSON.stringify({
          id: user.id,
          name,
          surname,
          nickname,
          avatar,
          password: player.password,
          email,
          birthDate: player.birthDate,
          authority: player.authority
        }),
      }
    );

      if (response.ok) {
        window.location.href = "/profile";
      } else {
        const data = await response.json();
        setMessage(data.message || "Error updating player");
        setVisible(true);
      }
    } catch (error) {
      setMessage("Error updating player");
      setVisible(true);
    }
  };

  return (
    
    <div className="home-page-container">
     <div className="scrollable-content" style={scrollbarStyles}> 
      <div className="hero-div">
        <h1 className="text-center">Edit Profile</h1>
        {player ? (
          <div>
            <p style={{ marginBottom: "-2px", color: "white" }}>Name:</p>
            <Input style={{ marginBottom: "10px" }} type="text" value={name} onChange={handleNameChange} />
            {nameError && <p style={{ color: "red" }}>{nameError}</p>}
            <p style={{ marginBottom: "-2px", color: "white" }}>Surname:</p>
            <Input style={{ marginBottom: "10px" }} type="text" value={surname} onChange={handleSurnameChange} />
            {surnameError && <p style={{ color: "red" }}>{surnameError}</p>}
            <p style={{ marginBottom: "-2px", color: "white" }}>Nickname:</p>
            <Input readOnly style={{ marginBottom: "10px", background: "transparent", color: "white", border: "none"}} type="text" value={nickname}/>
            <p style={{ marginBottom: "-2px", color: "white" }}>Email:</p>
            <Input readOnly style={{ marginBottom: "10px", background: "transparent", color: "white", border: "none" }} type="text" value={email}/>
            <p style={{ marginBottom: "-2px", color: "white" }}>Birthdate:</p>
            <p readOnly style={{ marginBottom: "10px", background: "transparent", color: "white", border: "none" }} type="text" value={birthDate}>{formatDate(player.birthDate)}</p>
            <p style={{ marginBottom: "-2px", color: "white" }}>Avatar:</p>
            <Input style={{ marginBottom: "20px" }} type="text" value={avatar} onChange={handleAvatarChange} />
            {avatarError && <p style={{ color: "red" }}>{avatarError}</p>}
            {!avatarError && avatar && (
            <div style={{ textAlign: "center", marginTop: "10px" }}>
              <img
                src={avatar}
                alt="avatar"
                style={{ width: "100px", height: "100px", borderRadius: "50%" }}
              />
            </div>
          )}
          </div>
        ) : (
          <p style={{ color: "white" }}>Loading player data...</p>
        )}
        <div style={{ display: "flex", justifyContent: "space-between", marginTop: "20px" }}>
        <Button 
        className="negative-button"
        size="lg"
        onClick={handleSaveChanges}
        >
          Cancel
        </Button>
        <div style={{ width: "10px" }}></div>
        <Button 
        className="positive-button"
        size="lg"
        onClick={handleSaveChanges}
        >
          Save Changes
        </Button>
        </div>
        {modal}
        </div>
      </div>
    </div>
  );
}
