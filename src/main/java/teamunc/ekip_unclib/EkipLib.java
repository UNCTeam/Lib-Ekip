package teamunc.ekip_unclib;

import org.bukkit.plugin.java.JavaPlugin;

public class EkipLib {
    private static JavaPlugin plugin;

    public static JavaPlugin getPlugin() {
        return plugin;
    }

    public static void init(JavaPlugin plugin) {
        EkipLib.plugin = plugin;
    }
}
