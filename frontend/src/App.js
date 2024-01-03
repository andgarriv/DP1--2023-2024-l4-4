import jwt_decode from "jwt-decode";
import React, { useEffect, useState } from 'react';
import { ErrorBoundary } from "react-error-boundary";
import { Route, Routes, useLocation } from 'react-router-dom';
import AppNavbar from "./AppNavbar";
import GameNavbar from "./GameNavbar";
import AchievementEdit from "./achievement/achievementEdit";
import AchievementList from "./achievement/achievementList";
import AchievementPlayer from "./achievement/achievementListPlayer";
import FriendshipListAdmin from "./admin/friendships/FriendshipListAdmin";
import PlayerListAdmin from "./admin/players/PlayerListAdmin";
import UserEditAdmin from "./admin/users/UserEditAdmin";
import UserListAdmin from "./admin/users/UserListAdmin";
import Login from "./auth/login";
import Logout from "./auth/logout";
import Register from "./auth/register";
import AdminBoard from "./games/adminBoard";
import AdminGamesList from "./games/adminGamesList";
import Board from "./games/board";
import NewGame from "./games/newGame";
import PlayerGamesList from "./games/playerGamesList";
import Home from "./home";
import FriendshipListPlayer from "./player/friendships/FriendshipListPlayer";
import PlayerProfile from "./player/playerProfile";
import PlayerProfileEdit from "./player/playerProfileEdit";
import PlayerStats from "./player/playerStats";
import PrivateRoute from "./privateRoute";
import PlanList from "./public/plan";
import SwaggerDocs from "./public/swagger";
import PDFViewer from "./rules";
import tokenService from "./services/token.service";


function ErrorFallback({ error, resetErrorBoundary }) {
  return (
    <div role="alert">
      <p>Something went wrong:</p>
      <pre>{error.message}</pre>
      <button onClick={resetErrorBoundary}>Try again</button>
    </div>
  )
}

function App() {
  const jwt = tokenService.getLocalAccessToken();
  let roles = []
  if (jwt) {
    roles = getRolesFromJWT(jwt);
  }

  function getRolesFromJWT(jwt) {
    return jwt_decode(jwt).authorities;
  }

  let adminRoutes = <></>;
  let ownerRoutes = <></>;
  let userRoutes = <></>;
  let playerRoutes = <></>;
  let gameRoutes = <></>;
  let publicRoutes = <></>;

  roles.forEach((role) => {
    if (role === "ADMIN") {
      adminRoutes = (
        <>
          <Route path="/users" exact={true} element={<PrivateRoute><UserListAdmin /></PrivateRoute>} />
          <Route path="/users/:username" exact={true} element={<PrivateRoute><UserEditAdmin /></PrivateRoute>} />
          <Route path="/achievements" exact={true} element={<PrivateRoute><AchievementList /></PrivateRoute>} />
          <Route path="/achievements/:achievementId" exact={true} element={<PrivateRoute><AchievementEdit /></PrivateRoute>} />
          <Route path="/games" exact={true} element={<PrivateRoute><AdminGamesList /></PrivateRoute>} />
          <Route path="/players" exact={true} element={<PrivateRoute><PlayerListAdmin /></PrivateRoute>} />
          <Route path="/friendships" exact={true} element={<PrivateRoute><FriendshipListAdmin /></PrivateRoute>} />
          <Route path="/game/:id" exact={true} element={<PrivateRoute><AdminBoard /></PrivateRoute>} />
        </>)
    }
    if (role === "PLAYER") {
      playerRoutes = (
        <>
          <Route path="/games" exact={true} element={<PrivateRoute><PlayerGamesList /></PrivateRoute>} />
          <Route path="/profile" exact={true} element={<PrivateRoute><PlayerProfile /></PrivateRoute>} />
          <Route path="/profile/edit" exact={true} element={<PrivateRoute><PlayerProfileEdit /></PrivateRoute>} />
          <Route path="/achievements" exact={true} element={<PrivateRoute><AchievementPlayer /></PrivateRoute>} />
          <Route path="/achievements/:achievementId" exact={true} element={<PrivateRoute><AchievementPlayer /></PrivateRoute>} />
          <Route path="/stats" exact={true} element={<PrivateRoute><PlayerStats /></PrivateRoute>} />
          <Route path="/play" exact={true} element={<PrivateRoute><NewGame /></PrivateRoute>} />
          <Route path="/game/:id" exact={true} element={<PrivateRoute><Board /></PrivateRoute>} />
          <Route path="/friendships" exact={true} element={<PrivateRoute><FriendshipListPlayer /></PrivateRoute>} />
        </>)
      gameRoutes = (
        <>
          <Route path="/game/:id" exact={true} element={<PrivateRoute><Board /></PrivateRoute>} />
          <Route path="/rulesInGame" element={<PDFViewer />} />
        </>
      )
    }
  })
  if (!jwt) {
    publicRoutes = (
      <>
        <Route path="/register" element={<Register />} />
        <Route path="/login" element={<Login />} />
      </>
    )
  } else {
    userRoutes = (
      <>
        <Route path="/logout" element={<Logout />} />
        <Route path="/login" element={<Login />} />
      </>
    )
  }

  const location = useLocation();
  const [isGameRoute, setIsGameRoute] = useState(false);
  const [isRulesInGameRoute, setIsRulesInGameRoute] = useState(false);

  useEffect(() => {
    const gamePaths = ['/game/:id'];
    const rulesInGamePaths = ['/rulesInGame'];
    const currentPath = location.pathname;
    setIsGameRoute(gamePaths.some(path => currentPath.startsWith(path.replace(':id', ''))));
    setIsRulesInGameRoute(rulesInGamePaths.some(path => currentPath.startsWith(path)));
  }, [location]);

  return (
    <div>
      <ErrorBoundary FallbackComponent={ErrorFallback} >
        {(isGameRoute || isRulesInGameRoute) ? <GameNavbar /> : <AppNavbar />}
        <Routes>
          <Route path="/" exact={true} element={<Home />} />
          <Route path="/plans" element={<PlanList />} />
          <Route path="/docs" element={<SwaggerDocs />} />
          <Route path="/rules" element={<PDFViewer />} />
          {publicRoutes}
          {userRoutes}
          {adminRoutes}
          {ownerRoutes}
          {playerRoutes}
          {gameRoutes}
        </Routes>
      </ErrorBoundary>
    </div>
  );
}

export default App;
