package fr.teamunc.ekip_unclib.models;

import lombok.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@Builder(toBuilder = true)
public class UNCTeam{
    @Getter
    private IUNCTeamInformation additionalInformation;
    @Getter
    private String color;
    @Getter @NonNull
    private String name;
    @Getter
    private String prefix;
    @Getter
    private Collection<UUID> players;
    @Getter
    private UUID teamId;

    public <T> T getAdditionalInformation(String key, Class<T> clazz) throws ClassCastException {
        return clazz.cast(this.additionalInformation.get(key));
    }

    public static UNCTeamBuilder builder(String name) {
        return new UNCTeamBuilder().name(name).players(new ArrayList<>());
    }

    public void addPlayer(UUID uuid) {
        this.players.add(uuid);
    }

    public void removePlayer(UUID uuid) {
        this.players.remove(uuid);
    }
}
