package fr.teamunc.ekip_unclib.minecraft.commandsExecutor;

import fr.teamunc.base_unclib.models.utils.helpers.Message;
import fr.teamunc.ekip_unclib.EkipLib;
import fr.teamunc.ekip_unclib.models.UNCTeam;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class TeamCommands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!EkipLib.isInit()) {
            EkipLib.logError("EkipLib is not initialized!");
            return true;
        }

        if (args.length != 0) {
            switch (args[0]) {
                case "create": {
                    if (args.length < 2) {
                        Message.Get().sendMessage("usage : /unct create <teamName> <(optional) teamColor> <(optional) customPrefix>", sender, true);
                    } else if (EkipLib.getTeamContainer().getTeam(args[1]) == null) {
                        UNCTeam.UNCTeamBuilder teamBuilder = UNCTeam.builder(args[1]);

                        if (args.length >= 3) {
                            teamBuilder.color(args[3]);
                        }

                        if (args.length >= 4) {
                            teamBuilder.prefix(args[4]);
                        } else {
                            teamBuilder.prefix(args[1]);
                        }

                        UNCTeam team = teamBuilder.build();

                        EkipLib.getTeamContainer().addTeam(team);
                        Message.Get().sendMessage("Team " + args[1] + " created!", sender, false);
                    } else {
                        Message.Get().sendMessage("Team " + args[1] + " already exists!", sender, true);
                    }
                    return true;
                }
                case "delete": {
                    if (args.length < 2) {
                        Message.Get().sendMessage("usage : /unct delete <teamName>", sender, true);
                    } else {
                        boolean deleted = EkipLib.getTeamContainer().removeTeam(args[1]);

                        if (deleted)
                            Message.Get().sendMessage("Team " + args[1] + " deleted!", sender, false);
                        else
                            Message.Get().sendMessage("Team " + args[1] + " not found!", sender, true);
                    }
                    return true;
                }
                case "join": {
                    if (args.length < 2) {
                        Message.Get().sendMessage("usage : /unct join <teamName> <playerName>", sender, true);
                    } else {
                        Player player = Bukkit.getPlayer(args[2]);
                        UNCTeam team = EkipLib.getTeamContainer().getTeam(args[1]);
                        if (player == null || team == null) {
                            Message.Get().sendMessage("Player or team not found!", sender, true);
                        } else {
                            UUID uuid = player.getUniqueId();
                            if (team.getPlayers().contains(uuid)) {
                                Message.Get().sendMessage("Player " + args[2] + " is already in team " + args[1], sender, true);
                            } else {
                                team.addPlayer(uuid);
                                Message.Get().sendMessage("Player " + args[2] + " added to team " + args[1], sender, false);
                            }
                        }
                    }
                    return true;
                }
                case "leave": {
                    if (args.length < 2) {
                        Message.Get().sendMessage("usage : /unct leave <teamName> <playerName>", sender, true);
                    } else {
                        Player player = Bukkit.getPlayer(args[2]);
                        UNCTeam team = EkipLib.getTeamContainer().getTeam(args[1]);

                        if (player == null || team == null) {
                            Message.Get().sendMessage("Player or team not found!", sender, true);
                        } else {
                            UUID uuid = player.getUniqueId();
                            if (!team.getPlayers().contains(uuid)) {
                                Message.Get().sendMessage("Player " + args[2] + " is not in team " + args[1], sender, true);
                            } else {
                                team.removePlayer(uuid);
                                Message.Get().sendMessage("Player " + args[2] + " removed from team " + args[1], sender, false);
                            }
                        }
                    }
                    return true;
                }
                case "list": {
                    if (args.length >= 2 && EkipLib.getTeamContainer().getTeams().stream().noneMatch(team -> team.getName().equals(args[1]))) {
                        Message.Get().sendMessage("usage : /unct list <(optional) teamName>", sender, true);
                    } else if (args.length >= 2) {
                        UNCTeam team = EkipLib.getTeamContainer().getTeam(args[1]);
                        if (team != null) {
                            Message.Get().sendMessage("--- Team " + team.getName() + " : ", sender, false);
                            Message.Get().sendMessage("Players : ", sender, false);
                            team.getPlayers().forEach(uuid -> Message.Get().sendMessage(Bukkit.getOfflinePlayer(uuid).getName(), sender, false));
                        } else {
                            Message.Get().sendMessage("Team " + args[1] + " not found!", sender, true);
                        }
                    } else {
                        Message.Get().sendMessage("--- Teams : ", sender, false);
                        EkipLib.getTeamContainer().getTeams().forEach(team -> Message.Get().sendMessage(team.getName(), sender, false));
                    }
                    return true;
                }
                case "info": {
                    if (args.length < 2) {
                        Message.Get().sendMessage("usage : /unct info <teamName>", sender, true);
                    } else {
                        UNCTeam team = EkipLib.getTeamContainer().getTeam(args[1]);
                        if (team != null) {
                            Message.Get().sendMessage("---- Team " + team.getName() + " : ", sender, false);
                            Message.Get().sendMessage("Players : ", sender, false);
                            team.getPlayers().forEach(uuid -> Message.Get().sendMessage(" - " + Bukkit.getOfflinePlayer(uuid).getName(), sender, false));
                            Message.Get().sendMessage("Prefix : " + team.getPrefix(), sender, false);
                            Message.Get().sendMessage("Color : " + team.getColor(), sender, false);

                            // eventual additional info
                            if (team.getAdditionalInformation() != null)
                                team.getAdditionalInformation().forEach((key, value) -> Message.Get().sendMessage(key + " : " + value, sender, false));
                        } else {
                            Message.Get().sendMessage("Team " + args[1] + " not found!", sender, true);
                        }
                    }
                    return true;
                }
                default:
                    Message.Get().sendMessage("usage : /unct <create|delete|join|leave|list|info>", sender, true);
                    return true;
            }
        } else {
            Message.Get().sendMessage("usage : /unct <create|delete|join|leave|list|info>", sender, true);
            return true;
        }
    }
}
