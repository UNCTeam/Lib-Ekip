package fr.teamunc.ekip_unclib.controllers;

import fr.teamunc.ekip_unclib.models.UNCTeam;
import fr.teamunc.ekip_unclib.models.UNCTeamContainer;

import java.util.ArrayList;

public class UNCTeamController{

    private UNCTeamContainer container;

    public ArrayList<UNCTeam> getTeams() {
        return container.getTeams();
    }

    public UNCTeam getTeam(String name) {
        return getTeams().stream().filter(team -> team.getName().equals(name)).findFirst().orElse(null);
    }

    public void addTeam(UNCTeam team) {
        getTeams().add(team);
    }

    public void removeTeam(UNCTeam team) {
        getTeams().remove(team);
    }

    public boolean removeTeam(String teamName) {
        return getTeams().removeIf(team -> team.getName().equals(teamName));
    }

    public UNCTeamController(UNCTeamContainer uncTeamContainer) {
        this.container = uncTeamContainer;
    }

    public void save(String fileName) {
        container.save(fileName);
    }
}
