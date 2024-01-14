import React, { useState } from 'react';
import { Button } from 'reactstrap';
import PlayerStats from './StatsListPlayer';
import GlobalStats from './statsListGlobal';

export default function StatsSwitcher() {
    const [showGlobalStats, setShowGlobalStats] = useState(false);

    const toggleStatsView = () => {
        setShowGlobalStats(!showGlobalStats);
    };

    return (
        <div className="home-page-container" style={{ color: 'white', backgroundColor: 'black', padding: '20px' }}>
            <div className="hero-div" style={{height: "60%", width: "28%"}}>
                {showGlobalStats ? <GlobalStats /> : <PlayerStats />}
                <Button
                    className="normal-button"
                    size="lg"
                    style={{ marginTop: "5%", borderRadius: "5px" }}
                    onClick={toggleStatsView}>
                    {showGlobalStats ? 'Personal Stats' : 'Global Stats'}
                </Button>
            </div>
        </div>
    );
}
