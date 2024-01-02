import React, { useEffect, useState } from "react";
import { Button } from "reactstrap";
import tokenService from "../../services/token.service";
import deleteFromList from "../../util/deleteFromList";
import getErrorModal from "../../util/getErrorModal";
import useFetchState from "../../util/useFetchState";

const Pagination = ({ friendshipsPerPage, totalFriendships, paginate, currentPage }) => {
    const pageNumbers = [];

    for (let i = 1; i <= Math.ceil(totalFriendships / friendshipsPerPage); i++) {
        pageNumbers.push(i);
    }

    const getPageStyle = (pageNumber) => {
        return {
            backgroundColor: '#343F4B',
            color: currentPage === pageNumber ? "#75FBFD" : '#EF87E0',
            border: 'none',
            padding: '5px 10px',
            margin: '0 5px',
            borderRadius: '5px',
            cursor: 'pointer'
        };
    };

    return (
        <nav>
            <ul className='pagination'>
                {pageNumbers.map(number => (
                    <li key={number} className='page-item'>
                        <a
                            onClick={(e) => {
                                e.preventDefault();
                                paginate(number);
                            }}
                            href="!#"
                            style={getPageStyle(number)}
                            className='page-link'
                        >
                            {number}
                        </a>
                    </li>
                ))}
            </ul>
        </nav>
    );
};

export default function FriendshipList() {
    const jwt = tokenService.getLocalAccessToken();
    const user = tokenService.getUser();
    const [friendships, setFriendships] = useFetchState(null, `/api/v1/friendships/players/${user.id}/ACCEPTED`, jwt);
    const [currentPage, setCurrentPage] = useState(1);
    const [friendshipsPerPage] = useState(5);

    const [message, setMessage] = useState("");
    const [visible, setVisible] = useState(false);
    const [alerts, setAlerts] = useState([]);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await fetch(`/api/v1/friendships/players/${user.id}/ACCEPTED`, {
                    headers: {
                        Authorization: `Bearer ${jwt}`,
                    },
                });
                const data = await response.json();
                setFriendships(data);
            } catch (error) {
                setMessage("Error fetching friendships data");
                setVisible(true);
            }
        };
        fetchData();
    }, [jwt, setFriendships, user.id]);

    const indexOfLastFriendship = currentPage * friendshipsPerPage;
    const indexOfFirstFriendship = indexOfLastFriendship - friendshipsPerPage;
    const currentFriendships = friendships ? friendships.slice(indexOfFirstFriendship, indexOfLastFriendship) : [];
    const paginate = pageNumber => setCurrentPage(pageNumber);

    const modal = getErrorModal(setVisible, visible, message);

    const displayUserDetails = (friendship) => {
        const isSender = friendship.sender.id === user.id;
        const otherUser = isSender ? friendship.receiver : friendship.sender;

        return (
            <div key={friendship.id} style={{ display: 'flex', justifyContent: 'space-between', width: '100%', padding: '10px', borderBottom: '1px solid #ddd', alignItems: 'center' }}>
                <span style={{ flex: 1, textAlign: 'center', paddingLeft: '10px' }}>{otherUser.nickname}</span>
                <span style={{ flex: 1, textAlign: 'center' }}>
                    <img src={otherUser.avatar} alt={`${otherUser.nickname}'s avatar`} style={{ borderRadius: "50%", width: "40px", height: "40px" }} />
                </span>
                <Button
                    aria-label={"delete-" + friendship.id}
                    size="sm"
                    color="danger"
                    onClick={() => deleteFromList(
                        `/api/v1/friendships/${friendship.id}`,
                        friendship.id,
                        [friendships, setFriendships],
                        [alerts, setAlerts],
                        setMessage,
                        setVisible
                    )}
                >
                    Delete
                </Button>
            </div>
        );
    };

    return (
        <div className="home-page-container">
            <div className="hero-div">
                <h1 style={{ textAlign: 'center', color: "#EF87E0" }}>Friendships</h1>
                <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center', width: '100%' }}>
                    <div style={{ display: 'flex', width: '100%', padding: '10px', justifyContent: 'space-between' }}>
                        <span style={{ flex: 3, textAlign: 'center' }}>Nickname</span>
                        <span style={{ flex: 2, textAlign: 'center' }}>Avatar</span>
                        <span style={{ flex: 1.5, textAlign: 'center' }}></span> 
                    </div>
                    {currentFriendships.length > 0 ? (
                        currentFriendships.map((friendship) => displayUserDetails(friendship))
                    ) : (
                        <div style={{ textAlign: 'center', width: '100%' }}>You don´t have any friend yet</div>
                    )}
                </div>
                <Pagination
                    friendshipsPerPage={friendshipsPerPage}
                    totalFriendships={friendships ? friendships.length : 0}
                    paginate={paginate}
                    currentPage={currentPage}
                />
                {modal}
                <div style={{ width: '100%', display: 'flex', justifyContent: 'flex-end' }}>
                <Button 
                className="auth-button-eol-edit"
                color="warning" 
                size='lg' 
                style={{ marginRight: '10px' }}
                >
                    Pending
                </Button>
                <Button 
                className="auth-button-eol-create"
                color="success" 
                size='lg' 
                >
                    Create
                    </Button>
                </div>
            </div>
        </div>
    );
};