import React, { useState } from 'react';
import PlayerStats from './StatsListPlayer';
import GlobalStats from './statsListGlobal';

export default function StatsSwitcher() {
    const [showGlobalStats, setShowGlobalStats] = useState(false);

    const toggleStatsView = () => {
        setShowGlobalStats(!showGlobalStats);
    };

    return (
        <div className="home-page-container" style={{ color: 'white', backgroundColor: 'black', padding: '20px' }}>
            <div className="hero-div">
                {showGlobalStats ? <GlobalStats /> : <PlayerStats />}
                <button
                    className="normal-button"
                    size="lg"
                    style={{ marginTop: "5%", borderRadius: "5px" }}
                    onClick={toggleStatsView}>
                    {showGlobalStats ? 'Personal Stats' : 'Global Stats'}
                </button>
            </div>
        </div>
    );
}
