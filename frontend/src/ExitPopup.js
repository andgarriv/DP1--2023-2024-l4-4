import { Button, Modal } from "reactstrap";
import './static/css/home/home.css';

function ExitPopup({ setVisible, visible }) {

    return (
        <Modal className="exit-popup" isOpen={visible} toggle={() => setVisible(false)} centered>
            <div className="modal-content-custom">
                <p>YOU ARE ABOUT TO EXIT.</p>
                <p>ARE YOU SURE?</p>
                <div className="modal-footer-custom">
                    <Button
                        className="negative-button"
                        size="lg"
                        onClick={() => setVisible(false)}>NO</Button>
                    <span style={{ display: "block", margin: "5px 20px" }}></span>
                    <Button
                        className="positive-button"
                        size="lg"
                        onClick={() => window.location.href = '/'}>YES</Button>
                </div>
            </div>
        </Modal>
    );
}

export default ExitPopup;
