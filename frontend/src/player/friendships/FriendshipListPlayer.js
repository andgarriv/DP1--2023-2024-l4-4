import React, { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
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
            margin: '10px 5px',
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
    const [friendshipType, setFriendshipType] = useState("ACCEPTED");
    const [friendships, setFriendships] = useFetchState(null, `/api/v1/players/${user.id}/friendships/${friendshipType}`, jwt);
    const [currentPage, setCurrentPage] = useState(1);
    const [friendshipsPerPage] = useState(5);

    const [message, setMessage] = useState("");
    const [visible, setVisible] = useState(false);
    const [alerts, setAlerts] = useState([]);

    let navigate = useNavigate();

    const handleClick = () => {
        navigate("/friendships/create");
    };

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await fetch(`/api/v1/players/${user.id}/friendships/${friendshipType}`, {
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
    }, [jwt, user.id, friendshipType, setFriendships]);

    const sortedPendingRequest = friendships ? [...friendships].sort((a, b) => {
        const isAReceiver = a.receiver.id === user.id;
        const isBReceiver = b.receiver.id === user.id;
        if (isAReceiver && !isBReceiver && a.friendState === "PENDING")
            return -1;
        else if (!isAReceiver && isBReceiver && b.friendState === "PENDING")
            return 1;
        else
            return 0;
    }) : [];

    const indexOfLastFriendship = currentPage * friendshipsPerPage;
    const indexOfFirstFriendship = indexOfLastFriendship - friendshipsPerPage;
    const currentFriendships = friendships ? sortedPendingRequest.slice(indexOfFirstFriendship, indexOfLastFriendship) : [];
    const paginate = pageNumber => setCurrentPage(pageNumber);

    const modal = getErrorModal(setVisible, visible, message);

    const updateFriendshipStatus = async (friendshipId, senderId, receiverId, friendState) => {
        try {
            const response = await fetch(`/api/v1/friendships/${friendshipId}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${jwt}`,
                },
                body: JSON.stringify({
                    sender: senderId,
                    receiver: receiverId,
                    friendship_state: friendState
                })
            });

            if (response.ok) {
                setFriendships(friendships.filter(friendship => friendship.id !== friendshipId));
                setMessage(`Friendship ${friendState.toLowerCase()} successfully`);
            } else {
                throw new Error('Failed to update friendship status');
            }
        } catch (error) {
            setMessage(`Error: ${error}`);
            setVisible(true);
        }
    };

    const columnStyles = {
        pending: {
            nickname: { flex: 3, textAlign: 'center', paddingLeft: '10px' },
            avatar: { flex: 2, textAlign: 'center' },
            actions: { flex: 3, textAlign: 'center' } 
        },
        accepted: {
            nickname: { flex: 3, textAlign: 'center', paddingLeft: '10px' }, 
            avatar: { flex: 2, textAlign: 'center' }, 
            actions: { flex: 1.5, textAlign: 'center' }
        }
    };

    const displayUserDetails = (friendship) => {
        const isSender = friendship.sender.id === user.id;
        const otherUser = isSender ? friendship.receiver : friendship.sender;

        const currentColumnStyle = friendshipType === "PENDING" ? columnStyles.pending : columnStyles.accepted;

        return (
            <div key={friendship.id} style={{ display: 'flex', justifyContent: 'space-between', width: '100%', padding: '10px', borderBottom: '1px solid #ddd', alignItems: 'center' }}>
                <span style={currentColumnStyle.nickname}>{otherUser.nickname}</span>
                <span style={currentColumnStyle.avatar}>
                    <img src={otherUser.avatar} alt={`${otherUser.nickname}'s avatar`} style={{ borderRadius: "50%", width: "40px", height: "40px" }} />
                </span>
                <span style={currentColumnStyle.actions}>
                    {friendshipType === "PENDING" && !isSender ? (
                        <div>
                            <Button
                                aria-label={"update-" + friendship.id}
                                size="sm"
                                style={{ marginRight: '5px' }}
                                className="positive-button"
                                onClick={() => updateFriendshipStatus(friendship.id, friendship.sender.id, friendship.receiver.id, "ACCEPTED")}
                            >
                                Accept
                            </Button>

                            <Button
                                aria-label={"delete-" + friendship.id}
                                size="sm"
                                color="danger"
                                className="negative-button"
                                onClick={() => deleteFromList(
                                    `/api/v1/friendships/${friendship.id}`,
                                    friendship.id,
                                    [friendships, setFriendships],
                                    [alerts, setAlerts],
                                    setMessage,
                                    setVisible
                                )}
                            >
                                Deny
                            </Button>
                        </div>
                    ) : (
                        <Button
                            aria-label={"delete-" + friendship.id}
                            size="sm"
                            color="danger"
                            className="negative-button"
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
                    )}
                </span>
            </div>
        );
    };

    return (
        <div className="home-page-container">
            <div className="hero-div">
                <h1> {friendshipType === "ACCEPTED" ? "Friendships" : "Pending invites"}</h1>
                <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center', width: '100%' }}>
                    <div style={{ display: 'flex', width: '100%', padding: '10px', justifyContent: 'space-between', color: "#EF87E0" }}>
                        <span style={friendshipType === "PENDING" ? columnStyles.pending.nickname : columnStyles.accepted.nickname}>
                            {currentFriendships.length > 0 ? "Nickname" : ""}
                        </span>
                        <span style={friendshipType === "PENDING" ? columnStyles.pending.avatar : columnStyles.accepted.avatar}>
                            {currentFriendships.length > 0 ? "Avatar" : ""}
                        </span>
                        <span style={friendshipType === "PENDING" ? columnStyles.pending.actions : columnStyles.accepted.actions}>
                        </span>
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
                        className="normal-button"
                        size='lg'
                        style={{ marginRight: '10px' }}
                        onClick={() => {
                            setFriendshipType(friendshipType === "PENDING" ? "ACCEPTED" : "PENDING");
                            setCurrentPage(1)
                        }}
                    >
                        {friendshipType === "PENDING" ? "Friendships" : "Pending"}
                    </Button>
                    <Button
                        className="positive-button"
                        size='lg'
                        onClick={handleClick}
                    >
                        <Link to="/friendships/create" style={{ textDecoration: "none", color: "#4BB25B" }}>
                        </Link>
                        Create
                    </Button>
                </div>
            </div>
        </div>
    );
};