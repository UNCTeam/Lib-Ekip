package fr.teamunc.ekip_unclib.minecraft.eventlisteners;

import fr.teamunc.ekip_unclib.EkipLib;
import fr.teamunc.ekip_unclib.models.UNCTeam;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Optional;

public class PlayerListener implements Listener {

    @EventHandler
    public void onPlayerSpeak(AsyncPlayerChatEvent event) {
        if (!EkipLib.isInit()) return;

        if(event.getPlayer().getName().equals("UNCDelsus") || event.getPlayer().getName().equals("ValkyrieHD")) {
            String format = ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "[STAFF]"
                    + ChatColor.GRAY + " %s:" + ChatColor.WHITE + " %s";
            event.setFormat(format);
            return;
        }

        Optional<UNCTeam> team = EkipLib.getTeamController().getTeams().stream()
                .filter(t -> t.getPlayers().contains(event.getPlayer().getUniqueId()))
                .findFirst();

        if(team.isPresent()) {
            // name of the team in the chat
            UNCTeam teamPlayer = team.get();

            String prefix = ChatColor.valueOf(teamPlayer.getColor()) + "" + ChatColor.BOLD + "[" + teamPlayer.getPrefix()+ "] ";

            String formatTchat = prefix + ChatColor.GRAY + " %s:" + ChatColor.WHITE + " %s";
            event.setFormat(formatTchat);
        } else event.setFormat("%s: %s");
    }

    @EventHandler
    public void onPlayerConnect(PlayerJoinEvent event) {
        if (!EkipLib.isInit()) return;

        String joinFormat = "" + ChatColor.RED + ChatColor.BOLD + "[ UNC ] " + ChatColor.RESET + "%s" + ChatColor.GOLD + " a rejoint la partie !";

        Optional<UNCTeam> team = EkipLib.getTeamController().getTeams().stream()
                .filter(t -> t.getPlayers().contains(event.getPlayer().getUniqueId()))
                .findFirst();

        if(team.isPresent()) {
            UNCTeam teamPlayer = team.get();

            // custom join message
            String prefix = "" + ChatColor.valueOf(teamPlayer.getColor()) + ChatColor.BOLD + "[" + teamPlayer.getPrefix()+ "] ";
            event.setJoinMessage(String.format(joinFormat, ChatColor.RESET + prefix + event.getPlayer().getName()));

            // custom tablist
            event.getPlayer().setPlayerListName(prefix + event.getPlayer().getName());
        } else {
            event.setJoinMessage(String.format(joinFormat, ChatColor.GRAY + event.getPlayer().getName()));
        }
    }

    @EventHandler
    public void onPlayerDisconnect(PlayerQuitEvent event) {
        if (!EkipLib.isInit()) return;

        String quitFormat = "" + ChatColor.RED + ChatColor.BOLD + "[ UNC ] " + ChatColor.RESET + "%s" + ChatColor.GOLD + " a quitté la partie !";

        Optional<UNCTeam> team = EkipLib.getTeamController().getTeams().stream()
                .filter(t -> t.getPlayers().contains(event.getPlayer().getUniqueId()))
                .findFirst();

        if(team.isPresent()) {
            UNCTeam teamPlayer = team.get();

            // custom quit message
            String prefix = "" + ChatColor.valueOf(teamPlayer.getColor()) + ChatColor.BOLD + "[" + teamPlayer.getPrefix()+ "] ";
            event.setQuitMessage(String.format(quitFormat, ChatColor.RESET + prefix + event.getPlayer().getName()));
        } else {
            event.setQuitMessage(String.format(quitFormat, ChatColor.GRAY + event.getPlayer().getName()));
        }
    }

}
