import { Button, Modal } from "reactstrap";
import './static/css/home/home.css';

function OnGoingGamePopup({ setVisible, visible, gameId }) {

    return (
        <Modal className="exit-popup" isOpen={visible} toggle={() => setVisible(false)} centered>
            <div className="modal-content-ongoing">
                <p>GAME PENDING. DO YOU WANT TO JOIN IT?</p>
                <div className="modal-footer-custom">
                    <Button
                        className="negative-button"
                        size="lg"
                        onClick={() => setVisible(false)}>NO</Button>
                    <span style={{ display: "block", margin: "5px 20px" }}></span>
                    <Button
                        className="positive-button"
                        size="lg"
                        onClick={() =>window.location.href = `/games/${gameId}`}>YES</Button>
                </div>
            </div>
        </Modal>
    );
}

export default OnGoingGamePopup;
