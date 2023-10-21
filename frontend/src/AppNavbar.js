import React, { useState, useEffect } from 'react';
import { Navbar, NavbarBrand, NavLink, NavItem, Nav, NavbarToggler, Collapse, UncontrolledDropdown, DropdownToggle, DropdownMenu, DropdownItem } from 'reactstrap';
import { Link } from 'react-router-dom';
import tokenService from './services/token.service';
import jwt_decode from "jwt-decode";
import './static/css/home/home.css';

function AppNavbar() {
    const [roles, setRoles] = useState([]);
    const [username, setUsername] = useState("");
    const jwt = tokenService.getLocalAccessToken();
    const [collapsed, setCollapsed] = useState(true);

    const toggleNavbar = () => setCollapsed(!collapsed);

    useEffect(() => {
        if (jwt) {
            setRoles(jwt_decode(jwt).authorities);
            setUsername(jwt_decode(jwt).sub);
        }
    }, [jwt])

    let adminLinks = <></>;
    let playerLinks = <></>;
    let userLogout = <></>;
    let publicLinks = <></>;

    roles.forEach((role) => {
        if (role === "ADMIN") {
            adminLinks = (
                <>
                    <NavItem>
                        <NavLink className="fuente" style={{ color: "#75FBFD" }} tag={Link} to="/playedgames">Games</NavLink>
                    </NavItem>
                    <NavItem>
                        <NavLink className="fuente" style={{ color: "#75FBFD" }} tag={Link} to="/players">Players</NavLink>
                    </NavItem>
                    <NavItem>
                        <NavLink className="fuente" style={{ color: "#75FBFD" }} tag={Link} to="/rules">Rules</NavLink>
                    </NavItem>
                    <span style={{ color: "gray", display: "inline-block", margin: "5px 10px" }}>|</span>

                </>
            )
        }
        if (role === "PLAYER") {
            playerLinks = (
                <>
                    <NavItem>
                        <NavLink className="fuente" style={{ color: "#75FBFD" }} tag={Link} to="/play">Play Now!</NavLink>
                    </NavItem>
                    <NavItem>
                        <NavLink className="fuente" style={{ color: "#75FBFD" }} tag={Link} to="/rules">Rules</NavLink>
                    </NavItem>
                    <span style={{ color: "gray", display: "inline-block", margin: "5px 10px" }}>|</span>
                    <UncontrolledDropdown nav inNavbar>
                        <DropdownToggle nav caret className="fuente" style={{ color: "#EF87E0" }}>
                            {username}
                        </DropdownToggle>
                        <DropdownMenu right style={{ backgroundColor: "#222222", textAlign: 'center' }}>
                            <DropdownItem style={{ borderBottom: '1px solid gray', padding: '10px' }}>
                                <NavLink className="fuente" style={{ color: "white" }} tag={Link} to="/profile">My Profile</NavLink>
                            </DropdownItem>
                            <DropdownItem style={{ borderBottom: '1px solid gray', padding: '10px' }}>
                                <NavLink className="fuente" style={{ color: "white" }} tag={Link} to="/achievements">Achievementes</NavLink>
                            </DropdownItem>
                            <DropdownItem>
                                <NavLink className="fuente" style={{ color: "white" }} tag={Link} to="/stats">Stats</NavLink>
                            </DropdownItem>
                        </DropdownMenu>
                    </UncontrolledDropdown>
                </>
            )

        }
    })

    if (!jwt) {
        publicLinks = (
            <>
                <NavItem>
                    <NavLink className="fuente" style={{ color: "#75FBFD" }} tag={Link} to="/rules">Rules</NavLink>
                </NavItem>
                <span style={{ color: "gray", display: "inline-block", margin: "5px 10px" }}>|</span>
                <NavItem>
                    <NavLink className="fuente" style={{ color: "#EF87E0" }} id="login" tag={Link} to="/login">Sign in</NavLink>
                </NavItem>
                <NavItem>
                    <NavLink className="fuente" style={{ color: "#EF87E0" }} id="register" tag={Link} to="/register">Register</NavLink>
                </NavItem>
            </>
        )
    } else {
        userLogout = (
            <>
                <NavItem className="d-flex">
                    <NavLink className="fuente" style={{ color: "#EF87E0" }} id="logout" tag={Link} to="/logout">Logout</NavLink>
                </NavItem>
            </>
        )

    }

    return (
        <div>
            <Navbar expand="md" dark color="dark">
                <NavbarBrand href="/">
                    <img alt="logo" src="/eol_logo.png" style={{ height: 30, width: 250 }} />
                </NavbarBrand>
                <NavbarToggler onClick={toggleNavbar} className="ms-2" />
                <Collapse isOpen={!collapsed} navbar>
                    <Nav className="ms-auto mb-2 mb-lg-0" navbar>
                        {adminLinks}
                        {playerLinks}
                        {publicLinks}
                        {userLogout}
                    </Nav>
                </Collapse>
            </Navbar>
        </div>
    );
}

export default AppNavbar;