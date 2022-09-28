package teamunc.ekip_unclib;

import org.bukkit.plugin.java.JavaPlugin;
import teamunc.ekip_unclib.models.UNCTeam;
import teamunc.ekip_unclib.models.UNCTeamContainer;

import java.util.Arrays;

public final class Ekip_UNCLib extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        UNCTeam team = UNCTeam.builder("test").build();
        UNCTeamContainer container = new UNCTeamContainer();
        container.registerEntity(team);
        container.save("teams");
        UNCTeamContainer reloadedContainer = UNCTeamContainer.loadContainer("teams", UNCTeamContainer.class);
        Arrays.stream(reloadedContainer.getEntities()).forEach(System.out::println);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
