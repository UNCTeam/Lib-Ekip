package fr.teamunc.ekip_unclib.models;

import fr.teamunc.base_unclib.models.jsonEntities.UNCEntitiesContainer;
import lombok.Getter;

import java.util.ArrayList;

public class UNCTeamContainer extends UNCEntitiesContainer {
    @Getter
    private ArrayList<UNCTeam> teams = new ArrayList<>();
}
