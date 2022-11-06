package fr.teamunc.ekip_unclib.minecraft.commandsExecutor;

import fr.teamunc.base_unclib.BaseLib;
import fr.teamunc.base_unclib.utils.helpers.Message;
import fr.teamunc.base_unclib.models.libtools.CommandsTab;
import fr.teamunc.ekip_unclib.EkipLib;
import fr.teamunc.ekip_unclib.models.UNCTeam;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collectors;

public class TeamCommands  extends CommandsTab implements CommandExecutor{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!EkipLib.isInit()) {
            Message.Get().sendMessage("EkipLib is not initialized!", sender, true);
            return true;
        }

        if (args.length != 0) {
            switch (args[0]) {
                case "create": {
                    if (args.length < 2) {
                        Message.Get().sendMessage("usage : /unct create <teamName> <(optional) teamColor> <(optional) customPrefix>", sender, true);
                    } else if (EkipLib.getTeamController().getTeam(args[1]) == null) {
                        UNCTeam.UNCTeamBuilder teamBuilder = UNCTeam.builder(args[1]);

                        if (args.length >= 3) {
                            teamBuilder.color(args[2]);
                        }

                        if (args.length >= 4) {
                            teamBuilder.prefix(args[3]);
                        } else {
                            teamBuilder.prefix(args[1]);
                        }

                        UNCTeam team = teamBuilder.build();

                        EkipLib.getTeamController().addTeam(team);
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
                        boolean deleted = EkipLib.getTeamController().removeTeam(args[1]);

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
                        UNCTeam team = EkipLib.getTeamController().getTeam(args[1]);
                        if (player == null || team == null) {
                            Message.Get().sendMessage("Player or team not found!", sender, true);
                        } else {
                            UUID uuid = player.getUniqueId();
                            if (team.getPlayers().contains(uuid)) {
                                Message.Get().sendMessage("Player " + args[2] + " is already in team " + args[1], sender, true);
                            } else {
                                EkipLib.getTeamController().joinTeam(player, team);
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
                        UNCTeam team = EkipLib.getTeamController().getTeam(args[1]);

                        if (player == null || team == null) {
                            Message.Get().sendMessage("Player or team not found!", sender, true);
                        } else {
                            UUID uuid = player.getUniqueId();
                            if (!team.getPlayers().contains(uuid)) {
                                Message.Get().sendMessage("Player " + args[2] + " is not in team " + args[1], sender, true);
                            } else {
                                EkipLib.getTeamController().leaveTeam(player, team);
                                Message.Get().sendMessage("Player " + args[2] + " removed from team " + args[1], sender, false);
                            }
                        }
                    }
                    return true;
                }
                case "list": {
                    if (args.length >= 2 && EkipLib.getTeamController().getTeams().stream().noneMatch(team -> team.getName().equals(args[1]))) {
                        Message.Get().sendMessage("usage : /unct list <(optional) teamName>", sender, true);
                    } else if (args.length >= 2) {
                        UNCTeam team = EkipLib.getTeamController().getTeam(args[1]);
                        if (team != null) {
                            Message.Get().sendMessage("--- Team " + team.getName() + " : ", sender, false);
                            Message.Get().sendMessage("Players : ", sender, false);
                            team.getPlayers().forEach(uuid -> Message.Get().sendMessage(Bukkit.getOfflinePlayer(uuid).getName(), sender, false));
                        } else {
                            Message.Get().sendMessage("Team " + args[1] + " not found!", sender, true);
                        }
                    } else {
                        Message.Get().sendMessage("--- Teams : ", sender, false);
                        EkipLib.getTeamController().getTeams().forEach(team -> Message.Get().sendMessage(team.getName(), sender, false));
                    }
                    return true;
                }
                case "info": {
                    if (args.length < 2) {
                        Message.Get().sendMessage("usage : /unct info <teamName>", sender, true);
                    } else {
                        UNCTeam team = EkipLib.getTeamController().getTeam(args[1]);
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

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> result;
        List<String> subcommands = Arrays.asList("create", "delete", "join", "leave", "list", "info");
        switch (args[0]) {
            case "create": {
                result = checkAllTab(
                        args,
                        subcommands,
                        EkipLib.getTeamController().getTeams().stream().map(UNCTeam::getName).collect(Collectors.toList()),
                        Arrays.stream(ChatColor.values()).map(ChatColor::name).collect(Collectors.toList()));
                break;
            }
            case "info":
            case "list":
            case "delete": {
                result = checkAllTab(
                        args,
                        subcommands,
                        EkipLib.getTeamController().getTeams().stream().map(UNCTeam::getName).collect(Collectors.toList()));
                break;
            }
            case "leave":
            case "join": {
                result = checkAllTab(
                        args,
                        subcommands,
                        EkipLib.getTeamController().getTeams().stream().map(UNCTeam::getName).collect(Collectors.toList()),
                        Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList()));
                break;
            }
            default: {
                result = checkAllTab(args, subcommands);
                break;
            }
        }

        //sort the list
        Collections.sort(result);
        return result;
    }
}
