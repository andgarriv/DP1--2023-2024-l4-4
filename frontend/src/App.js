import jwt_decode from "jwt-decode";
import React, { useEffect, useState } from 'react';
import { ErrorBoundary } from "react-error-boundary";
import { Route, Routes, useLocation } from 'react-router-dom';
import AppNavbar from "./AppNavbar";
import GameNavbar from "./GameNavbar";
import AchievementEditAdmin from "./admin/achievements/AchievementEditAdmin";
import AchievementListAdmin from "./admin/achievements/AchievementListAdmin";
import FriendshipListAdmin from "./admin/friendships/FriendshipListAdmin";
import GameBoardAdmin from "./admin/games/GameBoardAdmin";
import GameListAdmin from "./admin/games/GameListAdmin";
import PlayerListAdmin from "./admin/players/PlayerListAdmin";
import Login from "./auth/login";
import Logout from "./auth/logout";
import Register from "./auth/register";
import Home from "./home";
import AchievementListPlayer from "./player/achievements/AchievementListPlayer";
import FriendshipCreatePlayer from "./player/friendships/FriendshipCreatePlayer";
import FriendshipListPlayer from "./player/friendships/FriendshipListPlayer";
import GameBoardPlayer from "./player/games/GameBoardPlayer";
import GameListPlayer from "./player/games/GameListPlayer";
import GameNewPlayer from "./player/games/GameNewPlayer";
import ProfileEditPlayer from "./player/profile/ProfileEditPlayer";
import ProfileListPlayer from "./player/profile/ProfileListPlayer";
import StatsSwitcher from "./player/stats/statsSwitch";
import PrivateRoute from "./privateRoute";
import SwaggerDocs from "./public/swagger";
import RulesViewer from "./rules";
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
          <Route path="/achievements" exact={true} element={<PrivateRoute><AchievementListAdmin /></PrivateRoute>} />
          <Route path="/achievements/:achievementId" exact={true} element={<PrivateRoute><AchievementEditAdmin /></PrivateRoute>} />
          <Route path="/games" exact={true} element={<PrivateRoute><GameListAdmin /></PrivateRoute>} />
          <Route path="/players" exact={true} element={<PrivateRoute><PlayerListAdmin /></PrivateRoute>} />
          <Route path="/friendships" exact={true} element={<PrivateRoute><FriendshipListAdmin /></PrivateRoute>} />
          <Route path="/games/:id" exact={true} element={<PrivateRoute><GameBoardAdmin /></PrivateRoute>} />
        </>)
    }
    if (role === "PLAYER") {
      playerRoutes = (
        <>
          <Route path="/games" exact={true} element={<PrivateRoute><GameListPlayer /></PrivateRoute>} />
          <Route path="/profile" exact={true} element={<PrivateRoute><ProfileListPlayer /></PrivateRoute>} />
          <Route path="/profile/edit" exact={true} element={<PrivateRoute><ProfileEditPlayer /></PrivateRoute>} />
          <Route path="/achievements" exact={true} element={<PrivateRoute><AchievementListPlayer /></PrivateRoute>} />
          <Route path="/achievements/:achievementId" exact={true} element={<PrivateRoute><AchievementListPlayer /></PrivateRoute>} />
          <Route path="/stats" exact={true} element={<PrivateRoute><StatsSwitcher /></PrivateRoute>} />
          <Route path="/play" exact={true} element={<PrivateRoute><GameNewPlayer /></PrivateRoute>} />
          <Route path="/games/:id" exact={true} element={<PrivateRoute><GameBoardPlayer /></PrivateRoute>} />
          <Route path="/friendships" exact={true} element={<PrivateRoute><FriendshipListPlayer /></PrivateRoute>} />
          <Route path="/friendships/create" exact={true} element={<PrivateRoute><FriendshipCreatePlayer /></PrivateRoute>} />
        </>)
      gameRoutes = (
        <>
          <Route path="/games/:id" exact={true} element={<PrivateRoute><GameBoardPlayer /></PrivateRoute>} />
          <Route path="/rulesInGame" element={<RulesViewer />} />
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
    const gamePaths = ['/games/:id'];
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
          <Route path="/docs" element={<SwaggerDocs />} />
          <Route path="/rules" element={<RulesViewer />} />
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
