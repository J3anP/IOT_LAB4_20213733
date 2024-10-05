package com.example.tele_futbol.DTO;

import com.example.tele_futbol.entity.League;

import java.io.Serializable;
import java.util.List;

public class LeagueDTO implements Serializable {
    List<League> leagues;
    List<League> countries;

    public List<League> getLeagues() {
        return leagues;
    }

    public void setLeagues(List<League> leagues) {
        this.leagues = leagues;
    }

    public List<League> getCountries() {
        return countries;
    }

    public void setCountries(List<League> countries) {
        this.countries = countries;
    }
}
