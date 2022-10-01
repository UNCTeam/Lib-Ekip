package fr.teamunc.ekip_unclib.models;

import lombok.Getter;
import fr.teamunc.base_unclib.models.jsonEntities.UNCEntitiesContainer;

import java.util.ArrayList;

public class UNCTeamContainer extends UNCEntitiesContainer{

    @Getter
    private ArrayList<UNCTeam> teams = new ArrayList<>();

    public UNCTeam getTeam(String name) {
        return teams.stream().filter(team -> team.getName().equals(name)).findFirst().orElse(null);
    }

    public void addTeam(UNCTeam team) {
        this.teams.add(team);
    }

    public void removeTeam(UNCTeam team) {
        this.teams.remove(team);
    }

    public boolean removeTeam(String teamName) {
        return this.teams.removeIf(team -> team.getName().equals(teamName));
    }

    public UNCTeamContainer() {
        super();
    }
}
