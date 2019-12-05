package com.kolotilov.jplagweb.services;

import com.kolotilov.jplagweb.models.Match;

public class MatchPartService extends Match {

    private int matchId;

    public int getMatchId() {
        return matchId;
    }

    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }
}
