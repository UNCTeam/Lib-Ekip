package fr.teamunc.ekip_unclib.controllers;

import fr.teamunc.base_unclib.utils.helpers.Message;
import fr.teamunc.ekip_unclib.models.UNCTeam;
import fr.teamunc.ekip_unclib.models.UNCTeamContainer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

public class UNCTeamController{

    private final UNCTeamContainer container;

    public List<UNCTeam> getTeams() {
        return container.getTeams();
    }

    @Nullable
    public UNCTeam getTeam(String name) {
        return getTeams().stream().filter(team -> team.getName().equals(name)).findFirst().orElse(null);
    }

    public void addTeam(UNCTeam team) {
        getTeams().add(team);

        // custom tablist
        refreshPlayerList();
    }

    public void joinTeam(Player player, UNCTeam team) {
        team.addPlayer(player.getUniqueId());

        // custom tablist
        refreshPlayerList();
    }

    public void leaveTeam(Player player, UNCTeam team) {
        team.removePlayer(player.getUniqueId());

        // custom tablist
        refreshPlayerList();
    }

    public boolean removeTeam(String teamName) {
        Optional<UNCTeam> optionnalTeam = Optional.ofNullable(getTeam(teamName));

        if (optionnalTeam.isPresent()) {
            getTeams().remove(optionnalTeam.get());
            return true;
        }

        // custom tablist
        refreshPlayerList();

        return false;
    }

    public UNCTeamController(UNCTeamContainer uncTeamContainer) {
        this.container = uncTeamContainer;
        Message.Get().broadcastMessageToConsole("[CustomTeamController] : Loading " + container.getTeams().size() + " teams");

        // custom tablist
        refreshPlayerList();
    }

    public void refreshPlayerList() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            Optional<UNCTeam> team = getTeams().stream()
                    .filter(t -> t.getPlayers().contains(player.getUniqueId()))
                    .findFirst();

            if (team.isPresent()) {
                UNCTeam teamPlayer = team.get();

                // custom tablist
                player.setPlayerListName("" + ChatColor.valueOf(teamPlayer.getColor()) + ChatColor.BOLD + "[" + teamPlayer.getPrefix() + "] " + player.getName());
            } else {
                // custom tablist
                player.setPlayerListName(player.getName());
            }
        });
    }

    public void save(String fileName) {
        container.save(fileName);
    }
}
