import { Button, Modal } from "reactstrap";
import './static/css/home/home.css';

function OnGoingGamePopup({ setVisible, visible, gameId, player }) {

    return (
        <Modal className="exit-popup" isOpen={visible} toggle={() => setVisible(false)} centered>
            <div className="modal-content-ongoing">
                <p>GAME PENDING. DO YOU WANT TO JOIN IT?</p>
                {player && <p style={{ color: "#FFFFFF" }}>{player.nickname}</p>}
                {player && <img
                    src={player.avatar}
                    alt="avatar"
                    style={{
                        borderRadius: "50%",
                        width: "75px",
                        height: "75px",
                    }}
                />}
                <div className="modal-footer-custom">
                    <Button
                        className="negative-button"
                        size="lg"
                        onClick={() => setVisible(false)}>NO</Button>
                    <span style={{ display: "block", margin: "5px 20px" }}></span>
                    <Button
                        className="positive-button"
                        size="lg"
                        onClick={() => window.location.href = `/games/${gameId}`}>YES</Button>
                </div>
            </div>
        </Modal>
    );
}

export default OnGoingGamePopup;
