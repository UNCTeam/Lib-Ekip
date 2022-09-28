package teamunc.ekip_unclib.models;

import lombok.*;
import teamunc.base_unclib.models.jsonEntities.UNCEntitySerializable;

import java.util.Collection;
import java.util.UUID;

@Builder(toBuilder = true)
public class UNCTeam extends UNCEntitySerializable {
    @Getter
    private IUNCTeamInformation additionalInformation;
    @Getter
    private String color;
    @Getter @NonNull
    private String name;
    @Getter
    private String prefix;
    @Getter @Singular
    private Collection<UUID> players;
    @Getter
    private UUID teamId;

    public <T> T getAdditionalInformation(String key, Class<T> clazz) throws ClassCastException {
        return clazz.cast(this.additionalInformation.get(key));
    }

    public static UNCTeamBuilder builder(String name) {
        return new UNCTeamBuilder().name(name);
    }
}
