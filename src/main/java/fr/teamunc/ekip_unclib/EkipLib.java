package fr.teamunc.ekip_unclib;

import fr.teamunc.ekip_unclib.controllers.UNCTeamController;
import fr.teamunc.ekip_unclib.minecraft.eventlisteners.PlayerListener;
import fr.teamunc.ekip_unclib.models.UNCTeamContainer;
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
    private static UNCTeamController teamController;
    @Getter
    private static Map<String, Object> teamInformationInitialiser;

    private EkipLib() {}

    public static void init(JavaPlugin plugin, Map<String, Object> teamInformationInitialiser) {
        EkipLib.plugin = plugin;
        EkipLib.teamInformationInitialiser = teamInformationInitialiser;

        // init team container
        teamController = new UNCTeamController(initTeamContainer());

        // register commands
        initCommands();
    }

    public static boolean isInit() {
        return Objects.nonNull(plugin);
    }

    private static UNCTeamContainer initTeamContainer() {
        UNCEntitiesContainer.init(plugin.getDataFolder());
        UNCTeamContainer res;

        try {
            res = UNCEntitiesContainer.loadContainer("teams", UNCTeamContainer.class);
        } catch (Exception e) {
            plugin.getLogger().info("Creating new team container file");
            res = new UNCTeamContainer();
        }
        return res;
    }

    private static void initCommands() {
        PluginCommand teamCommand = plugin.getCommand("uncteam");
        if (teamCommand != null) {
            teamCommand.setExecutor(new TeamCommands());
        }
    }

    public static void initGameListeners(Ekip_UNCLib ekip_uncLib) {
        ekip_uncLib.getServer().getPluginManager().registerEvents(new PlayerListener(), ekip_uncLib);
    }

}
