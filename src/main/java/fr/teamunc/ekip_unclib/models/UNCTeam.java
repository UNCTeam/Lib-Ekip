package fr.teamunc.ekip_unclib.models;

import com.google.errorprone.annotations.RestrictedApi;
import fr.teamunc.ekip_unclib.EkipLib;
import lombok.*;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;

@Builder(toBuilder = true)
public class UNCTeam{
    @Getter
    private Map<String, Object> additionalInformation;
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

    public void setAdditionalInformation(String key, Object value) {
        this.additionalInformation.put(key, value);
    }

    public static UNCTeamBuilder builder(String name) {
        return new UNCTeamBuilder().name(name).players(new ArrayList<>()).additionalInformation(EkipLib.getTeamInformationInitialiser());
    }

    @RestrictedApi(explanation = "This method is only used by the plugin itself", link = "UncTeamController")
    public void addPlayer(UUID uuid) {
        this.players.add(uuid);
    }

    @RestrictedApi(explanation = "This method is only used by the plugin itself", link = "UncTeamController")
    public void removePlayer(UUID uuid) {
        this.players.remove(uuid);
    }
}
