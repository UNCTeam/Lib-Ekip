package teamunc.ekip_unclib;

import org.bukkit.plugin.java.JavaPlugin;
import teamunc.base_unclib.BaseLib;
import teamunc.base_unclib.Base_UNCLib;

import java.util.Objects;

public class EkipLib {
    private static JavaPlugin plugin;

    public static JavaPlugin getPlugin() {
        return plugin;
    }

    public static void init(JavaPlugin plugin) {
        EkipLib.plugin = plugin;
        BaseLib.init(plugin);
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
