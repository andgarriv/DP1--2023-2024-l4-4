import { Button, Modal } from "reactstrap";
import '../../../static/css/home/home.css';

function FinishPopup({ setVisible, visible, winner }) {

    return (
        <Modal className="exit-popup" isOpen={visible} toggle={() => setVisible(false)} centered>
            <div className="modal-content-win">
                <p>{winner} Wins!</p>
                <div className="modal-footer-custom">
                    <Button
                        className="win-button"
                        size="lg"
                        onClick={() => window.location.href = '/'}>Go Home</Button>
                </div>
            </div>
        </Modal>
    );
}

export default FinishPopup;
