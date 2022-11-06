package fr.teamunc.ekip_unclib;

import org.bukkit.plugin.java.JavaPlugin;

public final class Ekip_UNCLib extends JavaPlugin {

    @Override
    public void onEnable() {
        EkipLib.initGameListeners(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        if (EkipLib.isInit()) {
            EkipLib.getTeamController().save("teams");
        }
    }
}
