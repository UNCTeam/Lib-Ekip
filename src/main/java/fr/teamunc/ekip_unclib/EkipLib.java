package fr.teamunc.ekip_unclib;

import fr.teamunc.ekip_unclib.controllers.UNCTeamController;
import lombok.Getter;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;
import fr.teamunc.base_unclib.models.jsonEntities.UNCEntitiesContainer;
import fr.teamunc.ekip_unclib.minecraft.commandsExecutor.TeamCommands;

import java.util.Map;
import java.util.Objects;

public class EkipLib {
    
    @Getter
    private static JavaPlugin plugin;
    @Getter
    private static UNCTeamController teamContainer;
    @Getter
    private static Map<String, Object> teamInformationInitialiser;

    public static void init(JavaPlugin plugin, Map<String, Object> teamInformationInitialiser) {
        EkipLib.plugin = plugin;
        EkipLib.teamInformationInitialiser = teamInformationInitialiser;

        // init team container
        teamContainer = initTeamContainer();

        // register commands
        initCommands();
    }

    public static boolean isInit() {
        return Objects.nonNull(plugin);
    }

    private static UNCTeamController initTeamContainer() {
        UNCEntitiesContainer.init(plugin.getDataFolder());
        UNCTeamController res;

        try {
            res = UNCEntitiesContainer.loadContainer("teams", UNCTeamController.class);
        } catch (Exception e) {
            plugin.getLogger().info("Creating new team container file");
            res = new UNCTeamController();
        }
        return res;
    }

    private static void initCommands() {
        PluginCommand teamCommand = plugin.getCommand("uncteam");
        if (teamCommand != null) {
            teamCommand.setExecutor(new TeamCommands());
        }
    }

}
