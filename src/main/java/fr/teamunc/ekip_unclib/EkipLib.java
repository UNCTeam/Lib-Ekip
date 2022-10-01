package fr.teamunc.ekip_unclib;

import fr.teamunc.base_unclib.models.utils.helpers.Message;
import fr.teamunc.ekip_unclib.models.UNCTeamContainer;
import lombok.Getter;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;
import fr.teamunc.base_unclib.models.jsonEntities.UNCEntitiesContainer;
import fr.teamunc.ekip_unclib.minecraft.commandsExecutor.TeamCommands;

import java.util.Objects;

public class EkipLib {
    private static JavaPlugin plugin;
    @Getter
    private static UNCTeamContainer teamContainer;

    public static JavaPlugin getPlugin() {
        return plugin;
    }

    public static void init(JavaPlugin plugin) {
        EkipLib.plugin = plugin;
        UNCEntitiesContainer.init(plugin.getDataFolder());

        // init team container
        try {
            teamContainer = UNCEntitiesContainer.loadContainer("teams", UNCTeamContainer.class);
        } catch (Exception e) {
            e.printStackTrace();
            plugin.getLogger().info("Creating new team container file");
            teamContainer = new UNCTeamContainer();
        }

        // register commands
        PluginCommand teamCommand = plugin.getCommand("uncteam");
        if (teamCommand != null) {
            teamCommand.setExecutor(new TeamCommands());
        }
    }

    public static boolean isInit() {
        return Objects.nonNull(plugin);
    }
    public static void log(String message) {
        plugin.getLogger().info(message);
    }

    public static void logError(String message) {
        plugin.getLogger().severe(message);
    }

    public static void logWarning(String message) {
        plugin.getLogger().warning(message);
    }

    public static void logDebug(String message) {
        if (plugin.getConfig().getBoolean("debug")) {
            plugin.getLogger().info("[DEBUG] " + message);
        }
    }
}
